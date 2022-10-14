--liquibase formatted sql
--changeset <postgres>:<create-assets-table>
CREATE TABLE IF NOT EXISTS public.assets
(
    id bigint NOT NULL,
    asset_id character varying(256) NOT NULL,
    title character varying(256) NOT NULL,
    CONSTRAINT asset_pk PRIMARY KEY (id)
    );

--rollback DROP TABLE assets;
