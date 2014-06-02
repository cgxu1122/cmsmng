create table TY_DEVICE_PROCESS_LOG_20142
(
  process_id   NUMBER(15) not null,
  imei         varchar2(50),
  ua           varchar2(100),
  model_name  varchar2(100),
  channel_id   varchar2(100),
  device_code  varchar2(100),
  group_id     number(15),
  batch_code   varchar2(100),
  process_time varchar2(100),
  create_time  date default SYSDATE not null
);
comment on column TY_DEVICE_PROCESS_LOG_20142.process_id
  is '唯一主键';
comment on column TY_DEVICE_PROCESS_LOG_20142.imei
  is '手机imei码';
comment on column TY_DEVICE_PROCESS_LOG_20142.ua
  is '手机ua';
comment on column TY_DEVICE_PROCESS_LOG_20142.model_name
  is '机型名称';
comment on column TY_DEVICE_PROCESS_LOG_20142.channel_id
  is '渠道id';
comment on column TY_DEVICE_PROCESS_LOG_20142.device_code
  is '设备编码';
comment on column TY_DEVICE_PROCESS_LOG_20142.group_id
  is '渠道组id';
comment on column TY_DEVICE_PROCESS_LOG_20142.batch_code
  is '批次号';
comment on column TY_DEVICE_PROCESS_LOG_20142.process_time
  is '加工时间';
comment on column TY_DEVICE_PROCESS_LOG_20142.create_time
  is '上传时间';


alter table TY_DEVICE_PROCESS_LOG_20142
  add constraint PK_TY_DEVICE_PROCESS_LOG_20142 primary key (process_id)
  using index ;
create index  IDX_DEVICE_LOG_20142_IMEI on TY_DEVICE_PROCESS_LOG_20142(imei);
create index  IDX_DEVICE_LOG_20142_CTIME on TY_DEVICE_PROCESS_LOG_20142(create_time);



  -- Create table
create table TY_COUNTER_UPLOAD_LOG_20142
(
  counter_id   number(15) not null,
  imei          varchar2(50),
  ua            varchar2(100),
  model_name   varchar2(100),
  channel_id   varchar2(100),
  device_code  varchar2(100),
  group_id     number(15),
  batch_code   varchar2(100),
  process_time varchar2(100),
  create_time  date default SYSDATE not null,
  active        varchar2(100) not null
)
;
-- Add comments to the columns
comment on column TY_COUNTER_UPLOAD_LOG_20142.counter_id
  is '唯一主键';
comment on column TY_COUNTER_UPLOAD_LOG_20142.imei
  is '手机imei码';
comment on column TY_COUNTER_UPLOAD_LOG_20142.ua
  is '手机ua';
comment on column TY_COUNTER_UPLOAD_LOG_20142.model_name
  is '机型名称';
comment on column TY_COUNTER_UPLOAD_LOG_20142.channel_id
  is '渠道id';
comment on column TY_COUNTER_UPLOAD_LOG_20142.device_code
  is '设备编码';
comment on column TY_COUNTER_UPLOAD_LOG_20142.group_id
  is '渠道组id';
comment on column TY_COUNTER_UPLOAD_LOG_20142.batch_code
  is '批次号';
comment on column TY_COUNTER_UPLOAD_LOG_20142.process_time
  is '加工日期';
comment on column TY_COUNTER_UPLOAD_LOG_20142.create_time
  is '上传时间';
comment on column TY_COUNTER_UPLOAD_LOG_20142.active
  is '1：有效到达，2：无效-替换，3：无效-卸载';


alter table TY_COUNTER_UPLOAD_LOG_20142 add constraint PK_TY_COUNTER_UPLOAD_LOG_20142 primary key (counter_id);
create index  IDX_COUNTER_LOG_20142_IMEI on TY_COUNTER_UPLOAD_LOG_20142(imei);
create index  IDX_COUNTER_LOG_20142_CTIME on TY_COUNTER_UPLOAD_LOG_20142(create_time);



-- Create table
create table TY_DICT_INFO
(
  dict_id     number(15) not null,
  key_code    varchar2(50) not null,
  key_value   varchar2(50) not null,
  remark      varchar2(1000),
  create_time date default SYSDATE not null,
  update_time date default SYSDATE not null
);
-- Create/Recreate primary, unique and foreign key constraints
alter table TY_DICT_INFO
  add constraint PK_TY_DICT_INFO primary key (dict_id);
alter table TY_DICT_INFO
  add constraint UNIQ_TY_DICT_INFO_KEYCODE unique (key_code);



create table TY_COUNTER_FAIL_LOG
(
  fail_id      number(15) not null,
  imei          varchar2(50),
  ua            varchar2(100),
  model_name   varchar2(100),
  channel_id   varchar2(100),
  device_code  varchar2(100),
  group_id     number(15),
  batch_code   varchar2(100),
  process_time varchar2(100),
  create_time  date default SYSDATE not null,
  active        varchar2(100) not null
);
-- Add comments to the columns
comment on column TY_COUNTER_FAIL_LOG.fail_id
  is '唯一主键';
comment on column TY_COUNTER_FAIL_LOG.imei
  is '手机imei码';
comment on column TY_COUNTER_FAIL_LOG.ua
  is '手机ua';
comment on column TY_COUNTER_FAIL_LOG.model_name
  is '机型名称';
comment on column TY_COUNTER_FAIL_LOG.channel_id
  is '渠道id';
comment on column TY_COUNTER_FAIL_LOG.device_code
  is '设备编码';
comment on column TY_COUNTER_FAIL_LOG.group_id
  is '渠道组id';
comment on column TY_COUNTER_FAIL_LOG.batch_code
  is '批次号';
comment on column TY_COUNTER_FAIL_LOG.process_time
  is '加工日期';
comment on column TY_COUNTER_FAIL_LOG.create_time
  is '上传时间';
comment on column TY_COUNTER_FAIL_LOG.active
  is '1：有效到达，2：无效-替换，3：无效-卸载';


alter table TY_COUNTER_FAIL_LOG
  add constraint TY_COUNTER_FAIL_LOG primary key (fail_id);
alter table TY_COUNTER_FAIL_LOG
  add constraint UNIQ_COUNTER_FAIL_LOG_IMEI unique (IMEI);
create index  IDX_TY_FAIL_LOG_CTIME on TY_COUNTER_FAIL_LOG(create_time);


-- Create table
create table TY_CHANNEL_VERSION
(
  version_id  NUMBER(15) not null,
  channel_id  NUMBER(15) not null,
  md5value    VARCHAR2(50) not null,
  path        VARCHAR2(500) not null,
  version     VARCHAR2(50) not null,
  create_time DATE not null,
  update_time DATE not null
)
comment on table TY_CHANNEL_VERSION  is '渠道对应apk版本库信息';

alter table TY_CHANNEL_VERSION add constraint PK_TY_CHANNEL_VERSION primary key (VERSION_ID);
alter table TY_CHANNEL_VERSION add constraint UNIQ_TY_CHANNEL_VERSION_CID unique (CHANNEL_ID);



create table TY_APK_INFO  (
   APK_ID         			number(15)           not null,
   APK_NAME            varchar2(50)					not null,
   SOFT_NAME           varchar2(100)					not null,
   FTP_PATH        		varchar2(500)         not null,
   MD5VALUE        		varchar2(500)         not null,
   DOWNLOAD_URL       varchar2(500)         not null,
   ACTIVE              varchar2(2)           not null,
   CREATE_TIME        		date               default SYSDATE not null,
   UPDATE_TIME        		date               default SYSDATE not null
);
alter table TY_APK_INFO add constraint PK_TY_APK_INFO primary key (APK_ID);
comment on column TY_APK_INFO.ACTIVE is 'Y:有效，N:无效';



create table TY_PACKAGE_INFO  (
   PACKAGE_ID         			number(15)           not null,
   PACKAGE_NAME            varchar2(100)					not null,
   BATCH_ID                number(15)         not null,
   BATCH_CODE              varchar2(100)         not null,
   TYPE                    varchar2(2)           not null,
   ACTIVE                  varchar2(2)           not null,
   REMARK                  varchar2(1000),
   CREATE_TIME        		date               default SYSDATE not null,
   UPDATE_TIME        		date               default SYSDATE not null
);
alter table TY_PACKAGE_INFO add constraint PK_TY_PACKAGE_INFO primary key (PACKAGE_ID);
comment on column TY_PACKAGE_INFO.ACTIVE is 'Y:有效，N:无效';
comment on column TY_PACKAGE_INFO.TYPE is 'Y:通用包，N:普通包';

create table TY_PACKAGE_APK_REF  (
   ID         			        number(15)           not null,
   PACKAGE_ID         			number(15)           not null,
   APK_ID         			    number(15)           not null,
   APK_NAME                varchar2(100)					not null,
   AUTO_RUN                varchar2(2)         not null,
   DESKTOP_ICON           varchar2(2)         not null,
   SORT                    number(10)         not null,
   ACTIVE                  varchar2(2)         not null,
   CREATE_TIME        		date               default SYSDATE not null
);
alter table TY_PACKAGE_APK_REF add constraint PK_TY_PACKAGE_APK_REF primary key (ID);
comment on column TY_PACKAGE_APK_REF.ACTIVE is 'Y:有效，N:无效';


create table TY_PUBLISH_TASK  (
   PUBLISH_ID         			number(15)           not null,
   PACKAGE_NAME            varchar2(100)					not null,
   PACKAGE_ID              number(15)         not null,
   EFFECT_TIME             date,
   ACTIVE              varchar2(2)           not null,
   CREATE_TIME        		date               default SYSDATE not null,
   UPDATE_TIME        		date               default SYSDATE not null
);
alter table TY_PUBLISH_TASK add constraint PK_TY_PUBLISH_TASK primary key (PUBLISH_ID);
comment on column TY_PUBLISH_TASK.ACTIVE is 'Y:有效，N:无效';


create table TY_PUBLISH_CHANNEL_REF  (
   ID         			      number(15)           not null,
   PUBLISH_ID         	  number(15)           not null,
   PACKAGE_ID         		number(15)           not null,
   GROUP_ID         			number(15)           not null,
   CHANNEL_ID         		number(15)           not null,
   CREATE_TIME        		date                 default SYSDATE not null,
);
alter table TY_PUBLISH_CHANNEL_REF add constraint PK_TY_PUBLISH_CHANNEL_REF primary key (ID);
comment on column TY_PUBLISH_CHANNEL_REF.ACTIVE is 'Y:有效，N:无效';


create table TY_PUBLISH_MODEL_REF  (
   ID         			      number(15)           not null,
   PUBLISH_ID         	  number(15)           not null,
   PACKAGE_ID         		number(15)           not null,
   MODEL_ID         			number(15)           not null,
   CREATE_TIME        		date                 default SYSDATE not null,
);
alter table TY_PUBLISH_MODEL_REF add constraint PK_TY_PUBLISH_MODEL_REF primary key (ID);
comment on column TY_PUBLISH_MODEL_REF.ACTIVE is 'Y:有效，N:无效';



