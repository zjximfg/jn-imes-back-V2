package cn.jianing.imes.domain.system;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "pe_permission_point")
public class PermissionPoint {

    @Id
    private String id;

    private String pointIcon;

    private String pointClass;

    private String pointStatus;
}
