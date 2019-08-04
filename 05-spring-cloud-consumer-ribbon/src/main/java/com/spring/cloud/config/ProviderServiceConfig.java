package com.spring.cloud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyi
 * @date 2019/7/23
 */

@Configuration
public class ProviderServiceConfig {

    @Value("${provider.url}")
    private String url;

    public String getUrl() {
        return url;
    }

}
