-- CATALOG
DROP SCHEMA "catalog" CASCADE;
CREATE SCHEMA "catalog";

-- PRODUCT
CREATE TABLE catalog.product
(
    product_id bigint NOT NULL,
    cost double precision,
    description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT product_pkey PRIMARY KEY (product_id)
)
TABLESPACE pg_default;
ALTER TABLE catalog.product OWNER to postgres;
INSERT INTO catalog.product(product_id, cost, description, name) 
VALUES
	(1, 1.5, 'King size', 'Cookie King'),
	(2, 3, 'Forgives you', 'Cookie God'),
	(3, 5, 'Mom size', 'Cookie Universe');

-- ITEM
CREATE TABLE catalog.item
(
    item_id bigint NOT NULL,
    item_status integer,
    order_id bigint,
    product_id bigint NOT NULL,
    CONSTRAINT item_pkey PRIMARY KEY (item_id),
    CONSTRAINT fkd1g72rrhgq1sf7m4uwfvuhlhe FOREIGN KEY (product_id)
        REFERENCES catalog.product (product_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)
TABLESPACE pg_default;
ALTER TABLE catalog.item OWNER to postgres;
INSERT INTO catalog.item(item_id, item_status, order_id, product_id) VALUES 
	(1, 0, 1, 1),
	(2, 0, 2, 2),
	(3, 0, 3, 2),
	(4, 0, 3, 3);



-- ACCOUNTING
DROP SCHEMA "accounting" CASCADE;
CREATE SCHEMA "accounting";

-- CLIENT
CREATE TABLE accounting.client
(
    client_id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT client_pkey PRIMARY KEY (client_id)
)
TABLESPACE pg_default;
ALTER TABLE accounting.client OWNER to postgres;
INSERT INTO accounting.client(client_id, name) VALUES 
	(1, 'Vito'),
	(2, 'Kolyan');

-- ORDER
CREATE TABLE accounting."order"
(
    order_id bigint NOT NULL,
    order_status integer,
    client_id bigint NOT NULL,
    CONSTRAINT order_pkey PRIMARY KEY (order_id),
    CONSTRAINT fkbb5wakyppwqmfuhp53p3jvs5u FOREIGN KEY (client_id)
        REFERENCES accounting.client (client_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)
TABLESPACE pg_default;
ALTER TABLE accounting."order" OWNER to postgres;
INSERT INTO accounting."order"(order_id, order_status, client_id) VALUES 
	(1, 0, 1),
	(2, 0, 1),
	(3, 0, 2);
	