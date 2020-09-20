package cn.jianing.imes.system.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.common.utils.SetUtils;
import cn.jianing.imes.domain.system.Role;
import cn.jianing.imes.domain.system.RolePermission;
import cn.jianing.imes.domain.system.UserRole;
import cn.jianing.imes.system.mapper.RoleMapper;
import cn.jianing.imes.system.mapper.RolePermissionMapper;
import cn.jianing.imes.system.service.RoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;
    @Resource
    private IdWorker idWorker;

    @Override
    public void insertRole(Role role) {
        String id = String.valueOf(idWorker.nextId());
        role.setId(id);
        role.setIsDeleted(false);
        roleMapper.insertSelective(role);
    }

    @Override
    public void deleteRoleById(String id) {
        Role role = new Role();
        role.setIsDeleted(false);
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void updateRoleById(String id, Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public Page<Role> getRolePage(int current, int pageSize, String companyId, Map<String, Object> map) {
        PageHelper.startPage(current, pageSize);
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("companyId", companyId);
        criteria.andEqualTo("isDeleted", false);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("key")) {
                criteria.orLike("username", "%" + entry.getValue() + "%").orLike("mobile", "%" + entry.getValue() + "%").orLike("workNumber", "%" + entry.getValue() + "%");
            }
        }
        return (Page<Role>)roleMapper.selectByExample(example);
    }

    @Override
    public Role getRoleById(String id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Role> getRoleList(String companyId) {
        Role role = new Role();
        role.setIsDeleted(false);
        role.setCompanyId(companyId);
        return roleMapper.select(role);
    }

    @Override
    public List<RolePermission> getRolePermissionByRoleId(String roleId) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(roleId);
        return rolePermissionMapper.select(rolePermission);
    }

    @Override
    public void updateRolePermissionByRoleId(String roleId, List<String> permissionIds) {
        // 原始数据
        List<RolePermission> rolePermissionList = getRolePermissionByRoleId(roleId);
        List<String> permissionIdsOld = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissionList) {
            permissionIdsOld.add(rolePermission.getPermissionId());
        }
        // old 差集 new 删除
        Set<String> deleteSet = SetUtils.getDiffSet(permissionIdsOld, permissionIds);
        for (String permissionId : deleteSet) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionMapper.delete(rolePermission);
        }
        // new 差集 old 增加
        Set<String> addSet = SetUtils.getDiffSet(permissionIds, permissionIdsOld);
        for (String permissionId : addSet) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setId(String.valueOf(idWorker.nextId()));
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionMapper.insertSelective(rolePermission);
        }
    }
}
