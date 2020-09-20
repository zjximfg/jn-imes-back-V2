package cn.jianing.imes.common.utils;

import cn.jianing.imes.domain.annotation.ExcelAttribute;
import lombok.Data;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class ExcelExportUtils<T> {

    private int rowIndex;
    private int styleIndex;
    private String templatePath;
    private Class<T> clazz;
    private Field[] fields;

    public ExcelExportUtils(Class<T> clazz, int rowIndex, int styleIndex) {
        this.clazz = clazz;
        this.rowIndex = rowIndex;
        this.styleIndex = styleIndex;
        this.fields = clazz.getDeclaredFields();  // 获取类中所有的属性名 （不包括继承，包括public，protected， private）
    }

    public File export(HttpServletResponse response, InputStream inputStream, List<T> objs, String fileName) throws Exception {

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        // 获取模板样式行
        CellStyle[] templateStyles = getTemplateStyles(sheet.getRow(styleIndex));

        // 每一行数据写进excel中
        AtomicInteger rowIndexAi = new AtomicInteger(rowIndex);
        for (T obj : objs) {
            XSSFRow row = sheet.createRow(rowIndexAi.getAndIncrement());
            for (int i = 0; i < templateStyles.length; i++) {
                XSSFCell cell = row.createCell(i);
                cell.setCellStyle(templateStyles[i]);
                for (Field field : fields) {
                    if (field.isAnnotationPresent(ExcelAttribute.class)) {
                        field.setAccessible(true);  // 取消 属性访问权限检查 用于反射 如 private public 等
                        ExcelAttribute annotation = field.getAnnotation(ExcelAttribute.class);    // 获取属性上标记的注解
                        if (i == annotation.exportCellNum()) {    // 比较注解中 cellNum的值 与 单元格的数值是否一致
                            Object fieldValue = field.get(obj);
                            if (fieldValue != null) {
                                // 日期格式化
                                if (field.getType().getSimpleName().equals("Date")) {
                                    cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(fieldValue));
                                } else {
                                    cell.setCellValue(fieldValue.toString());  //利用反射， 赋值单元格， field 是 类中的所有属性， obj为实例，field.get(obj) 为 获得 obj中属性中与field一致 的属性的 值
                                }
                            }
                        }
                    }
                }
            }
        }

        // 写到输出流中
//        fileName = URLEncoder.encode(fileName, "UTF-8");
//        response.setContentType("application/octet-stream");
//        response.setHeader("content-disposition", "attachment;filename=" + new
//                String(fileName.getBytes("ISO8859-1")));
//        response.setHeader("filename", fileName);
        File file = new File(fileName);
        workbook.write(new FileOutputStream(file));
        workbook.close();
        return file;
    }

    private CellStyle[] getTemplateStyles(Row row) {
        CellStyle[] cellStyles = new CellStyle[row.getLastCellNum()];
        for(int i = 0; i < row.getLastCellNum(); i++) {
            if (row.getCell(i) != null)
            cellStyles[i] = row.getCell(i).getCellStyle();
        }
        return cellStyles;
    }
}
