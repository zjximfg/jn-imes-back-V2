package cn.jianing.imes.warehouse.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.common.utils.ExcelImportUtils;
import cn.jianing.imes.common.utils.ResponseEntityDownloadUtils;
import cn.jianing.imes.domain.warehouse.RebarEntry;
import cn.jianing.imes.domain.warehouse.vo.RebarEntryReport;
import cn.jianing.imes.warehouse.service.RebarEntryService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("warehouse/rebarEntries")
public class RebarEntryController extends BaseController {

    @Resource
    private RebarEntryService rebarEntryService;

    @GetMapping
    public ResponseEntity<List<RebarEntry>> getRebarEntryListByWarehouseId(@RequestParam("warehouseId") String warehouseId) {
        List<RebarEntry> rebarEntryList= rebarEntryService.getRebarEntryListByWarehouseId(warehouseId);
        return ResponseEntity.ok(rebarEntryList);
    }

    @GetMapping("{id}")
    public ResponseEntity<RebarEntry> getRebarEntryById(@PathVariable("id") String id) {
        RebarEntry rebarEntry = rebarEntryService.getRebarEntryById(id);
        return ResponseEntity.ok(rebarEntry);
    }


    // 需要联动保存附件表
    @PostMapping
    public ResponseEntity<Void> insertRebarEntry(@RequestBody RebarEntry rebarEntry) {
        rebarEntryService.insertRebarEntry(rebarEntry);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 需要联动更新附件表
    @PutMapping("{id}")
    public ResponseEntity<Void> updateRebarEntry(@PathVariable("id") String id, @RequestBody RebarEntry rebarEntry) {
        rebarEntry.setId(id);
        rebarEntryService.updateRebarEntry(rebarEntry);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteRebarEntry(@PathVariable("id") String id) {
        rebarEntryService.deleteRebarEntry(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("files/template")
    public ResponseEntity<FileSystemResource> exportUserExcel() throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("excel-template/Rebar_Entry_Import_Template.xlsx");
        return ResponseEntityDownloadUtils.getResponseEntityDownload(classPathResource.getFile());
    }

    /**
     * 上传 导入 excel
     * @param file 上传的文件
     * @return 返回 void
     * @throws IOException 上传的文件无法打开，可能不是excel
     */
    @PostMapping("files/import/{warehouseEntryId}")
    public ResponseEntity<Void> importUserExcel(@PathVariable("warehouseEntryId") String warehouseEntryId, @RequestParam("file") MultipartFile file) throws Exception {
        List<RebarEntry> rebarEntryList = new ExcelImportUtils<>(RebarEntry.class).parseExcel(file.getInputStream(), 2, 0);
        for (RebarEntry rebarEntry : rebarEntryList) {
            rebarEntry.setWarehouseEntryId(warehouseEntryId);
            rebarEntry.setIsDeleted(false);
            rebarEntryService.insertRebarEntry(rebarEntry);
            System.out.println(rebarEntry);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("files/print")
    public void printRebarEntries(@RequestBody List<String> list) throws IOException {
        rebarEntryService.storageByRebarEntryIdList(list);
        // TODO 打印jasper reports
        // 读取模板
        ClassPathResource resource = new ClassPathResource("jasper-template/RebarEntryReports.jasper");
        InputStream inputStream = resource.getInputStream();
        // 获取输出流，返回用
        ServletOutputStream outputStream = response.getOutputStream();
        // 填充数据
        try {
            // 1. fileInputStream 模板文件
            // 2. new HashMap<>() 参数
            // 3. new JREmptyDataSource() 数据源，可以是connection， javaBean
            List<RebarEntryReport> rebarEntryReportList = new ArrayList<>();
            for (String rebarEntryId : list) {
                List<RebarEntryReport> rebarEntryReports = rebarEntryService.getRebarEntryReport(rebarEntryId);
                rebarEntryReportList.addAll(rebarEntryReports);
            }
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(rebarEntryReportList);
            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, new HashMap<>(), jrBeanCollectionDataSource);
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
            outputStream.close();
        }
    }

}
