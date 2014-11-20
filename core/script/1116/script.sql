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




insert into TY_DEVICE_SWITCH( ID,DEVICE_CODE,STATUS) values(SEQ_DEVICE_SWITCH.nextval,'42:49:1F:48:FF:48',0);
insert into TY_DEVICE_SWITCH( ID,DEVICE_CODE,STATUS) values(SEQ_DEVICE_SWITCH.nextval,'0E:0F:23:0A:BD:2E',0);
insert into TY_DEVICE_SWITCH( ID,DEVICE_CODE,STATUS) values(SEQ_DEVICE_SWITCH.nextval,'0E:18:23:33:BD:04',0);
insert into TY_DEVICE_SWITCH( ID,DEVICE_CODE,STATUS) values(SEQ_DEVICE_SWITCH.nextval,'0E:18:23:33:BD:53',0);
insert into TY_DEVICE_SWITCH( ID,DEVICE_CODE,STATUS) values(SEQ_DEVICE_SWITCH.nextval,'82:56:96:48:CF:48',0);
insert into TY_DEVICE_SWITCH( ID,DEVICE_CODE,STATUS) values(SEQ_DEVICE_SWITCH.nextval,'0E:0F:23:77:BD:14',0);
insert into TY_DEVICE_SWITCH( ID,DEVICE_CODE,STATUS) values(SEQ_DEVICE_SWITCH.nextval,'0E:13:23:7F:BD:2F',0);
insert into TY_DEVICE_SWITCH( ID,DEVICE_CODE,STATUS) values(SEQ_DEVICE_SWITCH.nextval,'0E:17:23:75:BD:20',0);
insert into TY_DEVICE_SWITCH( ID,DEVICE_CODE,STATUS) values(SEQ_DEVICE_SWITCH.nextval,'0E:0F:23:0A:BD:2D',0);
insert into TY_DEVICE_SWITCH( ID,DEVICE_CODE,STATUS) values(SEQ_DEVICE_SWITCH.nextval,'0E:0F:23:07:BD:19',0);
insert into TY_DEVICE_SWITCH( ID,DEVICE_CODE,STATUS) values(SEQ_DEVICE_SWITCH.nextval,'0E:18:23:34:BD:12',0);
insert into TY_DEVICE_SWITCH( ID,DEVICE_CODE,STATUS) values(SEQ_DEVICE_SWITCH.nextval,'0E:0F:23:19:BD:31',0);
insert into TY_DEVICE_SWITCH( ID,DEVICE_CODE,STATUS) values(SEQ_DEVICE_SWITCH.nextval,'0E:17:23:76:BD:28',0);
insert into TY_DEVICE_SWITCH( ID,DEVICE_CODE,STATUS) values(SEQ_DEVICE_SWITCH.nextval,'0E:17:23:74:BD:23',0);
insert into TY_DEVICE_SWITCH( ID,DEVICE_CODE,STATUS) values(SEQ_DEVICE_SWITCH.nextval,'0E:0F:23:16:BD:1E',0);
insert into TY_DEVICE_SWITCH( ID,DEVICE_CODE,STATUS) values(SEQ_DEVICE_SWITCH.nextval,'0E:0F:23:0B:BD:1D',0);
insert into TY_DEVICE_SWITCH( ID,DEVICE_CODE,STATUS) values(SEQ_DEVICE_SWITCH.nextval,'0E:0F:23:76:BD:22',0);
insert into TY_DEVICE_SWITCH( ID,DEVICE_CODE,STATUS) values(SEQ_DEVICE_SWITCH.nextval,'0E:18:23:32:BD:23',0);


