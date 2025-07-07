-- Types de personnes
INSERT INTO type_personne (id, libelle)
VALUES (1, 'etudiant'),
       (2, 'enseignant'),
       (3, 'professionnel');

-- Personnes
INSERT INTO personne (nom, prenom, email, adresse, date_naissance)
VALUES ('Rakoto', 'Jean', 'rakoto.jean@biblio.com', 'Antananarivo', '2000-01-01'),
       ('Rabe', 'Marie', 'rabe.marie@biblio.com', 'Fianarantsoa', '2001-02-01'),
       ('Rasoa', 'Anne', 'rasoa.anne@biblio.com', 'Antsirabe', '2000-03-01');

-- Bibliothécaires
INSERT INTO bibliothecaire (personne_id, matricule, mot_de_passe, date_embauche)
VALUES (1, 'BIB001', 'password123', '2023-01-10'),
       (2, 'BIB002', 'admin456', '2024-03-15');

INSERT INTO statut_pret (libelle)
VALUES ('en cours'),
       ('rendu'),
       ('en prolongement');

INSERT INTO statut_validation (libelle)
VALUES ('en cours de validation'),
       ('acceptée'),
       ('refusée'),
       ('prévu');

INSERT INTO quota (type_personne_id, nombre_livre_pret, nombre_livre_prolongement, nombre_jour_penalite,
                   nombre_jour_pret, nombre_jour_prolongement)
VALUES (1, 3, 3, 5, 10, 5),
       (2, 5, 5, 10, 15, 5),
       (3, 4, 3, 10, 15, 5);

INSERT INTO configuration (nom, valeur)
VALUES ('alternatif_jour_ferie', 1), -- 1 pour prendre le jour d'après, -1 pour le jour avant
       ('weekend', 1);

-- Jours fériés récurrents (exemple : 1er mai, 25 décembre)
INSERT INTO jour_ferie (date_ferie, description, is_recurrent, mois, jour)
VALUES ('2025-01-01', 'Jour de l An', true, 1, 1),
       ('2025-05-01', 'Fête du Travail', true, 5, 1),
       ('2025-05-08', 'Victoire 1945', true, 5, 8),
       ('2025-06-26', 'Fête Nationale', true, 6, 26),
       ('2025-08-15', 'Assomption', true, 8, 15),
       ('2025-12-25', 'Noël', true, 12, 25);
