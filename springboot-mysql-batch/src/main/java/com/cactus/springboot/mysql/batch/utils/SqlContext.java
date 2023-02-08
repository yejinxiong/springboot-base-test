package com.cactus.springboot.mysql.batch.utils;

import org.apache.ibatis.session.ExecutorType;
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
     * 获取SqlSession
     *
     * @return SqlSession
     */
    public SqlSession getSqlSession() {
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        return sqlSessionFactory.openSession();
    }

    /**
     * 获取SqlSession（批量处理，非自动提交事务）
     *
     * @return SqlSession
     */
    public SqlSession getSqlSessionNotAutoCommit() {
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        // 开启批量处理模式、关闭自动提交事务
        return sqlSessionFactory.openSession(ExecutorType.BATCH, false);
    }

}
