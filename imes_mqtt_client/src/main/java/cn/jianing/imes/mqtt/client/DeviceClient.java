package cn.jianing.imes.mqtt.client;

import cn.jianing.imes.domain.device.Device;
import cn.jianing.imes.domain.device.DeviceProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "imes-device")
public interface DeviceClient {

    @GetMapping("device/deviceProperties/mqtt/read")
    ResponseEntity<List<DeviceProperty>> getReadPropertyListByTemplateId(@RequestParam("templateId") String templateId);

    @GetMapping("device/devices/mqtt/{id}")
    ResponseEntity<Device> getDeviceById(@PathVariable("id") String id);
}
