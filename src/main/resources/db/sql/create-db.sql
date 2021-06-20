CREATE TABLE payment
(
    id            BIGINT PRIMARY KEY,
    price         DECIMAL(30, 8) NOT NULL,
    bank_response varchar(10)    NOT NULL,
    order_id      varchar(100)   NOT NULL,
    merchant_id   BIGINT         NOT NULL
);

CREATE TABLE product
(
    id          BIGINT PRIMARY KEY,
    name        varchar(50)    NOT NULL,
    description varchar(100)   NOT NULL,
    stock       BIGINT         NOT NULL,
    price       DECIMAL(30, 8) NOT NULL,
    merchant_id BIGINT         NOT NULL,
    version     BIGINT         NOT NULL
);