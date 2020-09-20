package cn.jianing.imes.system.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.domain.system.Permission;
import cn.jianing.imes.system.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("system/permissions")
public class PermissionController extends BaseController {

    @Resource
    private PermissionService permissionService;

    @PostMapping
    public ResponseEntity<Void> insertPermission(@RequestBody Permission permission) {
        permissionService.insertPermission(permission);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePermissionById(@PathVariable("id") String id) {
        permissionService.deletePermissionById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updatePermissionById(@PathVariable("id") String id, @RequestBody Permission permission) {
        permissionService.updatePermissionById(id, permission);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable("id") String id) {
        Permission permission = permissionService.getPermissionById(id);
        return ResponseEntity.ok(permission);
    }

    @GetMapping
    public ResponseEntity<List<Permission>> getPermissionList() {
        List<Permission> permissionList = permissionService.getPermissionList();
        return ResponseEntity.ok(permissionList);
    }
}
