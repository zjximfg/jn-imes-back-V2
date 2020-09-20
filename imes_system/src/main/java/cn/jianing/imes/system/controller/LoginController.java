package cn.jianing.imes.system.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.domain.system.LoginRequest;
import cn.jianing.imes.domain.system.LoginStateType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("system/login")
public class LoginController extends BaseController {

    @PostMapping("account")
    public ResponseEntity<LoginStateType> accountLogin(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        try {
            // 0. 加密密码
            String password = new Md5Hash(loginRequest.getPassword(), loginRequest.getUsername(), 5).toString();
            // 1. 构造登录令牌
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            // 2. 获取subject
            Subject subject = SecurityUtils.getSubject();
            // 3. 调用login 方法
            subject.login(usernamePasswordToken);
            // 4. 获取sessionId
            String sessionId = (String)subject.getSession().getId();
            // 5. 返回结果
            LoginStateType loginStateType = new LoginStateType();
            loginStateType.setStatus("ok");
            loginStateType.setSessionId(sessionId);
            loginStateType.setType(loginRequest.getType());
            return ResponseEntity.ok(loginStateType);
        } catch (Exception e) {
            LoginStateType loginStateType = new LoginStateType();
            loginStateType.setStatus("error");
            loginStateType.setType(loginRequest.getType());
            return ResponseEntity.ok(loginStateType);
        }
    }
}
