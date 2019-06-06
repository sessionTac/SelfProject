/*
Navicat MySQL Data Transfer

Source Server         : SESSION-本地数据库
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : osbulkparts

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2019-06-06 17:16:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for m_function_info
-- ----------------------------
DROP TABLE IF EXISTS `m_function_info`;
CREATE TABLE `m_function_info` (
  `function_id` int(10) NOT NULL COMMENT '主键id',
  `parent_id` int(10) DEFAULT NULL COMMENT '父节点',
  `function_name` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `function_code` varchar(100) DEFAULT NULL COMMENT '权限代码',
  `function_desc` varchar(200) DEFAULT NULL COMMENT '权限描述',
  `priority` int(11) DEFAULT NULL COMMENT '菜单排序',
  `menu_id` varchar(32) DEFAULT NULL COMMENT '所属菜单',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(14) DEFAULT NULL COMMENT '更新时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `is_delete` int(1) DEFAULT NULL COMMENT '逻辑删除',
  `version` int(10) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单列表';

-- ----------------------------
-- Records of m_function_info
-- ----------------------------
INSERT INTO `m_function_info` VALUES ('1', '0', '全部', '*', '开发用权限', null, null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('2', '0', '系统维护首页', 'maintenance:index', null, '1100', '', null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('3', '2', '系统设置', 'maintenance:system:config', null, '1110', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('4', '3', '数据字典', 'maintenance:system:dictionary', null, '1111', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('5', '2', '用户管理', 'maintenance:system:users', null, '1120', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('6', '5', '用户信息', 'maintenance:system:users:info', null, '1121', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('7', '5', '角色权限', 'maintenance:system:users:role', null, '1125', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('8', '2', '基础数据', 'maintenance:basis:data', null, '1130', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('9', '8', '物料主数据', 'maintenance:basis:matter', null, '1131', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('10', '8', '供应商信息', 'maintenance:basis:supplier', null, '1132', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('11', '2', '出入库管理', 'maintenance:basis:warehouse', null, '1140', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('12', '11', '订单计划', 'maintenance:warehouse:orderplan', null, '1141', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('13', '11', '预配发货', 'maintenance:warehouse:collection', null, '1142', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('14', '11', '发货明细', 'maintenance:warehouse:delivery', null, '1143', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('15', '11', '发货综合查询', 'maintenance:warehouse:rollingplan', null, '1144', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('16', '11', '发货统计信息', 'maintenance:warehouse:finance', null, '1145', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('17', '6', '用户信息画面查看', 'maintenance:system:users:info:view', null, '1122', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('18', '6', '用户信息编辑', 'maintenance:system:users:info:edit', null, '1123', null, null, null, null, null, '0', '1');

-- ----------------------------
-- Table structure for m_material_info
-- ----------------------------
DROP TABLE IF EXISTS `m_material_info`;
CREATE TABLE `m_material_info` (
  `material_info_id` varchar(32) NOT NULL COMMENT '主键ID',
  `material_order_code` varchar(20) DEFAULT NULL COMMENT '订单号(订单型号，成品型号)',
  `material_hnr_code` varchar(20) DEFAULT NULL COMMENT '物料HNR号',
  `material_code` varchar(20) DEFAULT NULL COMMENT '物料号',
  `material_category` varchar(30) DEFAULT NULL COMMENT '物料类别',
  `material_desc_cn` varchar(200) DEFAULT NULL COMMENT '物料中文描述',
  `material_desc_en` varchar(200) DEFAULT NULL COMMENT '物料英文描述',
  `material_desc_rn` varchar(200) DEFAULT NULL COMMENT '物料俄文描述',
  `hs_no` varchar(100) DEFAULT NULL COMMENT 'HS海关编码',
  `supplier_id` varchar(32) DEFAULT NULL COMMENT '供应商编号',
  `material_unit` varchar(30) DEFAULT NULL COMMENT '单位',
  `material_relation` varchar(5) DEFAULT NULL COMMENT '换算关系',
  `material_relation_unit` varchar(30) DEFAULT NULL COMMENT '换算后单位',
  `material_minpackage_amt` double(18,2) DEFAULT NULL COMMENT '最小包装数量',
  `material_tax_price` decimal(18,2) DEFAULT NULL COMMENT '未税单价',
  `material_vat_price` decimal(18,2) DEFAULT NULL COMMENT '含税单价',
  `material_rate` decimal(18,2) DEFAULT NULL COMMENT '代理费率',
  `material_currency` varchar(30) DEFAULT NULL COMMENT '币种',
  `material_price` decimal(18,2) DEFAULT NULL COMMENT '单价',
  `level_bom_code` int(10) DEFAULT NULL COMMENT '分级BOM编码',
  `material_supply_mode` varchar(30) DEFAULT NULL COMMENT '物料供货模式分类标识',
  `factory_code` varchar(32) DEFAULT NULL COMMENT '工厂号',
  `data_role_at` varchar(30) DEFAULT NULL COMMENT '数据所属',
  `user_defined1` varchar(100) DEFAULT NULL COMMENT '用户自定义1',
  `user_defined2` varchar(100) DEFAULT NULL COMMENT '用户自定义2',
  `user_defined3` varchar(100) DEFAULT NULL COMMENT '用户自定义3',
  `user_defined4` varchar(100) DEFAULT NULL COMMENT '用户自定义4',
  `user_defined5` varchar(100) DEFAULT NULL COMMENT '用户自定义5',
  `user_defined6` varchar(100) DEFAULT NULL COMMENT '用户自定义6',
  `user_defined7` varchar(100) DEFAULT NULL COMMENT '用户自定义7',
  `user_defined8` varchar(100) DEFAULT NULL COMMENT '用户自定义8',
  `user_defined9` varchar(100) DEFAULT NULL COMMENT '用户自定义9',
  `user_defined10` varchar(100) DEFAULT NULL COMMENT '用户自定义10',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(14) DEFAULT NULL COMMENT '更新时间',
  `is_delete` int(1) DEFAULT NULL COMMENT '逻辑删除',
  `version` int(20) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`material_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of m_material_info
-- ----------------------------

-- ----------------------------
-- Table structure for m_role_info
-- ----------------------------
DROP TABLE IF EXISTS `m_role_info`;
CREATE TABLE `m_role_info` (
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  `role_name` varchar(20) NOT NULL COMMENT '角色名',
  `role_desc` varchar(100) DEFAULT NULL COMMENT '角色说明',
  `role_at` varchar(30) DEFAULT NULL COMMENT '角色所属',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(14) DEFAULT NULL COMMENT '更新时间',
  `is_delete` int(1) DEFAULT NULL COMMENT '逻辑删除',
  `version` int(10) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of m_role_info
-- ----------------------------
INSERT INTO `m_role_info` VALUES ('10001', 'systemAdmin', '系统管理员2', '0', null, null, null, null, '0', '1');
INSERT INTO `m_role_info` VALUES ('10002', 'guest01', '海尔客户', '1', null, null, null, null, '0', '1');
INSERT INTO `m_role_info` VALUES ('10003', 'guest02', '海信客户', '2', null, null, null, null, '0', '1');
INSERT INTO `m_role_info` VALUES ('1c5f45cddec24ca89242ad55d2893199', '2', '123456721312', '0', '10001', null, null, null, '0', '13');
INSERT INTO `m_role_info` VALUES ('5cbfe86b9bfa4dbfb0ada6b17306b69d', '22', '2', '0', '10001', null, '10001', '20190606152257', '1', '1');

-- ----------------------------
-- Table structure for m_supplier_info
-- ----------------------------
DROP TABLE IF EXISTS `m_supplier_info`;
CREATE TABLE `m_supplier_info` (
  `supplier_id` varchar(32) NOT NULL COMMENT '供应商编号',
  `supplier_code` varchar(50) NOT NULL COMMENT '供应商代码',
  `supplier_name_cn` varchar(100) NOT NULL COMMENT '供应商中文名称',
  `supplier_name_en` varchar(100) DEFAULT NULL COMMENT '供应商英文名称',
  `supplier_desc_cn` varchar(200) DEFAULT NULL COMMENT '供应商中文说明',
  `supplier_desc_en` varchar(200) DEFAULT NULL COMMENT '供应商英文说明',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `contact` varchar(30) DEFAULT NULL COMMENT '联系人',
  `account_bank` varchar(20) DEFAULT NULL COMMENT '开户银行',
  `bank_address` varchar(200) DEFAULT NULL COMMENT '开户银行地址',
  `account_no` varchar(30) DEFAULT NULL COMMENT '帐号信息',
  `accountant` varchar(30) DEFAULT NULL COMMENT '账户人名',
  `contact_ways` varchar(20) DEFAULT '' COMMENT '联系方式',
  `supplier_cata` varchar(30) DEFAULT NULL COMMENT '供应商分类',
  `supplier_level` varchar(30) DEFAULT NULL COMMENT '供应商等级',
  `supplier_at` varchar(30) DEFAULT NULL COMMENT '供应商所属',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(14) DEFAULT NULL COMMENT '更新时间',
  `is_delete` int(1) DEFAULT NULL COMMENT '逻辑删除',
  `version` int(20) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of m_supplier_info
-- ----------------------------
INSERT INTO `m_supplier_info` VALUES ('dee183d3877511e999690a0027000012', 'supplierA', 'A供应商', 'Supplier_A', null, null, null, null, null, null, null, null, '', '1', '2', '1', 'admin', '20190605220122', null, null, '0', '1');

-- ----------------------------
-- Table structure for m_user_info
-- ----------------------------
DROP TABLE IF EXISTS `m_user_info`;
CREATE TABLE `m_user_info` (
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户名',
  `password` varchar(120) DEFAULT NULL COMMENT '密码',
  `user_real_name` varchar(20) DEFAULT NULL COMMENT '用户真实姓名',
  `user_type` int(1) DEFAULT NULL COMMENT '用户类型   1:系统管理员;2:操作用户;3:客户',
  `user_status` int(1) DEFAULT NULL COMMENT '用户状态 0:启用;1:停用;',
  `user_phone` varchar(15) DEFAULT NULL,
  `user_mail` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(14) DEFAULT NULL COMMENT '创建时间   YYYYMMDDHHmmSS',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(14) DEFAULT NULL COMMENT '更新时间   YYYYMMDDHHmmSS',
  `is_delete` int(1) DEFAULT NULL COMMENT '逻辑删除  0：正常  1：删除',
  `version` int(10) DEFAULT NULL COMMENT '版本 ',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of m_user_info
-- ----------------------------
INSERT INTO `m_user_info` VALUES ('06c2df9d081e47eca14f3bd2c7eb6a62', '112', '$2a$10$xt0IV9bm5vjnpGOZ.AjasO5sdau/ghZdAHiBlf9u0B/8uNLZIQ.K.', '1', '1', '0', '15063058059', '15063058059@qq.com', '10001', '20190603161600', '10001', '20190603165755', '1', '1');
INSERT INTO `m_user_info` VALUES ('1', '张三', '123456', '张三', '1', '0', '15063058059', '15063058059@qq.com', 'zhangsan', '20190529113600', 'zhangsan', '20190529113600', '0', '0');
INSERT INTO `m_user_info` VALUES ('10001', 'admin', '$2a$10$/l43mxlCorXVwylbbNsdVOkv9cy2nLUS4uRL./0fx6VgVN40TaBtO', '系统管理员', '1', '0', '15011112222', null, null, null, null, null, '0', '1');
INSERT INTO `m_user_info` VALUES ('2', '李四', '123456', '李四', '1', '0', '2', null, 'lisi', '20190529113600', 'lisi', '20190529113600', '0', '0');
INSERT INTO `m_user_info` VALUES ('476bfb141d4f42fda1c7e13d696ade57', '22', '$2a$10$7I0zgmkqoLNv3n0Ii16sR.WfQECRRAAjmfttw3rn3b.Ngar.cmpFK', '22634', '1', '0', '15063058059', '15063058059@qq.com', '10001', '20190603162846', '10001', '20190604114920', '0', '1');
INSERT INTO `m_user_info` VALUES ('514dfbd98073405799e91add3a128c87', '123', '$2a$10$l4VFJmGU475m61Rr4sjd8.ahvp8BWgsZgVh6fwSeDwRzigf4K.UIi', '1', '1', '0', '15063058059', '15063058059@qq.com', '10001', '20190603153947', '10001', '20190603165823', '1', '1');
INSERT INTO `m_user_info` VALUES ('99ee1c0bb3144c97a37a00b1cc0f316b', '11', '$2a$10$3oV/Z7CK6KdXdAAVHEMCEe/F9owDFS0RHhS/gt5DHO85DntuKESb2', null, '1', '0', null, null, '10001', '20190603153607', null, null, '0', '1');

-- ----------------------------
-- Table structure for t_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `t_dict_data`;
CREATE TABLE `t_dict_data` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `dict_type_code` varchar(32) NOT NULL COMMENT '字典类型编码',
  `value` varchar(30) NOT NULL COMMENT '字典值',
  `name` varchar(50) NOT NULL COMMENT '字典名称',
  `desc` varchar(100) DEFAULT NULL,
  `sort_code` int(11) DEFAULT NULL COMMENT '排序',
  `is_enable` int(1) DEFAULT '1' COMMENT '是否有效(0:无效 1:有效)',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` varchar(14) DEFAULT NULL COMMENT '更新时间',
  `delete_flg` int(1) DEFAULT NULL COMMENT '删除标识',
  `version` int(20) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_type_value` (`dict_type_code`,`value`),
  UNIQUE KEY `idx_type_value_name` (`dict_type_code`,`value`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表 2018/11/14 create';

-- ----------------------------
-- Records of t_dict_data
-- ----------------------------
INSERT INTO `t_dict_data` VALUES ('1', 'currency', '1', 'RMB', '人民币', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('10', 'matterstatus', '2', '已出库', '已出库', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('11', 'usertype', '1', '系统管理员', '系统管理员', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('12', 'usertype', '2', '操作用户', '操作用户', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('13', 'usertype', '3', '客户', '客户', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('14', 'userstatus', '0', '启用', '启用', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('15', 'userstatus', '1', '停用', '停用', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('16', 'userlevel', '1', '海尔', '海尔', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('17', 'userlevel', '2', '海信', '海信', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('18', 'roleAt', '1', '海尔', '角色对海尔数据具备权限', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('19', 'roleAt', '2', '海信', '角色对海信数据具备权限', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('2', 'currency', '2', 'SUR', '俄罗斯卢布', '1', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('20', 'roleAt', '0', '全部', '角色对平台中所有数据具备权限', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('21', 'supplierCata', '1', '物料供应商', '物料供应商', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('22', 'supplierCata', '2', '其他供应商', '其他供应商', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('23', 'supplierLevel', '1', '初级', '供应商最低级别', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('24', 'supplierLevel', '2', '中级', '一般供应商', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('25', 'supplierLevel', '3', '高级', '信任度极高供应商', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('3', 'currency', '3', 'USD', '美元', '2', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('4', 'currency', '4', 'EUR', '欧元', '3', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('5', 'currency', '5', 'GBP', '英镑', '4', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('6', 'mattertype', '1', '0', '物料类别0', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('7', 'mattertype', '2', '1', '物料类别1', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('8', 'unit', '1', 'EA', '物料单位', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('9', 'matterstatus', '1', '未出库', '未出库', '0', '1', null, null, null, null, null, '0', '1');

-- ----------------------------
-- Table structure for t_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `t_dict_type`;
CREATE TABLE `t_dict_type` (
  `dict_type_id` varchar(32) NOT NULL COMMENT '主键',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父节点',
  `code` varchar(30) DEFAULT NULL COMMENT '编码',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `desc` varchar(100) DEFAULT NULL,
  `sort_code` int(10) DEFAULT NULL COMMENT '排序',
  `is_enable` int(1) DEFAULT '1' COMMENT '是否有效(0:无效 1:有效)',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` varchar(14) DEFAULT NULL COMMENT '更新时间',
  `is_delete` int(1) DEFAULT '0' COMMENT '是否删除',
  `version` int(20) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`dict_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典分类表 ';

-- ----------------------------
-- Records of t_dict_type
-- ----------------------------
INSERT INTO `t_dict_type` VALUES ('1', '0', 'currency', '币种', '币种', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('10', '0', 'supplierLevel', '供应商等级', '供应商等级', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('2', '0', 'mattertype', '物料类别', '物料类别', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('3', '0', 'unit', '单位', '单位', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('4', '0', 'matterstatus', '物料状态', '物料状态', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('5', '0', 'usertype', '用户类型', '用户类型', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('6', '0', 'userstatus', '用户状态', '用户状态', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('7', '0', 'userlevel', '用户所属', '用户所属', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('8', '0', 'roleAt', '角色域', '角色作用域', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('9', '0', 'supplierCata', '供应商类型', '供应商类型', '0', '1', null, null, null, null, null, '0', '1');

-- ----------------------------
-- Table structure for t_role_function_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_role_function_relation`;
CREATE TABLE `t_role_function_relation` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `role_id` varchar(32) DEFAULT NULL COMMENT '角色id',
  `function_id` varchar(32) DEFAULT NULL COMMENT '权限id',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `update_time` varchar(14) DEFAULT NULL COMMENT '修改时间',
  `is_delete` int(1) DEFAULT '0' COMMENT '逻辑删除  默认‘0’',
  `version` int(5) DEFAULT NULL COMMENT '排他版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_role_function_relation
-- ----------------------------
INSERT INTO `t_role_function_relation` VALUES ('03172c9c882711e999690a0027000012', '1c5f45cddec24ca89242ad55d2893199', '1', '10001', '20190606140642', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('6fba1dbb882611e999690a0027000012', '10001', '1', '10001', '20190606140634', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('6fba2b47882611e999690a0027000012', '10001', '2', '10001', '20190606140634', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('6fba2c34882611e999690a0027000012', '10001', '3', '10001', '20190606140634', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('6fba2ca3882611e999690a0027000012', '10001', '4', '10001', '20190606140634', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('6fba2d0a882611e999690a0027000012', '10001', '5', '10001', '20190606140634', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('6fba2d6c882611e999690a0027000012', '10001', '6', '10001', '20190606140634', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('6fba2dcb882611e999690a0027000012', '10001', '17', '10001', '20190606140634', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('6fba2e2e882611e999690a0027000012', '10001', '18', '10001', '20190606140634', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('6fba2e89882611e999690a0027000012', '10001', '7', '10001', '20190606140634', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('6fba2ee0882611e999690a0027000012', '10001', '8', '10001', '20190606140634', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('6fba2f33882611e999690a0027000012', '10001', '9', '10001', '20190606140634', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('6fba2f8a882611e999690a0027000012', '10001', '10', '10001', '20190606140634', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('6fba2fe4882611e999690a0027000012', '10001', '11', '10001', '20190606140634', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('6fba303f882611e999690a0027000012', '10001', '12', '10001', '20190606140634', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('6fba3092882611e999690a0027000012', '10001', '13', '10001', '20190606140634', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('6fba30ed882611e999690a0027000012', '10001', '14', '10001', '20190606140634', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('6fba3144882611e999690a0027000012', '10001', '15', '10001', '20190606140634', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('6fba31a3882611e999690a0027000012', '10001', '16', '10001', '20190606140634', null, null, '0', '0');

-- ----------------------------
-- Table structure for t_user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role_relation`;
CREATE TABLE `t_user_role_relation` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  `private_menu` text COMMENT '角色菜单',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(14) DEFAULT NULL COMMENT '更新时间',
  `version` int(10) DEFAULT NULL COMMENT '版本',
  `is_delete` int(1) DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`,`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_user_role_relation
-- ----------------------------
INSERT INTO `t_user_role_relation` VALUES ('1231231', '10001', '10001', null, null, null, null, null, '1', '0');
INSERT INTO `t_user_role_relation` VALUES ('14423423', '1', '10002', null, null, null, null, null, '1', '0');
