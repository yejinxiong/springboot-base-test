package com.cactus.springboot.multi.thread.rest;

import com.cactus.springboot.multi.thread.SpringbootMultiThreadApplication;
import com.cactus.springboot.multi.thread.entity.QmItems;
import com.cactus.springboot.multi.thread.service.QmItemsService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 多线程事务测试
 *
 * @Author yejx
 * @Date 2023/2/3
 */
@SpringBootTest(classes = SpringbootMultiThreadApplication.class)
public class MultiThreadTransactionTest {

    @Resource
    private QmItemsService qmItemsService;

    /**
     * 多线程事务失败
     */
    @Test
    public void transactionalFail() {
        List<QmItems> list = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            QmItems qmItems = new QmItems();
            qmItems.setItemsName("标签-" + (i + 2));
            list.add(qmItems);
        }
        qmItemsService.transactionalFail(1L, list);
    }

    /**
     * 多线程事务成功
     */
    @Test
    public void transactionalSuccess() {
        List<QmItems> list = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            QmItems qmItems = new QmItems();
            qmItems.setItemsName("标签-" + (i + 2));
            list.add(qmItems);
        }
        qmItemsService.transactionalSuccess(1L, list);
    }
}
