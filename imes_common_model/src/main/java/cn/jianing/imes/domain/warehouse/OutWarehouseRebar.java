package cn.jianing.imes.domain.warehouse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "wh_out_warehouse_rebar")
public class OutWarehouseRebar implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "out_warehouse_id")
    private String outWarehouseId;

    @Column(name = "warehouse_storage_id")
    private String warehouseStorageId;

    @Column(name = "rebar_storage_id")
    private String rebarStorageId;

    /**
     * 钢筋种类
     */
    @Column(name = "rebar_category")
    private Integer rebarCategory;

    /**
     * 规格
     */
    @Column(name = "specification")
    private String specification;

    /**
     * 直径
     */
    @Column(name = "diameter")
    private Integer diameter;

    /**
     * 长度
     */
    @Column(name = "`length`")
    private Integer length;

    /**
     * 批次号
     */
    @Column(name = "batch_number")
    private String batchNumber;

    /**
     * 加工设备
     */
    @Column(name = "device_id")
    private String deviceId;

    /**
     * 出库数量
     */
    @Column(name = "outbound_quantity")
    private Integer outboundQuantity;

    /**
     * 出库单位
     */
    @Column(name = "outbound_quantity_unit")
    private String outboundQuantityUnit;

    /**
     * 出库理重
     */
    @Column(name = "outbound_theoretical_weight")
    private BigDecimal outboundTheoreticalWeight;

    /**
     * 出库实重
     */
    @Column(name = "outbound_actual_weight")
    private BigDecimal outboundActualWeight;

    @Transient
    private List<OutWarehouseRebarMember> outWarehouseRebarMemberList;


    private static final long serialVersionUID = 1L;
}