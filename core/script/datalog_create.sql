
drop table TY_DATA_LOG_201402 cascade constraints;


/*==============================================================*/
/* Table: TY_DATA_LOG_201402                                           */
/*==============================================================*/
create table TY_DATA_LOG_201402  (
   ID                   NUMBER(15)                      not null,
   IMEI                 VARCHAR2(50)                    not null,
   UA                   VARCHAR2(100),
   MODEL_NAME           VARCHAR2(100),
   CHANNEL_ID           NUMBER(15),
   DEVICE_CODE          VARCHAR2(100),
   GROUP_ID             NUMBER(15),
   BATCH_CODE           VARCHAR2(100),
   PROCESS_TIME         VARCHAR2(100),
   DEVICE_UPLOAD_TIME   DATE                           default SYSDATE,
   ACTIVE               NUMBER(2),
   COUNTER_UPLOAD_TIME  DATE,
   constraint PK_TY_DATA_LOG_201402 primary key (ID)
);

comment on table TY_DATA_LOG_201402 is
'数据流水表';

comment on column TY_DATA_LOG_201402.ID is
'主键ID';

comment on column TY_DATA_LOG_201402.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_201402.UA is
'手机UA';

comment on column TY_DATA_LOG_201402.MODEL_NAME is
'机型名称';

comment on column TY_DATA_LOG_201402.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_201402.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_201402.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_201402.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_201402.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_201402.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_201402.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_201402.COUNTER_UPLOAD_TIME is
'计数器上传时间';

/*==============================================================*/
/* Index: "UNIQ_IMEI_DATALOG_201402"                                            */
/*==============================================================*/
create unique index "UNIQ_IMEI_DATALOG_201402" on TY_DATA_LOG_201402 (
   IMEI ASC
);

/*==============================================================*/
/* Index: "IDX_DUTIME_DATALOG_201402"                                            */
/*==============================================================*/
create index "IDX_DUTIME_DATALOG_201402" on TY_DATA_LOG_201402 (
   DEVICE_UPLOAD_TIME ASC
);

/*==============================================================*/
/* Index: "IDX_CUTIME_DATALOG_201402"                                            */
/*==============================================================*/
create index "IDX_CUTIME_DATALOG_201402" on TY_DATA_LOG_201402 (
   COUNTER_UPLOAD_TIME ASC
);



drop table TY_DATA_LOG_201403 cascade constraints;


/*==============================================================*/
/* Table: TY_DATA_LOG_201403                                           */
/*==============================================================*/
create table TY_DATA_LOG_201403  (
   ID                   NUMBER(15)                      not null,
   IMEI                 VARCHAR2(50)                    not null,
   UA                   VARCHAR2(100),
   MODEL_NAME           VARCHAR2(100),
   CHANNEL_ID           NUMBER(15),
   DEVICE_CODE          VARCHAR2(100),
   GROUP_ID             NUMBER(15),
   BATCH_CODE           VARCHAR2(100),
   PROCESS_TIME         VARCHAR2(100),
   DEVICE_UPLOAD_TIME   DATE                           default SYSDATE,
   ACTIVE               NUMBER(2),
   COUNTER_UPLOAD_TIME  DATE,
   constraint PK_DATA_LOG_201403 primary key (ID)
);

comment on table TY_DATA_LOG_201403 is
'数据流水表';

comment on column TY_DATA_LOG_201403.ID is
'主键ID';

comment on column TY_DATA_LOG_201403.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_201403.UA is
'手机UA';

comment on column TY_DATA_LOG_201403.MODEL_NAME is
'机型名称';

comment on column TY_DATA_LOG_201403.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_201403.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_201403.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_201403.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_201403.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_201403.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_201403.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_201403.COUNTER_UPLOAD_TIME is
'计数器上传时间';

/*==============================================================*/
/* Index: "UNIQ_IMEI_DATALOG_201403"                                            */
/*==============================================================*/
create unique index "UNIQ_IMEI_DATALOG_201403" on TY_DATA_LOG_201403 (
   IMEI ASC
);

/*==============================================================*/
/* Index: "IDX_DUTIME_DATALOG_201403"                                            */
/*==============================================================*/
create index "IDX_DUTIME_DATALOG_201403" on TY_DATA_LOG_201403 (
   DEVICE_UPLOAD_TIME ASC
);

/*==============================================================*/
/* Index: "IDX_CUTIME_DATALOG_201403"                                            */
/*==============================================================*/
create index "IDX_CUTIME_DATALOG_201403" on TY_DATA_LOG_201403 (
   COUNTER_UPLOAD_TIME ASC
);


drop table TY_DATA_LOG_201404 cascade constraints;


/*==============================================================*/
/* Table: TY_DATA_LOG_201403                                           */
/*==============================================================*/
create table TY_DATA_LOG_201404  (
   ID                   NUMBER(15)                      not null,
   IMEI                 VARCHAR2(50)                    not null,
   UA                   VARCHAR2(100),
   MODEL_NAME           VARCHAR2(100),
   CHANNEL_ID           NUMBER(15),
   DEVICE_CODE          VARCHAR2(100),
   GROUP_ID             NUMBER(15),
   BATCH_CODE           VARCHAR2(100),
   PROCESS_TIME         VARCHAR2(100),
   DEVICE_UPLOAD_TIME   DATE                           default SYSDATE,
   ACTIVE               NUMBER(2),
   COUNTER_UPLOAD_TIME  DATE,
   constraint PK_DATA_LOG_201404 primary key (ID)
);

comment on table TY_DATA_LOG_201404 is
'数据流水表';

comment on column TY_DATA_LOG_201404.ID is
'主键ID';

comment on column TY_DATA_LOG_201404.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_201404.UA is
'手机UA';

comment on column TY_DATA_LOG_201404.MODEL_NAME is
'机型名称';

comment on column TY_DATA_LOG_201404.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_201404.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_201404.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_201404.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_201404.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_201404.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_201404.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_201404.COUNTER_UPLOAD_TIME is
'计数器上传时间';

/*==============================================================*/
/* Index: "UNIQ_IMEI_DATALOG_201404"                                            */
/*==============================================================*/
create unique index "UNIQ_IMEI_DATALOG_201404" on TY_DATA_LOG_201404 (
   IMEI ASC
);

/*==============================================================*/
/* Index: "IDX_DUTIME_DATALOG_201404"                                            */
/*==============================================================*/
create index "IDX_DUTIME_DATALOG_201404" on TY_DATA_LOG_201404 (
   DEVICE_UPLOAD_TIME ASC
);

/*==============================================================*/
/* Index: "IDX_CUTIME_DATALOG_201404"                                            */
/*==============================================================*/
create index "IDX_CUTIME_DATALOG_201404" on TY_DATA_LOG_201404 (
   COUNTER_UPLOAD_TIME ASC
);



drop table TY_DATA_LOG_201501 cascade constraints;


/*==============================================================*/
/* Table: TY_DATA_LOG_201403                                           */
/*==============================================================*/
create table TY_DATA_LOG_201501  (
   ID                   NUMBER(15)                      not null,
   IMEI                 VARCHAR2(50)                    not null,
   UA                   VARCHAR2(100),
   MODEL_NAME           VARCHAR2(100),
   CHANNEL_ID           NUMBER(15),
   DEVICE_CODE          VARCHAR2(100),
   GROUP_ID             NUMBER(15),
   BATCH_CODE           VARCHAR2(100),
   PROCESS_TIME         VARCHAR2(100),
   DEVICE_UPLOAD_TIME   DATE                           default SYSDATE,
   ACTIVE               NUMBER(2),
   COUNTER_UPLOAD_TIME  DATE,
   constraint PK_DATA_LOG_201501 primary key (ID)
);

comment on table TY_DATA_LOG_201501 is
'数据流水表';

comment on column TY_DATA_LOG_201501.ID is
'主键ID';

comment on column TY_DATA_LOG_201501.IMEI is
'IMEI码';

comment on column TY_DATA_LOG_201501.UA is
'手机UA';

comment on column TY_DATA_LOG_201501.MODEL_NAME is
'机型名称';

comment on column TY_DATA_LOG_201501.CHANNEL_ID is
'渠道ID';

comment on column TY_DATA_LOG_201501.DEVICE_CODE is
'设备编码';

comment on column TY_DATA_LOG_201501.GROUP_ID is
'渠道组ID';

comment on column TY_DATA_LOG_201501.BATCH_CODE is
'批次号';

comment on column TY_DATA_LOG_201501.PROCESS_TIME is
'加工时间';

comment on column TY_DATA_LOG_201501.DEVICE_UPLOAD_TIME is
'设备上传时间';

comment on column TY_DATA_LOG_201501.ACTIVE is
'到达状态';

comment on column TY_DATA_LOG_201501.COUNTER_UPLOAD_TIME is
'计数器上传时间';

/*==============================================================*/
/* Index: "UNIQ_IMEI_DATALOG_201501"                                            */
/*==============================================================*/
create unique index "UNIQ_IMEI_DATALOG_201501" on TY_DATA_LOG_201501 (
   IMEI ASC
);

/*==============================================================*/
/* Index: "IDX_DUTIME_DATALOG_201501"                                            */
/*==============================================================*/
create index "IDX_DUTIME_DATALOG_201501" on TY_DATA_LOG_201501 (
   DEVICE_UPLOAD_TIME ASC
);

/*==============================================================*/
/* Index: "IDX_CUTIME_DATALOG_201501"                                            */
/*==============================================================*/
create index "IDX_CUTIME_DATALOG_201501" on TY_DATA_LOG_201501 (
   COUNTER_UPLOAD_TIME ASC
);