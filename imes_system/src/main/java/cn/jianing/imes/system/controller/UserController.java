package cn.jianing.imes.system.controller;

import cn.jianing.imes.common.config.AliyunOssConfig;
import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.common.entity.FileUploadResponse;
import cn.jianing.imes.common.entity.PageResult;
import cn.jianing.imes.common.utils.ExcelExportUtils;
import cn.jianing.imes.common.utils.ExcelImportUtils;
import cn.jianing.imes.common.utils.FileUploadUtils;
import cn.jianing.imes.common.utils.ResponseEntityDownloadUtils;
import cn.jianing.imes.domain.company.Department;
import cn.jianing.imes.domain.system.User;
import cn.jianing.imes.domain.system.UserRole;
import cn.jianing.imes.system.client.DepartmentClient;
import cn.jianing.imes.system.service.UserService;
import com.aliyun.oss.OSS;
import com.github.pagehelper.Page;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("system/users")
public class UserController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private DepartmentClient departmentClient;
    @Resource
    private OSS ossClient;
    @Resource
    private AliyunOssConfig aliyunOssConfig;

    @PostMapping
    public ResponseEntity<Void> insertUser(@RequestBody User user) {
        String companyId = currentUser.getCompanyId();
        user.setCompanyId(companyId);
        userService.insertUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") String id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUserById(@PathVariable("id") String id, @RequestBody User user) {
        userService.updateUserById(id, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("page")
    public ResponseEntity<PageResult<User>> getUserPage(int current, int pageSize, @RequestParam Map<String, Object> map) {
        String companyId = currentUser.getCompanyId();
        Page<User> page = userService.getUserPage(current, pageSize, companyId, map);
        PageResult<User> userPageResult = new PageResult<>(page.getTotal(), page.getResult());
        return ResponseEntity.ok(userPageResult);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUserListByDepartmentId(@RequestParam("departmentId") String departmentId) {
        List<User> userList = userService.getUserListByDepartmentId(departmentId);
        return ResponseEntity.ok(userList);
    }

    @GetMapping("{userId}/userRoles")
    public ResponseEntity<List<UserRole>> getUserRoleByUserId(@PathVariable("userId") String userId) {
        List<UserRole> userRoleList = userService.getUserRoleByUserId(userId);
        return ResponseEntity.ok(userRoleList);
    }

    @PutMapping("{userId}/userRoles")
    public ResponseEntity<Void> updateUserRoleByUserId(@PathVariable("userId") String userId, @RequestBody List<String> roleIds) {
        userService.updateUserRoleByUserId(userId, roleIds);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 上传 导入 excel
     * @param file 上传的文件
     * @return 返回 void
     * @throws IOException 上传的文件无法打开，可能不是excel
     */
    @PostMapping("files")
    public ResponseEntity<Void> importUserExcel(@RequestParam("file") MultipartFile file) throws Exception {
        List<User> userList = new ExcelImportUtils<>(User.class).parseExcel(file.getInputStream(), 1, 0);
        for (User user : userList) {
            System.out.println(user);
        }
        userService.insertUserList(userList, currentUser.getCompanyId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("files")
    public ResponseEntity<FileSystemResource> exportUserExcel() throws Exception {
        List<User> userList = userService.getUserListByCompanyId(currentUser.getCompanyId());
        ClassPathResource classPathResource = new ClassPathResource("excel-template/user-demo.xlsx");
        FileInputStream fileInputStream = new FileInputStream(classPathResource.getFile());
        File export = new ExcelExportUtils<>(User.class, 2, 2).export(response, fileInputStream, userList, "user.xlsx");
        return ResponseEntityDownloadUtils.getResponseEntityDownload(export);
    }

    // 测试feign组件
    @GetMapping("test/{id}")
    public ResponseEntity<Department> testFeign(@PathVariable("id") String id) {
        return departmentClient.getDepartmentById(id);
    }

    @GetMapping("currentUser")
    public ResponseEntity<User> getUserByCurrentUser() {
        User user = userService.getUserByCurrentUser(currentUser);
        return ResponseEntity.ok(user);
    }

    @PostMapping("{id}/avatar")
    public ResponseEntity<FileUploadResponse> uploadAvatar(@PathVariable("id") String id, @RequestParam("file") MultipartFile file) {
        //上传到阿里云oss服务器中, 文件名就是用户的id
        FileUploadResponse fileUploadResponse = FileUploadUtils.imageUpload(file, ossClient, aliyunOssConfig.getBucketName(), aliyunOssConfig.getUrlPrefix(), id);
        // 将带连接地址的文件名 保存到用户的Avatar
        User user = new User();
        user.setAvatar(fileUploadResponse.getName());
        userService.updateUserById(id, user);
        return ResponseEntity.ok(fileUploadResponse);
    }
}
