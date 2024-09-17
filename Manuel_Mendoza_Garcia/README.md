José Manuel Mendoza García

El presente documento pretende recabar los conocimientos aprendidos a los largo del curso Bloque 2 Java de Wizeline

Semana 1: 
    - Aprendizaje de comando basicos de Git para versionamiento de código
    - Levantamiento y configuración de microservicios usando Docker
    - Consumo de servicios RestApi a una API Publica
    - Creacion de función Lamba en AWS así como el monitoreo de esta usando CloudWatch
Semana 2:
    - Uso de Kafka para servicio de mensajeria basado en productor y consumidor
    - Operaciones CRUD usando MongoDB

Entregable 1

    -Dicho entregable contempla las practicas vistas a lo largo de las semanas 1 y 2 (GIT, DOCKER, SERVERLESS, AWS, MONGO, KAFKA)

Semana 3:
    Dicha semana estuvo enfocada en aprender en Java los siguientes conceptos
    - Programación Orientada a Objetos
    - Métodos de Cifrado
    - Anotaciones
    - Colecciones
    - Concurrencia/Hilos
    - Expresiones Regulares
    - Excepciones
    - Fechas y Tiempos
    - Funcional
    - Genericos
    - Optional
 
Semana 4:
    Esta semana estuvo enfoncada en ver SpringBoot donde los temás más relevantes fueron
    - Actuators
    - MVC
    - Security
    - JPA

Entregable 2 
    Para el entregable 2 se contempla la entrega de un proyecto generado en spring initializr dicho proyecto debe de cumplir ciertas metricas. A continuación se lista en donde fueron cubiertas y el correcto funcionamiento del proyecto.

    - Security: Para la parte de seguridad se implementaron 2 roles "ADMIN" y "USER", así mismo, se expuso el endpoint Authenticate para poder generar el token Bearer. En la colección de Postman se encuentran separados por carpetas los endpoints con respecto a su nivel de seguridad (por rol o expuestos)

    - Actuators: Para la parte de Actuators este endpoint (LearningJava/Actuator) esta expuesto y consumiendolo pueden listarse los demás (info, metrics, loggers, health)

    -JPA: Para la parte de Mongo se listan los siguientes endpoints para el cumplimiento de dicho punto
        - Escritura: LearningJava/getAccounts este endpoint realiza la escritura de cuentas en la BD, no esta protegido y solo basta con consumirlo
        - Eliminacion: LearningJava/deleteAccounts este endpoint realiza la eliminación de todas las cuentas en la BD, esta protegido con el rol "ADMIN" por lo que es necesario pasarle el bearer token correspondiente
        - Lectura y Escritura: LearningJava/updateUserAccounts este endpoint realiza primero la lectura (find) y despues la actualización (update) de los registros de la BD, para funcionar, recibe 2 parametros (oldUser y newUser) donde las cuentas de oldUser pasan a ser de newUser (se actualiza el userName)

    -MVC: Para la parte de MVC en la colección de Postman se encuentran distintos enpoints de cada tipo ejemplo:
        - GET: LearningJava/getAccounts
        - POST: LearningJava/createUsers, LearningJava/createDefaultUsers
        - PUT: LearningJava/updateUserAccounts
        - DELETE:LearningJava/deleteAccounts

    -RestTemplate y JAXB: Para esta parte del proyecto se expone el endpoint LearningJava/getXMLApiPublica donde se consume una ApiPublica y convierte el resultado en un xml. Para poder utilizarlo solo se necesita enviar un id

    -Cifrado: El cifrado se encuentra implementado en la respuesta del endpoint LearningJava/getAccounts especificamente en los campos accountName y country

    - Stream Datos: Se pueden encontrar 2 Streams de datos y 2 collectores (BankingAccountController/AuthenticationController)

    -Interfaz Funcional, Función Lambda: Se puede encontrar un uso en el metodo getAccountsGroupByType del controlador BankingAccountController

    - Dato Generico: Se puede encontrar un ejemplo en la clase Utils al consumir el endpoint LearningJava/SayHello

    - Optional: Se puede encontrar un ejemplo al usar el endpoint LearningJava/getAccountsByUser al no indicar un user

    - Anotaciones: Se pueden encontrar distintas anotaciones en el proyecto

    - Threads: Se puede utilizar el endpoint LearningJava/createDefaultUsers parar poder generar usuarios usando concurrencia

    - Api Fecha y Tiempos: Del mismo modo se puede observar en los Logs el uso de esta API en distintos endpoints

    - Lista, Mapa, Arreglo - Se pueden encontrar el uso de estos a lo largo del proyecto

    - Expresión Regular: En la clase Utils se puede encontrar el uso de expresiones regulares