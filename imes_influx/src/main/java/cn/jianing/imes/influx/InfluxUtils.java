package cn.jianing.imes.influx;

import org.influxdb.InfluxDB;

public class InfluxUtils {

    private static InfluxDB influxDB;
    private String database;
    private String retentionPolicy;
    private String measurement;
    private String topic;
    private String valueKey;

    /**
     * 实例化
     * @param influxDB static 只有一个
     * @param database 数据库名称：telegraf
     * @param retentionPolicy autogen
     * @param measurement mqtt_consumer
     */
    public InfluxUtils(InfluxDB influxDB, String database, String retentionPolicy, String measurement) {
        InfluxUtils.influxDB = influxDB;
        this.database = database;
        this.retentionPolicy = retentionPolicy;
        this.measurement = measurement;
    }

//    public String getCurrent(String topic, String valueKey) {
//
//    }


}
