package com.cactus.springboot.lifecycle.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Spring生命周期：初始化
 */
@Configuration
public class Initialization implements InitializingBean {

    /**
     * 方案一：实现InitializingBean接口
     *
     * @throws Exception 异常
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Spring生命周期（初始化），方案一：实现InitializingBean接口");
    }

    /**
     * 方案二：使用注解@PostConstruct
     */
    @PostConstruct
    public void init() {
        System.out.println("Spring生命周期（初始化），方案二：使用注解@PostConstruct");
    }
}
