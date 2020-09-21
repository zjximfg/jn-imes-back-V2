package cn.jianing.imes.warehouse.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.domain.warehouse.RebarEntry;
import cn.jianing.imes.domain.warehouse.WarehouseEntry;
import cn.jianing.imes.domain.warehouse.WarehouseStorage;
import cn.jianing.imes.warehouse.mapper.WarehouseStorageMapper;
import cn.jianing.imes.warehouse.service.RebarStorageService;
import cn.jianing.imes.warehouse.service.WarehouseStorageService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

@Service
public class WarehouseStorageServiceImpl implements WarehouseStorageService {

    @Resource
    private IdWorker idWorker;
    @Resource
    private WarehouseStorageMapper warehouseStorageMapper;
    @Resource
    private RebarStorageService rebarStorageService;

    @Override
    public Page<WarehouseStorage> getWarehouseStoragePageByCompanyId(int current, int pageSize, String companyId, Map<String, Object> map) {
        PageHelper.startPage(current, pageSize);
        Example example = new Example(WarehouseStorage.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("companyId", companyId);
        String sortName = null;
        String sortOrder = null;
        for (Map.Entry<String, Object> entry : map.entrySet()) {

            if (entry.getKey().equals("rebarCategory")) {
                criteria.andEqualTo("rebarCategory", entry.getValue());
            }
            if (entry.getKey().equals("specification")) {
                criteria.andLike("specification", "%" + entry.getValue() + "%");
            }
            if (entry.getKey().equals("diameter")) {
                criteria.andEqualTo("diameter", entry.getValue());
            }
            if (entry.getKey().equals("length")) {
                criteria.andEqualTo("length", entry.getValue());
            }
            if (entry.getKey().equals("sortName")) {
                sortName = (String) entry.getValue();
            }
            if (entry.getKey().equals("sortOrder")) {
                sortOrder = (String) entry.getValue();
            }
        }
        if (StringUtils.isNotBlank(sortName) && StringUtils.isNotBlank(sortOrder)) {
            if (sortOrder.equals("ascend")) example.orderBy(sortName).asc();
            if (sortOrder.equals("descend")) example.orderBy(sortName).desc();
        }
        return (Page<WarehouseStorage>) warehouseStorageMapper.selectByExample(example);
    }

    @Override
    @Transactional
    public boolean insertWarehouseStorage(WarehouseEntry warehouseEntry, RebarEntry rebarEntry) {
        // 创建实体类
        WarehouseStorage warehouseStorage = new WarehouseStorage();
        warehouseStorage.setCompanyId(warehouseEntry.getCompanyId());
        warehouseStorage.setRebarCategory(rebarEntry.getRebarCategory());
        warehouseStorage.setSpecification(rebarEntry.getSpecification());
        warehouseStorage.setDiameter(rebarEntry.getDiameter());
        warehouseStorage.setLength(rebarEntry.getLength());

        String id = String.valueOf(idWorker.nextId());
        // 查找是否存在？
        List<WarehouseStorage> warehouseStorageListSaved = warehouseStorageMapper.select(warehouseStorage);
        if (warehouseStorageListSaved == null || warehouseStorageListSaved.size() == 0) {
            // 如果不存在，插入一个新值
            warehouseStorage.setId(id);
            warehouseStorage.setTotalTheoreticalWeight(rebarEntry.getTheoreticalWeight());
            warehouseStorageMapper.insertSelective(warehouseStorage);
        } else {
            // 如果存在，不插入warehouseStorage， 更新总的理重
            id = warehouseStorageListSaved.get(0).getId();
            Double totalTheoreticalWeight = warehouseStorageListSaved.get(0).getTotalTheoreticalWeight();
            warehouseStorageListSaved.get(0).setTotalTheoreticalWeight(totalTheoreticalWeight + rebarEntry.getTheoreticalWeight());
            warehouseStorageMapper.updateByPrimaryKeySelective(warehouseStorageListSaved.get(0));
        }
        //插入rebarStorage
        return rebarStorageService.insertRebarStorage(id, warehouseEntry, rebarEntry);
    }

    @Override
    public WarehouseStorage getWarehouseStorageById(String id) {
        return warehouseStorageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<WarehouseStorage> queryWarehouseStorageByCondition(Map<String, Object> conditions) {
        Example example = new Example(WarehouseStorage.class);
        Example.Criteria criteria = example.createCriteria();
        for (Map.Entry<String, Object> entry : conditions.entrySet()) {
            criteria.andEqualTo(entry.getKey(), entry.getValue());
        }
        return warehouseStorageMapper.selectByExample(example);
    }
}
