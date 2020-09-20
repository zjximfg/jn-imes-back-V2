package cn.jianing.imes.device.service;

import cn.jianing.imes.domain.device.DeviceSupplier;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface DeviceSupplierService {
    List<DeviceSupplier> getDeviceSupplierListByCompanyId(String companyId);

    DeviceSupplier getDeviceSupplierById(String id);

    Page<DeviceSupplier> getDeviceSupplierPageByCompanyId(int current, int pageSize, String companyId, Map<String, Object> map);

    void insertDeviceSupplier(DeviceSupplier deviceSupplier);

    void updateDeviceSupplierById(DeviceSupplier deviceSupplier);

    void deleteDeviceSupplierById(String id);
}
