drop table TY_DATA_LOG_20142 cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20142                                     */
/*==============================================================*/
create table TY_DATA_LOG_20142  (
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

drop table TY_DATA_LOG_20143 cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20143                                     */
/*==============================================================*/
create table TY_DATA_LOG_20143  (
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

drop table TY_DATA_LOG_20144 cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20144                                     */
/*==============================================================*/
create table TY_DATA_LOG_20144  (
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


drop table TY_DATA_LOG_20151 cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20151                                     */
/*==============================================================*/
create table TY_DATA_LOG_20151  (
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
   constraint PK_TY_DATA_LOG_20151 primary key (ID)
);

comment on table TY_DATA_LOG_20151 is
'数据流水表20151';

comment on column TY_DATA_LOG_20151.ID is
'主键ID';

comment on column TY_DATA_LOG_20151.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20151.UA is
'手机UA';

comment on column TY_DATA_LOG_20151.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20151.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20151.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20151.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20151.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20151.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20151.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20151.COUNTER_UPLOAD_TIME is
'计数器上传时间';

drop table TY_DATA_LOG_20152 cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20152                                     */
/*==============================================================*/
create table TY_DATA_LOG_20152  (
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
   constraint PK_TY_DATA_LOG_20152 primary key (ID)
);

comment on table TY_DATA_LOG_20152 is
'数据流水表20152';

comment on column TY_DATA_LOG_20152.ID is
'主键ID';

comment on column TY_DATA_LOG_20152.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20152.UA is
'手机UA';

comment on column TY_DATA_LOG_20152.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20152.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20152.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20152.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20152.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20152.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20152.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20152.COUNTER_UPLOAD_TIME is
'计数器上传时间';

drop table TY_DATA_LOG_20153 cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20153                                     */
/*==============================================================*/
create table TY_DATA_LOG_20153  (
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
   constraint PK_TY_DATA_LOG_20153 primary key (ID)
);

comment on table TY_DATA_LOG_20153 is
'数据流水表20153';

comment on column TY_DATA_LOG_20153.ID is
'主键ID';

comment on column TY_DATA_LOG_20153.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20153.UA is
'手机UA';

comment on column TY_DATA_LOG_20153.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20153.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20153.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20153.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20153.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20153.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20153.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20153.COUNTER_UPLOAD_TIME is
'计数器上传时间';

drop table TY_DATA_LOG_20154 cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20154                                     */
/*==============================================================*/
create table TY_DATA_LOG_20154  (
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
   constraint PK_TY_DATA_LOG_20154 primary key (ID)
);

comment on table TY_DATA_LOG_20154 is
'数据流水表20154';

comment on column TY_DATA_LOG_20154.ID is
'主键ID';

comment on column TY_DATA_LOG_20154.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20154.UA is
'手机UA';

comment on column TY_DATA_LOG_20154.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20154.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20154.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20154.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20154.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20154.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20154.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20154.COUNTER_UPLOAD_TIME is
'计数器上传时间';

drop table TY_DATA_LOG_20161 cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20161                                     */
/*==============================================================*/
create table TY_DATA_LOG_20161  (
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
   constraint PK_TY_DATA_LOG_20161 primary key (ID)
);

comment on table TY_DATA_LOG_20161 is
'数据流水表20161';

comment on column TY_DATA_LOG_20161.ID is
'主键ID';

comment on column TY_DATA_LOG_20161.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20161.UA is
'手机UA';

comment on column TY_DATA_LOG_20161.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20161.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20161.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20161.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20161.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20161.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20161.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20161.COUNTER_UPLOAD_TIME is
'计数器上传时间';

drop table TY_DATA_LOG_20162 cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20162                                     */
/*==============================================================*/
create table TY_DATA_LOG_20162  (
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
   constraint PK_TY_DATA_LOG_20162 primary key (ID)
);

comment on table TY_DATA_LOG_20162 is
'数据流水表20162';

comment on column TY_DATA_LOG_20162.ID is
'主键ID';

comment on column TY_DATA_LOG_20162.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20162.UA is
'手机UA';

comment on column TY_DATA_LOG_20162.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20162.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20162.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20162.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20162.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20162.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20162.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20162.COUNTER_UPLOAD_TIME is
'计数器上传时间';

drop table TY_DATA_LOG_20163 cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20163                                     */
/*==============================================================*/
create table TY_DATA_LOG_20163  (
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
   constraint PK_TY_DATA_LOG_20163 primary key (ID)
);

comment on table TY_DATA_LOG_20163 is
'数据流水表20163';

comment on column TY_DATA_LOG_20163.ID is
'主键ID';

comment on column TY_DATA_LOG_20163.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20163.UA is
'手机UA';

comment on column TY_DATA_LOG_20163.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20163.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20163.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20163.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20163.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20163.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20163.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20163.COUNTER_UPLOAD_TIME is
'计数器上传时间';

drop table TY_DATA_LOG_20164 cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20164                                     */
/*==============================================================*/
create table TY_DATA_LOG_20164  (
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
   constraint PK_TY_DATA_LOG_20164 primary key (ID)
);

comment on table TY_DATA_LOG_20164 is
'数据流水表20164';

comment on column TY_DATA_LOG_20164.ID is
'主键ID';

comment on column TY_DATA_LOG_20164.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20164.UA is
'手机UA';

comment on column TY_DATA_LOG_20164.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20164.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20164.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20164.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20164.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20164.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20164.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20164.COUNTER_UPLOAD_TIME is
'计数器上传时间';

drop table TY_DATA_LOG_20171 cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20171                                     */
/*==============================================================*/
create table TY_DATA_LOG_20171  (
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
   constraint PK_TY_DATA_LOG_20171 primary key (ID)
);

comment on table TY_DATA_LOG_20171 is
'数据流水表20171';

comment on column TY_DATA_LOG_20171.ID is
'主键ID';

comment on column TY_DATA_LOG_20171.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20171.UA is
'手机UA';

comment on column TY_DATA_LOG_20171.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20171.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20171.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20171.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20171.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20171.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20171.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20171.COUNTER_UPLOAD_TIME is
'计数器上传时间';

drop table TY_DATA_LOG_20172 cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20172                                     */
/*==============================================================*/
create table TY_DATA_LOG_20172  (
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
   constraint PK_TY_DATA_LOG_20172 primary key (ID)
);

comment on table TY_DATA_LOG_20172 is
'数据流水表20172';

comment on column TY_DATA_LOG_20172.ID is
'主键ID';

comment on column TY_DATA_LOG_20172.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20172.UA is
'手机UA';

comment on column TY_DATA_LOG_20172.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20172.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20172.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20172.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20172.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20172.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20172.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20172.COUNTER_UPLOAD_TIME is
'计数器上传时间';

drop table TY_DATA_LOG_20173 cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20173                                     */
/*==============================================================*/
create table TY_DATA_LOG_20173  (
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
   constraint PK_TY_DATA_LOG_20173 primary key (ID)
);

comment on table TY_DATA_LOG_20173 is
'数据流水表20173';

comment on column TY_DATA_LOG_20173.ID is
'主键ID';

comment on column TY_DATA_LOG_20173.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20173.UA is
'手机UA';

comment on column TY_DATA_LOG_20173.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20173.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20173.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20173.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20173.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20173.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20173.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20173.COUNTER_UPLOAD_TIME is
'计数器上传时间';

drop table TY_DATA_LOG_20174 cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20174                                     */
/*==============================================================*/
create table TY_DATA_LOG_20174  (
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
   constraint PK_TY_DATA_LOG_20174 primary key (ID)
);

comment on table TY_DATA_LOG_20174 is
'数据流水表20174';

comment on column TY_DATA_LOG_20174.ID is
'主键ID';

comment on column TY_DATA_LOG_20174.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20174.UA is
'手机UA';

comment on column TY_DATA_LOG_20174.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20174.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20174.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20174.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20174.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20174.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20174.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20174.COUNTER_UPLOAD_TIME is
'计数器上传时间';

drop table TY_DATA_LOG_20181 cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20181                                     */
/*==============================================================*/
create table TY_DATA_LOG_20181  (
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
   constraint PK_TY_DATA_LOG_20181 primary key (ID)
);

comment on table TY_DATA_LOG_20181 is
'数据流水表20181';

comment on column TY_DATA_LOG_20181.ID is
'主键ID';

comment on column TY_DATA_LOG_20181.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20181.UA is
'手机UA';

comment on column TY_DATA_LOG_20181.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20181.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20181.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20181.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20181.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20181.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20181.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20181.COUNTER_UPLOAD_TIME is
'计数器上传时间';

drop table TY_DATA_LOG_20182 cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20182                                     */
/*==============================================================*/
create table TY_DATA_LOG_20182  (
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
   constraint PK_TY_DATA_LOG_20182 primary key (ID)
);

comment on table TY_DATA_LOG_20182 is
'数据流水表20182';

comment on column TY_DATA_LOG_20182.ID is
'主键ID';

comment on column TY_DATA_LOG_20182.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20182.UA is
'手机UA';

comment on column TY_DATA_LOG_20182.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20182.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20182.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20182.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20182.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20182.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20182.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20182.COUNTER_UPLOAD_TIME is
'计数器上传时间';

drop table TY_DATA_LOG_20183 cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20183                                     */
/*==============================================================*/
create table TY_DATA_LOG_20183  (
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
   constraint PK_TY_DATA_LOG_20183 primary key (ID)
);

comment on table TY_DATA_LOG_20183 is
'数据流水表20183';

comment on column TY_DATA_LOG_20183.ID is
'主键ID';

comment on column TY_DATA_LOG_20183.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20183.UA is
'手机UA';

comment on column TY_DATA_LOG_20183.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20183.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20183.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20183.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20183.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20183.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20183.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20183.COUNTER_UPLOAD_TIME is
'计数器上传时间';


drop table TY_DATA_LOG_20184 cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20184                                     */
/*==============================================================*/
create table TY_DATA_LOG_20184  (
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
   constraint PK_TY_DATA_LOG_20184 primary key (ID)
);

comment on table TY_DATA_LOG_20184 is
'数据流水表20184';

comment on column TY_DATA_LOG_20184.ID is
'主键ID';

comment on column TY_DATA_LOG_20184.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_20184.UA is
'手机UA';

comment on column TY_DATA_LOG_20184.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_20184.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_20184.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_20184.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_20184.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_20184.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_20184.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_20184.COUNTER_UPLOAD_TIME is
'计数器上传时间';

