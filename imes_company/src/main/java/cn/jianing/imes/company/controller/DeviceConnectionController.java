package cn.jianing.imes.company.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.company.service.DeviceConnectionService;
import cn.jianing.imes.domain.company.DeviceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("company/deviceConnections")
public class DeviceConnectionController extends BaseController {

    @Resource
    private DeviceConnectionService deviceConnectionService;


    @PutMapping("{companyId}")
    public ResponseEntity<Void> updateDeviceConnectionByCompanyId(@PathVariable("companyId") String companyId, @RequestBody DeviceConnection deviceConnection) {
        deviceConnection.setCompanyId(companyId);
        deviceConnectionService.updateDeviceConnectionByCompanyId(deviceConnection);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{companyId}")
    public ResponseEntity<DeviceConnection> getDeviceConnectionByCompanyId(@PathVariable("companyId") String companyId) {
        DeviceConnection deviceConnection = deviceConnectionService.getDeviceConnectionByCompanyId(companyId);
        return ResponseEntity.ok(deviceConnection);
    }

    @GetMapping("currentCompany")
    public ResponseEntity<DeviceConnection> getCurrentCompanyDeviceConnection() {
        DeviceConnection deviceConnection = deviceConnectionService.getDeviceConnectionByCompanyId(currentUser.getCompanyId());
        return ResponseEntity.ok(deviceConnection);
    }


}
