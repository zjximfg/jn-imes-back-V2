package cn.jianing.imes.domain.warehouse;

import cn.jianing.imes.domain.annotation.ExcelAttribute;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Data
@Table(name = "wh_rebar_entry")
public class RebarEntry {

    @Id
    private String id;
    private String warehouseEntryId;
    private Boolean hasPrinted;     // =true 已经打印， = false 未打印
    @ExcelAttribute(importCellNum = 0)
    private Integer rebarCategory;   //钢筋种类，0=棒材， 1=线材
    @ExcelAttribute(importCellNum = 1)
    private String manufacturer;
    @ExcelAttribute(importCellNum = 2)
    private String specification;
    @ExcelAttribute(importCellNum = 3)
    private Integer diameter;
    @ExcelAttribute(importCellNum = 4)
    private Integer length;
    @ExcelAttribute(importCellNum = 5)
    private Integer quantity;
    private String quantityUnit = "捆";
    @ExcelAttribute(importCellNum = 6)
    private Integer packageQuantity;
    private String packageQuantityUnit = "根";
    @ExcelAttribute(importCellNum = 7)
    private Double theoreticalWeight;
    @ExcelAttribute(importCellNum = 8)
    private Double actualWeight;
    @ExcelAttribute(importCellNum = 9)
    private String batchNumber;
    @ExcelAttribute(importCellNum = 10)
    private String usagePosition;
    @ExcelAttribute(importCellNum = 11)
    private String driver;
    @ExcelAttribute(importCellNum = 12)
    private String vehicle;
    @ExcelAttribute(importCellNum = 13)
    private String remarks;
    private Boolean isDeleted;
    @Transient
    private String experimentCode;
    @Transient
    private List<Accessory> accessories;
}
