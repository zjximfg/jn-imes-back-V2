package cn.jianing.imes.system.shiro.realm;

import cn.jianing.imes.common.entity.CurrentUser;
import cn.jianing.imes.common.shiro.realm.CommonRealm;
import cn.jianing.imes.domain.system.User;
import cn.jianing.imes.system.service.UserService;
import org.apache.shiro.authc.*;

import javax.annotation.Resource;

public class UserRealm extends CommonRealm {

    @Resource
    private UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // 1. 获取用户的用户名和密码
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)authenticationToken;
        String username = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());
        // 2. 根据用户名查询用户和密码是否存在
        User user = userService.findUserByUsernamePassword(username, password);
        if (user != null) {
            // 3. 构造安全数据并返回（安全数据： currentUser）放入服务端session中
            CurrentUser currentUser = new CurrentUser(user);
            return new SimpleAuthenticationInfo(currentUser, user.getPassword(), this.getName());
        }
        // 返回null，会抛出异常，说明用户名和密码不对
        return null;
    }
}
