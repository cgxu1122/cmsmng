/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2014/7/5 22:11:14                            */
/*==============================================================*/


drop index "Index_17";

drop index "Index_19";

drop index IDX_DL_20141_CTIME;

drop index IDX_DL_20141_DTIME;

drop index IDX_DL_20141_MD5;

drop index IDX_DL_20141_PMD5;

drop index UNIQ_DL_20141_IMEI;

drop index IDX_DL_20142_CTIME;

drop index IDX_DL_20142_DTIME;

drop index IDX_DL_20142_MD5;

drop index IDX_DL_20142_PMD5;

drop index UNIQ_DL_20142_IMEI;

drop index IDX_DL_20143_CTIME;

drop index IDX_DL_20143_DTIME;

drop index IDX_DL_20143_MD5;

drop index IDX_DL_20143_PMD5;

drop index UNIQ_DL_20143_IMEI;

drop index IDX_DL_20144_CTIME;

drop index IDX_DL_20144_DTIME;

drop index IDX_DL_20144_MD5;

drop index IDX_DL_20144_PMD5;

drop index UNIQ_DL_20144_IMEI;

drop index "Index_7";

drop index "Index_3";

drop index "Index_13";

drop index "Index_4";

drop index "Index_9";

drop index "Index_10";

drop index "Index_11";

drop index "Index_12";

drop index "Index_8";

drop index "Index_2";

drop index "Index_1";

drop table TY_APK_INFO cascade constraints;

drop table TY_BATCH_INFO cascade constraints;

drop table TY_BATCH_PRODUCT_REF cascade constraints;

drop table TY_CHANNEL_GROUP cascade constraints;

drop table TY_CHANNEL_INFO cascade constraints;

drop table TY_COUNTER_TEMP_LOG cascade constraints;

drop table TY_DATA_LOG_20141 cascade constraints;

drop table TY_DATA_LOG_20142 cascade constraints;

drop table TY_DATA_LOG_20143 cascade constraints;

drop table TY_DATA_LOG_20144 cascade constraints;

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

drop table TY_USER_ROLE_REF cascade constraints;

drop sequence SEQ_APK_INFO;

drop sequence SEQ_BATCH_INFO;

drop sequence SEQ_BATCH_INFO_DB;

drop sequence SEQ_BATCH_INFO_QT;

drop sequence SEQ_BATCH_INFO_TY;

drop sequence SEQ_BATCH_PRODUCT_REF;

drop sequence SEQ_CHANNEL_GROUP;

drop sequence SEQ_CHANNEL_INFO;

drop sequence SEQ_COUNTER_TEMP_LOG;

drop sequence SEQ_DATA_LOG;

drop sequence SEQ_DEVICE_INFO;

drop sequence SEQ_DEVICE_SYSTEM;

drop sequence SEQ_DICT_INFO;

drop sequence SEQ_LOG_STAT;

drop sequence SEQ_MODEL_CHANNEL_REF;

drop sequence SEQ_MODEL_INFO;

drop sequence SEQ_PACKAGE_APK_REF;

drop sequence SEQ_PACKAGE_INFO;

drop sequence SEQ_PARTNER_INFO;

drop sequence SEQ_PRODUCT_INFO;

drop sequence SEQ_PRODUCT_STAT;

drop sequence SEQ_PUBLISH_TASK;

drop sequence SEQ_PUB_CHL_MOD_REF;

drop sequence SEQ_RESOURCE;

drop sequence SEQ_ROLE;

drop sequence SEQ_ROLE_RESOURCE_REF;

drop sequence SEQ_SETTLE_INFO;

drop sequence SEQ_USER;

drop sequence SEQ_USER_ROLE_REF;

create sequence SEQ_APK_INFO;

create sequence SEQ_BATCH_INFO;

create sequence SEQ_BATCH_INFO_DB;

create sequence SEQ_BATCH_INFO_QT;

create sequence SEQ_BATCH_INFO_TY;

create sequence SEQ_BATCH_PRODUCT_REF;

create sequence SEQ_CHANNEL_GROUP;

create sequence SEQ_CHANNEL_INFO;

create sequence SEQ_COUNTER_TEMP_LOG;

create sequence SEQ_DATA_LOG;

create sequence SEQ_DEVICE_INFO;

create sequence SEQ_DEVICE_SYSTEM;

create sequence SEQ_DICT_INFO;

create sequence SEQ_LOG_STAT;

create sequence SEQ_MODEL_CHANNEL_REF;

create sequence SEQ_MODEL_INFO;

create sequence SEQ_PACKAGE_APK_REF;

create sequence SEQ_PACKAGE_INFO;

create sequence SEQ_PARTNER_INFO;

create sequence SEQ_PRODUCT_INFO;

create sequence SEQ_PRODUCT_STAT;

create sequence SEQ_PUBLISH_TASK;

create sequence SEQ_PUB_CHL_MOD_REF;

create sequence SEQ_RESOURCE;

create sequence SEQ_ROLE;

create sequence SEQ_ROLE_RESOURCE_REF;

create sequence SEQ_SETTLE_INFO;

create sequence SEQ_USER;

create sequence SEQ_USER_ROLE_REF;

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
   ACTIVE               VARCHAR2(2 CHAR)               default 'Y',
   PACKAGE_PATH         VARCHAR2(200 CHAR),
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
   PRODUCT_ID           NUMBER(15)                      not null,
   CREATE_TIME          DATE                           default SYSDATE,
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
   TYPE                 NUMBER(2)                      default 1,
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
'类型 0：未上传 1：未统计 2：已统计';

/*==============================================================*/
/* Index: "Index_17"                                            */
/*==============================================================*/
create unique index "Index_17" on TY_COUNTER_TEMP_LOG (
   IMEI ASC
);

/*==============================================================*/
/* Index: "Index_19"                                            */
/*==============================================================*/
create index "Index_19" on TY_COUNTER_TEMP_LOG (
   CREATE_TIME ASC
);

/*==============================================================*/
/* Table: TY_DATA_LOG_20141                                     */
/*==============================================================*/
create table TY_DATA_LOG_20141  (
   ID                   NUMBER(15)                      not null,
   IMEI                 VARCHAR2(50)                    not null,
   UA                   VARCHAR2(100),
   CHANNEL_ID           NUMBER(15),
   DEVICE_CODE          VARCHAR2(100),
   GROUP_ID             NUMBER(15),
   BATCH_CODE           VARCHAR2(100),
   PROCESS_TIME         DATE,
   DEVICE_UPLOAD_TIME   DATE                           default SYSDATE not null,
   ACTIVE               NUMBER(2),
   COUNTER_UPLOAD_TIME  DATE,
   MD5_KEY              VARCHAR2(50)                    not null,
   P_MD5_KEY            VARCHAR2(50),
   constraint PK_TY_DATA_LOG_20141 primary key (ID)
);

comment on table TY_DATA_LOG_20141 is
'数据流水表201401';

comment on column TY_DATA_LOG_20141.ID is
'主键ID';

comment on column TY_DATA_LOG_20141.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20141.UA is
'手机UA';

comment on column TY_DATA_LOG_20141.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20141.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20141.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20141.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20141.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20141.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20141.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20141.COUNTER_UPLOAD_TIME is
'计数器上传时间';

comment on column TY_DATA_LOG_20141.MD5_KEY is
'MD5_KEY=(UA + ChannelId + DeviceCode)';

comment on column TY_DATA_LOG_20141.P_MD5_KEY is
'P_MD5_KEY=(UA + GroupId + BatchCode)';

/*==============================================================*/
/* Index: UNIQ_DL_20141_IMEI                                    */
/*==============================================================*/
create unique index UNIQ_DL_20141_IMEI on TY_DATA_LOG_20141 (
   IMEI ASC
);

/*==============================================================*/
/* Index: IDX_DL_20141_DTIME                                    */
/*==============================================================*/
create index IDX_DL_20141_DTIME on TY_DATA_LOG_20141 (
   DEVICE_UPLOAD_TIME ASC
);

/*==============================================================*/
/* Index: IDX_DL_20141_CTIME                                    */
/*==============================================================*/
create index IDX_DL_20141_CTIME on TY_DATA_LOG_20141 (
   COUNTER_UPLOAD_TIME ASC
);

/*==============================================================*/
/* Index: IDX_DL_20141_MD5                                      */
/*==============================================================*/
create index IDX_DL_20141_MD5 on TY_DATA_LOG_20141 (
   MD5_KEY ASC
);

/*==============================================================*/
/* Index: IDX_DL_20141_PMD5                                     */
/*==============================================================*/
create index IDX_DL_20141_PMD5 on TY_DATA_LOG_20141 (
   P_MD5_KEY ASC
);

/*==============================================================*/
/* Table: TY_DATA_LOG_20142                                     */
/*==============================================================*/
create table TY_DATA_LOG_20142  (
   ID                   NUMBER(15)                      not null,
   IMEI                 VARCHAR2(50)                    not null,
   UA                   VARCHAR2(100),
   CHANNEL_ID           NUMBER(15),
   DEVICE_CODE          VARCHAR2(100),
   GROUP_ID             NUMBER(15),
   BATCH_CODE           VARCHAR2(100),
   PROCESS_TIME         DATE,
   DEVICE_UPLOAD_TIME   DATE                           default SYSDATE not null,
   ACTIVE               NUMBER(2),
   COUNTER_UPLOAD_TIME  DATE,
   MD5_KEY              VARCHAR2(50)                    not null,
   P_MD5_KEY            VARCHAR2(50),
   constraint PK_TY_DATA_LOG_20142 primary key (ID)
);

comment on table TY_DATA_LOG_20142 is
'数据流水表20142';

comment on column TY_DATA_LOG_20142.ID is
'主键ID';

comment on column TY_DATA_LOG_20142.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20142.UA is
'手机UA';

comment on column TY_DATA_LOG_20142.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20142.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20142.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20142.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20142.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20142.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20142.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20142.COUNTER_UPLOAD_TIME is
'计数器上传时间';

comment on column TY_DATA_LOG_20142.MD5_KEY is
'MD5_KEY=(UA + ChannelId + DeviceCode)';

comment on column TY_DATA_LOG_20142.P_MD5_KEY is
'P_MD5_KEY=(UA + GroupId + BatchCode)';

/*==============================================================*/
/* Index: UNIQ_DL_20142_IMEI                                    */
/*==============================================================*/
create unique index UNIQ_DL_20142_IMEI on TY_DATA_LOG_20142 (
   IMEI ASC
);

/*==============================================================*/
/* Index: IDX_DL_20142_DTIME                                    */
/*==============================================================*/
create index IDX_DL_20142_DTIME on TY_DATA_LOG_20142 (
   DEVICE_UPLOAD_TIME ASC
);

/*==============================================================*/
/* Index: IDX_DL_20142_CTIME                                    */
/*==============================================================*/
create index IDX_DL_20142_CTIME on TY_DATA_LOG_20142 (
   COUNTER_UPLOAD_TIME ASC
);

/*==============================================================*/
/* Index: IDX_DL_20142_MD5                                      */
/*==============================================================*/
create index IDX_DL_20142_MD5 on TY_DATA_LOG_20142 (
   MD5_KEY ASC
);

/*==============================================================*/
/* Index: IDX_DL_20142_PMD5                                     */
/*==============================================================*/
create index IDX_DL_20142_PMD5 on TY_DATA_LOG_20142 (
   P_MD5_KEY ASC
);

/*==============================================================*/
/* Table: TY_DATA_LOG_20143                                     */
/*==============================================================*/
create table TY_DATA_LOG_20143  (
   ID                   NUMBER(15)                      not null,
   IMEI                 VARCHAR2(50)                    not null,
   UA                   VARCHAR2(100),
   CHANNEL_ID           NUMBER(15),
   DEVICE_CODE          VARCHAR2(100),
   GROUP_ID             NUMBER(15),
   BATCH_CODE           VARCHAR2(100),
   PROCESS_TIME         DATE,
   DEVICE_UPLOAD_TIME   DATE                           default SYSDATE not null,
   ACTIVE               NUMBER(2),
   COUNTER_UPLOAD_TIME  DATE,
   MD5_KEY              VARCHAR2(50)                    not null,
   P_MD5_KEY            VARCHAR2(50),
   constraint PK_TY_DATA_LOG_20143 primary key (ID)
);

comment on table TY_DATA_LOG_20143 is
'数据流水表20143';

comment on column TY_DATA_LOG_20143.ID is
'主键ID';

comment on column TY_DATA_LOG_20143.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20143.UA is
'手机UA';

comment on column TY_DATA_LOG_20143.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20143.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20143.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20143.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20143.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20143.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20143.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20143.COUNTER_UPLOAD_TIME is
'计数器上传时间';

comment on column TY_DATA_LOG_20143.MD5_KEY is
'MD5_KEY=(UA + ChannelId + DeviceCode )';

comment on column TY_DATA_LOG_20143.P_MD5_KEY is
'P_MD5_KEY=(UA + GroupId + BatchCode)';

/*==============================================================*/
/* Index: UNIQ_DL_20143_IMEI                                    */
/*==============================================================*/
create unique index UNIQ_DL_20143_IMEI on TY_DATA_LOG_20143 (
   IMEI ASC
);

/*==============================================================*/
/* Index: IDX_DL_20143_DTIME                                    */
/*==============================================================*/
create index IDX_DL_20143_DTIME on TY_DATA_LOG_20143 (
   DEVICE_UPLOAD_TIME ASC
);

/*==============================================================*/
/* Index: IDX_DL_20143_CTIME                                    */
/*==============================================================*/
create index IDX_DL_20143_CTIME on TY_DATA_LOG_20143 (
   COUNTER_UPLOAD_TIME ASC
);

/*==============================================================*/
/* Index: IDX_DL_20143_MD5                                      */
/*==============================================================*/
create index IDX_DL_20143_MD5 on TY_DATA_LOG_20143 (
   MD5_KEY ASC
);

/*==============================================================*/
/* Index: IDX_DL_20143_PMD5                                     */
/*==============================================================*/
create index IDX_DL_20143_PMD5 on TY_DATA_LOG_20143 (
   P_MD5_KEY ASC
);

/*==============================================================*/
/* Table: TY_DATA_LOG_20144                                     */
/*==============================================================*/
create table TY_DATA_LOG_20144  (
   ID                   NUMBER(15)                      not null,
   IMEI                 VARCHAR2(50)                    not null,
   UA                   VARCHAR2(100),
   CHANNEL_ID           NUMBER(15),
   DEVICE_CODE          VARCHAR2(100),
   GROUP_ID             NUMBER(15),
   BATCH_CODE           VARCHAR2(100),
   PROCESS_TIME         DATE,
   DEVICE_UPLOAD_TIME   DATE                           default SYSDATE not null,
   ACTIVE               NUMBER(2),
   COUNTER_UPLOAD_TIME  DATE,
   MD5_KEY              VARCHAR2(50)                    not null,
   P_MD5_KEY            VARCHAR2(50),
   constraint PK_TY_DATA_LOG_20144 primary key (ID)
);

comment on table TY_DATA_LOG_20144 is
'数据流水表20144';

comment on column TY_DATA_LOG_20144.ID is
'主键ID';

comment on column TY_DATA_LOG_20144.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20144.UA is
'手机UA';

comment on column TY_DATA_LOG_20144.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20144.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20144.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20144.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20144.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20144.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20144.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20144.COUNTER_UPLOAD_TIME is
'计数器上传时间';

comment on column TY_DATA_LOG_20144.MD5_KEY is
'MD5_KEY=(UA + ChannelId + DeviceCode)';

comment on column TY_DATA_LOG_20144.P_MD5_KEY is
'P_MD5_KEY=(UA + GroupId + BatchCode)';

/*==============================================================*/
/* Index: UNIQ_DL_20144_IMEI                                    */
/*==============================================================*/
create unique index UNIQ_DL_20144_IMEI on TY_DATA_LOG_20144 (
   IMEI ASC
);

/*==============================================================*/
/* Index: IDX_DL_20144_DTIME                                    */
/*==============================================================*/
create index IDX_DL_20144_DTIME on TY_DATA_LOG_20144 (
   DEVICE_UPLOAD_TIME ASC
);

/*==============================================================*/
/* Index: IDX_DL_20144_CTIME                                    */
/*==============================================================*/
create index IDX_DL_20144_CTIME on TY_DATA_LOG_20144 (
   COUNTER_UPLOAD_TIME ASC
);

/*==============================================================*/
/* Index: IDX_DL_20144_MD5                                      */
/*==============================================================*/
create index IDX_DL_20144_MD5 on TY_DATA_LOG_20144 (
   MD5_KEY ASC
);

/*==============================================================*/
/* Index: IDX_DL_20144_PMD5                                     */
/*==============================================================*/
create index IDX_DL_20144_PMD5 on TY_DATA_LOG_20144 (
   P_MD5_KEY ASC
);

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
'加工设备信息表';

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
/* Index: "Index_7"                                             */
/*==============================================================*/
create unique index "Index_7" on TY_DICT_INFO (
   KEY_CODE ASC
);

/*==============================================================*/
/* Table: TY_LOG_STAT                                           */
/*==============================================================*/
create table TY_LOG_STAT  (
   ID                   NUMBER(15)                      not null,
   UA                   VARCHAR2(100),
   GROUP_ID             NUMBER(15),
   CHANNEL_ID           NUMBER(15),
   DEVICE_CODE          VARCHAR2(100),
   PROCESS_DATE         DATE,
   LAOWU_ID             NUMBER(15),
   DEVICE_PRS_DAY_NUM   NUMBER(15)                     default 0,
   DEVICE_UPD_DAY_NUM   NUMBER(15)                     default 0,
   PRS_ACTIVE_TOTAL_NUM NUMBER(15)                     default 0,
   PRS_ACTIVE_VALID_NUM NUMBER(15)                     default 0,
   PRS_ACTIVE_INVALID_NUM NUMBER(15)                     default 0,
   PRS_INVALID_REPLACE_NUM NUMBER(15)                     default 0,
   PRS_INVALID_UNINSTALL_NUM NUMBER(15)                     default 0,
   COUNTER_UPD_DAY_NUM  NUMBER(15)                     default 0,
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

comment on column TY_LOG_STAT.COUNTER_UPD_DAY_NUM is
'计数器当天上传的总数';

comment on column TY_LOG_STAT.MD5_KEY is
'MD5KEY';

comment on column TY_LOG_STAT.VERSION is
'VERSION';

/*==============================================================*/
/* Index: "Index_3"                                             */
/*==============================================================*/
create unique index "Index_3" on TY_LOG_STAT (
   MD5_KEY ASC
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
/* Index: "Index_13"                                            */
/*==============================================================*/
create index "Index_13" on TY_PACKAGE_APK_REF (
   PACKAGE_ID ASC,
   APK_ID ASC
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
'数据状态 Y:????效，N:无效';

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
   PRS_ACTIVE_INVALID_NUM NUMBER(15)                     default 0,
   PRS_INVALID_REPLACE_NUM NUMBER(15)                     default 0,
   PRS_INVALID_UNINSTALL_NUM NUMBER(15)                     default 0,
   COUNTER_PRODUCT_DAY_NUM NUMBER(15)                     default 0,
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

comment on column TY_PRODUCT_STAT.COUNTER_PRODUCT_DAY_NUM is
'产品当天上传总数';

comment on column TY_PRODUCT_STAT.MD5_KEY is
'MD5KEY';

comment on column TY_PRODUCT_STAT.VERSION is
'VERSION';

/*==============================================================*/
/* Index: "Index_4"                                             */
/*==============================================================*/
create unique index "Index_4" on TY_PRODUCT_STAT (
   MD5_KEY ASC
);

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
   PKG_TYPE             VARCHAR(2),
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
   CREATE_TIME          DATE                           default SYSDATE,
   UPDATE_TIME          DATE                           default SYSDATE,
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
'安装包类型 Y:通用包，N:普通包';

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
/* Index: "Index_12"                                            */
/*==============================================================*/
create index "Index_12" on TY_PUB_CHL_MOD_REF (
   GROUP_ID ASC,
   CHANNEL_ID ASC
);

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
   LEVELS               NUMBER(15),
   CREATE_TIME          DATE                           default SYSDATE,
   constraint PK_TY_ROLE primary key (ROLE_ID)
);

comment on table TY_ROLE is
'角色表';

/*==============================================================*/
/* Table: TY_ROLE_RESOURCE_REF                                  */
/*==============================================================*/
create table TY_ROLE_RESOURCE_REF  (
   ID                   NUMBER(15)                      not null,
   ROLE_ID              NUMBER(15),
   RESOURCE_ID          NUMBER(15),
   ACCES                NUMBER(2),
   CREATE_TIME          DATE                           default SYSDATE,
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
/* Index: "Index_8"                                             */
/*==============================================================*/
create index "Index_8" on TY_SETTLE_INFO (
   CREATE_BY ASC
);

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
'用户与角色映射表';

/*==============================================================*/
/* Index: "Index_1"                                             */
/*==============================================================*/
create index "Index_1" on TY_USER_ROLE_REF (
   USER_ID ASC,
   ROLE_ID ASC
);

