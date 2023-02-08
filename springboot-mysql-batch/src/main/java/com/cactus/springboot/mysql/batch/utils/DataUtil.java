package com.cactus.springboot.mysql.batch.utils;

import com.cactus.springboot.mysql.batch.entity.QmItems;

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
            qmItems.setItemsName("标签-" + (i + 1));
            list.add(qmItems);
        }
        return list;
    }

}
