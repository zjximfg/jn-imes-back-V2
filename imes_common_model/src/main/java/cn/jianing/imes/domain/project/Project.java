package cn.jianing.imes.domain.project;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name = "pj_project")
public class Project {
    @Id
    private String id;

    private String name;

    private String companyId;

    private String description;

    private Boolean isDeleted;

    @Transient
    private Integer frameMemberQuantity;

    @Transient
    private Double progress;

}
