package cn.jianing.imes.common.shiro.realm;

import cn.jianing.imes.common.entity.CurrentUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

public class CommonRealm extends AuthorizingRealm {


    // 指定realm名称
    @Override
    public void setName(String name) {
        super.setName("commonRealm");
    }

    /**
     * 授权方法
     * @param principalCollection 保存安全数据的session
     * @return 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 1.获取安全数据
        CurrentUser currentUser = (CurrentUser) principalCollection.getPrimaryPrincipal();
        // 2.获取权限信息
        Set<String> apis = currentUser.getPermissionCodeMap().get("apis");
        // 3.构造权限数据，返回
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(apis);
        return simpleAuthorizationInfo;
    }


    /**
     * 认证方法， 见system模块 用于登录
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
