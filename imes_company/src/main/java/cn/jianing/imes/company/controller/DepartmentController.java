package cn.jianing.imes.company.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.company.service.DepartmentService;
import cn.jianing.imes.domain.company.Department;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("company/departments")
public class DepartmentController extends BaseController {

    @Resource
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<Void> insertDepartment(@RequestBody Department department) {
        String companyId = currentUser.getCompanyId();
        department.setCompanyId(companyId);
        departmentService.insertDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDepartmentById(@PathVariable("id") String id) {
        departmentService.deleteDepartmentById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateDepartmentById(@PathVariable("id") String id, @RequestBody Department department) {
        departmentService.updateDepartmentById(id, department);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") String id) {
        Department department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }

    @GetMapping()
    public ResponseEntity<List<Department>> getDepartmentList() {
        String companyId = currentUser.getCompanyId();
        List<Department> departmentList = departmentService.getDepartmentListByCompanyId(companyId);
        return ResponseEntity.ok(departmentList);
    }

    @GetMapping("department")
    public ResponseEntity<Department> getDepartmentByCodeAndCompanyId(@RequestParam("departmentCode") String departmentCode, @RequestParam("companyId") String companyId) {
        Department department = departmentService.getDepartmentByCodeAndCompanyId(departmentCode, companyId);
        return ResponseEntity.ok(department);
    }
}
