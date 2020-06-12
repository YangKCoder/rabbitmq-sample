package cn.deveye.producer.component;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 这里是确认消息的回调监听接口，用于确认消息是否被broker收到
     */
    final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
        /**
         * @param correlationData 作为唯一表示
         * @param ack 是否落盘成功
         * @param cause 失败原因
         */
        System.err.println("消息ACK结果:" + ack + " ," + correlationData);
    };


    /**
     * 对外发送消息的方法
     */

    public void send(Object message, Map<String, Object> properties) throws Exception {
        MessageHeaders messageHeaders = new MessageHeaders(properties);
        Message<?> msg = MessageBuilder.createMessage(message, messageHeaders);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        MessagePostProcessor messagePostProcessor = m -> {
            System.err.println("--> post to do:" + m);
            return m;
        };
        rabbitTemplate.convertAndSend("exchange-1",
                "springboot.rabbit", msg, messagePostProcessor, correlationData);
    }
}
