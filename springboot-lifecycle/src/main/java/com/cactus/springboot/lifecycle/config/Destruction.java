package com.cactus.springboot.lifecycle.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

/**
 * Spring生命周期：销毁
 */
@Configuration
public class Destruction implements DisposableBean {

    /**
     * 方案一：实现DisposableBean接口
     *
     * @throws Exception 异常
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("Spring生命周期（销毁），方案一：实现DisposableBean接口");
    }

    /**
     * 方案二：使用注解@PreDestroy
     */
    @PreDestroy
    public void des() {
        System.out.println("Spring生命周期（销毁），方案二：使用注解@PreDestroy");
    }
}
