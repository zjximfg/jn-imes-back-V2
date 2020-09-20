package cn.jianing.imes.common.controller;

import cn.jianing.imes.common.entity.ResponseErrorBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ErrorController {

    // 公共错误跳转
    @RequestMapping("auth-error")
    public ResponseEntity<ResponseErrorBody> authError(int code) {
        ResponseErrorBody responseErrorBody = new ResponseErrorBody();
        if (code == 1) {
            responseErrorBody.setStatus(HttpStatus.UNAUTHORIZED.value());
            responseErrorBody.setError("Unauthorized");
            responseErrorBody.setMessage("未登录或登录错误");
            responseErrorBody.setSuccess(true);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseErrorBody);
        } else {
            responseErrorBody.setStatus(HttpStatus.FORBIDDEN.value());
            responseErrorBody.setError("Unauthorized");
            responseErrorBody.setMessage("没有权限");
            responseErrorBody.setSuccess(true);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseErrorBody);
        }

    }
}
