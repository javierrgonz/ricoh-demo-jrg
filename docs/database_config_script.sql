/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Volcando estructura de base de datos para ricoh
CREATE DATABASE IF NOT EXISTS `ricoh` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ricoh`;

-- Volcando estructura para tabla ricoh.articulo
CREATE TABLE IF NOT EXISTS `articulo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_catalogo` int(11) NOT NULL DEFAULT '0',
  `precio` int(11) NOT NULL DEFAULT '0',
  `descripcion` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_articulo_catalogo` (`id_catalogo`),
  CONSTRAINT `FK_articulo_catalogo` FOREIGN KEY (`id_catalogo`) REFERENCES `catalogo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- Volcando estructura para tabla ricoh.catalogo
CREATE TABLE IF NOT EXISTS `catalogo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Volcando estructura para tabla ricoh.pedido
CREATE TABLE IF NOT EXISTS `pedido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cliente` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Volcando estructura para tabla ricoh.pedido_articulo
CREATE TABLE IF NOT EXISTS `pedido_articulo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_pedido` int(11) NOT NULL DEFAULT '0',
  `id_articulo` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_pedido_articulo_pedido` (`id_pedido`),
  KEY `FK_pedido_articulo_articulo` (`id_articulo`),
  CONSTRAINT `FK_pedido_articulo_articulo` FOREIGN KEY (`id_articulo`) REFERENCES `articulo` (`id`),
  CONSTRAINT `FK_pedido_articulo_pedido` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

-- Volcando datos para la tabla ricoh.catalogo: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `catalogo` DISABLE KEYS */;
INSERT INTO `catalogo` (`id`, `descripcion`) VALUES
	(1, 'FRUTA'),
	(2, 'ROPA'),
	(3, 'TECNOLOGIA');
/*!40000 ALTER TABLE `catalogo` ENABLE KEYS */;

-- Volcando datos para la tabla ricoh.pedido: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` (`id`, `cliente`) VALUES
	(1, 'LUIS'),
	(2, 'JUAN'),
	(3, 'MANOLO');
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;

-- Volcando datos para la tabla ricoh.articulo: ~10 rows (aproximadamente)
/*!40000 ALTER TABLE `articulo` DISABLE KEYS */;
INSERT INTO `articulo` (`id`, `id_catalogo`, `precio`, `descripcion`) VALUES
	(1, 1, 2, 'PERA'),
	(2, 1, 5, 'MANZANA'),
	(3, 2, 10, 'CAMISETA'),
	(4, 2, 20, 'PANTALON'),
	(5, 3, 100, 'MOVIL'),
	(6, 3, 200, 'TV'),
	(7, 1, 6, 'SANDIA'),
	(8, 2, 100, 'ABRIGO'),
	(9, 1, 10, 'MELOCOTON'),
	(10, 3, 500, 'PC');
/*!40000 ALTER TABLE `articulo` ENABLE KEYS */;

-- Volcando datos para la tabla ricoh.pedido_articulo: ~8 rows (aproximadamente)
/*!40000 ALTER TABLE `pedido_articulo` DISABLE KEYS */;
INSERT INTO `pedido_articulo` (`id`, `id_pedido`, `id_articulo`) VALUES
	(1, 1, 10),
	(3, 1, 8),
	(4, 1, 3),
	(5, 3, 3),
	(6, 3, 7),
	(7, 3, 9),
	(8, 2, 6),
	(11, 2, 1);
/*!40000 ALTER TABLE `pedido_articulo` ENABLE KEYS */;

CREATE DATABASE IF NOT EXISTS RICOH;
use RICOH;

DROP TABLE IF EXISTS OAUTH_CLIENT_DETAILS;
DROP TABLE IF EXISTS ROLE_USER;
DROP TABLE IF EXISTS PERMISSION_ROLE;
DROP TABLE IF EXISTS PERMISSION;
DROP TABLE IF EXISTS ROLE;
DROP TABLE IF EXISTS USER;

CREATE TABLE OAUTH_CLIENT_DETAILS ( CLIENT_ID VARCHAR(255) NOT NULL PRIMARY KEY,
CLIENT_SECRET VARCHAR(255) NOT NULL,
RESOURCE_IDS VARCHAR(255) DEFAULT NULL,
SCOPE VARCHAR(255) DEFAULT NULL,
AUTHORIZED_GRANT_TYPES VARCHAR(255) DEFAULT NULL,
WEB_SERVER_REDIRECT_URI VARCHAR(255) DEFAULT NULL,
AUTHORITIES VARCHAR(255) DEFAULT NULL,
ACCESS_TOKEN_VALIDITY INT(11) DEFAULT NULL,
REFRESH_TOKEN_VALIDITY INT(11) DEFAULT NULL,
ADDITIONAL_INFORMATION VARCHAR(4096) DEFAULT NULL,
AUTOAPPROVE VARCHAR(255) DEFAULT NULL);

/* can_read_user assigned to role_user */
CREATE TABLE USER ( ID INT PRIMARY KEY AUTO_INCREMENT,
USERNAME VARCHAR(24) UNIQUE KEY NOT NULL,
PASSWORD VARCHAR(255) NOT NULL,
EMAIL VARCHAR(255) NOT NULL,
ENABLED BIT(1) NOT NULL,
ACCOUNT_EXPIRED BIT(1) NOT NULL,
CREDENTIALS_EXPIRED BIT(1) NOT NULL,
ACCOUNT_LOCKED BIT(1) NOT NULL);

CREATE TABLE ROLE (ID INT PRIMARY KEY AUTO_INCREMENT,
NAME VARCHAR(60) UNIQUE KEY);

CREATE TABLE PERMISSION ( ID INT PRIMARY KEY AUTO_INCREMENT,
NAME VARCHAR(60) UNIQUE KEY);

CREATE TABLE ROLE_USER (ROLE_ID INT,
FOREIGN KEY(ROLE_ID) REFERENCES ROLE(ID),
USER_ID INT,
FOREIGN KEY(USER_ID) REFERENCES USER(ID));

CREATE TABLE PERMISSION_ROLE( PERMISSION_ID INT,
FOREIGN KEY(PERMISSION_ID) REFERENCES PERMISSION(ID),
ROLE_ID INT,
FOREIGN KEY(ROLE_ID) REFERENCES ROLE(ID));

INSERT
	INTO
	OAUTH_CLIENT_DETAILS ( CLIENT_ID,
	CLIENT_SECRET,
	RESOURCE_IDS,
	SCOPE,
	AUTHORIZED_GRANT_TYPES,
	WEB_SERVER_REDIRECT_URI,
	AUTHORITIES,
	ACCESS_TOKEN_VALIDITY,
	REFRESH_TOKEN_VALIDITY,
	ADDITIONAL_INFORMATION,
	AUTOAPPROVE)
VALUES( 'USER_CLIENT_APP',
'{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi',
'USER_CLIENT_RESOURCE,USER_ADMIN_RESOURCE',
'role_admin,role_user',
'authorization_code,password,refresh_token,implicit',
NULL,
NULL,
900,
3600,
'{}',
NULL);

INSERT
	INTO
	USER ( USERNAME,
	PASSWORD,
	EMAIL,
	ENABLED,
	ACCOUNT_EXPIRED,
	CREDENTIALS_EXPIRED,
	ACCOUNT_LOCKED)
VALUES ( 'admin',
'{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi', -- password
'admin@gmail.com',
1,
0,
0,
0),
('user',
'{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi', -- password
'user@gmail.com',
1,
0,
0,
0);

INSERT
	INTO
	ROLE (NAME)
VALUES ('role_admin'),
('role_user');

INSERT
	INTO
	PERMISSION (NAME)
VALUES ('can_create_user'),
('can_update_user'),
('can_read_user'),
('can_delete_user');

INSERT
	INTO
	ROLE_USER (ROLE_ID,
	USER_ID)
VALUES (1,
1)/* role_admin assigned to admin user */
,
(2,
2)/* role_user assigned to user user */;

INSERT
	INTO
	PERMISSION_ROLE (PERMISSION_ID,
	ROLE_ID)
VALUES (1,
1),
/* can_create_user assigned to role_admin */
(2,
1),
/* can_update_user assigned to role_admin */
(3,
1),
/* can_read_user assigned to role_admin */
(4,
1),
/* can_delete_user assigned to role_admin */
(3,
2);