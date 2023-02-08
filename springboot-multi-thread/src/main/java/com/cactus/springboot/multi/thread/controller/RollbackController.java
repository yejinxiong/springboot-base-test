package com.cactus.springboot.multi.thread.controller;

import com.cactus.springboot.multi.thread.entity.QmItems;
import com.cactus.springboot.multi.thread.service.RollbackService;
import com.cactus.springboot.multi.thread.utils.DataUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * 事务回滚
 *
 * @author yejx
 * @since 2023-02-04 21:59:38
 */
@RestController
@RequestMapping("/rollback")
public class RollbackController {

    @Resource
    private RollbackService rollbackService;

    /**
     * 多线程事务失败
     */
    @GetMapping("/fail")
    public void transactionalFail() {
        List<QmItems> list = DataUtil.createList(16);
        rollbackService.transactionalFail(1L, list);
    }

    /**
     * 多线程事务成功
     */
    @GetMapping("/success")
    public void transactionalSuccess() throws SQLException {
        List<QmItems> list = DataUtil.createList(16);
        rollbackService.transactionalSuccess(1L, list);
    }

}
