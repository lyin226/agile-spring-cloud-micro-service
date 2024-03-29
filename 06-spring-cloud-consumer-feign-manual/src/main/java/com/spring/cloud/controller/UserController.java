package com.spring.cloud.controller;

import com.spring.cloud.entity.User;
import com.spring.cloud.feign.UserFeignClient;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@Import(FeignClientsConfiguration.class)
@RestController
public class UserController {
  private UserFeignClient userUserFeignClient;

  private UserFeignClient adminUserFeignClient;

  @Autowired
  public UserController(Decoder decoder, Encoder encoder, Client client, Contract contract) {
    // 这边的decoder、encoder、client、contract，可以debug看看是什么实例。
    this.userUserFeignClient = Feign.builder().client(client).encoder(encoder).decoder(decoder).contract(contract)
            .requestInterceptor(new BasicAuthRequestInterceptor("user", "password1")).target(UserFeignClient.class, "http://06-spring-cloud-provider/");
    this.adminUserFeignClient = Feign.builder().client(client).encoder(encoder).decoder(decoder).contract(contract)
            .requestInterceptor(new BasicAuthRequestInterceptor("admin", "password2"))
            .target(UserFeignClient.class, "http://06-spring-cloud-provider/");
  }

  @GetMapping("/user-user/{id}")
  public User findByIdUser(@PathVariable Long id) {
    return this.userUserFeignClient.findById(id);
  }

  @GetMapping("/user-admin/{id}")
  public User findByIdAdmin(@PathVariable Long id) {
    return this.adminUserFeignClient.findById(id);
  }
}
