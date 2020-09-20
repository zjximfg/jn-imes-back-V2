package cn.jianing.imes.warehouse.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.domain.warehouse.RebarEntry;
import cn.jianing.imes.domain.warehouse.RebarStorage;
import cn.jianing.imes.domain.warehouse.WarehouseEntry;
import cn.jianing.imes.domain.warehouse.WarehouseStorage;
import cn.jianing.imes.warehouse.mapper.RebarStorageMapper;
import cn.jianing.imes.warehouse.mapper.WarehouseStorageMapper;
import cn.jianing.imes.warehouse.service.RebarMemberStorageService;
import cn.jianing.imes.warehouse.service.RebarStorageService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RebarStorageServiceImpl implements RebarStorageService {

    @Resource
    private IdWorker idWorker;
    @Resource
    private RebarStorageMapper rebarStorageMapper;
    @Resource
    private RebarMemberStorageService rebarMemberStorageService;
    @Resource
    private WarehouseStorageMapper warehouseStorageMapper;

    @Override
    @Transactional
    public boolean insertRebarStorage(String id, WarehouseEntry warehouseEntry, RebarEntry rebarEntry) {
        // 构建实例类
        String rebarStorageId = String.valueOf(idWorker.nextId());
        RebarStorage rebarStorage = new RebarStorage();
        rebarStorage.setId(rebarStorageId);
        rebarStorage.setWarehouseStorageId(id);
        rebarStorage.setRebarEntryId(rebarEntry.getId());
        rebarStorage.setManufacturer(rebarEntry.getManufacturer());
        rebarStorage.setQuantity(rebarEntry.getQuantity());
        rebarStorage.setTotalQuantity(rebarEntry.getQuantity() * rebarEntry.getPackageQuantity());
        rebarStorage.setTheoreticalWeight(rebarEntry.getTheoreticalWeight());
        rebarStorage.setReceivingTime(warehouseEntry.getReceivingTime());
        rebarStorage.setBatchNumber(rebarEntry.getBatchNumber());
        rebarStorage.setUsagePosition(rebarEntry.getUsagePosition());
//        rebarStorage.setAlarmInfo();  todo
        int i = rebarStorageMapper.insertSelective(rebarStorage);
        if (i != 1) return false;
        // 插入rebarMember
        return rebarMemberStorageService.insertRebarMemberStorageList(rebarStorageId, rebarEntry);
    }

    @Override
    public Page<RebarStorage> getRebarStoragePageByWarehouseStorageId(int current, int pageSize, String warehouseStorageId, Map<String, Object> map) {
        PageHelper.startPage(current, pageSize);
        Example example = new Example(RebarStorage.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("warehouseStorageId", warehouseStorageId);
        example.orderBy("receivingTime").desc();
        return (Page<RebarStorage>) rebarStorageMapper.selectByExample(example);
    }

    @Override
    public RebarStorage getRebarStorageStorageById(String id) {
        return rebarStorageMapper.selectByPrimaryKey(id);
    }

    @Override
    public RebarStorage getRebarStorageByRebarEntryId(String rebarEntryId) {
        RebarStorage rebarStorage = new RebarStorage();
        rebarStorage.setRebarEntryId(rebarEntryId);
        return rebarStorageMapper.select(rebarStorage).get(0);
    }

    @Override
    public List<String> getBatchNumberListByWarehouseStorageId(String id) {
        RebarStorage rebarStorage = new RebarStorage();
        rebarStorage.setWarehouseStorageId(id);
        List<RebarStorage> rebarStorageList = rebarStorageMapper.select(rebarStorage);
        return rebarStorageList.stream().map(RebarStorage::getBatchNumber).collect(Collectors.toList());
    }

    @Override
    public List<String> getBatchNumberListByConditions(int rebarCategory, String specification, int diameter, int length) {
        WarehouseStorage warehouseStorage = new WarehouseStorage();
        warehouseStorage.setRebarCategory(rebarCategory);
        warehouseStorage.setSpecification(specification);
        warehouseStorage.setDiameter(diameter);
        warehouseStorage.setLength(length);
        WarehouseStorage result = warehouseStorageMapper.selectOne(warehouseStorage);
        if (result == null) return null;
        return getBatchNumberListByWarehouseStorageId(result.getId());
    }

    @Override
    public RebarStorage getRebarStorageStorageByBatchNumber(String batchNumber) {
        RebarStorage rebarStorage = new RebarStorage();
        rebarStorage.setBatchNumber(batchNumber);
        return rebarStorageMapper.selectOne(rebarStorage);
    }
}
