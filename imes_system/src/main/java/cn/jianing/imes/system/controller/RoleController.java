package cn.jianing.imes.system.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.common.entity.PageResult;
import cn.jianing.imes.domain.system.Role;
import cn.jianing.imes.domain.system.RolePermission;
import cn.jianing.imes.system.service.RoleService;
import com.github.pagehelper.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("system/roles")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<Void> insertRole(@RequestBody Role role) {
        String companyId = currentUser.getCompanyId();
        role.setCompanyId(companyId);
        roleService.insertRole(role);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteRoleById(@PathVariable("id") String id) {
        roleService.deleteRoleById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateRoleById(@PathVariable("id") String id, @RequestBody Role role) {
        roleService.updateRoleById(id, role);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("page")
    public ResponseEntity<PageResult<Role>> getRolePage(int current, int pageSize, @RequestParam Map<String, Object> map) {
        String companyId = currentUser.getCompanyId();
        Page<Role> page = roleService.getRolePage(current, pageSize, companyId, map);
        PageResult<Role> rolePageResult = new PageResult<>(page.getTotal(), page.getResult());
        return ResponseEntity.ok(rolePageResult);
    }

    @GetMapping("{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable("id") String id) {
        Role role = roleService.getRoleById(id);
        return ResponseEntity.ok(role);
    }

    @GetMapping
    public ResponseEntity<List<Role>> getRoleList() {
        String companyId = currentUser.getCompanyId();
        List<Role> roleList = roleService.getRoleList(companyId);
        return ResponseEntity.ok(roleList);
    }

    @GetMapping("{roleId}/rolePermissions")
    public ResponseEntity<List<RolePermission>> getRolePermissionByRoleId(@PathVariable("roleId") String roleId) {
        List<RolePermission> rolePermissionList = roleService.getRolePermissionByRoleId(roleId);
        return ResponseEntity.ok(rolePermissionList);
    }

    @PutMapping("{roleId}/rolePermissions")
    public ResponseEntity<Void> updateRolePermissionByRoleId(@PathVariable("roleId") String roleId, @RequestBody List<String> permissionIds) {
        roleService.updateRolePermissionByRoleId(roleId, permissionIds);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
