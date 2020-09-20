package cn.jianing.imes.device.service;

import cn.jianing.imes.domain.device.DeviceDaily;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public interface DeviceDailyService {
    List<DeviceDaily> getDeviceDailyList(String deviceId, int year, int month);

    DeviceDaily getDeviceDailyByDeviceIdAndDate(String deviceId, String date) throws ParseException;

    DeviceDaily getDeviceDailyByDeviceIdAndDate(String deviceId, Date date) throws ParseException;

    void updateDeviceDailyById(DeviceDaily deviceDaily);

    void insertDeviceDaily(DeviceDaily deviceDaily);
}
