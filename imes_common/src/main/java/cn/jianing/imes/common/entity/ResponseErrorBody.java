package cn.jianing.imes.common.entity;

import lombok.Data;

@Data
public class ResponseErrorBody {

    private int status;
    private String error;
    private String message;
    private boolean success;
}
