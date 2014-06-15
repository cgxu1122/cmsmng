/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2014/6/15 14:03:32                           */
/*==============================================================*/


drop index "Index_5";

drop index "Index_6";

drop index "Index_3";

drop index "Index_4";

drop index "Index_7";

drop index "Index_8";

drop index "Index_9";

drop index "Index_10";

drop index "Index_11";

drop index "Index_2";

drop index "Index_1";

drop table TY_APK_INFO cascade constraints;

drop table TY_BATCH_INFO cascade constraints;

drop table TY_BATCH_PRODUCT_REF cascade constraints;

drop table TY_CHANNEL_GROUP cascade constraints;

drop table TY_CHANNEL_INFO cascade constraints;

drop table TY_COUNTER_FAIL_LOG cascade constraints;

drop table TY_COUNTER_UPLOAD_LOG cascade constraints;

drop table TY_DEVICE_INFO cascade constraints;

drop table TY_DEVICE_PROCESS_LOG cascade constraints;

drop table TY_DEVICE_SYSTEM cascade constraints;

drop table TY_DICT_INFO cascade constraints;

drop table TY_MODEL_CHANNEL_REF cascade constraints;

drop table TY_MODEL_INFO cascade constraints;

drop table TY_PACKAGE_APK_REF cascade constraints;

drop table TY_PACKAGE_INFO cascade constraints;

drop table TY_PARTNER_INFO cascade constraints;

drop table TY_PRODUCT_INFO cascade constraints;

drop table TY_PUBLISH_TASK cascade constraints;

drop table TY_PUB_CHL_MOD_REF cascade constraints;

drop table TY_RESOURCE cascade constraints;

drop table TY_ROLE cascade constraints;

drop table TY_ROLE_RESOURCE_REF cascade constraints;

drop table TY_SETTLE_INFO cascade constraints;

drop table TY_USER cascade constraints;

drop table TY_USER_ROLE_REF cascade constraints;

drop sequence SEQ_BATCH_INFO;

drop sequence SEQ_BATCH_INFO_DB;

drop sequence SEQ_BATCH_INFO_QT;

drop sequence SEQ_BATCH_INFO_TY;

drop sequence SEQ_CHANNEL_GROUP;

drop sequence SEQ_CHANNEL_INFO;

drop sequence SEQ_DEVICE_INFO;

drop sequence SEQ_DEVICE_SYSTEM;

drop sequence SEQ_MODEL_INFO;

drop sequence SEQ_PACKAGE_INFO;

drop sequence SEQ_PARTNER_INFO;

drop sequence SEQ_PRODUCT_INFO;

drop sequence SEQ_TY_COUNTER_FAIL_LOG;

drop sequence SEQ_TY_COUNTER_UPLOAD_LOG;

drop sequence SEQ_TY_DEVICE_PROCESS_LOG;

drop sequence SEQ_TY_DICT_INFO;

drop sequence SEQ_TY_RESOURCE;

drop sequence SEQ_TY_ROLE;

drop sequence SEQ_TY_ROLE_RESOURCE_REF;

drop sequence SEQ_TY_USER;

drop sequence SEQ_TY_USER_ROLE_REF;

create sequence SEQ_BATCH_INFO;

create sequence SEQ_BATCH_INFO_DB;

create sequence SEQ_BATCH_INFO_QT;

create sequence SEQ_BATCH_INFO_TY;

create sequence SEQ_CHANNEL_GROUP;

create sequence SEQ_CHANNEL_INFO;

create sequence SEQ_DEVICE_INFO;

create sequence SEQ_DEVICE_SYSTEM;

create sequence SEQ_MODEL_INFO;

create sequence SEQ_PACKAGE_INFO;

create sequence SEQ_PARTNER_INFO;

create sequence SEQ_PRODUCT_INFO;

create sequence SEQ_TY_COUNTER_FAIL_LOG;

create sequence SEQ_TY_COUNTER_UPLOAD_LOG;

create sequence SEQ_TY_DEVICE_PROCESS_LOG;

create sequence SEQ_TY_DICT_INFO;

create sequence SEQ_TY_RESOURCE;

create sequence SEQ_TY_ROLE;

create sequence SEQ_TY_ROLE_RESOURCE_REF;

create sequence SEQ_TY_USER;

create sequence SEQ_TY_USER_ROLE_REF;

/*==============================================================*/
/* Table: TY_APK_INFO                                           */
/*==============================================================*/
create table TY_APK_INFO  (
   APK_ID               NUMBER(15)                      not null,
   APK_NAME             VARCHAR2(100 CHAR),
   SOFT_NAME            VARCHAR2(100 CHAR),
   FTP_PATH             VARCHAR2(200 CHAR),
   MD5VALUE             VARCHAR2(100 CHAR),
   DOWNLOAD_URL         VARCHAR2(500 CHAR),
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE                           default SYSDATE,
   ACTIVE               VARCHAR2(2 CHAR)               default 'Y',
   constraint PK_TY_APK_INFO primary key (APK_ID)
);

comment on table TY_APK_INFO is
'APK信息表';

comment on column TY_APK_INFO.APK_ID is
'主键ID';

comment on column TY_APK_INFO.APK_NAME is
'APK名称';

comment on column TY_APK_INFO.SOFT_NAME is
'APK文件名称';

comment on column TY_APK_INFO.FTP_PATH is
'FTP路径';

comment on column TY_APK_INFO.MD5VALUE is
'md5值';

comment on column TY_APK_INFO.DOWNLOAD_URL is
'下载链接';

comment on column TY_APK_INFO.CREATE_TIME is
'创建时间';

comment on column TY_APK_INFO.UPDATE_TIME is
'修改时间';

comment on column TY_APK_INFO.ACTIVE is
'数据状态 Y:有效，N:无效';

/*==============================================================*/
/* Table: TY_BATCH_INFO                                         */
/*==============================================================*/
create table TY_BATCH_INFO  (
   BATCH_ID             NUMBER(15)                      not null,
   BATCH_CODE           VARCHAR2(50 CHAR),
   GROUP_ID             NUMBER(15),
   BATCH_PRODUCT_NAME   VARCHAR2(2000),
   BATCH_PRODUCT_NUM    NUMBER(15),
   START_TIME           DATE,
   CREATE_TIME          DATE,
   UPDATE_TIME          DATE,
   ACTIVE               VARCHAR2(2)                    default 'Y',
   constraint PK_TY_BATCH_INFO primary key (BATCH_ID)
);

comment on table TY_BATCH_INFO is
'批次信息表';

comment on column TY_BATCH_INFO.BATCH_ID is
'主键ID';

comment on column TY_BATCH_INFO.BATCH_CODE is
'批次号';

comment on column TY_BATCH_INFO.GROUP_ID is
'渠道组ID';

comment on column TY_BATCH_INFO.BATCH_PRODUCT_NAME is
'批次关联产品名称';

comment on column TY_BATCH_INFO.BATCH_PRODUCT_NUM is
'批次关联产品数量';

comment on column TY_BATCH_INFO.START_TIME is
'开始时间';

comment on column TY_BATCH_INFO.CREATE_TIME is
'创建时间';

comment on column TY_BATCH_INFO.UPDATE_TIME is
'修改时间';

comment on column TY_BATCH_INFO.ACTIVE is
'活动状态（Y:有效，D:删除）';

/*==============================================================*/
/* Table: TY_BATCH_PRODUCT_REF                                  */
/*==============================================================*/
create table TY_BATCH_PRODUCT_REF  (
   BATCH_ID             NUMBER(15)                      not null,
   PRODUCT_ID           NUMBER(15)                      not null,
   CREATE_TIME          DATE,
   constraint PK_TY_BATCH_PRODUCT_REF primary key (BATCH_ID, PRODUCT_ID)
);

comment on table TY_BATCH_PRODUCT_REF is
'批次产品映射表';

comment on column TY_BATCH_PRODUCT_REF.BATCH_ID is
'批次ID';

comment on column TY_BATCH_PRODUCT_REF.PRODUCT_ID is
'产品ID';

comment on column TY_BATCH_PRODUCT_REF.CREATE_TIME is
'创建时间';

/*==============================================================*/
/* Table: TY_CHANNEL_GROUP                                      */
/*==============================================================*/
create table TY_CHANNEL_GROUP  (
   GROUP_ID             NUMBER(15)                      not null,
   GROUP_NAME           VARCHAR2(200 CAHR),
   CREATE_TIME          DATE,
   constraint PK_CHANNEL_GROUP primary key (GROUP_ID)
);

comment on table TY_CHANNEL_GROUP is
'渠道组表';

comment on column TY_CHANNEL_GROUP.GROUP_ID is
'主键ID';

comment on column TY_CHANNEL_GROUP.GROUP_NAME is
'渠道组名称';

comment on column TY_CHANNEL_GROUP.CREATE_TIME is
'创建时间';

/*==============================================================*/
/* Table: TY_CHANNEL_INFO                                       */
/*==============================================================*/
create table TY_CHANNEL_INFO  (
   CHANNEL_ID           NUMBER(15)                      not null,
   PARENT_ID            NUMBER(15),
   GROUP_ID             NUMBER(15),
   MNG_ID               NUMBER(15),
   USER_ID              NUMBER(15),
   CHANNEL_NAME         VARCHAR(200 CHAR)),
   "DESC"               VARCHAR(500 CHAR),
   LEAF                 VARCHAR2(2 CHAR),
   TYPE                 VARCHAR2(2 CHAR),
   LAOWU_ID             NUMBER(15),
   QUERY_IMEI_SOURCE    VARCHAR2(2 CHAR),
   ACTIVE               VARCHAR2(2 CHAR)               default 'Y',
   CREATE_TIME          DATE,
   UPDATE_TIME          DATE,
   constraint PK_CHANNEL_INFO primary key (CHANNEL_ID)
);

comment on table TY_CHANNEL_INFO is
'渠道信息表';

comment on column TY_CHANNEL_INFO.CHANNEL_ID is
'主键ID';

comment on column TY_CHANNEL_INFO.PARENT_ID is
'父ID';

comment on column TY_CHANNEL_INFO.GROUP_ID is
'渠道组id';

comment on column TY_CHANNEL_INFO.MNG_ID is
'负责人id';

comment on column TY_CHANNEL_INFO.USER_ID is
'登录用户id';

comment on column TY_CHANNEL_INFO.CHANNEL_NAME is
'渠道名称';

comment on column TY_CHANNEL_INFO."DESC" is
'备注';

comment on column TY_CHANNEL_INFO.LEAF is
'是否叶子节点（Y:叶子节点，N:非叶子节点）';

comment on column TY_CHANNEL_INFO.TYPE is
'渠道类型（L:劳务公司，M:主渠道，C:子渠道，O:其他）';

comment on column TY_CHANNEL_INFO.LAOWU_ID is
'劳务公司ID';

comment on column TY_CHANNEL_INFO.QUERY_IMEI_SOURCE is
'是否可以查询IMEI（Y:可以，N:不可以）';

comment on column TY_CHANNEL_INFO.ACTIVE is
'活动状态（Y:有效，D:删除）';

comment on column TY_CHANNEL_INFO.CREATE_TIME is
'创建时间';

comment on column TY_CHANNEL_INFO.UPDATE_TIME is
'修改时间';

/*==============================================================*/
/* Table: TY_COUNTER_FAIL_LOG                                   */
/*==============================================================*/
create table TY_COUNTER_FAIL_LOG  (
   FAIL_ID              NUMBER(15),
   IMEI                 VARCHAR2(50),
   UA                   VARCHAR2(50),
   MODEL_NAME           VARCHAR2(50),
   CHANNEL_ID           NUMBER(15),
   DEVICE_CODE          VARCHAR2(100),
   GROUP_ID             NUMBER(15),
   BATCH_CODE           VARCHAR2(50),
   PROCESS_TIME         VARCHAR2(50),
   CREATE_TIME          DATE                           default SYSDATE,
   ACTIVE               VARCHAR2(10)                   default 'Y'
);

comment on table TY_COUNTER_FAIL_LOG is
'计数器上传数据缓冲表';

comment on column TY_COUNTER_FAIL_LOG.FAIL_ID is
'主键ID';

comment on column TY_COUNTER_FAIL_LOG.IMEI is
'IMEI码';

comment on column TY_COUNTER_FAIL_LOG.UA is
'手机UA';

comment on column TY_COUNTER_FAIL_LOG.MODEL_NAME is
'机型名称';

comment on column TY_COUNTER_FAIL_LOG.CHANNEL_ID is
'渠道ID';

comment on column TY_COUNTER_FAIL_LOG.DEVICE_CODE is
'设备编码';

comment on column TY_COUNTER_FAIL_LOG.GROUP_ID is
'渠道组ID';

comment on column TY_COUNTER_FAIL_LOG.BATCH_CODE is
'批次号';

comment on column TY_COUNTER_FAIL_LOG.PROCESS_TIME is
'加工时间';

comment on column TY_COUNTER_FAIL_LOG.CREATE_TIME is
'创建时间';

comment on column TY_COUNTER_FAIL_LOG.ACTIVE is
'到达状态';

/*==============================================================*/
/* Table: TY_COUNTER_UPLOAD_LOG                                 */
/*==============================================================*/
create table TY_COUNTER_UPLOAD_LOG  (
   COUNTER_ID           NUMBER(15)                      not null,
   IMEI                 VARCHAR2(50),
   UA                   VARCHAR2(100),
   MODEL_NAME           VARCHAR2(100),
   CHANNEL_ID           NUMBER(15),
   DEVICE_CODE          VARCHAR2(100),
   GROUP_ID             NUMBER(15),
   BATCH_CODE           VARCHAR2(50),
   PROCESS_TIME         VARCHAR2(50),
   CREATE_TIME          DATE                           default SYSDATE,
   ACTIVE               VARCHAR2(10)                   default 'Y',
   constraint PK_TY_COUNTER_UPLOAD_LOG primary key (COUNTER_ID)
);

comment on table TY_COUNTER_UPLOAD_LOG is
'计数器流水表';

comment on column TY_COUNTER_UPLOAD_LOG.COUNTER_ID is
'主键ID';

comment on column TY_COUNTER_UPLOAD_LOG.IMEI is
'IMEI码';

comment on column TY_COUNTER_UPLOAD_LOG.UA is
'手机UA';

comment on column TY_COUNTER_UPLOAD_LOG.MODEL_NAME is
'机型名称';

comment on column TY_COUNTER_UPLOAD_LOG.CHANNEL_ID is
'渠道ID';

comment on column TY_COUNTER_UPLOAD_LOG.DEVICE_CODE is
'设备编码';

comment on column TY_COUNTER_UPLOAD_LOG.GROUP_ID is
'渠道组ID';

comment on column TY_COUNTER_UPLOAD_LOG.BATCH_CODE is
'批次号';

comment on column TY_COUNTER_UPLOAD_LOG.PROCESS_TIME is
'加工时间';

comment on column TY_COUNTER_UPLOAD_LOG.CREATE_TIME is
'创建时间';

comment on column TY_COUNTER_UPLOAD_LOG.ACTIVE is
'到达状态
1：有效到达，
2：无效-替换，
3：无效-卸载';

/*==============================================================*/
/* Index: "Index_5"                                             */
/*==============================================================*/
create index "Index_5" on TY_COUNTER_UPLOAD_LOG (
   IMEI ASC
);

/*==============================================================*/
/* Index: "Index_6"                                             */
/*==============================================================*/
create index "Index_6" on TY_COUNTER_UPLOAD_LOG (
   CREATE_TIME ASC
);

/*==============================================================*/
/* Table: TY_DEVICE_INFO                                        */
/*==============================================================*/
create table TY_DEVICE_INFO  (
   DEVICE_ID            NUMBER(15)                      not null,
   DEVICE_CODE          VARCHAR2(50 CHAR),
   GROUP_ID             NUMBER(15),
   CHANNEL_ID           NUMBER(15),
   CCREATE_TIME         DATE,
   UPDATE_TIME          DATE,
   ACTIVE               VARCHAR2(2)                    default 'Y',
   constraint PK_TY_DEVICE_INFO primary key (DEVICE_ID)
);

comment on table TY_DEVICE_INFO is
'加工设备信息表';

comment on column TY_DEVICE_INFO.DEVICE_ID is
'主键ID';

comment on column TY_DEVICE_INFO.DEVICE_CODE is
'设备编码';

comment on column TY_DEVICE_INFO.GROUP_ID is
'渠道组ID';

comment on column TY_DEVICE_INFO.CHANNEL_ID is
'渠道ID';

comment on column TY_DEVICE_INFO.CCREATE_TIME is
'创建时间';

comment on column TY_DEVICE_INFO.UPDATE_TIME is
'修改时间';

comment on column TY_DEVICE_INFO.ACTIVE is
'活动状态（Y:有效，D:删除）';

/*==============================================================*/
/* Table: TY_DEVICE_PROCESS_LOG                                 */
/*==============================================================*/
create table TY_DEVICE_PROCESS_LOG  (
   PROCESS_ID           NUMBER(15)                      not null,
   IMEI                 VARCHAR2(50),
   UA                   VARCHAR2(100),
   MODEL_NAME           VARCHAR2(100),
   CHANNEL_ID           NUMBER(15),
   DEVICE_CODE          VARCHAR2(100),
   GROUP_ID             NUMBER(15),
   BATCH_CODE           VARCHAR2(100),
   PROCESS_TIME         VARCHAR2(100),
   CREATE_TIME          DATE                           default SYSDATE,
   constraint PK_TY_DEVICE_PROCESS_LOG primary key (PROCESS_ID)
);

comment on table TY_DEVICE_PROCESS_LOG is
'加工设备流水??';

comment on column TY_DEVICE_PROCESS_LOG.PROCESS_ID is
'主键ID';

comment on column TY_DEVICE_PROCESS_LOG.IMEI is
'IMEI码';

comment on column TY_DEVICE_PROCESS_LOG.UA is
'手机UA';

comment on column TY_DEVICE_PROCESS_LOG.MODEL_NAME is
'机型名称';

comment on column TY_DEVICE_PROCESS_LOG.CHANNEL_ID is
'渠道ID';

comment on column TY_DEVICE_PROCESS_LOG.DEVICE_CODE is
'设备编码';

comment on column TY_DEVICE_PROCESS_LOG.GROUP_ID is
'渠道组ID';

comment on column TY_DEVICE_PROCESS_LOG.BATCH_CODE is
'批次号';

comment on column TY_DEVICE_PROCESS_LOG.PROCESS_TIME is
'加工时间';

comment on column TY_DEVICE_PROCESS_LOG.CREATE_TIME is
'创建时间';

/*==============================================================*/
/* Index: "Index_3"                                             */
/*==============================================================*/
create index "Index_3" on TY_DEVICE_PROCESS_LOG (
   IMEI ASC
);

/*==============================================================*/
/* Index: "Index_4"                                             */
/*==============================================================*/
create index "Index_4" on TY_DEVICE_PROCESS_LOG (
   CREATE_TIME ASC
);

/*==============================================================*/
/* Table: TY_DEVICE_SYSTEM                                      */
/*==============================================================*/
create table TY_DEVICE_SYSTEM  (
   SYSTEM_ID            CHAR(10)                        not null,
   VERSION              CHAR(10),
   FTP_PATH             CHAR(10),
   DOWNLOAD_URL         CHAR(10),
   MD5VALUE             CHAR(10),
   EFFECTIVE_TIME       CHAR(10),
   CREATE_TIME          CHAR(10),
   constraint PK_TY_DEVICE_SYSTEM primary key (SYSTEM_ID)
);

comment on table TY_DEVICE_SYSTEM is
'设备系统升级表';

comment on column TY_DEVICE_SYSTEM.SYSTEM_ID is
'主键ID';

comment on column TY_DEVICE_SYSTEM.VERSION is
'版本号';

comment on column TY_DEVICE_SYSTEM.FTP_PATH is
'FTP路径';

comment on column TY_DEVICE_SYSTEM.DOWNLOAD_URL is
'下载链接';

comment on column TY_DEVICE_SYSTEM.MD5VALUE is
'文件md5sum值';

comment on column TY_DEVICE_SYSTEM.EFFECTIVE_TIME is
'生效时间';

comment on column TY_DEVICE_SYSTEM.CREATE_TIME is
'创建时间';

/*==============================================================*/
/* Table: TY_DICT_INFO                                          */
/*==============================================================*/
create table TY_DICT_INFO  (
   DICT_ID              NUMBER(15)                     default NUMBER(15) not null,
   KEY_CODE             VARCHAR2(50)                    not null,
   KEY_VALUE            VARCHAR2(50),
   REMARK               VARCHAR2(500),
   CREATE_TIME          DATE,
   UPDATE_TIME          DATE,
   constraint PK_TY_DICT_INFO primary key (DICT_ID)
);

comment on table TY_DICT_INFO is
'基础数据配置表';

comment on column TY_DICT_INFO.DICT_ID is
'主键ID';

comment on column TY_DICT_INFO.KEY_CODE is
'KEY';

comment on column TY_DICT_INFO.KEY_VALUE is
'VALUE';

comment on column TY_DICT_INFO.REMARK is
'备注';

comment on column TY_DICT_INFO.CREATE_TIME is
'创建时间';

comment on column TY_DICT_INFO.UPDATE_TIME is
'修改时间';

/*==============================================================*/
/* Index: "Index_7"                                             */
/*==============================================================*/
create unique index "Index_7" on TY_DICT_INFO (
   KEY_CODE ASC
);

/*==============================================================*/
/* Table: TY_MODEL_CHANNEL_REF                                  */
/*==============================================================*/
create table TY_MODEL_CHANNEL_REF  (
   MODEL_ID             NUMBER(15)                      not null,
   CHANNEL_ID           NUMBER(15)                      not null,
   GROUP_ID             NUMBER(15),
   CREATE_TIME          DATE                           default SYSDATE
);

comment on table TY_MODEL_CHANNEL_REF is
'渠道机型映射表';

comment on column TY_MODEL_CHANNEL_REF.MODEL_ID is
'机型ID';

comment on column TY_MODEL_CHANNEL_REF.CHANNEL_ID is
'渠道ID';

comment on column TY_MODEL_CHANNEL_REF.GROUP_ID is
'渠道组ID';

comment on column TY_MODEL_CHANNEL_REF.CREATE_TIME is
'创建时间';

/*==============================================================*/
/* Table: TY_MODEL_INFO                                         */
/*==============================================================*/
create table TY_MODEL_INFO  (
   MODEL_ID             NUMBER(15)                      not null,
   UA                   VARCHAR2(100),
   MODEL_NAME           VARCHAR2(200),
   GROUP_ID             NUMBER(15),
   TAGNUM               NUMBER(15),
   TAGPRICE             NUMBER(15,3),
   CREATE_TIME          DATE,
   UPDATE_TIME          DATE,
   ACTIVE               VARCHAR2(1 CHAR)               default 'Y',
   constraint PK_TY_MODEL_INFO primary key (MODEL_ID)
);

comment on table TY_MODEL_INFO is
'机型信息表';

comment on column TY_MODEL_INFO.MODEL_ID is
'主键ID';

comment on column TY_MODEL_INFO.UA is
'手机UA';

comment on column TY_MODEL_INFO.MODEL_NAME is
'机型名称';

comment on column TY_MODEL_INFO.GROUP_ID is
'渠道组ID';

comment on column TY_MODEL_INFO.TAGNUM is
'标签数量';

comment on column TY_MODEL_INFO.TAGPRICE is
'标签单价';

comment on column TY_MODEL_INFO.CREATE_TIME is
'创建时间';

comment on column TY_MODEL_INFO.UPDATE_TIME is
'修改时间';

comment on column TY_MODEL_INFO.ACTIVE is
'活动状态（Y:有效，D:删除）';

/*==============================================================*/
/* Table: TY_PACKAGE_APK_REF                                    */
/*==============================================================*/
create table TY_PACKAGE_APK_REF  (
   ID                   NUMBER(15)                      not null,
   PACKAGE_ID           NUMBER(15),
   APK_ID               NUMBER(15),
   APK_NAME             VARCHAR2(100),
   AUTO_RUN             VARCHAR2(2),
   DESKTOP_ICON         VARCHAR2(2),
   SORT                 NUMBER(10),
   CREATE_TIME          DATE,
   ACTIVE               VARCHAR2(2)                    default 'Y',
   constraint PK_TY_PACKAGE_APK_REF primary key (ID)
);

comment on column TY_PACKAGE_APK_REF.ID is
'主键ID';

comment on column TY_PACKAGE_APK_REF.PACKAGE_ID is
'安装包ID';

comment on column TY_PACKAGE_APK_REF.APK_ID is
'APKID';

comment on column TY_PACKAGE_APK_REF.APK_NAME is
'APK名称';

comment on column TY_PACKAGE_APK_REF.AUTO_RUN is
'自动运行';

comment on column TY_PACKAGE_APK_REF.DESKTOP_ICON is
'创建图标';

comment on column TY_PACKAGE_APK_REF.SORT is
'排序';

comment on column TY_PACKAGE_APK_REF.CREATE_TIME is
'创建时间';

comment on column TY_PACKAGE_APK_REF.ACTIVE is
'数据状态 Y:有效，N:无效';

/*==============================================================*/
/* Index: "Index_8"                                             */
/*==============================================================*/
create index "Index_8" on TY_PACKAGE_APK_REF (
   PACKAGE_ID ASC
);

/*==============================================================*/
/* Table: TY_PACKAGE_INFO                                       */
/*==============================================================*/
create table TY_PACKAGE_INFO  (
   PACKAGE_ID           NUMBER(15)                      not null,
   PACKAGE_NAME         VARCHAR2(100),
   GROUP_ID             NUMBER(15),
   BATCH_ID             NUMBER(15),
   BATCH_CODE           VARCHAR2(100),
   TYPE                 VARCHAR2(2),
   REMARK               VARCHAR2(500 CHAR),
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE                           default SYSDATE,
   ACTIVE               VARCHAR2(2)                    default 'Y',
   constraint PK_TY_PACKAGE_INFO primary key (PACKAGE_ID)
);

comment on table TY_PACKAGE_INFO is
'安装包信息表';

comment on column TY_PACKAGE_INFO.PACKAGE_ID is
'主键ID';

comment on column TY_PACKAGE_INFO.PACKAGE_NAME is
'安装包名称';

comment on column TY_PACKAGE_INFO.GROUP_ID is
'渠道组ID';

comment on column TY_PACKAGE_INFO.BATCH_ID is
'批次ID';

comment on column TY_PACKAGE_INFO.BATCH_CODE is
'批次号';

comment on column TY_PACKAGE_INFO.TYPE is
'安装包类型  Y:通用包，N:普通包';

comment on column TY_PACKAGE_INFO.REMARK is
'备注';

comment on column TY_PACKAGE_INFO.CREATE_TIME is
'创建时间';

comment on column TY_PACKAGE_INFO.UPDATE_TIME is
'修改时间';

comment on column TY_PACKAGE_INFO.ACTIVE is
'数据状态 Y:有效，N:无效';

/*==============================================================*/
/* Table: TY_PARTNER_INFO                                       */
/*==============================================================*/
create table TY_PARTNER_INFO  (
   PARTNER_ID           NUMBER(15)                      not null,
   USER_ID              NUMBER(15),
   PARTNER_NAME         VARCHAR2(100 CHAR),
   QUERY_IMEI_SOURCE    VARCHAR2(2),
   EXPORT_IMEI_SOURCE   VARCHAR2(2),
   CREATE_TIME          DATE,
   UPDATE_TIME          DATE,
   ACTIVE               VARCHAR2(1)                    default 'Y',
   constraint PK_TY_PARTNER_INFO primary key (PARTNER_ID)
);

comment on table TY_PARTNER_INFO is
'合作方信息表';

comment on column TY_PARTNER_INFO.PARTNER_ID is
'主键ID';

comment on column TY_PARTNER_INFO.USER_ID is
'用户ID';

comment on column TY_PARTNER_INFO.PARTNER_NAME is
'合作方名称';

comment on column TY_PARTNER_INFO.QUERY_IMEI_SOURCE is
'是否可以查询IMEI（Y:可以，N:不可以）';

comment on column TY_PARTNER_INFO.EXPORT_IMEI_SOURCE is
'是否可以导出IMEI（Y:可以，N:不可以）';

comment on column TY_PARTNER_INFO.CREATE_TIME is
'创建时间';

comment on column TY_PARTNER_INFO.UPDATE_TIME is
'修改时间';

comment on column TY_PARTNER_INFO.ACTIVE is
'活动状态（Y:有效，D:删除）';

/*==============================================================*/
/* Table: TY_PRODUCT_INFO                                       */
/*==============================================================*/
create table TY_PRODUCT_INFO  (
   PRODUCT_ID           NUMBER(15)                      not null,
   PRODUCT_NAME         VARCHAR2(50 CHAR),
   PARTNER_ID           NUMBER(15),
   QUERY_DATA_SOURCE    VARCHAR2(2),
   QUERY_START_TIME     DATE,
   CREATE_TIME          DATE,
   UPDATE_TIME          DATE,
   ACTIVE               VARCHAR2(2)                    default 'Y',
   constraint PK_TY_PRODUCT_INFO primary key (PRODUCT_ID)
);

comment on table TY_PRODUCT_INFO is
'产品信息表';

comment on column TY_PRODUCT_INFO.PRODUCT_ID is
'主键ID';

comment on column TY_PRODUCT_INFO.PRODUCT_NAME is
'产品名称';

comment on column TY_PRODUCT_INFO.PARTNER_ID is
'合作方ID';

comment on column TY_PRODUCT_INFO.QUERY_DATA_SOURCE is
'是否可以查询（Y:可以，N:不可以）';

comment on column TY_PRODUCT_INFO.QUERY_START_TIME is
'查询开始时间';

comment on column TY_PRODUCT_INFO.CREATE_TIME is
'创建时间';

comment on column TY_PRODUCT_INFO.UPDATE_TIME is
'修改时间';

comment on column TY_PRODUCT_INFO.ACTIVE is
'活动状态（Y:有效，D:删除）';

/*==============================================================*/
/* Table: TY_PUBLISH_TASK                                       */
/*==============================================================*/
create table TY_PUBLISH_TASK  (
   PUBLISH_ID           NUMBER(15)                      not null,
   安装包ID                NUMBER(15),
   PACKAGE_NAME         VARCHAR2(100),
   EFFECT_TIME          DATE,
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE                           default SYSDATE,
   ACTIVE               VARCHAR2(2)                    default 'Y',
   constraint PK_TY_PUBLISH_TASK primary key (PUBLISH_ID)
);

comment on table TY_PUBLISH_TASK is
'发布任务表';

comment on column TY_PUBLISH_TASK.PUBLISH_ID is
'主键ID';

comment on column TY_PUBLISH_TASK.安装包ID is
'安装包ID';

comment on column TY_PUBLISH_TASK.PACKAGE_NAME is
'安装包名称';

comment on column TY_PUBLISH_TASK.EFFECT_TIME is
'生效时间';

comment on column TY_PUBLISH_TASK.CREATE_TIME is
'创建时间';

comment on column TY_PUBLISH_TASK.UPDATE_TIME is
'修改时间';

comment on column TY_PUBLISH_TASK.ACTIVE is
'数据状态';

/*==============================================================*/
/* Index: "Index_9"                                             */
/*==============================================================*/
create index "Index_9" on TY_PUBLISH_TASK (
   UPDATE_TIME ASC
);

/*==============================================================*/
/* Table: TY_PUB_CHL_MOD_REF                                    */
/*==============================================================*/
create table TY_PUB_CHL_MOD_REF  (
   ID                   NUMBER(15)                      not null,
   PUBLISH_ID           NUMBER(15),
   GROUP_ID             NUMBER(15),
   CHANNEL_ID           NUMBER(15),
   PACKAGE_ID           NUMBER(15),
   MODEL_ID             NUMBER(15),
   PKG_TYPE             VARCHAR(2),
   CREATE_TIME          DATE,
   UPDATE_TIME          DATE,
   ACTIVE               VARCHAR(2),
   constraint PK_TY_PUB_CHL_MOD_REF primary key (ID)
);

comment on table TY_PUB_CHL_MOD_REF is
'发布任务与渠道-机型映射表';

comment on column TY_PUB_CHL_MOD_REF.ID is
'主键ID';

comment on column TY_PUB_CHL_MOD_REF.PUBLISH_ID is
'发布任务ID';

comment on column TY_PUB_CHL_MOD_REF.GROUP_ID is
'渠道组ID';

comment on column TY_PUB_CHL_MOD_REF.CHANNEL_ID is
'渠道ID';

comment on column TY_PUB_CHL_MOD_REF.PACKAGE_ID is
'安装包ID';

comment on column TY_PUB_CHL_MOD_REF.MODEL_ID is
'机型ID';

comment on column TY_PUB_CHL_MOD_REF.PKG_TYPE is
'通用包标识';

comment on column TY_PUB_CHL_MOD_REF.CREATE_TIME is
'创建时间';

comment on column TY_PUB_CHL_MOD_REF.UPDATE_TIME is
'修改时间';

comment on column TY_PUB_CHL_MOD_REF.ACTIVE is
' 数据状态 Y:有效，N:无效';

/*==============================================================*/
/* Index: "Index_10"                                            */
/*==============================================================*/
create index "Index_10" on TY_PUB_CHL_MOD_REF (
   UPDATE_TIME ASC
);

/*==============================================================*/
/* Index: "Index_11"                                            */
/*==============================================================*/
create index "Index_11" on TY_PUB_CHL_MOD_REF (
   PUBLISH_ID ASC
);

/*==============================================================*/
/* Table: TY_RESOURCE                                           */
/*==============================================================*/
create table TY_RESOURCE  (
   RESOURCE_ID          NUMBER(15)                      not null,
   PARENT_ID            NUMBER(15),
   RES_NAME             VARCHAR2(50 ),
   RES_URL              VARCHAR2(0 BYTE),
   FULL_PATH            VARCHAR2(500),
   LEVELS               NUMBER(15),
   CREATE_TIME          DATE,
   constraint PK_TY_RESOURCE primary key (RESOURCE_ID)
);

comment on table TY_RESOURCE is
'权限资源表';

/*==============================================================*/
/* Table: TY_ROLE                                               */
/*==============================================================*/
create table TY_ROLE  (
   ROLE_ID              NUMBER(15)                      not null,
   ROLE_NAME            VARCHAR2(50)                    not null,
   PARENT_ID            NUMBER(15),
   FULL_PATH            VARCHAR2(500),
   LEVELS               NUMBER(15),
   CREATE_TIME          DATE,
   constraint PK_TY_ROLE primary key (ROLE_ID)
);

comment on table TY_ROLE is
'角色表';

/*==============================================================*/
/* Table: TY_ROLE_RESOURCE_REF                                  */
/*==============================================================*/
create table TY_ROLE_RESOURCE_REF  (
   ID                   CHAR(10)                        not null,
   ROLE_ID              CHAR(10),
   RESOURCE_ID          CHAR(10),
   ACCES                CHAR(10),
   CREATE_TIME          CHAR(10),
   constraint PK_TY_ROLE_RESOURCE_REF primary key (ID)
);

comment on table TY_ROLE_RESOURCE_REF is
'角色与资源映射表';

comment on column TY_ROLE_RESOURCE_REF.ID is
'NUMBER(15)';

comment on column TY_ROLE_RESOURCE_REF.ROLE_ID is
'NUMBER(15)';

comment on column TY_ROLE_RESOURCE_REF.RESOURCE_ID is
'NUMBER(15)';

comment on column TY_ROLE_RESOURCE_REF.ACCES is
' NUMBER(2)';

comment on column TY_ROLE_RESOURCE_REF.CREATE_TIME is
'DATE';

/*==============================================================*/
/* Table: TY_SETTLE_INFO                                        */
/*==============================================================*/
create table TY_SETTLE_INFO  (
   SETTLE_ID            NUMBER(15)                      not null,
   MODEL_NAME           VARCHAR2(100 CHAR),
   START_TIME           DATE,
   END_TIME             DATE,
   PRICE                NUMBER(12,3),
   REMARK               VARCHAR2(500 CHAR),
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE                           default SYSDATE,
   constraint PK_TY_SETTLE_INFO primary key (SETTLE_ID)
);

comment on table TY_SETTLE_INFO is
'结算信息表';

comment on column TY_SETTLE_INFO.SETTLE_ID is
'主键ID';

comment on column TY_SETTLE_INFO.MODEL_NAME is
'机型名称';

comment on column TY_SETTLE_INFO.START_TIME is
'开始时间';

comment on column TY_SETTLE_INFO.END_TIME is
'结束时间';

comment on column TY_SETTLE_INFO.PRICE is
'价格';

comment on column TY_SETTLE_INFO.REMARK is
'备注';

comment on column TY_SETTLE_INFO.CREATE_TIME is
'创建时间';

comment on column TY_SETTLE_INFO.UPDATE_TIME is
'修改时间';

/*==============================================================*/
/* Table: TY_USER                                               */
/*==============================================================*/
create table TY_USER  (
   USER_ID              NUMBER(15)                      not null,
   LOGIN_NAME           VARCHAR2(50),
   REAL_NAME            VARCHAR2(50),
   PASSWORD             VARCHAR2(50),
   CELLPHONE            VARCHAR2(50),
   ADDRESS              VARCHAR2(500),
   STATUS               NUMBER(2),
   TYPE                 NUMBER(2),
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE                           default SYSDATE,
   constraint PK_TY_USER primary key (USER_ID)
);

comment on table TY_USER is
'用户表';

comment on column TY_USER.USER_ID is
'主键ID';

comment on column TY_USER.LOGIN_NAME is
'登录名称';

comment on column TY_USER.REAL_NAME is
'真实名称';

comment on column TY_USER.PASSWORD is
'密码';

comment on column TY_USER.CELLPHONE is
'电话';

comment on column TY_USER.ADDRESS is
'地址';

comment on column TY_USER.STATUS is
'状态';

comment on column TY_USER.TYPE is
'类型';

comment on column TY_USER.CREATE_TIME is
'创建时间';

comment on column TY_USER.UPDATE_TIME is
'修改时间';

/*==============================================================*/
/* Index: "Index_2"                                             */
/*==============================================================*/
create unique index "Index_2" on TY_USER (
   LOGIN_NAME ASC
);

/*==============================================================*/
/* Table: TY_USER_ROLE_REF                                      */
/*==============================================================*/
create table TY_USER_ROLE_REF  (
   ID                   NUMBER(15)                      not null,
   USER_ID              NUMBER(15),
   ROLE_ID              NUMBER(15),
   CREATE_TIME          DATE                           default SYSDATE,
   constraint PK_TY_USER_ROLE_REF primary key (ID)
);

comment on table TY_USER_ROLE_REF is
'用户与角色关系???';

/*==============================================================*/
/* Index: "Index_1"                                             */
/*==============================================================*/
create index "Index_1" on TY_USER_ROLE_REF (
   USER_ID ASC,
   ROLE_ID ASC
);

