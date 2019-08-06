package com.spring.cloud.feign;

import com.spring.cloud.config.FeignConfiguration;
import com.spring.cloud.entity.User;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "05-spring-cloud-provider", configuration = FeignConfiguration.class)
public interface UserFeignClient {



  @RequestLine("GET /{id}")
  User findById(@Param("id") Long id);
}
