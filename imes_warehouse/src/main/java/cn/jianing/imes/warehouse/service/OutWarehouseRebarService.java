package cn.jianing.imes.warehouse.service;

import cn.jianing.imes.domain.warehouse.OutWarehouseRebar;

import java.util.List;
import java.util.Map;

public interface OutWarehouseRebarService{


    void insetOutWarehouseRebar(OutWarehouseRebar outWarehouseRebar);

    List<OutWarehouseRebar> getOutWarehouseRebarByOutWarehouseId(String outWarehouseId);

    OutWarehouseRebar getOutWarehouseRebarById(String id);

    List<String> querySpecificationListInWarehouseStorage(Map<String, Object> map, String companyId);

    List<Integer> queryDiameterListInWarehouseStorage(Map<String, Object> map, String companyId);

    List<Integer> queryLengthListInWarehouseStorage(Map<String, Object> map, String companyId);
}
