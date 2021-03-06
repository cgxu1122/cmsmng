/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2014/7/12 18:00:32                           */
/*==============================================================*/

drop table TY_APK_INFO cascade constraints;

drop table TY_BATCH_INFO cascade constraints;

drop table TY_BATCH_PRODUCT_REF cascade constraints;

drop table TY_CHANNEL_GROUP cascade constraints;

drop table TY_CHANNEL_INFO cascade constraints;

drop table TY_COUNTER_TEMP_LOG cascade constraints;

drop table TY_DATA_LOG cascade constraints;

drop table TY_DEVICE_INFO cascade constraints;

drop table TY_DEVICE_SYSTEM cascade constraints;

drop table TY_DICT_INFO cascade constraints;

drop table TY_LOG_STAT cascade constraints;

drop table TY_MODEL_CHANNEL_REF cascade constraints;

drop table TY_MODEL_INFO cascade constraints;

drop table TY_PACKAGE_APK_REF cascade constraints;

drop table TY_PACKAGE_INFO cascade constraints;

drop table TY_PARTNER_INFO cascade constraints;

drop table TY_PRODUCT_INFO cascade constraints;

drop table TY_PRODUCT_STAT cascade constraints;

drop table TY_PUBLISH_TASK cascade constraints;

drop table TY_PUB_CHL_MOD_REF cascade constraints;

drop table TY_RESOURCE cascade constraints;

drop table TY_ROLE cascade constraints;

drop table TY_ROLE_RESOURCE_REF cascade constraints;

drop table TY_SETTLE_INFO cascade constraints;

drop table TY_USER cascade constraints;

drop table TY_USER_PRODUCT_REF cascade constraints;


/*==============================================================*/
/* Table: TY_APK_INFO                                           */
/*==============================================================*/
create table TY_APK_INFO  (
   APK_ID               NUMBER(15)                      not null,
   APK_NAME             VARCHAR2(100 CHAR),
   SOFT_NAME            VARCHAR2(100 CHAR),
   FTP_PATH             VARCHAR2(200 CHAR),
   TYPE                 VARCHAR2(2),
   MD5VALUE             VARCHAR2(100 CHAR),
   DOWNLOAD_URL         VARCHAR2(500 CHAR),
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE                           default SYSDATE,
   FILE_UPDATE_TIME    DATE,
   ACTIVE               VARCHAR2(2 CHAR)               default 'Y',
   PACKAGE_PATH         VARCHAR2(500 CHAR),
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

comment on column TY_APK_INFO.TYPE is
'apk类型 1:非计数器 2:计数器';

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

comment on column TY_APK_INFO.PACKAGE_PATH is
'Apk文件的包路径';

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
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE                           default SYSDATE,
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
   BATCH_CODE           VARCHAR2(50 CHAR),
   PRODUCT_ID           NUMBER(15)                      not null,
   CREATE_TIME          DATE                           default SYSDATE,
   constraint PK_TY_BATCH_PRODUCT_REF primary key (BATCH_ID, PRODUCT_ID)
);

comment on table TY_BATCH_PRODUCT_REF is
'批次产品映射表';

comment on column TY_BATCH_PRODUCT_REF.BATCH_ID is
'批次ID';

comment on column TY_BATCH_PRODUCT_REF.BATCH_CODE is
'批次号';

comment on column TY_BATCH_PRODUCT_REF.PRODUCT_ID is
'产品ID';

comment on column TY_BATCH_PRODUCT_REF.CREATE_TIME is
'创建时间';


/*==============================================================*/
/* Table: TY_CHANNEL_GROUP                                      */
/*==============================================================*/
create table TY_CHANNEL_GROUP  (
   GROUP_ID             NUMBER(15)                      not null,
   GROUP_NAME           VARCHAR2(200 CHAR),
   CREATE_TIME          DATE                           default SYSDATE,
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
   CHANNEL_NAME         VARCHAR(200 CHAR),
   "DESC"               VARCHAR(500 CHAR),
   LEAF                 VARCHAR2(2 CHAR)               default 'Y',
   TYPE                 VARCHAR2(2 CHAR),
   LAOWU_ID             NUMBER(15),
   QUERY_IMEI_SOURCE    VARCHAR2(2 CHAR),
   ACTIVE               VARCHAR2(2 CHAR)               default 'Y',
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE                           default SYSDATE,
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
/* Table: TY_COUNTER_TEMP_LOG                                   */
/*==============================================================*/
create table TY_COUNTER_TEMP_LOG  (
   ID                   NUMBER(15)                      not null,
   IMEI                 VARCHAR2(50)                    not null,
   UA                   VARCHAR2(100),
   CREATE_TIME          DATE                           default SYSDATE not null,
   ACTIVE               NUMBER(2),
   TYPE                 NUMBER(2)                      default 0,
   constraint PK_TY_COUNTER_TEMP_LOG primary key (ID)
);

comment on table TY_COUNTER_TEMP_LOG is
'计数器数据缓冲表';

comment on column TY_COUNTER_TEMP_LOG.ID is
'主键ID';

comment on column TY_COUNTER_TEMP_LOG.IMEI is
'IMEI码';

comment on column TY_COUNTER_TEMP_LOG.UA is
'手机UA';

comment on column TY_COUNTER_TEMP_LOG.CREATE_TIME is
'创建时间';

comment on column TY_COUNTER_TEMP_LOG.ACTIVE is
'到达状态';

comment on column TY_COUNTER_TEMP_LOG.TYPE is
'类型 0：未整合 1：未统计 2：已统计';

/*==============================================================*/
/* Table: TY_DATA_LOG                                           */
/*==============================================================*/
create table TY_DATA_LOG  (
   ID                   NUMBER(15)                      not null,
   IMEI                 VARCHAR2(50)                    not null,
   UA                   VARCHAR2(100)                   not null,
   CHANNEL_ID           NUMBER(15)                      not null,
   DEVICE_CODE          VARCHAR2(100)                   not null,
   GROUP_ID             NUMBER(15)                      not null,
   BATCH_CODE           VARCHAR2(100)                   not null,
   PROCESS_TIME         DATE                            not null,
   DEVICE_UPLOAD_TIME   DATE                           default SYSDATE not null,
   ACTIVE               NUMBER(2)                      default 0 not null,
   COUNTER_UPLOAD_TIME  DATE,
   constraint PK_TY_DATA_LOG primary key (ID)
);

comment on table TY_DATA_LOG is
'数据流水表-模板';

comment on column TY_DATA_LOG.ID is
'主键ID';

comment on column TY_DATA_LOG.IMEI is
'IMEI码';

comment on column TY_DATA_LOG.UA is
'手机UA';

comment on column TY_DATA_LOG.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG.COUNTER_UPLOAD_TIME is
'计数器上传时间';


/*==============================================================*/
/* Table: TY_DEVICE_INFO                                        */
/*==============================================================*/
create table TY_DEVICE_INFO  (
   DEVICE_ID            NUMBER(15)                      not null,
   DEVICE_CODE          VARCHAR2(50 CHAR),
   GROUP_ID             NUMBER(15),
   CHANNEL_ID           NUMBER(15),
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE                           default SYSDATE,
   ACTIVE               VARCHAR2(2)                    default 'Y',
   constraint PK_TY_DEVICE_INFO primary key (DEVICE_ID)
);

comment on table TY_DEVICE_INFO is
'设备信息表';

comment on column TY_DEVICE_INFO.DEVICE_ID is
'主键ID';

comment on column TY_DEVICE_INFO.DEVICE_CODE is
'设备编码';

comment on column TY_DEVICE_INFO.GROUP_ID is
'渠道组ID';

comment on column TY_DEVICE_INFO.CHANNEL_ID is
'渠道ID';

comment on column TY_DEVICE_INFO.CREATE_TIME is
'创建时间';

comment on column TY_DEVICE_INFO.UPDATE_TIME is
'修改时间';

comment on column TY_DEVICE_INFO.ACTIVE is
'活动状态（Y:有效，D:删除）';

/*==============================================================*/
/* Table: TY_DEVICE_SYSTEM                                      */
/*==============================================================*/
create table TY_DEVICE_SYSTEM  (
   SYSTEM_ID            NUMBER(15)                      not null,
   VERSION              VARCHAR2(50 CHAR),
   FTP_PATH             VARCHAR2(500 CHAR),
   DOWNLOAD_URL         VARCHAR2(500 CHAR),
   MD5VALUE             VARCHAR2(50 CHAR),
   EFFECTIVE_TIME       DATE,
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE                           default SYSDATE,
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

comment on column TY_DEVICE_SYSTEM.UPDATE_TIME is
'修改时间';

/*==============================================================*/
/* Table: TY_DICT_INFO                                          */
/*==============================================================*/
create table TY_DICT_INFO  (
   DICT_ID              NUMBER(15)                      not null,
   KEY_CODE             VARCHAR2(50)                    not null,
   KEY_VALUE            VARCHAR2(50),
   REMARK               VARCHAR2(500),
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE                           default SYSDATE,
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
/* Table: TY_LOG_STAT                                           */
/*==============================================================*/
create table TY_LOG_STAT  (
   ID                   NUMBER(15)                      not null,
   UA                   VARCHAR2(100)                   not null,
   GROUP_ID             NUMBER(15)                      not null,
   CHANNEL_ID           NUMBER(15)                      not null,
   DEVICE_CODE          VARCHAR2(100)                   not null,
   PROCESS_DATE         DATE                            not null,
   LAOWU_ID             NUMBER(15),
   DEVICE_PRS_DAY_NUM   NUMBER(15)                     default 0,
   DEVICE_UPD_DAY_NUM   NUMBER(15)                     default 0,
   PRS_ACTIVE_TOTAL_NUM NUMBER(15)                     default 0,
   PRS_ACTIVE_VALID_NUM NUMBER(15)                     default 0,
   PRS_ACTIVE_INVALID_NUM NUMBER(15)                   default 0,
   PRS_INVALID_REPLACE_NUM NUMBER(15)                  default 0,
   PRS_INVALID_UNINSTALL_NUM NUMBER(15)                default 0,
   PRS_INVALID_UN_RE_NUM NUMBER(15)                default 0,
   CREATE_TIME          DATE                           default SYSDATE,
   MD5_KEY              VARCHAR2(50)                    not null,
   VERSION              NUMBER(15)                     default 0,
   constraint PK_TY_LOG_STAT primary key (ID)
);

comment on table TY_LOG_STAT is
'数据统计表';

comment on column TY_LOG_STAT.ID is
'主键ID';

comment on column TY_LOG_STAT.UA is
'手机UA';

comment on column TY_LOG_STAT.GROUP_ID is
'渠道组ID';

comment on column TY_LOG_STAT.CHANNEL_ID is
'渠道ID';

comment on column TY_LOG_STAT.DEVICE_CODE is
'设备编码';

comment on column TY_LOG_STAT.PROCESS_DATE is
'加工日期';

comment on column TY_LOG_STAT.LAOWU_ID is
'劳务ID';

comment on column TY_LOG_STAT.DEVICE_PRS_DAY_NUM is
'设备当天装机数量';

comment on column TY_LOG_STAT.DEVICE_UPD_DAY_NUM is
'设备当天上传总数';

comment on column TY_LOG_STAT.PRS_ACTIVE_TOTAL_NUM is
'装机数量中到达总数';

comment on column TY_LOG_STAT.PRS_ACTIVE_VALID_NUM is
'装机数量中有效到达总数';

comment on column TY_LOG_STAT.PRS_ACTIVE_INVALID_NUM is
'装机数量中无效到达总数';

comment on column TY_LOG_STAT.PRS_INVALID_REPLACE_NUM is
'装机数量中替换总数';

comment on column TY_LOG_STAT.PRS_INVALID_UNINSTALL_NUM is
'装机数量中卸载总数';

comment on column TY_LOG_STAT.MD5_KEY is
'MD5KEY';

comment on column TY_LOG_STAT.VERSION is
'VERSION';


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
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE                           default SYSDATE,
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
   APK_TYPE             NUMBER(2)                      default 1,
   CREATE_TIME          DATE                           default SYSDATE,
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

comment on column TY_PACKAGE_APK_REF.APK_TYPE is
'计数器标识 1:非计数器 2:计数器';

comment on column TY_PACKAGE_APK_REF.CREATE_TIME is
'创建时间';


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
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE                           default SYSDATE,
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
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE                           default SYSDATE,
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
/* Table: TY_PRODUCT_STAT                                       */
/*==============================================================*/
create table TY_PRODUCT_STAT  (
   ID                   NUMBER(15)                      not null,
   PRODUCT_ID           NUMBER(15),
   GROUP_ID             NUMBER(15),
   UA                   VARCHAR2(100),
   PROCESS_DATE         DATE,
   PRODUCT_PRS_DAY_NUM  NUMBER(15)                     default 0,
   PRODUCT_UPD_DAY_NUM  NUMBER(15)                     default 0,
   PRS_ACTIVE_TOTAL_NUM NUMBER(15)                     default 0,
   PRS_ACTIVE_VALID_NUM NUMBER(15)                     default 0,
   PRS_ACTIVE_INVALID_NUM NUMBER(15)                   default 0,
   PRS_INVALID_REPLACE_NUM NUMBER(15)                  default 0,
   PRS_INVALID_UNINSTALL_NUM NUMBER(15)                default 0,
   PRS_INVALID_UN_RE_NUM NUMBER(15)                   default 0,
   CREATE_TIME          DATE                           default SYSDATE,
   MD5_KEY              VARCHAR2(50)                    not null,
   VERSION              NUMBER(15)                     default 0,
   constraint PK_TY_PRODUCT_STAT primary key (ID)
);

comment on table TY_PRODUCT_STAT is
'产品统计表';

comment on column TY_PRODUCT_STAT.ID is
'主键ID';

comment on column TY_PRODUCT_STAT.PRODUCT_ID is
'产品ID';

comment on column TY_PRODUCT_STAT.GROUP_ID is
'渠道组ID';

comment on column TY_PRODUCT_STAT.UA is
'手机UA';

comment on column TY_PRODUCT_STAT.PROCESS_DATE is
'加工日期';

comment on column TY_PRODUCT_STAT.PRODUCT_PRS_DAY_NUM is
'产品当天装机数量';

comment on column TY_PRODUCT_STAT.PRODUCT_UPD_DAY_NUM is
'产品当天上传数量';

comment on column TY_PRODUCT_STAT.PRS_ACTIVE_TOTAL_NUM is
'装机数量中到达总数';

comment on column TY_PRODUCT_STAT.PRS_ACTIVE_VALID_NUM is
'装机数量中有效总数';

comment on column TY_PRODUCT_STAT.PRS_ACTIVE_INVALID_NUM is
'装机数量中无效总数';

comment on column TY_PRODUCT_STAT.PRS_INVALID_REPLACE_NUM is
'装机数量中替换总数';

comment on column TY_PRODUCT_STAT.PRS_INVALID_UNINSTALL_NUM is
'装机数量中卸载总数';

comment on column TY_PRODUCT_STAT.MD5_KEY is
'MD5KEY';

comment on column TY_PRODUCT_STAT.VERSION is
'VERSION';

/*==============================================================*/
/* Table: TY_PUBLISH_TASK                                       */
/*==============================================================*/
create table TY_PUBLISH_TASK  (
   PUBLISH_ID           NUMBER(15)                      not null,
   PACKAGE_ID           NUMBER(15),
   PACKAGE_NAME         VARCHAR2(100),
   EFFECT_TIME          DATE,
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE                           default SYSDATE,
   ACTIVE               VARCHAR2(2)                    default 'Y',
   PKG_TYPE             VARCHAR(2)                     default 'N',
   constraint PK_TY_PUBLISH_TASK primary key (PUBLISH_ID)
);

comment on table TY_PUBLISH_TASK is
'发布任务表';

comment on column TY_PUBLISH_TASK.PUBLISH_ID is
'主键ID';

comment on column TY_PUBLISH_TASK.PACKAGE_ID is
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

comment on column TY_PUBLISH_TASK.PKG_TYPE is
'安装包类型 Y:通用包，N:普通包';


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
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE                           default SYSDATE,
   ACTIVE               VARCHAR(2)                     default 'Y',
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
'安装包类型 Y:通用包，N:普通包';

comment on column TY_PUB_CHL_MOD_REF.CREATE_TIME is
'创建时间';

comment on column TY_PUB_CHL_MOD_REF.UPDATE_TIME is
'修改时间';

comment on column TY_PUB_CHL_MOD_REF.ACTIVE is
' 数据状态 Y:有效，N:无效';

/*==============================================================*/
/* Table: TY_RESOURCE                                           */
/*==============================================================*/
create table TY_RESOURCE  (
   RESOURCE_ID          NUMBER(15)                      not null,
   PARENT_ID            NUMBER(15),
   RES_NAME             VARCHAR2(50 ),
   RES_URL              VARCHAR2(500),
   FULL_PATH            VARCHAR2(500),
   LEVELS               NUMBER(15),
   CREATE_TIME          DATE                           default SYSDATE,
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
   LEVELS               NUMBER(5),
   ROOT_ID              NUMBER(15),
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE,
   CAN_DEL              NUMBER(2),
   constraint PK_TY_ROLE primary key (ROLE_ID)
);

comment on table TY_ROLE is
'角色表';

comment on column TY_ROLE.ROLE_NAME is
'角色名称';

comment on column TY_ROLE.PARENT_ID is
'父ID';

comment on column TY_ROLE.FULL_PATH is
'路径';

comment on column TY_ROLE.LEVELS is
'level';

comment on column TY_ROLE.ROOT_ID is
'ROOT_ID';

comment on column TY_ROLE.CREATE_TIME is
'创建时间';

comment on column TY_ROLE.UPDATE_TIME is
'修改时间';

comment on column TY_ROLE.CAN_DEL is
'能否删除';


/*==============================================================*/
/* Table: TY_ROLE_RESOURCE_REF                                  */
/*==============================================================*/
create table TY_ROLE_RESOURCE_REF  (
   ID                   NUMBER(15)                      not null,
   ROLE_ID              NUMBER(15),
   RESOURCE_ID          NUMBER(15),
   CREATE_TIME          DATE                           default SYSDATE,
   constraint PK_TY_ROLE_RESOURCE_REF primary key (ID)
);

comment on table TY_ROLE_RESOURCE_REF is
'角色与资源映射表';

comment on column TY_ROLE_RESOURCE_REF.ID is
'主键ID';

comment on column TY_ROLE_RESOURCE_REF.ROLE_ID is
'角色ID';

comment on column TY_ROLE_RESOURCE_REF.RESOURCE_ID is
'资源ID';

comment on column TY_ROLE_RESOURCE_REF.CREATE_TIME is
'创建时间';

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
   CREATE_BY            NUMBER(15)                      not null,
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

comment on column TY_SETTLE_INFO.CREATE_BY is
'创建人';

comment on column TY_SETTLE_INFO.CREATE_TIME is
'创建时间';

comment on column TY_SETTLE_INFO.UPDATE_TIME is
'修改时间';


/*==============================================================*/
/* Table: TY_USER                                               */
/*==============================================================*/
create table TY_USER  (
   USER_ID              NUMBER(15)                      not null,
   LOGIN_NAME           VARCHAR2(50)                    not null,
   REAL_NAME            VARCHAR2(50)                    not null,
   PASSWORD             VARCHAR2(50)                    not null,
   CELLPHONE            VARCHAR2(50),
   ADDRESS              VARCHAR2(500),
   ACTIVE               VARCHAR2(5)                    default 'Y',
   ROLE_ID              NUMBER(15)                      not null,
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE                           default SYSDATE,
   LAST_LOGIN_TIME      DATE,
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

comment on column TY_USER.ACTIVE is
'状态';

comment on column TY_USER.ROLE_ID is
'角色ID';

comment on column TY_USER.CREATE_TIME is
'创建时间';

comment on column TY_USER.UPDATE_TIME is
'修改时间';



/*==============================================================*/
/* Table: TY_USER_PRODUCT_REF                                   */
/*==============================================================*/
create table TY_USER_PRODUCT_REF  (
   ID                   NUMBER(15)                      not null,
   USER_ID              NUMBER(15)                      not null,
   PRODUCT_ID           NUMBER(15),
   GROUP_ID             NUMBER(15),
   CREATE_TIME          DATE                           default SYSDATE,
   constraint PK_TY_USER_PRODUCT_REF primary key (ID)
);

comment on table TY_USER_PRODUCT_REF is
'用户与产品权限映射表';

comment on column TY_USER_PRODUCT_REF.ID is
'主键ID';

comment on column TY_USER_PRODUCT_REF.USER_ID is
'用户ID';

comment on column TY_USER_PRODUCT_REF.PRODUCT_ID is
'产品ID';

comment on column TY_USER_PRODUCT_REF.GROUP_ID is
'渠道组ID';

comment on column TY_USER_PRODUCT_REF.CREATE_TIME is
'创建时间';

drop table TY_PRODUCT_INSTALL_STAT cascade constraints;
drop table TY_PRODUCT_ARRIVE_STAT cascade constraints;
drop table TY_LOG_ARRIVE_STAT cascade constraints;
drop table TY_STAT_DEDUCTION cascade constraints;
drop table TY_PRODUCT_ARRIVE_STAT_TEMP cascade constraints;
drop table TY_LOG_ARRIVE_STAT_TEMP cascade constraints;

/*==============================================================*/
/* Table: TY_PRODUCT_INSTALL_STAT                                */
/*==============================================================*/
create table TY_PRODUCT_INSTALL_STAT  (
   ID                   NUMBER(15)                      not null,
   PRODUCT_ID           NUMBER(15)                      not null,
   CHANNEL_ID           NUMBER(15)                      not null,
   GROUP_ID             NUMBER(15)                      not null,
   UA                   VARCHAR2(100)                   not null,
   STAT_DATE            DATE                            not null,
   INSTALL_TOTAL_NUM   NUMBER(15)                     default 0,
   TOTAL_NUM            NUMBER(15)                     default 0,
   VALID_NUM            NUMBER(15)                     default 0,
   INVALID_NUM          NUMBER(15)                     default 0,
   REPLACE_NUM          NUMBER(15)                     default 0,
   UNINSTALL_NUM        NUMBER(15)                     default 0,
   UN_AND_RE_NUM        NUMBER(15)                     default 0,
   CREATE_TIME          DATE                           default SYSDATE,
   VERSION              NUMBER(15)                     default 0,
   MD5_KEY              VARCHAR2(50)                    not null,
   constraint PK_TY_PRODUCT_INSTALL_STAT primary key (ID)
);


/*==============================================================*/
/* Table: TY_PRODUCT_ARRIVE_STAT                                */
/*==============================================================*/
create table TY_PRODUCT_ARRIVE_STAT  (
   ID                   NUMBER(15)                      not null,
   PRODUCT_ID           NUMBER(15)                      not null,
   CHANNEL_ID           NUMBER(15)                      not null,
   GROUP_ID             NUMBER(15)                      not null,
   UA                   VARCHAR2(100)                   not null,
   STAT_DATE           DATE                            not null,
   TOTAL_NUM            NUMBER(15)                     default 0,
   VALID_NUM            NUMBER(15)                     default 0,
   INVALID_NUM          NUMBER(15)                     default 0,
   REPLACE_NUM          NUMBER(15)                     default 0,
   UNINSTALL_NUM       NUMBER(15)                     default 0,
   UN_AND_RE_NUM       NUMBER(15)                     default 0,
   CREATE_TIME          DATE                           default SYSDATE,
   VERSION              NUMBER(15)                     default 0,
   MD5_KEY              VARCHAR2(50)                    not null,
   constraint PK_TY_PRODUCT_ARRIVE_STAT primary key (ID)
);


/*==============================================================*/
/* Table: TY_LOG_ARRIVE_STAT                                    */
/*==============================================================*/
create table TY_LOG_ARRIVE_STAT  (
   ID                   NUMBER(15)                      not null,
   UA                   VARCHAR2(100)                   not null,
   GROUP_ID             NUMBER(15)                      not null,
   CHANNEL_ID           NUMBER(15)                      not null,
   STAT_DATE           DATE                            not null,
   LAOWU_ID             NUMBER(15),
   TOTAL_NUM            NUMBER(15)                     default 0,
   VALID_NUM            NUMBER(15)                     default 0,
   INVALID_NUM          NUMBER(15)                     default 0,
   REPLACE_NUM          NUMBER(15)                     default 0,
   UNINSTALL_NUM        NUMBER(15)                     default 0,
   UN_AND_RE_NUM        NUMBER(15)                     default 0,
   CREATE_TIME          DATE                           default SYSDATE,
   VERSION              NUMBER(15)                     default 0,
   MD5_KEY              VARCHAR2(50)                    not null,
   constraint PK_TY_LOG_ARRIVE_STAT primary key (ID)
);


/*==============================================================*/
/* Table: TY_LOG_ARRIVE_STAT_TEMP                               */
/*==============================================================*/
create table TY_LOG_ARRIVE_STAT_TEMP  (
   ID                   NUMBER(15)                      not null,
   UA                   VARCHAR2(100)                   not null,
   GROUP_ID             NUMBER(15)                      not null,
   CHANNEL_ID           NUMBER(15)                      not null,
   STAT_DATE            DATE                            not null,
   LAOWU_ID             NUMBER(15),
   TOTAL_NUM            NUMBER(15)                     default 0,
   VALID_NUM            NUMBER(15)                     default 0,
   CREATE_TIME          DATE                           default SYSDATE,
   constraint TY_LOG_ARRIVE_STAT_TEMP primary key (ID)
);


/*==============================================================*/
/* Table: TY_PRODUCT_ARRIVE_STAT_TEMP                           */
/*==============================================================*/
create table TY_PRODUCT_ARRIVE_STAT_TEMP  (
   ID                   NUMBER(15)                      not null,
   PRODUCT_ID           NUMBER(15)                      not null,
   CHANNEL_ID           NUMBER(15)                      not null,
   GROUP_ID             NUMBER(15)                      not null,
   UA                   VARCHAR2(100)                   not null,
   STAT_DATE           DATE                            not null,
   TOTAL_NUM            NUMBER(15)                     default 0,
   VALID_NUM            NUMBER(15)                     default 0,
   CREATE_TIME          DATE                           default SYSDATE,
   constraint PK_TY_PRODUCT_ARRIVE_STAT_TEMP primary key (ID)
);


/*==============================================================*/
/* Table: TY_STAT_DEDUCTION                                     */
/*==============================================================*/
create table TY_STAT_DEDUCTION  (
   ID                   NUMBER(15)                      not null,
   CHANNEL_ID          NUMBER(15)                     not null,
   GROUP_ID             NUMBER(15)                     not null,
   TYPE                 NUMBER(5)                      default 1,
   BASIC_NUM            NUMBER(15),
   PERCENTAGE           NUMBER(10),
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE                           default SYSDATE,
   constraint PK_TY_STAT_DEDUCTION primary key (ID)
);

comment on column TY_STAT_DEDUCTION.CHANNEL_ID is
'渠道ID';
comment on column TY_STAT_DEDUCTION.GROUP_ID is
'渠道组ID';
comment on column TY_STAT_DEDUCTION.TYPE is
'类型 默认为1，1：基础扣量，2：阶梯扣量';
comment on column TY_STAT_DEDUCTION.BASIC_NUM is
'扣量基础数值';
comment on column TY_STAT_DEDUCTION.PERCENTAGE is
'扣量百分比';
comment on column TY_STAT_DEDUCTION.CREATE_TIME is
'创建时间';
comment on column TY_STAT_DEDUCTION.UPDATE_TIME is
'修改时间';