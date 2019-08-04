package com.spring.cloud.controller;


import com.spring.cloud.config.ProviderServiceConfig;
import com.spring.cloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author liuyi
 * @date 2019/7/23
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ProviderServiceConfig providerServiceConfig;
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("{id}")
    public User findById(@PathVariable Long id) {
        return this.restTemplate.getForObject(providerServiceConfig.getUrl() + id, User.class);
    }

    @GetMapping("/user-instance")
    public List<ServiceInstance> showInfo() {
        return this.discoveryClient.getInstances("04-spring-cloud-provider-my-metadata");
    }


}
