CREATE DATABASE IF NOT EXISTS ricoh;
USE ricoh;

CREATE TABLE oauth_client_details (
client_id VARCHAR(255) NOT NULL PRIMARY KEY,
client_secret VARCHAR(255) NOT NULL,
resource_ids VARCHAR(255) DEFAULT NULL,
scope VARCHAR(255) DEFAULT NULL,
authorized_grant_types VARCHAR(255) DEFAULT NULL,
web_server_redirect_uri VARCHAR(255) DEFAULT NULL,
authorities VARCHAR(255) DEFAULT NULL,
access_token_validity INT(11) DEFAULT NULL,
refresh_token_validity INT(11) DEFAULT NULL,
additional_information VARCHAR(4096) DEFAULT NULL,
autoapprove VARCHAR(255) DEFAULT NULL
);

CREATE TABLE user ( 
id INT PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(24) UNIQUE KEY NOT NULL,
password VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL,
enabled BIT(1) NOT NULL,
account_expired BIT(1) NOT NULL,
credentials_expired BIT(1) NOT NULL,
account_locked BIT(1) NOT NULL
);

CREATE TABLE role (
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(60) UNIQUE KEY
);

CREATE TABLE permission ( 
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(60) UNIQUE KEY
);

CREATE TABLE role_user (
role_id INT,
FOREIGN KEY(role_id) REFERENCES role(id),
user_id INT,
FOREIGN KEY(user_id) REFERENCES user(id));

CREATE TABLE permission_role( 
permission_id INT,
FOREIGN KEY(permission_id) REFERENCES permission(id),
role_id INT,
FOREIGN KEY(role_id) REFERENCES role(id));

INSERT INTO	oauth_client_details ( 
client_id,
client_secret,
resource_ids,
scope,
authorized_grant_types,
web_server_redirect_uri,
authorities,
access_token_validity,
refresh_token_validity,
additional_information,
autoapprove
) VALUES (
'USER_CLIENT_APP',
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

INSERT INTO	user ( 
username,
password,
email,
enabled,
account_expired,
credentials_expired,
account_locked
) VALUES ( 
	'admin',
	'{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi', -- password
	'admin@gmail.com',
	1,
	0,
	0,
	0
), (
	'user',
	'{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi', -- password
	'user@gmail.com',
	1,
	0,
	0,
	0
);

INSERT INTO	role (name)
VALUES ('role_admin'),('role_user');

INSERT INTO	permission (name)
VALUES ('can_create_user'),
('can_update_user'),
('can_read_user'),
('can_delete_user');

INSERT INTO	role_user (role_id, user_id)
VALUES 
(1,1)/* role_admin assigned to admin user */
,(2,2)/* role_user assigned to user user */;

INSERT INTO	permission_role (permission_id, role_id)
VALUES (1,1),
/* can_create_user assigned to role_admin */
(2,1),
/* can_update_user assigned to role_admin */
(3,1),
/* can_read_user assigned to role_admin */
(4,1),
/* can_delete_user assigned to role_admin */
(3,2);

/* 
 * ARTICULOS Y PEDIDOS 
 */
-- Volcando estructura para tabla ricoh.catalogo
CREATE TABLE IF NOT EXISTS `catalogo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

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

-- Volcando datos para la tabla ricoh.catalogo: ~3 rows (aproximadamente)
INSERT INTO `catalogo` (`id`, `descripcion`) VALUES
	(1, 'FRUTA'),
	(2, 'ROPA'),
	(3, 'TECNOLOGIA');

-- Volcando datos para la tabla ricoh.pedido: ~3 rows (aproximadamente)
INSERT INTO `pedido` (`id`, `cliente`) VALUES
	(1, 'LUIS'),
	(2, 'JUAN'),
	(3, 'MANOLO');

-- Volcando datos para la tabla ricoh.articulo: ~10 rows (aproximadamente)
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

-- Volcando datos para la tabla ricoh.pedido_articulo: ~8 rows (aproximadamente)
INSERT INTO `pedido_articulo` (`id`, `id_pedido`, `id_articulo`) VALUES
	(1, 1, 10),
	(3, 1, 8),
	(4, 1, 3),
	(5, 3, 3),
	(6, 3, 7),
	(7, 3, 9),
	(8, 2, 6),
	(11, 2, 1);