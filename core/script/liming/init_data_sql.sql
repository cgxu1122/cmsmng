insert into TY_COUNTER_UPLOAD_LOG_20142 (COUNTER_ID, IMEI, UA, CHANNEL_ID, DEVICE_CODE, GROUP_ID, BATCH_CODE, PROCESS_TIME, CREATE_TIME, ACTIVE, MODEL_NAME)
values (1, 'imei1', 'ua1', '10', 'dc1', 1, 'bc1', '2014-05-19', to_date('19-05-2014', 'dd-mm-yyyy'), 1, 'mn1');
insert into TY_COUNTER_UPLOAD_LOG_20142 (COUNTER_ID, IMEI, UA, CHANNEL_ID, DEVICE_CODE, GROUP_ID, BATCH_CODE, PROCESS_TIME, CREATE_TIME, ACTIVE, MODEL_NAME)
values (2, 'imei2', 'ua1', '10', 'dc1', 1, 'bc1', '2014-05-19', to_date('20-05-2014', 'dd-mm-yyyy'), 3, 'mn1');
insert into TY_COUNTER_UPLOAD_LOG_20142 (COUNTER_ID, IMEI, UA, CHANNEL_ID, DEVICE_CODE, GROUP_ID, BATCH_CODE, PROCESS_TIME, CREATE_TIME, ACTIVE, MODEL_NAME)
values (3, 'imei3', 'ua2', '10', 'dc2', 1, 'bc2', '2014-05-19', to_date('19-05-2014', 'dd-mm-yyyy'), 2, 'mn2');
insert into TY_COUNTER_UPLOAD_LOG_20142 (COUNTER_ID, IMEI, UA, CHANNEL_ID, DEVICE_CODE, GROUP_ID, BATCH_CODE, PROCESS_TIME, CREATE_TIME, ACTIVE, MODEL_NAME)
values (4, 'imei4', 'ua2', '10', 'dc2', 1, 'bc2', '2014-05-20', to_date('19-05-2014', 'dd-mm-yyyy'), 1, 'mn2');
insert into TY_COUNTER_UPLOAD_LOG_20142 (COUNTER_ID, IMEI, UA, CHANNEL_ID, DEVICE_CODE, GROUP_ID, BATCH_CODE, PROCESS_TIME, CREATE_TIME, ACTIVE, MODEL_NAME)
values (5, 'imei5', 'ua1', '10', 'dc2', 1, 'bc1', '2014-05-19', to_date('20-05-2014', 'dd-mm-yyyy'), 2, 'mn1');
insert into TY_COUNTER_UPLOAD_LOG_20142 (COUNTER_ID, IMEI, UA, CHANNEL_ID, DEVICE_CODE, GROUP_ID, BATCH_CODE, PROCESS_TIME, CREATE_TIME, ACTIVE, MODEL_NAME)
values (6, 'imei6', 'ua2', '10', 'dc1', 1, 'bc2', '2014-05-20', to_date('19-05-2014', 'dd-mm-yyyy'), 1, 'mn1');

insert into TY_DEVICE_PROCESS_LOG_20142 (PROCESS_ID, IMEI, UA, DEVICE_CODE, GROUP_ID, BATCH_CODE, CREATE_TIME, CHANNEL_ID, PROCESS_TIME, MODEL_NAME)
values (1, 'imei1', 'ua1', 'dc1', 1, 'bc1', to_date('19-05-2014 13:15:55', 'dd-mm-yyyy hh24:mi:ss'), '10', '2014-05-19', 'mn1');
insert into TY_DEVICE_PROCESS_LOG_20142 (PROCESS_ID, IMEI, UA, DEVICE_CODE, GROUP_ID, BATCH_CODE, CREATE_TIME, CHANNEL_ID, PROCESS_TIME, MODEL_NAME)
values (6, 'imei6', 'ua1', 'dc1', 1, 'bc1', to_date('19-05-2014 13:20:31', 'dd-mm-yyyy hh24:mi:ss'), '10', '2014-05-19', 'mn1');
insert into TY_DEVICE_PROCESS_LOG_20142 (PROCESS_ID, IMEI, UA, DEVICE_CODE, GROUP_ID, BATCH_CODE, CREATE_TIME, CHANNEL_ID, PROCESS_TIME, MODEL_NAME)
values (11, 'imei11', 'ua2', 'dc1', 1, 'bc1', to_date('19-05-2014 13:20:35', 'dd-mm-yyyy hh24:mi:ss'), '10', '2014-05-19', 'mn2');
insert into TY_DEVICE_PROCESS_LOG_20142 (PROCESS_ID, IMEI, UA, DEVICE_CODE, GROUP_ID, BATCH_CODE, CREATE_TIME, CHANNEL_ID, PROCESS_TIME, MODEL_NAME)
values (16, 'imei16', 'ua2', 'dc2', 1, 'bc2', to_date('19-05-2014 13:20:38', 'dd-mm-yyyy hh24:mi:ss'), '10', '2014-05-19', 'mn2');
insert into TY_DEVICE_PROCESS_LOG_20142 (PROCESS_ID, IMEI, UA, DEVICE_CODE, GROUP_ID, BATCH_CODE, CREATE_TIME, CHANNEL_ID, PROCESS_TIME, MODEL_NAME)
values (21, 'imei21', 'ua1', 'dc1', 1, 'bc1', to_date('19-05-2014 13:20:41', 'dd-mm-yyyy hh24:mi:ss'), '10', '2014-05-18', 'mn1');
commit;