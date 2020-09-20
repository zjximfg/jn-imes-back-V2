package cn.jianing.imes.domain.system;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "pe_permission_api")
public class PermissionApi {

    @Id
    private String id;

    private String apiUrl;

    private String apiMethod;

    private String apiLevel;

}
