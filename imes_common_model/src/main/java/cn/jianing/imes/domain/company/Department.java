package cn.jianing.imes.domain.company;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "co_department")
public class Department {

    @Id
    private String id;

    private String companyId;

    private String parentId;

    private String name;

    private String code;

    private String category;

    private String managerId;

    private String manager;

    private String city;

    private String introduce;

    private Date createTime;

    private Boolean isDeleted;
}
