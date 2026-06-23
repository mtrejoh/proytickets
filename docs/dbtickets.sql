CREATE DATABASE `dbtickets` 
/*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci */ 
/*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE dbtickets.tbpersonal (
	idpersonal BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
	nombre varchar(40) NOT NULL COMMENT 'Nombre del personal',
	app varchar(40) NULL COMMENT 'apellido paterno del personal',
	apm varchar(40) NULL COMMENT 'apellido materno del personal',
	estatus TINYINT UNSIGNED DEFAULT '1' NOT NULL COMMENT 'Default 1= Activo',
	CONSTRAINT tbpersonal_pk PRIMARY KEY (idpersonal)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_spanish_ci;


CREATE TABLE dbtickets.tbroles (
	idrol BIGINT UNSIGNED auto_increment NOT NULL,
	cverol varchar(6) NOT NULL,
	nombre VARCHAR(40) NOT NULL,
	estatus TINYINT UNSIGNED DEFAULT 1 NOT NULL,
	CONSTRAINT tbroles_pk PRIMARY KEY (idrol),
	CONSTRAINT tbroles_unique UNIQUE KEY (cverol)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_spanish_ci;


CREATE TABLE dbtickets.tbusuarios (
	idusuario BIGINT UNSIGNED  auto_increment NOT NULL,
	idpersonal BIGINT UNSIGNED NOT NULL,
	idrol BIGINT UNSIGNED NULL,
	usuario varchar(120) NOT NULL UNIQUE,
	password varchar(15) NOT NULL,
	estatus ENUM('ACTIVO', 'INACTIVO', 'BAJA', 'CHANGE_PASS') DEFAULT 'CHANGE_PASS' NOT NULL,
	fcreacion DATETIME DEFAULT NOW() NOT NULL,
	CONSTRAINT tbusuarios_pk PRIMARY KEY (idusuario),
    CONSTRAINT tbusuarios_tbpersonal_FK FOREIGN KEY (idpersonal) 
        REFERENCES dbtickets.tbpersonal(idpersonal),
    CONSTRAINT tbusuarios_tbroles_FK FOREIGN KEY (idrol) 
        REFERENCES dbtickets.tbroles(idrol)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_spanish_ci;


CREATE TABLE dbtickets.tbtickets (
	idticket BIGINT UNSIGNED auto_increment NOT NULL,
	idusuario BIGINT UNSIGNED NOT NULL,
	titulo varchar(255) NOT NULL,
	descripcion TEXT NULL,
	modulo varchar(200) DEFAULT 'ALL' NOT NULL,
	fcreacion DATETIME DEFAULT NOW() NOT NULL,
	finicio DATETIME NULL,
	fentrega DATETIME NULL,
	hinvertidas varchar(5) DEFAULT '0 d' NULL,
	priority enum('BAJA','NORMAL','MEDIA','ALTA','CRITICA') DEFAULT 'NORMAL' NOT NULL,
	estado enum('ABIERTO', 'DESARROLLO', 'RESUELTO', 'CERRADO', 'CANCELADO') DEFAULT 'ABIERTO' NOT NULL,
	CONSTRAINT tbtickets_pk PRIMARY KEY (idticket),
	CONSTRAINT tbtickets_tbusuarios_FK FOREIGN KEY (idusuario) 
        REFERENCES dbtickets.tbusuarios(idusuario)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_spanish_ci;


CREATE TABLE dbtickets.tbticketsfile (
	idfile BIGINT UNSIGNED auto_increment NOT NULL,
	idticket BIGINT UNSIGNED NOT NULL,
	nomarchivo varchar(40) NOT NULL,
	CONSTRAINT tbticketsfile_pk PRIMARY KEY (idfile),
	CONSTRAINT tbticketsfile_tbtickets_FK FOREIGN KEY (idticket) 
        REFERENCES dbtickets.tbtickets(idticket)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_spanish_ci;


CREATE TABLE dbtickets.tbticketseg (
	idseguimiento BIGINT UNSIGNED auto_increment NOT NULL,
	idticket BIGINT UNSIGNED NOT NULL,
	idusuario BIGINT UNSIGNED NOT NULL,
	comentario TEXT NOT NULL,
	fcomentario datetime DEFAULT NOW() NOT NULL,
	CONSTRAINT tbticketseg_pk PRIMARY KEY (idseguimiento),
	CONSTRAINT tbticketseg_tbtickets_FK FOREIGN KEY (idticket) REFERENCES dbtickets.tbtickets(idticket),
	CONSTRAINT tbticketseg_tbusuarios_FK FOREIGN KEY (idusuario) REFERENCES dbtickets.tbusuarios(idusuario)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_spanish_ci;


CREATE TABLE dbtickets.tbpersonalasig (
	idpersonalasig BIGINT UNSIGNED auto_increment NOT NULL,
	idusuario BIGINT UNSIGNED NOT NULL,
	idticket BIGINT UNSIGNED NOT NULL,
	fasignacion DATETIME DEFAULT NOW() NOT NULL,
	CONSTRAINT tbpersonalasig_pk PRIMARY KEY (idpersonalasig),
    CONSTRAINT tbpersonalasig_tbusuarios_FK FOREIGN KEY (idusuario) 
        REFERENCES dbtickets.tbusuarios(idusuario),
    CONSTRAINT tbpersonalasig_tbtickets_FK FOREIGN KEY (idticket) 
        REFERENCES dbtickets.tbtickets(idticket)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_spanish_ci;




-- ::: Insertar campos necesarios -----------------
INSERT INTO dbtickets.tbpersonal (nombre, app, apm)
VALUES
('Test1', 'appTest1', 'apmTest1'),
('Test2', 'appTest2', 'apmTest2')
('Test3', 'appTest3', 'apmTest3');

INSERT INTO dbtickets.tbroles (cverol, nombre)
VALUES
('ADMSYS', 'Administrador del Sistema'),
('USRSYS', 'Usuario del Sistema'),
('REVSYS', 'Consultor del Sistema');




INSERT INTO dbtickets.tbusuarios (idpersonal, usuario, password, estatus, idrol)
VALUES
(1, 'test1@gmail.com', '123456', 'CHANGE_PASS', 1),
(2, 'test2@gmail.com', '123456', 'ACTIVO', 2),
(3, 'test3@gmail.com', '123456', 'ACTIVO', 3);


--- Para pruebas
INSERT INTO dbtickets.tbtickets
(idusuario, idasignado, titulo, descripcion, modulo, fcreacion, finicio, fentrega, hinvertidas, priority, estado)
VALUES
(2, null, 'Esto es un test1', 'Solo Test1', 'ALL', null, null, null, 'NORMAL'),
(3, null, 'Esto es un test2', 'Solo Test2', 'Contabilidad -> Reportes -> Cobranza -> Reporte Final', null, null, null, 'NORMAL');







