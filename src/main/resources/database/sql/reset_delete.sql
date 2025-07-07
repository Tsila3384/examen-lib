-- Script de nettoyage complet de la base sans TRUNCATE (utilise DELETE dans l'ordre des dépendances)
-- Désactive temporairement les contraintes de vérification
SET session_replication_role = 'replica';

DELETE FROM statut_reservation;
DELETE FROM reservation;
DELETE FROM statut_prolongation;
DELETE FROM prolongation_pret;
DELETE FROM pret_historique;
DELETE FROM statut_validation_pret;
DELETE FROM pret;
DELETE FROM penalite;
DELETE FROM abonnement;
DELETE FROM statut_exemplaire_livre;
DELETE FROM exemplaire;
DELETE FROM restriction_livre;
DELETE FROM categorie_livre;
DELETE FROM livre;
DELETE FROM categorie;
DELETE FROM quota;
DELETE FROM statut_disponibilite;
DELETE FROM statut_pret;
DELETE FROM statut_validation;
DELETE FROM configuration;
DELETE FROM jour_ferie;
DELETE FROM type_personne;
DELETE FROM adherent;
DELETE FROM bibliothecaire;
DELETE FROM personne;

-- Réactive les contraintes de vérification
SET session_replication_role = 'origin';

-- Remise à 1 de toutes les séquences SERIAL
ALTER SEQUENCE type_personne_id_seq RESTART WITH 1;
ALTER SEQUENCE personne_id_seq RESTART WITH 1;
ALTER SEQUENCE categorie_id_seq RESTART WITH 1;
ALTER SEQUENCE livre_id_seq RESTART WITH 1;
ALTER SEQUENCE restriction_livre_id_seq RESTART WITH 1;
ALTER SEQUENCE statut_disponibilite_id_seq RESTART WITH 1;
ALTER SEQUENCE exemplaire_id_seq RESTART WITH 1;
ALTER SEQUENCE statut_exemplaire_livre_id_seq RESTART WITH 1;
ALTER SEQUENCE abonnement_id_seq RESTART WITH 1;
ALTER SEQUENCE penalite_id_seq RESTART WITH 1;
ALTER SEQUENCE quota_id_seq RESTART WITH 1;
ALTER SEQUENCE statut_pret_id_seq RESTART WITH 1;
ALTER SEQUENCE statut_validation_id_seq RESTART WITH 1;
ALTER SEQUENCE pret_id_seq RESTART WITH 1;
ALTER SEQUENCE statut_validation_pret_id_seq RESTART WITH 1;
ALTER SEQUENCE pret_historique_id_seq RESTART WITH 1;
ALTER SEQUENCE prolongation_pret_id_seq RESTART WITH 1;
ALTER SEQUENCE statut_prolongation_id_seq RESTART WITH 1;
ALTER SEQUENCE reservation_id_seq RESTART WITH 1;
ALTER SEQUENCE statut_reservation_id_seq RESTART WITH 1;
ALTER SEQUENCE jour_ferie_id_seq RESTART WITH 1;
ALTER SEQUENCE configuration_id_seq RESTART WITH 1;
