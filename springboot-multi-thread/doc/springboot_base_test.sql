/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : 127.0.0.1:3306
 Source Schema         : springboot_base_test

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 04/02/2023 18:39:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_qm_items
-- ----------------------------
DROP TABLE IF EXISTS `tbl_qm_items`;
CREATE TABLE `tbl_qm_items`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `standard_id` bigint(0) NULL DEFAULT NULL COMMENT '评分模板ID',
  `items_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评分项名称',
  `items_score` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评分项分值',
  `items_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评分类型（0：加分项 1：减分项）',
  `use_flag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否已被用作评分（0：未使用 1：已使用）',
  `delete_flag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否删除（0：否 1：是）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备用字段1',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备用字段2',
  `field3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备用字段3',
  `tenant_id` int(0) NULL DEFAULT NULL COMMENT '租户ID',
  `pro_id` int(0) NULL DEFAULT NULL COMMENT '省份ID',
  `org_id` int(0) NULL DEFAULT NULL COMMENT '部门ID',
  `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人账号',
  `create_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` datetime(0) NULL DEFAULT NULL COMMENT '修改人账号',
  `update_name` datetime(0) NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评分项表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_qm_items
-- ----------------------------
INSERT INTO `tbl_qm_items` VALUES (1, NULL, '测试', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-03 17:06:45', NULL, NULL, '2023-02-03 17:06:45');

SET FOREIGN_KEY_CHECKS = 1;
