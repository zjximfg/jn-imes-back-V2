package cn.jianing.imes.warehouse.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.domain.warehouse.*;
import cn.jianing.imes.domain.warehouse.vo.RebarEntryReport;
import cn.jianing.imes.warehouse.mapper.RebarEntryMapper;
import cn.jianing.imes.warehouse.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class RebarEntryServiceImpl implements RebarEntryService {

    @Resource
    private IdWorker idWorker;
    @Resource
    private RebarEntryMapper rebarEntryMapper;
    @Resource
    private AccessoryService accessoryService;
    @Resource
    private WarehouseEntryService warehouseEntryService;
    @Resource
    private WarehouseStorageService warehouseStorageService;
    @Resource
    private RebarMemberStorageService rebarMemberStorageService;
    @Resource
    private RebarStorageService rebarStorageService;


    @Override
    public List<RebarEntry> getRebarEntryListByWarehouseId(String warehouseId) {
        RebarEntry rebarEntry = new RebarEntry();
        rebarEntry.setIsDeleted(false);
        rebarEntry.setWarehouseEntryId(warehouseId);
        return rebarEntryMapper.select(rebarEntry);
    }

    @Override
    public RebarEntry getRebarEntryById(String id) {
        return rebarEntryMapper.selectByPrimaryKey(id);
    }

    @Override
    public void insertRebarEntry(RebarEntry rebarEntry) {
        String id = String.valueOf(idWorker.nextId());
        rebarEntry.setId(id);
        rebarEntry.setIsDeleted(false);
        rebarEntry.setHasPrinted(false);
        rebarEntryMapper.insertSelective(rebarEntry);
        // 插入附件表
        List<Accessory> accessories = rebarEntry.getAccessories();
        if (accessories == null) return;
        for (Accessory accessory : accessories) {
            accessory.setParentObjectId(id);
            accessoryService.insertAccessory(accessory);
        }
    }

    @Override
    public void updateRebarEntry(RebarEntry rebarEntry) {
        RebarEntry find = rebarEntryMapper.selectByPrimaryKey(rebarEntry.getId());
        if (find != null && find.getHasPrinted().equals(false)) {
            rebarEntryMapper.updateByPrimaryKeySelective(rebarEntry);
            // 更新附件表
            accessoryService.updateAccessoryListByParentId(rebarEntry.getId(), rebarEntry.getAccessories());
        }
    }

    @Override
    public void deleteRebarEntry(String id) {
        RebarEntry rebarEntry = rebarEntryMapper.selectByPrimaryKey(id);
        if (rebarEntry != null ) {
            rebarEntry.setIsDeleted(true);
            rebarEntryMapper.updateByPrimaryKeySelective(rebarEntry);
        }
    }

    @Override
    @Transactional
    public void storageByRebarEntryIdList(List<String> list) {
        // 根据list读取所有的warehouseEntry信息
        for (String id : list) {
            RebarEntry rebarEntry = this.getRebarEntryById(id);
            // 如果已经打印过了，就跳过入库
            if (rebarEntry.getHasPrinted()) continue;
            WarehouseEntry warehouseEntry = warehouseEntryService.getWarehouseEntryById(rebarEntry.getWarehouseEntryId());
            // 入库操作
            boolean isSuccess = warehouseStorageService.insertWarehouseStorage(warehouseEntry, rebarEntry);
            // 一个失败则停止后续操作
            if (isSuccess) {
                // 更新状态 为 已经打印状态
                rebarEntry.setHasPrinted(true);
                rebarEntryMapper.updateByPrimaryKeySelective(rebarEntry);
            }
        }
    }

    @Override
    public List<RebarEntryReport> getRebarEntryReport(String rebarEntryId) {
        // 获取rebarMemberStorage
        RebarStorage rebarStorage = rebarStorageService.getRebarStorageByRebarEntryId(rebarEntryId);
        if (rebarStorage == null) return null;
        WarehouseStorage warehouseStorage = warehouseStorageService.getWarehouseStorageById(rebarStorage.getWarehouseStorageId());
        if (warehouseStorage == null) return null;
        List<RebarMemberStorage> rebarMemberStorageList = rebarMemberStorageService.getRebarMemberStorageListByRebarStorageId(rebarStorage.getId());
        List<RebarEntryReport> rebarEntryReportList = new ArrayList<>();
        for (RebarMemberStorage rebarMemberStorage : rebarMemberStorageList) {
            // 封装实体rebarEntryReport
            RebarEntryReport rebarEntryReport = new RebarEntryReport();
            rebarEntryReport.setId(rebarMemberStorage.getId());
            rebarEntryReport.setRebarEntryId(rebarEntryId);
            rebarEntryReport.setBatchNumber(rebarStorage.getBatchNumber());
            rebarEntryReport.setReceivingTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rebarStorage.getReceivingTime()));
            rebarEntryReport.setRebarCategory(warehouseStorage.getRebarCategory().equals(0)? "棒材": "线材");
            rebarEntryReport.setDiameter(warehouseStorage.getDiameter());
            rebarEntryReport.setSpecification(warehouseStorage.getSpecification());
            rebarEntryReport.setPackageQuantity(rebarMemberStorage.getQuantity());   // 根数
            rebarEntryReport.setQuantity(rebarStorage.getQuantity());  // 捆数
            rebarEntryReport.setRebarIndex(rebarMemberStorage.getRebarIndex());
            rebarEntryReport.setLabel("第" + rebarMemberStorage.getRebarIndex() + "捆/共" + rebarStorage.getQuantity() + "捆");
            rebarEntryReportList.add(rebarEntryReport);
        }
        return rebarEntryReportList;
    }
}
