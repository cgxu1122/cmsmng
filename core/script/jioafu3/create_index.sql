
drop index UNIQ_PIS_MD5KEY;
drop index IDX_PIS_PID;
drop index IDX_PIS_UA;
drop index IDX_PIS_STATDATE;
drop index IDX_PIS_CHANNELID;

drop index UNIQ_PAS_MD5KEY;
drop index IDX_PAS_PID;
drop index IDX_PAS_UA;
drop index IDX_PAS_STATDATE;
drop index IDX_PAS_CHANNELID;

drop index UNIQ_LAS_MD5KEY;
drop index IDX_LAS_UA;
drop index IDX_LAS_STATDATE;
drop index IDX_LAS_CHANNELID;

drop index IDX_LAST_UA;
drop index IDX_LAST_STATDATE;
drop index IDX_LAST_CHANNELID;

drop index IDX_PAST_PID;
drop index IDX_PAST_UA;
drop index IDX_PAST_STATDATE;
drop index IDX_PAST_CHANNELID;

drop index IDX_SD_CHANNELID;


create unique index UNIQ_PIS_MD5KEY on TY_PRODUCT_INSTALL_STAT (
   MD5_KEY ASC
);

create index IDX_PIS_PID on TY_PRODUCT_INSTALL_STAT (
   PRODUCT_ID ASC
);

create index IDX_PIS_UA on TY_PRODUCT_INSTALL_STAT (
   UA ASC
);

create index IDX_PIS_STATDATE on TY_PRODUCT_INSTALL_STAT (
   STAT_DATE ASC
);

create index IDX_PIS_CHANNELID on TY_PRODUCT_INSTALL_STAT (
   CHANNEL_ID ASC
);




create unique index UNIQ_PAS_MD5KEY on TY_PRODUCT_ARRIVE_STAT (
   MD5_KEY ASC
);

create index IDX_PAS_PID on TY_PRODUCT_ARRIVE_STAT (
   PRODUCT_ID ASC
);

create index IDX_PAS_UA on TY_PRODUCT_ARRIVE_STAT (
   UA ASC
);

create index IDX_PAS_STATDATE on TY_PRODUCT_ARRIVE_STAT (
   STAT_DATE ASC
);

create index IDX_PAS_CHANNELID on TY_PRODUCT_ARRIVE_STAT (
   CHANNEL_ID ASC
);




create unique index UNIQ_LAS_MD5KEY on TY_LOG_ARRIVE_STAT (
   MD5_KEY ASC
);

create index IDX_LAS_UA on TY_LOG_ARRIVE_STAT (
   UA ASC
);

create index IDX_LAS_STATDATE on TY_LOG_ARRIVE_STAT (
   STAT_DATE ASC
);

create index IDX_LAS_CHANNELID on TY_LOG_ARRIVE_STAT (
   CHANNEL_ID ASC
);



create index IDX_LAST_UA on TY_LOG_ARRIVE_STAT_TEMP (
   UA ASC
);

create index IDX_LAST_STATDATE on TY_LOG_ARRIVE_STAT_TEMP (
   STAT_DATE ASC
);

create index IDX_LAST_CHANNELID on TY_LOG_ARRIVE_STAT_TEMP (
   CHANNEL_ID ASC
);




create index IDX_PAST_PID on TY_PRODUCT_ARRIVE_STAT_TEMP (
   PRODUCT_ID ASC
);

create index IDX_PAST_UA on TY_PRODUCT_ARRIVE_STAT_TEMP (
   UA ASC
);

create index IDX_PAST_STATDATE on TY_PRODUCT_ARRIVE_STAT_TEMP (
   STAT_DATE ASC
);

create index IDX_PAST_CHANNELID on TY_PRODUCT_ARRIVE_STAT_TEMP (
   CHANNEL_ID ASC
);


create index IDX_SD_CHANNELID on TY_STAT_DEDUCTION (
   CHANNEL_ID ASC
);