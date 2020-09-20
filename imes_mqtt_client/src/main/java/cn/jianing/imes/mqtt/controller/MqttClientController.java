package cn.jianing.imes.mqtt.controller;

import cn.jianing.imes.domain.mqtt.MqttResult;
import cn.jianing.imes.mqtt.service.MqttClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("mqtt")
public class MqttClientController {

    @Resource
    private MqttClientService mqttClientService;

    @GetMapping("current")
    public ResponseEntity<MqttResult> getCurrentDeviceProperty(@RequestParam("gatewayId") String gatewayId, @RequestParam("variableAddress") String variableAddress) {
        MqttResult mqttResult = mqttClientService.getCurrentDeviceProperty(gatewayId, variableAddress);
        return ResponseEntity.ok(mqttResult);
    }

//    @GetMapping("online/read")
//    public ResponseEntity<List<DeviceProperty>> getOnlineReadDevicePropertiesByGatewayId(@RequestParam("gatewayId") String gatewayId) {
//        List<DeviceProperty> devicePropertyList = mqttClientService.getOnlineReadDevicePropertiesByGatewayId(gatewayId);
//        return ResponseEntity.ok(devicePropertyList);
//    }
}
