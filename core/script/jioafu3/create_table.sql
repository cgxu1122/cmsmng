drop table TY_PRODUCT_INSTALL_STAT cascade constraints;
drop table TY_PRODUCT_ARRIVE_STAT cascade constraints;
drop table TY_LOG_ARRIVE_STAT cascade constraints;
drop table TY_PRODUCT_ARRIVE_STAT_TEMP cascade constraints;
drop table TY_LOG_ARRIVE_STAT_TEMP cascade constraints;
drop table TY_STAT_DEDUCTION cascade constraints;
drop sequence SEQ_STAT_DEDUCTION;
drop sequence SEQ_LOG_ARRIVE_STAT;
drop sequence SEQ_PRODUCT_INSTALL_STAT;
drop sequence SEQ_PRODUCT_ARRIVE_STAT;

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
   VALID_NUM            NUMBER(15)                     default 0,
   INVALID_NUM          NUMBER(15)                     default 0,
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
   VALID_NUM            NUMBER(15)                     default 0,
   INVALID_NUM          NUMBER(15)                     default 0,
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



create sequence SEQ_STAT_DEDUCTION;
create sequence SEQ_LOG_ARRIVE_STAT;
create sequence SEQ_PRODUCT_INSTALL_STAT;
create sequence SEQ_PRODUCT_ARRIVE_STAT;