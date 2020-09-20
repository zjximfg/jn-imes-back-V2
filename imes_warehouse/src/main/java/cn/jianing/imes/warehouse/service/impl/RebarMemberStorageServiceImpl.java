package cn.jianing.imes.warehouse.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.domain.warehouse.RebarEntry;
import cn.jianing.imes.domain.warehouse.RebarMemberStorage;
import cn.jianing.imes.domain.warehouse.RebarStorage;
import cn.jianing.imes.warehouse.mapper.RebarMemberStorageMapper;
import cn.jianing.imes.warehouse.service.RebarMemberStorageService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RebarMemberStorageServiceImpl implements RebarMemberStorageService {

    @Resource
    private IdWorker idWorker;
    @Resource
    private RebarMemberStorageMapper rebarMemberStorageMapper;

    @Override
    public boolean insertRebarMemberStorageList(String rebarStorageId, RebarEntry rebarEntry) {
        int count = 0;
        for (int i = 0; i < rebarEntry.getQuantity(); i++) {
            RebarMemberStorage rebarMemberStorage = new RebarMemberStorage();
            rebarMemberStorage.setId(String.valueOf(idWorker.nextId()));
            rebarMemberStorage.setRebarStorageId(rebarStorageId);
            rebarMemberStorage.setRebarIndex(i + 1);
            rebarMemberStorage.setQuantity(rebarEntry.getPackageQuantity());
            rebarMemberStorage.setTheoreticalWeight(rebarEntry.getTheoreticalWeight() / rebarEntry.getQuantity());
            rebarMemberStorage.setUnitTheoreticalWeight(rebarEntry.getTheoreticalWeight() / rebarEntry.getQuantity() / rebarEntry.getPackageQuantity());
            count += rebarMemberStorageMapper.insertSelective(rebarMemberStorage);
        }
        return count == rebarEntry.getQuantity();
    }

    @Override
    public Page<RebarMemberStorage> getRebarMemberStoragePageByRebarStorageId(int current, int pageSize, String rebarStorageId, Map<String, Object> map) {
        PageHelper.startPage(current, pageSize);
        Example example = new Example(RebarMemberStorage.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("rebarStorageId", rebarStorageId);
        example.orderBy("rebarIndex").asc();
        return (Page<RebarMemberStorage>) rebarMemberStorageMapper.selectByExample(example);
    }

    @Override
    public List<RebarMemberStorage> getRebarMemberStorageListByRebarStorageId(String id) {
        RebarMemberStorage rebarMemberStorage = new RebarMemberStorage();
        rebarMemberStorage.setRebarStorageId(id);
        return rebarMemberStorageMapper.select(rebarMemberStorage);
    }
}
