-- Creo BD si no existe

CREATE DATABASE IF NOT EXISTS PROYECTO_UTN;

USE PROYECTO_UTN;

-- Creamos tabla PERSONA
CREATE TABLE PERSONA(
    ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    NOMBRE_PERSONA VARCHAR(50) NOT NULL
);

-- Creo tabla PRONOSTICO

CREATE TABLE PRONOSTICO(
	ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	ID_PERSONA INT NOT NULL,
    EQUIPO_UNO VARCHAR(100) NOT NULL,
    EQUIPO_DOS VARCHAR(100) NOT NULL,
    GANA_UNO CHAR(1) CHECK(GANA_UNO = "X" OR GANA_UNO = ""),
    EMPATE CHAR(1) CHECK(EMPATE = "X" OR EMPATE = ""),
    GANA_DOS CHAR(1) CHECK(GANA_DOS = "X" OR GANA_DOS = ""),
    FOREIGN KEY (ID_PERSONA) REFERENCES PERSONAS (ID)
);
