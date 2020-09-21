package cn.jianing.imes.warehouse.service;

import cn.jianing.imes.domain.warehouse.RebarEntry;
import cn.jianing.imes.domain.warehouse.WarehouseEntry;
import cn.jianing.imes.domain.warehouse.WarehouseStorage;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface WarehouseStorageService {
    Page<WarehouseStorage> getWarehouseStoragePageByCompanyId(int current, int pageSize, String companyId, Map<String, Object> map);

    boolean insertWarehouseStorage(WarehouseEntry warehouseEntry, RebarEntry rebarEntry);

    WarehouseStorage getWarehouseStorageById(String id);

    List<WarehouseStorage> queryWarehouseStorageByCondition(Map<String, Object> conditions);
}
