CREATE TABLE usuario (
                         id UUID PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         telefone VARCHAR(20) NOT NULL UNIQUE,
                         endereco VARCHAR(255) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         senha VARCHAR(255) NOT NULL,
                         dataNascimento DATE NOT NULL

);
-- Adicionar ao schema.sql existente ou criar como nova tabela

CREATE TABLE humor_financeiro (
                                  id UUID PRIMARY KEY,
                                  data TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  estado VARCHAR(20) NOT NULL,
                                  usuario_id UUID NOT NULL,

    -- Constraint para validar os valores do enum
                                  CONSTRAINT chk_estado_humor CHECK (
                                      estado IN (
                                                 'MUITO_PESSIMO',
                                                 'PESSIMO',
                                                 'RUIM',
                                                 'NEUTRO',
                                                 'BOM',
                                                 'MUITO_BOM',
                                                 'EXCELENTE'
                                          )
                                      ),

    -- Foreign key para tabela usuario
                                  CONSTRAINT fk_humor_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- Índices para melhor performance
CREATE INDEX idx_humor_usuario_id ON humor_financeiro(usuario_id);
CREATE INDEX idx_humor_data ON humor_financeiro(data);
CREATE INDEX idx_humor_usuario_data ON humor_financeiro(usuario_id, data);