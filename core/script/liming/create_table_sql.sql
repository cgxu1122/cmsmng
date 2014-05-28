create table TY_LOG_COUNT
(
  LOG_COUNT_ID              NUMBER(15) not null,
  MODLE_NAME                VARCHAR2(100) not null,
  CHANNEL_ID                NUMBER(15) not null,
  DEVICE_CODE               VARCHAR2(100) not null,
  GROUP_ID                  NUMBER(15) not null,
  BATCH_CODE                VARCHAR2(100) not null,
  COUNT_TIME                DATE not null,
  PROCESS_KEY               VARCHAR2(40) not null,
  LAOWU_ID                  NUMBER(15),
  PROCESS_DAY_COUNT         NUMBER(15) default 0,
  DEVICE_UPLOAD_DAY_COUNT   NUMBER(15) default 0,
  ALLCOUNT                  NUMBER(15) default 0,
  ACTIVE_COUNT              NUMBER(15) default 0,
  NONACTIVE_COUNT           NUMBER(15) default 0,
  NONACTIVE_UNINSTALL_COUNT NUMBER(15) default 0,
  NONACTIVE_REPLACE_COUNT   NUMBER(15) default 0,
  COUNTER_UPLOAD_DAY_COUNT  NUMBER(15) default 0
);
-- Add comments to the table
comment on table TY_LOG_COUNT
  is '流水统计表';
-- Add comments to the columns
comment on column TY_LOG_COUNT.MODLE_NAME
  is '机型名称';
comment on column TY_LOG_COUNT.CHANNEL_ID
  is '渠道ID';
comment on column TY_LOG_COUNT.DEVICE_CODE
  is '设备编码';
comment on column TY_LOG_COUNT.GROUP_ID
  is '组织ID';
comment on column TY_LOG_COUNT.BATCH_CODE
  is '批次号';
comment on column TY_LOG_COUNT.COUNT_TIME
  is '统计时间';
comment on column TY_LOG_COUNT.PROCESS_KEY
  is '查询MD5KEY';
comment on column TY_LOG_COUNT.PROCESS_DAY_COUNT
  is '加工设备当天加工数';
comment on column TY_LOG_COUNT.DEVICE_UPLOAD_DAY_COUNT
  is '加工设备当天上传数';
comment on column TY_LOG_COUNT.ALLCOUNT
  is '所有总数';
comment on column TY_LOG_COUNT.ACTIVE_COUNT
  is '有效到达数';
comment on column TY_LOG_COUNT.NONACTIVE_COUNT
  is '无效数';
comment on column TY_LOG_COUNT.NONACTIVE_UNINSTALL_COUNT
  is '无效-卸载';
comment on column TY_LOG_COUNT.NONACTIVE_REPLACE_COUNT
  is '无效-替换';
comment on column TY_LOG_COUNT.COUNTER_UPLOAD_DAY_COUNT
  is '计数器当天上传数';
-- Create/Recreate primary, unique and foreign key constraints
alter table TY_LOG_COUNT
  add constraint PK_TY_LOG_COUNT primary key (LOG_COUNT_ID);
-- Create/Recreate indexes
create index INDEX_TY_LOG_COUNT_COUNT_TIME on TY_LOG_COUNT (COUNT_TIME);

create table TY_PRODUCT_COUNT
(
  PRODUCT_COUNT_ID          NUMBER(15) not null,
  COUNT_TIME                DATE not null,
  GROUP_ID                  NUMBER(15) not null,
  UA                        VARCHAR2(100),
  MODLE_NAME                VARCHAR2(100),
  PROCESS_DAY_COUNT         NUMBER(15) default 0,
  ALLCOUNT                  NUMBER(15) default 0,
  ACTIVE_COUNT              NUMBER(15) default 0,
  NONACTIVE_COUNT           NUMBER(15) default 0,
  NONACTIVE_UNINSTALL_COUNT NUMBER(15) default 0,
  NONACTIVE_REPLACE_COUNT   NUMBER(15) default 0,
  PRODUCT_ID                NUMBER(15) default 0 not null,
  PROCESS_KEY               VARCHAR2(100)
);
-- Add comments to the table
comment on table TY_PRODUCT_COUNT
  is '产品统计表';
-- Add comments to the columns
comment on column TY_PRODUCT_COUNT.PRODUCT_COUNT_ID
  is '主健';
comment on column TY_PRODUCT_COUNT.COUNT_TIME
  is '统计时间';
comment on column TY_PRODUCT_COUNT.GROUP_ID
  is '组织ID';
comment on column TY_PRODUCT_COUNT.UA
  is 'UA';
comment on column TY_PRODUCT_COUNT.MODLE_NAME
  is '机型名称';
comment on column TY_PRODUCT_COUNT.PROCESS_DAY_COUNT
  is '加工统计';
comment on column TY_PRODUCT_COUNT.ALLCOUNT
  is '计数器总数';
comment on column TY_PRODUCT_COUNT.ACTIVE_COUNT
  is '有效总数';
comment on column TY_PRODUCT_COUNT.NONACTIVE_COUNT
  is '无效总数';
comment on column TY_PRODUCT_COUNT.NONACTIVE_UNINSTALL_COUNT
  is '无效-卸载数';
comment on column TY_PRODUCT_COUNT.NONACTIVE_REPLACE_COUNT
  is '无效-替换数';
comment on column TY_PRODUCT_COUNT.PRODUCT_ID
  is '产品ID';
comment on column TY_PRODUCT_COUNT.PROCESS_KEY
  is 'md5(ua+count_time+group_id+PRODUCT_ID)';
-- Create/Recreate primary, unique and foreign key constraints
alter table TY_PRODUCT_COUNT
  add constraint PK_PRODUCT_COUNT_ID primary key (PRODUCT_COUNT_ID);
-- Create/Recreate indexes
create index INDEX_PRODUCTCOUNT_COUNT_TIME on TY_PRODUCT_COUNT (COUNT_TIME);