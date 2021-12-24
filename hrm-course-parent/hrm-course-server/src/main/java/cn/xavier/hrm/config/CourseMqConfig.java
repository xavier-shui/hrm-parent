package cn.xavier.hrm.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static cn.xavier.hrm.constant.RabbitMqConstant.*;

/**
 * @author Zheng-Wei Shui
 * @date 12/23/2021
 */
@Configuration
public class CourseMqConfig {
    @Bean
    public Exchange createExchange() {
        return ExchangeBuilder.topicExchange(COURSE_MESSAGE_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue createSmsQueue() {
        return QueueBuilder.durable(COURSE_MESSAGE_QUEUE_SMS).build();
    }

    @Bean
    public Queue createEmailQueue() {
        return QueueBuilder.durable(COURSE_MESSAGE_QUEUE_EMAIL).build();
    }

    @Bean
    public Queue createSystemQueue() {
        return QueueBuilder.durable(COURSE_MESSAGE_QUEUE_SYSTEM).build();
    }

    @Bean
    public Binding createSmsBinding() {
        return BindingBuilder.bind(createSmsQueue()).to(createExchange()).with(COURSE_MESSAGE_ROUTING_KEY_SMS).noargs();
    }

    @Bean
    public Binding createEmailBinding() {
        return BindingBuilder.bind(createEmailQueue()).to(createExchange()).with(COURSE_MESSAGE_ROUTING_KEY_EMAIL).noargs();
    }

    @Bean
    public Binding createSystemBinding() {
        return BindingBuilder.bind(createSystemQueue()).to(createExchange()).with(COURSE_MESSAGE_ROUTING_KEY_SYSTEM).noargs();
    }
}
