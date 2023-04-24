use pronostico;

select p.nombre,a.equipo1,a.ganaEquipo1,a.empate,a.ganaEquipo2,a.equipo2
From Personas As p, Apuestas as a
where p.idPersona=A.idPersona; 