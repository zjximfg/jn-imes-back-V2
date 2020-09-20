package cn.jianing.imes.device.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.common.entity.PageResult;
import cn.jianing.imes.device.service.DeviceSupplierService;
import cn.jianing.imes.domain.device.DeviceSupplier;
import com.github.pagehelper.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("device/deviceSuppliers")
public class DeviceSupplierController extends BaseController {

    @Resource
    private DeviceSupplierService deviceSupplierService;

    @GetMapping
    public ResponseEntity<List<DeviceSupplier>> getDeviceSupplierListByCompanyId() {
        List<DeviceSupplier> deviceSupplierList = deviceSupplierService.getDeviceSupplierListByCompanyId(currentUser.getCompanyId());
        return ResponseEntity.ok(deviceSupplierList);
    }

    @GetMapping("{id}")
    public ResponseEntity<DeviceSupplier> getDeviceSupplierById(@PathVariable("id") String id) {
        DeviceSupplier deviceSupplier = deviceSupplierService.getDeviceSupplierById(id);
        return ResponseEntity.ok(deviceSupplier);
    }

    @GetMapping("page")
    public ResponseEntity<PageResult<DeviceSupplier>> getDeviceSupplierPageByCompanyId(int current, int pageSize, @RequestParam Map<String, Object> map) {
        Page<DeviceSupplier> deviceSupplierPage = deviceSupplierService.getDeviceSupplierPageByCompanyId(current, pageSize, currentUser.getCompanyId(), map);
        PageResult<DeviceSupplier> pageResult = new PageResult<>(deviceSupplierPage.getTotal(), deviceSupplierPage.getResult());
        return ResponseEntity.ok(pageResult);
    }

    @PostMapping
    public ResponseEntity<Void> insertDeviceSupplier(@RequestBody DeviceSupplier deviceSupplier) {
        deviceSupplier.setCompanyId(currentUser.getCompanyId());
        deviceSupplierService.insertDeviceSupplier(deviceSupplier);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateDeviceSupplierById(@PathVariable("id") String id, @RequestBody DeviceSupplier deviceSupplier) {
        deviceSupplier.setId(id);
        deviceSupplierService.updateDeviceSupplierById(deviceSupplier);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDeviceSupplierById(@PathVariable("id") String id) {
        deviceSupplierService.deleteDeviceSupplierById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
