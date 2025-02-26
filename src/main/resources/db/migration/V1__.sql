CREATE TABLE accounts
(
    id         UUID NOT NULL,
    phone      text,
    first_name text,
    last_name  text,
    patronymic text,
    email      text,
    role       text,
    CONSTRAINT pk_accounts PRIMARY KEY (id)
);

CREATE TABLE jwt_accounts
(
    id            UUID NOT NULL,
    account_id    UUID,
    jwt_id        text,
    jwt           text,
    exp           TIMESTAMP WITHOUT TIME ZONE,
    nbf           TIMESTAMP WITHOUT TIME ZONE,
    iat           TIMESTAMP WITHOUT TIME ZONE,
    refresh_token UUID,
    refresh_exp   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_jwt_accounts PRIMARY KEY (id)
);

CREATE TABLE sms_codes
(
    id      UUID NOT NULL,
    phone   text,
    code    text,
    expires TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_sms_codes PRIMARY KEY (id)
);

CREATE TABLE temporary_accounts
(
    id            UUID NOT NULL,
    phone         VARCHAR(255),
    register_uuid UUID,
    CONSTRAINT pk_temporary_accounts PRIMARY KEY (id)
);