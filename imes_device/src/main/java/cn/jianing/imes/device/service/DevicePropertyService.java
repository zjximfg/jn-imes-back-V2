package cn.jianing.imes.device.service;

import cn.jianing.imes.domain.device.DeviceProperty;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface DevicePropertyService {
    Page<DeviceProperty> getDevicePropertyPageByTemplateId(int current, int pageSize, String templateId, Integer category, Map<String, Object> map);

    DeviceProperty getDevicePropertyById(String id);

    void insertDeviceProperty(DeviceProperty deviceProperty);

    void updateDevicePropertyById(DeviceProperty deviceProperty);

    void deleteDevicePropertyById(String id);

    List<DeviceProperty> getReadPropertyListByTemplateId(String templateId);

    List<DeviceProperty> getReadPropertyListByDeviceId(String deviceId);
}
