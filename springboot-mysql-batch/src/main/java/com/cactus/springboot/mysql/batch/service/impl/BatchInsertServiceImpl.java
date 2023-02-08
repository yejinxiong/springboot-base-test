package com.cactus.springboot.mysql.batch.service.impl;

import com.cactus.springboot.mysql.batch.entity.QmItems;
import com.cactus.springboot.mysql.batch.mapper.QmItemsMapper;
import com.cactus.springboot.mysql.batch.service.BatchInsertService;
import com.cactus.springboot.mysql.batch.utils.ExecutorConfig;
import com.cactus.springboot.mysql.batch.utils.SqlContext;
import org.apache.commons.collections4.ListUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 评分项表(QmItems)表服务实现类
 *
 * @author yejx
 * @since 2023-02-08 15:14:38
 */
@Service("qmItemsService")
public class BatchInsertServiceImpl implements BatchInsertService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchInsertServiceImpl.class);

    @Resource
    private QmItemsMapper qmItemsMapper;

    @Resource
    private SqlContext sqlContext;

    /**
     * 批量插入：for循环插入（单条），自动提交事务
     *
     * @param itemsList 数据集
     * @return 操作信息
     */
    @Override
    public String forSingle(List<QmItems> itemsList) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String result = "成功";
        int count = 0;
        for (QmItems qmItems : itemsList) {
            int insertCount = qmItemsMapper.insert(qmItems);
            count += insertCount;
        }
        if (count != itemsList.size()) {
            // 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = "失败";
        }
        stopWatch.stop();
        return String.format("操作类型：批量插入：for循环插入（单条），自动提交事务<br/>操作数量：%s条<br/>操作耗时：%s秒<br/>操作结果：%s", itemsList.size(), stopWatch.getTotalTimeSeconds(), result);
    }

    /**
     * 批量插入：for循环插入（单条），手动提交事务
     *
     * @param itemsList 数据集
     * @return 操作信息
     */
    @Override
    public String forSingleManualCommit(List<QmItems> itemsList) throws SQLException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 获取数据库连接，获取会话(内部自有事务)
        SqlSession sqlSession = sqlContext.getSqlSession();
        Connection connection = sqlSession.getConnection();
        String result = "成功";
        try {
            // 关闭自动提交
            connection.setAutoCommit(false);
            // 获取mapper
            QmItemsMapper mapper = sqlSession.getMapper(QmItemsMapper.class);
            for (QmItems qmItems : itemsList) {
                int insertCount = mapper.insert(qmItems);
                if (insertCount != 1) {
                    connection.rollback();
                    result = "失败";
                }
            }
            // 手动提交事务
            connection.commit();
            stopWatch.stop();
            return String.format("操作类型：批量插入：for循环插入（单条），手动提交事务<br/>操作数量：%s条<br/>操作耗时：%s秒<br/>操作结果：%s", itemsList.size(), stopWatch.getTotalTimeSeconds(), result);
        } catch (SQLException e) {
            // 事务回滚
            connection.rollback();
            throw new RuntimeException("操作失败：" + e.getMessage());
        } finally {
            connection.close();
        }
    }

    /**
     * 批量插入：sql拼接，自动提交事务
     *
     * @param itemsList 数据集
     * @return 操作信息
     */
    @Override
    public String sqlForeach(List<QmItems> itemsList) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String result = "成功";
        int count = qmItemsMapper.batchInsert(itemsList);
        if (count != itemsList.size()) {
            // 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = "失败";
        }
        stopWatch.stop();
        return String.format("操作类型：批量插入：sql拼接，自动提交事务<br/>操作数量：%s条<br/>操作耗时：%s秒<br/>操作结果：%s", itemsList.size(), stopWatch.getTotalTimeSeconds(), result);
    }

    /**
     * 批量插入：sql拼接，手动提交事务
     *
     * @param itemsList 数据集
     * @return 操作信息
     */
    @Override
    public String sqlForeachManualCommit(List<QmItems> itemsList) throws SQLException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 获取数据库连接，获取会话(内部自有事务)
        SqlSession sqlSession = sqlContext.getSqlSession();
        Connection connection = sqlSession.getConnection();
        String result = "成功";
        try {
            // 关闭自动提交
            connection.setAutoCommit(false);
            // 获取mapper
            QmItemsMapper mapper = sqlSession.getMapper(QmItemsMapper.class);
            int batchInsertCount = mapper.batchInsert(itemsList);
            if (batchInsertCount != itemsList.size()) {
                connection.rollback();
                result = "失败";
            }
            // 手动提交事务
            connection.commit();
            stopWatch.stop();
            return String.format("操作类型：批量插入：sql拼接，手动提交事务<br/>操作数量：%s条<br/>操作耗时：%s秒<br/>操作结果：%s", itemsList.size(), stopWatch.getTotalTimeSeconds(), result);
        } catch (SQLException e) {
            // 事务回滚
            connection.rollback();
            throw new RuntimeException("操作失败：" + e.getMessage());
        } finally {
            connection.close();
        }
    }

    /**
     * 批量插入：list分片，sql拼接，自动提交事务
     *
     * @param itemsList 数据集
     * @return 操作信息
     */
    @Override
    public String splitListSqlForeach(List<QmItems> itemsList) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String result = "成功";
        // list分片：每个小集合的容量为5
        List<List<QmItems>> list = ListUtils.partition(itemsList, 500);
        // 收集子线程
        List<Callable<Integer>> callableList = new ArrayList<>();
        // 生成子线程任务
        for (List<QmItems> items : list) {
            int batchInsertCount = qmItemsMapper.batchInsert(items);
            if (batchInsertCount != items.size()) {
                // 手动回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                result = "失败";
            }
        }
        stopWatch.stop();
        return String.format("操作类型：批量插入<br/>操作数量：%s条<br/>操作耗时：%s秒<br/>操作结果：%s", itemsList.size(), stopWatch.getTotalTimeSeconds(), result);
    }

    /**
     * 批量插入：list分片，sql拼接，手动提交事务
     * 通过多线程异步处理多个小list，并通过SqlSession控制事务
     *
     * @param itemsList 数据集
     * @return 操作信息
     */
    @Override
    public String splitListSqlForeachManualCommit(List<QmItems> itemsList) throws SQLException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 获取数据库连接，获取会话(内部自有事务)
        SqlSession sqlSession = sqlContext.getSqlSession();
        Connection connection = sqlSession.getConnection();
        try {
            // 关闭自动提交
            connection.setAutoCommit(false);
            // 主线程：删除操作
            QmItemsMapper mapper = sqlSession.getMapper(QmItemsMapper.class);
            // 获取线程池
            ExecutorService executorService = ExecutorConfig.getThreadPool();
            // list分片：每个小集合的容量为5
            List<List<QmItems>> list = ListUtils.partition(itemsList, 500);
            // 收集子线程
            List<Callable<Integer>> callableList = new ArrayList<>();
            // 生成子线程任务
            for (List<QmItems> items : list) {
                Callable<Integer> callable = () -> mapper.batchInsert(items);
                callableList.add(callable);
            }
            // 执行子线程
            List<Future<Integer>> futures = executorService.invokeAll(callableList);
            String result = "成功";
            for (int i = 0; i < futures.size(); i++) {
                if (futures.get(i).get() <= 0) {
                    LOGGER.error("任务【{}】执行失败", i + 1);
                    // 事务回滚
                    connection.rollback();
                    result = "失败";
                    break;
                } else {
                    LOGGER.info("任务【{}】执行完成", i + 1);
                }
            }
            // 手动提交事务
            connection.commit();
            LOGGER.warn("事务提交完毕");
            stopWatch.stop();
            return String.format("操作类型：批量插入<br/>操作数量：%s条<br/>操作耗时：%s秒<br/>操作结果：%s", itemsList.size(), stopWatch.getTotalTimeSeconds(), result);
        } catch (Exception e) {
            // 事务回滚
            connection.rollback();
            throw new RuntimeException("操作失败：" + e.getMessage());
        } finally {
            connection.close();
        }
    }

}
