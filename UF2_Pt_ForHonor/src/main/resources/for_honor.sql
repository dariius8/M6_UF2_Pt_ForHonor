CREATE DATABASE IF NOT EXISTS ForHonor;

CREATE TABLE IF NOT EXISTS Faccion
(
	faccion_id integer PRIMARY KEY, 
	nombre_faccion varchar(15), 
	lore varchar(200)
);
CREATE TABLE IF NOT EXISTS Personaje
(
	personaje_id integer PRIMARY KEY, 
	nombre_personaje varchar(15), 
	ataque integer, 
	defensa integer, 
	faccion_id integer, 
	FOREIGN KEY (faccion_id) REFERENCES Faccion(faccion_id)
);

INSERT INTO Faccion VALUES (1, "Caballeros", "Los caballeros de Ashfeld son paradigmas del poder");
INSERT INTO Faccion VALUES (2, "Vikingos", "Los vikingos son los maestros indiscutibles del mar y el agua");
INSERT INTO Faccion VALUES (3, "Samurais", "Los samurais son la faccion mas unificada de las tres");

INSERT INTO Personaje VALUES (1, "Guardian", 3, 8, 1);
INSERT INTO Personaje VALUES (2, "Conquistador", 9, 3, 1);
INSERT INTO Personaje VALUES (3, "Pacificadora", 6, 6, 1);
INSERT INTO Personaje VALUES (4, "Huscarle", 4, 7, 2);
INSERT INTO Personaje VALUES (5, "Berserker", 7, 5, 2);
INSERT INTO Personaje VALUES (6, "Shinnobi", 5,9, 3);
INSERT INTO Personaje VALUES (7, "Aramusha", 8, 4, 3);

DELIMITER //
    CREATE PROCEDURE Change_Faction
        (IN `id_personaje` int(11), IN `id_faccion_destino` int(11))
        BEGIN
            UPDATE Personaje SET faccion_id = id_faccion_destino
            WHERE personaje_id = id_personaje;
    END;
//