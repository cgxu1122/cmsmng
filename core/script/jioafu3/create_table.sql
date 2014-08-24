drop table TY_PRODUCT_ARRIVE_STAT cascade constraints;

/*==============================================================*/
/* Table: TY_PRODUCT_ARRIVE_STAT                                */
/*==============================================================*/
create table TY_PRODUCT_ARRIVE_STAT  (
   ID                   NUMBER(15)                      not null,
   PRODUCT_ID           NUMBER(15)                      not null,
   CHANNEL_ID           NUMBER(15)                      not null,
   GROUP_ID             NUMBER(15)                      not null,
   UA                   VARCHAR2(100)                   not null,
   ARRIVE_DATE          DATE                            not null,
   TOTAL_NUM            NUMBER(15)                     default 0,
   VALID_NUM            NUMBER(15)                     default 0,
   INVALID_NUM          NUMBER(15)                     default 0,
   INVALID_REPLACE_NUM  NUMBER(15)                     default 0,
   INVALID_UNINSTALL_NUM NUMBER(15)                     default 0,
   INVALID_UN_RE_NUM    NUMBER(15)                     default 0,
   CREATE_TIME          DATE                           default SYSDATE,
   VERSION              NUMBER(15)                     default 0,
   MD5_KEY              VARCHAR2(50)                    not null,
);

comment on table TY_PRODUCT_ARRIVE_STAT is
'计数器产品到达统计表';

comment on column TY_PRODUCT_ARRIVE_STAT.ID is
'主键ID';

comment on column TY_PRODUCT_ARRIVE_STAT.PRODUCT_ID is
'产品ID';

comment on column TY_PRODUCT_ARRIVE_STAT.CHANNEL_ID is
'渠道ID';

comment on column TY_PRODUCT_ARRIVE_STAT.GROUP_ID is
'渠道组ID';

comment on column TY_PRODUCT_ARRIVE_STAT.UA is
'手机UA';

comment on column TY_PRODUCT_ARRIVE_STAT.ARRIVE_DATE is
'加工日期';

comment on column TY_PRODUCT_ARRIVE_STAT.PRS_ACTIVE_TOTAL_NUM is
'当天到达总数';

comment on column TY_PRODUCT_ARRIVE_STAT.PRS_ACTIVE_VALID_NUM is
'当天有效到达总数';

comment on column TY_PRODUCT_ARRIVE_STAT.PRS_ACTIVE_INVALID_NUM is
'当天无效到达总数';

comment on column TY_PRODUCT_ARRIVE_STAT.PRS_INVALID_REPLACE_NUM is
'当天替换总数';

comment on column TY_PRODUCT_ARRIVE_STAT.PRS_INVALID_UNINSTALL_NUM is
'当天卸载总数';

comment on column TY_PRODUCT_ARRIVE_STAT.PRS_INVALID_UN_RE_NUM is
'当天卸载替换总数';

comment on column TY_PRODUCT_ARRIVE_STAT.MD5_KEY is
'MD5KEY';

comment on column TY_PRODUCT_ARRIVE_STAT.CREATE_TIME is
'创建时间';

comment on column TY_PRODUCT_ARRIVE_STAT.VERSION is
'VERSION';


drop index IDX_CASTAT_UA_CID_ARRDATE;

drop table TY_LOG_ARRIVE_STAT cascade constraints;

/*==============================================================*/
/* Table: TY_LOG_ARRIVE_STAT                                    */
/*==============================================================*/
create table TY_LOG_ARRIVE_STAT  (
   ID                   NUMBER(15)                      not null,
   UA                   VARCHAR2(100)                   not null,
   GROUP_ID             NUMBER(15)                      not null,
   CHANNEL_ID           NUMBER(15)                      not null,
   ARRIVE_DATE          DATE                            not null,
   LAOWU_ID             NUMBER(15),
   TOTAL_NUM            NUMBER(15)                     default 0,
   VALID_NUM            NUMBER(15)                     default 0,
   INVALID_NUM          NUMBER(15)                     default 0,
   INVALID_REPLACE_NUM  NUMBER(15)                     default 0,
   INVALID_UNINSTALL_NUM NUMBER(15)                     default 0,
   INVALID_UN_RE_NUM    NUMBER(15)                     default 0,
   CREATE_TIME          DATE                           default SYSDATE,
   VERSION              NUMBER(15)                     default 0,
   MD5_KEY              VARCHAR2(50)                    not null,
   constraint PK_TY_LOG_ARRIVE_STAT primary key (ID)
);

comment on table TY_LOG_ARRIVE_STAT is
'计数器到达统计表';

comment on column TY_LOG_ARRIVE_STAT.ID is
'主键ID';

comment on column TY_LOG_ARRIVE_STAT.UA is
'手机UA';

comment on column TY_LOG_ARRIVE_STAT.GROUP_ID is
'渠道组ID';

comment on column TY_LOG_ARRIVE_STAT.CHANNEL_ID is
'渠道ID';

comment on column TY_LOG_ARRIVE_STAT.ARRIVE_DATE is
'加工日期';

comment on column TY_LOG_ARRIVE_STAT.LAOWU_ID is
'劳务ID';

comment on column TY_LOG_ARRIVE_STAT.TOTAL_NUM is
'当天到达总数';

comment on column TY_LOG_ARRIVE_STAT.VALID_NUM is
'当天有效到达总数';

comment on column TY_LOG_ARRIVE_STAT.INVALID_NUM is
'当天无效到达总数';

comment on column TY_LOG_ARRIVE_STAT.INVALID_REPLACE_NUM is
'当天替换总数';

comment on column TY_LOG_ARRIVE_STAT.INVALID_UNINSTALL_NUM is
'当天卸载总数';

comment on column TY_LOG_ARRIVE_STAT.INVALID_UN_RE_NUM is
'当天卸载替换总数';

comment on column TY_LOG_ARRIVE_STAT.CREATE_TIME is
'创建时间';

comment on column TY_LOG_ARRIVE_STAT.VERSION is
'VERSION';

comment on column TY_LOG_ARRIVE_STAT.MD5_KEY is
'MD5KEY';

/*==============================================================*/
/* Index: IDX_CASTAT_UA_CID_ARRDATE                             */
/*==============================================================*/
create unique index IDX_CASTAT_UA_CID_ARRDATE on TY_LOG_ARRIVE_STAT (
   UA ASC,
   CHANNEL_ID ASC,
   ARRIVE_DATE ASC
);


drop table TY_PRODUCT_ARRIVE_STAT_TEMP cascade constraints;

/*==============================================================*/
/* Table: TY_PRODUCT_ARRIVE_STAT_TEMP                           */
/*==============================================================*/
create table TY_PRODUCT_ARRIVE_STAT_TEMP  (
   ID                   NUMBER(15)                      not null,
   PRODUCT_ID           NUMBER(15),
   GROUP_ID             NUMBER(15)                      not null,
   UA                   VARCHAR2(100)                   not null,
   ARRIVE_DATE          DATE                            not null,
   PRS_ACTIVE_TOTAL_NUM NUMBER(15)                     default 0,
   PRS_ACTIVE_VALID_NUM NUMBER(15)                     default 0,
   PRS_ACTIVE_INVALID_NUM NUMBER(15)                     default 0,
   PRS_INVALID_REPLACE_NUM NUMBER(15)                     default 0,
   PRS_INVALID_UNINSTALL_NUM NUMBER(15)                     default 0,
   PRS_INVALID_UN_RE_NUM NUMBER(15)                     default 0,
   MD5_KEY              VARCHAR2(50)                    not null,
   CREATE_TIME          DATE                           default SYSDATE,
   VERSION              NUMBER(15)                     default 0
);

comment on table TY_PRODUCT_ARRIVE_STAT_TEMP is
'计数器产品到达统计临时表';

comment on column TY_PRODUCT_ARRIVE_STAT_TEMP.ID is
'主键ID';

comment on column TY_PRODUCT_ARRIVE_STAT_TEMP.PRODUCT_ID is
'产品ID';

comment on column TY_PRODUCT_ARRIVE_STAT_TEMP.GROUP_ID is
'渠道组ID';

comment on column TY_PRODUCT_ARRIVE_STAT_TEMP.UA is
'手机UA';

comment on column TY_PRODUCT_ARRIVE_STAT_TEMP.ARRIVE_DATE is
'加工日期';

comment on column TY_PRODUCT_ARRIVE_STAT_TEMP.PRS_ACTIVE_TOTAL_NUM is
'当天到达总数';

comment on column TY_PRODUCT_ARRIVE_STAT_TEMP.PRS_ACTIVE_VALID_NUM is
'当天有效到达总数';

comment on column TY_PRODUCT_ARRIVE_STAT_TEMP.PRS_ACTIVE_INVALID_NUM is
'当天无效到达总数';

comment on column TY_PRODUCT_ARRIVE_STAT_TEMP.PRS_INVALID_REPLACE_NUM is
'当天替换总数';

comment on column TY_PRODUCT_ARRIVE_STAT_TEMP.PRS_INVALID_UNINSTALL_NUM is
'当天卸载总数';

comment on column TY_PRODUCT_ARRIVE_STAT_TEMP.PRS_INVALID_UN_RE_NUM is
'当天卸载替换总数';

comment on column TY_PRODUCT_ARRIVE_STAT_TEMP.MD5_KEY is
'MD5KEY';

comment on column TY_PRODUCT_ARRIVE_STAT_TEMP.CREATE_TIME is
'创建时间';

comment on column TY_PRODUCT_ARRIVE_STAT_TEMP.VERSION is
'VERSION';


drop index IDX_CASTAT_UA_CID_ARRDATE2;

drop table TY_LOG_ARRIVE_STAT_TEMP cascade constraints;

/*==============================================================*/
/* Table: TY_LOG_ARRIVE_STAT_TEMP                               */
/*==============================================================*/
create table TY_LOG_ARRIVE_STAT_TEMP  (
   ID                   NUMBER(15)                      not null,
   UA                   VARCHAR2(100)                   not null,
   GROUP_ID             NUMBER(15)                      not null,
   CHANNEL_ID           NUMBER(15)                      not null,
   ARRIVE_DATE          DATE                            not null,
   LAOWU_ID             NUMBER(15),
   TOTAL_NUM            NUMBER(15)                     default 0,
   VALID_NUM            NUMBER(15)                     default 0,
   INVALID_NUM          NUMBER(15)                     default 0,
   INVALID_REPLACE_NUM  NUMBER(15)                     default 0,
   INVALID_UNINSTALL_NUM NUMBER(15)                     default 0,
   INVALID_UN_RE_NUM    NUMBER(15)                     default 0,
   CREATE_TIME          DATE                           default SYSDATE,
   VERSION              NUMBER(15)                     default 0,
   MD5_KEY              VARCHAR2(50)                    not null,
   constraint PK_TY_LOG_ARRIVE_STAT_TEMP primary key (ID)
);

comment on table TY_LOG_ARRIVE_STAT_TEMP is
'计数器到达统计临时表';

comment on column TY_LOG_ARRIVE_STAT_TEMP.ID is
'主键ID';

comment on column TY_LOG_ARRIVE_STAT_TEMP.UA is
'手机UA';

comment on column TY_LOG_ARRIVE_STAT_TEMP.GROUP_ID is
'渠道组ID';

comment on column TY_LOG_ARRIVE_STAT_TEMP.CHANNEL_ID is
'渠道ID';

comment on column TY_LOG_ARRIVE_STAT_TEMP.ARRIVE_DATE is
'加工日期';

comment on column TY_LOG_ARRIVE_STAT_TEMP.LAOWU_ID is
'劳务ID';

comment on column TY_LOG_ARRIVE_STAT_TEMP.PRS_ACTIVE_TOTAL_NUM is
'当天到达总数';

comment on column TY_LOG_ARRIVE_STAT_TEMP.PRS_ACTIVE_VALID_NUM is
'当天有效到达总数';

comment on column TY_LOG_ARRIVE_STAT_TEMP.PRS_ACTIVE_INVALID_NUM is
'当天无效到达总数';

comment on column TY_LOG_ARRIVE_STAT_TEMP.PRS_INVALID_REPLACE_NUM is
'当天替换总数';

comment on column TY_LOG_ARRIVE_STAT_TEMP.PRS_INVALID_UNINSTALL_NUM is
'当天卸载总数';

comment on column TY_LOG_ARRIVE_STAT_TEMP.PRS_INVALID_UN_RE_NUM is
'当天卸载替换总数';

comment on column TY_LOG_ARRIVE_STAT_TEMP.CREATE_TIME is
'创建时间';

comment on column TY_LOG_ARRIVE_STAT_TEMP.VERSION is
'VERSION';

comment on column TY_LOG_ARRIVE_STAT_TEMP.MD5_KEY is
'MD5KEY';

/*==============================================================*/
/* Index: IDX_CASTAT_UA_CID_ARRDATE2                            */
/*==============================================================*/
create unique index IDX_CASTAT_UA_CID_ARRDATE2 on TY_LOG_ARRIVE_STAT_TEMP (
   UA ASC,
   CHANNEL_ID ASC,
   ARRIVE_DATE ASC
);


