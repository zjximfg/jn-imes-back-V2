package cn.jianing.imes.common.utils;

import cn.jianing.imes.domain.influx.InfluxResult;
import cn.jianing.imes.domain.influx.InfluxResults;
import cn.jianing.imes.domain.influx.InfluxSerie;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfluxUtils {
    private String baseUrl;
    private String database;
    private String retentionPolicy;
    private String measurement;


    /**
     * 实例化
     * @param baseUrl 数据库的url 默认为： http://182.92.194.48:8806
     * @param database 数据库名称：telegraf
     * @param retentionPolicy autogen
     * @param measurement mqtt_consumer
     */
    public InfluxUtils(String baseUrl, String database, String retentionPolicy, String measurement) {
        this.baseUrl = baseUrl;
        this.database = database;
        this.retentionPolicy = retentionPolicy;
        this.measurement = measurement;
    }

    public String getCurrentValue(String topic, String valueKey) {

        RestTemplate restTemplate = new RestTemplate();

        String query = "select " + valueKey +" from " + this.database + "." + this.retentionPolicy + "." + this.measurement + " where topic='" + topic + "' order by time desc limit 1";

        // pretty = true 表示返回的是json格式数据
        String url = baseUrl + "/query?pretty=true&q=" + query;

        String objectStr = restTemplate.getForObject(url, String.class);

        InfluxResults influxResults = JSONObject.parseObject(objectStr, InfluxResults.class);

        if (influxResults == null) return "";

        List<InfluxResult> results = influxResults.getResults();

        String value = "";

        for (InfluxSerie serie : results.get(0).getSeries()) {
            for (List<String> serieValue : serie.getValues()) {
                int valueIndex = 0;
                for (int i = 0; i < serie.getColumns().size(); i++) {
                    if (serie.getColumns().get(i).equals("value")) {
                        valueIndex = i;
                    }
                }
                value = serieValue.get(valueIndex);
                return value;
            }
        }
        return value;
    }

    public Map<String, String> getCurrentMap(String topic, String valueKey) {

        RestTemplate restTemplate = new RestTemplate();

        String query = "select " + valueKey +" from " + this.database + "." + this.retentionPolicy + "." + this.measurement + " where topic='" + topic + "' order by time desc limit 1";

        // pretty = true 表示返回的是json格式数据
        String url = baseUrl + "/query?pretty=true&q=" + query;

        String objectStr = restTemplate.getForObject(url, String.class);

        InfluxResults influxResults = JSONObject.parseObject(objectStr, InfluxResults.class);

        if (influxResults == null) return null;

        List<InfluxResult> results = influxResults.getResults();

        Map<String, String> resultMap = new HashMap<>();

        for (int i = 0; i < results.get(0).getSeries().get(0).getColumns().size(); i++) {
            resultMap.put(results.get(0).getSeries().get(0).getColumns().get(i), results.get(0).getSeries().get(0).getValues().get(0).get(i));
        }
        return resultMap;
    }

}
