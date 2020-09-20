package cn.jianing.imes.warehouse.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.domain.warehouse.WorkArea;
import cn.jianing.imes.warehouse.mapper.WorkAreaMapper;
import cn.jianing.imes.warehouse.service.WorkAreaService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class WorkAreaServiceImpl implements WorkAreaService {

    @Resource
    private IdWorker idWorker;
    @Resource
    private WorkAreaMapper workAreaMapper;

    @Override
    public Page<WorkArea> getWorkAreaPageByCompanyId(int current, int pageSize, String companyId, Map<String, Object> map) {
        PageHelper.startPage(current, pageSize);
        Example example = new Example(WorkArea.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("companyId", companyId);
        criteria.andEqualTo("isDeleted", false);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("key")) {
                criteria.orLike("name", "%" + entry.getValue() + "%").orLike("liaison", "%" + entry.getValue() + "%").orLike("position", "%" + entry.getValue() + "%");
            }
        }
        return (Page<WorkArea>) workAreaMapper.selectByExample(example);
    }

    @Override
    public WorkArea getWorkAreaById(String id) {
        return workAreaMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteWorkAreaById(String id) {
        WorkArea workArea = workAreaMapper.selectByPrimaryKey(id);
        if (workArea != null ) {
            workArea.setIsDeleted(true);
            workAreaMapper.updateByPrimaryKeySelective(workArea);
        }
    }

    @Override
    public void updateWorkAreaById(WorkArea workArea) {
        WorkArea find = workAreaMapper.selectByPrimaryKey(workArea.getId());
        if (find != null ) {
            workAreaMapper.updateByPrimaryKeySelective(workArea);
        }
    }

    @Override
    public void insertWorkArea(WorkArea workArea) {
        String id = String.valueOf(idWorker.nextId());
        workArea.setId(id);
        workArea.setIsDeleted(false);
        workAreaMapper.insertSelective(workArea);
    }

    @Override
    public List<WorkArea> getWorkAreaListByCompanyId(String companyId) {
        WorkArea workArea = new WorkArea();
        workArea.setCompanyId(companyId);
        workArea.setIsDeleted(false);
        return workAreaMapper.select(workArea);
    }
}
