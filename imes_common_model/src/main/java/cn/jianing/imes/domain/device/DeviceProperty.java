package cn.jianing.imes.domain.device;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name = "de_device_property")
public class DeviceProperty {

    @Transient
    @JsonIgnore
    public static final String STRING = "string";
    @Transient
    @JsonIgnore
    public static final String INTEGER = "integer";
    @Transient
    @JsonIgnore
    public static final String UNSIGNED = "unsigned";
    @Transient
    @JsonIgnore
    public static final String BOOLEAN = "boolean";
    @Transient
    @JsonIgnore
    public static final String FLOAT = "float";

    @Id
    private String id;

    private String templateId;  // 模板id

    private String name;  // 属性名

    private String variableAddress;   // 变量地址

    private String variableType;

    private Integer category;    // 1 = platform read from gateway, 2 = platform write to gateway

    private String description;    // 描述

    @JsonIgnore
    private Boolean isDeleted;

    @Transient
    private String value;

    @Transient
    private String time;
}
