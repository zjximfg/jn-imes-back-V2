package cn.jianing.imes.domain.system;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;

    private String password;

    private String mobile;

    private String captcha;  //验证码

    private String type;
}
