# Ricoh Demo - Javier Rodriguez Gonzalez
Demo para proceso de selección de Ricoh (Agosto 2020)

## Resumen

Creación de una API REST. Con un servicio que listará pedidos, pudiendo acceder al detalle de un catalogo de cualquier articulo de un pedido
- Servicio 1 -> CRUD sobre MySQL
  - `/api/pedido` POST
  - `/api/pedido/{id}` GET
 ...

Llamada al servicio 2 para recuperar detalle
- Servicio 2 -> Retrieve sobre MySQL
  - `/api/articulo/{id}` GET

Entiendase que una Pedido es una entidad quecontine conjunto de Artículos
Entiendase que un Artículo pertenece a un Catálogo

### Requisitos

- [x] Uso de SpringBoot
- [x] Documentación API con Swagger
- [x] Acceso a datos con Spring Data
- [x] El servicio de orders es transaccional
- [x] Implementar test unitarios
- [x] Implementar test integracion (Opcional)
- [x] Integrar seguridad OAuth con Spring Security usando token JWT (Opcional)
- [x] Test de rendimiento (Opcional)
- [x] Opcional (Entrega en contenedores): $ docker-compose up que arranque:
    - service1
    - service2
    - mysql

Documentación asociada a los ejercicios realizados. ¿Cómo se ha realizado?¿Que has usado y por qué?
Ejemplo petición $ curl -X POST /api/pedido
Ejemplo petición $ curl -X GET /api/pedido/{id}
Especificar la gestión de seguridad para realizar las llamadas anteriores.
Con maven o gradle se deberá construir y generar automáticamente el proyecto

## Solución propuesta
### Descripción de la arquitectura

El proyecto se ha diseñado como dos proyectos separados, incluyendo:
- Proyecto `ricoh-demo` donde se incluye el servidor de autenticación `ricoh-demo-auth-server` y dos resource server, uno para pedidos y otro para articulos (`ricoh-demo-articulo-server` y `ricoh-demo-pedido-server`).
- Proyecto `ricoh-demo-model`, donde se incluye la capa de persistencia comun a los resource servers de articulo y pedidos

```
|-  ricoh-demo-model (jar)
|
|- ricoh-demo (pom)
   |
   |- ricoh-demo-auth-server (jar)
   |- ricoh-demo-articulo-server (jar)
   |- ricoh-demo-pedido-server (jar)
```

### Compilado e instalación

Mediante el plugin (Maven Wrapper) se ha configurado la posibilidad de ejecutar la instalación de ambos modulos desde el directorio del proyecto padre (ricoh-demo). Asimismo se crea una configuración de Maven Wrapper para ambos proyectos:
`mvn -N io.takari:maven:0.7.7:wrapper`

**Construcción y generación de ambos modulos**
- `mvnw clean install`: clean install del proyecto multimodular a ejecutar desde la carpeta del proyecto padre. Compila de forma limpia los modulos del proyecto. Asimismo, ejecuta los test (integración, unitarios y de rendimiento). Para evitar la ejecución de todos los test incluir el parametro `-Dmaven.test.skip=true -DskipTests=true`
- `mvnw verify`: goal para ejecución de testing. Se incluyen los test unitarios así como test de integración, configurado a través del plugin **surefire**

**Construcción y generación de modulos por separado**
No obstante puede ejecutar de forma independiente para cada proyecto,d dentro de cada directorio ya que cada uno tiene su maven wrapper:
- `mvnw clean install`: clean install de cada proyecto. Incluye ejecución de proyectos. Para evitar la ejecución de test incluir el parametro `-Dmaven.test.skip=true`
- `mvnw verify`: goal para ejecución de testing. Se incluyen los test unitarios así como test de integración, configurado a través del plugin **surefire**

**Ejecución**
Para la ejecución de cada modulo por separado, se puede lanzar el goal `mvn spring-boot:run`

**Base de datos**
TODO

**Testing**
Goals maven:
- `mvn verify` - test integracion y unitarios
- `mvn jmeter:jmeter` - test performance

**Docker**
- Comandos varios:
  - Ver contenedores: `docker ps -a`
  - Arrancar contenedor: `docker container start <id>`
  - Parar contenedor: `docker container stop <id>`
  - Borrar contenedor: `docker rm <id>`
  - Ver contenedores activos: `docker container ls`

- Mysql:
  - Hacer pull de la imagen de docker de mysql (en este caso la 5.6): `docker pull mysql`
  - Correrla en local: `docker run --name ricoh-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=ricoh -e MYSQL_USER=root -e MYSQL_PASSWORD=root -d mysql:5.6`. En este caso correrá la imagen de mysql como `rico-mysql` en un nuevo contenedor

- Crear Dockerfiles:
  - Auth server:
    ```
    FROM openjdk:8-jdk-alpine
    ADD target/ricoh-demo-auth-server.jar ricoh-demo-auth-server.jar
    EXPOSE 8081
    ENTRYPOINT ["java","-jar","ricoh-demo-auth-server.jar"]
    ```
    - Resource server:
    ```
    FROM openjdk:8-jdk-alpine
    ADD target/ricoh-demo-resource-server.jar ricoh-demo-resource-server.jar
    EXPOSE 8080 
    ENTRYPOINT ["java","-jar","ricoh-demo-resource-server.jar"]
    ```
  - Build de cada uno: 
    - `docker build -t javierrodriguezgonzalez/ricoh-demo-resource-server .`
    - `docker build -t javierrodriguezgonzalez/ricoh-demo-auth-server .`

  - Uso de docker compose: `docker-compose up` 
  
- swagger UI: http://localhost:8080/swagger-ui.html