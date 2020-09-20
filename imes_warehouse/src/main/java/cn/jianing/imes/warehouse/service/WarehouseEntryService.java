package cn.jianing.imes.warehouse.service;

import cn.jianing.imes.domain.warehouse.WarehouseEntry;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface WarehouseEntryService {
    Page<WarehouseEntry> getWarehouseEntryPageByCompanyId(int current, int pageSize, String companyId, Map<String, Object> map);

    WarehouseEntry getWarehouseEntryById(String id);

    void insertWarehouseEntry(WarehouseEntry warehouseEntry);

    void updateWarehouseEntry(WarehouseEntry warehouseEntry);

    void deleteWarehouseEntry(String id);

    List<WarehouseEntry> getListWithRebarEntryInfo(List<WarehouseEntry> result);
}
