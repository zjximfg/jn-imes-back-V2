package cn.jianing.imes.warehouse.controller;

import cn.jianing.imes.common.config.AliyunOssConfig;
import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.common.entity.FileUploadResponse;
import cn.jianing.imes.common.utils.FileUploadUtils;
import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.domain.warehouse.Accessory;
import cn.jianing.imes.warehouse.service.AccessoryService;
import com.aliyun.oss.OSS;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("warehouse/accessories")
public class AccessoryController extends BaseController {

    @Resource
    private AccessoryService accessoryService;
    @Resource
    private OSS ossClient;
    @Resource
    private AliyunOssConfig aliyunOssConfig;
    @Resource
    private IdWorker idWorker;

    @GetMapping
    public ResponseEntity<List<Accessory>> getAccessoriesByParentId(@RequestParam("parentId") String  parentId) {
        List<Accessory> accessoryList = accessoryService.getAccessoriesByParentId(parentId);
        return ResponseEntity.ok(accessoryList);
    }

    @PostMapping("upload")
    public ResponseEntity<Accessory> uploadAccessories(@RequestParam("file") MultipartFile file) {
        //上传到阿里云oss服务器中,
        FileUploadResponse fileUploadResponse = FileUploadUtils.imageUpload(file, ossClient, aliyunOssConfig.getBucketName(), aliyunOssConfig.getUrlPrefix(), file.getName());
        Accessory accessory = new Accessory();
        accessory.setUid(String.valueOf(idWorker.nextId()));
        accessory.setName(file.getName());
        accessory.setStatus(fileUploadResponse.getStatus());
        accessory.setUrl(fileUploadResponse.getName());
        return ResponseEntity.ok(accessory);
    }
}
