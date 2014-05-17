create table TY_EXAMPLE
(
  id   NUMBER(15) not null,
  name VARCHAR2(100)
)
comment on column TY_EXAMPLE.id is '唯一主键';
comment on column TY_EXAMPLE.name is '名称';
alter table TY_EXAMPLE add constraint PK_TY_EXAMPLE primary key (ID);

create sequence SEQ_EXAMPLE;