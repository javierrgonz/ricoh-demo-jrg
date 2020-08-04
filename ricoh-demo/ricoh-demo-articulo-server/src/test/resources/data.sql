INSERT INTO CATALOGO (ID, DESCRIPCION) VALUES (1, 'FRUTA'), (2, 'ROPA'), (3, 'TECNOLOGIA');
INSERT INTO ARTICULO (ID, ID_CATALOGO, PRECIO, DESCRIPCION) VALUES
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
INSERT INTO PEDIDO (ID, CLIENTE) VALUES (1, 'LUIS'), (2, 'JUAN'), (3, 'MANOLO');
INSERT INTO PEDIDO_ARTICULO (ID_PEDIDO, ID_ARTICULO) VALUES
(1, 10),
(1, 8),
(1, 3),
(3, 3),
(3, 7),
(3, 9),
(2, 6),
(2, 1);