package cn.jianing.imes.warehouse.service;

import cn.jianing.imes.domain.warehouse.OutWarehouse;
import com.github.pagehelper.Page;

import java.util.Map;

public interface OutWarehouseService{

    void insertOutWarehouseCascade(OutWarehouse outWarehouse);

    Page<OutWarehouse> getOutWarehousePageByCompanyId(int current, int pageSize, String companyId, Map<String, Object> map);

    OutWarehouse getOutWarehouseById(String id);

    void updateOutWarehouse(OutWarehouse outWarehouse);

    void insertOutWarehouse(OutWarehouse outWarehouse);
}
