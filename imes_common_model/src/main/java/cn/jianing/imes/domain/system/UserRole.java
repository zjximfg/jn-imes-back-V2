package cn.jianing.imes.domain.system;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "pe_user_role")
public class UserRole {

    @Id
    private String id;

    private String userId;

    private String roleId;
}
