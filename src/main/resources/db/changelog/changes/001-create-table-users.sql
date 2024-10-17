--liquibase formatted sql

--changeset com.arakviel:1
CREATE TABLE users
    (
        id BIGSERIAL
            PRIMARY KEY,
        email VARCHAR(350) NOT NULL
            UNIQUE,
        password VARCHAR(128) NOT NULL,
        avatar VARCHAR(2048) NULL,
        role VARCHAR(128) NOT NULL
            CHECK (role IN ('USER', 'ADMIN')),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
