package cn.jianing.imes.project.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.domain.project.FrameMember;
import cn.jianing.imes.project.mapper.FrameMemberMapper;
import cn.jianing.imes.project.service.FrameMemberService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class FrameMemberServiceImpl implements FrameMemberService {

    @Resource
    private IdWorker idWorker;
    @Resource
    private FrameMemberMapper frameMemberMapper;

    @Override
    public List<FrameMember> getFrameMemberListByProjectId(String projectId) {
        FrameMember frameMember = new FrameMember();
        frameMember.setIsDeleted(false);
        frameMember.setProjectId(projectId);
        return frameMemberMapper.select(frameMember);
    }

    @Override
    public void insertFrameMember(FrameMember frameMember) {
        String id = String.valueOf(idWorker.nextId());
        frameMember.setId(id);
        frameMember.setIsDeleted(false);
        frameMemberMapper.insertSelective(frameMember);
    }

    @Override
    public void updateFrameMemberById(FrameMember frameMember) {
        frameMemberMapper.updateByPrimaryKeySelective(frameMember);
    }

    @Override
    public void deleteFrameMemberById(String id) {
        FrameMember frameMember = frameMemberMapper.selectByPrimaryKey(id);
        frameMember.setIsDeleted(false);
        frameMemberMapper.updateByPrimaryKeySelective(frameMember);
    }

    @Override
    public FrameMember getFrameMemberById(String id) {
        return frameMemberMapper.selectByPrimaryKey(id);
    }

    @Override
    public Page<FrameMember> getFrameMemberPageByProjectId(int current, int pageSize, String projectId, Map<String, Object> map) {
        PageHelper.startPage(current, pageSize);
        Example example = new Example(FrameMember.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("projectId", projectId);
        criteria.andEqualTo("isDeleted", false);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("key")) {
                criteria.orLike("name", "%" + entry.getValue() + "%").orLike("description", "%" + entry.getValue() + "%");
            }
        }
        return (Page<FrameMember>) frameMemberMapper.selectByExample(example);
    }
}
