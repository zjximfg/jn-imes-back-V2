package cn.jianing.imes.domain.warehouse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "wh_warehouse_entry")
public class WarehouseEntry {
    @Id
    private String id;
    private String companyId;
    private String workAreaId;
    private String materialSupplierId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receivingTime;
    private Integer procurementMethod;
    private String receiver;
    private String submitter;
    private Boolean isDeleted;
    @Transient
    private Double totalTheoreticalWeight;
    @Transient
    private Double totalActualWeight;
    @Transient
    private Integer total;
    @Transient
    private List<Accessory> accessories;
}
