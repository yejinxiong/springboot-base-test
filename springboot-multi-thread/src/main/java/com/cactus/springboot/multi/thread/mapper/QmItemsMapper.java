package com.cactus.springboot.multi.thread.mapper;

import com.cactus.springboot.multi.thread.entity.QmItems;

import java.util.List;

/**
 * 评分项表(QmItems)表数据库访问层
 *
 * @author yejx
 * @since 2023-02-03 14:33:28
 */
public interface QmItemsMapper {

    /**
     * 批量插入
     *
     * @param list 数据源
     * @return 影响行数
     */
    int insertBatch(List<QmItems> list);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return 影响行数
     */
    int delete(Long id);

}

