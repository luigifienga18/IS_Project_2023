
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
    citta                     VARCHAR(255)    NOT NULL,
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



