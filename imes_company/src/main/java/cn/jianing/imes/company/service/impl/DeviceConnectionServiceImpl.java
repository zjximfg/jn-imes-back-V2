package cn.jianing.imes.company.service.impl;

import cn.jianing.imes.company.mapper.DeviceConnectionMapper;
import cn.jianing.imes.company.service.DeviceConnectionService;
import cn.jianing.imes.domain.company.DeviceConnection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DeviceConnectionServiceImpl implements DeviceConnectionService {

    @Resource
    private DeviceConnectionMapper deviceConnectionMapper;

    @Override
    public void updateDeviceConnectionByCompanyId(DeviceConnection deviceConnection) {
        DeviceConnection current = deviceConnectionMapper.selectByPrimaryKey(deviceConnection.getCompanyId());
        if (current == null) {
            // 新建
            deviceConnectionMapper.insertSelective(deviceConnection);
        } else {
            // 更新
            deviceConnectionMapper.updateByPrimaryKeySelective(deviceConnection);
        }
    }

    @Override
    public DeviceConnection getDeviceConnectionByCompanyId(String companyId) {
        return deviceConnectionMapper.selectByPrimaryKey(companyId);
    }
}
