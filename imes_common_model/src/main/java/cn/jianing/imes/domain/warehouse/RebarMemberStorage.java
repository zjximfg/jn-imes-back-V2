package cn.jianing.imes.domain.warehouse;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "wh_rebar_member_storage")
public class RebarMemberStorage {
    @Id
    private String id;

    private String rebarStorageId;       // 一对多 钢筋库存id
    private Integer rebarIndex;           // 钢筋捆序号
    private Integer quantity;             // 每捆的实际剩余数量
    private Double theoreticalWeight;    // 每捆实际剩余理重
    private Double unitTheoreticalWeight;  // 每根理重
}
