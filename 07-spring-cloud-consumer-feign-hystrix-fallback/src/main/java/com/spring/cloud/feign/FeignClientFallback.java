package com.spring.cloud.feign;

import com.spring.cloud.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author liuyi
 * @date 2019/8/11
 */
@Component
public class FeignClientFallback implements UserFeignClient {

    @Override
    public User findById(Long id) {
        User user = new User();
        user.setId(-1L);
        user.setUsername("默认用户");
        return user;
    }
}
