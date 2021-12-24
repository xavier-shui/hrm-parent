package cn.xavier.hrm.constant;

/**
 * @author Zheng-Wei Shui
 * @date 12/23/2021
 */
public interface RabbitMqConstant {
    String COURSE_MESSAGE_EXCHANGE = "course_message_exchange";
    String COURSE_MESSAGE_QUEUE_SMS = "course_message_queue_sms";
    String COURSE_MESSAGE_QUEUE_EMAIL = "course_message_queue_email";
    String COURSE_MESSAGE_QUEUE_SYSTEM = "course_message_queue_system";
    String COURSE_MESSAGE_ROUTING_KEY_SYSTEM = "message.system";
    String COURSE_MESSAGE_ROUTING_KEY_SMS = "message.sms";
    String COURSE_MESSAGE_ROUTING_KEY_EMAIL = "message.email";
}
