package cn.jianing.imes.warehouse.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.domain.warehouse.OutWarehouse;
import cn.jianing.imes.domain.warehouse.OutWarehouseRebar;
import cn.jianing.imes.domain.warehouse.WarehouseStorage;
import cn.jianing.imes.warehouse.service.OutWarehouseRebarService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import cn.jianing.imes.warehouse.mapper.OutWarehouseMapper;
import cn.jianing.imes.warehouse.service.OutWarehouseService;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Map;

@Service
public class OutWarehouseServiceImpl implements OutWarehouseService {

    @Resource
    private OutWarehouseMapper outWarehouseMapper;

    @Resource
    private OutWarehouseRebarService outWarehouseRebarService;

    @Resource
    private IdWorker idWorker;

    @Transactional
    @Override
    public void insertOutWarehouseCascade(OutWarehouse outWarehouse) {
        // 1. 赋值id
        outWarehouse.setId(String.valueOf(idWorker.nextId()));
        // 2. 赋值清除位
        outWarehouse.setIsDeleted(false);
        // 3. 保存到数据库
        outWarehouseMapper.insertSelective(outWarehouse);
        // 4. 保存outWarehouseRebar
        for (OutWarehouseRebar outWarehouseRebar : outWarehouse.getOutWarehouseRebarList()) {
            // 4.1 赋值outWarehouseRebar的父id
            outWarehouseRebar.setOutWarehouseId(outWarehouse.getId());
            outWarehouseRebarService.insetOutWarehouseRebar(outWarehouseRebar);
        }
        // 5. 发送message到mq 通知库存模块减库存
        // TODO
    }

    @Override
    public Page<OutWarehouse> getOutWarehousePageByCompanyId(int current, int pageSize, String companyId, Map<String, Object> map) {
        PageHelper.startPage(current, pageSize);
        Example example = new Example(WarehouseStorage.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("companyId", companyId);
        String sortName = null;
        String sortOrder = null;
        for (Map.Entry<String, Object> entry : map.entrySet()) {

            if (entry.getKey().equals("id")) {
                criteria.andEqualTo("id", entry.getValue());
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
        return (Page<OutWarehouse>) outWarehouseMapper.selectByExample(example);
    }

    @Override
    public OutWarehouse getOutWarehouseById(String id) {
        return outWarehouseMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateOutWarehouse(OutWarehouse outWarehouse) {
        OutWarehouse find = outWarehouseMapper.selectByPrimaryKey(outWarehouse.getId());
        if (find != null) {
            outWarehouseMapper.updateByPrimaryKeySelective(outWarehouse);
        }
    }

    @Override
    public void insertOutWarehouse(OutWarehouse outWarehouse) {
        outWarehouse.setId(String.valueOf(idWorker.nextId()));
        // 2. 赋值清除位
        outWarehouse.setIsDeleted(false);
        // 3. 保存到数据库
        outWarehouseMapper.insertSelective(outWarehouse);
    }
}
