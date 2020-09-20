package cn.jianing.imes.device.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.device.mapper.DeviceDailyMapper;
import cn.jianing.imes.device.service.DeviceDailyService;
import cn.jianing.imes.domain.device.DeviceDaily;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DeviceDailyServiceImpl implements DeviceDailyService {

    @Resource
    private IdWorker idWorker;
    @Resource
    private DeviceDailyMapper deviceDailyMapper;

    @Override
    public List<DeviceDaily> getDeviceDailyList(String deviceId, int year, int month) {
        Date firstDate = getFirstDateOfMonth(year, month);
        Date lastDate = getLastDateOfMonth(year, month);
        Example example = new Example(DeviceDaily.class);
        example.createCriteria().andEqualTo("deviceId", deviceId)
                .andBetween("date", firstDate, lastDate);
        return deviceDailyMapper.selectByExample(example);
    }

    @Override
    public DeviceDaily getDeviceDailyByDeviceIdAndDate(String deviceId, String date) throws ParseException {
        Example example = new Example(DeviceDaily.class);
        example.createCriteria().andEqualTo("deviceId", deviceId)
                .andEqualTo("date", date);
        return deviceDailyMapper.selectOneByExample(example);
    }

    @Override
    public DeviceDaily getDeviceDailyByDeviceIdAndDate(String deviceId, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = simpleDateFormat.format(date);
        Example example = new Example(DeviceDaily.class);
        example.createCriteria().andEqualTo("deviceId", deviceId)
                .andEqualTo("date", dateString);
        return deviceDailyMapper.selectOneByExample(example);
    }

    @Override
    public void updateDeviceDailyById(DeviceDaily deviceDaily) {
        deviceDailyMapper.updateByPrimaryKeySelective(deviceDaily);
    }

    @Override
    public void insertDeviceDaily(DeviceDaily deviceDaily) {
        // 查找是否有该设备同一天的值
        DeviceDaily deviceDailySaved = this.getDeviceDailyByDeviceIdAndDate(deviceDaily.getDeviceId(), deviceDaily.getDate());
        if (deviceDailySaved == null) {
            // 如果没有，新建
            String id = String.valueOf(idWorker.nextId());
            deviceDaily.setId(id);
            deviceDailyMapper.insert(deviceDaily);
        } else {
            // 如果有，更新
            deviceDaily.setId(deviceDailySaved.getId());
            this.updateDeviceDailyById(deviceDaily);
        }

    }


    private Date getFirstDateOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        //设置月份
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //获取某月最小天数
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        calendar.set(Calendar.DAY_OF_MONTH, firstDay);
        return new Date(calendar.getTimeInMillis());
    }

    private Date getLastDateOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        //设置月份
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        //获取某月最大天数
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        calendar.set(Calendar.DAY_OF_MONTH, lastDay);
        return new Date(calendar.getTimeInMillis());
    }

}
