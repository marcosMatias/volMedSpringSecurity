/*  
 drop view xxmcc.pacientes#;
 drop view XXMCC.usuarios#;
 drop view XXMCC.MEDICOS#;
 DROP VIEW XXMCC.CONSULTAS#;
 drop sequence xxmcc.pacientes_s;
 drop sequence XXMCC.usuarios_s;
 drop sequence XXMCC.medicos_s;
 drop sequence XXMCC.consultas_s;
 drop table xxmcc.pacientes;
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



CREATE TABLE xxmcc.pacientes(
id NUMBER(15) NOT NULL	
, nome VARCHAR2(100) NOT NULL
,email VARCHAR2(100) NOT NULL
,telefone VARCHAR2(100) NOT NULL
,cpf VARCHAR2(14) NOT NULL
)TABLESPACE xxmccd;
   
   ALTER TABLE xxmcc.pacientes ADD CONSTRAINT id_pacientes_pk PRIMARY KEY ( id ) USING INDEX TABLESPACE xxmccx ; 
   ALTER TABLE xxmcc.pacientes ADD CONSTRAINT pacientes_uk1 UNIQUE ( email ) USING INDEX TABLESPACE xxmccx ; 
   ALTER TABLE xxmcc.pacientes ADD CONSTRAINT pacientes_uk2 UNIQUE ( telefone ) USING INDEX TABLESPACE xxmccx ; 
   ALTER TABLE xxmcc.pacientes ADD CONSTRAINT pacientes_uk3 UNIQUE ( cpf ) USING INDEX TABLESPACE xxmccx ; 
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


CREATE SEQUENCE XXMCC.pacientes_s
INCREMENT BY 1
START WITH 1
MAXVALUE 999999999999999
MINVALUE 1
NOCACHE;

--------------------------------------------------------------------------------

GRANT SELECT ON XXMCC.medicos_s   TO APPS;
GRANT SELECT ON XXMCC.consultas_s TO APPS;
GRANT SELECT ON XXMCC.usuarios_s  TO APPS;
GRANT SELECT ON XXMCC.pacientes_s TO APPS;


--------------------------------------------------------------------------------


BEGIN
  AD_ZD_TABLE.UPGRADE('XXMCC','MEDICOS');
  AD_ZD_TABLE.UPGRADE('XXMCC','CONSULTAS');
  AD_ZD_TABLE.UPGRADE('XXMCC','USUARIOS');
  AD_ZD_TABLE.UPGRADE('XXMCC','PACIENTES');
  
END;

--------------------------------------------------------------------------------

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

// Dropar a trigger a aula 6 do segundo curso. drop trigger MEDICOS_S_TRG;
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


// Dropar a trigger a aula 6 do segundo curso. drop trigger PACIENTES_S_TRG;
CREATE OR REPLACE TRIGGER APPS.PACIENTES_S_TRG
 BEFORE INSERT OR UPDATE ON XXMCC.PACIENTES#
 FOR EACH ROW

BEGIN
  BEGIN
     --Populando a primary key
     IF INSERTING AND :new.ID IS NULL THEN
        SELECT XXMCC.pacientes_s.nextval INTO :new.ID FROM DUAL;
     END IF;
     
  
     --
  END;
END PACIENTES_S_TRG;
--------------------------------------------------------------------------------

CREATE OR REPLACE SYNONYM medicos_s    FOR XXMCC.medicos_s ;
CREATE OR REPLACE SYNONYM consultas_s  FOR XXMCC.consultas_s ;
CREATE OR REPLACE SYNONYM usuarios_s   FOR XXMCC.usuarios_s;
CREATE OR REPLACE SYNONYM pacientes_s  FOR XXMCC.pacientes_s;
---------------------------------------------------------------------------------

-- para criptografar as senhas :  https://www.javainuse.com/onlineBcrypt
 INSERT INTO usuarios(nome, email, senha) VALUES ('Joao', 'joao@email.com', '$2y$10$GE/i8ZvFAPuRU8Av7Bq4sO3/PbSYWon2MG2oscE2B7nxPwCEGI2yu');  --joao123
 INSERT INTO usuarios(nome, email, senha) VALUES ('Maria', 'maria@email.com', '$2a$10$vfOb6emgnXWMPoELDfj7fOwXpGFl3fpLHe9gGT2Hqj7SsgZ4lD/C.'); --maria123
 
 --------------------------------------------------------------------------------
 -- Alteracoes
 
 ALTER TABLE usuarios 
ADD perfil VARCHAR2(20) 
CHECK (perfil IN ('ATENDENTE', 'MEDICO', 'PACIENTE'));
-- relaizar os updates na tabela usuario para preencher os campos. após rodar o comando abaixo

 ALTER TABLE usuarios 
modify perfil VARCHAR2(20) not null;
 
-- Rodar o ADZ para propagar as alterações

BEGIN

  AD_ZD_TABLE.PATCH('XXMCC','USUARIOS');

  
END;

-- consultas
alter table consultas drop column paciente;

ALTER TABLE consultas
ADD paciente_id NUMBER(15) NOT NULL;

ALTER TABLE xxmcc.consultas ADD CONSTRAINT pacientes_consultas_fk FOREIGN KEY ( paciente_id )   REFERENCES  xxmcc.pacientes ( id ) ;


BEGIN

  AD_ZD_TABLE.PATCH('XXMCC','CONSULTAS');

  
END;

-- USUARIOS
ALTER TABLE usuarios
ADD senha_alterada varchar2(1) ;

update usuarios
set senha_alterada ='N';

ALTER TABLE usuarios
MODIFY senha_alterada varchar2(1) NOT NULL ;


ALTER TABLE usuarios ADD token VARCHAR2(64); 
ALTER TABLE usuarios add EXPIRACAO_TOKEN TIMESTAMP;




BEGIN

  AD_ZD_TABLE.PATCH('XXMCC','USUARIOS');

  
END;