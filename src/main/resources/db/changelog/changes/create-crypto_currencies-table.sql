--liquibase formatted sql
--changeset <postgres>:<create-crypto_currencies-table>
CREATE TABLE IF NOT EXISTS public.crypto_currencies
(
    asset_id_quote character varying(256) NOT NULL,
    date_time timestamp NOT NULL,
    rate character varying(256) NOT NULL,
    CONSTRAINT crypto_currencies_pk PRIMARY KEY (asset_id_quote)
    );

--rollback DROP TABLE crypto_currencies;
