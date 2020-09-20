package cn.jianing.imes.system.service;

import cn.jianing.imes.domain.system.Permission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PermissionService {
    void insertPermission(Permission permission);

    void deletePermissionById(String id);

    void updatePermissionById(String id, Permission permission);

    Permission getPermissionById(String id);

    List<Permission> getPermissionList();
}
