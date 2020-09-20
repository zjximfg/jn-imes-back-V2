package cn.jianing.imes.warehouse.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.common.entity.PageResult;
import cn.jianing.imes.domain.warehouse.WarehouseEntry;
import cn.jianing.imes.warehouse.service.WarehouseEntryService;
import com.github.pagehelper.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("warehouse/warehouseEntries")
public class WarehouseEntryController extends BaseController {

    @Resource
    private WarehouseEntryService warehouseEntryService;

    @GetMapping("page")
    public ResponseEntity<PageResult<WarehouseEntry>> getWarehouseEntryPageByCompanyId(int current, int pageSize, @RequestParam Map<String, Object> map) {
        System.out.println(map);
        Page<WarehouseEntry> page = warehouseEntryService.getWarehouseEntryPageByCompanyId(current, pageSize, currentUser.getCompanyId(), map);
        List<WarehouseEntry> result = page.getResult();
        List<WarehouseEntry> warehouseEntryList = warehouseEntryService.getListWithRebarEntryInfo(result);
        PageResult<WarehouseEntry> pageResult = new PageResult<>(page.getTotal(), warehouseEntryList);
        return ResponseEntity.ok(pageResult);
    }

    @GetMapping("{id}")
    public ResponseEntity<WarehouseEntry> getWarehouseEntryById(@PathVariable("id") String id) {
        WarehouseEntry warehouseEntry = warehouseEntryService.getWarehouseEntryById(id);
        return ResponseEntity.ok(warehouseEntry);
    }


    // 需要联动保存附件表
    @PostMapping
    public ResponseEntity<Void> insertWarehouseEntry(@RequestBody WarehouseEntry warehouseEntry) {
        warehouseEntry.setCompanyId(currentUser.getCompanyId());
        warehouseEntryService.insertWarehouseEntry(warehouseEntry);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 需要联动更新附件表
    @PutMapping("{id}")
    public ResponseEntity<Void> updateWarehouseEntry(@PathVariable("id") String id, @RequestBody WarehouseEntry warehouseEntry) {
        warehouseEntry.setId(id);
        warehouseEntryService.updateWarehouseEntry(warehouseEntry);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteWarehouseEntry(@PathVariable("id") String id) {
        warehouseEntryService.deleteWarehouseEntry(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
