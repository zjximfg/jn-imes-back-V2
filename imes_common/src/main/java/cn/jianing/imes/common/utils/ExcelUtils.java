package cn.jianing.imes.common.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import java.text.SimpleDateFormat;

public class ExcelUtils {
    public static String getValue(Cell cell) {
        String value = null;
        switch (cell.getCellType()) {
            case STRING:   // 字符串类型
                System.out.println(cell.getStringCellValue());
                value = cell.getRichStringCellValue().getString().trim();
                break;
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) { //日期格式
                    System.out.println(cell.getDateCellValue());
                    value = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cell.getDateCellValue());
                    System.out.println(value);
                } else {
                    value = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case FORMULA:
                value = cell.getCellFormula();
                break;
            default:
                break;
        }
        return value;
    }
}
