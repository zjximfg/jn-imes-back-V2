package cn.jianing.imes.domain.device;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "de_device")
public class Device {

    @Id
    private String id;

    private String name;

    @Column(name = "device_usage")
    private String usage;

    private String code;

    @Column(name = "device_function")
    private String function;

    private String supplierId;

    private String templateId;

    private String gatewayId;

    private String description;

    private Boolean isDeleted;

    private String companyId;
}
