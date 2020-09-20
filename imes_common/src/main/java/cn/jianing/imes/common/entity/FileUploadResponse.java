package cn.jianing.imes.common.entity;

import lombok.Data;

import java.util.Map;

@Data
public class FileUploadResponse {

    //   uid: 'uid',      // 文件唯一标识，建议设置为负数，防止和内部产生的 id 冲突
    //   name: 'xx.png'   // 文件名
    //   status: 'done', // 状态有：uploading done error removed
    //   response: '{"status": "success"}', // 服务端响应内容
    //   linkProps: '{"download": "image"}', // 下载链接额外的 HTML 属性
    public static final String STATUS_DONE = "done";
    public static final String STATUS_UPLOADING = "uploading";
    public static final String STATUS_ERROR = "error";
    public static final String STATUS_REMOVED = "removed";



    private String uid;
    private String name;
    private String status;
    private String response;
    private Map<String, Object> linkProps;

}
