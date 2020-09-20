package cn.jianing.imes.domain.influx;

import lombok.Data;

import java.util.List;

@Data
public class InfluxResults {

    private List<InfluxResult> results;
}
