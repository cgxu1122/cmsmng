drop index "Index_5";

drop index "Index_24";

drop index "Index_17";

drop index "Index_19";

drop index IDX_DUTIME;

drop index IDX_PTIME;

drop index IDX_UA_CID_DCODE;

drop index IDX_UA_GID_BCODE;

drop index UNIQ_IMEI;

drop index "Index_7";

drop index "Index_16";

drop index "Index_18";

drop index "Index_20";

drop index "Index_21";

drop index "Index_3";

drop index "Index_22";

drop index "Index_13";

drop index "Index_26";

drop index "Index_14";

drop index "Index_15";

drop index "Index_4";

drop index "Index_6";

drop index "Index_9";

drop index "Index_10";

drop index "Index_11";

drop index "Index_12";

drop index "Index_23";

drop index "Index_8";

drop index "Index_2";

drop index "Index_1";

drop index "Index_25";

drop index "Index_27";

/*==============================================================*/
/* Index: "Index_27"                                            */
/*==============================================================*/
create index "Index_27" on TY_USER (
   ROLE_ID ASC
)

/*==============================================================*/
/* Index: "Index_25"                                            */
/*==============================================================*/
create index "Index_25" on TY_USER_PRODUCT_REF (
   USER_ID ASC
) ;


/*==============================================================*/
/* Index: "Index_5"                                             */
/*==============================================================*/
create unique index "Index_5" on TY_BATCH_PRODUCT_REF (
   PRODUCT_ID ASC,
   BATCH_CODE ASC,
   BATCH_ID ASC
) ;

/*==============================================================*/
/* Index: "Index_24"                                            */
/*==============================================================*/
create unique index "Index_24" on TY_CHANNEL_INFO (
   USER_ID ASC
) ;

/*==============================================================*/
/* Index: "Index_17"                                            */
/*==============================================================*/
create unique index "Index_17" on TY_COUNTER_TEMP_LOG (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: "Index_19"                                            */
/*==============================================================*/
create index "Index_19" on TY_COUNTER_TEMP_LOG (
   CREATE_TIME ASC
) ;

/*==============================================================*/
/* Index: UNIQ_IMEI                                             */
/*==============================================================*/
create unique index UNIQ_IMEI on TY_DATA_LOG (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE                                      */
/*==============================================================*/
create index IDX_UA_CID_DCODE on TY_DATA_LOG (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME                                             */
/*==============================================================*/
create index IDX_PTIME on TY_DATA_LOG (
   PROCESS_TIME ASC
) ;


/*==============================================================*/
/* Index: IDX_DUTIME                                            */
/*==============================================================*/
create index IDX_DUTIME on TY_DATA_LOG (
   DEVICE_UPLOAD_TIME ASC
) ;


/*==============================================================*/
/* Index: "Index_7"                                             */
/*==============================================================*/
create unique index "Index_7" on TY_DICT_INFO (
   KEY_CODE ASC
) ;

/*==============================================================*/
/* Index: "Index_3"                                             */
/*==============================================================*/
create unique index "Index_3" on TY_LOG_STAT (
   MD5_KEY ASC
) ;

/*==============================================================*/
/* Index: "Index_16"                                            */
/*==============================================================*/
create index "Index_16" on TY_LOG_STAT (
   UA ASC
) ;

/*==============================================================*/
/* Index: "Index_18"                                            */
/*==============================================================*/
create index "Index_18" on TY_LOG_STAT (
   CHANNEL_ID ASC
) ;

/*==============================================================*/
/* Index: "Index_20"                                            */
/*==============================================================*/
create index "Index_20" on TY_LOG_STAT (
   PROCESS_DATE ASC
) ;

/*==============================================================*/
/* Index: "Index_21"                                            */
/*==============================================================*/
create index "Index_21" on TY_LOG_STAT (
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: "Index_22"                                            */
/*==============================================================*/
create index "Index_22" on TY_MODEL_CHANNEL_REF (
   MODEL_ID ASC,
   CHANNEL_ID ASC
) ;

/*==============================================================*/
/* Index: "Index_13"                                            */
/*==============================================================*/
create index "Index_13" on TY_PACKAGE_APK_REF (
   PACKAGE_ID ASC,
   APK_ID ASC
) ;

/*==============================================================*/
/* Index: "Index_26"                                            */
/*==============================================================*/
create index "Index_26" on TY_PRODUCT_INFO (
   PARTNER_ID ASC
) ;

/*==============================================================*/
/* Index: "Index_4"                                             */
/*==============================================================*/
create unique index "Index_4" on TY_PRODUCT_STAT (
   MD5_KEY ASC
) ;

/*==============================================================*/
/* Index: "Index_6"                                             */
/*==============================================================*/
create index "Index_6" on TY_PRODUCT_STAT (
   PRODUCT_ID ASC
) ;

/*==============================================================*/
/* Index: "Index_14"                                            */
/*==============================================================*/
create index "Index_14" on TY_PRODUCT_STAT (
   UA ASC
) ;

/*==============================================================*/
/* Index: "Index_15"                                            */
/*==============================================================*/
create index "Index_15" on TY_PRODUCT_STAT (
   PROCESS_DATE ASC
) ;

/*==============================================================*/
/* Index: "Index_9"                                             */
/*==============================================================*/
create index "Index_9" on TY_PUBLISH_TASK (
   UPDATE_TIME ASC
) ;

/*==============================================================*/
/* Index: "Index_10"                                            */
/*==============================================================*/
create index "Index_10" on TY_PUB_CHL_MOD_REF (
   UPDATE_TIME ASC
) ;

/*==============================================================*/
/* Index: "Index_11"                                            */
/*==============================================================*/
create index "Index_11" on TY_PUB_CHL_MOD_REF (
   PUBLISH_ID ASC
) ;

/*==============================================================*/
/* Index: "Index_12"                                            */
/*==============================================================*/
create index "Index_12" on TY_PUB_CHL_MOD_REF (
   GROUP_ID ASC,
   CHANNEL_ID ASC
) ;

/*==============================================================*/
/* Index: "Index_23"                                            */
/*==============================================================*/
create index "Index_23" on TY_ROLE_RESOURCE_REF (
   ROLE_ID ASC,
   RESOURCE_ID ASC
) ;

/*==============================================================*/
/* Index: "Index_8"                                             */
/*==============================================================*/
create index "Index_8" on TY_SETTLE_INFO (
   CREATE_BY ASC
) ;

/*==============================================================*/
/* Index: "Index_2"                                             */
/*==============================================================*/
create unique index "Index_2" on TY_USER (
   LOGIN_NAME ASC
) ;


/*==============================================================*/
/* Index: "Index_1"                                             */
/*==============================================================*/
create index "Index_1" on TY_USER_ROLE_REF (
   USER_ID ASC,
   ROLE_ID ASC
) ;



drop index IDX_DUTIME_20142;

drop index IDX_PTIME_20142;

drop index IDX_UA_CID_DCODE_20142;

drop index IDX_UA_GID_BCODE_20142;

drop index UNIQ_IMEI_20142;
/*==============================================================*/
/* Index: UNIQ_IMEI_20142                                       */
/*==============================================================*/
create unique index UNIQ_IMEI_20142 on TY_DATA_LOG_20142 (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE_20142                                */
/*==============================================================*/
create index IDX_UA_CID_DCODE_20142 on TY_DATA_LOG_20142 (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_GID_BCODE_20142                                */
/*==============================================================*/
create index IDX_UA_GID_BCODE_20142 on TY_DATA_LOG_20142 (
   UA ASC,
   GROUP_ID ASC,
   BATCH_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME_20142                                       */
/*==============================================================*/
create index IDX_PTIME_20142 on TY_DATA_LOG_20142 (
   PROCESS_TIME ASC
) ;

/*==============================================================*/
/* Index: IDX_DUTIME_20142                                      */
/*==============================================================*/
create index IDX_DUTIME_20142 on TY_DATA_LOG_20142 (
   DEVICE_UPLOAD_TIME ASC
) ;

drop index IDX_DUTIME_20143;

drop index IDX_PTIME_20143;

drop index IDX_UA_CID_DCODE_20143;

drop index IDX_UA_GID_BCODE_20143;

drop index UNIQ_IMEI_20143;


/*==============================================================*/
/* Index: UNIQ_IMEI_20143                                       */
/*==============================================================*/
create unique index UNIQ_IMEI_20143 on TY_DATA_LOG_20143 (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE_20143                                */
/*==============================================================*/
create index IDX_UA_CID_DCODE_20143 on TY_DATA_LOG_20143 (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_GID_BCODE_20143                                */
/*==============================================================*/
create index IDX_UA_GID_BCODE_20143 on TY_DATA_LOG_20143 (
   UA ASC,
   GROUP_ID ASC,
   BATCH_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME_20143                                       */
/*==============================================================*/
create index IDX_PTIME_20143 on TY_DATA_LOG_20143 (
   PROCESS_TIME ASC
) ;

/*==============================================================*/
/* Index: IDX_DUTIME_20143                                      */
/*==============================================================*/
create index IDX_DUTIME_20143 on TY_DATA_LOG_20143 (
   DEVICE_UPLOAD_TIME ASC
) ;

drop index IDX_DUTIME_20144;

drop index IDX_PTIME_20144;

drop index IDX_UA_CID_DCODE_20144;

drop index IDX_UA_GID_BCODE_20144;

drop index UNIQ_IMEI_20144;


/*==============================================================*/
/* Index: UNIQ_IMEI_20144                                       */
/*==============================================================*/
create unique index UNIQ_IMEI_20144 on TY_DATA_LOG_20144 (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE_20144                                */
/*==============================================================*/
create index IDX_UA_CID_DCODE_20144 on TY_DATA_LOG_20144 (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_GID_BCODE_20144                                */
/*==============================================================*/
create index IDX_UA_GID_BCODE_20144 on TY_DATA_LOG_20144 (
   UA ASC,
   GROUP_ID ASC,
   BATCH_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME_20144                                       */
/*==============================================================*/
create index IDX_PTIME_20144 on TY_DATA_LOG_20144 (
   PROCESS_TIME ASC
) ;

/*==============================================================*/
/* Index: IDX_DUTIME_20144                                      */
/*==============================================================*/
create index IDX_DUTIME_20144 on TY_DATA_LOG_20144 (
   DEVICE_UPLOAD_TIME ASC
) ;



drop index IDX_DUTIME_20151;

drop index IDX_PTIME_20151;

drop index IDX_UA_CID_DCODE_20151;

drop index IDX_UA_GID_BCODE_20151;

drop index UNIQ_IMEI_20151;


/*==============================================================*/
/* Index: UNIQ_IMEI_20151                                       */
/*==============================================================*/
create unique index UNIQ_IMEI_20151 on TY_DATA_LOG_20151 (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE_20151                                */
/*==============================================================*/
create index IDX_UA_CID_DCODE_20151 on TY_DATA_LOG_20151 (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_GID_BCODE_20151                                */
/*==============================================================*/
create index IDX_UA_GID_BCODE_20151 on TY_DATA_LOG_20151 (
   UA ASC,
   GROUP_ID ASC,
   BATCH_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME_20151                                       */
/*==============================================================*/
create index IDX_PTIME_20151 on TY_DATA_LOG_20151 (
   PROCESS_TIME ASC
) ;

/*==============================================================*/
/* Index: IDX_DUTIME_20151                                      */
/*==============================================================*/
create index IDX_DUTIME_20151 on TY_DATA_LOG_20151 (
   DEVICE_UPLOAD_TIME ASC
) ;

drop index IDX_DUTIME_20152;

drop index IDX_PTIME_20152;

drop index IDX_UA_CID_DCODE_20152;

drop index IDX_UA_GID_BCODE_20152;

drop index UNIQ_IMEI_20152;



/*==============================================================*/
/* Index: UNIQ_IMEI_20152                                       */
/*==============================================================*/
create unique index UNIQ_IMEI_20152 on TY_DATA_LOG_20152 (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE_20152                                */
/*==============================================================*/
create index IDX_UA_CID_DCODE_20152 on TY_DATA_LOG_20152 (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_GID_BCODE_20152                                */
/*==============================================================*/
create index IDX_UA_GID_BCODE_20152 on TY_DATA_LOG_20152 (
   UA ASC,
   GROUP_ID ASC,
   BATCH_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME_20152                                       */
/*==============================================================*/
create index IDX_PTIME_20152 on TY_DATA_LOG_20152 (
   PROCESS_TIME ASC
) ;

/*==============================================================*/
/* Index: IDX_DUTIME_20152                                      */
/*==============================================================*/
create index IDX_DUTIME_20152 on TY_DATA_LOG_20152 (
   DEVICE_UPLOAD_TIME ASC
) ;


drop index IDX_DUTIME_20153;

drop index IDX_PTIME_20153;

drop index IDX_UA_CID_DCODE_20153;

drop index IDX_UA_GID_BCODE_20153;

drop index UNIQ_IMEI_20153;



/*==============================================================*/
/* Index: UNIQ_IMEI_20153                                       */
/*==============================================================*/
create unique index UNIQ_IMEI_20153 on TY_DATA_LOG_20153 (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE_20153                                */
/*==============================================================*/
create index IDX_UA_CID_DCODE_20153 on TY_DATA_LOG_20153 (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_GID_BCODE_20153                                */
/*==============================================================*/
create index IDX_UA_GID_BCODE_20153 on TY_DATA_LOG_20153 (
   UA ASC,
   GROUP_ID ASC,
   BATCH_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME_20153                                       */
/*==============================================================*/
create index IDX_PTIME_20153 on TY_DATA_LOG_20153 (
   PROCESS_TIME ASC
) ;

/*==============================================================*/
/* Index: IDX_DUTIME_20153                                      */
/*==============================================================*/
create index IDX_DUTIME_20153 on TY_DATA_LOG_20153 (
   DEVICE_UPLOAD_TIME ASC
) ;


drop index IDX_DUTIME_20154;

drop index IDX_PTIME_20154;

drop index IDX_UA_CID_DCODE_20154;

drop index IDX_UA_GID_BCODE_20154;

drop index UNIQ_IMEI_20154;

/*==============================================================*/
/* Index: UNIQ_IMEI_20154                                       */
/*==============================================================*/
create unique index UNIQ_IMEI_20154 on TY_DATA_LOG_20154 (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE_20154                                */
/*==============================================================*/
create index IDX_UA_CID_DCODE_20154 on TY_DATA_LOG_20154 (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_GID_BCODE_20154                                */
/*==============================================================*/
create index IDX_UA_GID_BCODE_20154 on TY_DATA_LOG_20154 (
   UA ASC,
   GROUP_ID ASC,
   BATCH_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME_20154                                       */
/*==============================================================*/
create index IDX_PTIME_20154 on TY_DATA_LOG_20154 (
   PROCESS_TIME ASC
) ;

/*==============================================================*/
/* Index: IDX_DUTIME_20154                                      */
/*==============================================================*/
create index IDX_DUTIME_20154 on TY_DATA_LOG_20154 (
   DEVICE_UPLOAD_TIME ASC
) ;


drop index IDX_DUTIME_20161;

drop index IDX_PTIME_20161;

drop index IDX_UA_CID_DCODE_20161;

drop index IDX_UA_GID_BCODE_20161;

drop index UNIQ_IMEI_20161;


/*==============================================================*/
/* Index: UNIQ_IMEI_20161                                       */
/*==============================================================*/
create unique index UNIQ_IMEI_20161 on TY_DATA_LOG_20161 (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE_20161                                */
/*==============================================================*/
create index IDX_UA_CID_DCODE_20161 on TY_DATA_LOG_20161 (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_GID_BCODE_20161                                */
/*==============================================================*/
create index IDX_UA_GID_BCODE_20161 on TY_DATA_LOG_20161 (
   UA ASC,
   GROUP_ID ASC,
   BATCH_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME_20161                                       */
/*==============================================================*/
create index IDX_PTIME_20161 on TY_DATA_LOG_20161 (
   PROCESS_TIME ASC
) ;

/*==============================================================*/
/* Index: IDX_DUTIME_20161                                      */
/*==============================================================*/
create index IDX_DUTIME_20161 on TY_DATA_LOG_20161 (
   DEVICE_UPLOAD_TIME ASC
) ;


drop index IDX_DUTIME_20162;

drop index IDX_PTIME_20162;

drop index IDX_UA_CID_DCODE_20162;

drop index IDX_UA_GID_BCODE_20162;

drop index UNIQ_IMEI_20162;


/*==============================================================*/
/* Index: UNIQ_IMEI_20162                                       */
/*==============================================================*/
create unique index UNIQ_IMEI_20162 on TY_DATA_LOG_20162 (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE_20162                                */
/*==============================================================*/
create index IDX_UA_CID_DCODE_20162 on TY_DATA_LOG_20162 (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_GID_BCODE_20162                                */
/*==============================================================*/
create index IDX_UA_GID_BCODE_20162 on TY_DATA_LOG_20162 (
   UA ASC,
   GROUP_ID ASC,
   BATCH_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME_20162                                       */
/*==============================================================*/
create index IDX_PTIME_20162 on TY_DATA_LOG_20162 (
   PROCESS_TIME ASC
) ;

/*==============================================================*/
/* Index: IDX_DUTIME_20162                                      */
/*==============================================================*/
create index IDX_DUTIME_20162 on TY_DATA_LOG_20162 (
   DEVICE_UPLOAD_TIME ASC
) ;


drop index IDX_DUTIME_20163;

drop index IDX_PTIME_20163;

drop index IDX_UA_CID_DCODE_20163;

drop index IDX_UA_GID_BCODE_20163;

drop index UNIQ_IMEI_20163;



/*==============================================================*/
/* Index: UNIQ_IMEI_20163                                       */
/*==============================================================*/
create unique index UNIQ_IMEI_20163 on TY_DATA_LOG_20163 (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE_20163                                */
/*==============================================================*/
create index IDX_UA_CID_DCODE_20163 on TY_DATA_LOG_20163 (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_GID_BCODE_20163                                */
/*==============================================================*/
create index IDX_UA_GID_BCODE_20163 on TY_DATA_LOG_20163 (
   UA ASC,
   GROUP_ID ASC,
   BATCH_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME_20163                                       */
/*==============================================================*/
create index IDX_PTIME_20163 on TY_DATA_LOG_20163 (
   PROCESS_TIME ASC
) ;

/*==============================================================*/
/* Index: IDX_DUTIME_20163                                      */
/*==============================================================*/
create index IDX_DUTIME_20163 on TY_DATA_LOG_20163 (
   DEVICE_UPLOAD_TIME ASC
) ;


drop index IDX_DUTIME_20164;

drop index IDX_PTIME_20164;

drop index IDX_UA_CID_DCODE_20164;

drop index IDX_UA_GID_BCODE_20164;

drop index UNIQ_IMEI_20164;



/*==============================================================*/
/* Index: UNIQ_IMEI_20164                                       */
/*==============================================================*/
create unique index UNIQ_IMEI_20164 on TY_DATA_LOG_20164 (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE_20164                                */
/*==============================================================*/
create index IDX_UA_CID_DCODE_20164 on TY_DATA_LOG_20164 (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_GID_BCODE_20164                                */
/*==============================================================*/
create index IDX_UA_GID_BCODE_20164 on TY_DATA_LOG_20164 (
   UA ASC,
   GROUP_ID ASC,
   BATCH_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME_20164                                       */
/*==============================================================*/
create index IDX_PTIME_20164 on TY_DATA_LOG_20164 (
   PROCESS_TIME ASC
) ;

/*==============================================================*/
/* Index: IDX_DUTIME_20164                                      */
/*==============================================================*/
create index IDX_DUTIME_20164 on TY_DATA_LOG_20164 (
   DEVICE_UPLOAD_TIME ASC
) ;


drop index IDX_DUTIME_20171;

drop index IDX_PTIME_20171;

drop index IDX_UA_CID_DCODE_20171;

drop index IDX_UA_GID_BCODE_20171;

drop index UNIQ_IMEI_20171;



/*==============================================================*/
/* Index: UNIQ_IMEI_20171                                       */
/*==============================================================*/
create unique index UNIQ_IMEI_20171 on TY_DATA_LOG_20171 (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE_20171                                */
/*==============================================================*/
create index IDX_UA_CID_DCODE_20171 on TY_DATA_LOG_20171 (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_GID_BCODE_20171                                */
/*==============================================================*/
create index IDX_UA_GID_BCODE_20171 on TY_DATA_LOG_20171 (
   UA ASC,
   GROUP_ID ASC,
   BATCH_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME_20171                                       */
/*==============================================================*/
create index IDX_PTIME_20171 on TY_DATA_LOG_20171 (
   PROCESS_TIME ASC
) ;

/*==============================================================*/
/* Index: IDX_DUTIME_20171                                      */
/*==============================================================*/
create index IDX_DUTIME_20171 on TY_DATA_LOG_20171 (
   DEVICE_UPLOAD_TIME ASC
) ;


drop index IDX_DUTIME_20172;

drop index IDX_PTIME_20172;

drop index IDX_UA_CID_DCODE_20172;

drop index IDX_UA_GID_BCODE_20172;

drop index UNIQ_IMEI_20172;



/*==============================================================*/
/* Index: UNIQ_IMEI_20172                                       */
/*==============================================================*/
create unique index UNIQ_IMEI_20172 on TY_DATA_LOG_20172 (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE_20172                                */
/*==============================================================*/
create index IDX_UA_CID_DCODE_20172 on TY_DATA_LOG_20172 (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_GID_BCODE_20172                                */
/*==============================================================*/
create index IDX_UA_GID_BCODE_20172 on TY_DATA_LOG_20172 (
   UA ASC,
   GROUP_ID ASC,
   BATCH_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME_20172                                       */
/*==============================================================*/
create index IDX_PTIME_20172 on TY_DATA_LOG_20172 (
   PROCESS_TIME ASC
) ;

/*==============================================================*/
/* Index: IDX_DUTIME_20172                                      */
/*==============================================================*/
create index IDX_DUTIME_20172 on TY_DATA_LOG_20172 (
   DEVICE_UPLOAD_TIME ASC
) ;

drop index IDX_DUTIME_20173;

drop index IDX_PTIME_20173;

drop index IDX_UA_CID_DCODE_20173;

drop index IDX_UA_GID_BCODE_20173;

drop index UNIQ_IMEI_20173;


/*==============================================================*/
/* Index: UNIQ_IMEI_20173                                       */
/*==============================================================*/
create unique index UNIQ_IMEI_20173 on TY_DATA_LOG_20173 (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE_20173                                */
/*==============================================================*/
create index IDX_UA_CID_DCODE_20173 on TY_DATA_LOG_20173 (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_GID_BCODE_20173                                */
/*==============================================================*/
create index IDX_UA_GID_BCODE_20173 on TY_DATA_LOG_20173 (
   UA ASC,
   GROUP_ID ASC,
   BATCH_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME_20173                                       */
/*==============================================================*/
create index IDX_PTIME_20173 on TY_DATA_LOG_20173 (
   PROCESS_TIME ASC
) ;

/*==============================================================*/
/* Index: IDX_DUTIME_20173                                      */
/*==============================================================*/
create index IDX_DUTIME_20173 on TY_DATA_LOG_20173 (
   DEVICE_UPLOAD_TIME ASC
) ;


drop index IDX_DUTIME_20174;

drop index IDX_PTIME_20174;

drop index IDX_UA_CID_DCODE_20174;

drop index IDX_UA_GID_BCODE_20174;

drop index UNIQ_IMEI_20174;

/*==============================================================*/
/* Index: UNIQ_IMEI_20174                                       */
/*==============================================================*/
create unique index UNIQ_IMEI_20174 on TY_DATA_LOG_20174 (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE_20174                                */
/*==============================================================*/
create index IDX_UA_CID_DCODE_20174 on TY_DATA_LOG_20174 (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_GID_BCODE_20174                                */
/*==============================================================*/
create index IDX_UA_GID_BCODE_20174 on TY_DATA_LOG_20174 (
   UA ASC,
   GROUP_ID ASC,
   BATCH_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME_20174                                       */
/*==============================================================*/
create index IDX_PTIME_20174 on TY_DATA_LOG_20174 (
   PROCESS_TIME ASC
) ;

/*==============================================================*/
/* Index: IDX_DUTIME_20174                                      */
/*==============================================================*/
create index IDX_DUTIME_20174 on TY_DATA_LOG_20174 (
   DEVICE_UPLOAD_TIME ASC
) ;


drop index IDX_DUTIME_20181;

drop index IDX_PTIME_20181;

drop index IDX_UA_CID_DCODE_20181;

drop index IDX_UA_GID_BCODE_20181;

drop index UNIQ_IMEI_20181;

/*==============================================================*/
/* Index: UNIQ_IMEI_20181                                       */
/*==============================================================*/
create unique index UNIQ_IMEI_20181 on TY_DATA_LOG_20181 (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE_20181                                */
/*==============================================================*/
create index IDX_UA_CID_DCODE_20181 on TY_DATA_LOG_20181 (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_GID_BCODE_20181                                */
/*==============================================================*/
create index IDX_UA_GID_BCODE_20181 on TY_DATA_LOG_20181 (
   UA ASC,
   GROUP_ID ASC,
   BATCH_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME_20181                                       */
/*==============================================================*/
create index IDX_PTIME_20181 on TY_DATA_LOG_20181 (
   PROCESS_TIME ASC
) ;

/*==============================================================*/
/* Index: IDX_DUTIME_20181                                      */
/*==============================================================*/
create index IDX_DUTIME_20181 on TY_DATA_LOG_20181 (
   DEVICE_UPLOAD_TIME ASC
) ;

drop index IDX_DUTIME_20182;

drop index IDX_PTIME_20182;

drop index IDX_UA_CID_DCODE_20182;

drop index IDX_UA_GID_BCODE_20182;

drop index UNIQ_IMEI_20182;



/*==============================================================*/
/* Index: UNIQ_IMEI_20182                                       */
/*==============================================================*/
create unique index UNIQ_IMEI_20182 on TY_DATA_LOG_20182 (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE_20182                                */
/*==============================================================*/
create index IDX_UA_CID_DCODE_20182 on TY_DATA_LOG_20182 (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_GID_BCODE_20182                                */
/*==============================================================*/
create index IDX_UA_GID_BCODE_20182 on TY_DATA_LOG_20182 (
   UA ASC,
   GROUP_ID ASC,
   BATCH_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME_20182                                       */
/*==============================================================*/
create index IDX_PTIME_20182 on TY_DATA_LOG_20182 (
   PROCESS_TIME ASC
) ;

/*==============================================================*/
/* Index: IDX_DUTIME_20182                                      */
/*==============================================================*/
create index IDX_DUTIME_20182 on TY_DATA_LOG_20182 (
   DEVICE_UPLOAD_TIME ASC
) ;

drop index IDX_DUTIME_20183;

drop index IDX_PTIME_20183;

drop index IDX_UA_CID_DCODE_20183;

drop index IDX_UA_GID_BCODE_20183;

drop index UNIQ_IMEI_20183;


/*==============================================================*/
/* Index: UNIQ_IMEI_20183                                       */
/*==============================================================*/
create unique index UNIQ_IMEI_20183 on TY_DATA_LOG_20183 (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE_20183                                */
/*==============================================================*/
create index IDX_UA_CID_DCODE_20183 on TY_DATA_LOG_20183 (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_GID_BCODE_20183                                */
/*==============================================================*/
create index IDX_UA_GID_BCODE_20183 on TY_DATA_LOG_20183 (
   UA ASC,
   GROUP_ID ASC,
   BATCH_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME_20183                                       */
/*==============================================================*/
create index IDX_PTIME_20183 on TY_DATA_LOG_20183 (
   PROCESS_TIME ASC
) ;

/*==============================================================*/
/* Index: IDX_DUTIME_20183                                      */
/*==============================================================*/
create index IDX_DUTIME_20183 on TY_DATA_LOG_20183 (
   DEVICE_UPLOAD_TIME ASC
) ;

drop index IDX_DUTIME_20184;

drop index IDX_PTIME_20184;

drop index IDX_UA_CID_DCODE_20184;

drop index IDX_UA_GID_BCODE_20184;

drop index UNIQ_IMEI_20184;


/*==============================================================*/
/* Index: UNIQ_IMEI_20184                                       */
/*==============================================================*/
create unique index UNIQ_IMEI_20184 on TY_DATA_LOG_20184 (
   IMEI ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_CID_DCODE_20184                                */
/*==============================================================*/
create index IDX_UA_CID_DCODE_20184 on TY_DATA_LOG_20184 (
   UA ASC,
   CHANNEL_ID ASC,
   DEVICE_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_UA_GID_BCODE_20184                                */
/*==============================================================*/
create index IDX_UA_GID_BCODE_20184 on TY_DATA_LOG_20184 (
   UA ASC,
   GROUP_ID ASC,
   BATCH_CODE ASC
) ;

/*==============================================================*/
/* Index: IDX_PTIME_20184                                       */
/*==============================================================*/
create index IDX_PTIME_20184 on TY_DATA_LOG_20184 (
   PROCESS_TIME ASC
) ;

/*==============================================================*/
/* Index: IDX_DUTIME_20184                                      */
/*==============================================================*/
create index IDX_DUTIME_20184 on TY_DATA_LOG_20184 (
   DEVICE_UPLOAD_TIME ASC
) ;
