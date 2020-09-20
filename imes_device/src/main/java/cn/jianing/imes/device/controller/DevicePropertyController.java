package cn.jianing.imes.device.controller;

import cn.jianing.imes.common.entity.PageResult;
import cn.jianing.imes.device.service.DevicePropertyService;
import cn.jianing.imes.domain.device.DeviceProperty;
import com.github.pagehelper.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("device/deviceProperties")
public class DevicePropertyController {

    @Resource
    private DevicePropertyService devicePropertyService;

    @GetMapping("page")
    public ResponseEntity<PageResult<DeviceProperty>> getDevicePropertyPageByTemplateId(int current, int pageSize, String templateId, Integer category,@RequestParam Map<String, Object> map) {
        Page<DeviceProperty> page = devicePropertyService.getDevicePropertyPageByTemplateId(current, pageSize, templateId, category, map);
        PageResult<DeviceProperty> propertyPageResult = new PageResult<>(page.getTotal(), page.getResult());
        return ResponseEntity.ok(propertyPageResult);
    }

    @GetMapping("{id}")
    public ResponseEntity<DeviceProperty> getDevicePropertyById(@PathVariable("id") String id) {
        DeviceProperty deviceProperty= devicePropertyService.getDevicePropertyById(id);
        return ResponseEntity.ok(deviceProperty);
    }

    @PostMapping
    public ResponseEntity<Void> insertDeviceProperty(@RequestBody DeviceProperty deviceProperty) {
        devicePropertyService.insertDeviceProperty(deviceProperty);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateDevicePropertyById(@PathVariable("id") String id, @RequestBody DeviceProperty deviceProperty) {
        deviceProperty.setId(id);
        devicePropertyService.updateDevicePropertyById(deviceProperty);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDevicePropertyById(@PathVariable("id") String id) {
        devicePropertyService.deleteDevicePropertyById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("mqtt/read")
    public ResponseEntity<List<DeviceProperty>> getReadPropertyListByTemplateId(@RequestParam("templateId") String templateId) {
        List<DeviceProperty> devicePropertyList = devicePropertyService.getReadPropertyListByTemplateId(templateId);
        return ResponseEntity.ok(devicePropertyList);
    }

    @GetMapping("read")
    public ResponseEntity<List<DeviceProperty>> getReadPropertyListByDeviceId(@RequestParam("deviceId") String deviceId) {
        List<DeviceProperty> devicePropertyList = devicePropertyService.getReadPropertyListByDeviceId(deviceId);
        return ResponseEntity.ok(devicePropertyList);
    }

}
