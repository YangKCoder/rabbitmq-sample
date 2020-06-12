package cn.deveye.producer;

import cn.deveye.producer.component.RabbitSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class RabbitmqProducerApplicationTests {

    @Test
    void contextLoads() {
    }


    @Autowired
    private RabbitSender rabbitSender;


    @Test
    public void testSender() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put("attr1", "12345");
        properties.put("attr2", "abcde");
        rabbitSender.send("hello rabbitMQ", properties);

        Thread.sleep(10000);
    }


}
