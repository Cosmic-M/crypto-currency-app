--liquibase formatted sql
--changeset <postgres>:<create-symbol_ids-table>
CREATE TABLE IF NOT EXISTS public.symbol_ids
(
    id bigint NOT NULL,
    symbol_id character varying(256) NOT NULL,
    date_time timestamp, NOT NULL,
    price character varying(256) NOT NULL,
    CONSTRAINT symbol_pk PRIMARY KEY (id)
    );

--rollback DROP TABLE symbol_ids;
