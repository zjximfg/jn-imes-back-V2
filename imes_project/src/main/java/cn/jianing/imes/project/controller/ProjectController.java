package cn.jianing.imes.project.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.common.entity.PageResult;
import cn.jianing.imes.domain.project.Project;
import cn.jianing.imes.project.service.ProjectService;
import com.github.pagehelper.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("project/projects")
public class ProjectController extends BaseController {

    @Resource
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Project>> getProjectListByCompanyId() {
        List<Project> projectList = projectService.getProjectListByCompanyId(currentUser.getCompanyId());
        return ResponseEntity.ok(projectList);
    }

    @PostMapping
    public ResponseEntity<Void> insertProject(@RequestBody Project project) {
        project.setCompanyId(currentUser.getCompanyId());
        projectService.insertProject(project);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateProjectById(@PathVariable("id") String id, @RequestBody Project project) {
        project.setId(id);
        projectService.updateProjectById(project);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProjectById(@PathVariable("id") String id) {
        projectService.deleteProjectById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable("id") String id) {
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    @GetMapping("page")
    public ResponseEntity<PageResult<Project>> getProjectPageByCompanyId(int current, int pageSize, @RequestParam Map<String, Object> map) {
        PageResult<Project> pageResult = projectService.getProjectPageByCompanyId(current, pageSize, currentUser.getCompanyId(), map);
        return ResponseEntity.ok(pageResult);
    }
}
