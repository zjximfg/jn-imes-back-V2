package cn.jianing.imes.company.service;

import cn.jianing.imes.domain.company.DeviceConnection;
import org.springframework.stereotype.Service;

@Service
public interface DeviceConnectionService {
    void updateDeviceConnectionByCompanyId(DeviceConnection deviceConnection);

    DeviceConnection getDeviceConnectionByCompanyId(String companyId);
}
