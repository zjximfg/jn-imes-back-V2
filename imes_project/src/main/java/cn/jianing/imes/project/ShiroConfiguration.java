package cn.jianing.imes.project;

import cn.jianing.imes.common.shiro.realm.CommonRealm;
import cn.jianing.imes.common.shiro.session.CommonSessionManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfiguration {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    // 配置自定义的Realm
    @Bean
    public CommonRealm commonRealm() {
        return new CommonRealm();
    }

    // 配置安全管理器
    @Bean
    public SecurityManager securityManager(Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 配置shiro redisManager
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);

        // cacheManager缓存 redis实现
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        // *****1. 设置缓存管理器 ****
        securityManager.setCacheManager(redisCacheManager);

        // session 的 redis存储实例
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);

        CommonSessionManager commonSessionManager = new CommonSessionManager();
        commonSessionManager.setSessionDAO(redisSessionDAO);
        // 禁用cookies
        commonSessionManager.setSessionIdCookieEnabled(false);
        // 禁用url重新
        commonSessionManager.setSessionIdUrlRewritingEnabled(false);
        // *****2. 设置会话管理器 ****
        securityManager.setSessionManager(commonSessionManager);

        // *****3. 设置realm ****
        securityManager.setRealm(realm);
        return securityManager;
    }

    // Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        filterFactoryBean.setLoginUrl("/auth-error?code=1");  // 设置未登录页面
        filterFactoryBean.setUnauthorizedUrl("/auth-error?code=2");  // 授权失败跳转页面
        /*** 配置过滤器集合
         * key  ：访问连接
         *      支持通配符的形式
         * value：过滤器类型
         *      shiro常用过滤器
         *          anno    ：匿名访问（表明此链接所有人可以访问）
         *          authc   ：认证后访问（表明此链接需登录认证成功之后可以访问）
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/auth-error", "anon");
        filterMap.put("/**", "authc");

        filterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return filterFactoryBean;
    }

    // 配置 shiro 注解支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
