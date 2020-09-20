package cn.jianing.imes.warehouse.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.common.entity.PageResult;
import cn.jianing.imes.domain.warehouse.MaterialSupplier;
import cn.jianing.imes.warehouse.service.MaterialSupplierService;
import com.github.pagehelper.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("warehouse/materialSuppliers")
public class MaterialSupplierController extends BaseController {
    @Resource
    private MaterialSupplierService materialSupplierService;

    @GetMapping("page")
    public ResponseEntity<PageResult<MaterialSupplier>> getMaterialSupplierPageByCompanyId(int current, int pageSize, @RequestParam Map<String, Object> map) {
        Page<MaterialSupplier> page = materialSupplierService.getMaterialSupplierPageByCompanyId(current, pageSize, currentUser.getCompanyId(), map);
        PageResult<MaterialSupplier> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return ResponseEntity.ok(pageResult);
    }

    @GetMapping
    public ResponseEntity<List<MaterialSupplier>> getMaterialSupplierListByCompanyId() {
        List<MaterialSupplier> materialSupplierList = materialSupplierService.getMaterialSupplierListByCompanyId(currentUser.getCompanyId());
        return ResponseEntity.ok(materialSupplierList);
    }

    @GetMapping("{id}")
    public ResponseEntity<MaterialSupplier> getMaterialSupplierById(@PathVariable("id") String id) {
        MaterialSupplier materialSupplier = materialSupplierService.getMaterialSupplierById(id);
        return ResponseEntity.ok(materialSupplier);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMaterialSupplierById(@PathVariable("id") String id) {
        materialSupplierService.deleteMaterialSupplierById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateMaterialSupplierById(@PathVariable("id") String id, @RequestBody MaterialSupplier materialSupplier) {
        materialSupplier.setId(id);
        materialSupplierService.updateMaterialSupplierById(materialSupplier);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Void> insertMaterialSupplier(@RequestBody MaterialSupplier materialSupplier) {
        materialSupplier.setCompanyId(currentUser.getCompanyId());
        materialSupplierService.insertMaterialSupplier(materialSupplier);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
