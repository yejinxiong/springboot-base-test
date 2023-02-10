package com.cactus.springboot.mysql.batch.utils;

import com.cactus.springboot.mysql.batch.entity.QmItems;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据工具类
 *
 * @Author yejx
 * @Date 2023/2/8 15:14:16
 */
public class DataUtil {

    /**
     * 创建测试数据
     *
     * @param size        集合长度
     * @param defaultSize 默认集合长度
     * @return 集合
     */
    public static List<QmItems> createList(int size, int defaultSize) {
        size = size > 0 ? size : (defaultSize > 0 ? defaultSize : 10);
        List<QmItems> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            QmItems qmItems = new QmItems();
            qmItems.setStandardId(1L);
            qmItems.setItemsName("标签-" + (i + 1));
            qmItems.setItemsScore(new BigDecimal("1"));
            qmItems.setItemsType(1);
            qmItems.setUseFlag(0);
            qmItems.setDeleteFlag(0);
            qmItems.setRemark("问题，对于开发人员来说，是一个跟语言无关的公共问题。本文分享了一些解决这类问题非常实用的办法，绝大部分内容我在项目中实践过的，给有需要的小伙伴一个参考。");
            qmItems.setCreateUser("admin");
            qmItems.setCreateName("管理员");
            list.add(qmItems);
        }
        return list;
    }

}
