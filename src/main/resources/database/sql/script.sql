-- Table des types de personnes 
CREATE TABLE type_personne
(
    id      SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL -- etudiant, enseignant, professionnel
);

-- Table des personnes (toute personne pouvant utiliser la bibliothèque)
CREATE TABLE personne
(
    id             SERIAL PRIMARY KEY,
    nom            VARCHAR(100) NOT NULL,
    prenom         VARCHAR(100) NOT NULL,
    email          VARCHAR(150) UNIQUE,
    adresse        TEXT,
    date_naissance DATE
);

-- Table des bibliothécaires
CREATE TABLE bibliothecaire
(
    personne_id   INTEGER PRIMARY KEY REFERENCES personne (id) ON DELETE CASCADE,
    matricule     VARCHAR(50) UNIQUE NOT NULL,
    mot_de_passe  VARCHAR(255)       NOT NULL,
    date_embauche DATE               NOT NULL DEFAULT CURRENT_DATE
);

-- Table des adhérents (uniquement les personnes inscrites)
CREATE TABLE adherent
(
    personne_id      INTEGER PRIMARY KEY REFERENCES personne (id) ON DELETE CASCADE,
    numero_adherent  VARCHAR(20) UNIQUE NOT NULL,
    mot_de_passe     VARCHAR(255)       NOT NULL,
    type_personne_id INTEGER            NOT NULL REFERENCES type_personne (id),
    date_inscription DATE               NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE categorie
(
    id      SERIAL PRIMARY KEY,
    libelle VARCHAR(255)
);

-- Table des livres
CREATE TABLE livre
(
    id        SERIAL PRIMARY KEY,
    reference VARCHAR(30) UNIQUE NOT NULL,
    titre     VARCHAR(255)       NOT NULL,
    auteur    VARCHAR(255),
    resume    TEXT
);

-- un livre pourrait appartenir à plusieurs catégories et inversement.
CREATE TABLE categorie_livre
(
    livre_id     INTEGER NOT NULL REFERENCES livre (id) ON DELETE CASCADE,
    categorie_id INTEGER NOT NULL REFERENCES categorie (id) ON DELETE CASCADE
);

CREATE TABLE restriction_livre
(
    id               SERIAL PRIMARY KEY,
    livre_id         INTEGER NOT NULL REFERENCES livre (id),
    public_restreint INTEGER NULL REFERENCES type_personne (id), -- exemple restreint aux étudiants (nullable si on vise une age)
    age_minimum      INTEGER NULL                                -- nullable si on vise un public
);

CREATE TABLE statut_disponibilite
(
    id      SERIAL PRIMARY KEY,
    libelle VARCHAR(100) -- 'disponible', 'réservé', 'emprunté'
);

-- Table des exemplaires
CREATE TABLE exemplaire
(
    id        SERIAL PRIMARY KEY,
    livre_id  INTEGER NOT NULL REFERENCES livre (id) ON DELETE CASCADE,
    statut_id INTEGER NOT NULL REFERENCES statut_disponibilite (id)
);

CREATE TABLE statut_exemplaire_livre
(
    id            SERIAL PRIMARY KEY,
    exemplaire_id INTEGER,
    date_fin      TIMESTAMP,
    statut_id     INTEGER NOT NULL REFERENCES statut_disponibilite (id)
);

CREATE TABLE abonnement
(
    id              SERIAL PRIMARY KEY,
    personne_id     INTEGER   NOT NULL REFERENCES personne (id),
    date_abonnement TIMESTAMP NOT NULL,
    date_effet      TIMESTAMP NOT NULL,
    date_expiration TIMESTAMP NOT NULL
);


CREATE TABLE quota
(
    id                        SERIAL PRIMARY KEY,
    type_personne_id          INTEGER NOT NULL REFERENCES type_personne (id),
    nombre_livre_pret         INTEGER, -- à emporter ihany no misy quota
    nombre_livre_prolongement INTEGER,
    nombre_jour_penalite      INTEGER,
    nombre_jour_pret          INTEGER,
    nombre_jour_prolongement  INTEGER
);

CREATE TABLE statut_pret
(
    id      SERIAL PRIMARY KEY,
    libelle VARCHAR(50) -- en cours, rendu
);

CREATE TABLE statut_validation
(
    id      SERIAL PRIMARY KEY,
    libelle VARCHAR(100) -- en cours de validation, acceptée, refusée, prévu (pour une réservation)
);

-- Table des prêts
CREATE TABLE pret
(
    id                 SERIAL PRIMARY KEY,
    exemplaire_id      INTEGER     NOT NULL REFERENCES exemplaire (id) ON DELETE CASCADE,
    adherent_id        INTEGER     NOT NULL REFERENCES adherent (personne_id) ON DELETE CASCADE,
    type_pret          VARCHAR(20) NOT NULL CHECK (type_pret IN ('sur place', 'emporte')),
    date_emprunt       TIMESTAMP   NOT NULL DEFAULT now(),
    date_retour_prevue TIMESTAMP   NOT NULL,
    statut_pret_id     INTEGER REFERENCES statut_pret (id),
    statut_validation  INTEGER     NOT NULL REFERENCES statut_validation (id)
);

CREATE TABLE statut_validation_pret
(
    id                SERIAL PRIMARY KEY,
    pret_id           INTEGER NOT NULL REFERENCES pret (id),
    bibliothecaire_id INTEGER NOT NULL REFERENCES bibliothecaire (personne_id),
    statut_validation INTEGER NOT NULL REFERENCES statut_validation (id),
    date_fin_statut   TIMESTAMP
);

-- Table d'historique des prêts
CREATE TABLE pret_historique
(
    id              SERIAL PRIMARY KEY,
    pret_id         INTEGER NOT NULL REFERENCES pret (id) ON DELETE CASCADE,
    statut_pret_id  INTEGER NOT NULL REFERENCES statut_pret (id),
    date_fin_statut TIMESTAMP
);

CREATE TABLE prolongation_pret
(
    id                   SERIAL PRIMARY KEY,
    date_prolongation    TIMESTAMP NOT NULL DEFAULT now(),
    pret_id              INTEGER   NOT NULL,
    nouvelle_date_retour TIMESTAMP NOT NULL,
    statut_id            INTEGER   NOT NULL REFERENCES statut_validation (id)
);

CREATE TABLE statut_prolongation
(
    id                SERIAL PRIMARY KEY,
    prolongation_id   INTEGER NOT NULL REFERENCES prolongation_pret (id),
    statut_id         INTEGER NOT NULL REFERENCES statut_validation (id),
    bibliothecaire_id INTEGER NOT NULL REFERENCES bibliothecaire (personne_id),
    date_fin          TIMESTAMP
);

-- Table des réservations
CREATE TABLE reservation
(
    id               SERIAL PRIMARY KEY,
    exemplaire_id    INTEGER     NOT NULL REFERENCES exemplaire (id) ON DELETE CASCADE,
    adherent_id      INTEGER     NOT NULL REFERENCES adherent (personne_id) ON DELETE CASCADE,
    type_pret        VARCHAR(20) NOT NULL CHECK (type_pret IN ('sur place', 'emporte')),
    date_reservation TIMESTAMP   NOT NULL DEFAULT now(),
    date_expiration  TIMESTAMP   NOT NULL,
    statut_id        INTEGER     NOT NULL REFERENCES statut_validation (id)
);

CREATE TABLE statut_reservation
(
    id                SERIAL PRIMARY KEY,
    reservation_id    INTEGER NOT NULL REFERENCES reservation (id),
    statut_id         INTEGER NOT NULL REFERENCES statut_validation (id),
    bibliothecaire_id INTEGER NOT NULL REFERENCES bibliothecaire (personne_id),
    date_fin          TIMESTAMP
);

-- Table pour les jours fériés (pour le calcul des pénalités)
-- insertion directe
CREATE TABLE jour_ferie
(
    id           SERIAL PRIMARY KEY,
    date_ferie   DATE    NOT NULL,
    description  VARCHAR(100),
    is_recurrent BOOLEAN NOT NULL DEFAULT FALSE, -- true si c'est un jour férié récurrent (ex: 1er mai)
    mois         SMALLINT,                       -- pour les jours fériés récurrents (ex: 1 pour janvier)
    jour         SMALLINT                        -- pour les jours fériés récurrents (ex: 1 pour le 1er du mois)
);

-- Table des pénalités
CREATE TABLE penalite
(
    id          SERIAL PRIMARY KEY,
    personne_id INTEGER NOT NULL REFERENCES personne (id) ON DELETE CASCADE,
    pret_id     INTEGER REFERENCES pret (id),
    date_debut  DATE    NOT NULL,
    date_fin    DATE    NOT NULL
);

CREATE TABLE configuration
(
    id     SERIAL PRIMARY KEY,
    nom    VARCHAR(100),
    valeur INTEGER
);