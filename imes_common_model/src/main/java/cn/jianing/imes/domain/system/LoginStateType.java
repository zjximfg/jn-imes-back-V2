package cn.jianing.imes.domain.system;

import lombok.Data;

@Data
public class LoginStateType {
    private String status;  //'ok' | 'error'

    private String sessionId;

    private String type;

}
