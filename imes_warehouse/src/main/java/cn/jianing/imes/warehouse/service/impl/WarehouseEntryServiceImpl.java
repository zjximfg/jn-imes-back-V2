package cn.jianing.imes.warehouse.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.domain.warehouse.Accessory;
import cn.jianing.imes.domain.warehouse.RebarEntry;
import cn.jianing.imes.domain.warehouse.WarehouseEntry;
import cn.jianing.imes.warehouse.mapper.WarehouseEntryMapper;
import cn.jianing.imes.warehouse.service.AccessoryService;
import cn.jianing.imes.warehouse.service.RebarEntryService;
import cn.jianing.imes.warehouse.service.WarehouseEntryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WarehouseEntryServiceImpl implements WarehouseEntryService {

    @Resource
    private IdWorker idWorker;
    @Resource
    private WarehouseEntryMapper warehouseEntryMapper;
    @Resource
    private RebarEntryService rebarEntryService;
    @Resource
    private AccessoryService accessoryService;

    @Override
    public Page<WarehouseEntry> getWarehouseEntryPageByCompanyId(int current, int pageSize, String companyId, Map<String, Object> map) {
        PageHelper.startPage(current, pageSize);
        Example example = new Example(WarehouseEntry.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("companyId", companyId);
        criteria.andEqualTo("isDeleted", false);
        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Map.Entry<String, Object> entry : map.entrySet()) {

            if (entry.getKey().equals("id")) {
                criteria.andLike("id", "%" + entry.getValue() + "%");
            }
            try {
                if (entry.getKey().equals("receivingTimeStart")) {
                    startDate = simpleDateFormat.parse((String)entry.getValue());
                }
                if (entry.getKey().equals("receivingTimeEnd")) {
                    endDate = simpleDateFormat.parse((String)entry.getValue());
                }
                if (ObjectUtils.allNotNull(startDate, endDate)) {
                    criteria.andBetween("receivingTime", startDate, endDate);
                }
            } catch (Exception ex) {
                log.warn("日期解析错误！" + ex.getMessage());
            }
            if (entry.getKey().equals("key")) {
                criteria.orLike("name", "%" + entry.getValue() + "%").orLike("receiver", "%" + entry.getValue() + "%").orLike("submitter", "%" + entry.getValue() + "%");
            }
        }
//        example.setOrderByClause("receiving_time DESC");
        example.orderBy("receivingTime").desc();
        return (Page<WarehouseEntry>) warehouseEntryMapper.selectByExample(example);
    }

    @Override
    public WarehouseEntry getWarehouseEntryById(String id) {
        return warehouseEntryMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void insertWarehouseEntry(WarehouseEntry warehouseEntry) {
        String id = String.valueOf(idWorker.nextId());
        warehouseEntry.setId(id);
        warehouseEntry.setIsDeleted(false);
        warehouseEntryMapper.insertSelective(warehouseEntry);
        // 插入附件表
        List<Accessory> accessories = warehouseEntry.getAccessories();
        for (Accessory accessory : accessories) {
            accessory.setParentObjectId(id);
            accessoryService.insertAccessory(accessory);
        }
    }

    @Override
    @Transactional
    public void updateWarehouseEntry(WarehouseEntry warehouseEntry) {
        WarehouseEntry find = warehouseEntryMapper.selectByPrimaryKey(warehouseEntry.getId());
        if (find != null ) {
            warehouseEntryMapper.updateByPrimaryKeySelective(warehouseEntry);
            // 更新附件表
            accessoryService.updateAccessoryListByParentId(warehouseEntry.getId(), warehouseEntry.getAccessories());
        }
    }

    @Override
    public void deleteWarehouseEntry(String id) {
        WarehouseEntry warehouseEntry = warehouseEntryMapper.selectByPrimaryKey(id);
        if (warehouseEntry != null ) {
            warehouseEntry.setIsDeleted(true);
            warehouseEntryMapper.updateByPrimaryKeySelective(warehouseEntry);
        }
    }

    @Override
    public List<WarehouseEntry> getListWithRebarEntryInfo(List<WarehouseEntry> result) {
        for (WarehouseEntry warehouseEntry : result) {
            List<RebarEntry> rebarEntryList = rebarEntryService.getRebarEntryListByWarehouseId(warehouseEntry.getId());
            if (rebarEntryList == null || rebarEntryList.size() <= 0) continue;
            warehouseEntry.setTotal(rebarEntryList.size());
            double totalTheoreticalWeight = 0;
            double totalActualWeight = 0;
            for (RebarEntry rebarEntry : rebarEntryList) {
                totalTheoreticalWeight += rebarEntry.getTheoreticalWeight();
                totalActualWeight += rebarEntry.getActualWeight();
            }
            warehouseEntry.setTotalTheoreticalWeight(totalTheoreticalWeight);
            warehouseEntry.setTotalActualWeight(totalActualWeight);
        }
        return result;
    }
}
