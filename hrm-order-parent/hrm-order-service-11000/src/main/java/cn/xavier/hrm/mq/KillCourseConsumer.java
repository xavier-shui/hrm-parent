package cn.xavier.hrm.mq;

import cn.xavier.hrm.constant.MQConstants;
import cn.xavier.hrm.domain.KillOrder;
import cn.xavier.hrm.domain.KillOrderItem;
import cn.xavier.hrm.dto.PayResultToMQ;
import cn.xavier.hrm.mapper.KillOrderItemMapper;
import cn.xavier.hrm.mapper.KillOrderMapper;
import cn.xavier.hrm.vo.KillOrderVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class KillCourseConsumer {

    @Autowired
    private KillOrderMapper killOrderMapper ;

    @Autowired
    private KillOrderItemMapper killOrderItemMapper ;

    @Autowired
    private RedisTemplate redisTemplate ;

    @Autowired
    private RabbitTemplate rabbitTemplate ;

    @Autowired
    private RedissonClient redissonClient ;

    //保存订单
    @RabbitListener(queues = MQConstants.KILL_QUEUE_NAME_ORDER)
    public void saveOrder(@Payload KillOrderVo vo, Message message , Channel channel){
        //保存订单到DB
        //幂等性处理
        Boolean hasKey = redisTemplate.hasKey(vo.getOrderSn());
        if(hasKey)return ;

        Date date = new Date();
        KillOrder order = new KillOrder();
        order.setTitle(vo.getKillCourseName());
        order.setTotalPrice(vo.getPrice());
        order.setCreateTime(date);
        order.setKillCount(vo.getKillCount());
        order.setOrderNo(vo.getOrderSn());
        order.setStatusOrder(KillOrder.STATE_ORDER_UN_PAY);
        order.setStatusPay(KillOrder.STATE_PAY_UN_PAY);
        order.setUserId(vo.getUserId());
        killOrderMapper.insert(order);

        KillOrderItem item = new KillOrderItem();
        item.setCount(vo.getKillCount());
        item.setCreateTime(date);
        item.setKillCourseId(vo.getKillCourseId());
        item.setKillCourseName(vo.getKillCourseName());
        item.setKillOrderId(order.getId());
        item.setPrice(vo.getPrice());
        item.setSessionNumber(vo.getSessionNumber());
        killOrderItemMapper.insert(item) ;

        //往Redis保存订单，用来做支付
        redisTemplate.opsForValue().set("order:"+vo.getOrderSn(),order);

        //往延迟队列存放消息
        rabbitTemplate.convertAndSend(MQConstants.KILL_EXCHANGE_NAME_TOPIC
                ,MQConstants.KILL_DELAY_ROUTINGKEY_ORDER,vo);

        log.info("下单成功,添加消息到延迟队列：{} : {}",vo.getOrderSn(),vo.getKillCourseName());
        ack(vo, message, channel);
    }

    private void ack(@Payload KillOrderVo vo, Message message, Channel channel) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            redisTemplate.opsForValue().set(vo.getOrderSn(),"",10, TimeUnit.MINUTES);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    //处理过期消息
    @RabbitListener(queues = MQConstants.KILL_DEATH_QUEUE_NAME_ORDER)
    public void expireOrder(@Payload KillOrderVo vo, Message message , Channel channel){
        //1.判断订单的状态是否确实是未支付
        String orderSn = vo.getOrderSn();
        KillOrder killOrder = killOrderMapper.selectByOrderSn(orderSn);
        if(killOrder.getStatusPay() == KillOrder.STATE_PAY_UN_PAY){
            //2.退回库存
            RSemaphore semaphore = redissonClient.getSemaphore("store:" + vo.getSessionNumber() + ":" + vo.getKillCourseId());
            semaphore.addPermits(vo.getKillCount());

            //支付状态，订单状态 ： 支付超时
            killOrder.setStatusPay(KillOrder.STATE_PAY_OUT_TIME);
            killOrder.setStatusOrder(KillOrder.STATE_ORDER_FAIL);
            killOrderMapper.updateById(killOrder);

            redisTemplate.delete("killlog:"+vo.getUserId());
        }

        ack(vo, message, channel);

    }


    //处理支付结果
    @RabbitListener(queues = MQConstants.KILL_PAY_RESULT_QUEUE_NAME)
    public void payResult(@Payload PayResultToMQ result, Message message , Channel channel){
        //1.根据订单查询订单
        String out_trade_no = result.getOut_trade_no();
        KillOrder killOrder = killOrderMapper.selectByOrderSn(out_trade_no);
        synchronized (this){
            if(killOrder.getStatusPay() == KillOrder.STATE_PAY_UN_PAY){

                //2.修改订单状态为已支付
                if(result.isTradeSuccess()){
                    killOrder.setStatusPay(KillOrder.STATE_PAY_SUCCESS);
                    killOrder.setStatusOrder(KillOrder.STATE_ORDER_PAY_SUCCESS);
                    //3.自动发货逻辑 ：用一个表，把当前订单对应的秒杀课程ID和用户ID保存起来，
                    // 代表用户可以看这个课程了，因为购买成功了
                    //TODO
                }else{
                    killOrder.setStatusPay(KillOrder.STATE_PAY_FAIL);
                    killOrder.setStatusOrder(KillOrder.STATE_ORDER_FAIL);
                    redisTemplate.delete("killlog:"+killOrder.getUserId());
                }
                killOrder.setPayUpdateTime(new Date());
                killOrderMapper.updateById(killOrder);
                log.warn("支付结果处理：{}",result);
            }else{
                log.warn("支付结果已经被处理：{}",result);
            }
        }

        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
