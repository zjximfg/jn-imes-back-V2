package cn.jianing.imes.warehouse.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.domain.warehouse.OutWarehouseRebar;
import cn.jianing.imes.domain.warehouse.WarehouseStorage;
import cn.jianing.imes.warehouse.service.OutWarehouseRebarService;
import cn.jianing.imes.warehouse.service.WarehouseStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("warehouse/outWarehouseRebar")
public class OutWarehouseRebarController extends BaseController {

    @Resource
    private OutWarehouseRebarService outWarehouseRebarService;
    @Resource
    private WarehouseStorageService warehouseStorageService;


    @GetMapping("list")
    public ResponseEntity<List<OutWarehouseRebar>> getOutWarehouseRebarByOutWarehouseId(@RequestParam("outWarehouseId") String outWarehouseId) {
        List<OutWarehouseRebar> outWarehouseRebarList = outWarehouseRebarService.getOutWarehouseRebarByOutWarehouseId(outWarehouseId);
        return ResponseEntity.ok(outWarehouseRebarList);
    }

    @GetMapping("{id}")
    public ResponseEntity<OutWarehouseRebar> getOutWarehouseRebarById(@PathVariable("id") String id) {
        OutWarehouseRebar outWarehouseRebar = outWarehouseRebarService.getOutWarehouseRebarById(id);
        return ResponseEntity.ok(outWarehouseRebar);
    }

    @GetMapping("specification/list")
    public ResponseEntity<List<String>> querySpecificationListInWarehouseStorage(@RequestParam Map<String, Object> map) {
        List<String> specificationList = outWarehouseRebarService.querySpecificationListInWarehouseStorage(map, currentUser.getCompanyId());
        return ResponseEntity.ok(specificationList);
    }

    @GetMapping("diameter/list")
    public ResponseEntity<List<Integer>> queryDiameterListInWarehouseStorage(@RequestParam Map<String, Object> map) {
        List<Integer> diameterList = outWarehouseRebarService.queryDiameterListInWarehouseStorage(map, currentUser.getCompanyId());
        return ResponseEntity.ok(diameterList);
    }

    @GetMapping("length/list")
    public ResponseEntity<List<Integer>> queryLengthListInWarehouseStorage(@RequestParam Map<String, Object> map) {
        List<Integer> lengthList = outWarehouseRebarService.queryLengthListInWarehouseStorage(map, currentUser.getCompanyId());
        return ResponseEntity.ok(lengthList);
    }

    @PostMapping()
    public ResponseEntity<Void> insertOutWarehouseRebar(@RequestBody OutWarehouseRebar outWarehouseRebar) {
        // 查询warehouseStorage
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("companyId", currentUser.getCompanyId());
        conditions.put("rebarCategory", outWarehouseRebar.getRebarCategory());
        conditions.put("specification", outWarehouseRebar.getSpecification());
        conditions.put("diameter", outWarehouseRebar.getDiameter());
        conditions.put("length", outWarehouseRebar.getLength());
        List<WarehouseStorage> warehouseStorageList = warehouseStorageService.queryWarehouseStorageByCondition(conditions);
        if (warehouseStorageList != null && warehouseStorageList.size() == 1) {
            outWarehouseRebar.setWarehouseStorageId(warehouseStorageList.get(0).getId());
            outWarehouseRebarService.insetOutWarehouseRebar(outWarehouseRebar);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
