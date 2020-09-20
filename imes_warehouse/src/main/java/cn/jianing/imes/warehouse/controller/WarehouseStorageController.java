package cn.jianing.imes.warehouse.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.common.entity.PageResult;
import cn.jianing.imes.domain.warehouse.WarehouseEntry;
import cn.jianing.imes.domain.warehouse.WarehouseStorage;
import cn.jianing.imes.warehouse.service.WarehouseStorageService;
import com.github.pagehelper.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("warehouse/warehouseStorage")
public class WarehouseStorageController extends BaseController {

    @Resource
    private WarehouseStorageService warehouseStorageService;

    @GetMapping("page")
    public ResponseEntity<PageResult<WarehouseStorage>> getWarehouseStoragePageByCompanyId(int current, int pageSize, @RequestParam Map<String, Object> map) {
        Page<WarehouseStorage> page = warehouseStorageService.getWarehouseStoragePageByCompanyId(current, pageSize, currentUser.getCompanyId(), map);
        PageResult<WarehouseStorage> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return ResponseEntity.ok(pageResult);
    }

    @GetMapping("{id}")
    public ResponseEntity<WarehouseStorage> getWarehouseStorageById(@PathVariable("id") String id) {
        WarehouseStorage warehouseStorage = warehouseStorageService.getWarehouseStorageById(id);
        return ResponseEntity.ok(warehouseStorage);
    }
}
