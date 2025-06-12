CREATE TABLE usuario (
                         id UUID PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         telefone VARCHAR(20) NOT NULL UNIQUE,
                         endereco VARCHAR(255) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         senha VARCHAR(255) NOT NULL,
                         dataNascimento DATE NOT NULL

);
