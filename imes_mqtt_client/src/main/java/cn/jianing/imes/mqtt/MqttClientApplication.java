package cn.jianing.imes.mqtt;

import cn.jianing.imes.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "cn.jianing.imes")
@EnableFeignClients
@EnableEurekaClient
@EnableDiscoveryClient
public class MqttClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqttClientApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1,1);
    }
}

