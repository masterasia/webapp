CREATE TABLESPACE spacename DATAFILE '/u01/app/oracle/oradata/path/name.dbf'
    SIZE 200M AUTOEXTEND ON EXTENT MANAGEMENT LOCAL SEGMENT SPACE MANAGEMENT AUTO;

CREATE USER 'name' IDENTIFIED BY 'password' DEFAULT TABLESPACE spacename;

grant connect,resource,dba to 'name';

-- 菜单
-- Create table
create table sys_menu
(
  menu_id   number not null,
  parent_id number not null,
  menu_name nvarchar2(64),
  url       nvarchar2(64),
  perms     nvarchar2(64),
  menu_type number,
  menu_icon nvarchar2(32),
  menu_path nvarchar2(64),
  order_num number
)
;
-- Add comments to the columns
comment on column sys_menu.parent_id
  is '父菜单ID，一级菜单为0';
comment on column sys_menu.menu_name
  is '菜单名称';
comment on column sys_menu.url
  is '菜单URL';
comment on column sys_menu.perms
  is '授权(多个用逗号分隔，如：user:list,user:create)';
comment on column sys_menu.menu_type
  is '类型   0：目录   1：菜单   2：按钮';
comment on column sys_menu.menu_icon
  is '菜单图标';
comment on column sys_menu.menu_path
  is '菜单路径';
comment on column sys_menu.order_num
  is '排序';
-- Create/Recreate primary, unique and foreign key constraints
alter table sys_menu
  add constraint sys_menu_menu_id primary key (MENU_ID);

-- 部门
-- Create table
create table sys_dept
(
  dept_id   number not null,
  parent_id number not null,
  dept_name nvarchar2(64),
  dept_code varchar2(64),
  del_flag number default 0 not null,
  order_num number
)
;
-- Add comments to the columns
comment on column sys_dept.parent_id
  is '上级部门ID，一级部门为0';
comment on column sys_dept.dept_name
  is '部门名称';
comment on column sys_dept.dept_code
  is '部门编码';
comment on column sys_dept.del_flag
  is '是否删除  -1：已删除  0：正常';
comment on column sys_dept.order_num
  is '排序';
-- Create/Recreate primary, unique and foreign key constraints
alter table sys_dept
  add constraint sys_dept_dept_id primary key (DEPT_ID);

-- 行政区域
-- Create table
create table sys_area
(
  area_id   varchar2(32) not null,
  parent_id varchar2(32) not null,
  area_name nvarchar2(64),
  area_code varchar2(64),
  del_flag number default 0 not null,
  order_num number
)
;
-- Add comments to the columns
comment on column sys_area.parent_id
  is '上级区域ID，一级区域为0';
comment on column sys_area.area_name
  is '区域名称';
comment on column sys_area.area_code
  is '区域编码: 和区域名称对应,可用于合成身份证编号中部分号段';
comment on column sys_area.del_flag
  is '是否删除  -1：已删除  0：正常';
comment on column sys_area.order_num
  is '排序';
-- Create/Recreate primary, unique and foreign key constraints
alter table sys_area
  add constraint sys_area_id primary key (AREA_ID);

--创建部门和区域的关联表:
-- Create table
create table sys_dept_area
(
  uuid varchar2(32) not null,
  area_code varchar2(64) not null,
  dept_code varchar2(64) not null
)
;
-- Add comments to the table
comment on table sys_dept_area
  is '区域与部门对应关系';
-- Add comments to the columns
comment on column sys_dept_area.area_code
  is '区域编码';
comment on column sys_dept_area.dept_code
  is '部门编码';
-- Create/Recreate primary, unique and foreign key constraints
alter table sys_dept_area
  add constraint sys_dept_area_id primary key (uuid);

--主类表
-- Create table
create table main_category
(
  uuid number not null,
  mainCategory_code varchar2(64) not null,
  mainCategory_name varchar2(64) not null,
  mainCategory_displayName varchar2(64),
  mainCategory_desc varchar2(250),
  created_ts timestamp default sysdate not null,
  updated_ts timestamp default sysdate not null,
  del_flag number default 0 not null,
  order_num number
)
;
-- Add comments to the columns
comment on column main_category.uuid
  is '主类ID';
comment on column main_category.mainCategory_code
  is '主类编码';
comment on column main_category.mainCategory_name
  is '主类名称';
comment on column main_category.mainCategory_displayName
  is '主类显示名称';
comment on column main_category.mainCategory_desc
  is '主类描述';
comment on column main_category.created_ts
  is '记录创建时间';
comment on column main_category.updated_ts
  is '记录更新时间';
comment on column main_category.del_flag
  is '是否删除  -1：已删除  0：正常';
comment on column main_category.order_num
  is '排序';
-- Create/Recreate primary, unique and foreign key constraints
alter table main_category
  add constraint main_category_id primary key (uuid);
alter table main_category
  add constraint main_category_code_unique unique(mainCategory_code);


--小类表
-- Create table
create table sub_category
(
  uuid number not null,
  mainCategory_code varchar2(64) not null,   --为简化查询流程使用
  subCategory_code varchar2(64) not null,
  subCategory_value varchar2(64) not null,
  subCategory_displayName varchar2(64),
  subCategory_desc varchar2(250),
  created_ts timestamp default sysdate not null,
  updated_ts timestamp default sysdate not null,
  del_flag number default 0 not null,
  order_num number
)
;
-- Add comments to the columns
comment on column sub_category.uuid
  is '子类ID';
comment on column sub_category.subCategory_code
  is '子类编码';
comment on column sub_category.subCategory_value
  is '子类有效值';
comment on column sub_category.subCategory_displayName
  is '子类显示名称';
comment on column sub_category.subCategory_desc
  is '子类描述';
comment on column sub_category.created_ts
  is '记录创建时间';
comment on column sub_category.updated_ts
  is '记录更新时间';
comment on column sub_category.del_flag
  is '是否删除  -1：已删除  0：正常';
comment on column sub_category.order_num
  is '排序';
-- Create/Recreate primary, unique and foreign key constraints
alter table sub_category
  add constraint sub_category_id primary key (uuid);
alter table sub_category
  add constraint main_category_fk foreign key (mainCategory_code) REFERENCES main_category(mainCategory_code);

-- 系统用户
-- Create table
create table SYS_USER
(
  user_id     number not null,
  username    varchar2(50) not null,
  password    varchar2(100) not null,
  salt        varchar2(20) not null,
  email       varchar2(100),
  mobile      varchar2(100),
  status      number,
  dept_id     number,
  create_time timestamp
)
;
-- Add comments to the table
comment on table SYS_USER
  is '系统用户';
-- Add comments to the columns
comment on column SYS_USER.username
  is '用户名';
comment on column SYS_USER.password
  is '密码';
comment on column SYS_USER.salt
  is '盐';
comment on column SYS_USER.email
  is '邮箱';
comment on column SYS_USER.mobile
  is '手机号';
comment on column SYS_USER.status
  is '状态  0：禁用   1：正常';
comment on column SYS_USER.dept_id
  is '部门ID';
comment on column SYS_USER.create_time
  is '创建时间';
-- Create/Recreate indexes
create unique index SYS_USER_NAME on SYS_USER (username);
-- Create/Recreate primary, unique and foreign key constraints
alter table SYS_USER
  add constraint SYS_USER_ID primary key (USER_ID);

-- 角色
-- Create table
create table SYS_ROLE
(
  role_id     number not null,
  role_name   varchar2(100) not null,
  remark      varchar2(200) not null,
  dept_id     number not null,
  create_time timestamp  default sysdate not null
)
;
-- Add comments to the table
comment on table SYS_ROLE
  is '角色';
-- Add comments to the columns
comment on column SYS_ROLE.role_name
  is '角色名称';
comment on column SYS_ROLE.remark
  is '备注';
comment on column SYS_ROLE.dept_id
  is '部门ID';
comment on column SYS_ROLE.create_time
  is '创建时间';
-- Create/Recreate primary, unique and foreign key constraints
alter table SYS_ROLE
  add constraint SYS_ROLE_ID primary key (ROLE_ID);

-- 用户与角色对应关系
-- Create table
create table SYS_USER_ROLE
(
  rela_id number not null,
  user_id number not null,
  role_id number not null
)
;
-- Add comments to the table
comment on table SYS_USER_ROLE
  is '用户与角色对应关系';
-- Add comments to the columns
comment on column SYS_USER_ROLE.user_id
  is '用户ID';
comment on column SYS_USER_ROLE.role_id
  is '角色ID';
-- Create/Recreate primary, unique and foreign key constraints
alter table SYS_USER_ROLE
  add constraint SYS_USER_ROLE_ID primary key (RELA_ID);

-- 角色与菜单对应关系
-- Create table
create table SYS_ROLE_MENU
(
  rela_id number not null,
  role_id number not null,
  menu_id number not null
)
;
-- Add comments to the table
comment on table SYS_ROLE_MENU
  is '角色与菜单对应关系';
-- Add comments to the columns
comment on column SYS_ROLE_MENU.role_id
  is '角色ID';
comment on column SYS_ROLE_MENU.menu_id
  is '菜单ID';
-- Create/Recreate primary, unique and foreign key constraints
alter table SYS_ROLE_MENU
  add constraint SYS_ROLE_MENU_ID primary key (RELA_ID);

-- 角色与部门对应关系
-- Create table
create table SYS_ROLE_DEPT
(
  rela_id number not null,
  role_id number not null,
  dept_id number not null
)
;
-- Add comments to the table
comment on table SYS_ROLE_DEPT
  is '角色与部门对应关系';
-- Add comments to the columns
comment on column SYS_ROLE_DEPT.role_id
  is '角色ID';
comment on column SYS_ROLE_DEPT.dept_id
  is '部门ID';
-- Create/Recreate primary, unique and foreign key constraints
alter table SYS_ROLE_DEPT
  add constraint SYS_ROLE_DEPT_ID primary key (RELA_ID);

-- 系统配置信息
-- Create table
create table SYS_CONFIG
(
  config_id    NUMBER not null,
  config_key   VARCHAR2(50) not null,
  config_value VARCHAR2(2000) not null,
  status       NUMBER default 1 not null,
  remark       VARCHAR2(500)
);
-- Add comments to the table
comment on table SYS_CONFIG
  is '系统配置信息表';
-- Add comments to the columns
comment on column SYS_CONFIG.config_key
  is 'KEY';
comment on column SYS_CONFIG.config_value
  is 'VALUE';
comment on column SYS_CONFIG.status
  is '状态   0：隐藏   1：显示';
comment on column SYS_CONFIG.remark
  is '备注';
-- Create/Recreate indexes
create unique index SYS_CONFIG_KEY on SYS_CONFIG (CONFIG_KEY);
-- Create/Recreate primary, unique and foreign key constraints
alter table SYS_CONFIG
  add primary key (CONFIG_ID)

-- 系统操作日志
-- Create table
create table SYS_LOG
(
  log_id      number,
  username    varchar2(50),
  operation   varchar2(50),
  method      varchar2(200),
  params      varchar2(2000),
  executetime number,
  ip          varchar2(64),
  create_date timestamp default SYSDATE
)
;
-- Add comments to the table
comment on table SYS_LOG
  is '系统日志';
-- Add comments to the columns
comment on column SYS_LOG.username
  is '用户名';
comment on column SYS_LOG.operation
  is '用户操作';
comment on column SYS_LOG.method
  is '请求方法';
comment on column SYS_LOG.params
  is '请求参数';
comment on column SYS_LOG.executetime
  is '执行时长(毫秒)';
comment on column SYS_LOG.ip
  is 'IP地址';
comment on column SYS_LOG.create_date
  is '创建时间';
-- Create/Recreate primary, unique and foreign key constraints
alter table SYS_LOG
  add constraint SYS_LOG_ID primary key (LOG_ID);

create sequence SYS_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 200
increment by 1
cache 20;
create sequence PERSON_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 100000000000000
increment by 1
cache 20;
create sequence house_seq
minvalue 1
maxvalue 999999999999999999999999999
start with 100000000
increment by 1
cache 20;

CREATE OR REPLACE FUNCTION GET_DICT_NAME(
  DICT_MAIN IN VARCHAR2,
  DICT_CODE IN VARCHAR2)
  RETURN VARCHAR2
AS
  DICT_NAME VARCHAR2(256);
BEGIN
  SELECT SUBCATEGORY_DISPLAYNAME INTO DICT_NAME
    FROM SUB_CATEGORY WHERE MAINCATEGORY_CODE = DICT_MAIN AND SUBCATEGORY_CODE = DICT_CODE;
  RETURN DICT_NAME;
EXCEPTION
   WHEN NO_DATA_FOUND THEN
      DBMS_OUTPUT.PUT_LINE('你需要的数据不存在!');
      RETURN '';
   WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE(SQLCODE||'---'||SQLERRM);
      RETURN '';
END GET_DICT_NAME;

CREATE OR REPLACE FUNCTION GET_AREA_NAME(
  DICT_CODE IN VARCHAR2)
  RETURN VARCHAR2
AS
  DICT_NAME VARCHAR2(256);
BEGIN
  SELECT AREA_NAME INTO DICT_NAME
    FROM SYS_AREA WHERE AREA_ID = DICT_CODE;
  RETURN DICT_NAME;
EXCEPTION
   WHEN NO_DATA_FOUND THEN
      DBMS_OUTPUT.PUT_LINE('你需要的数据不存在!');
      RETURN '';
   WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE(SQLCODE||'---'||SQLERRM);
      RETURN '';
END GET_AREA_NAME;


-- 定时任务
-- Create table
create table SCHEDULE_JOB
(
  jobid          number,
  beanname       varchar2(200),
  methodname     varchar2(100),
  params         varchar2(2000),
  cronexpression varchar2(100),
  status         number,
  remark         varchar2(255),
  createtime     timestamp default sysdate
)
;
-- Add comments to the table
comment on table SCHEDULE_JOB
  is '定时任务';
-- Add comments to the columns
comment on column SCHEDULE_JOB.jobid
  is '任务ID';
comment on column SCHEDULE_JOB.beanname
  is 'SPRING BEAN名称';
comment on column SCHEDULE_JOB.methodname
  is '方法名';
comment on column SCHEDULE_JOB.params
  is '参数';
comment on column SCHEDULE_JOB.cronexpression
  is 'CRON表达式';
comment on column SCHEDULE_JOB.status
  is '任务状态  0：正常  1：暂停';
comment on column SCHEDULE_JOB.remark
  is '备注';
-- Create/Recreate primary, unique and foreign key constraints
alter table SCHEDULE_JOB
  add constraint SCHEDULE_JOB_ID primary key (JOBID);

-- 定时任务日志-- Create table
create table SCHEDULE_JOB_LOG
(
  log_id      number,
  job_id      number,
  bean_name    varchar2(200),
  method_name varchar2(100),
  params      varchar2(2000),
  status      number,
  error       varchar2(2000),
  times       number,
  create_time timestamp default sysdate
)
;
-- Add comments to the table
comment on table SCHEDULE_JOB_LOG
  is '定时任务日志';
-- Add comments to the columns
comment on column SCHEDULE_JOB_LOG.log_id
  is '任务日志ID';
comment on column SCHEDULE_JOB_LOG.job_id
  is '任务ID';
comment on column SCHEDULE_JOB_LOG.beanname
  is 'SPRING BEAN名称';
comment on column SCHEDULE_JOB_LOG.method_name
  is '方法名';
comment on column SCHEDULE_JOB_LOG.params
  is '参数';
comment on column SCHEDULE_JOB_LOG.status
  is '任务状态    0：成功    1：失败';
comment on column SCHEDULE_JOB_LOG.error
  is '失败信息';
comment on column SCHEDULE_JOB_LOG.times
  is '耗时(单位：毫秒)';
-- Create/Recreate primary, unique and foreign key constraints
alter table SCHEDULE_JOB_LOG
  add constraint SCHEDULE_JOB_LOG_ID primary key (LOG_ID);


--  quartz自带表结构
-- 存储每一个已配置的 Job 的详细信息
CREATE TABLE QRTZ_JOB_DETAILS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    JOB_NAME  VARCHAR2(200) NOT NULL,
    JOB_GROUP VARCHAR2(200) NOT NULL,
    DESCRIPTION VARCHAR2(250) NULL,
    JOB_CLASS_NAME   VARCHAR2(250) NOT NULL,
    IS_DURABLE VARCHAR2(1) NOT NULL,
    IS_NONCONCURRENT VARCHAR2(1) NOT NULL,
    IS_UPDATE_DATA VARCHAR2(1) NOT NULL,
    REQUESTS_RECOVERY VARCHAR2(1) NOT NULL,
    JOB_DATA BLOB NULL,
    CONSTRAINT QRTZ_JOB_DETAILS_PK PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
);
--  存储已配置的 Trigger 的信息
CREATE TABLE QRTZ_TRIGGERS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    JOB_NAME  VARCHAR2(200) NOT NULL,
    JOB_GROUP VARCHAR2(200) NOT NULL,
    DESCRIPTION VARCHAR2(250) NULL,
    NEXT_FIRE_TIME NUMBER(13) NULL,
    PREV_FIRE_TIME NUMBER(13) NULL,
    PRIORITY NUMBER(13) NULL,
    TRIGGER_STATE VARCHAR2(16) NOT NULL,
    TRIGGER_TYPE VARCHAR2(8) NOT NULL,
    START_TIME NUMBER(13) NOT NULL,
    END_TIME NUMBER(13) NULL,
    CALENDAR_NAME VARCHAR2(200) NULL,
    MISFIRE_INSTR NUMBER(2) NULL,
    JOB_DATA BLOB NULL,
    CONSTRAINT QRTZ_TRIGGERS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT QRTZ_TRIGGER_TO_JOBS_FK FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
      REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP)
);
-- 存储简单的 Trigger，包括重复次数，间隔，以及已触的次数
CREATE TABLE QRTZ_SIMPLE_TRIGGERS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    REPEAT_COUNT NUMBER(7) NOT NULL,
    REPEAT_INTERVAL NUMBER(12) NOT NULL,
    TIMES_TRIGGERED NUMBER(10) NOT NULL,
    CONSTRAINT QRTZ_SIMPLE_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT QRTZ_SIMPLE_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
    REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
-- 存储 Cron Trigger，包括 Cron 表达式和时区信息
CREATE TABLE QRTZ_CRON_TRIGGERS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    CRON_EXPRESSION VARCHAR2(120) NOT NULL,
    TIME_ZONE_ID VARCHAR2(80),
    CONSTRAINT QRTZ_CRON_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT QRTZ_CRON_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
      REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE QRTZ_SIMPROP_TRIGGERS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    STR_PROP_1 VARCHAR2(512) NULL,
    STR_PROP_2 VARCHAR2(512) NULL,
    STR_PROP_3 VARCHAR2(512) NULL,
    INT_PROP_1 NUMBER(10) NULL,
    INT_PROP_2 NUMBER(10) NULL,
    LONG_PROP_1 NUMBER(13) NULL,
    LONG_PROP_2 NUMBER(13) NULL,
    DEC_PROP_1 NUMERIC(13,4) NULL,
    DEC_PROP_2 NUMERIC(13,4) NULL,
    BOOL_PROP_1 VARCHAR2(1) NULL,
    BOOL_PROP_2 VARCHAR2(1) NULL,
    CONSTRAINT QRTZ_SIMPROP_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT QRTZ_SIMPROP_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
      REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
-- Trigger 作为 Blob 类型存储(用于 Quartz 用户用 JDBC 创建他们自己定制的 Trigger 类型，<span style="color:#800080;">JobStore</span> 并不知道如何存储实例的时候)
CREATE TABLE QRTZ_BLOB_TRIGGERS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    BLOB_DATA BLOB NULL,
    CONSTRAINT QRTZ_BLOB_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT QRTZ_BLOB_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
-- 以 Blob 类型存储 Quartz 的 Calendar 信息
CREATE TABLE QRTZ_CALENDARS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    CALENDAR_NAME  VARCHAR2(200) NOT NULL,
    CALENDAR BLOB NOT NULL,
    CONSTRAINT QRTZ_CALENDARS_PK PRIMARY KEY (SCHED_NAME,CALENDAR_NAME)
);
-- 存储已暂停的 Trigger 组的信息
CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_GROUP  VARCHAR2(200) NOT NULL,
    CONSTRAINT QRTZ_PAUSED_TRIG_GRPS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP)
);
-- 存储与已触发的 Trigger 相关的状态信息，以及相联 Job 的执行信息
CREATE TABLE QRTZ_FIRED_TRIGGERS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    ENTRY_ID VARCHAR2(95) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    INSTANCE_NAME VARCHAR2(200) NOT NULL,
    FIRED_TIME NUMBER(13) NOT NULL,
    PRIORITY NUMBER(13) NOT NULL,
    STATE VARCHAR2(16) NOT NULL,
    JOB_NAME VARCHAR2(200) NULL,
    JOB_GROUP VARCHAR2(200) NULL,
    IS_NONCONCURRENT VARCHAR2(1) NULL,
    REQUESTS_RECOVERY VARCHAR2(1) NULL,
    SCHED_TIME NUMBER(13) NOT NULL DEFAULT 0,
    CONSTRAINT QRTZ_FIRED_TRIGGER_PK PRIMARY KEY (SCHED_NAME,ENTRY_ID)
);
-- 存储少量的有关 Scheduler 的状态信息，和别的 Scheduler 实例(假如是用于一个集群中)
CREATE TABLE QRTZ_SCHEDULER_STATE
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    INSTANCE_NAME VARCHAR2(200) NOT NULL,
    LAST_CHECKIN_TIME NUMBER(13) NOT NULL,
    CHECKIN_INTERVAL NUMBER(13) NOT NULL,
    CONSTRAINT QRTZ_SCHEDULER_STATE_PK PRIMARY KEY (SCHED_NAME,INSTANCE_NAME)
);
-- 存储程序的悲观锁的信息(假如使用了悲观锁)
CREATE TABLE QRTZ_LOCKS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    LOCK_NAME  VARCHAR2(40) NOT NULL,
    CONSTRAINT QRTZ_LOCKS_PK PRIMARY KEY (SCHED_NAME,LOCK_NAME)
);

create index idx_qrtz_j_req_recovery on qrtz_job_details(SCHED_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_j_grp on qrtz_job_details(SCHED_NAME,JOB_GROUP);

create index idx_qrtz_t_j on qrtz_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_t_jg on qrtz_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_t_c on qrtz_triggers(SCHED_NAME,CALENDAR_NAME);
create index idx_qrtz_t_g on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP);
create index idx_qrtz_t_state on qrtz_triggers(SCHED_NAME,TRIGGER_STATE);
create index idx_qrtz_t_n_state on qrtz_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_n_g_state on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_next_fire_time on qrtz_triggers(SCHED_NAME,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st on qrtz_triggers(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
create index idx_qrtz_t_nft_st_misfire_grp on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

create index idx_qrtz_ft_trig_inst_name on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME);
create index idx_qrtz_ft_inst_job_req_rcvry on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_ft_j_g on qrtz_fired_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_ft_jg on qrtz_fired_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_ft_t_g on qrtz_fired_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
create index idx_qrtz_ft_tg on qrtz_fired_triggers(SCHED_NAME,TRIGGER_GROUP);
