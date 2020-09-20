package cn.jianing.imes.system.service;

import cn.jianing.imes.common.entity.CurrentUser;
import cn.jianing.imes.domain.system.User;
import cn.jianing.imes.domain.system.UserRole;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    void insertUser(User user);

    void deleteUserById(String id);

    void updateUserById(String id, User user);

    User getUserById(String id);

    Page<User> getUserPage(int current, int pageSize, String companyId, Map<String, Object> map);

    List<User> getUserListByDepartmentId(String departmentId);

    List<UserRole> getUserRoleByUserId(String userId);

    void updateUserRoleByUserId(String userId, List<String> roleIds);

    User findUserByUsernamePassword(String username, String password);

    User getUserByUsername(String username);

    void insertUserList(List<User> userList, String companyId);

    List<User> getUserListByCompanyId(String companyId);

    User getUserByCurrentUser(CurrentUser currentUser);
}
