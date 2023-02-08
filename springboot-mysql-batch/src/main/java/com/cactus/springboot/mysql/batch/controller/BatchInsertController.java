package com.cactus.springboot.mysql.batch.controller;

import com.cactus.springboot.mysql.batch.entity.QmItems;
import com.cactus.springboot.mysql.batch.service.BatchInsertService;
import com.cactus.springboot.mysql.batch.utils.DataUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * MySQL批量插入
 * <p>
 * 耗时：单条sql-自动提交事务（12.52s） > 单条sql-手动提交事务（2.3s） > sql拼接-自动提交事务（0.24s） > sql拼接-手动提交事务（0.23s） > list分片-sql拼接-手动提交事务（0.2s）
 */
@RestController
@RequestMapping("/insert")
public class BatchInsertController {

    @Resource
    private BatchInsertService batchInsertService;

    /**
     * 批量插入：for循环插入（单条），自动提交事务
     *
     * @param size 数据量
     * @return 操作信息
     */
    @GetMapping("/forSingle")
    public String forSingle(int size) {
        List<QmItems> list = DataUtil.createList(size, 1000);
        return batchInsertService.forSingle(list);
    }

    /**
     * 批量插入：for循环插入（单条），手动提交事务
     *
     * @param size 数据量
     * @return 操作信息
     */
    @GetMapping("/forSingleManualCommit")
    public String forSingleManualCommit(int size) throws SQLException {
        List<QmItems> list = DataUtil.createList(size, 1000);
        return batchInsertService.forSingleManualCommit(list);
    }

    /**
     * 批量插入：sql拼接，自动提交事务
     *
     * @param size 数据量
     * @return 操作信息
     */
    @GetMapping("/sqlForeach")
    public String sqlForeach(int size) {
        List<QmItems> list = DataUtil.createList(size, 1000);
        return batchInsertService.sqlForeach(list);
    }

    /**
     * 批量插入：sql拼接，手动提交事务
     *
     * @param size 数据量
     * @return 操作信息
     */
    @GetMapping("/sqlForeachManualCommit")
    public String sqlForeachManualCommit(int size) throws SQLException {
        List<QmItems> list = DataUtil.createList(size, 1000);
        return batchInsertService.sqlForeachManualCommit(list);
    }

    /**
     * 批量插入：list分片，sql拼接，手动提交事务
     *
     * @param size 数据量
     * @return 操作信息
     */
    @GetMapping("/splitListSqlForeachManualCommit")
    public String splitListSqlForeachManualCommit(int size) throws SQLException {
        List<QmItems> list = DataUtil.createList(size, 1000);
        return batchInsertService.splitListSqlForeachManualCommit(list);
    }

}
