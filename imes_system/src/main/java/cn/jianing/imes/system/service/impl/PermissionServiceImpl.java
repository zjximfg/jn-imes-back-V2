package cn.jianing.imes.system.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.domain.system.*;
import cn.jianing.imes.system.mapper.PermissionApiMapper;
import cn.jianing.imes.system.mapper.PermissionMapper;
import cn.jianing.imes.system.mapper.PermissionMenuMapper;
import cn.jianing.imes.system.mapper.PermissionPointMapper;
import cn.jianing.imes.system.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private PermissionMenuMapper permissionMenuMapper;

    @Resource
    private PermissionPointMapper permissionPointMapper;

    @Resource
    private PermissionApiMapper permissionApiMapper;

    @Resource
    private IdWorker idWorker;

    @Override
    public void insertPermission(Permission permission) {
        String id = String.valueOf(idWorker.nextId());
        permission.setId(id);
        if (permission.getSystemVisible()==null) permission.setSystemVisible(false);
        permissionMapper.insertSelective(permission);
        switch (permission.getType()) {
            case PermissionType.MENU:
                permission.getPermissionMenu().setId(id);
                permissionMenuMapper.insertSelective(permission.getPermissionMenu());
                break;
            case PermissionType.POINT:
                permission.getPermissionPoint().setId(id);
                permissionPointMapper.insertSelective(permission.getPermissionPoint());
                break;
            case PermissionType.API:
                permission.getPermissionApi().setId(id);
                permissionApiMapper.insertSelective(permission.getPermissionApi());
                break;
        }
    }

    @Override
    public void deletePermissionById(String id) {
        permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updatePermissionById(String id, Permission permission) {
        permission.setId(id);
        permissionMapper.updateByPrimaryKeySelective(permission);
        switch (permission.getType()) {
            case PermissionType.MENU:
                permission.getPermissionMenu().setId(id);
                permissionMenuMapper.updateByPrimaryKeySelective(permission.getPermissionMenu());
                break;
            case PermissionType.POINT:
                permission.getPermissionPoint().setId(id);
                permissionPointMapper.updateByPrimaryKeySelective(permission.getPermissionPoint());
                break;
            case PermissionType.API:
                permission.getPermissionApi().setId(id);
                permissionApiMapper.updateByPrimaryKeySelective(permission.getPermissionApi());
                break;
        }
    }

    @Override
    public Permission getPermissionById(String id) {
        Permission permission = permissionMapper.selectByPrimaryKey(id);
        switch (permission.getType()) {
            case PermissionType.MENU:
                PermissionMenu permissionMenu = permissionMenuMapper.selectByPrimaryKey(id);
                permission.setPermissionMenu(permissionMenu);
                break;
            case PermissionType.POINT:
                PermissionPoint permissionPoint = permissionPointMapper.selectByPrimaryKey(id);
                permission.setPermissionPoint(permissionPoint);
                break;
            case PermissionType.API:
                PermissionApi permissionApi = permissionApiMapper.selectByPrimaryKey(id);
                permission.setPermissionApi(permissionApi);
                break;
        }
        return permission;
    }

    @Override
    public List<Permission> getPermissionList() {
        return permissionMapper.selectAll();
    }
}
