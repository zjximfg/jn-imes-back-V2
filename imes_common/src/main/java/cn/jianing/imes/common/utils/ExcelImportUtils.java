package cn.jianing.imes.common.utils;

import cn.jianing.imes.domain.annotation.ExcelAttribute;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExcelImportUtils<T> {

    private Class<T> clazz;
    private Field[] fields;

    public ExcelImportUtils(Class<T> clazz) {
        this.clazz = clazz;
        this.fields = clazz.getDeclaredFields();
    }

    /**
     * @param inputStream 上传的excel文件的数据流
     * @param rowIndex    数据的起始行 以0开始
     * @param cellIndex   数据的起始列 以0开始
     * @return 对象数据
     * @throws Exception 异常
     */
    public List<T> parseExcel(InputStream inputStream, int rowIndex, int cellIndex) throws Exception {
        List<T> list = new ArrayList<>();

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        for (int rowNum = rowIndex; rowNum < sheet.getLastRowNum() + 1; rowNum++) {
            XSSFRow row = sheet.getRow(rowNum);
            if (row != null) {
                T entity = clazz.newInstance();
                for (int cellNum = cellIndex; cellNum < row.getLastCellNum(); cellNum++) {
                    XSSFCell cell = row.getCell(cellNum);
                    if (cell != null) {
                        for (Field field : fields) {
                            if (field.isAnnotationPresent(ExcelAttribute.class)) {
                                field.setAccessible(true);
                                ExcelAttribute annotation = field.getAnnotation(ExcelAttribute.class);
                                if (cellNum == annotation.importCellNum()) {
                                    Class<?> type = field.getType();
                                    field.set(entity, getValue(type, cell));
                                }
                            }
                        }
                    }
                }
                list.add(entity);
            }
        }
        return list;
    }

    private Object getValue(Class<?> type, XSSFCell cell) throws Exception {
        String value = ExcelUtils.getValue(cell);
        switch (type.getSimpleName()) {
            case "String":
                return value;
            case "Date":
                return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(value);
            case "int":
                if (value.endsWith(".0")) {
                    value = StringUtils.substringBefore(value, ".0");
                }
                return Integer.parseInt(value);
            case "Integer":
                if (value.endsWith(".0")) {
                    value = StringUtils.substringBefore(value, ".0");
                }
                return Integer.parseInt(value);
            case "double":
                return Double.parseDouble(value);
            case "Double":
                return Double.parseDouble(value);
            case "Boolean":
                return Boolean.parseBoolean(value);
            case "boolean":
                return Boolean.parseBoolean(value);
            default:
                return null;
        }
    }
}
