SELECT P.NOMBRE_PERSONA,PRO.EQUIPO_UNO, PRO.EQUIPO_DOS, PRO.GANA_UNO, PRO.EMPATE, PRO.GANA_DOS
FROM PROYECTO_UTN.PERSONA AS P, PROYECTO_UTN.PRONOSTICO AS PRO
WHERE P.ID = PRO.ID_PERSONA;
