create table TY_DEVICE_PROCESS_LOG
(
  process_id   NUMBER(15) not null,
  imei         varchar2(50),
  ua           varchar2(100),
  channel_id   number(15),
  device_code  varchar2(100),
  group_id     number(15),
  batch_code   varchar2(100),
  process_time date,
  create_time  date default SYSDATE not null
);
comment on column TY_DEVICE_PROCESS_LOG.process_id
  is '唯一主键';
comment on column TY_DEVICE_PROCESS_LOG.imei
  is '手机imei码';
comment on column TY_DEVICE_PROCESS_LOG.ua
  is '手机ua';
comment on column TY_DEVICE_PROCESS_LOG.channel_id
  is '渠道id';
comment on column TY_DEVICE_PROCESS_LOG.device_code
  is '设备编码';
comment on column TY_DEVICE_PROCESS_LOG.group_id
  is '渠道组id';
comment on column TY_DEVICE_PROCESS_LOG.batch_code
  is '批次号';
comment on column TY_DEVICE_PROCESS_LOG.process_time
  is '加工时间';
comment on column TY_DEVICE_PROCESS_LOG.create_time
  is '上传时间';


alter table TY_DEVICE_PROCESS_LOG
  add constraint PK_TY_DEVICE_PROCESS_LOG primary key (PROCESS_ID)
  using index ;
create index  IDX_PROCESS_LOG_IMEI on TY_DEVICE_PROCESS_LOG(IMEI);



  -- Create table
create table TY_COUNTER_UPLOAD_LOG
(
  counter_id   number(15) not null,
  imei         varchar2(50),
  ua           varchar2(100),
  channel_id   number(15),
  device_code  varchar2(100),
  group_id     number(15),
  batch_code   varchar2(100),
  process_time date,
  create_time  date default SYSDATE not null,
  active       number(1) not null
)
;
-- Add comments to the columns
comment on column TY_COUNTER_UPLOAD_LOG.counter_id
  is '唯一主键';
comment on column TY_COUNTER_UPLOAD_LOG.imei
  is '手机imei码';
comment on column TY_COUNTER_UPLOAD_LOG.ua
  is '手机ua';
comment on column TY_COUNTER_UPLOAD_LOG.channel_id
  is '渠道id';
comment on column TY_COUNTER_UPLOAD_LOG.device_code
  is '设备编码';
comment on column TY_COUNTER_UPLOAD_LOG.group_id
  is '渠道组id';
comment on column TY_COUNTER_UPLOAD_LOG.batch_code
  is '批次号';
comment on column TY_COUNTER_UPLOAD_LOG.process_time
  is '加工日期';
comment on column TY_COUNTER_UPLOAD_LOG.create_time
  is '上传时间';
comment on column TY_COUNTER_UPLOAD_LOG.active
  is '1：有效到达，2：无效-替换，3：无效-卸载';


alter table TY_COUNTER_UPLOAD_LOG
  add constraint PK_TY_COUNTER_UPLOAD_LOG primary key (COUNTER_ID);
create index  IDX_COUNTER_LOG_IMEI on TY_COUNTER_UPLOAD_LOG(IMEI);



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
  add constraint PK_TY_DICT_INFO primary key (DICT_ID);
alter table TY_DICT_INFO
  add constraint UNIQ_TY_DICT_INFO_KEYCODE unique (KEY_CODE);


