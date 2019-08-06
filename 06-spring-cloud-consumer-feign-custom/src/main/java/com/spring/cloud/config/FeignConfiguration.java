package com.spring.cloud.config;

import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyi
 * @date 2019/8/6
 *
 * 该类不应该在应用程序上下文的@ComponentScan中
 */

@Configuration
public class FeignConfiguration {

    @Bean
    public Contract feignContract() {
        return new feign.Contract.Default();
    }
}
