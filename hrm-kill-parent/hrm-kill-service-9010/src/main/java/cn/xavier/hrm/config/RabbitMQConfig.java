package cn.xavier.hrm.config;

import cn.xavier.hrm.constant.MQConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static cn.xavier.hrm.constant.MQConstants.*;

//rabbitMQ的配置
@Configuration
public class RabbitMQConfig {




    //以下配置RabbitMQ消息服务
    @Autowired
    public ConnectionFactory connectionFactory;


    //定义交换机
    @Bean(KILL_EXCHANGE_NAME_TOPIC)
    public Exchange exchangeNameTopic(){
        return ExchangeBuilder.topicExchange(KILL_EXCHANGE_NAME_TOPIC).durable(true).build();
    }

    //定义订单的队列Bean
    @Bean(KILL_QUEUE_NAME_ORDER)
    public Queue orderQueue(){
        return new Queue(KILL_QUEUE_NAME_ORDER,true);
    }

    //延迟队列
    @Bean(KILL_DELAY_QUEUE_NAME_ORDER)
    public Queue delayQueue(){
        Map<String,Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", MQConstants.KILL_EXCHANGE_NAME_TOPIC);  //过期的消息给哪个交换机的名字
        map.put("x-dead-letter-routing-key", KILL_DEATH_ROUTINGKEY_ORDER);   //死信交换机把消息个哪个个routingkey
        map.put("x-message-ttl", 20000);    //队列过期时间10s

        return new Queue(KILL_DELAY_QUEUE_NAME_ORDER,true,false,false,map);
    }

    //死信队列  就是一个普通队列 只是这个队列专门用处理超时的订单消息
    @Bean(KILL_DEATH_QUEUE_NAME_ORDER)
    public Queue deathQueue(){
        return new Queue(KILL_DEATH_QUEUE_NAME_ORDER,true);
    }

    //支付结果队列
    @Bean(MQConstants.KILL_PAY_RESULT_QUEUE_NAME)
    public Queue payResultQueue(){
        return new Queue(MQConstants.KILL_PAY_RESULT_QUEUE_NAME,true);
    }


    @Bean
    public Binding payResultQueueBinding(){
        // #.systemmessage.course
        return BindingBuilder.bind(payResultQueue()).to(exchangeNameTopic()).with(MQConstants.KILL_PAY_RESULT_ROUTINGKEY).noargs();
    }


    @Bean
    public Binding delayOrderQueueBinding(){
        // #.systemmessage.course
        return BindingBuilder.bind(delayQueue()).to(exchangeNameTopic()).with(KILL_DELAY_ROUTINGKEY_ORDER).noargs();
    }

    @Bean
    public Binding deathOrderQueueBinding(){
        // #.systemmessage.course
        return BindingBuilder.bind(deathQueue()).to(exchangeNameTopic()).with(KILL_DEATH_ROUTINGKEY_ORDER).noargs();
    }

    @Bean
    public Binding orderQueueBinding(){
        // #.systemmessage.course
        return BindingBuilder.bind(orderQueue()).to(exchangeNameTopic()).with(KILL_ROUTINGKEY_ORDER).noargs();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 这里的转换器设置实现了发送消息时自动序列化消息对象为message body
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        //投递消息失败，把消息返回给回调函数
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }
}