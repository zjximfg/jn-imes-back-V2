package cn.jianing.imes.domain.warehouse;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "wh_warehouse_storage")
public class WarehouseStorage {

    @Id
    private String id;

    private String companyId;

    private Integer rebarCategory;

    private String specification;

    private Integer diameter;

    private Integer length;

    private Double totalTheoreticalWeight;

    private String alarmInfo;
}
