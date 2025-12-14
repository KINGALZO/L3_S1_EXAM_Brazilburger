
CREATE TABLE Client (
    idClient SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100),
    telephone VARCHAR(20) UNIQUE,
    adresse VARCHAR(150),
    motDePasse VARCHAR(100) NOT NULL
);

CREATE TABLE Zone (
    idZone SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    prix NUMERIC(10,2)
);

CREATE TABLE Livraison (
    idLivraison SERIAL PRIMARY KEY,
    dateLivraison DATE,
    idZone INT REFERENCES Zone(idZone)
);

CREATE TABLE Paiement (
    idPaiement SERIAL PRIMARY KEY,
    datePaiement DATE,
    montant NUMERIC(10,2),
    modePaiement VARCHAR(50) -- Wave ou OM
);

CREATE TABLE Commande (
    idCommande SERIAL PRIMARY KEY,
    dateCommande DATE NOT NULL,
    etat VARCHAR(30) DEFAULT 'en attente',
    mode VARCHAR(30), -- sur place / emporter / livrer
    idClient INT NOT NULL REFERENCES Client(idClient),
    idLivraison INT REFERENCES Livraison(idLivraison),
    idPaiement INT REFERENCES Paiement(idPaiement)
);

CREATE TABLE Burger (
    idBurger SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prix NUMERIC(10,2) NOT NULL,
    image VARCHAR(255),
    etat VARCHAR(30) DEFAULT 'actif'
);

CREATE TABLE Menu (
    idMenu SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    image VARCHAR(255)
);

CREATE TABLE Complement (
    idComplement SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prix NUMERIC(10,2),
    image VARCHAR(255)
);

-- Tables d’association
CREATE TABLE MenuBurger (
    idMenu INT REFERENCES Menu(idMenu),
    idBurger INT REFERENCES Burger(idBurger),
    PRIMARY KEY (idMenu, idBurger)
);

CREATE TABLE LigneCommande (
    idCommande INT REFERENCES Commande(idCommande),
    idBurger INT REFERENCES Burger(idBurger),
    quantite INT DEFAULT 1,
    sousTotal NUMERIC(10,2),
    PRIMARY KEY (idCommande, idBurger)
);

CREATE TABLE CommandeComplement (
    idCommande INT REFERENCES Commande(idCommande),
    idComplement INT REFERENCES Complement(idComplement),
    quantite INT DEFAULT 1,
    PRIMARY KEY (idCommande, idComplement)
);
