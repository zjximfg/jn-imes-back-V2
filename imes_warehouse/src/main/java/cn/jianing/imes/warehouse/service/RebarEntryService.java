package cn.jianing.imes.warehouse.service;

import cn.jianing.imes.domain.warehouse.RebarEntry;
import cn.jianing.imes.domain.warehouse.vo.RebarEntryReport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RebarEntryService {
    List<RebarEntry> getRebarEntryListByWarehouseId(String warehouseId);

    RebarEntry getRebarEntryById(String id);

    void insertRebarEntry(RebarEntry rebarEntry);

    void updateRebarEntry(RebarEntry rebarEntry);

    void deleteRebarEntry(String id);

    void storageByRebarEntryIdList(List<String> list);

    List<RebarEntryReport> getRebarEntryReport(String rebarEntryId);
}
