package cn.jianing.imes.domain.system;

import cn.jianing.imes.domain.annotation.ExcelAttribute;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.Set;

@Data
@Table(name = "bs_user")
@NoArgsConstructor
public class User {

    @Id
    private String id;

    @ExcelAttribute(exportCellNum = 1, importCellNum = 1)
    private String username;

    @ExcelAttribute(exportCellNum = 2, importCellNum = 2)
    private String mobile;

    private String password;

    @ExcelAttribute(exportCellNum = 3, importCellNum = 3)
    private String workNumber;    // 工号

    private Boolean enableState;   //禁用 和 启用

    private String companyId;

    private String departmentId;

    @Transient   // 用户excel导入
    @ExcelAttribute(exportCellNum = 4, importCellNum = 4)
    private String departmentCode;

    @ExcelAttribute(exportCellNum = 5)
    private Date createTime;

    @ExcelAttribute(exportCellNum = 6)
    private String description;

    private Boolean isDeleted;

    private String avatar;

//    public User(Object[] values) {
//        username = (String) values[1];
//        mobile = (String) values[2];
//        workNumber = (String) values[3];
//        departmentCode = (String) values[4];
//    }

    @Transient
    private Set<Role> roleSet;

}
