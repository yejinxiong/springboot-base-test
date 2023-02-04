package com.cactus.springboot.multi.thread.utils;

import com.cactus.springboot.multi.thread.entity.QmItems;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据工具类
 *
 * @author yejx
 * @since 2023-02-04 21:59:38
 */
public class DataUtil {

    /**
     * 创建测试数据
     *
     * @param size 集合长度
     * @return 集合
     */
    public static List<QmItems> createList(int size) {
        size = size <= 0 ? 10 : size;
        List<QmItems> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            QmItems qmItems = new QmItems();
            qmItems.setItemsName("标签-" + (i + 2));
            list.add(qmItems);
        }
        return list;
    }

}
