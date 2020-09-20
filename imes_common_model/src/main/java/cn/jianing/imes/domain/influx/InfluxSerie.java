package cn.jianing.imes.domain.influx;

import lombok.Data;

import java.util.List;

@Data
public class InfluxSerie {

    private String name;
    private List<String> columns;
    private List<List<String>> values;
}
