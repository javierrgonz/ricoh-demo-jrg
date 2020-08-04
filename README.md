# Ricoh Demo - Javier Rodriguez Gonzalez

Demo para proceso de selección de Ricoh (Agosto 2020)

- - - 

## INICIO RAPIDO

- Compile el proyecto model en la carpeta `/ricoh-demo-model/` mediante `mvnw clean install`
- Compile los servicios en la carpeta `/ricoh-demo` mediante `mvnw clean install`
- Construya los contenedores de los servicios y la base de datos `/ricoh-demo` con `docker-compose up`
- Obtenga su token de autenticación mediante la orden `curl -X POST "http://localhost:8085/oauth/token" -H "Authorization: Basic VVNFUl9DTElFTlRfQVBQOnBhc3N3b3Jk" -F "grant_type=password" -F "username=admin" -F "password=password"`
- Obtenga un listado de pedidos mediante la orden `curl -X GET "http://localhost:8084/api/pedido/" -H "accept: */*" -H "Authorization: Bearer {token}"` sustituyendo `{token}` por el obtenido en el paso anterior

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


- Documentación asociada a los ejercicios realizados. ¿Cómo se ha realizado?¿Que has usado y por qué?
- Ejemplo petición $ curl -X POST /api/pedido
- Ejemplo petición $ curl -X GET /api/pedido/{id}
- Especificar la gestión de seguridad para realizar las llamadas anteriores.
- Con maven o gradle se deberá construir y generar automáticamente el proyecto

- - -
## Solución propuesta
### Descripción de la arquitectura y resumen de ejercicio

El proyecto se ha diseñado como dos proyectos separados, incluyendo:
- Proyecto `ricoh-demo` donde se incluye el servidor de autenticación OAuth2 con JWT `ricoh-demo-auth-server` y dos resource server, uno para pedidos y otro para articulos (`ricoh-demo-articulo-server` y `ricoh-demo-pedido-server`).
- Proyecto `ricoh-demo-model`, donde se incluye la capa de persistencia comun a los resource servers de articulo y pedidos

NOTA: En adelante nos referiremos al proyecto `ricoh-demo-model` como MODEL, al proyecto padre `ricoh-demo` como PADRE, al submódulo `ricoh-demo-auth-server` como AUTH y a los submódulos `ricoh-demo-articulo-server` y `ricoh-demo-pedido-server` como ARTICULO y PEDIDO.

```
ESTRUCTURA DEL PROYECTO

|-  ricoh-demo-model (jar) -> Proyecto para acceso a datos común (MODEL)
|
|- ricoh-demo (pom) -> Proyecto padre (PARENT)
   |
   |- ricoh-demo-auth-server (jar) -> Servidor de autenticacion OAuth2 con JWT (AUTH)
   |- ricoh-demo-articulo-server (jar) -> Servidor de articulos (ARTICULO)
   |- ricoh-demo-pedido-server (jar) -> Servidor de pedidos (PEDIDO)
```

Todos los proyectos corren sobre SpringBoot 2, usando Spring Data (JPA) como capa de acceso a datos. Con el fin de mejorar la reusabilidad en el codigo y aumentar la encapsulación de los mismos, se ha diseñado un proyecto MODEL, compartido por los proyectos principales, que define las clases para el acceso a datos.

Para la securización, se ha añadido un servidor de autenticación independiente OAuth2 con JWT definido en el submodulo AUTH, a fin de establecer la independencia entre la capa de seguridad y la API Rest indicada. Este token será necesario para realizar cualquier operación sobre la API.

Como base de datos se elige MySQL acorde al enunciado del ejercicio. No obstante, para la realización de los test de integración, se ha utilizado una base de datos H2 no persistente, a fin de establecer una ejecución más realista de éstos. Dicha base de datos se autoalimenta a través de los scripts incluidos en `src/test/resources` de los proyectos ARTICULO y PEDIDO.

Finalmente, se incluye una configuración DOCKER-COMPOSE para la creación de imágenes y contenedores de los proyectos AUTH, ARTICULO y PEDIDO. Esta construcción requiere de una serie de configuraciones que se explican en el punto correspondiente. 

_NOTA: acorde a lo indicado en el enunciado del ejercicio, al obtener los pedidos s puede navegar hasta el catalogo de un articulo del pedido. Para ello se ha establecido que el fetch de la relacion ManyToMany sea de tipo EAGER mediante la anotacion `@ManyToMany(fetch=FetchType.EAGER)`_

### Compilado e instalación

Mediante el plugin (Maven Wrapper) se ha configurado la posibilidad de ejecutar la instalación de ambos modulos desde el directorio del proyecto padre. Asimismo se crea una configuración de Maven Wrapper para ambos proyectos. Deberá usarse este plugin para la construcción automática y testeo del proyecto

#### Construcción y generación de proyectos

- _NOTA: Dada la dependencia del modulo `ricoh-demo-model` es necesario construir este proyecto el primero a fin de importarlo en los demás_

- _NOTA2: El proyecto utiliza lombok para la simplificación del código. Es posible que tenga que instalarlo de forma previa en su IDE para el correcto funcionamiento de los proyectos. Para más info visite `https://projectlombok.org`_

De forma previa a la construcción del proyecto, debe configurarse el datasource a utilizar, en caso de tratarse de una ejecución en local o una ejecución a través de DOCKER. En los archivos `application.yml` de los proyectos AUTH, ARTICULO y PEDIDO, se debe comentar o descomentar la linea `url` según la finalidad de la construcción:
```
...
spring:
  datasource:
    url: jdbc:mysql://ricoh-mysql:3306/ricoh?useSSL=false  -> Configuración para uso en contenedores DOCKER
    url: jdbc:mysql://localhost:3306/ricoh?useSSL=fals     -> Configuración para ejecución en local
...
```
En caso de ejecución en local, deberá tenerse arrancada y configurada la base de datos MySQL local corriendo en el puerto 3306 con un usuario `root`:`root` con permisos sobre todos los elementos. Se incluye el script `docs/database_config_script.sql` para creación e inserción de datos de prueba, que deberá realizarse de forma previa.

Para el compilado se incluye el goal `mvnw clean install`. 
- Desde la carpeta de cada proyecto principal PARENT y MODEL, realiza un clean install del proyecto donde se ejecuta. 
- Asimismo, ejecuta los test (integración, unitarios y de rendimiento). Para evitar la ejecución de todos los test incluir el parametro `-Dmaven.test.skip=true -DskipTests=true`. 

Esto puede realizarse en cada directorio de cada modulo por separado. Como se a indicado, deberá haberse compilado y estar disponible en el repositorio el jar del proyecto MODEL.

Para la ejecución, ejecutar los archivos `.jar` creados en los directorios de cada proyecto.

#### API Rest y seguridad

El proyecto realizado consiste en dos servicios API Rest para el acceso a información de Articulos y Pedidos. Asimismo, se incluye un servidor independiente de autenticación OAuth2 con token JWT. Los endpoint expuestos se pueden consultar en la documentación SWAGGER incluida en el proyecto: `http://localhost:PORT/swagger-ui.html`. 

Se adjuntan a continuación las llamadas CURL a los distintos endpoints de los proyectos. Cabe indicar que se requiere disponer de un token de autenticación, accesible a través del servidor AUTH, con una duración establecida de 900 segundos (15 min).

Para el testeo de la seguridad, se ha restringido el acceso de algunos metodos según los permisos de usuario:

- _Usuario ADMIN_: Puede ejecutar todos los métodos
- _Usuario USER_: Solo puede ejecutar los metodos `getArticulo`, `getArticulosById`, `getPedidos`, `getPedidoById`.

Para la solicitud del token deberá usarse la autenticación Básica con datos de usuario `USER_CLIENT_APP` y contraseña `password`, indicando como método de obtención de token `password`. Los datos de los usuarios son:

| USUARIO | PASSWORD |
| ------- | -------- |
| ADMIN   | password |
| USER    | password |

Se incluye un ejemplo para cada usuario en las llamadas CURL del punto posterior.

**Llamadas curl**
  - AUTH: Obtencion token:
    - Token user ADMIN: `curl -X POST "http://localhost:{PORT}/oauth/token" -H "Authorization: Basic VVNFUl9DTElFTlRfQVBQOnBhc3N3b3Jk" -F "grant_type=password" -F "username=admin" -F "password=password"`
    - Token user USER: `curl -X POST "http://localhost:{PORT}/oauth/token" -H "Authorization: Basic VVNFUl9DTElFTlRfQVBQOnBhc3N3b3Jk" -F "grant_type=password" -F "username=user" -F "password=password"`
  - ARTICULO:
    - getArticulos: `curl -X GET "http://localhost:{PORT}/api/articulo/" -H "accept: */*" -H "Authorization: Bearer {token}"`
    - getArticulosById: `curl -X GET "http://localhost:{PORT}/api/articulo/1" -H "accept: */*" -H "Authorization: Bearer {token}"`
  - PEDIDO:
    - getPedidos: `curl -X GET "http://localhost:{PORT}/api/pedido/" -H "accept: */*" -H "Authorization: Bearer {token}"`
    - getPedidoById: `curl -X GET "http://localhost:{PORT}/api/pedido/1" -H "accept: */*" -H "Authorization: Bearer {token}"`
    - createPedido: `curl -X POST "http://localhost:{PORT}/api/pedido/" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"articulos\": [ 1 ], \"cliente\": \"CLIENTE\"}" -H "Authorization: Bearer {token}"`
    - updatePedido: `curl -X PUT "http://localhost:{PORT}/api/pedido/" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"articulos\": [ 1, 2 ], \"cliente\": \"UPDATE\", \"id\": 1}" -H "Authorization: Bearer {token}"`
    - deletePedidById: `curl -X DELETE "http://localhost:{PORT}/api/pedido/1" -H "accept: */*" -H "Authorization: Bearer {token}"`


A fin de facilitar el testeo de los distintos endpoints, se incluye una colección de request para POSTMAN, incluida en `docs/jrg_ricoh.postman_collection.json`. Tenga en cuenta que para su ejecución deberá seleccionar el entorno de pruebas (Local o Docker) dado que los puertos de los distintos servicios cambian. Esto se ha realizado así para comprobar fehacientemente que las solicitudes se hacen sobre uno u otro entorno:

| PUERTO SERVICIO | LOCALHOST | DOCKER |
| --------------- | --------- | ------ |
| ARTICULO        | 8080      | 8083   |
| PEDIDO          | 8081      | 8084   |
| ARTICULO        | 8082      | 8085   |

Para la modificación de los puertos de escucha se debe modificar el parametro `port` de los distintos `application.yml` del proyecto. Tenga en cuenta que deberá ajustar los puertos en el archivo `docker-compose.yml` acorde a los nuevos puertos locales y docker:
Por ejemplo, el servicio ARTICULO desplegado en entorno local escucha en el puerto 8080, mientras que cuando se despliega el contenedor DOCKER, escucha en el puerto 8083.

```
...
ports:
  - "{PUERTO DOCKER}:{PUERTO LOCAL}"
...
```

Un ejemplo de testeo seria:
- Obtener el token de autenticación mediante postman/curl para alguno de los usuarios a través de la llamada al servidor AUTH
- Usando el token obtenido, realizar la llamada al servicio correspondiente.

Por otro lado, tal y como se indica en el enunciado, los servicios de Pedidos son transaccionales.

### Testing

Se incluyen test unitarios, de integración y de rendimiento mediante el plugin Jmeter. Puede ejecutarlos realizando un **mvnw clean install** del proyecto.
_NOTA: Los test se incluyen en los proyectos ARTICULO y PEDIDO según el ámbito y clases de cada uno de ellos_

**Test unitarios**:

_Paquete com.jrg.ricoh.demo.test.unit_

Se ha utilizado JUnit y Mockito para la realización de los distintos test unitarios de controladores, servicios y otras clases principales. Se han mockeado las distintas clases accesorias con el fin implementar el aislamiento correspondiente de este tipo de tests. Pueden lanzarse a traves del IDE correspondiente o simplemente haciendo un _mvnw clean install_ del proyecto.

**Test de integración**:

_Paquete com.jrg.ricoh.demo.test.integrity.test_

Para la realización de los test de integración se ha utilizado una base de datos volátil H2, autoalimentada al inicio de cada ronda de test, y reiniciada después de cada test que modifique los datos (metodos CREATE, UPDATE, DELETE) mediante la anotación `@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)`. Por otro lado y para evitar la necesidad de autenticación a través de token, se ha mockeado dicha autenticación mediante el uso de la @interface `WithMockOAuth2Authority`, que permite indicar el rol con el que se desea realizar el test, a fin de incluir el testeo de la seguridad establecida. 

**Test de rendimiento**

_Folder src/main/resources/performance/_

Se incluyen los test de rendimiento diseñados mediante la herramienta JMeter. Se han probado los distintos endpoints de cada controlador según el servicio a testear. Los resultados son exportados en formato CSV a la carpeta _src/main/resources/performance/results_ de cada proyecto.

**Docker**

Se incluye una configuración `docker-compose.yml` para la construcción y despliegue de contenedores para los proyectos. Utiliza una base de datos mysql:5.6, que precarga la estructura de tablas y unos datos de ejemplo. Dicha base de datos es persistente.
Para el testeo, utilizar los puertos configurados de Docker, indicados en el apartado **llamadas curl**. Se incluye el script sql que ejecuta la base de datos al inicio en el directorio `mysql-dump`.

- - - 
Autor: Javier Rodriguez Gonzalez

Agosto 2020