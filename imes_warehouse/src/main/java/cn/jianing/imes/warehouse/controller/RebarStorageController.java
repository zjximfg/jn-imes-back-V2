package cn.jianing.imes.warehouse.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.common.entity.PageResult;
import cn.jianing.imes.domain.warehouse.RebarStorage;
import cn.jianing.imes.warehouse.service.RebarStorageService;
import com.github.pagehelper.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("warehouse/rebarStorage")
public class RebarStorageController extends BaseController {

    @Resource
    private RebarStorageService rebarStorageService;

    @GetMapping("page")
    public ResponseEntity<PageResult<RebarStorage>> getRebarStoragePageByWarehouseStorageId(int current, int pageSize, String warehouseStorageId, @RequestParam Map<String, Object> map) {
        Page<RebarStorage> page = rebarStorageService.getRebarStoragePageByWarehouseStorageId(current, pageSize, warehouseStorageId, map);
        PageResult<RebarStorage> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return ResponseEntity.ok(pageResult);
    }

    @GetMapping("{id}")
    public ResponseEntity<RebarStorage> getRebarStorageStorageById(@PathVariable("id") String id) {
        RebarStorage rebarStorage = rebarStorageService.getRebarStorageStorageById(id);
        return ResponseEntity.ok(rebarStorage);
    }

    @GetMapping("batchNumberList")
    public ResponseEntity<List<String>> getBatchNumberListByWarehouseStorageId(String id) {
        List<String> batchNumberList = rebarStorageService.getBatchNumberListByWarehouseStorageId(id);
        return ResponseEntity.ok(batchNumberList);
    }

    @GetMapping("batchNumberList/conditions")
    public ResponseEntity<List<String>> getBatchNumberListByConditions(int rebarCategory, String specification, int diameter, int length) {
        List<String> batchNumberList = rebarStorageService.getBatchNumberListByConditions(rebarCategory, specification, diameter, length);
        return ResponseEntity.ok(batchNumberList);
    }

    @GetMapping("query/batchNumber")
    public ResponseEntity<RebarStorage> getRebarStorageStorageByBatchNumber(@RequestParam("batchNumber") String batchNumber) {
        RebarStorage rebarStorage = rebarStorageService.getRebarStorageStorageByBatchNumber(batchNumber);
        return ResponseEntity.ok(rebarStorage);
    }
}
