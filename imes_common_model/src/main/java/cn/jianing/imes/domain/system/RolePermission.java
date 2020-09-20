package cn.jianing.imes.domain.system;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "pe_role_permission")
public class RolePermission {


    @Id
    private String id;

    private String roleId;

    private String permissionId;

}
