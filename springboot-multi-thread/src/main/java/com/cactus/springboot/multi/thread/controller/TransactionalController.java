package com.cactus.springboot.multi.thread.controller;

import com.cactus.springboot.multi.thread.entity.QmItems;
import com.cactus.springboot.multi.thread.service.QmItemsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transactional")
public class TransactionalController {

    @Resource
    private QmItemsService qmItemsService;

    /**
     * 多线程事务失败
     */
    @GetMapping("/fail")
    public void transactionalFail() {
        List<QmItems> list = this.createList(16);
        qmItemsService.transactionalFail(1L, list);
    }

    /**
     * 多线程事务成功
     */
    @GetMapping("/success")
    public void transactionalSuccess() throws SQLException {
        List<QmItems> list = this.createList(16);
        qmItemsService.transactionalSuccess(1L, list);
    }


    /**************************************************** 公共方法 ****************************************************/

    /**
     * 创建测试数据
     *
     * @param size 集合长度
     * @return 集合
     */
    private List<QmItems> createList(int size) {
        List<QmItems> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            QmItems qmItems = new QmItems();
            qmItems.setItemsName("标签-" + (i + 2));
            list.add(qmItems);
        }
        return list;
    }

}
