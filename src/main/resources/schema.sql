--
-- Create Schema Script
--   Database Version          : 11.2.0.4.0
--   Database Compatible Level : 11.2.0.4.0
--   Script Compatible Level   : 11.2.0.4.0
--   Toad Version              : 12.6.0.53
--   DB Connect String         : DBPDEV
--   Schema                    : TFS_YKY
--   Script Created by         : TFS__FRAMEWORK
--   Script Created at         : 04.08.2015 17:05:26
--   Notes                     : 
--

-- Object Counts: 
--   Indexes: 8         Columns: 11         
--   Packages: 1        Lines of Code: 10 
--   Package Bodies: 1  Lines of Code: 30 
--   Sequences: 5 
--   Tables: 8          Columns: 53         Partitions: 2       Constraints: 15     
--   Triggers: 8 


--
-- FRM_ANNOUNCEMENTS  (Table) 
--
CREATE TABLE TFS_YKY.FRM_ANNOUNCEMENTS
(
  ID           NUMBER                           NOT NULL,
  HEADER       VARCHAR2(512 CHAR),
  BODY         CLOB,
  VALID_UNTIL  DATE                             DEFAULT SYSDATE,
  STATUS       NUMBER                           DEFAULT 1,
  INSDATE      TIMESTAMP(6)                     DEFAULT SYSTIMESTAMP,
  INSBY        VARCHAR2(16 CHAR)                DEFAULT USER,
  UPDDATE      TIMESTAMP(6)                     DEFAULT SYSTIMESTAMP,
  UPDBY        VARCHAR2(16 CHAR)                DEFAULT USER
)
TABLESPACE USERS
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- FRM_ANNOUNCEMENT_ATTACHMENTS  (Table) 
--
CREATE TABLE TFS_YKY.FRM_ANNOUNCEMENT_ATTACHMENTS
(
  FRM_ANNOUNCEMENT_ID  NUMBER,
  FRM_ATTACHMENT_PATH  VARCHAR2(1024 CHAR),
  FRM_ATTACHMENT_NAME  VARCHAR2(128 CHAR),
  FRM_ATTACHMENT_DESC  VARCHAR2(1024 CHAR)
)
TABLESPACE USERS
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- FRM_ANNOUNCEMENT_READ  (Table) 
--
CREATE TABLE TFS_YKY.FRM_ANNOUNCEMENT_READ
(
  FRM_ANNOUNCEMENT_ID  NUMBER,
  FRM_USERNAME         VARCHAR2(16 CHAR)
)
TABLESPACE USERS
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- FRM_LOG  (Table) 
--
CREATE TABLE TFS_YKY.FRM_LOG
(
  ID                     NUMBER,
  EXCEPTION_ID           VARCHAR2(16 CHAR),
  EXCEPTION_LEVEL        NUMBER,
  EXCEPTION_NAME         VARCHAR2(1024 CHAR),
  EXCEPTION_MESSAGE      VARCHAR2(1024 CHAR),
  EXCEPTION_STACK_TRACE  CLOB,
  EXCEPTION_URL          VARCHAR2(128 CHAR),
  STATUS                 NUMBER                 DEFAULT 1,
  INSDATE                DATE,
  INSBY                  VARCHAR2(16 CHAR),
  UPDDATE                DATE,
  UPDBY                  VARCHAR2(16 CHAR)
)
TABLESPACE USERS
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            BUFFER_POOL      DEFAULT
           )
PARTITION BY RANGE (INSDATE)
INTERVAL( NUMTOYMINTERVAL(1,'YEAR'))
(  
  PARTITION VALUES LESS THAN (TO_DATE(' 2010-01-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    TABLESPACE USERS
    PCTFREE    10
    INITRANS   1
    MAXTRANS   255
    STORAGE    (
                MAXSIZE          UNLIMITED
                BUFFER_POOL      DEFAULT
               ),  
  PARTITION VALUES LESS THAN (TO_DATE(' 2016-01-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    TABLESPACE USERS
    PCTFREE    10
    INITRANS   1
    MAXTRANS   255
    STORAGE    (
                INITIAL          8M
                NEXT             1M
                MAXSIZE          UNLIMITED
                MINEXTENTS       1
                MAXEXTENTS       UNLIMITED
                BUFFER_POOL      DEFAULT
               )
);


--
-- FRM_PARAMETERS  (Table) 
--
CREATE TABLE TFS_YKY.FRM_PARAMETERS
(
  ID       NUMBER,
  NAME     VARCHAR2(256 CHAR)                   NOT NULL,
  VALUE    VARCHAR2(2048 CHAR)                  NOT NULL,
  STATUS   NUMBER                               DEFAULT 1,
  INSDATE  TIMESTAMP(6),
  INSBY    VARCHAR2(16 CHAR),
  UPDDATE  TIMESTAMP(6),
  UPDBY    VARCHAR2(16 CHAR)
)
TABLESPACE USERS
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- FRM_ROLES  (Table) 
--
CREATE TABLE TFS_YKY.FRM_ROLES
(
  ID           NUMBER,
  NAME         VARCHAR2(64 CHAR),
  DESCRIPTION  VARCHAR2(128 CHAR),
  STATUS       NUMBER,
  INSDATE      TIMESTAMP(6),
  INSBY        VARCHAR2(16 CHAR),
  UPDDATE      TIMESTAMP(6),
  UPDBY        VARCHAR2(16 CHAR)
)
TABLESPACE USERS
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- SEQ_FRM_ANNOUNCEMENTS  (Sequence) 
--
CREATE SEQUENCE TFS_YKY.SEQ_FRM_ANNOUNCEMENTS
  START WITH 43
  MAXVALUE 9999999999999999999999999999
  MINVALUE 0
  NOCYCLE
  NOCACHE
  NOORDER;


--
-- SEQ_FRM_AUTHS  (Sequence) 
--
CREATE SEQUENCE TFS_YKY.SEQ_FRM_AUTHS
  START WITH 25
  MAXVALUE 9999999999999999999999999999
  MINVALUE 0
  NOCYCLE
  NOCACHE
  NOORDER;


--
-- SEQ_FRM_LOG  (Sequence) 
--
CREATE SEQUENCE TFS_YKY.SEQ_FRM_LOG
  START WITH 226
  MAXVALUE 9999999999999999999999999999
  MINVALUE 0
  NOCYCLE
  NOCACHE
  NOORDER;


--
-- SEQ_FRM_PARAMETERS  (Sequence) 
--
CREATE SEQUENCE TFS_YKY.SEQ_FRM_PARAMETERS
  START WITH 17
  MAXVALUE 9999999999999999999999999999
  MINVALUE 0
  NOCYCLE
  NOCACHE
  NOORDER;


--
-- SEQ_FRM_ROLES  (Sequence) 
--
CREATE SEQUENCE TFS_YKY.SEQ_FRM_ROLES
  START WITH 90
  MAXVALUE 9999999999999999999999999999
  MINVALUE 0
  NOCYCLE
  NOCACHE
  NOORDER;


--
-- FRM_ANNOUNCEMENTS_PK  (Index) 
--
CREATE UNIQUE INDEX TFS_YKY.FRM_ANNOUNCEMENTS_PK ON TFS_YKY.FRM_ANNOUNCEMENTS
(ID)
TABLESPACE USERS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );

--
-- FRM_ANNOUNCEMENT_ATTACS_PK  (Index) 
--
CREATE UNIQUE INDEX TFS_YKY.FRM_ANNOUNCEMENT_ATTACS_PK ON TFS_YKY.FRM_ANNOUNCEMENT_ATTACHMENTS
(FRM_ANNOUNCEMENT_ID, FRM_ATTACHMENT_PATH)
TABLESPACE USERS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );

--
-- FRM_ANNOUNCEMENT_READ_PK  (Index) 
--
CREATE UNIQUE INDEX TFS_YKY.FRM_ANNOUNCEMENT_READ_PK ON TFS_YKY.FRM_ANNOUNCEMENT_READ
(FRM_ANNOUNCEMENT_ID, FRM_USERNAME)
TABLESPACE USERS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );

--
-- FRM_LOG_PK  (Index) 
--
CREATE UNIQUE INDEX TFS_YKY.FRM_LOG_PK ON TFS_YKY.FRM_LOG
(ID)
TABLESPACE USERS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );

--
-- FRM_PARAMETERS_PK  (Index) 
--
CREATE UNIQUE INDEX TFS_YKY.FRM_PARAMETERS_PK ON TFS_YKY.FRM_PARAMETERS
(ID)
TABLESPACE USERS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );

--
-- FRM_ROLES_PK  (Index) 
--
CREATE UNIQUE INDEX TFS_YKY.FRM_ROLES_PK ON TFS_YKY.FRM_ROLES
(ID)
TABLESPACE USERS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );

--
-- API_FRM_ROLES  (Package) 
--
create or replace package TFS_YKY.api_frm_roles
as
  function get_all_roles
    return owa.vc_arr;

  function get_user_roles(p_user              in varchar2,
                          p_password          in varchar2,
                          p_user_type_ldap_id in varchar2)
    return varchar2;
end api_frm_roles;
/


--
-- API_FRM_ROLES  (Package Body) 
--
create or replace package body TFS_YKY.api_frm_roles
as
  function get_all_roles
    return owa.vc_arr
  is
    l_all_roles owa.vc_arr;
  begin
    for rec in (select name
                  from frm_roles
                 where status > 0)
    loop
      l_all_roles(l_all_roles.count + 1) := rec.name;
    end loop;

    return l_all_roles;
  end get_all_roles;

  function get_user_roles(p_user              in varchar2,
                          p_password          in varchar2,
                          p_user_type_ldap_id in varchar2)
    return varchar2
  is
    l_user_roles varchar2(32767);
    l_all_roles  owa.vc_arr;
  begin
    l_all_roles  := get_all_roles;
    l_user_roles := tfs_lib.glb_pkg_ldap.get_ldap_roles(p_user_type_ldap_id, p_user, p_password, l_all_roles);
    return l_user_roles;
  end;
end api_frm_roles;
/


--
-- T_BIR_FRM_ANNOUNCEMENTS  (Trigger) 
--
create or replace trigger TFS_YKY.t_bir_frm_announcements
  before insert
  on TFS_YKY.frm_announcements
  referencing new as new old as old
  for each row
begin
  :new.id      := nvl(:new.id, seq_frm_announcements.nextval);
  :new.insdate := systimestamp;
  :new.insby   := nvl(nvl(:new.updby, v('APP_USER')), user);
  :new.upddate := systimestamp;
  :new.updby   := nvl(nvl(:new.updby, v('APP_USER')), user);
end t_bir_frm_announcements;
/


--
-- T_BIR_FRM_PARAMETERS  (Trigger) 
--
create or replace trigger TFS_YKY.t_bir_frm_parameters
  before insert
  on TFS_YKY.frm_parameters
  referencing new as new old as old
  for each row
begin
  :new.id      := nvl(:new.id, seq_frm_parameters.nextval);
  :new.insdate := systimestamp;
  :new.insby   := nvl(nvl(:new.updby, v('APP_USER')), user);
  :new.upddate := systimestamp;
  :new.updby   := nvl(nvl(:new.updby, v('APP_USER')), user);
end t_bir_frm_parameters;
/


--
-- T_BIR_FRM_ROLES  (Trigger) 
--
create or replace trigger TFS_YKY.t_bir_frm_roles
  before insert
  on TFS_YKY.frm_roles
  referencing new as new old as old
  for each row
begin
  :new.id      := nvl(:new.id, seq_frm_roles.nextval);
  :new.insdate := systimestamp;
  :new.insby   := nvl(nvl(:new.updby, v('APP_USER')), user);
  :new.upddate := systimestamp;
  :new.updby   := nvl(nvl(:new.updby, v('APP_USER')), user);
end t_bir_frm_roles;
/


--
-- T_BUR_FRM_ANNOUNCEMENTS  (Trigger) 
--
create or replace trigger TFS_YKY.t_bur_frm_announcements
  before update
  on TFS_YKY.frm_announcements
  referencing new as new old as old
  for each row
begin
  :new.id      := :old.id;
  :new.upddate := systimestamp;
  :new.updby   := nvl(nvl(:new.updby, v('APP_USER')), user);
end t_bur_frm_announcements;
/


--
-- T_BUR_FRM_PARAMETERS  (Trigger) 
--
create or replace trigger TFS_YKY.t_bur_frm_parameters
  before update
  on TFS_YKY.frm_parameters
  referencing new as new old as old
  for each row
begin
  :new.id      := :old.id;
  :new.insdate := :old.insdate;
  :new.insby   := nvl(nvl(:new.updby, v('APP_USER')), user);
  :new.upddate := systimestamp;
  :new.updby   := nvl(nvl(:new.updby, v('APP_USER')), user);
end t_bur_frm_parameters;
/


--
-- T_BUR_FRM_ROLES  (Trigger) 
--
create or replace trigger TFS_YKY.t_bur_frm_roles
  before update
  on TFS_YKY.frm_roles
  referencing new as new old as old
  for each row
begin
  :new.id      := :old.id;
  :new.insdate := :old.insdate;
  :new.insby   := nvl(nvl(:new.updby, v('APP_USER')), user);
  :new.upddate := systimestamp;
  :new.updby   := nvl(nvl(:new.updby, v('APP_USER')), user);
end t_bur_frm_roles;
/


--
-- FRM_ANNOUNCEMENT_ROLE  (Table) 
--
CREATE TABLE TFS_YKY.FRM_ANNOUNCEMENT_ROLE
(
  FRM_ANNOUNCEMENT_ID  NUMBER,
  FRM_ROLE_ID          NUMBER
)
TABLESPACE USERS
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- FRM_AUTHS  (Table) 
--
CREATE TABLE TFS_YKY.FRM_AUTHS
(
  ID        NUMBER,
  ROLE_ID   NUMBER,
  URL_PATH  VARCHAR2(128 CHAR),
  STATUS    NUMBER,
  INSDATE   TIMESTAMP(6),
  INSBY     VARCHAR2(16 CHAR),
  UPDDATE   TIMESTAMP(6),
  UPDBY     VARCHAR2(16 CHAR)
)
TABLESPACE USERS
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- FRM_ANNOUNCEMENT_ROLE_PK  (Index) 
--
CREATE UNIQUE INDEX TFS_YKY.FRM_ANNOUNCEMENT_ROLE_PK ON TFS_YKY.FRM_ANNOUNCEMENT_ROLE
(FRM_ANNOUNCEMENT_ID, FRM_ROLE_ID)
TABLESPACE USERS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );

--
-- FRM_AUTHS_PK  (Index) 
--
CREATE UNIQUE INDEX TFS_YKY.FRM_AUTHS_PK ON TFS_YKY.FRM_AUTHS
(ID)
TABLESPACE USERS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );

--
-- T_BIR_FRM_AUTHS  (Trigger) 
--
create or replace trigger TFS_YKY.t_bir_frm_auths
  before insert
  on TFS_YKY.frm_auths
  referencing new as new old as old
  for each row
begin
  :new.id      := nvl(:new.id, seq_frm_auths.nextval);
  :new.insdate := systimestamp;
  :new.insby   := nvl(nvl(:new.updby, v('APP_USER')), user);
  :new.upddate := systimestamp;
  :new.updby   := nvl(nvl(:new.updby, v('APP_USER')), user);
end t_bir_frm_auths;
/


--
-- T_BUR_FRM_AUTHS  (Trigger) 
--
create or replace trigger TFS_YKY.t_bur_frm_auths
  before update
  on TFS_YKY.frm_auths
  referencing new as new old as old
  for each row
begin
  :new.id      := :old.id;
  :new.insdate := :old.insdate;
  :new.insby   := nvl(nvl(:new.updby, v('APP_USER')), user);
  :new.upddate := systimestamp;
  :new.updby   := nvl(nvl(:new.updby, v('APP_USER')), user);
end t_bur_frm_auths;
/


-- 
-- Non Foreign Key Constraints for Table FRM_ANNOUNCEMENTS 
-- 
ALTER TABLE TFS_YKY.FRM_ANNOUNCEMENTS ADD (
  CONSTRAINT FRM_ANNOUNCEMENTS_PK
  PRIMARY KEY
  (ID)
  USING INDEX TFS_YKY.FRM_ANNOUNCEMENTS_PK
  ENABLE VALIDATE);


-- 
-- Non Foreign Key Constraints for Table FRM_ANNOUNCEMENT_READ 
-- 
ALTER TABLE TFS_YKY.FRM_ANNOUNCEMENT_READ ADD (
  CONSTRAINT FRM_ANNOUNCEMENT_READ_PK
  PRIMARY KEY
  (FRM_ANNOUNCEMENT_ID, FRM_USERNAME)
  USING INDEX TFS_YKY.FRM_ANNOUNCEMENT_READ_PK
  ENABLE VALIDATE);


-- 
-- Non Foreign Key Constraints for Table FRM_LOG 
-- 
ALTER TABLE TFS_YKY.FRM_LOG ADD (
  CONSTRAINT FRM_LOG_PK
  PRIMARY KEY
  (ID)
  USING INDEX TFS_YKY.FRM_LOG_PK
  ENABLE VALIDATE);


-- 
-- Non Foreign Key Constraints for Table FRM_PARAMETERS 
-- 
ALTER TABLE TFS_YKY.FRM_PARAMETERS ADD (
  CONSTRAINT FRM_PARAMETERS_PK
  PRIMARY KEY
  (ID)
  USING INDEX TFS_YKY.FRM_PARAMETERS_PK
  ENABLE VALIDATE);


-- 
-- Non Foreign Key Constraints for Table FRM_ROLES 
-- 
ALTER TABLE TFS_YKY.FRM_ROLES ADD (
  CONSTRAINT FRM_ROLES_PK
  PRIMARY KEY
  (ID)
  USING INDEX TFS_YKY.FRM_ROLES_PK
  ENABLE VALIDATE);


-- 
-- Non Foreign Key Constraints for Table FRM_ANNOUNCEMENT_ROLE 
-- 
ALTER TABLE TFS_YKY.FRM_ANNOUNCEMENT_ROLE ADD (
  CONSTRAINT FRM_ANNOUNCEMENT_ROLE_PK
  PRIMARY KEY
  (FRM_ANNOUNCEMENT_ID, FRM_ROLE_ID)
  USING INDEX TFS_YKY.FRM_ANNOUNCEMENT_ROLE_PK
  ENABLE VALIDATE);


-- 
-- Non Foreign Key Constraints for Table FRM_AUTHS 
-- 
ALTER TABLE TFS_YKY.FRM_AUTHS ADD (
  CONSTRAINT FRM_AUTHS_PK
  PRIMARY KEY
  (ID)
  USING INDEX TFS_YKY.FRM_AUTHS_PK
  ENABLE VALIDATE);


-- 
-- Foreign Key Constraints for Table FRM_ANNOUNCEMENT_ATTACHMENTS 
-- 
ALTER TABLE TFS_YKY.FRM_ANNOUNCEMENT_ATTACHMENTS ADD (
  CONSTRAINT FRM_ANNOUNCEMENT_ATTACS_R01 
  FOREIGN KEY (FRM_ANNOUNCEMENT_ID) 
  REFERENCES TFS_YKY.FRM_ANNOUNCEMENTS (ID)
  ON DELETE CASCADE
  ENABLE VALIDATE);


-- 
-- Foreign Key Constraints for Table FRM_ANNOUNCEMENT_READ 
-- 
ALTER TABLE TFS_YKY.FRM_ANNOUNCEMENT_READ ADD (
  CONSTRAINT FRM_ANNOUNCEMENT_READ_R01 
  FOREIGN KEY (FRM_ANNOUNCEMENT_ID) 
  REFERENCES TFS_YKY.FRM_ANNOUNCEMENTS (ID)
  ON DELETE CASCADE
  ENABLE VALIDATE);


-- 
-- Foreign Key Constraints for Table FRM_ANNOUNCEMENT_ROLE 
-- 
ALTER TABLE TFS_YKY.FRM_ANNOUNCEMENT_ROLE ADD (
  CONSTRAINT FRM_ANNOUNCEMENT_ROLE_R01 
  FOREIGN KEY (FRM_ANNOUNCEMENT_ID) 
  REFERENCES TFS_YKY.FRM_ANNOUNCEMENTS (ID)
  ON DELETE CASCADE
  ENABLE VALIDATE);

ALTER TABLE TFS_YKY.FRM_ANNOUNCEMENT_ROLE ADD (
  CONSTRAINT FRM_ANNOUNCEMENT_ROLE_R02 
  FOREIGN KEY (FRM_ROLE_ID) 
  REFERENCES TFS_YKY.FRM_ROLES (ID)
  ON DELETE CASCADE
  ENABLE VALIDATE);


-- 
-- Foreign Key Constraints for Table FRM_AUTHS 
-- 
ALTER TABLE TFS_YKY.FRM_AUTHS ADD (
  CONSTRAINT FRM_AUTHS_R01 
  FOREIGN KEY (ROLE_ID) 
  REFERENCES TFS_YKY.FRM_ROLES (ID)
  ON DELETE CASCADE
  ENABLE VALIDATE);