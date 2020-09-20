package cn.jianing.imes.warehouse.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.common.entity.PageResult;
import cn.jianing.imes.domain.warehouse.WorkArea;
import cn.jianing.imes.warehouse.service.WorkAreaService;
import com.github.pagehelper.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("warehouse/workAreas")
public class WorkAreaController extends BaseController {

    @Resource
    private WorkAreaService workAreaService;

    @GetMapping("page")
    public ResponseEntity<PageResult<WorkArea>> getWorkAreaPageByCompanyId(int current, int pageSize, @RequestParam Map<String, Object> map) {
        Page<WorkArea> page = workAreaService.getWorkAreaPageByCompanyId(current, pageSize, currentUser.getCompanyId(), map);
        PageResult<WorkArea> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return ResponseEntity.ok(pageResult);
    }

    @GetMapping
    public ResponseEntity<List<WorkArea>> getWorkAreaListByCompanyId() {
        List<WorkArea> workAreaList = workAreaService.getWorkAreaListByCompanyId(currentUser.getCompanyId());
        return ResponseEntity.ok(workAreaList);
    }

    @GetMapping("{id}")
    public ResponseEntity<WorkArea> getWorkAreaById(@PathVariable("id") String id) {
        WorkArea workArea = workAreaService.getWorkAreaById(id);
        return ResponseEntity.ok(workArea);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteWorkAreaById(@PathVariable("id") String id) {
        workAreaService.deleteWorkAreaById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateWorkAreaById(@PathVariable("id") String id, @RequestBody WorkArea workArea) {
        workArea.setId(id);
        workAreaService.updateWorkAreaById(workArea);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Void> insertWorkArea(@RequestBody WorkArea workArea) {
        workArea.setCompanyId(currentUser.getCompanyId());
        workAreaService.insertWorkArea(workArea);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
