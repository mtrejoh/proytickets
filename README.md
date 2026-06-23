:::: Proyecto Tickets ::::
Este proyecto fue creado como una aplicación sencilla
debido al exceso de tiempo libre, pero que puede ayudar
a equipos a un seguimiento en sus tareas a desarrollar
en una empresa.

Comentar que es ampliamente mejorable, falta agregar procesos
como Seguridad, tokens, bean validation, etc.

PEro es funcional para un requerimiento sencillo.

Author 
   Moises Trejo Hernandez
   mtrejoh@gmail.com


Este proyecto esta hecho en dos partes:
    Front: Angular 17
    Back: Java 17
    BD: MySql 8.0.46


Nota: Para conectar el front con el Back busca el
componente api.config.ts y cambia la variable:
baseUrl: 'http://localhost:8080'

Por el valor, url o IP que requieras.

en el BACK busca el resource application.properties y cambia los valores
a) La ip, host, dns de tu BD y el nombre de la Base 
b) Cambia desde los corchetes y pon el usuario
c) En el campo Password has lo mismo que con el usuario.

spring.datasource.url=jdbc:mysql://[localhost:3306/dbtickets]
spring.datasource.username=[Aqui va el usuario]
spring.datasource.password=[Aqui va el password]

