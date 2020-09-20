package cn.jianing.imes.common.controller;

import cn.jianing.imes.common.entity.CurrentUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {

    HttpServletRequest request;

    protected HttpServletResponse response;

    protected CurrentUser currentUser;

    @ModelAttribute // controller方法前执行
    public void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response) {

        this.request = request;
        this.response = response;

        // 获取session中的安全数据
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();

        if (principalCollection != null && !principalCollection.isEmpty()) {
            // 1.获取安全数据
            this.currentUser = (CurrentUser) principalCollection.getPrimaryPrincipal();
        }
    }
}
