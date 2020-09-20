package cn.jianing.imes.domain.warehouse;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "wh_out_warehouse_rebar_member")
public class OutWarehouseRebarMember implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "out_warehouse_rebar_id")
    private String outWarehouseRebarId;

    @Column(name = "rebar_member_storage_id")
    private String rebarMemberStorageId;

    @Column(name = "rebar_storage_id")
    private String rebarStorageId;

    /**
     * 钢筋捆序号
     */
    @Column(name = "rebar_index")
    private Integer rebarIndex;

    /**
     * 根数
     */
    @Column(name = "out_quantity")
    private Integer outQuantity;

    /**
     * 剩余理总（t）
     */
    @Column(name = "out_theoretical_weight")
    private BigDecimal outTheoreticalWeight;

    /**
     * 单根理总（t）
     */
    @Column(name = "unit_theoretical_weight")
    private BigDecimal unitTheoreticalWeight;

    private static final long serialVersionUID = 1L;
}