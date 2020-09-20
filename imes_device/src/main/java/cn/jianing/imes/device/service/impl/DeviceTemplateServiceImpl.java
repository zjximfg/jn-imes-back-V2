package cn.jianing.imes.device.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.device.mapper.DeviceTemplateMapper;
import cn.jianing.imes.device.service.DeviceTemplateService;
import cn.jianing.imes.domain.device.DeviceTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeviceTemplateServiceImpl implements DeviceTemplateService {

    @Resource
    private IdWorker idWorker;

    @Resource
    private DeviceTemplateMapper deviceTemplateMapper;

    @Override
    public List<DeviceTemplate> getDeviceTemplateList() {
        DeviceTemplate deviceTemplate = new DeviceTemplate();
        deviceTemplate.setIsDeleted(false);
        return deviceTemplateMapper.select(deviceTemplate);
    }

    @Override
    public void insertDeviceTemplate(DeviceTemplate deviceTemplate) {
        String id = String.valueOf(idWorker.nextId());
        deviceTemplate.setId(id);
        deviceTemplate.setIsDeleted(false);
        deviceTemplateMapper.insertSelective(deviceTemplate);
    }

    @Override
    public void updateDeviceTemplateById(DeviceTemplate deviceTemplate) {
        deviceTemplate.setIsDeleted(false);
        deviceTemplateMapper.updateByPrimaryKeySelective(deviceTemplate);
    }

    @Override
    public void deleteDeviceTemplateById(String id) {
        DeviceTemplate deviceTemplate = deviceTemplateMapper.selectByPrimaryKey(id);
        if (deviceTemplate != null) {
            deviceTemplate.setIsDeleted(true);
            deviceTemplateMapper.updateByPrimaryKeySelective(deviceTemplate);
        }

    }
}
