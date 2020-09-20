package cn.jianing.imes.mqtt;

import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Configuration
@Data
public class MqttClientConfig {

    @Value("${mqtt.server.host}")
    private String host;

    @Value("${mqtt.server.port}")
    private Integer port;

    @Value("${mqtt.server.topic-publish}")
    private String topicPublish;

    @Value(("${mqtt.server.topic-subscribe}"))
    private String topicSubscribe;

    private String broker;    // tcp://182.92.194.48:1883

    private Map<String, MqttMessage> messageMap = new HashMap<>();  // key: topic, value: message

    @Value("${spring.application.name}")
    private String clientName;

//    private static MemoryPersistence persistence = new MemoryPersistence();

    @Bean
    public MqttClient mqttClient() {
        broker = "tcp://" + host + ":" + port;
        MqttClient client = null;
        try {
            client = new MqttClient(broker, clientName);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return client;
    }
}
