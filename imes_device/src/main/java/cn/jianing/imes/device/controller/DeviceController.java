package cn.jianing.imes.device.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.common.entity.PageResult;
import cn.jianing.imes.common.utils.InfluxUtils;
import cn.jianing.imes.device.service.DeviceService;
import cn.jianing.imes.domain.device.Device;
import com.github.pagehelper.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("device/devices")
public class DeviceController extends BaseController {

    @Resource
    private DeviceService deviceService;

    @GetMapping
    public ResponseEntity<List<Device>> getDeviceListByCompanyId() {
        List<Device> deviceList = deviceService.getDeviceListByCompanyId(currentUser.getCompanyId());
        return ResponseEntity.ok(deviceList);
    }

    @GetMapping("{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable("id") String id) {
        Device device = deviceService.getDeviceById(id);
        return ResponseEntity.ok(device);
    }

    @GetMapping("mqtt/{id}")
    public ResponseEntity<Device> getMqttDeviceById(@PathVariable("id") String id) {
        Device device = deviceService.getDeviceById(id);
        return ResponseEntity.ok(device);
    }

    @GetMapping("page")
    public ResponseEntity<PageResult<Device>> getDevicePageByCompanyId(int current, int pageSize, @RequestParam Map<String, Object> map) {
        Page<Device> devicePage = deviceService.getDevicePageByCompanyId(current, pageSize, currentUser.getCompanyId(), map);
        PageResult<Device> pageResult = new PageResult<>(devicePage.getTotal(), devicePage.getResult());
        return ResponseEntity.ok(pageResult);
    }

    @PostMapping
    public ResponseEntity<Void> insertDevice(@RequestBody Device device) {
        device.setCompanyId(currentUser.getCompanyId());
        deviceService.insertDevice(device);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateDeviceById(@PathVariable("id") String id, @RequestBody Device device) {
        device.setId(id);
        deviceService.updateDeviceById(device);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDeviceById(@PathVariable("id") String id) {
        deviceService.deleteDeviceById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    /**
     * 用于测试读取influx db的数据
     * 采用 http协议
     */
    @GetMapping("influx")
    public ResponseEntity<String> getInfluxDBCurrentValue() {
        InfluxUtils influxUtils = new InfluxUtils("http://182.92.194.48:8086", "telegraf", "autogen", "mqtt_consumer");
        String value = influxUtils.getCurrentValue("jn-imes/mqtt_id/temperature/real", "*");
        System.out.println(value);
        return ResponseEntity.ok(value);
    }

    @GetMapping("influx/map")
    public ResponseEntity<Map<String, String>> getInfluxDBCurrentMap() {
        InfluxUtils influxUtils = new InfluxUtils("http://182.92.194.48:8086", "telegraf", "autogen", "mqtt_consumer");
        Map<String, String> currentMap = influxUtils.getCurrentMap("jn-imes/mqtt_id/temperature/real", "*");
        System.out.println(currentMap);
        return ResponseEntity.ok(currentMap);
    }


}
