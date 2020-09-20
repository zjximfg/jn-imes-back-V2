package cn.jianing.imes.domain.warehouse.vo;

import lombok.Data;

@Data
public class RebarEntryReport {
    private String id;
    private String rebarEntryId;
    private String batchNumber;
    private String receivingTime;
    private String rebarCategory;   //钢筋种类，0=棒材， 1=线材
    private Integer diameter;
    private String specification;   // 规格
    private Integer packageQuantity;   // 根数
    private Integer quantity;       // 捆数
    private Integer rebarIndex;     // 第几捆
    private String label;           // 第？捆/共？捆
}
