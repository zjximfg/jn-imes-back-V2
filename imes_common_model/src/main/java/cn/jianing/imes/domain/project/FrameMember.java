package cn.jianing.imes.domain.project;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "pj_frame_member")
public class FrameMember {

    @Id
    private String id;

    private String name;

    private String projectId;

    private Double progress;

    private String typeId;

    private String description;

    private Boolean isDeleted;

}
