package cn.xavier.hrm.service.impl;

import cn.xavier.hrm.constant.MQConstants;
import cn.xavier.hrm.domain.KillOrder;
import cn.xavier.hrm.domain.PayFlow;
import cn.xavier.hrm.dto.AlipayNotifyDto;
import cn.xavier.hrm.dto.PayResultToMQ;
import cn.xavier.hrm.mapper.PayFlowMapper;
import cn.xavier.hrm.properties.AlipayProperties;
import cn.xavier.hrm.service.IPayFlowService;
import cn.xavier.hrm.util.ValidUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author terrylv
 * @since 2021-03-21
 */
@Service
@Slf4j
public class PayFlowServiceImpl extends ServiceImpl<PayFlowMapper, PayFlow> implements IPayFlowService {

    @Autowired
    private RedisTemplate redisTemplate ;

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private RabbitTemplate rabbitTemplate ;

    @Override
    public String pay(String orderNo) {
        //1.根据订单号查询订单
        KillOrder order = (KillOrder) redisTemplate.opsForValue().get("order:" + orderNo);
        ValidUtils.assertNotNull(order,"无效的订单号，或订单正在处理中...您可以稍后重试");

        //2.封装支付参数

        // 1. 设置参数（全局只需设置一次）
        Factory.setOptions(alipayProperties.getOptions());
        log.info("notifyUrl:"+alipayProperties.getNotifyUrl());;
        try {
            // 2. 发起API调用,网页支付
            //String subject, String outTradeNo, String totalAmount, String returnUrl
            AlipayTradePagePayResponse response = Factory.Payment.Page().pay(
                    order.getTitle(), order.getOrderNo(), order.getTotalPrice().toString(), alipayProperties.getReturnUrl());
            // 3. 处理响应或异常
            if (ResponseChecker.success(response)) {
                log.info("支付申请成功");
                baseMapper.insert(createPayFlow(order.getTitle(),order.getTotalPrice(),order.getOrderNo(),order.getId()));
                //html格式的文本，需要把这个内容用response写给浏览器
                return response.getBody();
            } else {
                log.error("支付申请失败");
            }

        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            log.info("支付申请异常[{}]",e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return "支付申请失败";
    }

    @Override
    public PayFlow selectByNo(String payNo) {
        return baseMapper.selectByNo(payNo);
    }

    @Override
    public String payNotify(AlipayNotifyDto alipayNotifyDto) {
        try {
            //1.判断参数
            //2.验签名
            if(!StringUtils.hasLength(alipayNotifyDto.getSign())){
                log.info("签名错误....");
                return "false";
            }

            boolean signSuccess = Factory.Payment.Common().verifyNotify(JSON.parseObject(JSON.toJSONString(alipayNotifyDto), Map.class));

            if(!signSuccess){
                log.info("签名错误....");
                return "false";
            }

            //3.判断金额，订单号
            //修改流水状态
            PayFlow payFlow = baseMapper.selectByNo(alipayNotifyDto.getOut_trade_no());
            if(payFlow == null){
                log.info("流水号错误....");
                return "false";
            }
            synchronized (this){
                //幂等性处理
                if(payFlow.getPayStatus() != PayFlow.STATUS_APPLY){
                    log.info("流水号错误....");
                    return "success";
                }

                boolean amountOk = payFlow.getAmount().compareTo(new BigDecimal(alipayNotifyDto.getTotal_amount())) == 0;
                ValidUtils.isTrue(amountOk,"支付结果处理金额验证不一致");

                //4.修改支付记录的状态
                String messsage = null;
                if(alipayNotifyDto.isTradeSuccess()) {
                    payFlow.setPayStatus(PayFlow.STATUS_SUCCESS);
                    messsage = "支付成功";
                    payFlow.setResultDesc(messsage);
                    baseMapper.updateById(payFlow);
                }else{
                    payFlow.setPayStatus(PayFlow.STATUS_FAIL);
                    messsage ="支付失败["+alipayNotifyDto.getMsg()+"]";
                    payFlow.setResultDesc(messsage);
                    baseMapper.updateById(payFlow);
                }
            }
            PayResultToMQ payResultToMQ = new PayResultToMQ();
            BeanUtils.copyProperties(alipayNotifyDto , payResultToMQ);

            //5.往MQ发送支付结果消息
            rabbitTemplate.convertAndSend(
                    MQConstants.KILL_EXCHANGE_NAME_TOPIC,
                    MQConstants.KILL_PAY_RESULT_ROUTINGKEY,
                    payResultToMQ);
            log.info("支付结果发送到MQ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private PayFlow createPayFlow(String intro, BigDecimal amount, String orderNo, Long relationId){
        PayFlow payFlow = new PayFlow();
        payFlow.setAmount(amount);
        payFlow.setIntro(intro);
        payFlow.setPayNo(orderNo);
        payFlow.setPayStatus(PayFlow.STATUS_APPLY);
        //支付方式:0余额直接，1支付宝，2微信,3京东支付，4蚂蚁花呗
        payFlow.setPayType(PayFlow.TYPE_BUY_ALI);
        payFlow.setResultDesc("支付中");
        payFlow.setRelationId(relationId);
        return payFlow;
    }
}
