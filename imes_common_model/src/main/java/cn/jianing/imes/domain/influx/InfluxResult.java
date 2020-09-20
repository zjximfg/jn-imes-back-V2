package cn.jianing.imes.domain.influx;

import lombok.Data;

import java.util.List;

@Data
public class InfluxResult {

    private Integer statment_id;

    private List<InfluxSerie> series;
}
