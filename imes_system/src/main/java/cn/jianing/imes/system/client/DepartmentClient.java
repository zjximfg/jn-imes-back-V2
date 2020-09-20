package cn.jianing.imes.system.client;

import cn.jianing.imes.domain.company.Department;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "imes-company")   // 微服务名称
public interface DepartmentClient {

    // Controller 一致
    @GetMapping("companies/departments/{id}")
    ResponseEntity<Department> getDepartmentById(@PathVariable("id") String id);

    @GetMapping("companies/departments/department")
    ResponseEntity<Department> getDepartmentByCodeAndCompanyId(@RequestParam("departmentCode") String departmentCode, @RequestParam("companyId") String companyId);

}
