package cn.jianing.imes.warehouse.service;

import cn.jianing.imes.domain.warehouse.Accessory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccessoryService {
    void insertAccessory(Accessory accessory);

    void updateAccessoryListByParentId(String parentId, List<Accessory> accessories);

    List<Accessory> getAccessoriesByParentId(String parentId);
}
