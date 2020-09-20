package cn.jianing.imes.warehouse.controller;

import cn.jianing.imes.common.entity.PageResult;
import cn.jianing.imes.domain.warehouse.RebarMemberStorage;
import cn.jianing.imes.warehouse.service.RebarMemberStorageService;
import com.github.pagehelper.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("warehouse/rebarMemberStorage")
public class RebarMemberStorageController {

    @Resource
    private RebarMemberStorageService rebarMemberStorageService;

    @GetMapping("page")
    public ResponseEntity<PageResult<RebarMemberStorage>> getRebarMemberStoragePageByRebarStorageId(int current, int pageSize, String rebarStorageId, @RequestParam Map<String, Object> map) {
        Page<RebarMemberStorage> page = rebarMemberStorageService.getRebarMemberStoragePageByRebarStorageId(current, pageSize, rebarStorageId, map);
        PageResult<RebarMemberStorage> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return ResponseEntity.ok(pageResult);
    }

    @GetMapping("list/query/rebarStorageId")
    public ResponseEntity<List<RebarMemberStorage>> getRebarMemberStorageListByRebarStorageId(@RequestParam("rebarStorageId") String rebarStorageId) {
        List<RebarMemberStorage> rebarMemberStorageList = rebarMemberStorageService.getRebarMemberStorageListByRebarStorageId(rebarStorageId);
        return ResponseEntity.ok(rebarMemberStorageList);
    }
}
