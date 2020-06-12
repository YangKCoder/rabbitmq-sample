package com.deveye.consumer.component;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class RabbitReceive {


    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            value = "queue-1",
                            durable = "true"
                    ),
                    exchange = @Exchange(
                            value = "exchange-1",
                            durable = "true",
                            type = "topic",
                            ignoreDeclarationExceptions = "true"
                    ),
                    key = "springboot.*"
            )
    )
    @RabbitHandler
    public void onMessage(Message<?> message, Channel channel) throws Exception {
        System.err.println("----------------------------");
        System.err.println("消费信息" + message.getPayload());
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTag, false);
    }

}
