package com.cactus.springboot.multi.thread.service.impl;

import com.alibaba.fastjson.JSON;
import com.cactus.springboot.multi.thread.config.ExecutorConfig;
import com.cactus.springboot.multi.thread.entity.QmItems;
import com.cactus.springboot.multi.thread.mapper.QmItemsMapper;
import com.cactus.springboot.multi.thread.service.QmItemsService;
import com.cactus.springboot.multi.thread.web.SqlContext;
import org.apache.commons.collections4.ListUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 评分项表(QmItems)表服务实现类
 *
 * @author makejava
 * @since 2023-02-03 14:33:38
 */
@Service("qmItemsService")
public class QmItemsServiceImpl implements QmItemsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QmItemsServiceImpl.class);

    @Resource
    private QmItemsMapper qmItemsMapper;

    @Resource
    private SqlContext sqlContext;

    /**
     * 模拟事务失败
     *
     * @param id   更新ID
     * @param list 数据集
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transactionalFail(Long id, List<QmItems> list) {
        // 主线程：删除操作
        qmItemsMapper.delete(id);
        // 获取线程池
        ExecutorService executorService = ExecutorConfig.getThreadPool();
        // 拆分数据集合：每个小集合的容量为5
        List<List<QmItems>> subList = ListUtils.partition(list, 5);
        // 收集子线程
        Thread[] threadArray = new Thread[subList.size()];
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        // 生成子线程任务
        for (int i = 0; i < subList.size(); i++) {
            LOGGER.info("{}", JSON.toJSONString(subList.get(i)));
            // 记录最后一个子线程
            if (i == subList.size() - 1) {
                atomicBoolean.set(false);
            }
            List<QmItems> items = subList.get(i);
            threadArray[i] = new Thread(() -> {
                //最后一个线程抛出异常
                if (!atomicBoolean.get()) {
                    throw new RuntimeException("最后一个线程出现异常");
                }
                //批量添加
                qmItemsMapper.insertBatch(items);
            });
        }
        // 执行子线程
        for (int i = 0; i < subList.size(); i++) {
            executorService.execute(threadArray[i]);
        }
        LOGGER.warn("操作完毕");
    }

    /**
     * 事务成功
     *
     * @param id        更新ID
     * @param itemsList 数据集
     */
    @Override
    public void transactionalSuccess(Long id, List<QmItems> itemsList) {
        // 获取数据库连接，获取会话(内部自有事务)
        SqlSession sqlSession = sqlContext.getSqlSession();
        Connection connection = sqlSession.getConnection();
        try {
            // 设置手动提交
            connection.setAutoCommit(false);
            // 主线程：删除操作
            QmItemsMapper mapper = sqlSession.getMapper(QmItemsMapper.class);
            mapper.delete(id);
            // 获取线程池
            ExecutorService executorService = ExecutorConfig.getThreadPool();
            // 拆分数据集合：每个小集合的容量为5
            List<List<QmItems>> list = ListUtils.partition(itemsList, 5);
            for (List<QmItems> qmItemsList : list) {
                LOGGER.info("{}", qmItemsList);
            }
            // 收集子线程
            List<Callable<Integer>> callableList = new ArrayList<>();
            AtomicBoolean atomicBoolean = new AtomicBoolean(true);
            // 生成子线程任务
            for (int i = 0; i < list.size(); i++) {
                // 记录最后一个子线程
                if (i == list.size() - 1) {
                    atomicBoolean.set(false);
                }
                List<QmItems> items = list.get(i);
                Callable<Integer> callable = () -> {
                    // 让最后一个子线程报错
                    if (!atomicBoolean.get()) {
                        throw new Exception("最后一个任务执行失败");
                    }
                    return mapper.insertBatch(items);
                };
                callableList.add(callable);
            }
            // 执行子线程
            List<Future<Integer>> futures = executorService.invokeAll(callableList);
            for (int i = 0; i < futures.size(); i++) {
                if (futures.get(i).get() <= 0) {
                    LOGGER.error("任务【{}】执行失败", i + 1);
                    connection.rollback();
                    return;
                } else {
                    LOGGER.info("任务【{}】执行完成", i + 1);
                }
            }
            // 手动提交事务
            connection.commit();
            LOGGER.warn("事务提交完毕");
        } catch (Exception e) {
            throw new RuntimeException("操作失败：" + e.getMessage());
        }
    }

}
