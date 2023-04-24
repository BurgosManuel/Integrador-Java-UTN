-- Borro bd si existe

Drop database if exists pronostico;

-- Creo BD

create database pronostico;

-- Uso BD pronostico
USE Pronostico;

-- Creo tabla personas
create table Personas(
	idPersona int NOT NULL auto_increment,
    Nombre Char(100) not null,
    primary key(idPersona)
);

-- Creo tabla Apuestas

Create table Apuestas(
	idApuesta int not null auto_increment,
    equipo1 char(25) not null,
    ganaequipo1 char(1),
    empate char(1),
    ganaequipo2 char(1),
    equipo2 char(25) not null,
    idPersona int not null,
    Primary key(idApuesta),
    foreign key(idPersona) references Personas (idPersona) 
);

