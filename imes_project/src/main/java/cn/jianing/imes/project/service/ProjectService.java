package cn.jianing.imes.project.service;

import cn.jianing.imes.common.entity.PageResult;
import cn.jianing.imes.domain.project.Project;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ProjectService {
    List<Project> getProjectListByCompanyId(String companyId);

    void insertProject(Project project);

    void updateProjectById(Project project);

    void deleteProjectById(String id);

    Project getProjectById(String id);

    PageResult<Project> getProjectPageByCompanyId(int current, int pageSize, String companyId, Map<String, Object> map);
}
