package cn.jianing.imes.project.service;

import cn.jianing.imes.domain.project.FrameMember;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface FrameMemberService {

    List<FrameMember> getFrameMemberListByProjectId(String projectId);

    void insertFrameMember(FrameMember frameMember);

    void updateFrameMemberById(FrameMember frameMember);

    void deleteFrameMemberById(String id);

    FrameMember getFrameMemberById(String id);

    Page<FrameMember> getFrameMemberPageByProjectId(int current, int pageSize, String projectId, Map<String, Object> map);
}
