package cn.jianing.imes.domain.device;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "de_device_daily")
public class DeviceDaily {

    @Id
    private String id;

    private String deviceId;

    private Double statusStopHour;

    private Double statusAwaitHour;

    private Double statusFaultHour;

    private Double statusRunHour;

    private Double yieldSummary;

    private Date date;
}
