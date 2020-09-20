/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1_3306
Source Server Version : 50721
Source Host           : 127.0.0.1:3306
Source Database       : jn-imes

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2020-07-15 09:25:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bs_user
-- ----------------------------
DROP TABLE IF EXISTS `bs_user`;
CREATE TABLE `bs_user` (
  `id` varchar(40) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `work_number` varchar(255) DEFAULT '',
  `enable_state` tinyint(4) NOT NULL DEFAULT '1',
  `company_id` varchar(40) DEFAULT NULL,
  `department_id` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
  `description` text,
  `mobile` varchar(255) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of bs_user
-- ----------------------------
INSERT INTO `bs_user` VALUES ('1270561573529849856', '张杨', '6821e207641db05e9280188f189f2387', '123', '1', '1269823707984039936', '1269913030632804352', '2020-06-10 03:41:00', '0', null, '13752560882', 'https://zhg-test1.oss-cn-beijing.aliyuncs.com/images/2020/06/30/1270561573529849856.jpg');
INSERT INTO `bs_user` VALUES ('1272131119663419392', 'user', 'ea6151b14a3b64331d6c33e645fccef6', '102', '1', '1269823707984039936', '1270163144282607616', '2020-06-14 11:37:49', '0', null, '13752560882', null);
INSERT INTO `bs_user` VALUES ('1274250614095876096', 'admin', '856aea89ad509f163284abb75579dcfc', '222', '1', '1269823707984039936', '1269913030632804352', '2020-06-20 07:59:55', '0', null, '19900000', null);
INSERT INTO `bs_user` VALUES ('1274250614179762176', 'customer', '69ef3723465c3031f774e8ff83c67f21', '11', '1', '1269823707984039936', '1269913030632804352', '2020-06-20 07:59:56', '1', null, '19900000', null);
INSERT INTO `bs_user` VALUES ('1274710090557034496', 'customer', '69ef3723465c3031f774e8ff83c67f21', '11', '1', '1269823707984039936', '1269913030632804352', '2020-06-21 14:25:43', '0', null, '19900000', null);

-- ----------------------------
-- Table structure for co_company
-- ----------------------------
DROP TABLE IF EXISTS `co_company`;
CREATE TABLE `co_company` (
  `id` varchar(40) NOT NULL COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '公司名称',
  `manager_id` varchar(255) NOT NULL COMMENT '企业登录账号ID',
  `version` varchar(255) DEFAULT NULL COMMENT '当前版本',
  `renewal_date` datetime DEFAULT NULL COMMENT '续期时间',
  `expiration_date` datetime DEFAULT NULL COMMENT '到期时间',
  `company_area` varchar(255) DEFAULT NULL COMMENT '公司地区',
  `company_address` text COMMENT '公司地址',
  `business_license_id` varchar(255) DEFAULT NULL COMMENT '营业执照-图片ID',
  `legal_representative` varchar(255) DEFAULT NULL COMMENT '法人代表',
  `company_phone` varchar(255) DEFAULT NULL COMMENT '公司电话',
  `mailbox` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `company_size` varchar(255) DEFAULT NULL COMMENT '公司规模',
  `industry` varchar(255) DEFAULT NULL COMMENT '所属行业',
  `remarks` text COMMENT '备注',
  `audit_state` varchar(255) DEFAULT NULL COMMENT '审核状态',
  `state` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态',
  `balance` double DEFAULT NULL COMMENT '当前余额',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
  `liaison` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of co_company
-- ----------------------------
INSERT INTO `co_company` VALUES ('1269823707984039936', '天津Imes智能设备有限公司', 'tjimes.com', '1.0', null, '2020-06-30 02:47:51', '天津', null, null, '张杨', '13752560882', '13752560882@163.com', '40人', '工业数字化，物联网', '用于数据测试', '未审核', '1', '0', '2020-06-08 02:48:59', '0', '张杨');

-- ----------------------------
-- Table structure for co_department
-- ----------------------------
DROP TABLE IF EXISTS `co_department`;
CREATE TABLE `co_department` (
  `id` varchar(40) NOT NULL,
  `company_id` varchar(255) NOT NULL COMMENT '企业ID',
  `parent_id` varchar(255) DEFAULT NULL COMMENT '父级部门ID',
  `name` varchar(255) NOT NULL COMMENT '部门名称',
  `code` varchar(255) NOT NULL COMMENT '部门编码',
  `category` varchar(255) DEFAULT NULL COMMENT '部门类别',
  `manager_id` varchar(255) DEFAULT NULL COMMENT '负责人ID',
  `city` varchar(255) DEFAULT NULL COMMENT '城市',
  `introduce` text COMMENT '介绍',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `manager` varchar(40) DEFAULT NULL COMMENT '部门负责人',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of co_department
-- ----------------------------
INSERT INTO `co_department` VALUES ('1269913030632804352', '1269823707984039936', '0', '开发部', '0001', '重要', '1', '天津', '用于测试', '2020-06-08 08:43:55', 'zhangsan', '0');
INSERT INTO `co_department` VALUES ('1270163144282607616', '1269823707984039936', '1269913030632804352', '前端开发', '10001', '开发', '2', '天津', null, '2020-06-09 01:17:47', 'lisi', '0');

-- ----------------------------
-- Table structure for co_device_connection
-- ----------------------------
DROP TABLE IF EXISTS `co_device_connection`;
CREATE TABLE `co_device_connection` (
  `company_id` varchar(40) NOT NULL,
  `influx_ip` varchar(255) DEFAULT NULL,
  `influx_port` int(11) DEFAULT NULL,
  `influx_retention_policy` varchar(255) DEFAULT NULL,
  `influx_database` varchar(255) DEFAULT NULL,
  `mqtt_service_ip` varchar(255) NOT NULL,
  `mqtt_service_port` int(11) NOT NULL,
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of co_device_connection
-- ----------------------------
INSERT INTO `co_device_connection` VALUES ('1269823707984039936', '182.92.194.48', '8806', 'autogen', 'telegraf', '127.0.0.1', '10001');

-- ----------------------------
-- Table structure for de_device
-- ----------------------------
DROP TABLE IF EXISTS `de_device`;
CREATE TABLE `de_device` (
  `id` varchar(40) NOT NULL COMMENT '设备id',
  `company_id` varchar(40) NOT NULL COMMENT '用户公司id',
  `name` varchar(255) NOT NULL COMMENT '设备名称',
  `code` varchar(255) NOT NULL COMMENT '设备编号',
  `device_usage` varchar(255) DEFAULT NULL COMMENT '用途',
  `function` varchar(255) DEFAULT NULL COMMENT '功能',
  `supplier_id` varchar(40) NOT NULL COMMENT '设备提供商id',
  `template_id` varchar(40) DEFAULT NULL,
  `gateway_id` varchar(255) DEFAULT NULL,
  `description` text,
  `is_deleted` tinyint(4) NOT NULL COMMENT '删除标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of de_device
-- ----------------------------
INSERT INTO `de_device` VALUES ('1279234271491526656', '1269823707984039936', '折弯机', 'JC12-001', '用于钢板折弯', '折弯', '1279230957437718528', '1281965130510372864', '178015235789A6938', null, '0');

-- ----------------------------
-- Table structure for de_device_current
-- ----------------------------
DROP TABLE IF EXISTS `de_device_current`;
CREATE TABLE `de_device_current` (
  `id` varchar(40) NOT NULL COMMENT '设备状况id',
  `device_id` varchar(40) NOT NULL COMMENT '设备id',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '设备状态，0=关机，1=待机，2=运行',
  `yield` double(10,2) NOT NULL COMMENT '产量实时值',
  `now_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of de_device_current
-- ----------------------------

-- ----------------------------
-- Table structure for de_device_daily
-- ----------------------------
DROP TABLE IF EXISTS `de_device_daily`;
CREATE TABLE `de_device_daily` (
  `id` varchar(40) NOT NULL,
  `device_id` varchar(40) NOT NULL COMMENT '设备id',
  `status_stop_hour` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '设备每日停机小时数',
  `status_await_hour` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '设备每日待机小时数',
  `status_fault_hour` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '每日故障小时数',
  `status_run_hour` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '每日运行小时数',
  `yield_summary` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '每日产能累计',
  `date` date NOT NULL COMMENT '统计日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of de_device_daily
-- ----------------------------
INSERT INTO `de_device_daily` VALUES ('1279402179337261056', '1279234271491526656', '1.00', '2.00', '1.00', '12.00', '100.00', '2020-07-04');

-- ----------------------------
-- Table structure for de_device_property
-- ----------------------------
DROP TABLE IF EXISTS `de_device_property`;
CREATE TABLE `de_device_property` (
  `id` varchar(40) NOT NULL,
  `template_id` varchar(40) NOT NULL,
  `name` varchar(255) NOT NULL,
  `variable_address` varchar(255) NOT NULL,
  `variable_type` varchar(255) NOT NULL,
  `category` int(11) NOT NULL DEFAULT '1' COMMENT '// 1 = platform read from gateway, 2 = platform write to gateway',
  `description` varchar(255) DEFAULT NULL,
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of de_device_property
-- ----------------------------
INSERT INTO `de_device_property` VALUES ('1282236228565078016', '1281955369958117376', '测试接收属性1', 'D1002', 'float', '1', '测试接收属性1', '0');
INSERT INTO `de_device_property` VALUES ('1282938744109928448', '1281965130510372864', '测试数据', 'D1002', 'float', '1', '测试数据', '0');

-- ----------------------------
-- Table structure for de_device_supplier
-- ----------------------------
DROP TABLE IF EXISTS `de_device_supplier`;
CREATE TABLE `de_device_supplier` (
  `id` varchar(40) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '供货商名称',
  `short_name` varchar(255) DEFAULT NULL COMMENT '简称',
  `code` varchar(255) DEFAULT NULL COMMENT '供货商代码',
  `liaison_name` varchar(255) DEFAULT NULL COMMENT '联系人姓名',
  `phone` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `description` text COMMENT '备注',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识',
  `company_id` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of de_device_supplier
-- ----------------------------
INSERT INTO `de_device_supplier` VALUES ('1279230957437718528', '山东路腾设备有限公司', '路腾', 'S001', '张骏', '13760837228', '主要设备供货商', '0', '1269823707984039936');

-- ----------------------------
-- Table structure for de_device_template
-- ----------------------------
DROP TABLE IF EXISTS `de_device_template`;
CREATE TABLE `de_device_template` (
  `id` varchar(40) NOT NULL,
  `name` varchar(255) NOT NULL,
  `avatar` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of de_device_template
-- ----------------------------
INSERT INTO `de_device_template` VALUES ('1281955369958117376', '剪切机模板', 'https://zhg-test1.oss-cn-beijing.aliyuncs.com/images/2020/07/11/1281955279205961728.jpg', '剪切机模板，用于系统开发测试', '0');
INSERT INTO `de_device_template` VALUES ('1281965130510372864', '折弯机模板', 'https://zhg-test1.oss-cn-beijing.aliyuncs.com/images/2020/07/11/1281964932052684800.jpg', '折弯机模板，用于系统测试，定义设备的属性及连接', '0');

-- ----------------------------
-- Table structure for pe_permission
-- ----------------------------
DROP TABLE IF EXISTS `pe_permission`;
CREATE TABLE `pe_permission` (
  `id` varchar(40) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` int(11) NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `parent_id` varchar(40) NOT NULL,
  `system_visible` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of pe_permission
-- ----------------------------
INSERT INTO `pe_permission` VALUES ('1271359703879913472', '企业管理', '1', 'company', '由Saas管理员管理', '0', '0');
INSERT INTO `pe_permission` VALUES ('1271704604874248192', '权限管理', '1', 'system', null, '0', '1');
INSERT INTO `pe_permission` VALUES ('1271704780519116800', '部门管理', '1', 'system_department', null, '1271704604874248192', '1');
INSERT INTO `pe_permission` VALUES ('1271704904712458240', '用户管理', '1', 'system_user', null, '1271704604874248192', '1');
INSERT INTO `pe_permission` VALUES ('1271705222980440064', '角色管理', '1', 'system_role', null, '1271704604874248192', '1');
INSERT INTO `pe_permission` VALUES ('1271705320980353024', '权限资源', '1', 'system_permission', null, '1271704604874248192', '1');

-- ----------------------------
-- Table structure for pe_permission_api
-- ----------------------------
DROP TABLE IF EXISTS `pe_permission_api`;
CREATE TABLE `pe_permission_api` (
  `id` varchar(40) NOT NULL,
  `api_url` varchar(255) NOT NULL,
  `api_method` varchar(255) NOT NULL,
  `api_level` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of pe_permission_api
-- ----------------------------

-- ----------------------------
-- Table structure for pe_permission_menu
-- ----------------------------
DROP TABLE IF EXISTS `pe_permission_menu`;
CREATE TABLE `pe_permission_menu` (
  `id` varchar(40) NOT NULL,
  `menu_icon` varchar(255) DEFAULT NULL,
  `menu_order` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of pe_permission_menu
-- ----------------------------
INSERT INTO `pe_permission_menu` VALUES ('1271359703879913472', 'smile', '001');
INSERT INTO `pe_permission_menu` VALUES ('1271704604874248192', null, null);
INSERT INTO `pe_permission_menu` VALUES ('1271704780519116800', null, null);
INSERT INTO `pe_permission_menu` VALUES ('1271704904712458240', null, null);
INSERT INTO `pe_permission_menu` VALUES ('1271705222980440064', null, null);
INSERT INTO `pe_permission_menu` VALUES ('1271705320980353024', null, null);

-- ----------------------------
-- Table structure for pe_permission_point
-- ----------------------------
DROP TABLE IF EXISTS `pe_permission_point`;
CREATE TABLE `pe_permission_point` (
  `id` varchar(40) NOT NULL,
  `point_icon` varchar(255) DEFAULT NULL,
  `point_class` varchar(255) DEFAULT NULL,
  `point_status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of pe_permission_point
-- ----------------------------

-- ----------------------------
-- Table structure for pe_role
-- ----------------------------
DROP TABLE IF EXISTS `pe_role`;
CREATE TABLE `pe_role` (
  `id` varchar(40) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `company_id` varchar(40) DEFAULT NULL,
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of pe_role
-- ----------------------------
INSERT INTO `pe_role` VALUES ('1270607526173478912', 'saas 管理员', '用于平台维护', '1269823707984039936', '0');
INSERT INTO `pe_role` VALUES ('1271662575649689600', '企业管理员', '企业最高权限', '1269823707984039936', '0');
INSERT INTO `pe_role` VALUES ('1271662743530901504', '操作权限', '操作人员权限', '1269823707984039936', '0');
INSERT INTO `pe_role` VALUES ('1271662848627576832', '设备维护', '设备日常维护权限', '1269823707984039936', '0');

-- ----------------------------
-- Table structure for pe_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `pe_role_permission`;
CREATE TABLE `pe_role_permission` (
  `id` varchar(40) NOT NULL,
  `role_id` varchar(40) NOT NULL,
  `permission_id` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of pe_role_permission
-- ----------------------------
INSERT INTO `pe_role_permission` VALUES ('1271703754483306496', '1270607526173478912', '1271359703879913472');
INSERT INTO `pe_role_permission` VALUES ('1271705383366430720', '1270607526173478912', '1271704780519116800');
INSERT INTO `pe_role_permission` VALUES ('1271705383383207936', '1270607526173478912', '1271705222980440064');
INSERT INTO `pe_role_permission` VALUES ('1271705383404179456', '1270607526173478912', '1271705320980353024');
INSERT INTO `pe_role_permission` VALUES ('1271705383420956672', '1270607526173478912', '1271704604874248192');
INSERT INTO `pe_role_permission` VALUES ('1271705383437733888', '1270607526173478912', '1271359703879913472');
INSERT INTO `pe_role_permission` VALUES ('1271705383454511104', '1270607526173478912', '1271704904712458240');
INSERT INTO `pe_role_permission` VALUES ('1271705384494698496', '1270607526173478912', '1271704780519116800');
INSERT INTO `pe_role_permission` VALUES ('1271705384524058624', '1270607526173478912', '1271705222980440064');
INSERT INTO `pe_role_permission` VALUES ('1271705384553418752', '1270607526173478912', '1271705320980353024');
INSERT INTO `pe_role_permission` VALUES ('1271705384574390272', '1270607526173478912', '1271704604874248192');
INSERT INTO `pe_role_permission` VALUES ('1271705384586973184', '1270607526173478912', '1271359703879913472');
INSERT INTO `pe_role_permission` VALUES ('1271705384612139008', '1270607526173478912', '1271704904712458240');
INSERT INTO `pe_role_permission` VALUES ('1272117743071662080', '1271662575649689600', '1271704604874248192');
INSERT INTO `pe_role_permission` VALUES ('1272117798704910336', '1271662575649689600', '1271704780519116800');
INSERT INTO `pe_role_permission` VALUES ('1272117798721687552', '1271662575649689600', '1271705222980440064');
INSERT INTO `pe_role_permission` VALUES ('1272117798738464768', '1271662575649689600', '1271705320980353024');
INSERT INTO `pe_role_permission` VALUES ('1272117798759436288', '1271662575649689600', '1271704604874248192');
INSERT INTO `pe_role_permission` VALUES ('1272117798784602112', '1271662575649689600', '1271704904712458240');

-- ----------------------------
-- Table structure for pe_user_role
-- ----------------------------
DROP TABLE IF EXISTS `pe_user_role`;
CREATE TABLE `pe_user_role` (
  `id` varchar(40) NOT NULL,
  `user_id` varchar(40) NOT NULL,
  `role_id` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of pe_user_role
-- ----------------------------
INSERT INTO `pe_user_role` VALUES ('1272131568579776512', '1272131119663419392', '1271662575649689600');
INSERT INTO `pe_user_role` VALUES ('1272131702533263360', '1270561573529849856', '1270607526173478912');

-- ----------------------------
-- Table structure for pj_frame_member
-- ----------------------------
DROP TABLE IF EXISTS `pj_frame_member`;
CREATE TABLE `pj_frame_member` (
  `id` varchar(40) NOT NULL,
  `name` varchar(255) NOT NULL,
  `project_id` varchar(40) NOT NULL,
  `progress` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '进度百分比，0-100',
  `type_id` varchar(40) NOT NULL DEFAULT '0000' COMMENT '构件类型，//todo',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of pj_frame_member
-- ----------------------------
INSERT INTO `pj_frame_member` VALUES ('1280058242088701952', '构件1', '1280047269860741120', '30.00', '1', '0', null);
INSERT INTO `pj_frame_member` VALUES ('1280059460919889920', '构件2', '1280047269860741120', '60.00', '1', '0', null);
INSERT INTO `pj_frame_member` VALUES ('1280059511096348672', '构件3', '1280047269860741120', '10.00', '1', '0', null);
INSERT INTO `pj_frame_member` VALUES ('1280059565689409536', '构件4', '1280047269860741120', '100.00', '1', '0', null);

-- ----------------------------
-- Table structure for pj_project
-- ----------------------------
DROP TABLE IF EXISTS `pj_project`;
CREATE TABLE `pj_project` (
  `id` varchar(40) NOT NULL,
  `name` varchar(255) NOT NULL,
  `company_id` varchar(40) NOT NULL,
  `description` text,
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of pj_project
-- ----------------------------
INSERT INTO `pj_project` VALUES ('1280047269860741120', '项目一', '1269823707984039936', '测试项目', '0');
