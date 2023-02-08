package com.cactus.springboot.mysql.batch.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 评分项表(QmItems)实体类
 *
 * @author yejx
 * @since 2023-02-08 15:14:33
 */
@Data
public class QmItems implements Serializable {

    private static final long serialVersionUID = 883100555217192363L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 评分模板ID
     */
    private Long standardId;

    /**
     * 评分项名称
     */
    private String itemsName;

    /**
     * 评分项分值
     */
    private BigDecimal itemsScore;

    /**
     * 评分类型：0-加分项  1-减分项
     */
    private Integer itemsType;

    /**
     * 是否已被用作评分：0-未使用 1-已使用
     */
    private Integer useFlag;

    /**
     * 是否删除：0-否 1-是
     */
    private Integer deleteFlag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 备用字段1
     */
    private String field1;

    /**
     * 备用字段2
     */
    private String field2;

    /**
     * 备用字段3
     */
    private String field3;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 省份ID
     */
    private String proId;

    /**
     * 部门ID
     */
    private String orgId;

    /**
     * 创建人账号
     */
    private String createUser;

    /**
     * 创建人姓名
     */
    private String createName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人账号
     */
    private String updateUser;

    /**
     * 修改人姓名
     */
    private String updateName;

    /**
     * 修改时间
     */
    private Date updateTime;

}

