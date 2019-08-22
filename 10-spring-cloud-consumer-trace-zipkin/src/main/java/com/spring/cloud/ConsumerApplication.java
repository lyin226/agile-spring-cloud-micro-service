package com.spring.cloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author liuyi
 * @date 2019/7/23
 */

@SpringBootApplication
public class ConsumerApplication {

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder rest) {
    return rest.build();
  }

  public static void main(String[] args) {
    SpringApplication.run(ConsumerApplication.class, args);
  }
}
