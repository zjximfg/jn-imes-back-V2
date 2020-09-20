package cn.jianing.imes.domain.system;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "pe_permission_menu")
public class PermissionMenu {

    @Id
    private String id;

    private String menuIcon;

    private String menuOrder;
}
