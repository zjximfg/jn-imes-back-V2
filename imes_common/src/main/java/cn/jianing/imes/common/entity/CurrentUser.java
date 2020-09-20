package cn.jianing.imes.common.entity;

import cn.jianing.imes.domain.system.Permission;
import cn.jianing.imes.domain.system.PermissionType;
import cn.jianing.imes.domain.system.Role;
import cn.jianing.imes.domain.system.User;
import lombok.Data;
import org.crazycake.shiro.AuthCachePrincipal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class CurrentUser implements Serializable, AuthCachePrincipal {

    private String username;
    private String mobile;
    private String avatar;
    private String companyId;
    private Map<String, Set<String>> permissionCodeMap;

    public CurrentUser(User user) {
        this.username = user.getUsername();
        this.mobile = user.getPassword();
        this.companyId = user.getCompanyId();
        this.avatar = user.getAvatar();
        Set<String> menus = new HashSet<>();
        Set<String> points = new HashSet<>();
        Set<String> apis = new HashSet<>();
        Map<String, Set<String>> permissionCodeMap = new HashMap<>();
        System.out.println(user);
        for (Role role : user.getRoleSet()) {
            for (Permission permission : role.getPermissionSet()) {
                String code = permission.getCode();
                switch (permission.getType()) {
                    case PermissionType.MENU:
                        menus.add(code);
                        break;
                    case PermissionType.POINT:
                        points.add(code);
                        break;
                    case PermissionType.API:
                        apis.add(code);
                }
            }
        }
        permissionCodeMap.put("menus", menus);
        permissionCodeMap.put("points", points);
        permissionCodeMap.put("apis", apis);
        this.permissionCodeMap = permissionCodeMap;
    }

    @Override
    public String getAuthCacheKey() {
        return null;
    }
}
