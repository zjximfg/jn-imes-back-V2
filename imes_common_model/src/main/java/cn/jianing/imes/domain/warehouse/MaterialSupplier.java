package cn.jianing.imes.domain.warehouse;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "wh_material_supplier")
public class MaterialSupplier {

    @Id
    private String id;

    private String companyId;

    private String name;

    private String address;

    private String liaison;

    private String phone;

    private Boolean isDeleted;          // 删除标记， =true时，删除。默认=false
}
