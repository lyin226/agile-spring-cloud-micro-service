package com.spring.cloud.feign;

import com.spring.cloud.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


public interface UserFeignClient {
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  User findById(@PathVariable("id") Long id);
}
