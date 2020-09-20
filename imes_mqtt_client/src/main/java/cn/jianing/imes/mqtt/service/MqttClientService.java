package cn.jianing.imes.mqtt.service;

import cn.jianing.imes.common.utils.ByteUtils;
import cn.jianing.imes.domain.mqtt.MqttResult;
import cn.jianing.imes.domain.mqtt.Payload;
import cn.jianing.imes.mqtt.MqttClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;



@Slf4j
@Service
public class MqttClientService implements CommandLineRunner {


    @Resource
    private MqttClient mqttClient;
    @Resource
    private SubscribeService subscribeService;
    @Resource
    private MqttClientConfig mqttClientConfig;


    @Override
    public void run(String... args) throws Exception {
        if (mqttClient.isConnected()) mqttClient.disconnect();
        log.info("mqtt 开始连接......");
        mqttClient.connect();
        log.info("mqtt 连接完成");
        mqttClient.setCallback(subscribeService);
        mqttClient.subscribe(mqttClientConfig.getTopicPublish() + "#");
    }

    public MqttResult getCurrentDeviceProperty(String gatewayId, String variableAddress) {
        String topicStr = mqttClientConfig.getTopicPublish() + gatewayId + "/" + variableAddress;
        MqttMessage mqttMessage = mqttClientConfig.getMessageMap().get(topicStr);
        try {
            Payload payload = this.getMessagePayload(mqttMessage);
            return new MqttResult("", payload.getData().getValue(), payload.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public List<DeviceProperty> getOnlineReadDevicePropertiesByGatewayId(String gatewayId) {
//        Map<String, MqttMessage> messageMap = mqttClientConfig.getMessageMap();
//        List<DeviceProperty> devicePropertyList = new ArrayList<>();
//        for (Map.Entry<String, MqttMessage> stringMqttMessageEntry : messageMap.entrySet()) {
//            String key = stringMqttMessageEntry.getKey();
//            String[] split = StringUtils.split(key, "/");
//            if (split[1].equals("publish") && split[2].equals(gatewayId)) {
//                MqttMessage mqttMessage = stringMqttMessageEntry.getValue();
//                DeviceProperty deviceProperty = new DeviceProperty();
//                try {
//                    Payload payload = (Payload) ByteUtils.getObject(mqttMessage.getPayload());
//                    deviceProperty.setGatewayId(gatewayId);
//                    deviceProperty.setVariableType(payload.getData().getType());
//                    deviceProperty.setVariableAddress(payload.getData().getAddress());
//                    deviceProperty.setValue(payload.getData().getValue());
//                    deviceProperty.setTime(new Timestamp(Long.valueOf(payload.getTime())));
//                    devicePropertyList.add(deviceProperty);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return devicePropertyList;
//    }


    /**
     * 将Json 字符串解析成 Payload 对象
     * @param mqttMessage  mqtt 包下的消息返回类型 其中的payload 为byte[], JSON 字符串
     * @return Payload 在common-model包下定义
     * @throws IOException 异常
     * @throws ClassNotFoundException 异常
     */
    public Payload getMessagePayload(MqttMessage mqttMessage) throws IOException, ClassNotFoundException {
        return (Payload) ByteUtils.getObject(mqttMessage.getPayload());
    }
}
