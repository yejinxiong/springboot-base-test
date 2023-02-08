package com.cactus.springboot.mysql.batch.mapper;

import com.cactus.springboot.mysql.batch.entity.QmItems;

import java.util.List;

/**
 * 评分项表(QmItems)表数据库访问层
 *
 * @author yejx
 * @since 2023-02-08 15:14:28
 */
public interface QmItemsMapper {

    /**
     * 新增
     *
     * @param item 数据
     * @return 影响行数
     */
    int insert(QmItems item);

    /**
     * 批量插入
     *
     * @param list 数据源
     * @return 影响行数
     */
    int batchInsert(List<QmItems> list);
}

