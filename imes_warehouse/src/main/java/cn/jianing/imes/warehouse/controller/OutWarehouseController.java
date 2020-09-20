package cn.jianing.imes.warehouse.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.common.entity.PageResult;
import cn.jianing.imes.domain.warehouse.OutWarehouse;
import cn.jianing.imes.warehouse.service.OutWarehouseService;
import com.github.pagehelper.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("warehouse/outWarehouse")
public class OutWarehouseController extends BaseController {

    @Resource
    private OutWarehouseService outWarehouseService;

    @PostMapping("cascade")
    public ResponseEntity<Void> insertOutWarehouseCascade(@RequestBody OutWarehouse outWarehouse) {
        outWarehouse.setCompanyId(currentUser.getCompanyId());
        outWarehouseService.insertOutWarehouseCascade(outWarehouse);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("page")
    public ResponseEntity<PageResult<OutWarehouse>> getOutWarehousePageByCompanyId(int current, int pageSize, @RequestParam Map<String, Object> map) {
        Page<OutWarehouse> page = outWarehouseService.getOutWarehousePageByCompanyId(current, pageSize, currentUser.getCompanyId(), map);
        PageResult<OutWarehouse> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return ResponseEntity.ok(pageResult);
    }

    @GetMapping("{id}")
    public ResponseEntity<OutWarehouse> getOutWarehouseById(@PathVariable("id") String id) {
        OutWarehouse outWarehouse = outWarehouseService.getOutWarehouseById(id);
        return ResponseEntity.ok(outWarehouse);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateOutWarehouse(@PathVariable("id") String id, @RequestBody OutWarehouse outWarehouse) {
        outWarehouse.setId(id);
        outWarehouseService.updateOutWarehouse(outWarehouse);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Void> insertOutWarehouse(@RequestBody OutWarehouse outWarehouse) {
        outWarehouse.setCompanyId(currentUser.getCompanyId());
        outWarehouseService.insertOutWarehouse(outWarehouse);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
