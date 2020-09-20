package cn.jianing.imes.domain.warehouse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "wh_rebar_storage")
public class RebarStorage {
    @Id
    private String id;

    private String warehouseStorageId;   // 一对多 库存分类id
    private String rebarEntryId;         // 钢材入库明细 一对一
    private String manufacturer;         // 生产商
    private Integer quantity;        // 捆数
    private Integer totalQuantity;        // 总根数
    private Double theoreticalWeight;    // 总理重
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receivingTime;        // 收货时间
    private String batchNumber;          // 批次号
    private String experimentCode;       // 实验代码
    private String usagePosition;        // 使用位置
    private String alarmInfo;            // 报警信息
}
