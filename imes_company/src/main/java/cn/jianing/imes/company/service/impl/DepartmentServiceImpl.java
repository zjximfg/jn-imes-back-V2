package cn.jianing.imes.company.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.company.mapper.DepartmentMapper;
import cn.jianing.imes.company.service.DepartmentService;
import cn.jianing.imes.domain.company.Department;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private IdWorker idWorker;

    @Override
    public void insertDepartment(Department department) {
        String id = String.valueOf(idWorker.nextId());
        department.setId(id);
        department.setIsDeleted(false);
        department.setCreateTime(new Date());
        departmentMapper.insertSelective(department);
    }

    @Override
    public void deleteDepartmentById(String id) {
        Department department = new Department();
        department.setId(id);
        department.setIsDeleted(true);
        departmentMapper.updateByPrimaryKeySelective(department);
    }

    @Override
    public void updateDepartmentById(String id, Department department) {
        department.setId(id);
        departmentMapper.updateByPrimaryKeySelective(department);
    }

    @Override
    public Department getDepartmentById(String id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Department> getDepartmentListByCompanyId(String companyId) {
        Department department = new Department();
        department.setIsDeleted(false);
        department.setCompanyId(companyId);
        return departmentMapper.select(department);
    }

    @Override
    public Department getDepartmentByCodeAndCompanyId(String departmentCode, String companyId) {
        Department department = new Department();
        department.setIsDeleted(false);
        department.setCode(departmentCode);
        department.setCompanyId(companyId);
        return departmentMapper.selectOne(department);
    }
}
