-- =========================================================================
-- Table Creation Script for the Sporting Activities Search Application
-- Universidad Carlos III de Madrid - Diseño de Aplicaciones Telemáticas
-- =========================================================================

CREATE DATABASE IF NOT EXISTS sporting_manager;
USE sporting_manager;

-- Drop tables in reverse dependency order (if re-creating)
-- DROP TABLE IF EXISTS SUBSCRIPTIONS;
-- DROP TABLE IF EXISTS ACTIVITIES;
-- DROP TABLE IF EXISTS PAVILLIONS;
-- DROP TABLE IF EXISTS CLIENTS;

-- =========================================================================
-- PAVILLIONS table
-- =========================================================================
CREATE TABLE IF NOT EXISTS PAVILLIONS (
    PAVILLION VARCHAR(32) NOT NULL,
    LOCATION  VARCHAR(16),
    PRIMARY KEY (PAVILLION)
);

-- =========================================================================
-- ACTIVITIES table
-- =========================================================================
CREATE TABLE IF NOT EXISTS ACTIVITIES (
    ID              INT UNSIGNED NOT NULL AUTO_INCREMENT,
    NAME            VARCHAR(32),
    DESCRIPTION     VARCHAR(32),
    START_DATE      VARCHAR(16),
    COST            FLOAT,
    PAVILLION_NAME  VARCHAR(32) NOT NULL,
    TOTAL_PLACES    INT UNSIGNED NOT NULL,
    OCCUPIED_PLACES INT UNSIGNED NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (PAVILLION_NAME) REFERENCES PAVILLIONS(PAVILLION)
);

-- =========================================================================
-- CLIENTS table (Exercise 2)
-- PASSWD is VARCHAR(64) to hold SHA-256 hex hash (Exercise 5.3)
-- =========================================================================
CREATE TABLE IF NOT EXISTS CLIENTS (
    LOGIN    VARCHAR(16) NOT NULL,
    PASSWD   VARCHAR(64) NOT NULL,
    NAME     VARCHAR(16) NOT NULL,
    SURNAME  VARCHAR(16) NOT NULL,
    ADDRESS  VARCHAR(16) NOT NULL,
    PHONE    VARCHAR(16) NOT NULL,
    PRIMARY KEY (LOGIN)
);

-- =========================================================================
-- Sample data for pavillions
-- =========================================================================
INSERT INTO PAVILLIONS VALUES ('Ignacio Pinedo', 'Getafe');
INSERT INTO PAVILLIONS VALUES ('Alfredo Di Stefano', 'Leganes');
INSERT INTO PAVILLIONS VALUES ('Polideportivo ext', 'Colmenarejo');

-- =========================================================================
-- Sample data for activities
-- =========================================================================
INSERT INTO ACTIVITIES (NAME, DESCRIPTION, START_DATE, COST, PAVILLION_NAME, TOTAL_PLACES, OCCUPIED_PLACES)
VALUES ('swimming',    'classes for beginners', '12 June', 80,  'Ignacio Pinedo',    20, 7);

INSERT INTO ACTIVITIES (NAME, DESCRIPTION, START_DATE, COST, PAVILLION_NAME, TOTAL_PLACES, OCCUPIED_PLACES)
VALUES ('fitness',     'advanced fitness',      '17 June', 32,  'Alfredo Di Stefano', 50, 50);

INSERT INTO ACTIVITIES (NAME, DESCRIPTION, START_DATE, COST, PAVILLION_NAME, TOTAL_PLACES, OCCUPIED_PLACES)
VALUES ('aerobic',     'advanced aerobic',      '3 June',  40,  'Alfredo Di Stefano', 50, 48);

INSERT INTO ACTIVITIES (NAME, DESCRIPTION, START_DATE, COST, PAVILLION_NAME, TOTAL_PLACES, OCCUPIED_PLACES)
VALUES ('dual bike',   'competition',           '7 July',  10,  'Alfredo Di Stefano', 25, 25);

INSERT INTO ACTIVITIES (NAME, DESCRIPTION, START_DATE, COST, PAVILLION_NAME, TOTAL_PLACES, OCCUPIED_PLACES)
VALUES ('tennis',      'National instructors',  '1 Sept',  40,  'Ignacio Pinedo',     8, 7);

INSERT INTO ACTIVITIES (NAME, DESCRIPTION, START_DATE, COST, PAVILLION_NAME, TOTAL_PLACES, OCCUPIED_PLACES)
VALUES ('athletics',   'training',              '12 June',  0,  'Polideportivo ext',  60, 32);

INSERT INTO ACTIVITIES (NAME, DESCRIPTION, START_DATE, COST, PAVILLION_NAME, TOTAL_PLACES, OCCUPIED_PLACES)
VALUES ('taichi',      'classes for beginners', '2 Oct',   60,  'Alfredo Di Stefano', 15, 0);

INSERT INTO ACTIVITIES (NAME, DESCRIPTION, START_DATE, COST, PAVILLION_NAME, TOTAL_PLACES, OCCUPIED_PLACES)
VALUES ('Kick Boxing', 'Contact sport',         '24 June', 176, 'Ignacio Pinedo',    105, 1);

-- =========================================================================
-- Verify
-- =========================================================================
SELECT * FROM PAVILLIONS;
SELECT * FROM ACTIVITIES;
