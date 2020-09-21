package cn.jianing.imes.warehouse.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.domain.warehouse.OutWarehouseRebar;
import cn.jianing.imes.domain.warehouse.OutWarehouseRebarMember;
import cn.jianing.imes.domain.warehouse.WarehouseStorage;
import cn.jianing.imes.warehouse.mapper.WarehouseStorageMapper;
import cn.jianing.imes.warehouse.service.OutWarehouseRebarMemberService;
import cn.jianing.imes.warehouse.service.WarehouseStorageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import cn.jianing.imes.warehouse.mapper.OutWarehouseRebarMapper;
import cn.jianing.imes.warehouse.service.OutWarehouseRebarService;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OutWarehouseRebarServiceImpl implements OutWarehouseRebarService {

    @Resource
    private OutWarehouseRebarMapper outWarehouseRebarMapper;
    @Resource
    private IdWorker idWorker;
    @Resource
    private OutWarehouseRebarMemberService outWarehouseRebarMemberService;
    @Resource
    private WarehouseStorageMapper warehouseStorageMapper;

    @Transactional
    @Override
    public void insetOutWarehouseRebar(OutWarehouseRebar outWarehouseRebar) {
        // 1. 赋值id 并保存到数据库
        outWarehouseRebar.setId(String.valueOf(idWorker.nextId()));
        outWarehouseRebarMapper.insertSelective(outWarehouseRebar);
        // 2. 保存outWarehouseRebarMember
        for (OutWarehouseRebarMember outWarehouseRebarMember : outWarehouseRebar.getOutWarehouseRebarMemberList()) {
            outWarehouseRebarMember.setOutWarehouseRebarId(outWarehouseRebar.getId());
            outWarehouseRebarMemberService.insertOutWarehouseRebarMember(outWarehouseRebarMember);
        }
    }

    @Override
    public List<OutWarehouseRebar> getOutWarehouseRebarByOutWarehouseId(String outWarehouseId) {
        OutWarehouseRebar outWarehouseRebar = new OutWarehouseRebar();
        outWarehouseRebar.setOutWarehouseId(outWarehouseId);
        return outWarehouseRebarMapper.select(outWarehouseRebar);
    }

    @Override
    public OutWarehouseRebar getOutWarehouseRebarById(String id) {
        return outWarehouseRebarMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<String> querySpecificationListInWarehouseStorage(Map<String, Object> map, String companyId) {
        Example example = new Example(WarehouseStorage.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("companyId", companyId);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("specification")) {
                criteria.andLike("specification", "%" + entry.getValue() + "%");
            }
            if (entry.getKey().equals("rebarCategory")) {
                criteria.andEqualTo("rebarCategory", entry.getValue());
            }
            if (entry.getKey().equals("diameter")) {
                criteria.andEqualTo("diameter", entry.getValue());
            }
            if (entry.getKey().equals("length")) {
                criteria.andEqualTo("length", entry.getValue());
            }
        }
        List<WarehouseStorage> warehouseStorageList = warehouseStorageMapper.selectByExample(example);
        return warehouseStorageList.stream().map(WarehouseStorage::getSpecification).collect(Collectors.toList());
    }

    @Override
    public List<Integer> queryDiameterListInWarehouseStorage(Map<String, Object> map, String companyId) {
        Example example = new Example(WarehouseStorage.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("companyId", companyId);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("specification")) {
                criteria.andEqualTo("specification", entry.getValue());
            }
            if (entry.getKey().equals("rebarCategory")) {
                criteria.andEqualTo("rebarCategory", entry.getValue());
            }
            if (entry.getKey().equals("length")) {
                criteria.andEqualTo("length", entry.getValue());
            }
        }
        List<WarehouseStorage> warehouseStorageList = warehouseStorageMapper.selectByExample(example);
        return warehouseStorageList.stream().map(WarehouseStorage::getDiameter).collect(Collectors.toList());
    }

    @Override
    public List<Integer> queryLengthListInWarehouseStorage(Map<String, Object> map, String companyId) {
        Example example = new Example(WarehouseStorage.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("companyId", companyId);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("specification")) {
                criteria.andEqualTo("specification", entry.getValue());
            }
            if (entry.getKey().equals("rebarCategory")) {
                criteria.andEqualTo("rebarCategory", entry.getValue());
            }
            if (entry.getKey().equals("diameter")) {
                criteria.andEqualTo("diameter", entry.getValue());
            }
        }
        List<WarehouseStorage> warehouseStorageList = warehouseStorageMapper.selectByExample(example);
        return warehouseStorageList.stream().map(WarehouseStorage::getLength).collect(Collectors.toList());
    }
}