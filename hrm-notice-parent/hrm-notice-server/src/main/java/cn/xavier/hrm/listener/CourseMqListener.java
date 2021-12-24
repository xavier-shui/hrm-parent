package cn.xavier.hrm.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.io.IOException;
import static cn.xavier.hrm.constant.RabbitMqConstant.*;

/**
 * @author Zheng-Wei Shui
 * @date 12/23/2021
 */
@Component
public class CourseMqListener {

    @RabbitListener(queues = COURSE_MESSAGE_QUEUE_SMS)
    public void handleCourseMqSms(String content, Message message, Channel channel) throws IOException {
        System.out.println(content);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = COURSE_MESSAGE_QUEUE_EMAIL)
    public void handleCourseMqEmail(String content, Message message, Channel channel) throws IOException {
        System.out.println(content);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = COURSE_MESSAGE_QUEUE_SYSTEM)
    public void handleCourseMqSystem(String content, Message message, Channel channel) throws IOException {
        System.out.println(content);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
