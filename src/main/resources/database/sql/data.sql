-- Catégories
INSERT INTO categorie (id, libelle)
VALUES (1, 'Roman'),
       (2, 'Science'),
       (3, 'Histoire');

-- Livres avec résumés
INSERT INTO livre (id, reference, titre, auteur, resume)
VALUES (1, 'REF001', 'Le Petit Prince', 'Antoine de Saint-Exupéry', 'Un aviateur s''écrase dans le désert et rencontre un petit prince venu d''une autre planète. Un conte poétique sur l''amitié, l''amour et la découverte de soi.'),
       (2, 'REF002', '1984', 'George Orwell', 'Dans un monde totalitaire, Winston Smith tente de résister à la surveillance et à la manipulation du Parti. Un roman dystopique sur la liberté et la vérité.'),
       (3, 'REF003', 'Brève histoire du temps', 'Stephen Hawking', 'Une introduction accessible aux mystères de l''univers, du Big Bang aux trous noirs, par le célèbre physicien Stephen Hawking.'),
       (4, 'REF004', 'L''Étranger', 'Albert Camus', 'Meursault, un homme détaché, commet un meurtre en Algérie. Un roman existentialiste sur l''absurdité de la vie.'),
       (5, 'REF005', 'Secrets d’Adultes', 'Auteur Mature', 'Un roman contemporain explorant les secrets, les choix et les regrets de la vie adulte.'),
       (6, 'REF006', 'La Vie à 30 Ans', 'Auteur Expérimenté', 'Un regard sincère et humoristique sur les défis et les espoirs de la trentaine.');

-- Liens livre-catégorie
INSERT INTO categorie_livre (livre_id, categorie_id)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (4, 1),
       (4, 3);

-- Statuts de disponibilité
INSERT INTO statut_disponibilite (id, libelle)
VALUES (1, 'disponible'),
       (2, 'réservé'),
       (3, 'emprunté');

-- Exemplaires
INSERT INTO exemplaire (id, livre_id, statut_id)
VALUES (1, 1, 1), -- Le Petit Prince, disponible
       (2, 1, 1)
        ,         -- Le Petit Prince, disponible
       (3, 2, 1)
        ,         -- 1984, disponible
       (4, 3, 2)
        ,         -- Brève histoire du temps, réservé
       (5, 4, 1)
        ,         -- L'Étranger, disponible
       (6, 5, 1), -- Secrets d’Adultes, disponible
       (7, 6, 1);
-- La Vie à 30 Ans, disponible


-- Adhérent de test (personne + adhérent)
INSERT INTO personne (nom, prenom, email, adresse, date_naissance)
VALUES ('Test', 'Penalise', 'penalise@test.com', 'Adresse test', '2000-01-01');

INSERT INTO adherent (personne_id, numero_adherent, mot_de_passe, type_personne_id, date_inscription)
VALUES (4, 'A0001', 'mdp', 1, '2024-01-01');

-- Pénalité active pour cet adhérent
INSERT INTO penalite (personne_id, date_debut, date_fin)
VALUES (4, CURRENT_DATE - INTERVAL '2 day', CURRENT_DATE + INTERVAL '2 day');

-- Restrictions d'âge minimum 30 ans
INSERT INTO restriction_livre (livre_id, age_minimum)
VALUES (5, 30),
       (6, 30);
