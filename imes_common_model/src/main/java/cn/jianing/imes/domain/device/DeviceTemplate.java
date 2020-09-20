package cn.jianing.imes.domain.device;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "de_device_template")
public class DeviceTemplate {

    @Id
    private String id;

    private String name;

    private String avatar;

    private String description;

    private Boolean isDeleted;
}
