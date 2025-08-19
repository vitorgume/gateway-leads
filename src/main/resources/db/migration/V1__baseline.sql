-- railway.clientes definição

CREATE TABLE `clientes` (
  `id_cliente` binary(16) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `regiao` tinyint DEFAULT NULL,
  `segmento` tinyint DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `inativo` bit(1) NOT NULL,
  PRIMARY KEY (`id_cliente`),
  CONSTRAINT `clientes_chk_1` CHECK ((`regiao` between 0 and 2)),
  CONSTRAINT `clientes_chk_2` CHECK ((`segmento` between 0 and 5))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- railway.outros_contatos definição

CREATE TABLE `outros_contatos` (
  `id_outro_contato` bigint NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `setor` tinyint DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_outro_contato`),
  CONSTRAINT `outros_contatos_chk_1` CHECK ((`setor` between 0 and 3))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- railway.vendedores definição

CREATE TABLE `vendedores` (
  `id_vendedor` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `inativo` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id_vendedor`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- railway.conversas definição

CREATE TABLE `conversas` (
  `id_conversa` binary(16) NOT NULL,
  `data_criacao` datetime(6) DEFAULT NULL,
  `finalizada` bit(1) DEFAULT NULL,
  `coleta_regiao` bit(1) DEFAULT NULL,
  `coleta_segmento` bit(1) DEFAULT NULL,
  `coleta_nome` bit(1) DEFAULT NULL,
  `escolha_comercial` bit(1) DEFAULT NULL,
  `escolha_comercial_recontato` bit(1) DEFAULT NULL,
  `escolha_financeiro` bit(1) DEFAULT NULL,
  `mensagem_inicial` bit(1) DEFAULT NULL,
  `ultima_mensagem` datetime(6) DEFAULT NULL,
  `cliente_id_cliente` binary(16) DEFAULT NULL,
  `vendedor_id_vendedor` bigint DEFAULT NULL,
  `encerrada` bit(1) DEFAULT NULL,
  `data_ultima_mensagem` datetime(6) DEFAULT NULL,
  `escolha_logistica` bit(1) DEFAULT NULL,
  `tipo_ultima_mensagem` tinyint DEFAULT NULL,
  `ultima_mensagem_conversa_finalizada` datetime(6) DEFAULT NULL,
  `inativa` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id_conversa`),
  UNIQUE KEY `UKox9k01tt0dxxdwdh9yb82qsl2` (`cliente_id_cliente`),
  KEY `FKcu6q353yuyp02wfdqh8i2g9h5` (`vendedor_id_vendedor`),
  CONSTRAINT `FKcu6q353yuyp02wfdqh8i2g9h5` FOREIGN KEY (`vendedor_id_vendedor`) REFERENCES `vendedores` (`id_vendedor`),
  CONSTRAINT `FKsghu45fbsxh3idaiqxs2jp8rq` FOREIGN KEY (`cliente_id_cliente`) REFERENCES `clientes` (`id_cliente`),
  CONSTRAINT `conversas_chk_1` CHECK ((`tipo_ultima_mensagem` between 0 and 13))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




