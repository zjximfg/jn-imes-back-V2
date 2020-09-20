package cn.jianing.imes.domain.system;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name = "pe_permission")
public class Permission {

    @Id
    private String id;

    private String name;

    private Integer type;   // 1 = menu 2=  point 3 = api

    private String code;

    private String description;

    private String parentId;

    private Boolean systemVisible;

    @Transient
    private PermissionMenu permissionMenu;

    @Transient
    private PermissionPoint permissionPoint;

    @Transient
    private PermissionApi permissionApi;
}
