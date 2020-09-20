package cn.jianing.imes.domain.mqtt;

import lombok.Data;

@Data
public class Payload {

    private cn.jianing.imes.domain.mqtt.Data data;

    private String time;
}
