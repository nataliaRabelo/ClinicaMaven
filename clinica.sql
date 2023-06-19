-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Tempo de geração: 23-Set-2022 às 10:59
-- Versão do servidor: 5.7.31
-- versão do PHP: 7.4.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `clinica`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `administrador`
--

DROP TABLE IF EXISTS `administrador`;
CREATE TABLE IF NOT EXISTS `administrador` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(40) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `senha` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `administrador`
--

INSERT INTO `administrador` (`id`, `nome`, `cpf`, `senha`) VALUES
(1, 'Leo', '249.252.810-38', '111');

-- --------------------------------------------------------

--
-- Estrutura da tabela `consulta`
--

DROP TABLE IF EXISTS `consulta`;
CREATE TABLE IF NOT EXISTS `consulta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` datetime NOT NULL,
  `descricao` text NOT NULL,
  `realizada` varchar(1) NOT NULL DEFAULT 'N',
  `idmedico` int(11) NOT NULL,
  `idpaciente` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idmedico` (`idmedico`),
  KEY `idpaciente` (`idpaciente`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `consulta`
--

INSERT INTO `consulta` (`id`, `data`, `descricao`, `realizada`, `idmedico`, `idpaciente`) VALUES
(1, '2022-09-12 11:00:19', 'consulta da Maria', 'N', 1, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `especialidade`
--

DROP TABLE IF EXISTS `especialidade`;
CREATE TABLE IF NOT EXISTS `especialidade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `especialidade`
--

INSERT INTO `especialidade` (`id`, `descricao`) VALUES
(1, 'CARDIOLOGIA'),
(2, 'NEUROLOGIA'),
(3, 'GASTROLOGISTA'),
(4, 'PNEUMOLOGIA');

-- --------------------------------------------------------

--
-- Estrutura da tabela `exames`
--

DROP TABLE IF EXISTS `exames`;
CREATE TABLE IF NOT EXISTS `exames` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idtipoexame` int(11) NOT NULL,
  `idconsulta` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idconsulta` (`idconsulta`),
  KEY `idtipoexame` (`idtipoexame`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `exames`
--

INSERT INTO `exames` (`id`, `idtipoexame`, `idconsulta`) VALUES
(1, 4, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `medico`
--

DROP TABLE IF EXISTS `medico`;
CREATE TABLE IF NOT EXISTS `medico` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(40) NOT NULL,
  `crm` int(11) NOT NULL,
  `estadocrm` varchar(2) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `senha` varchar(10) NOT NULL,
  `autorizado` varchar(1) NOT NULL DEFAULT 'S',
  `idespecialidade` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idespecialidade` (`idespecialidade`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `medico`
--

INSERT INTO `medico` (`id`, `nome`, `crm`, `estadocrm`, `cpf`, `senha`, `autorizado`, `idespecialidade`) VALUES
(1, 'Marcos', 1234, 'RJ', '381.585.150-53', '111', 'S', 2),
(2, 'Flávia', 4565, 'RJ', '693.339.230-98', '111', 'S', 1),
(3, 'Marcos', 1234, 'RJ', '381.585.150-53', '111', 'S', 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `paciente`
--

DROP TABLE IF EXISTS `paciente`;
CREATE TABLE IF NOT EXISTS `paciente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(40) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `senha` varchar(10) NOT NULL,
  `autorizado` varchar(1) NOT NULL DEFAULT 'N',
  `idtipoplano` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idtipoplano` (`idtipoplano`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `paciente`
--

INSERT INTO `paciente` (`id`, `nome`, `cpf`, `senha`, `autorizado`, `idtipoplano`) VALUES
(1, 'Maria', '937.397.160-37', '111', 'N', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tipoexame`
--

DROP TABLE IF EXISTS `tipoexame`;
CREATE TABLE IF NOT EXISTS `tipoexame` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `tipoexame`
--

INSERT INTO `tipoexame` (`id`, `descricao`) VALUES
(1, 'CORAÇÃO'),
(2, 'PULMÃO'),
(3, 'ABDOME'),
(4, 'CRÂNIO');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tipoplano`
--

DROP TABLE IF EXISTS `tipoplano`;
CREATE TABLE IF NOT EXISTS `tipoplano` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `tipoplano`
--

INSERT INTO `tipoplano` (`id`, `descricao`) VALUES
(1, 'UNIMED'),
(2, 'AMIL'),
(3, 'SulAmérica'),
(4, 'Particular');

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `consulta`
--
ALTER TABLE `consulta`
  ADD CONSTRAINT `consulta_ibfk_1` FOREIGN KEY (`idmedico`) REFERENCES `medico` (`id`),
  ADD CONSTRAINT `consulta_ibfk_2` FOREIGN KEY (`idpaciente`) REFERENCES `paciente` (`id`);

--
-- Limitadores para a tabela `exames`
--
ALTER TABLE `exames`
  ADD CONSTRAINT `exames_ibfk_1` FOREIGN KEY (`idconsulta`) REFERENCES `consulta` (`id`),
  ADD CONSTRAINT `exames_ibfk_2` FOREIGN KEY (`idtipoexame`) REFERENCES `tipoexame` (`id`);

--
-- Limitadores para a tabela `medico`
--
ALTER TABLE `medico`
  ADD CONSTRAINT `medico_ibfk_1` FOREIGN KEY (`idespecialidade`) REFERENCES `especialidade` (`id`);

--
-- Limitadores para a tabela `paciente`
--
ALTER TABLE `paciente`
  ADD CONSTRAINT `paciente_ibfk_1` FOREIGN KEY (`idtipoplano`) REFERENCES `tipoplano` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
