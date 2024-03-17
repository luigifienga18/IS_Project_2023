
/*
COMANDI CREAZIONE TABELLE
*/

CREATE TABLE Inserzionista (
    idInserzionista        LONG            PRIMARY KEY,
    username               VARCHAR(255)    NOT NULL,
    password               VARCHAR(255)    NOT NULL    
);

CREATE TABLE MailingList(
    idMailingList          LONG            PRIMARY KEY
);

CREATE TABLE UtenteRegistrato (
    
    idUtenteRegistrato      LONG            PRIMARY KEY,
    nome                    VARCHAR(255)    NOT NULL,
    cognome                 VARCHAR(255)    NOT NULL,
    idMailingList           LONG    
);

ALTER TABLE UtenteRegistrato ADD CONSTRAINT FK_UtenteRegistrato_mailinglistid FOREIGN KEY (idMailingList) REFERENCES MailingList(idMailingList);

CREATE TABLE Contatto (
    email                    VARCHAR(255)    PRIMARY KEY,
    cellulare                CHAR(10)    NOT NULL,
    idinserzionista          LONG            UNIQUE,
    idutenteregistrato       LONG            UNIQUE
);

ALTER TABLE Contatto ADD CONSTRAINT FK_contatto_inserzionistaid FOREIGN KEY (idinserzionista) REFERENCES Inserzionista(idInserzionista);

ALTER TABLE Contatto ADD CONSTRAINT FK_contatto_utenteregistratoid FOREIGN KEY (idutenteregistrato) REFERENCES UtenteRegistrato(idUtenteRegistrato);

ALTER TABLE Contatto ALTER COLUMN idinserzionista SET NULL;

ALTER TABLE Contatto ALTER COLUMN idutenteregistrato SET NULL;


CREATE TABLE Annuncio(
    idAnnuncio                LONG            PRIMARY KEY,
    datadipubblicazione       DATE            NOT NULL,
    citta                     VARCHAR(255)    NOT NULL,     /*YYYY-MM-DD Formato Date*/
    cap                       CHAR(5)         NOT NULL,
    numerometriquadri         DOUBLE           NOT NULL,   
    numerovani                INT             NOT NULL,
    descrizione               VARCHAR(255)    NOT NULL,
    prezzo                    DOUBLE           NOT NULL,
    tipologia                 VARCHAR(255)    NOT NULL,
    stato                     VARCHAR(255)    NOT NULL,
    idinserzionista           LONG            NOT NULL
);

ALTER TABLE Annuncio ADD CONSTRAINT FK_annuncio_inserzionista FOREIGN KEY (idinserzionista) REFERENCES Inserzionista(idInserzionista);

CREATE TABLE Fotografia (
    idFotografia              LONG            PRIMARY KEY,
    path                      BLOB            NOT NULL,
    idannuncio                LONG            NOT NULL
);

ALTER TABLE Fotografia ADD CONSTRAINT FK_fotografia_annuncioid FOREIGN KEY (idannuncio) REFERENCES Annuncio(idAnnuncio);

/*
CREAZIONE SEQUENCE PER GLI INSERIMENTI
*/

CREATE SEQUENCE sequenza_inserzionista
INCREMENT BY 1
START WITH 100001
MAXVALUE 199999
NOCYCLE
NOCACHE;

CREATE SEQUENCE sequenza_utenteregistrato
INCREMENT BY 1
START WITH 200001
MAXVALUE 299999
NOCYCLE
NOCACHE;

CREATE SEQUENCE sequenza_contatto
INCREMENT BY 1
START WITH 300001
MAXVALUE 399999
NOCYCLE
NOCACHE;

CREATE SEQUENCE sequenza_annuncio
INCREMENT BY 1
START WITH 400001
MAXVALUE 499999
NOCYCLE
NOCACHE;

CREATE SEQUENCE sequenza_fotografia
INCREMENT BY 1
START WITH 500001
MAXVALUE 599999
NOCYCLE
NOCACHE;



/*INSERIMENTI NELLA BASE DATI*/

INSERT INTO Inserzionista VALUES (NEXTVAL('SEQUENZA_INSERZIONISTA'),'paolo.limiti','caricatore11');
INSERT INTO Contatto VALUES ('paolo.limiti@gmail.com','3392145620','100001',NULL);

INSERT INTO Inserzionista VALUES (NEXTVAL('SEQUENZA_INSERZIONISTA'),'gianmarcoRossi90','Gianfranco80!')
INSERT INTO Contatto VALUES ('gianrossi@gmail.com','3298508001','100002',NULL);


INSERT INTO Annuncio VALUES (NEXTVAL('SEQUENZA_ANNUNCIO'),CURRENT_DATE,'TORRE ANNUNZIATA','80058','125','7','Quadrilocale, Via Gino Alfani 45. Appartamento vista mare Ottavo piano con ascensore.','800','AFFITTO','RISTRUTTURATO','100001');
/*Inserisci le prime 8 foto nel DB per QuadrilocaleTA*/


INSERT INTO Annuncio VALUES (NEXTVAL('SEQUENZA_ANNUNCIO'),CURRENT_DATE,'PORTICI','80055','103','6','Trilocale, Via Gravina. Appartamento a piano Terra','215.000','VENDITA','RISTRUTTURATO','100001');
/*Inserisci le prime 6 foto nel DB per TrilocaleP*/

INSERT INTO Annuncio VALUES (NEXTVAL('SEQUENZA_ANNUNCIO'),CURRENT_DATE,'SOMMA VESUVIANA','80049','126','7','Quadrilocale, Via Aldo Moro. Appartamento a primo Piano.','199000','VENDITA','NUOVO','100001');

/*Inserisci le prime 7 foto nel DB per QuadrilocaleSV*/

INSERT INTO Annuncio VALUES (NEXTVAL('SEQUENZA_ANNUNCIO'),CURRENT_DATE,'MARANO DI NAPOLI','80016','102','6','Trilocale, Via Recca 118. Appartamento a piano Terra.','270000','VENDITA','RISTRUTTURATO','100002');

/*Inserisci le prime 5 foto nel DB per TrilocaleMdN*/

INSERT INTO Annuncio VALUES (NEXTVAL('SEQUENZA_ANNUNCIO'),CURRENT_DATE,'NAPOLI','80138','45','4','Bilocale via Enrico Pessina 56, zona Museo. Appartamento a Terzo piano con ascensore','1000','AFFITTO','RISTRUTTURATO','100002');

/*Inserisci le prime 8 foto nel DB per BilocaleNA*/


/*DROP*/

DROP TABLE CONTATTO;
DROP TABLE UtenteRegistrato;
DROP TABLE MailingList;
DROP TABLE FOTOGRAFIA;
DROP TABLE INSERZIONISTA;
DROP TABLE Annuncio;
DROP SEQUENCE SEQUENZA_ANNUNCIO;
DROP SEQUENCE SEQUENZA_CONTATTO;
DROP SEQUENCE SEQUENZA_FOTOGRAFIA;
DROP SEQUENCE SEQUENZA_INSERZIONISTA;

