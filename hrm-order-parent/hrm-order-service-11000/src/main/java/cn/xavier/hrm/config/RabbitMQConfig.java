package cn.xavier.hrm.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//rabbitMQ的配置
@Configuration
public class RabbitMQConfig {


    //以下配置RabbitMQ消息服务
    @Autowired
    public ConnectionFactory connectionFactory;

    //

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