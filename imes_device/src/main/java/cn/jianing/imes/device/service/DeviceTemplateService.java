package cn.jianing.imes.device.service;

import cn.jianing.imes.domain.device.DeviceTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeviceTemplateService {

    List<DeviceTemplate> getDeviceTemplateList();

    void insertDeviceTemplate(DeviceTemplate deviceTemplate);

    void updateDeviceTemplateById(DeviceTemplate deviceTemplate);

    void deleteDeviceTemplateById(String id);
}
