package cn.jianing.imes.device.service;

import cn.jianing.imes.domain.device.Device;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface DeviceService {
    List<Device> getDeviceListByCompanyId(String companyId);

    Device getDeviceById(String id);

    Page<Device> getDevicePageByCompanyId(int current, int pageSize, String companyId, Map<String, Object> map);

    void insertDevice(Device device);

    void updateDeviceById(Device device);

    void deleteDeviceById(String id);
}
