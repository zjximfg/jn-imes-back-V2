package cn.jianing.imes.system.service.impl;

import cn.jianing.imes.common.entity.CurrentUser;
import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.common.utils.SetUtils;
import cn.jianing.imes.domain.company.Department;
import cn.jianing.imes.domain.system.*;
import cn.jianing.imes.system.client.DepartmentClient;
import cn.jianing.imes.system.mapper.UserMapper;
import cn.jianing.imes.system.mapper.UserRoleMapper;
import cn.jianing.imes.system.service.PermissionService;
import cn.jianing.imes.system.service.RoleService;
import cn.jianing.imes.system.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private DepartmentClient departmentClient;
    @Resource
    private IdWorker idWorker;

    @Override
    public void insertUser(User user) {
        // username不能重复，因此需要先查询
        User userByUsername = getUserByUsername(user.getUsername());
        if (userByUsername != null) return;
        String id = String.valueOf(idWorker.nextId());
        String password = new Md5Hash("123456", user.getUsername(), 5).toString();
        user.setPassword(password);
        user.setId(id);
        user.setCreateTime(new Date());
        user.setIsDeleted(false);
        userMapper.insertSelective(user);
    }

    @Override
    public void deleteUserById(String id) {
        User user = new User();
        user.setId(id);
        user.setIsDeleted(true);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void updateUserById(String id, User user) {
        user.setId(id);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User getUserById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public Page<User> getUserPage(int current, int pageSize, String companyId, Map<String, Object> map) {
        PageHelper.startPage(current, pageSize);
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("companyId", companyId);
        criteria.andEqualTo("isDeleted", false);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("key")) {
                criteria.orLike("username", "%" + entry.getValue() + "%").orLike("mobile", "%" + entry.getValue() + "%").orLike("workNumber", "%" + entry.getValue() + "%");
            }
        }
        return (Page<User>)userMapper.selectByExample(example);
    }

    @Override
    public List<User> getUserListByDepartmentId(String departmentId) {
        User user = new User();
        user.setIsDeleted(false);
        user.setDepartmentId(departmentId);
        return userMapper.select(user);
    }

    @Override
    public List<UserRole> getUserRoleByUserId(String userId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        return userRoleMapper.select(userRole);
    }

    @Override
    public void updateUserRoleByUserId(String userId, List<String> roleIds) {
        // 原始数据
        List<UserRole> userRoleListOld = getUserRoleByUserId(userId);
        List<String> roleIdsOld = new ArrayList<>();
        for (UserRole userRole : userRoleListOld) {
            roleIdsOld.add(userRole.getRoleId());
        }
        // old 差集 new 删除
        Set<String> deleteSet = SetUtils.getDiffSet(roleIdsOld, roleIds);
        for (String roleId : deleteSet) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleMapper.delete(userRole);
        }
        // new 差集 old 增加
        Set<String> addSet = SetUtils.getDiffSet(roleIds, roleIdsOld);
        for (String roleId : addSet) {
            UserRole userRole = new UserRole();
            userRole.setId(String.valueOf(idWorker.nextId()));
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleMapper.insertSelective(userRole);
        }

    }

    @Override
    public User findUserByUsernamePassword(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        List<User> userList = userMapper.select(user);
        if (userList != null && userList.size() > 0) {
            User currentUser = userList.get(0);
            List<UserRole> userRoleList = getUserRoleByUserId(currentUser.getId());
            Set<Role> roleSet = new HashSet<>();
            for (UserRole userRole : userRoleList) {
                Role role = roleService.getRoleById(userRole.getRoleId());
                List<RolePermission> rolePermissionList = roleService.getRolePermissionByRoleId(role.getId());
                Set<Permission> permissionSet = new HashSet<>();
                for (RolePermission rolePermission : rolePermissionList) {
                    Permission permission = permissionService.getPermissionById(rolePermission.getPermissionId());
                    permissionSet.add(permission);
                }
                role.setPermissionSet(permissionSet);
                roleSet.add(role);
            }
            currentUser.setRoleSet(roleSet);
            return currentUser;
        } else {
            return null;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        User user= new User();
        user.setUsername(username);
        user.setIsDeleted(false);
        return userMapper.selectOne(user);
    }

    @Override
    public void insertUserList(List<User> userList, String companyId) {
        // 赋值
        for (User user : userList) {
            Department department = departmentClient.getDepartmentByCodeAndCompanyId(user.getDepartmentCode(), companyId).getBody();
            if (department == null) return;
            user.setCompanyId(companyId);
            user.setDepartmentId(department.getId());
            insertUser(user);
        }
    }

    @Override
    public List<User> getUserListByCompanyId(String companyId) {
        User user = new User();
        user.setCompanyId(companyId);
        user.setIsDeleted(false);
        return userMapper.select(user);
    }

    @Override
    public User getUserByCurrentUser(CurrentUser currentUser) {
        String username = currentUser.getUsername();
        return getUserByUsername(username);
    }


}
