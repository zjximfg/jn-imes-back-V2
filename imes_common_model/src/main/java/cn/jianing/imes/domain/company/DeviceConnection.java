package cn.jianing.imes.domain.company;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "co_device_connection")
public class DeviceConnection {

    @Id
    private String companyId;

    private String influxIp;

    private Integer influxPort;

    private String influxRetentionPolicy;

    private String influxDatabase;

    private String mqttServiceIp;

    private Integer mqttServicePort;

}
