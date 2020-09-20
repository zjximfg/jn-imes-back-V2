package cn.jianing.imes.device.controller;

import cn.jianing.imes.device.service.DeviceDailyService;
import cn.jianing.imes.domain.device.DeviceDaily;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("device/deviceDailies")
public class DeviceDailyController {

    @Resource
    private DeviceDailyService deviceDailyService;

    @GetMapping
    public ResponseEntity<List<DeviceDaily>> getDeviceDailyList(@RequestParam("deviceId") String  deviceId,
                                                                @RequestParam("year") int year,
                                                                @RequestParam("month") int month) {
        List<DeviceDaily> deviceDailyList = deviceDailyService.getDeviceDailyList(deviceId, year, month);
        return ResponseEntity.ok(deviceDailyList);
    }

    @GetMapping("one")
    public ResponseEntity<DeviceDaily> getDeviceDailyByDeviceIdAndDate(@RequestParam("deviceId") String  deviceId,
                                                                       @RequestParam("date") String date) throws ParseException {
        System.out.println(date);
        DeviceDaily deviceDaily = deviceDailyService.getDeviceDailyByDeviceIdAndDate(deviceId, date);
        return ResponseEntity.ok(deviceDaily);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateDeviceDailyById(@PathVariable("id") String id, @RequestBody DeviceDaily deviceDaily) {
        System.out.println(deviceDaily.getDate());
        deviceDaily.setId(id);
        deviceDailyService.updateDeviceDailyById(deviceDaily);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Void> insertDeviceDaily(@RequestBody DeviceDaily deviceDaily) {
        System.out.println(deviceDaily.getDate());
        deviceDailyService.insertDeviceDaily(deviceDaily);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
