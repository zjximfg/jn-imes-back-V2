package cn.jianing.imes.warehouse.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.domain.warehouse.OutWarehouseRebar;
import cn.jianing.imes.warehouse.service.OutWarehouseRebarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("warehouse/outWarehouseRebar")
public class OutWarehouseRebarController extends BaseController {

    @Resource
    private OutWarehouseRebarService outWarehouseRebarService;


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
    public ResponseEntity<List<String>> querySpecificationListInWarehouseStorages(@RequestParam Map<String, Object> map) {
        List<String> specificationList = outWarehouseRebarService.querySpecificationListInWarehouseStorages(map, currentUser.getCompanyId());
        return ResponseEntity.ok(specificationList);
    }

    @GetMapping("diameter/list")
    public ResponseEntity<List<Integer>> queryDiameterListInWarehouseStorages(@RequestParam Map<String, Object> map) {
        List<Integer> diameterList = outWarehouseRebarService.queryDiameterListInWarehouseStorages(map, currentUser.getCompanyId());
        return ResponseEntity.ok(diameterList);
    }

    @GetMapping("length/list")
    public ResponseEntity<List<Integer>> queryLengthListInWarehouseStorages(@RequestParam Map<String, Object> map) {
        List<Integer> lengthList = outWarehouseRebarService.queryLengthListInWarehouseStorages(map, currentUser.getCompanyId());
        return ResponseEntity.ok(lengthList);
    }
}
