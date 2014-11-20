drop table TY_DEVICE_SWITCH cascade constraints;

/*==============================================================*/
/* Table: TY_DATA_LOG_20183                                     */
/*==============================================================*/
create table TY_DEVICE_SWITCH  (
   ID                   NUMBER(15)                     not null,
   DEVICE_CODE         VARCHAR2(50)                    not null,
   STATUS               NUMBER(2)         default 0   not null,
   constraint TY_DEVICE_SWITCH primary key (ID)
);




create unique index "UNIQ_DEVICE_CODE" on TY_DEVICE_SWITCH (
   DEVICE_CODE ASC
) TABLESPACE TY_COUNTER_IDX;

drop sequence SEQ_DEVICE_SWITCH;

create sequence SEQ_DEVICE_SWITCH;
