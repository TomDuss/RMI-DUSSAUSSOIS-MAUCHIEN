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
-- Base de données :  `mauchien3u_banqueRMI`
--

-- --------------------------------------------------------

--
-- Structure de la table `banque`
--

CREATE TABLE IF NOT EXISTS `banque` (
  `idBanque` int(11) NOT NULL,
  `NomBanque` varchar(255) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

--
-- Contenu de la table `banque`
--

INSERT INTO `banque` (`idBanque`, `NomBanque`) VALUES
(1, 'Crédit-Mutuel'),
(2, 'Crédit-Agricole'),
(3, 'Caisse-d’Epargne'),
(4, 'AXA-Banque');

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE IF NOT EXISTS `client` (
  `idClient` int(11) NOT NULL,
  `NomClient` varchar(255) NOT NULL,
  `PrenomClient` varchar(255) NOT NULL,
  `Login` varchar(255) NOT NULL,
  `MDP` varchar(255) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

--
-- Contenu de la table `client`
--

INSERT INTO `client` (`idClient`, `NomClient`, `PrenomClient`, `Login`, `MDP`) VALUES
(1, 'Mauchien', 'Thomas', 'admin-banque', 'vagabond'),
(2, 'Fox', 'Mulder', 'danna', 'scully'),
(3, 'test1', 'test1', 'test', 'test'),
(4, 'test2', 'test2', 'rrr', '123'),
(5, 'test3', 'test3', 'test3', 'wankil'),
(6, 'test4', 'test4', 'test4', 'test4'),
(7, 'test5', 'test5', 'test5', 'aaa'),
(8, '666', '666', 'thomas', 'test'),
(9, 'ak', 'ahmet', 'ak57', 'test'),
(10, 'femme', 'moi', 'test6', 'test'),
(11, 'gerard', 'depardieu', 'admin', 'thomas'),
(12, 'aaa', 'aaa', 'admin-banque', 'aaa'),
(13, 'ccc', 'cccc', 'ccc', 'ccc'),
(14, 'aaa', 'aaaa', 'admin-banque', 'aaaa'),
(15, 'aaa', 'aaa', 'admin-banque', 'test'),
(16, 'ddd', 'ddd', 'admin-banque', 'aaa'),
(17, 'dfdfdf', 'dfdfdf', 'test', 'test'),
(18, 'dfdfdf', 'dfdfdf', 'test', 'test'),
(19, 'aaa', 'aaa', 'test', 'test'),
(20, 'ddd', 'ddd', 'admin-banque', 'test'),
(21, 'sss', 'sss', 'admin-banque', 'aaa'),
(22, 'adad', 'adad', 'test', 'aaa'),
(23, 'DUSSAUSSOIS', 'Tom', 'tom', 'tom');

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

CREATE TABLE IF NOT EXISTS `compte` (
  `idBanque` int(11) NOT NULL,
  `Solde` int(11) NOT NULL,
  `idClient` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

--
-- Contenu de la table `compte`
--

INSERT INTO `compte` (`idBanque`, `Solde`, `idClient`) VALUES
(1, 16313, 1),
(2, 1000, 2),
(3, 500, 3),
(4, 500, 8),
(3, 0, 10),
(4, 0, 11),
(2, 0, 16),
(1, 1500, 19),
(3, 1, 21),
(4, 10, 21),
(2, 1, 22),
(4, 499998506, 23);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `banque`
--
ALTER TABLE `banque`
  ADD PRIMARY KEY (`idBanque`,`NomBanque`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`idClient`);

--
-- Index pour la table `compte`
--
ALTER TABLE `compte`
  ADD PRIMARY KEY (`idBanque`,`Solde`),
  ADD KEY `idBanque` (`idBanque`),
  ADD KEY `idClient` (`idClient`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `banque`
--
ALTER TABLE `banque`
  MODIFY `idBanque` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `idClient` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=24;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `compte`
--
ALTER TABLE `compte`
  ADD CONSTRAINT `banque` FOREIGN KEY (`idBanque`) REFERENCES `banque` (`idBanque`),
  ADD CONSTRAINT `client` FOREIGN KEY (`idClient`) REFERENCES `client` (`idClient`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
