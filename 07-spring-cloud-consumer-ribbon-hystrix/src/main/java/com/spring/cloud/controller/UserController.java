package com.spring.cloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 * @author liuyi
 * @date 2019/8/19
 */

@RestController
public class UserController {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private LoadBalancerClient loadBalancerClient;

  @HystrixCommand(fallbackMethod = "findByIdFallback")
  @GetMapping("/user/{id}")
  public User findById(@PathVariable Long id) {
    return this.restTemplate.getForObject("http://05-spring-cloud-provider/" + id, User.class);
  }

  public User findByIdFallback(Long id) {
    User user = new User();
    user.setId(-1L);
    user.setName("默认用户");
    return user;
  }

  @GetMapping("/log-user-instance")
  public void logUserInstance() {
    ServiceInstance serviceInstance = this.loadBalancerClient.choose("05-spring-cloud-provider");
    // 打印当前选择的是哪个节点
    UserController.LOGGER.info("{}:{}:{}", serviceInstance.getServiceId(), serviceInstance.getHost(), serviceInstance.getPort());
  }
}
