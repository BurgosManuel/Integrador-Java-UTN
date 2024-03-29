## Proyecto Integrador - Curso Java Inicial (UTN)

### Integrantes Grupo 1:

- Burgos Manuel
- Perla Gustavo

<hr>

### Introducción
Nos han solicitado el desarrollo de un programa de Pronósticos Deportivos.
Un pronóstico deportivo consta de un posible resultado de un partido (que un equipo gane,pierda o empate), propuesto por una persona que está participando de una competencia contra otras.

Cada partido tendrá un resultado. Este resultado se utilizará para otorgar puntos a los
participantes de la competencia según el acierto de sus pronósticos.

Finalmente, quien gane la competencia será aquella persona que sume mayor cantidad de puntos.

<hr>

### Consigna
La propuesta del trabajo práctico consiste en implementar un programa de consola que dada la información de resultados de partidos e información de pronósticos, ordene por puntaje obtenido a los participantes.

<hr>

### Alcance
En este trabajo práctico nos limitaremos a pronosticar los resultados de los partidos, sin
importar los goles ni la estructura del torneo (si es grupo, eliminatoria u otro); simplmente se sumarán puntos y se obtendrá un listado final.

<hr>

## Ejecutar el programa

1. Clonamos el proyecto haciendo uso del comando `git clone`.
2. Abrimos nuestro IDE de preferencia (IntelliJ IDEA, Eclipse, etc. ) e instalamos las dependencias.
3. Nuestra clase "Main" debe tener los siguientes argumentos:
   `src\\main\\resources\\partidos.csv src\\main\\resources\\configuracion.json`
   De esta forma podemos probar el programa con las conexiones a la DB y partidos de prueba correspondientes. En caso de querer utlizar otros datos debemos modificar estos 2 archivos.
4. Ejecutar el programa.

Ejemplo de resultado:
![Ejemplo resultado](https://i.imgur.com/Jcw7T8n.jpg)
