-- phpMyAdmin SQL Dump
-- version 4.4.15.10
-- https://www.phpmyadmin.net
--
-- Client :  devbdd.iutmetz.univ-lorraine.fr
-- Généré le :  Jeu 24 Mars 2022 à 20:52
-- Version du serveur :  10.3.32-MariaDB
-- Version de PHP :  7.1.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `mauchien3u_rmi`
--

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE IF NOT EXISTS `client` (
  `idClient` int(11) NOT NULL,
  `NomClient` varchar(255) NOT NULL,
  `PrenomClient` varchar(255) NOT NULL,
  `Login` varchar(255) NOT NULL,
  `MotDePasse` varchar(255) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `client`
--

INSERT INTO `client` (`idClient`, `NomClient`, `PrenomClient`, `Login`, `MotDePasse`) VALUES
(1, 'Mauchien', 'Thomas', 'admin', 'thomas'),
(2, 'Fox', 'Mulder', 'danna', 'scully'),
(3, 'test1', 'test1', 'test', 'test'),
(4, 'jujutsu', 'kaisen', 'admin2', 'thomas'),
(5, 'DUSSAUSSOIS', 'Tom', 'tom', 'tom'),
(6, 'test2', 'test2', 'test2', 'test2');

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE IF NOT EXISTS `commande` (
  `idCommande` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `idClient` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `commande`
--

INSERT INTO `commande` (`idCommande`, `date`, `idClient`) VALUES
(1, '2021-11-09 00:00:00', 1),
(9, '2022-02-21 16:06:50', 1),
(11, '2022-02-21 16:19:00', 1),
(12, '2022-02-21 16:21:45', 1),
(13, '2022-02-22 09:43:50', 1),
(14, '2022-02-24 20:04:19', 1),
(15, '2022-03-13 15:49:26', 1),
(16, '2022-03-13 15:58:45', 1),
(17, '2022-03-13 16:02:16', 1),
(18, '2022-03-16 18:07:54', 1),
(19, '2022-03-16 18:08:13', 1),
(20, '2022-03-24 20:26:37', 1),
(21, '2022-03-24 20:35:28', 5);

-- --------------------------------------------------------

--
-- Structure de la table `ligne_commande`
--

CREATE TABLE IF NOT EXISTS `ligne_commande` (
  `idCommande` int(11) NOT NULL,
  `idProduit` int(11) NOT NULL,
  `QuantiteCommande` int(11) NOT NULL,
  `tarif` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `ligne_commande`
--

INSERT INTO `ligne_commande` (`idCommande`, `idProduit`, `QuantiteCommande`, `tarif`) VALUES
(1, 1, 3, 30),
(9, 3, 1, 500),
(11, 7, 2, 600),
(12, 3, 1, 500),
(13, 3, 1, 500),
(14, 2, 1, 200),
(15, 3, 2, 1000),
(16, 3, 3, 1500),
(17, 3, 3, 1500),
(18, 7, 1, 300),
(19, 6, 3, 60),
(20, 4, 2, 130),
(21, 7, 5, 1500);

-- --------------------------------------------------------

--
-- Structure de la table `magasin`
--

CREATE TABLE IF NOT EXISTS `magasin` (
  `idMagasin` int(11) NOT NULL,
  `NomMagasin` varchar(255) NOT NULL,
  `contact` varchar(255) NOT NULL,
  `TypeMagasin` varchar(255) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `magasin`
--

INSERT INTO `magasin` (`idMagasin`, `NomMagasin`, `contact`, `TypeMagasin`) VALUES
(1, 'fromagio', 'contact@gmail.com', 'habits'),
(2, 'Decathlon', 'contact@fr.decathlon.com', 'Sport'),
(3, 'Fnac', 'fnac@sav.com', 'Divers'),
(4, 'Amazon', 'amazon@sav.com', 'Divers');

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE IF NOT EXISTS `produit` (
  `idProduit` int(11) NOT NULL,
  `NomProduit` varchar(255) NOT NULL,
  `QuantiteProduit` int(11) NOT NULL,
  `prix` int(11) NOT NULL,
  `idMagasin` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `produit`
--

INSERT INTO `produit` (`idProduit`, `NomProduit`, `QuantiteProduit`, `prix`, `idMagasin`) VALUES
(1, 'T-shirt', 45, 10, 1),
(2, 'Vélo', 9, 200, 2),
(3, 'Kayak', 7, 500, 2),
(4, 'Coffret La Quatrième dimension L''intégrale DVD', 6, 65, 3),
(5, 'The X-Files Intégrale des 9 saisons Coffret Blu-ray', 1, 55, 3),
(6, 'Apocalypse now DVD', 4, 20, 3),
(7, 'Nintendo switch', 42, 300, 4);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`idClient`);

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`idCommande`),
  ADD KEY `idClient` (`idClient`);

--
-- Index pour la table `ligne_commande`
--
ALTER TABLE `ligne_commande`
  ADD PRIMARY KEY (`idCommande`,`idProduit`),
  ADD KEY `idProduit` (`idProduit`);

--
-- Index pour la table `magasin`
--
ALTER TABLE `magasin`
  ADD PRIMARY KEY (`idMagasin`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`idProduit`),
  ADD KEY `idMagasin` (`idMagasin`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `idClient` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT pour la table `commande`
--
ALTER TABLE `commande`
  MODIFY `idCommande` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT pour la table `magasin`
--
ALTER TABLE `magasin`
  MODIFY `idMagasin` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `idProduit` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `client` FOREIGN KEY (`idClient`) REFERENCES `client` (`idClient`);

--
-- Contraintes pour la table `ligne_commande`
--
ALTER TABLE `ligne_commande`
  ADD CONSTRAINT `idCommande` FOREIGN KEY (`idCommande`) REFERENCES `commande` (`idCommande`),
  ADD CONSTRAINT `idProduit` FOREIGN KEY (`idProduit`) REFERENCES `produit` (`idProduit`);

--
-- Contraintes pour la table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `idMagasin` FOREIGN KEY (`idMagasin`) REFERENCES `magasin` (`idMagasin`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
