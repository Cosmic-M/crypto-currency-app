--liquibase formatted sql
--changeset <postgres>:<create-crypto_currencies-table>
CREATE TABLE IF NOT EXISTS public.crypto_currencies
(
    id bigint NOT NULL,
    date_time timestamp NOT NULL,
    asset_id_quote character varying(256) NOT NULL,
    rate decimal(58, 29) NOT NULL
    );

--rollback DROP TABLE crypto_currencies;
