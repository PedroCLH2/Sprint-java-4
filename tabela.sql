-- 1. Tabela de Pacientes
CREATE TABLE TB_PACIENTE (
    id_paciente NUMBER NOT NULL PRIMARY KEY,
    nm_paciente VARCHAR2(100) NOT NULL,
    ds_email VARCHAR2(100) NOT NULL UNIQUE,
    ds_telefone VARCHAR2(20) NOT NULL
);

-- 2. Tabela de Médicos
CREATE TABLE TB_MEDICO (
    id_medico NUMBER NOT NULL PRIMARY KEY,
    nm_medico VARCHAR2(100) NOT NULL,
    nr_crm VARCHAR2(20) NOT NULL UNIQUE,
    ds_especialidade VARCHAR2(100) NOT NULL
);

-- 3. Tabela de Consultas (com relacionamentos)
CREATE TABLE TB_CONSULTA (
    id_consulta NUMBER NOT NULL PRIMARY KEY,
    id_paciente NUMBER NOT NULL,
    id_medico NUMBER NOT NULL,
    dt_hora TIMESTAMP NOT NULL,
    ds_motivo VARCHAR2(255),
    -- Definição das Chaves Estrangeiras (FK)
    CONSTRAINT fk_consulta_paciente FOREIGN KEY (id_paciente) REFERENCES TB_PACIENTE(id_paciente),
    CONSTRAINT fk_consulta_medico FOREIGN KEY (id_medico) REFERENCES TB_MEDICO(id_medico)
);

-- 4. Sequências (para gerar IDs automáticos)
CREATE SEQUENCE SQ_PACIENTE START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE SQ_MEDICO START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE SQ_CONSULTA START WITH 1 INCREMENT BY 1 NOCACHE;

-- Confirma as alterações
COMMIT;