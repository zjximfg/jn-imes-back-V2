package cn.jianing.imes.mqtt.service;

import cn.jianing.imes.domain.device.DeviceProperty;
import cn.jianing.imes.domain.mqtt.MqttResult;
import cn.jianing.imes.domain.mqtt.Payload;
import cn.jianing.imes.mqtt.MqttClientConfig;
import com.alibaba.fastjson.JSONObject;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubscribeService implements MqttCallback {

    public static Map<String, List<DeviceProperty>> socketMap = new HashMap<>();

    @Resource
    private MqttClient mqttClient;
    @Resource
    private MqttClientConfig mqttClientConfig;
    @Resource
    private MqttClientService mqttClientService;

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println(cause.getMessage());
        try {
            System.out.println("开始自动重连！");
            mqttClient.reconnect();
            System.out.println("重新连接完成");
            mqttClient.setCallback(this);
            mqttClient.subscribe(mqttClientConfig.getTopicPublish() + "#");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        mqttClientConfig.getMessageMap().put(topic, message);
        // todo 发布websocket
        // 1. 通过gateway
        for (Map.Entry<String, List<DeviceProperty>> gatewayMap : socketMap.entrySet()) {
            for (DeviceProperty deviceProperty : gatewayMap.getValue()) {
                // 按照topic查询
                String topicQuery = mqttClientConfig.getTopicPublish() + gatewayMap.getKey() + "/" +deviceProperty.getVariableAddress();
                System.out.println(topicQuery);
                if (topic.equals(topicQuery)){
                    System.out.println(topicQuery);
                    // 构造一个mqttResult
                    Payload messagePayload = JSONObject.parseObject(message.toString(), Payload.class);
                    System.out.println(messagePayload);
//                    Payload messagePayload = mqttClientService.getMessagePayload(message);
                    MqttResult mqttResult = new MqttResult(deviceProperty.getId(),  messagePayload.getData().getValue(), messagePayload.getTime());
                    System.out.println(mqttResult);
                    String string = JSONObject.toJSONString(mqttResult);
                    //simpMessagingTemplate.setMessageConverter(new MappingFastJsonMessageConverter());
                    System.out.println(string);
                   try {
                       simpMessagingTemplate.convertAndSend("/topic/" + gatewayMap.getKey(), string);
                   }catch (Exception e){
                       System.out.println("错误");
                   }

                }
            }
        }
        System.out.println(topic);
        System.out.println(message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
