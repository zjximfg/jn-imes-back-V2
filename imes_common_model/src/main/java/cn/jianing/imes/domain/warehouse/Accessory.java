package cn.jianing.imes.domain.warehouse;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name = "wh_accessory")
public class Accessory {

    public static final String STATUS_DONE = "done";
    public static final String STATUS_UPLOADING = "uploading";
    public static final String STATUS_ERROR = "error";
    public static final String STATUS_REMOVED = "removed";

    @Id
    private String uid;
    private String url;
    private String name;
    private String parentObjectId;
    @Transient
    private String status = STATUS_DONE;

}
