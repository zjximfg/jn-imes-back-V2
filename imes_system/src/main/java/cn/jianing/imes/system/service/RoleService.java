package cn.jianing.imes.system.service;

import cn.jianing.imes.domain.system.Role;
import cn.jianing.imes.domain.system.RolePermission;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RoleService {
    void insertRole(Role role);

    void deleteRoleById(String id);

    void updateRoleById(String id, Role role);

    Page<Role> getRolePage(int current, int pageSize, String companyId, Map<String, Object> map);

    Role getRoleById(String id);

    List<Role> getRoleList(String companyId);

    List<RolePermission> getRolePermissionByRoleId(String roleId);

    void updateRolePermissionByRoleId(String roleId, List<String> permissionIds);
}
