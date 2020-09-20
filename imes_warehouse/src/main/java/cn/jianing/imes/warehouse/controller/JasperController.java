package cn.jianing.imes.warehouse.controller;

import cn.jianing.imes.common.controller.BaseController;
import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
public class JasperController extends BaseController {

    @GetMapping("test/jasper")
    public void createJasperPdf() throws IOException {
        // 读取模板
        ClassPathResource resource = new ClassPathResource("jasper-template/test.jasper");
//        File file = resource.getFile();
//        FileInputStream fileInputStream = new FileInputStream(file);
        InputStream inputStream = resource.getInputStream();

        // 获取输出流，返回用
        ServletOutputStream outputStream = response.getOutputStream();
        // 填充数据
        try {
            // 1. fileInputStream 模板文件
            // 2. new HashMap<>() 参数
            // 3. new JREmptyDataSource() 数据源，可以是connection， javaBean
            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, new HashMap<>(), new JREmptyDataSource());
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
            outputStream.close();
        }
    }

    @GetMapping("test/jasper2")
    public void createJasperPdf2() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("jasper-template/testParam.jasper");

        ServletOutputStream outputStream = response.getOutputStream();

        Map<String, Object> map = new HashMap<>();

        map.put("username", "lisi");
        map.put("phone", "13752560888");

        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(classPathResource.getInputStream(), map, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            outputStream.close();
        }

    }


}
