package cn.jianing.imes.warehouse.service;

import cn.jianing.imes.domain.warehouse.OutWarehouseRebar;

import java.util.List;
import java.util.Map;

public interface OutWarehouseRebarService{


    void insetOutWarehouseRebar(OutWarehouseRebar outWarehouseRebar);

    List<OutWarehouseRebar> getOutWarehouseRebarByOutWarehouseId(String outWarehouseId);

    OutWarehouseRebar getOutWarehouseRebarById(String id);

    List<String> querySpecificationListInWarehouseStorages(Map<String, Object> map, String companyId);

    List<Integer> queryDiameterListInWarehouseStorages(Map<String, Object> map, String companyId);

    List<Integer> queryLengthListInWarehouseStorages(Map<String, Object> map, String companyId);
}
