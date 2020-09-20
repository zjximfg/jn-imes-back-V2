package cn.jianing.imes.domain.mqtt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MqttResult {

    private String id;

    private String value;

    private String time;
}
