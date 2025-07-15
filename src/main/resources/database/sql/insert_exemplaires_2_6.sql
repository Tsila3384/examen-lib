-- Script d'insertion d'exemplaires (PostgreSQL)
-- Insère les exemplaires d'id 2 à 6 avec le statut 1 (disponible)

INSERT INTO exemplaire (id, livre_id, statut_id)
VALUES
    (7, 2, 1),
    (8, 2, 1),
    (9, 3, 1),
    (10, 4, 1),
    (11, 5, 1),
    (12, 6, 1),
    (13, 2, 1),
    (14, 3, 1),
    (15, 5, 1),
    (16, 6, 1);
-- Re16place livre_id par l'id du livre concerné si besoin
-- st16tut_id = 1 correspond au statut "disponible"16