INSERT INTO TY_CHANNEL_GROUP(GROUP_ID,GROUP_NAME) VALUES(1,'天音渠道');
INSERT INTO TY_CHANNEL_GROUP(GROUP_ID,GROUP_NAME) VALUES(2,'地包渠道');
INSERT INTO TY_CHANNEL_GROUP(GROUP_ID,GROUP_NAME) VALUES(3,'其他渠道');
INSERT INTO TY_CHANNEL_GROUP(GROUP_ID,GROUP_NAME) VALUES(4,'劳务渠道');

insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'北京');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'成都');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'广州');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'哈尔滨');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'杭州');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'合肥');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'河北');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'湖南');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'昆明');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'南昌');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'南宁');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'厦门');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'山东');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'上海');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'深圳');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'沈阳');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'太原');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'天津');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'武汉');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'西安');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'长春');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,1,'郑州');

insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,2,'南宁');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,2,'东莞中域');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,2,'南宁海天世纪');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,2,'南宁鑫辉');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,2,'上海佳建');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,2,'玉林鑫辉');

insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,3,'豌豆荚');
insert into ty_channel_info(channel_id,group_id,channel_name)values(SEQ_CHANNEL_INFO.NEXTVAL,3,'豌豆荚公司');

insert into TY_DICT_INFO(DICT_ID,KEY_CODE,KEY_VALUE,REMARK,CREATE_TIME,UPDATE_TIME)values (SEQ_DICT_INFO.NEXTVAL,'system_init_date','2014-06-20 00:00:00','系统初始化时间',SYSDATE,SYSDATE);


insert into ty_role values (seq_role.nextval,'admin',-1,'',1,sysdate);
insert into ty_user values(seq_user.nextval,'admin','admin','123456','','',1,1,sysdate,sysdate);
insert into ty_user_role_ref values(seq_user_role_ref.nextval,seq_user.currval,seq_role.currval,sysdate);