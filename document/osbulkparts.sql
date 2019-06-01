/*
Navicat MySQL Data Transfer

Source Server         : SESSION-本地数据库
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : osbulkparts

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2019-06-01 16:33:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for m_function_info
-- ----------------------------
DROP TABLE IF EXISTS `m_function_info`;
CREATE TABLE `m_function_info` (
  `function_id` varchar(32) NOT NULL COMMENT '主键id',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父节点',
  `function_name` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `function_code` varchar(100) DEFAULT NULL COMMENT '权限代码',
  `function_desc` varchar(200) DEFAULT NULL COMMENT '权限描述',
  `priority` int(11) DEFAULT NULL COMMENT '菜单排序',
  `menu_id` varchar(32) DEFAULT NULL COMMENT '所属菜单',
  `update_user` varchar(32) DEFAULT NULL,
  `update_time` varchar(14) DEFAULT NULL,
  `create_user` varchar(32) DEFAULT NULL,
  `create_time` varchar(14) DEFAULT NULL,
  `is_delete` int(1) DEFAULT NULL COMMENT '逻辑删除',
  `version` int(5) DEFAULT NULL,
  PRIMARY KEY (`function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单列表';

-- ----------------------------
-- Records of m_function_info
-- ----------------------------
INSERT INTO `m_function_info` VALUES ('1', null, '全部', '*', '开发用权限', null, null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('10', '8', '供应商信息', 'maintenance:basis:supplier', null, '1132', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('11', '2', '出入库管理', 'maintenance:basis:warehouse', null, '1140', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('12', '11', '订单计划', 'maintenance:warehouse:orderplan', null, '1141', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('13', '11', '预配发货', 'maintenance:warehouse:collection', null, '1142', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('14', '11', '发货明细', 'maintenance:warehouse:delivery', null, '1143', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('15', '11', '发货综合查询', 'maintenance:warehouse:rollingplan', null, '1144', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('16', '11', '发货统计信息', 'maintenance:warehouse:finance', null, '1145', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('2', null, '系统维护首页', 'maintenance:index', null, '1100', '', null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('3', '2', '系统设置', 'maintenance:system:config', null, '1110', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('4', '3', '数据字典', 'maintenance:system:dictionary', null, '1111', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('5', '2', '用户管理', 'maintenance:system:users', null, '1120', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('6', '5', '用户信息', 'maintenance:system:user', null, '1121', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('7', '5', '角色权限', 'maintenance:system:role', null, '1122', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('8', '2', '基础数据', 'maintenance:basis:data', null, '1130', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('9', '8', '物料主数据', 'maintenance:basis:matter', null, '1131', null, null, null, null, null, '0', '1');

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
INSERT INTO `m_role_info` VALUES ('10001', 'systemAdmin', '系统管理员', '3', null, null, null, null, '0', '1');
INSERT INTO `m_role_info` VALUES ('10002', 'guest01', '海尔客户', '1', null, null, null, null, '0', '1');
INSERT INTO `m_role_info` VALUES ('10003', 'guest02', '海信客户', '2', null, null, null, null, '0', '1');

-- ----------------------------
-- Table structure for m_user_info
-- ----------------------------
DROP TABLE IF EXISTS `m_user_info`;
CREATE TABLE `m_user_info` (
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户名',
  `password` varchar(20) DEFAULT NULL COMMENT '密码',
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
INSERT INTO `m_user_info` VALUES ('1', '张三', '123456', '张三', '1', '0', '15063058059', '15063058059@qq.com', 'zhangsan', '20190529113600', 'zhangsan', '20190529113600', '0', '0');
INSERT INTO `m_user_info` VALUES ('10001', 'admin', '123456', '系统管理员', '1', '0', '15011112222', null, null, null, null, null, '0', '1');
INSERT INTO `m_user_info` VALUES ('2', '李四', '123456', '李四', '1', '0', '2', null, 'lisi', '20190529113600', 'lisi', '20190529113600', '0', '0');

-- ----------------------------
-- Table structure for t_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `t_dict_data`;
CREATE TABLE `t_dict_data` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `dict_type_id` varchar(32) NOT NULL COMMENT '字典分类表主键',
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
  `delete_flg` char(1) DEFAULT NULL COMMENT '删除标识',
  `version` int(20) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_type_value` (`dict_type_id`,`value`),
  UNIQUE KEY `idx_type_value_name` (`dict_type_id`,`value`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表 2018/11/14 create';

-- ----------------------------
-- Records of t_dict_data
-- ----------------------------
INSERT INTO `t_dict_data` VALUES ('1', '1', '1', 'RMB', '人民币', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('10', '4', '2', '已出库', '已出库', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('11', '5', '1', '系统管理员', '系统管理员', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('12', '5', '2', '操作用户', '操作用户', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('13', '5', '3', '客户', '客户', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('14', '6', '0', '启用', '启用', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('15', '6', '1', '停用', '停用', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('16', '7', '1', '海尔', '海尔', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('17', '7', '2', '海信', '海信', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('18', '8', '1', '海尔', '角色对海尔数据具备权限', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('19', '8', '2', '海信', '角色对海信数据具备权限', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('2', '1', '2', 'SUR', '俄罗斯卢布', '1', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('20', '8', '9', '全部', '角色对平台中所有数据具备权限', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('3', '1', '3', 'USD', '美元', '2', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('4', '1', '4', 'EUR', '欧元', '3', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('5', '1', '5', 'GBP', '英镑', '4', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('6', '2', '1', '0', '物料类别0', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('7', '2', '2', '1', '物料类别1', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('8', '3', '1', 'EA', '物料单位', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('9', '4', '1', '未出库', '未出库', '0', '1', null, null, null, null, null, '0', '1');

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
INSERT INTO `t_dict_type` VALUES ('2', '0', 'mattertype', '物料类别', '物料类别', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('3', '0', 'unit', '单位', '单位', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('4', '0', 'matterstatus', '物料状态', '物料状态', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('5', '0', 'usertype', '用户类型', '用户类型', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('6', '0', 'userstatus', '用户状态', '用户状态', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('7', '0', 'userlevel', '用户所属', '用户所属', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('8', '0', 'roleAt', '角色域', '角色作用域', '0', '1', null, null, null, null, null, '0', '1');

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
INSERT INTO `t_role_function_relation` VALUES ('13123123123', '10001', '1', null, null, null, null, '0', null);

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
INSERT INTO `t_user_role_relation` VALUES ('1231231', '10001', '10001', null, null, null, null, null, null, null);
INSERT INTO `t_user_role_relation` VALUES ('14423423', '1', '10002', null, null, null, null, null, null, null);
INSERT INTO `t_user_role_relation` VALUES ('523452345', '3', '10003', null, null, null, null, null, null, null);
