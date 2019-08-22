package com.spring.cloud.repository;


import com.spring.cloud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author liuyi
 * @date 2019/7/23
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}




