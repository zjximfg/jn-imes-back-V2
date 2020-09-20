package cn.jianing.imes.domain.device;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "de_device_supplier")
public class DeviceSupplier {

    @Id
    private String id;

    private String name;

    private String shortName;

    private String code;

    private String liaisonName;

    private String phone;

    private String description;

    private Boolean isDeleted;

    private String companyId;
}
