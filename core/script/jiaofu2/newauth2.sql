Insert into TY_RESOURCE (RESOURCE_ID,PARENT_ID,RES_NAME,RES_URL,FULL_PATH,LEVELS,CREATE_TIME) values (	111		,	1	,'到达报表统计','report_arrive_menu','',	1	,	SYSDATE	);
Insert into TY_RESOURCE (RESOURCE_ID,PARENT_ID,RES_NAME,RES_URL,FULL_PATH,LEVELS,CREATE_TIME) values (	11110	,	111	,'天音渠道到达查询','report_ty_arrive','',	2	,	SYSDATE	);
Insert into TY_RESOURCE (RESOURCE_ID,PARENT_ID,RES_NAME,RES_URL,FULL_PATH,LEVELS,CREATE_TIME) values (	11111	,	111	,'地包渠道到达查询','report_db_arrive','',	2	,	SYSDATE	);
Insert into TY_RESOURCE (RESOURCE_ID,PARENT_ID,RES_NAME,RES_URL,FULL_PATH,LEVELS,CREATE_TIME) values (	11112	,	111	,'其他渠道到达查询','report_qt_arrive','',	2	,	SYSDATE	);
Insert into TY_RESOURCE (RESOURCE_ID,PARENT_ID,RES_NAME,RES_URL,FULL_PATH,LEVELS,CREATE_TIME) values (	11113	,	111	,'产品到达查询','report_porduct_arrive','',	2	,	SYSDATE	);


insert into TY_ROLE_RESOURCE_REF (ID, ROLE_ID, RESOURCE_ID, CREATE_TIME) values (SEQ_ROLE_RESOURCE_REF.NEXTVAL, 1, 111		, SYSDATE);
insert into TY_ROLE_RESOURCE_REF (ID, ROLE_ID, RESOURCE_ID, CREATE_TIME) values (SEQ_ROLE_RESOURCE_REF.NEXTVAL, 1, 11110	, SYSDATE);
insert into TY_ROLE_RESOURCE_REF (ID, ROLE_ID, RESOURCE_ID, CREATE_TIME) values (SEQ_ROLE_RESOURCE_REF.NEXTVAL, 1, 11111	, SYSDATE);
insert into TY_ROLE_RESOURCE_REF (ID, ROLE_ID, RESOURCE_ID, CREATE_TIME) values (SEQ_ROLE_RESOURCE_REF.NEXTVAL, 1, 11112	, SYSDATE);
insert into TY_ROLE_RESOURCE_REF (ID, ROLE_ID, RESOURCE_ID, CREATE_TIME) values (SEQ_ROLE_RESOURCE_REF.NEXTVAL, 1, 11113	, SYSDATE);

Insert into TY_RESOURCE (RESOURCE_ID,PARENT_ID,RES_NAME,RES_URL,FULL_PATH,LEVELS,CREATE_TIME) values (	140	,	1	,'扣量功能','stat_deduction_menu','',	1	,	SYSDATE	);
Insert into TY_RESOURCE (RESOURCE_ID,PARENT_ID,RES_NAME,RES_URL,FULL_PATH,LEVELS,CREATE_TIME) values (	14010	,	140	,'扣量功能','stat_deduction','',	2	,	SYSDATE	);
Insert into TY_RESOURCE (RESOURCE_ID,PARENT_ID,RES_NAME,RES_URL,FULL_PATH,LEVELS,CREATE_TIME) values (	1401010	,	140	,'新增','stat_deduction_add','',	3	,	SYSDATE	);
Insert into TY_RESOURCE (RESOURCE_ID,PARENT_ID,RES_NAME,RES_URL,FULL_PATH,LEVELS,CREATE_TIME) values (	1401011	,	140	,'修改','stat_deduction_update','',	3	,	SYSDATE	);
Insert into TY_RESOURCE (RESOURCE_ID,PARENT_ID,RES_NAME,RES_URL,FULL_PATH,LEVELS,CREATE_TIME) values (	1401012	,	140	,'删除','stat_deduction_delete','',	3	,	SYSDATE	);

insert into TY_ROLE (ROLE_ID, ROLE_NAME, PARENT_ID, FULL_PATH, LEVELS,ROOT_ID, CREATE_TIME,UPDATE_TIME, CAN_DEL) values (12, '其他渠道查询'	, 7	, '7/12', 2,	7,	SYSDATE, SYSDATE, 0);


insert into TY_ROLE_RESOURCE_REF (ID, ROLE_ID, RESOURCE_ID, CREATE_TIME) values (SEQ_ROLE_RESOURCE_REF.NEXTVAL, 1, 140		, SYSDATE);
insert into TY_ROLE_RESOURCE_REF (ID, ROLE_ID, RESOURCE_ID, CREATE_TIME) values (SEQ_ROLE_RESOURCE_REF.NEXTVAL, 1, 14010	, SYSDATE);
insert into TY_ROLE_RESOURCE_REF (ID, ROLE_ID, RESOURCE_ID, CREATE_TIME) values (SEQ_ROLE_RESOURCE_REF.NEXTVAL, 1, 1401010	, SYSDATE);
insert into TY_ROLE_RESOURCE_REF (ID, ROLE_ID, RESOURCE_ID, CREATE_TIME) values (SEQ_ROLE_RESOURCE_REF.NEXTVAL, 1, 1401011	, SYSDATE);
insert into TY_ROLE_RESOURCE_REF (ID, ROLE_ID, RESOURCE_ID, CREATE_TIME) values (SEQ_ROLE_RESOURCE_REF.NEXTVAL, 1, 1401012	, SYSDATE);