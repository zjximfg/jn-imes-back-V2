package cn.jianing.imes.warehouse.service;

import cn.jianing.imes.domain.warehouse.RebarEntry;
import cn.jianing.imes.domain.warehouse.RebarStorage;
import cn.jianing.imes.domain.warehouse.WarehouseEntry;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RebarStorageService {
    boolean insertRebarStorage(String id, WarehouseEntry warehouseEntry, RebarEntry rebarEntry);

    Page<RebarStorage> getRebarStoragePageByWarehouseStorageId(int current, int pageSize, String warehouseStorageId, Map<String, Object> map);

    RebarStorage getRebarStorageStorageById(String id);

    RebarStorage getRebarStorageByRebarEntryId(String rebarEntryId);

    List<String> getBatchNumberListByWarehouseStorageId(String id);

    List<String> getBatchNumberListByConditions(int rebarCategory, String specification, int diameter, int length);

    RebarStorage getRebarStorageStorageByBatchNumber(String batchNumber);
}
