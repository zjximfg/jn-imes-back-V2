package cn.jianing.imes.domain.warehouse;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "wh_out_warehouse")
public class OutWarehouse implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 企业id
     */
    @Column(name = "company_id")
    private String companyId;

    /**
     * 用途
     */
    @Column(name = "purpose")
    private String purpose;

    /**
     * 领用单位
     */
    @Column(name = "recipients_unit")
    private String recipientsUnit;

    /**
     * 领用人
     */
    @Column(name = "recipient")
    private String recipient;

    /**
     * 领用时间
     */
    @Column(name = "recipients_time")
    private Date recipientsTime;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Transient
    private List<OutWarehouseRebar> outWarehouseRebarList;


    private static final long serialVersionUID = 1L;
}