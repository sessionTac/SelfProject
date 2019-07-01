/*
Navicat MySQL Data Transfer

Source Server         : SESSION-本地数据库
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : osbulkparts

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-07-01 14:09:18
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
INSERT INTO `m_function_info` VALUES ('2', null, '首頁', 'maintenance:dashboard', null, '10000', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('3', null, '系统设置', 'maintenance:system:config', null, '11000', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('4', '3', '数据字典', 'maintenance:system:dictionary', null, '11010', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('5', null, '用户管理', 'maintenance:system:users', null, '12000', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('6', '5', '用户信息', 'maintenance:system:users:info', null, '12010', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('7', '5', '角色权限', 'maintenance:system:users:role', null, '12020', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('8', null, '基础数据', 'maintenance:basis:data', null, '13000', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('9', '8', '物料主数据', 'maintenance:basis:matter:info', null, '13010', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('10', '8', '供应商信息', 'maintenance:basis:supplier:info', null, '13020', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('11', null, '出入库管理', 'maintenance:basis:warehouse', null, '14000', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('12', '11', '订单计划', 'maintenance:warehouse:orderinfo', null, '14010', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('13', '11', '预配发货', 'maintenance:warehouse:collection', null, '14020', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('14', '11', '发货明细', 'maintenance:warehouse:delivery', null, '14030', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('15', '11', '发货综合查询', 'maintenance:warehouse:rollingplan', null, '14040', 'info', null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('16', '11', '发货统计信息', 'maintenance:warehouse:finance', null, '14050', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('17', '6', '用户信息画面查看', 'maintenance:system:users:info:view', null, '12011', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('18', '6', '用户信息编辑', 'maintenance:system:users:info:edit', null, '12012', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('19', '6', '用户信息添加', 'maintenance:system:users:info:add', null, '12013', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('20', '6', '用户信息删除', 'maintenance:system:users:info:delete', null, '12014', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('21', '6', '用户信息查询', 'maintenance:system:users:info:select', null, '12015', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('22', '6', '用户角色设置', 'maintenance:system:users:info:roleset', null, '12016', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('23', '6', '用户角色查看', 'maintenance:system:users:info:roleview', null, '12017', null, null, null, null, '', '0', '1');
INSERT INTO `m_function_info` VALUES ('24', '7', '角色权限画面查看', 'maintenance:system:users:role:view', null, '12021', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('25', '7', '角色权限查询', 'maintenance:system:users:role:select', null, '12022', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('26', '7', '角色权限添加', 'maintenance:system:users:role:add', null, '12023', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('27', '7', '角色权限编辑', 'maintenance:system:users:role:edit', null, '12024', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('28', '7', '角色权限删除', 'maintenance:system:users:role:delete', null, '12025', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('29', '7', '角色权限设置', 'maintenance:system:users:role:funset', null, '12026', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('30', '4', '字典数据查询', 'maintenance:system:dictionary:select', '', '11011', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('31', '3', '字典数据添加', 'maintenance:system:dictionary:add', null, '11012', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('32', '3', '字典数据编辑', 'maintenance:system:dictionary:edit', null, '11013', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('33', '3', '字典数据删除', 'maintenance:system:dictionary:delete', null, '11014', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('34', '9', '物料数据页面查看', 'maintenance:basis:matter:info:view', null, '13011', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('35', '9', '物料数据查询', 'maintenance:basis:matter:info:select', null, '13012', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('36', '9', '物料数据添加', 'maintenance:basis:matter:info:add', null, '13013', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('37', '9', '物料数据导入', 'maintenance:basis:matter:info:import', null, '13014', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('38', '9', '物料数据导出', 'maintenance:basis:matter:info:export', null, '13015', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('39', '9', '物料数据删除', 'maintenance:basis:matter:info:delete', null, '13016', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('40', '9', '物料数据锁定', 'maintenance:basis:matter:info:locked', null, '13017', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('41', '9', '物料数据编辑', 'maintenance:basis:matter:info:edit', null, '13018', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('42', '10', '供应商页面查看', 'maintenance:basis:supplier:info:view', null, '13021', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('43', '10', '供应商查询', 'maintenance:basis:supplier:info:select', null, '13022', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('44', '10', '供应商编辑', 'maintenance:basis:supplier:info:edit', null, '13023', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('45', '10', '供应商添加', 'maintenance:basis:supplier:info:add', null, '13024', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('46', '10', '供应商删除', 'maintenance:basis:supplier:info:delete', null, '13025', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('47', '4', '数据字典分类', 'maintenance:system:dictionary:type', null, '11015', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('49', '9', '物料数据解锁', 'maintenance:basis:matter:info:unlocked', null, '13019', null, null, null, null, null, '0', '1');
INSERT INTO `m_function_info` VALUES ('50', '12', '订单计划画面查看', 'maintenance:warehouse:order:info:view', null, '14011', null, null, null, null, null, '0', '1');

-- ----------------------------
-- Table structure for m_material_info
-- ----------------------------
DROP TABLE IF EXISTS `m_material_info`;
CREATE TABLE `m_material_info` (
  `material_info_id` varchar(32) NOT NULL COMMENT '主键ID',
  `material_order_code` varchar(20) DEFAULT NULL COMMENT '订单号(订单型号，成品型号)',
  `material_order_code_desc` varchar(20) DEFAULT NULL COMMENT '订单型号描述，成品型号描述',
  `material_hnr_code` varchar(20) DEFAULT NULL COMMENT '物料HNR号',
  `material_ckd_code` varchar(20) DEFAULT NULL COMMENT '物料CKD号（ckd型号编码）',
  `material_code` varchar(20) DEFAULT NULL COMMENT '物料号',
  `material_category` varchar(30) DEFAULT NULL COMMENT '物料类别',
  `material_desc_cn` varchar(200) DEFAULT NULL COMMENT '物料中文描述',
  `material_desc_en` varchar(200) DEFAULT NULL COMMENT '物料英文描述',
  `material_desc_rn` varchar(200) DEFAULT NULL COMMENT '物料俄文描述',
  `hs_no` varchar(100) DEFAULT NULL COMMENT 'HS海关编码',
  `supplier_code` varchar(32) DEFAULT NULL COMMENT '供应商编号',
  `material_amount` decimal(18,2) DEFAULT NULL COMMENT '物料数量  （子件数量）',
  `material_unit` varchar(30) DEFAULT NULL COMMENT '单位',
  `material_relation` varchar(30) DEFAULT NULL COMMENT '换算关系',
  `material_relation_unit` varchar(30) DEFAULT NULL COMMENT '换算后单位',
  `material_minpackage_type` varchar(30) DEFAULT NULL COMMENT '最小包装类型',
  `material_minpackage_amt` double(18,2) DEFAULT NULL COMMENT '最小包装数量',
  `material_tax_price` decimal(18,2) DEFAULT NULL COMMENT '未税单价',
  `material_vat_price` decimal(18,2) DEFAULT NULL COMMENT '含税单价',
  `tax` decimal(18,2) DEFAULT NULL COMMENT '税率',
  `material_rate` decimal(18,2) DEFAULT NULL COMMENT '代理费率',
  `material_currency` varchar(30) DEFAULT NULL COMMENT '币种',
  `material_price` decimal(18,2) DEFAULT NULL COMMENT '单价',
  `level_bom_code` int(10) DEFAULT NULL COMMENT '分级BOM编码',
  `material_supply_mode` varchar(30) DEFAULT NULL COMMENT '物料供货模式分类标识',
  `factory_code` varchar(32) DEFAULT NULL COMMENT '工厂号',
  `length` decimal(18,2) DEFAULT NULL COMMENT '长',
  `width` decimal(18,2) DEFAULT NULL COMMENT '宽',
  `height` decimal(18,2) DEFAULT NULL COMMENT '高',
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
  `is_locked` int(1) DEFAULT NULL COMMENT '数据锁定标识 0:正常；1:锁定',
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
INSERT INTO `m_material_info` VALUES ('51c0f596eaf04447ac03ed34a33e2e6f', '1', null, null, '0069000241HNR', '0069000241HNR', '1', '灯罩-PS', 'LampCover(HRF-250E)', ' Циндао синь Бовен (10 из хуандао) ', 'CHN', 'V12981', '15.00', '1', '1', '1', null, '10.00', '9.00', '10.00', '0.13', '0.10', '1', null, null, null, '90C0', '11.00', '11.00', '11.00', '0', null, null, null, null, null, null, null, null, null, null, '1', 'admin', '20190620200604', 'admin', '20190620200650', '0', '1');
INSERT INTO `m_material_info` VALUES ('5294188dfedb4673ac45a036303970ad', '1', null, null, '0060732376HNR', '0060732376HNR', '1', '冷凝器-无', 'Condensor(HRF-250E)', ' Циндао синь Бовен (4 из хуандао) ', 'CHN', 'V12981', '20.00', '1', '1', '1', null, '10.00', '3.00', '4.00', '0.13', '0.10', '1', null, null, null, '90C0', '11.00', '11.00', '11.00', '0', null, null, null, null, null, null, null, null, null, null, '1', 'admin', '20190620200604', 'admin', '20190620200650', '0', '1');
INSERT INTO `m_material_info` VALUES ('539083ef526c4f7fb77d557162950f69', '0060303898', null, null, '0060845581HNR', '0060845581HNR', '1', '恒温器组件（恒温器+盖+旋钮+传输盖+LED）', 'ThermostatAssembly(Themostat+Cover+Knob+TransperentCover+LED)', ' Циндао синь Бовен (9 из хуандао) ', 'CHN', 'V12981', null, '1', '1', '1', null, '10.00', '8.00', '9.00', null, '0.10', '1', null, null, null, '90C0', '11.00', '11.00', '11.00', '0', null, null, null, null, null, null, null, null, null, null, '0', 'admin', '20190620200604', 'admin', '20190620200650', '0', '1');
INSERT INTO `m_material_info` VALUES ('57743835f7e0462fa23596fcd9ab5978', '0060303898', null, null, '0060308628AHNR', '0060308628AHNR', '1', 'RefGlassGlaxyBK398EPB', 'RefGlassGlaxyBK398EPB', ' Циндао синь Бовен (12 из хуандао) ', 'CHN', 'V12981', null, '1', '1', '1', null, '10.00', '11.00', '12.00', null, '0.10', '1', null, null, null, '90C0', '11.00', '11.00', '11.00', '0', null, null, null, null, null, null, null, null, null, null, '0', 'admin', '20190620200604', 'admin', '20190620200650', '0', '1');
INSERT INTO `m_material_info` VALUES ('6aba8f62ea8e48168f97f248b2608672', '00603038981', null, null, '0060221585AHNR', '0060221585AHNR', '1', '冷冻搁物架饰条', 'FrzShelfEndCap', ' Циндао синь Бовен (7 из хуандао) ', 'CHN', 'V12981', null, '1', '1', '1', null, '111.00', '111.00', '111.00', null, '1111.00', '1', '11.00', '11111', null, '90C0', '111.00', '111.00', '111.00', '0', null, null, null, null, null, null, null, null, null, null, null, null, null, 'admin', '20190622150655', '0', '10');
INSERT INTO `m_material_info` VALUES ('9586034284fd4460a85c2365bc0f1335', '0060303898', null, null, '0060103495C', '0060103495C', '1', 'VCM酒红冷冻门壳板材0.5*509*792', 'Freezerdoorvcmmaroon', ' Циндао синь Бовен (5 из хуандао) ', 'CHN', 'V12981', '13.50', '1', '1', '1', '1', '10.00', '4.00', '5.00', '1.15', '0.10', '1', '12312.00', '123', '1', '90C0', '12.00', '123.00', '22.00', '0', null, null, null, null, null, null, null, null, null, null, '0', null, null, 'admin', '20190628160653', '0', '15');
INSERT INTO `m_material_info` VALUES ('a9b939e569354254aebb591fdae70232', '0060303898', null, null, '0064001458HNR', '0064001458HNR', '1', '低温补偿加热丝', 'DrainHeater', ' Циндао синь Бовен (3 из хуандао) ', 'CHN', 'V12981', null, '1', '1', '1', null, '10.00', '2.00', '3.00', null, '0.10', '1', null, null, null, '90C0', null, null, null, '0', null, null, null, null, null, null, null, null, null, null, '0', null, null, 'admin', '20190627210654', '0', '3');
INSERT INTO `m_material_info` VALUES ('bfe7818fd8dc42b9882effa0ba8d5557', '0060303898', null, null, '0060220305HNR', '0060220305HNR', '1', '恒温箱', 'ThermostatBox', ' Циндао синь Бовен (6 из хуандао) ', 'CHN', 'V12981', null, '1', '1', '1', null, '10.00', '5.00', '6.00', null, '0.10', '1', null, null, null, '90C0', null, null, null, '0', null, null, null, null, null, null, null, null, null, null, '0', null, null, 'admin', '20190627210620', '0', '2');
INSERT INTO `m_material_info` VALUES ('cf8afe341ba34eb6a7c51b98fad8e631', '0060303898', null, null, '0060830638HNR', '0060830638HNR', '1', '新制冰机总成', 'icemachineassembly', ' Циндао синь Бовен (8 из хуандао) ', 'CHN', 'V12981', null, '1', '1', '1', null, '10.00', '7.00', '8.00', null, '0.10', '1', null, null, null, '90C0', null, null, null, '0', null, null, null, null, null, null, null, null, null, null, '0', null, null, 'admin', '20190627210606', '0', '2');
INSERT INTO `m_material_info` VALUES ('e5df55547d5b4a049f4ca1d96b1278fe', '0060303898', null, null, '0060308628HNR', '0060308628HNR', '1', 'RefGlassGlaxyBK398EPB', 'RefGlassGlaxyBK398EPB', ' Циндао синь Бовен (11 из хуандао) ', 'CHN', 'V12981', null, '1', '1', '1', null, '10.00', '10.00', '11.00', null, '0.10', '1', null, null, null, '90C0', null, null, null, '0', null, null, null, null, null, null, null, null, null, null, '0', null, null, 'admin', '20190627210627', '0', '2');
INSERT INTO `m_material_info` VALUES ('e77c3bc655404f55a5f9ce4076240e45', '0060303898', null, null, '0060303898HNR', '0060303898HNR', '1', 'HRF-342/382Frz黑色玻璃（连衣裙）', 'HRF-342/382FrzBlackGlass(Dress)', ' Циндао синь Бовен (2 из хуандао) ', 'CHN', 'V12981', null, '1', '1', '1', null, '10.00', '1.00', '2.00', null, '0.10', '1', null, null, null, '90C0', null, null, null, '0', null, null, null, null, null, null, null, null, null, null, '0', null, null, 'admin', '20190627210606', '0', '2');

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
  `supplier_quo` decimal(5,4) DEFAULT NULL COMMENT '供应商配额',
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
INSERT INTO `m_supplier_info` VALUES ('b5c3618143b141eeb60d1e6283d7cbeb', '12456', '12456', '12456', '12456', '12456', null, null, null, '阿斯顿发送到发', '12312312312312', ' 法师打发', '123123112', '1', '1', '0', null, null, null, 'admin', null, '0', '3');
INSERT INTO `m_supplier_info` VALUES ('dee183d3877511e999690a0027000012', 'V12981', 'A供应商', 'Supplier_A', null, null, null, null, null, null, null, null, '', '1', '2', '1', '0.1500', 'admin', '20190605220122', 'admin', '20190622130639', '0', '1');
INSERT INTO `m_supplier_info` VALUES ('e5098fe9e71d41efb838fb4a8ed1bc27', 'fadgadg213412412', 'fadgadg111', 'fadgadg', 'fadgadg', null, null, null, null, null, null, null, null, '1', '2', '0', null, null, null, 'admin', '20190622130639', '1', '3');
INSERT INTO `m_supplier_info` VALUES ('f83de516e75647e7948476c7b1677e01', '2514145', '2514145', '2514145', '2514145', '2514145', null, null, null, null, null, null, '', '1', '2', '0', null, 'admin', '20190615152245', 'admin', '20190622130628', '1', '1');

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
INSERT INTO `m_user_info` VALUES ('06c2df9d081e47eca14f3bd2c7eb6a62', '112', '$2a$10$/l43mxlCorXVwylbbNsdVOkv9cy2nLUS4uRL./0fx6VgVN40TaBtO', '1', '1', '0', '15063058059', '15063058059@qq.com', '10001', '20190603161600', '10001', '20190603165755', '1', '1');
INSERT INTO `m_user_info` VALUES ('1', '张三', '123456', '张三', '1', '0', '15063058059', '15063058059@qq.com', 'zhangsan', '20190529113600', 'zhangsan', '20190529113600', '0', '0');
INSERT INTO `m_user_info` VALUES ('10001', 'admin', '$2a$10$/l43mxlCorXVwylbbNsdVOkv9cy2nLUS4uRL./0fx6VgVN40TaBtO', '系统管理员', '1', '0', '15011112222', null, null, null, null, null, '0', '1');
INSERT INTO `m_user_info` VALUES ('2', '李四', '123456', '李四', '1', '0', '2', null, 'lisi', '20190529113600', 'lisi', '20190529113600', '0', '0');
INSERT INTO `m_user_info` VALUES ('476bfb141d4f42fda1c7e13d696ade57', '22', '$2a$10$7I0zgmkqoLNv3n0Ii16sR.WfQECRRAAjmfttw3rn3b.Ngar.cmpFK', '22634', '1', '0', '15063058059', '15063058059@qq.com', '10001', '20190603162846', '10001', '20190604114920', '0', '1');
INSERT INTO `m_user_info` VALUES ('514dfbd98073405799e91add3a128c87', '123', '$2a$10$l4VFJmGU475m61Rr4sjd8.ahvp8BWgsZgVh6fwSeDwRzigf4K.UIi', '1', '1', '0', '15063058059', '15063058059@qq.com', '10001', '20190603153947', '10001', '20190603165823', '1', '1');
INSERT INTO `m_user_info` VALUES ('99ee1c0bb3144c97a37a00b1cc0f316b', '11', '$2a$10$3oV/Z7CK6KdXdAAVHEMCEe/F9owDFS0RHhS/gt5DHO85DntuKESb2', null, '1', '0', null, null, '10001', '20190603153607', null, null, '0', '1');

-- ----------------------------
-- Table structure for sequence
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `name` varchar(32) NOT NULL,
  `value` int(6) DEFAULT NULL,
  `next` int(6) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sequence
-- ----------------------------
INSERT INTO `sequence` VALUES ('trans_no', '21', '1');

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
INSERT INTO `t_dict_data` VALUES ('26', 'supplyMode', '1', '1', '物料供货模式分类标识1', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('27', 'lockStatus', '0', '未锁定', '未锁定', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('28', 'lockStatus', '1', '锁定', '锁定', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('29', 'minpackageType', '1', '包装类型1', '包装类型1', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('3', 'currency', '3', 'USD', '美元', '2', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('30', 'minpackageType', '2', '包装类型2', '包装类型2', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('31', 'planStatus', '0', '已导入', '已导入未生成', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('32', 'planStatus', '1', '已生成', '已生成订单明细', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('33', 'converRelation', '10', '10', '换算的算法为10', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('34', 'converRelation', '20', '20', '换算的算法为20', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('35', 'unit', '2', '袋', '物料单位', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('36', 'orderStatus', '0', '初始', '初始', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('37', 'orderStatus', '1', '确认', '确认', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('38', 'countryCode', '1', '中国', '中国', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('39', 'countryCode', '2', '俄罗斯', '俄罗斯', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('4', 'currency', '4', 'EUR', '欧元', '3', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('5', 'currency', '5', 'GBP', '英镑', '4', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('6', 'mattertype', '1', '1', '物料类别1', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_data` VALUES ('7', 'mattertype', '2', '2', '物料类别2', '0', '1', null, null, null, null, null, '0', '1');
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
INSERT INTO `t_dict_type` VALUES ('11', '0', 'supplyMode', '物料供货模式分类标识', '物料供货模式分类标识', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('12', '0', 'lockStatus', '锁定状态', '锁定状态', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('13', '0', 'minpackageType', '包装类型', '包装类型', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('14', '0', 'planStatus', '计划状态', '计划状态', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('15', '0', 'converRelation', '换算关系', '换算关系', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('16', '0', 'taxRate', '税率', '税率', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('17', '0', 'orderStatus', '订单状态', '订单状态', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('18', '0', 'countryCode', '国家标志', '国家标志', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('2', '0', 'mattertype', '物料类别', '物料类别', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('3', '0', 'unit', '单位', '单位', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('4', '0', 'matterstatus', '物料状态', '物料状态', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('5', '0', 'usertype', '用户类型', '用户类型', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('6', '0', 'userstatus', '用户状态', '用户状态', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('7', '0', 'userlevel', '用户所属', '用户所属', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('8', '0', 'roleAt', '角色域', '角色作用域', '0', '1', null, null, null, null, null, '0', '1');
INSERT INTO `t_dict_type` VALUES ('9', '0', 'supplierCata', '供应商类型', '供应商类型', '0', '1', null, null, null, null, null, '0', '1');

-- ----------------------------
-- Table structure for t_order_detail_info
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_info`;
CREATE TABLE `t_order_detail_info` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `order_code` varchar(20) DEFAULT NULL COMMENT '订单产品型号',
  `order_code_desc` varchar(200) DEFAULT NULL COMMENT '订单数量',
  `order_amount` decimal(18,2) DEFAULT NULL COMMENT '订单日期',
  `order_date` varchar(14) DEFAULT NULL COMMENT '订单日期',
  `order_unit` varchar(30) DEFAULT NULL COMMENT '订单型号单位',
  `order_id` varchar(20) DEFAULT NULL COMMENT '订单号 格式：2019070100001',
  `order_id_item` varchar(5) DEFAULT NULL COMMENT '订单行项目',
  `material_code` varchar(20) DEFAULT NULL COMMENT '物料号',
  `material_desc_cn` varchar(200) DEFAULT NULL COMMENT '物料中文描述',
  `material_desc_en` varchar(200) DEFAULT NULL COMMENT '物料英文描述',
  `material_desc_rn` varchar(200) DEFAULT NULL COMMENT '物料俄文描述',
  `material_unit` varchar(30) DEFAULT NULL COMMENT '物料单位',
  `material_amount` decimal(18,2) DEFAULT NULL COMMENT '物料数量',
  `material_category` varchar(30) DEFAULT NULL COMMENT '物料类别',
  `material_relation` varchar(30) DEFAULT NULL COMMENT '换算关系',
  `material_relation_unit` varchar(30) DEFAULT NULL COMMENT '换算后单位',
  `material_relation_quantity` decimal(18,2) DEFAULT NULL COMMENT '换算后数量',
  `material_minpackage_type` varchar(30) DEFAULT NULL COMMENT '最小包装类型',
  `material_minpackage_amt` decimal(18,2) DEFAULT NULL COMMENT '最小包装数量',
  `material_minpackage_totalamt` double(18,2) DEFAULT NULL COMMENT '最小包装总量',
  `material_tax_price` decimal(18,2) DEFAULT NULL COMMENT '未税单价',
  `material_tax_totalprice` decimal(18,2) DEFAULT NULL COMMENT '未税总价',
  `material_vat_price` decimal(18,2) DEFAULT NULL COMMENT '含税单价',
  `material_vat_totalprice` decimal(18,2) DEFAULT NULL COMMENT '含税总价',
  `material_rate` decimal(18,2) DEFAULT NULL COMMENT '代理费率',
  `material_currency` varchar(30) DEFAULT NULL COMMENT '币种',
  `country_code` varchar(30) DEFAULT NULL COMMENT '国家标志',
  `confirm_status` varchar(30) DEFAULT NULL COMMENT '状态',
  `order_out_total_amount` decimal(18,2) DEFAULT NULL COMMENT '型号发货总数量',
  `mater_out_total_amount` decimal(18,2) DEFAULT NULL COMMENT '子件发货总数量',
  `residual_amount` decimal(18,2) DEFAULT NULL COMMENT '剩余数量',
  `trim_amount` decimal(18,2) DEFAULT NULL COMMENT '调整后数量',
  `stock_amount` decimal(18,2) DEFAULT NULL COMMENT '库存数量',
  `differ_amount` decimal(18,2) DEFAULT NULL COMMENT '差异数量',
  `take_over_amount` decimal(18,2) DEFAULT NULL COMMENT '收货数量',
  `delivery_amount` decimal(18,2) DEFAULT NULL COMMENT '发货数量',
  `surplus_amount` decimal(18,2) DEFAULT NULL COMMENT '剩余数量',
  `data_role_at` varchar(30) DEFAULT NULL COMMENT '数据所属',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(14) DEFAULT NULL COMMENT '更新时间',
  `is_delete` int(1) DEFAULT NULL COMMENT '逻辑删除',
  `version` int(20) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_order_detail_info
-- ----------------------------
INSERT INTO `t_order_detail_info` VALUES ('1', '1', '1', '1.00', '20190612000000', '1', '111', '222', '0069000241HNR', '灯罩-PS', 'LampCover(HRF-250E)', ' Циндао синь Бовен (10 из хуандао) ', '1', '10.00', '1', '1', '1', '1.00', '1', '1.00', '1.00', '1.00', '1.00', '1.00', '1.00', '1.00', '1', '1', '1', '1.00', '1.00', '1.00', '1.00', '1.00', '1.00', '1.00', '1.00', '1.00', '1', null, null, 'admin', '20190630140620', '0', '2');
INSERT INTO `t_order_detail_info` VALUES ('2', '1', '2', '1.00', '20190612000000', '1', '111', '1111', '0069000241HNR', '灯罩-PS', 'LampCover(HRF-250E)', ' Циндао синь Бовен (10 из хуандао) ', '1', '20.00', '1', '1', '1', '1.00', '1', '1.00', '1.00', '1.00', '1.00', '1.00', '1.00', '1.00', '1', '1', '1', '1.00', '1.00', '1.00', '1.00', '1.00', '1.00', '1.00', '1.00', '1.00', '1', '1', '20190629111111', 'admin', '20190630140654', '0', '1');
INSERT INTO `t_order_detail_info` VALUES ('3efcb4e46746464684ad6d62ea3f340f', 'BB09R1E4Z', 'C2FE636CWJRU1（散件）', '19.00', '20190706120000', '1', '20190629000021', '0002', '0060732376HNR', '冷凝器-无', 'Condensor(HRF-250E)', ' Циндао синь Бовен (4 из хуандао) ', '1', '57.00', '1', '1', '1', '57.00', null, '10.00', '5.70', '3.00', '171.00', '3.39', '193.23', '0.10', '1', null, '0', '0.00', '0.00', '0.00', null, '0.00', '0.00', '0.00', '0.00', '0.00', '0', 'admin', '20190629180625', null, null, '0', '1');
INSERT INTO `t_order_detail_info` VALUES ('597ffcbe98d74ff0a8219beb23c639f4', '1', '1', '1.00', '20190612000000', '1', '22', '333', '0069000241HNR', '灯罩-PS', 'LampCover(HRF-250E)', ' Циндао синь Бовен (10 из хуандао) ', '1', '1.00', '1', '1', '1', null, null, '10.00', null, '9.00', '1.00', '10.00', '1.00', '0.10', '1', '1', '1', null, null, null, '1.00', '1.00', '1.00', '2.00', '2.00', '2.00', '0', null, null, 'admin', '20190630140620', '0', '4');
INSERT INTO `t_order_detail_info` VALUES ('afe25137a0d54112b1c7c1cfd7c959f3', 'BB09R1E4Z', 'C2FE636CWJRU1（散件）', '19.00', '20190706120000', '1', '20190629000021', '0001', '0069000241HNR', '灯罩-PS', 'LampCover(HRF-250E)', ' Циндао синь Бовен (10 из хуандао) ', '1', '42.75', '1', '1', '1', '42.75', null, '10.00', '4.28', '9.00', '384.75', '10.17', '434.77', '0.10', '1', null, '1', '0.00', '0.00', '0.00', null, '0.00', '0.00', '0.00', '0.00', '0.00', '0', 'admin', '20190629180625', 'admin', '20190630140620', '0', '1');

-- ----------------------------
-- Table structure for t_order_info
-- ----------------------------
DROP TABLE IF EXISTS `t_order_info`;
CREATE TABLE `t_order_info` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `order_code` varchar(20) NOT NULL COMMENT '订单产品型号',
  `order_code_desc` varchar(200) DEFAULT NULL COMMENT '订单产品型号描述',
  `order_amount` decimal(18,2) DEFAULT NULL COMMENT '订单数量',
  `order_date` varchar(14) DEFAULT NULL COMMENT '订单日期',
  `order_unit` varchar(30) DEFAULT NULL COMMENT '订单型号单位',
  `order_status` varchar(30) DEFAULT NULL COMMENT '计划状态 0已导入未生成，1已生成关联数据字典中计划状态',
  `data_role_at` varchar(30) DEFAULT NULL COMMENT '数据所属',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(14) DEFAULT NULL COMMENT '更新时间',
  `is_delete` int(1) DEFAULT NULL COMMENT '逻辑删除',
  `version` int(20) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_order_info
-- ----------------------------
INSERT INTO `t_order_info` VALUES ('098ed106aaf54ac19dca846747daf2c4', '1111222', '1231231212', '123.00', '20190612000000', '1', '0', '0', 'admin', '20190625120616', 'admin', '20190625120639', '0', '1');
INSERT INTO `t_order_info` VALUES ('1', '1', '1', '1.00', '20190612000000', '1', '0', '0', '1', '20190622010101', 'admin', '20190622200611', '0', '1');
INSERT INTO `t_order_info` VALUES ('17784a3d23a24eeab4365b5ebd8e5923', 'BB09R7EAC', 'C2FE636CFJRU1（散件）', '10.00', '20190706120000', '1', '0', '0', 'admin', '20190625170640', null, null, '0', '1');
INSERT INTO `t_order_info` VALUES ('1b8f5b89f01146a5bcc803980540edf6', 'BB09R7EAC', 'C2FE636CCJRU1（散件）', '12.00', '20190706120000', '1', '0', '0', 'admin', '20190625170640', null, null, '0', '1');
INSERT INTO `t_order_info` VALUES ('2', '1', '1', '1.00', '11', '11', '11', '0', '1', null, 'admin', '20190625120639', '1', '1');
INSERT INTO `t_order_info` VALUES ('2ec5dd2df5a24d559c4cd3a02ab2cfee', 'BB09RBEAC', 'C2FE536CWJERU1（散件）', '11.00', '20190706120000', '1', '0', '0', 'admin', '20190625170640', null, null, '0', '1');
INSERT INTO `t_order_info` VALUES ('328a6033c4904896989db9e163faf558', 'BB09R7EAC', 'C2FE636COJRU1（散件）', '13.00', '20190706120000', '1', '0', '0', 'admin', '20190625170640', null, null, '0', '1');
INSERT INTO `t_order_info` VALUES ('73aad4a3963c4d049dcde4d3476d265e', 'BB09R7EAC', 'C2FE636CXJRU1（散件）', '10.00', '20190706120000', '1', '0', '0', 'admin', '20190625170640', null, null, '0', '1');
INSERT INTO `t_order_info` VALUES ('9018f4491244459ba795692cad653224', 'BB09R1E4Z', 'C2FE636CWJRU1（散件）', '19.00', '20190706120000', '1', '1', '0', 'admin', '20190625170640', 'admin', null, '0', '1');
INSERT INTO `t_order_info` VALUES ('b25c5ea308654f8f9f69fe9eb0dba08e', 'BB09R3EAA', 'C2F536CMSG（散件）', '650.00', '20190706120000', '1', '0', '0', 'admin', '20190625170640', null, null, '0', '1');
INSERT INTO `t_order_info` VALUES ('ccaba117263448d6b05ed5371a4ffb43', '111111111', '11111111', '32123.00', '20190710000000', '1', '0', '0', null, null, 'admin', '20190622200606', '0', '5');
INSERT INTO `t_order_info` VALUES ('d06696637c5848b39382629f060522ab', '12421', '421412321', '4214211.00', '20190624000000', '1', '0', '0', 'admin', '20190622190623', 'admin', '20190622200653', '0', '1');
INSERT INTO `t_order_info` VALUES ('d330f9062d4e4adfb15becaae4f08a68', 'BB09R1E4Z', 'C2F536CWFG(散件)', '10.00', '20190706120000', '1', '0', '0', 'admin', '20190625170640', null, null, '0', '1');

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
INSERT INTO `t_role_function_relation` VALUES ('e4ff2aa294c511e9915c0a0027000012', '10001', '1', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff4fe694c511e9915c0a0027000012', '10001', '2', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff51e894c511e9915c0a0027000012', '10001', '5', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff525394c511e9915c0a0027000012', '10001', '6', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff529a94c511e9915c0a0027000012', '10001', '17', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff531894c511e9915c0a0027000012', '10001', '18', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff535b94c511e9915c0a0027000012', '10001', '19', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff539394c511e9915c0a0027000012', '10001', '20', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff53d294c511e9915c0a0027000012', '10001', '21', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff540994c511e9915c0a0027000012', '10001', '22', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff547494c511e9915c0a0027000012', '10001', '23', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff54b394c511e9915c0a0027000012', '10001', '7', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff54ee94c511e9915c0a0027000012', '10001', '24', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff553994c511e9915c0a0027000012', '10001', '25', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff558894c511e9915c0a0027000012', '10001', '26', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff565a94c511e9915c0a0027000012', '10001', '27', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff56ec94c511e9915c0a0027000012', '10001', '28', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff574794c511e9915c0a0027000012', '10001', '29', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff579e94c511e9915c0a0027000012', '10001', '34', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff57d994c511e9915c0a0027000012', '10001', '35', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff581094c511e9915c0a0027000012', '10001', '36', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff584c94c511e9915c0a0027000012', '10001', '37', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff587f94c511e9915c0a0027000012', '10001', '38', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff58b694c511e9915c0a0027000012', '10001', '11', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff58f294c511e9915c0a0027000012', '10001', '12', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff592d94c511e9915c0a0027000012', '10001', '50', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff596494c511e9915c0a0027000012', '10001', '13', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff59a794c511e9915c0a0027000012', '10001', '14', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff5a4594c511e9915c0a0027000012', '10001', '15', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff5ac894c511e9915c0a0027000012', '10001', '16', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff5b0794c511e9915c0a0027000012', '10001', '8', '10001', '20190622160616', null, null, '0', '0');
INSERT INTO `t_role_function_relation` VALUES ('e4ff5b3e94c511e9915c0a0027000012', '10001', '9', '10001', '20190622160616', null, null, '0', '0');

-- ----------------------------
-- Table structure for t_stock_info
-- ----------------------------
DROP TABLE IF EXISTS `t_stock_info`;
CREATE TABLE `t_stock_info` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `material_code` varchar(32) DEFAULT NULL COMMENT '物料号',
  `material_category` varchar(30) DEFAULT NULL COMMENT '物料类别',
  `material_desc_cn` varchar(200) DEFAULT NULL COMMENT '物料中文描述',
  `material_desc_en` varchar(200) DEFAULT NULL COMMENT '物料英文描述',
  `material_desc_rn` varchar(200) DEFAULT NULL COMMENT '物料俄文描述',
  `stock_amount` decimal(18,2) DEFAULT NULL COMMENT '库存数量',
  `data_role_at` varchar(30) DEFAULT NULL COMMENT '数据所属',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(14) DEFAULT NULL COMMENT '更新时间',
  `is_delete` int(1) DEFAULT NULL COMMENT '逻辑删除',
  `version` int(10) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_stock_info
-- ----------------------------
INSERT INTO `t_stock_info` VALUES ('a83eed2232c2451bb96e263dbbee987d', '11111111112', '2', '12', '123', '12333333333333', '12.00', '0', null, null, 'admin', '20190701140725', '1', '4');
INSERT INTO `t_stock_info` VALUES ('c63010c4715442068f5d44ec8e4f0a49', '1111', '1', '2341234', '23124', '32142', '32142143.00', '0', 'admin', '20190630193055', 'admin', '20190701140725', '1', '1');

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

-- ----------------------------
-- Procedure structure for atuoNo
-- ----------------------------
DROP PROCEDURE IF EXISTS `atuoNo`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `atuoNo`( in prefix VARCHAR(20),-- 前缀
												 in modeType int , -- 日期模式
												 in nolen int ,  -- 流水号长度
												 in listna varchar(20), -- 流水号对应的列名 
												 in tablena varchar(50), -- 流水号对应的表名
												 out runningnum varchar(20) -- 生成的流水码
)
    COMMENT '自动生成流水账号 前缀+日期+流水号'
begin
      DECLARE currentDate CHAR (13) ;-- 定义currentDate为当前日期,格式为：年+月+日
			DECLARE Maxnum int  ;-- 获取表中最大的流水号
			IF modeType=0 THEN
					SELECT DATE_FORMAT(NOW(), '%Y%m%d') INTO currentDate ;-- 当前日期  订单编号形式:年月日+流水号，如：2018042600002 
					set @v_sql=CONCAT('select ifnull(Max(CONVERT(SUBSTRING(',listna,',-',nolen,'),SIGNED)),0)+1 into @Maxnum from ',tablena ,' where SUBSTRING(',listna,', 1, 8) = ',currentDate);  -- 拼写查询相关表中最近的流水单号

		ELSEIF modeType =1 THEN
				SELECT DATE_FORMAT(NOW(), '%Y%m') INTO currentDate ;-- 当前日期  订单编号形式:年月+流水号，如：20180400002 
				set @v_sql=CONCAT('select ifnull(Max(CONVERT(SUBSTRING(',listna,',-',nolen,'),SIGNED)),0)+1 into @Maxnum from ',tablena ,' where SUBSTRING(',listna,', 1, 6) = ',currentDate);  -- 拼写查询相关表中最近的流水单号

		ELSEIF modeType =2 THEN
				SELECT DATE_FORMAT(NOW(), '%Y') INTO currentDate ;-- 当前日期  订单编号形式:年月+流水号，如：201800002 
				set @v_sql=CONCAT('select ifnull(Max(CONVERT(SUBSTRING(',listna,',-',nolen,'),SIGNED)),0)+1 into @Maxnum from ',tablena ,' where SUBSTRING(',listna,', 1, 4) = ',currentDate);  -- 拼写查询相关表中最近的流水单号

		END IF;
		prepare stmt from @v_sql;  -- 预处理需要执行的动态SQL，其中stmt是一个变量
		EXECUTE stmt;      -- 执行SQL语句 
		deallocate prepare stmt;     -- 释放掉预处理段   
		set Maxnum=@Maxnum;
		set runningnum =CONCAT(prefix,currentDate,  LPAD((Maxnum), nolen, '0')) ; -- LPAD((maxNo + 1), 5, '0')：如果不足5位，将用0填充左边     
    end
;;
DELIMITER ;

-- ----------------------------
-- Function structure for get_trans_num
-- ----------------------------
DROP FUNCTION IF EXISTS `get_trans_num`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `get_trans_num`() RETURNS varchar(20) CHARSET utf8mb4
BEGIN
DECLARE getval VARCHAR(20);
SET getval = (SELECT CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), LPAD((SELECT next_trans_num('trans_no')), 6, '0')));
RETURN getval;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for next_trans_num
-- ----------------------------
DROP FUNCTION IF EXISTS `next_trans_num`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `next_trans_num`(seq_name  varchar(32)) RETURNS int(6)
BEGIN
 UPDATE sequence SET value=last_insert_id(value+next) WHERE name=seq_name;
 RETURN last_insert_id();
END
;;
DELIMITER ;
