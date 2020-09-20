package cn.jianing.imes.device.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.device.mapper.DeviceMapper;
import cn.jianing.imes.device.service.DeviceService;
import cn.jianing.imes.domain.device.Device;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Resource
    private IdWorker idWorker;

    @Resource
    private DeviceMapper deviceMapper;

    @Override
    public List<Device> getDeviceListByCompanyId(String companyId) {
        Device device = new Device();
        device.setIsDeleted(false);
        device.setCompanyId(companyId);
        return deviceMapper.select(device);
    }

    @Override
    public Device getDeviceById(String id) {
        return deviceMapper.selectByPrimaryKey(id);
    }

    @Override
    public Page<Device> getDevicePageByCompanyId(int current, int pageSize, String companyId, Map<String, Object> map) {
        PageHelper.startPage(current, pageSize);
        Example example = new Example(Device.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("companyId", companyId);
        criteria.andEqualTo("isDeleted", false);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("key")) {
                criteria.orLike("name", "%" + entry.getValue() + "%").orLike("code", "%" + entry.getValue() + "%").orLike("usage", "%" + entry.getValue() + "%");
            }
        }
        return (Page<Device>)deviceMapper.selectByExample(example);
    }

    @Override
    public void insertDevice(Device device) {
        device.setId(String.valueOf(idWorker.nextId()));
        device.setIsDeleted(false);
        deviceMapper.insertSelective(device);
    }

    @Override
    public void updateDeviceById(Device device) {
        deviceMapper.updateByPrimaryKeySelective(device);
    }

    @Override
    public void deleteDeviceById(String id) {
        Device device = deviceMapper.selectByPrimaryKey(id);
        device.setIsDeleted(false);
        deviceMapper.updateByPrimaryKeySelective(device);
    }
}
