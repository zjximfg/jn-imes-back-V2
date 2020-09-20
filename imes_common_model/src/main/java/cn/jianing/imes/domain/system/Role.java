package cn.jianing.imes.domain.system;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Set;

@Data
@Table(name = "pe_role")
public class Role {

    @Id
    private String id;

    private String name;

    private String description;

    private String companyId;

    private Boolean isDeleted;

    @Transient
    private Set<Permission> permissionSet;

}
