INSERT INTO TY_CHANNEL_GROUP(GROUP_ID,GROUP_NAME) VALUES(1,'天音渠道');
INSERT INTO TY_CHANNEL_GROUP(GROUP_ID,GROUP_NAME) VALUES(2,'地包渠道');
INSERT INTO TY_CHANNEL_GROUP(GROUP_ID,GROUP_NAME) VALUES(3,'其他渠道');
INSERT INTO TY_CHANNEL_GROUP(GROUP_ID,GROUP_NAME) VALUES(4,'劳务渠道');



insert into TY_DICT_INFO(DICT_ID,KEY_CODE,KEY_VALUE,REMARK,CREATE_TIME,UPDATE_TIME)values (SEQ_DICT_INFO.NEXTVAL,'system_init_date','2014-06-20 00:00:00','系统初始化时间',SYSDATE,SYSDATE);


insert into ty_role values (seq_role.nextval,'admin',-1,'',1,sysdate);
insert into ty_user values(seq_user.nextval,'admin','admin','123456','','',1,1,sysdate,sysdate);
insert into ty_user_role_ref values(seq_user_role_ref.nextval,seq_user.currval,seq_role.currval,sysdate);