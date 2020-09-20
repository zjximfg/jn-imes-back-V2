package cn.jianing.imes.warehouse.service.impl;

import cn.jianing.imes.common.utils.SetUtils;
import cn.jianing.imes.domain.warehouse.Accessory;
import cn.jianing.imes.warehouse.mapper.AccessoryMapper;
import cn.jianing.imes.warehouse.service.AccessoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AccessoryServiceImpl implements AccessoryService {

    @Resource
    private AccessoryMapper accessoryMapper;

    @Override
    public void insertAccessory(Accessory accessory) {
        accessoryMapper.insertSelective(accessory);
    }

    @Override
    public void updateAccessoryListByParentId(String parentId, List<Accessory> accessories) {
        // 原始数据
        List<Accessory> accessoryList = getAccessoriesByParentId(parentId);
        // 创建比较列表（新）
        List<String> accessoryIdsNew = new ArrayList<>();
        for (Accessory accessory : accessories) {
            accessoryIdsNew.add(accessory.getUid());
        }
        // 创建比较列表（旧）
        List<String> accessoryIdsOld = new ArrayList<>();
        for (Accessory accessory : accessoryList) {
            accessoryIdsOld.add(accessory.getUid());
        }
        // old 差集 new 删除
        Set<String> deleteSet = SetUtils.getDiffSet(accessoryIdsOld, accessoryIdsNew);
        for (String uid : deleteSet) {
            accessoryMapper.deleteByPrimaryKey(uid);
        }

        // new 差集 old 增加
        Set<String> addSet = SetUtils.getDiffSet(accessoryIdsNew, accessoryIdsOld);
        for (String uid : addSet) {
            for (Accessory accessory : accessories) {
                accessory.setParentObjectId(parentId);
                if (uid.equals(accessory.getUid())) {
                    accessoryMapper.insertSelective(accessory);
                }
            }
        }
    }

    @Override
    public List<Accessory> getAccessoriesByParentId(String parentId) {
        Accessory accessory = new Accessory();
        accessory.setParentObjectId(parentId);
        return accessoryMapper.select(accessory);
    }
}
