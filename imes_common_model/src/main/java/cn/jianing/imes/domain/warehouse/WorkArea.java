package cn.jianing.imes.domain.warehouse;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "wh_work_area")
public class WorkArea {

    @Id
    private String id;

    private String companyId;

    private String name;

    private String position;

    private String liaison;

    private String phone;

    private Boolean isDeleted;          // 删除标记， =true时，删除。默认=false

}
