/*  

 drop view XXMCC.usuarios#
 drop view XXMCC.MEDICOS#;
 DROP VIEW XXMCC.CONSULTAS#;
 drop sequence XXMCC.usuarios_s;
 drop sequence XXMCC.medicos_s;
 drop sequence XXMCC.consultas_s;
 drop table xxmcc.usuarios;
 drop table xxmcc.consultas;
 drop table xxmcc.medicos;

*/
--------------------------------------------------------------------------------

-- Script de criação de tabelas - Banco de dados Oracle 

--------------------------------------------------------------------------------
CREATE TABLE xxmcc.medicos(
    id             NUMBER(15)NOT NULL
    ,nome          VARCHAR2(100)NOT NULL
    ,email         VARCHAR2(100)NOT NULL UNIQUE
    ,telefone      VARCHAR2(20)NOT NULL
    ,crm           VARCHAR2(6)NOT NULL UNIQUE
    ,especialidade VARCHAR(100)NOT NULL
   
)

TABLESPACE xxmccd ;

ALTER TABLE xxmcc.medicos ADD CONSTRAINT id_medico_pk PRIMARY KEY ( id ) USING INDEX TABLESPACE xxmccx ; 

CREATE TABLE xxmcc.consultas(
    id        NUMBER(15)NOT NULL
    ,medico_id NUMBER(15)NOT NULL
    ,paciente  VARCHAR2(100)NOT NULL
    ,data      DATE NOT NULL
)
TABLESPACE xxmccd;

ALTER TABLE xxmcc.consultas ADD CONSTRAINT id_consultas_pk PRIMARY KEY ( id ) USING INDEX TABLESPACE xxmccx ; 
ALTER TABLE xxmcc.consultas ADD CONSTRAINT medicos_consultas_fk FOREIGN KEY ( medico_id )   REFERENCES  xxmcc.medicos ( id ) ;
   

CREATE TABLE  xxmcc.usuarios(
     id   NUMBER(15)NOT NULL
    ,nome VARCHAR2(100) NOT NULL
    ,email VARCHAR2(100) NOT NULL 
    ,senha VARCHAR2(100) NOT NULL

    
)TABLESPACE xxmccd;


ALTER TABLE xxmcc.usuarios ADD CONSTRAINT id_usuarios_pk PRIMARY KEY ( id ) USING INDEX TABLESPACE xxmccx ; 
ALTER TABLE xxmcc.usuarios ADD CONSTRAINT email_uk1 UNIQUE ( email ) USING INDEX TABLESPACE xxmccx ; 
   
--------------------------------------------------------------------------------  


CREATE SEQUENCE XXMCC.medicos_s
INCREMENT BY 1
START WITH 1
MAXVALUE 999999999999999
MINVALUE 1
NOCACHE;



CREATE SEQUENCE XXMCC.consultas_s
INCREMENT BY 1
START WITH 1
MAXVALUE 999999999999999
MINVALUE 1
NOCACHE;



CREATE SEQUENCE XXMCC.usuarios_s
INCREMENT BY 1
START WITH 1
MAXVALUE 999999999999999
MINVALUE 1
NOCACHE;

--------------------------------------------------------------------------------


BEGIN
  AD_ZD_TABLE.UPGRADE('XXMCC','MEDICOS');
  AD_ZD_TABLE.UPGRADE('XXMCC','CONSULTAS');
  AD_ZD_TABLE.UPGRADE('XXMCC','USUARIOS');
  
END;

--------------------------------------------------------------------------------






CREATE OR REPLACE TRIGGER APPS.MEDICOS_S_TRG
 BEFORE INSERT OR UPDATE ON XXMCC.MEDICOS#
 FOR EACH ROW

BEGIN
  BEGIN
     --Populando a primary key
     IF INSERTING AND :new.ID IS NULL THEN
        SELECT XXMCC.medicos_s.nextval INTO :new.ID FROM DUAL;
     END IF;
     
  
     --
  END;
END MEDICOS_S_TRG;




CREATE OR REPLACE TRIGGER APPS.CONSULTAS_S_TRG
 BEFORE INSERT OR UPDATE ON XXMCC.CONSULTAS#
 FOR EACH ROW

BEGIN
  BEGIN
     --Populando a primary key
     IF INSERTING AND :new.ID IS NULL THEN
        SELECT XXMCC.consultas_s.nextval INTO :new.ID FROM DUAL;
     END IF;
     
  
     --
  END;
END CONSULTAS_S_TRG;



CREATE OR REPLACE TRIGGER APPS.USUARIOS_S_TRG
 BEFORE INSERT OR UPDATE ON XXMCC.USUARIOS#
 FOR EACH ROW

BEGIN
  BEGIN
     --Populando a primary key
     IF INSERTING AND :new.ID IS NULL THEN
        SELECT XXMCC.usuarios_s.nextval INTO :new.ID FROM DUAL;
     END IF;
     
  
     --
  END;
END USUARIOS_S_TRG;

--------------------------------------------------------------------------------

GRANT SELECT ON XXMCC.medicos_s   TO APPS;
GRANT SELECT ON XXMCC.consultas_s TO APPS;
GRANT SELECT ON XXMCC.usuarios_s  TO APPS;
--------------------------------------------------------------------------------

CREATE OR REPLACE SYNONYM medicos_s   FOR XXMCC.medicos_s ;
CREATE OR REPLACE SYNONYM consultas_s FOR XXMCC.consultas_s ;
CREATE OR REPLACE SYNONYM usuarios_s  FOR XXMCC.usuarios_s;

---------------------------------------------------------------------------------

-- para criptografar as senhas :  https://www.javainuse.com/onlineBcrypt
 INSERT INTO usuarios(nome, email, senha) VALUES ('Joao', 'joao@email.com', '$2y$10$GE/i8ZvFAPuRU8Av7Bq4sO3/PbSYWon2MG2oscE2B7nxPwCEGI2yu');  --joao123
 INSERT INTO usuarios(nome, email, senha) VALUES ('Maria', 'maria@email.com', '$2a$10$vfOb6emgnXWMPoELDfj7fOwXpGFl3fpLHe9gGT2Hqj7SsgZ4lD/C.'); --maria123
 