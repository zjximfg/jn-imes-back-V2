package cn.jianing.imes.project.service.impl;

import cn.jianing.imes.common.entity.PageResult;
import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.domain.project.FrameMember;
import cn.jianing.imes.domain.project.Project;
import cn.jianing.imes.project.mapper.ProjectMapper;
import cn.jianing.imes.project.service.FrameMemberService;
import cn.jianing.imes.project.service.ProjectService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private IdWorker idWorker;
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private FrameMemberService frameMemberService;

    @Override
    public List<Project> getProjectListByCompanyId(String companyId) {
        Project project = new Project();
        project.setCompanyId(companyId);
        project.setIsDeleted(false);
        List<Project> projectList = projectMapper.select(project);
        for (Project item : projectList) {
            List<FrameMember> frameMemberList = frameMemberService.getFrameMemberListByProjectId(item.getId());
            item.setFrameMemberQuantity(frameMemberList.size());
            item.setProgress(this.getTotalProgress(frameMemberList));
        }
        return projectList;
    }

    @Override
    public void insertProject(Project project) {
        String id = String.valueOf(idWorker.nextId());
        project.setId(id);
        project.setIsDeleted(false);
        projectMapper.insertSelective(project);
    }

    @Override
    public void updateProjectById(Project project) {
        projectMapper.updateByPrimaryKeySelective(project);
    }

    @Override
    public void deleteProjectById(String id) {
        Project project = projectMapper.selectByPrimaryKey(id);
        project.setIsDeleted(false);
        projectMapper.updateByPrimaryKeySelective(project);
    }

    @Override
    public Project getProjectById(String id) {
        return projectMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult<Project> getProjectPageByCompanyId(int current, int pageSize, String companyId, Map<String, Object> map) {
        PageHelper.startPage(current, pageSize);
        Example example = new Example(Project.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("companyId", companyId);
        criteria.andEqualTo("isDeleted", false);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("key")) {
                criteria.orLike("name", "%" + entry.getValue() + "%").orLike("description", "%" + entry.getValue() + "%");
            }
        }
        Page<Project> projects = (Page<Project>) projectMapper.selectByExample(example);
        for (Project item : projects.getResult()) {
            List<FrameMember> frameMemberList = frameMemberService.getFrameMemberListByProjectId(item.getId());
            item.setFrameMemberQuantity(frameMemberList.size());
            item.setProgress(this.getTotalProgress(frameMemberList));
        }
        return new PageResult<>(projects.getTotal(), projects.getResult());
    }

    private double getTotalProgress(List<FrameMember> frameMemberList) {
        double totalProgress = 0.0;
        for (FrameMember frameMember : frameMemberList) {
            totalProgress += frameMember.getProgress();
        }
        if (frameMemberList.size() > 0) {
            totalProgress = totalProgress / frameMemberList.size();
        }
        return totalProgress;
    }
}
