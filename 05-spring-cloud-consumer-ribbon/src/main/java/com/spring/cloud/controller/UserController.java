package com.spring.cloud.controller;


import com.spring.cloud.config.ProviderServiceConfig;
import com.spring.cloud.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author liuyi
 * @date 2019/7/23
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ProviderServiceConfig providerServiceConfig;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("{id}")
    public User findById(@PathVariable Long id) {
        return this.restTemplate.getForObject(providerServiceConfig.getUrl() + id, User.class);
    }

    @GetMapping("/user-instance")
    public void showInfo() {
        ServiceInstance serviceInstance = this.loadBalancerClient.choose("05-spring-cloud-provider");
        logger.info("{}:{}:{}", serviceInstance.getServiceId(),serviceInstance.getHost(), serviceInstance.getPort());
    }



}
