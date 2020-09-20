package cn.jianing.imes.warehouse;

import cn.jianing.imes.common.utils.IdWorker;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "cn.jianing.imes")  // 由于要扫描imes_common 等模块中的包，所以要配置scanBasePackages
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@EnableRabbit
public class WarehouseApplication {
    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }


    @Bean
    // 利用雪花算法的id生成器
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }
}
