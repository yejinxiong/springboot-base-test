package com.cactus.springboot.mysql.batch.service;

import com.cactus.springboot.mysql.batch.entity.QmItems;

import java.sql.SQLException;
import java.util.List;

/**
 * 评分项表(QmItems)表服务接口
 *
 * @author yejx
 * @since 2023-02-08 15:14:35
 */
public interface BatchInsertService {

    /**
     * 批量插入：for循环插入（单条），自动提交事务
     *
     * @param itemsList 数据集
     * @return 操作信息
     */
    String forSingle(List<QmItems> itemsList);

    /**
     * 批量插入：for循环插入（单条），手动提交事务
     *
     * @param itemsList 数据集
     * @return 操作信息
     */
    String forSingleManualCommit(List<QmItems> itemsList) throws SQLException;

    /**
     * 批量插入：sql拼接，自动提交事务
     *
     * @param itemsList 数据集
     * @return 操作信息
     */
    String sqlForeach(List<QmItems> itemsList);

    /**
     * 批量插入：sql拼接，手动提交事务
     *
     * @param itemsList 数据集
     * @return 操作信息
     */
    String sqlForeachManualCommit(List<QmItems> itemsList) throws SQLException;

    /**
     * 批量插入：list分片，sql拼接，自动提交事务
     *
     * @param itemsList 数据集
     * @return 操作信息
     */
    String splitListSqlForeach(List<QmItems> itemsList);

    /**
     * 批量插入：list分片，sql拼接，手动提交事务
     *
     * @param itemsList 数据集
     * @return 操作信息
     */
    String splitListSqlForeachManualCommit(List<QmItems> itemsList) throws SQLException;

}
