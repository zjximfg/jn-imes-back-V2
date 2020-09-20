package cn.jianing.imes.company.service;

import cn.jianing.imes.domain.company.Department;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepartmentService {
    void insertDepartment(Department department);

    void deleteDepartmentById(String id);

    void updateDepartmentById(String id, Department department);

    Department getDepartmentById(String id);

    List<Department> getDepartmentListByCompanyId(String companyId);

    Department getDepartmentByCodeAndCompanyId(String departmentCode, String companyId);
}
