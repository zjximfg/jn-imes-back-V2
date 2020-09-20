package cn.jianing.imes.device.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.device.mapper.DevicePropertyMapper;
import cn.jianing.imes.device.service.DevicePropertyService;
import cn.jianing.imes.device.service.DeviceService;
import cn.jianing.imes.domain.device.Device;
import cn.jianing.imes.domain.device.DeviceProperty;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class DevicePropertyServiceImpl implements DevicePropertyService {

    @Resource
    private IdWorker idWorker;
    @Resource
    private DevicePropertyMapper devicePropertyMapper;
    @Resource
    private DeviceService deviceService;

    @Override
    public Page<DeviceProperty> getDevicePropertyPageByTemplateId(int current, int pageSize, String templateId, Integer category, Map<String, Object> map) {
        PageHelper.startPage(current, pageSize);
        Example example = new Example(DeviceProperty.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("templateId", templateId);
        criteria.andEqualTo("category", category);
        criteria.andEqualTo("isDeleted", false);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("key")) {
                criteria.orLike("name", "%" + entry.getValue() + "%").orLike("description", "%" + entry.getValue() + "%");
            }
        }
        return (Page<DeviceProperty>) devicePropertyMapper.selectByExample(example);
    }

    @Override
    public DeviceProperty getDevicePropertyById(String id) {
        return devicePropertyMapper.selectByPrimaryKey(id);
    }

    @Override
    public void insertDeviceProperty(DeviceProperty deviceProperty) {
        String id = String.valueOf(idWorker.nextId());
        deviceProperty.setId(id);
        deviceProperty.setIsDeleted(false);
        devicePropertyMapper.insertSelective(deviceProperty);
    }

    @Override
    public void updateDevicePropertyById(DeviceProperty deviceProperty) {
        deviceProperty.setIsDeleted(false);
        devicePropertyMapper.updateByPrimaryKeySelective(deviceProperty);
    }

    @Override
    public void deleteDevicePropertyById(String id) {
        DeviceProperty deviceProperty = devicePropertyMapper.selectByPrimaryKey(id);
        if (deviceProperty != null) {
            deviceProperty.setIsDeleted(true);
            devicePropertyMapper.updateByPrimaryKeySelective(deviceProperty);
        }
    }

    @Override
    public List<DeviceProperty> getReadPropertyListByTemplateId(String templateId) {
        DeviceProperty deviceProperty = new DeviceProperty();
        deviceProperty.setIsDeleted(false);
        deviceProperty.setTemplateId(templateId);
        return devicePropertyMapper.select(deviceProperty);
    }

    @Override
    public List<DeviceProperty> getReadPropertyListByDeviceId(String deviceId) {
        Device device = deviceService.getDeviceById(deviceId);
        if (device == null || device.getTemplateId() == null) {
            return null;
        }
        DeviceProperty deviceProperty = new DeviceProperty();
        deviceProperty.setIsDeleted(false);
        deviceProperty.setTemplateId(device.getTemplateId());
        return devicePropertyMapper.select(deviceProperty);
    }
}
