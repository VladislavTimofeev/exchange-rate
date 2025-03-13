--liquibase formatted sql

--changeset vtsimafeyeu:1
CREATE TABLE IF NOT EXISTS currency
(
    id   SERIAL PRIMARY KEY,
    code VARCHAR(255) UNIQUE,
    name VARCHAR(255) UNIQUE,
    rate NUMERIC(38,2),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
-- rollback DROP TABLE currency;