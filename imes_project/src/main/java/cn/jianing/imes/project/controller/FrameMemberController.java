package cn.jianing.imes.project.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.common.entity.PageResult;
import cn.jianing.imes.domain.project.FrameMember;
import cn.jianing.imes.project.service.FrameMemberService;
import com.github.pagehelper.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("project/frameMembers")
public class FrameMemberController extends BaseController {

    @Resource
    private FrameMemberService frameMemberService;

    @PostMapping
    public ResponseEntity<Void> insertFrameMember(@RequestBody FrameMember FrameMember) {
        frameMemberService.insertFrameMember(FrameMember);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateFrameMemberById(@PathVariable("id") String id, @RequestBody FrameMember FrameMember) {
        FrameMember.setId(id);
        frameMemberService.updateFrameMemberById(FrameMember);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFrameMemberById(@PathVariable("id") String id) {
        frameMemberService.deleteFrameMemberById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("{id}")
    public ResponseEntity<FrameMember> getFrameMemberById(@PathVariable("id") String id) {
        FrameMember FrameMember = frameMemberService.getFrameMemberById(id);
        return ResponseEntity.ok(FrameMember);
    }

    @GetMapping("page")
    public ResponseEntity<PageResult<FrameMember>> getFrameMemberPageByProjectId(int current, int pageSize, String projectId, @RequestParam Map<String, Object> map) {
        Page<FrameMember> page = frameMemberService.getFrameMemberPageByProjectId(current, pageSize, projectId, map);
        PageResult<FrameMember> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return ResponseEntity.ok(pageResult);
    }
}
