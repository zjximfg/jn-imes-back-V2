package cn.jianing.imes.device.controller;

import cn.jianing.imes.common.config.AliyunOssConfig;
import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.common.entity.FileUploadResponse;
import cn.jianing.imes.common.utils.FileUploadUtils;
import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.device.service.DeviceTemplateService;
import cn.jianing.imes.domain.device.DeviceTemplate;
import com.aliyun.oss.OSS;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("device/deviceTemplates")
public class DeviceTemplateController extends BaseController {

    @Resource
    private OSS ossClient;
    @Resource
    private AliyunOssConfig aliyunOssConfig;
    @Resource
    private IdWorker idWorker;
    @Resource
    private DeviceTemplateService deviceTemplateService;


    @PostMapping("upload/avatar")
    public ResponseEntity<FileUploadResponse> uploadAvatar(@RequestParam("file") MultipartFile file) {
        //上传到阿里云oss服务器中, 文件名就是随机生成id
        FileUploadResponse fileUploadResponse = FileUploadUtils.imageUpload(file, ossClient, aliyunOssConfig.getBucketName(), aliyunOssConfig.getUrlPrefix(), String.valueOf(idWorker.nextId()));
        // 将带连接地址的文件名 保存到用户的Avatar
        return ResponseEntity.ok(fileUploadResponse);
    }

    @GetMapping
    public ResponseEntity<List<DeviceTemplate>> getDeviceTemplateList() {
        List<DeviceTemplate> deviceTemplateList = deviceTemplateService.getDeviceTemplateList();
        return ResponseEntity.ok(deviceTemplateList);
    }

    @PostMapping
    public ResponseEntity<Void> insertDeviceTemplate(@RequestBody DeviceTemplate deviceTemplate) {
        deviceTemplateService.insertDeviceTemplate(deviceTemplate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateDeviceTemplateById(@PathVariable("id") String id, @RequestBody DeviceTemplate deviceTemplate) {
        deviceTemplate.setId(id);
        deviceTemplateService.updateDeviceTemplateById(deviceTemplate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDeviceTemplateById(@PathVariable("id") String id) {
        deviceTemplateService.deleteDeviceTemplateById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
