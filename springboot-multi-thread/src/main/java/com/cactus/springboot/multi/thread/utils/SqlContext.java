package com.cactus.springboot.multi.thread.utils;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author yejx
 * @Date 2023/2/3
 */
@Component
public class SqlContext {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 获取sql会话
     *
     * @return sql会话
     */
    public SqlSession getSqlSession() {
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        return sqlSessionFactory.openSession();
    }

}
