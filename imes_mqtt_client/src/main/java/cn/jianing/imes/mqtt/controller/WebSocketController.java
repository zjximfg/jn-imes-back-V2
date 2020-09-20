package cn.jianing.imes.mqtt.controller;

import cn.jianing.imes.domain.device.Device;
import cn.jianing.imes.domain.device.DeviceProperty;
import cn.jianing.imes.mqtt.client.DeviceClient;
import cn.jianing.imes.mqtt.service.SubscribeService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class WebSocketController {

    @Resource
    private DeviceClient deviceClient;

    @MessageMapping("/subscribe")
    public void publishDeviceProperty(@Payload String message) {
        // 读取网关id
        System.out.println(message);
        Device device = deviceClient.getDeviceById(message).getBody();
        System.out.println(device);
        if (device == null || device.getGatewayId() == null || device.getTemplateId() == null) {
            return;
        }
        List<DeviceProperty> readPropertyList = deviceClient.getReadPropertyListByTemplateId(device.getTemplateId()).getBody();
        if (readPropertyList == null || readPropertyList.size() <= 0) {
            return;
        }
        System.out.println(readPropertyList);

        SubscribeService.socketMap.put(device.getGatewayId(), readPropertyList);

    }

    @MessageMapping("/disconnect")
    public void disconnect(@Payload String message) {
        System.out.println(message);

        // 读取网关id
        Device device = deviceClient.getDeviceById(message).getBody();

        if (device == null || device.getGatewayId() == null) {
            return;
        }
        SubscribeService.socketMap.remove(device.getGatewayId());
    }

}
