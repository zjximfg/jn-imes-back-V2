package cn.jianing.imes.device.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.device.mapper.DeviceSupplierMapper;
import cn.jianing.imes.device.service.DeviceSupplierService;
import cn.jianing.imes.domain.device.DeviceSupplier;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class DeviceSupplierServiceImpl implements DeviceSupplierService {

    @Resource
    private IdWorker idWorker;

    @Resource
    private DeviceSupplierMapper deviceSupplierMapper;

    @Override
    public List<DeviceSupplier> getDeviceSupplierListByCompanyId(String companyId) {
        DeviceSupplier deviceSupplier = new DeviceSupplier();
        deviceSupplier.setIsDeleted(false);
        deviceSupplier.setCompanyId(companyId);
        return deviceSupplierMapper.select(deviceSupplier);
    }

    @Override
    public DeviceSupplier getDeviceSupplierById(String id) {
        return deviceSupplierMapper.selectByPrimaryKey(id);
    }

    @Override
    public Page<DeviceSupplier> getDeviceSupplierPageByCompanyId(int current, int pageSize, String companyId, Map<String, Object> map) {
        PageHelper.startPage(current, pageSize);
        Example example = new Example(DeviceSupplier.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("companyId", companyId);
        criteria.andEqualTo("isDeleted", false);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("key")) {
                criteria.orLike("name", "%" + entry.getValue() + "%").orLike("code", "%" + entry.getValue() + "%").orLike("shortName", "%" + entry.getValue() + "%");
            }
        }
        return (Page<DeviceSupplier>)deviceSupplierMapper.selectByExample(example);
    }

    @Override
    public void insertDeviceSupplier(DeviceSupplier deviceSupplier) {
        deviceSupplier.setId(String.valueOf(idWorker.nextId()));
        deviceSupplier.setIsDeleted(false);
        deviceSupplierMapper.insertSelective(deviceSupplier);
    }

    @Override
    public void updateDeviceSupplierById(DeviceSupplier deviceSupplier) {
        deviceSupplierMapper.updateByPrimaryKeySelective(deviceSupplier);
    }

    @Override
    public void deleteDeviceSupplierById(String id) {
        DeviceSupplier deviceSupplier = deviceSupplierMapper.selectByPrimaryKey(id);
        deviceSupplier.setIsDeleted(false);
        deviceSupplierMapper.updateByPrimaryKeySelective(deviceSupplier);
    }
}
