/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3307
 Source Schema         : user_center

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 14/07/2023 10:03:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_client
-- ----------------------------
DROP TABLE IF EXISTS `base_client`;
CREATE TABLE `base_client`  (
                                `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                `client_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统名称',
                                `client_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统标识编码',
                                `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统跳转链接',
                                `state` int(11) NOT NULL DEFAULT 0 COMMENT '状态（0-正常 1-禁用）',
                                `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                                `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
                                `is_del` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除 0(false)-未删除  1(true)-已删除',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE INDEX `uq_key_code`(`client_code`) USING BTREE COMMENT '系统编码唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统项目表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of base_client
-- ----------------------------

-- ----------------------------
-- Table structure for base_client_user
-- ----------------------------
DROP TABLE IF EXISTS `base_client_user`;
CREATE TABLE `base_client_user`  (
                                     `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                     `client_id` bigint(11) NOT NULL COMMENT '系统id',
                                     `user_id` bigint(11) NOT NULL COMMENT '用户id',
                                     `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                                     `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
                                     `is_del` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除 0(false)-未删除  1(true)-已删除',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of base_client_user
-- ----------------------------

-- ----------------------------
-- Table structure for base_unit
-- ----------------------------
DROP TABLE IF EXISTS `base_unit`;
CREATE TABLE `base_unit`  (
                              `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
                              `parent_id` bigint(11) NOT NULL COMMENT '父级单位id',
                              `unit_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '单位名称',
                              `short_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位简称',
                              `unit_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编码',
                              `created_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                              `updated_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                              `is_del` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除 0(false)-未删除  1(true)-已删除',
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE INDEX `uq_key_code`(`unit_code`) USING BTREE COMMENT '单位编码唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '单位部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_unit
-- ----------------------------

-- ----------------------------
-- Table structure for base_unit_user
-- ----------------------------
DROP TABLE IF EXISTS `base_unit_user`;
CREATE TABLE `base_unit_user`  (
                                   `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                   `unit_id` bigint(11) NOT NULL COMMENT '单位id',
                                   `user_id` bigint(11) NOT NULL COMMENT '人员id',
                                   `created_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                                   `updated_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                                   `is_del` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除 0(false)-未删除  1(true)-已删除',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '单位部门人员关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of base_unit_user
-- ----------------------------

-- ----------------------------
-- Table structure for base_user
-- ----------------------------
DROP TABLE IF EXISTS `base_user`;
CREATE TABLE `base_user`  (
                              `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
                              `unit_id` bigint(11) NOT NULL COMMENT '单位id',
                              `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
                              `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
                              `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
                              `sex` tinyint(1) NOT NULL DEFAULT 0 COMMENT '性别（0-男 1-女）',
                              `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
                              `tel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '办公电话',
                              `id_card` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
                              `duty` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职务/职称',
                              `rank` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '军衔',
                              `identity_type` int(11) NULL DEFAULT NULL COMMENT '身份类别',
                              `head_sculpture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像路径',
                              `user_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户编码',
                              `state` int(11) NOT NULL DEFAULT 0 COMMENT '状态（0-正常 1-禁用）',
                              `created_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                              `updated_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
                              `is_del` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除 0(false)-未删除  1(true)-已删除',
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE INDEX `uq_key_code`(`user_code`) USING BTREE COMMENT '用户编码唯一',
                              UNIQUE INDEX `uq_key_card`(`id_card`) USING BTREE COMMENT '身份证号唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '人员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of base_user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;


