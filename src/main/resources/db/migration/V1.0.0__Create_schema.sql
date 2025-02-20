CREATE TABLE accounts
(
    id         UUID PRIMARY KEY,
    phone      text NOT NULL,
    first_name text NOT NULL,
    last_name  text NOT NULL,
    patronymic text NOT NULL,
    email      text NOT NULL,
    role       text NOT NULL,
);

CREATE TABLE jwt_accounts
(
    id            UUID      PRIMARY KEY,
    account_id    UUID      NOT NULL,
    jwt_id        text      NOT NULL,
    jwt           text      NOT NULL,
    exp           TIMESTAMP NOT NULL,
    nbf           TIMESTAMP NOT NULL,
    iat           TIMESTAMP NOT NULL,
    refresh_token UUID      NOT NULL,
    refresh_exp   TIMESTAMP NOT NULL,
);

CREATE TABLE sms_codes
(
    id         UUID   PRIMARY KEY,
    account_id UUID      NOT NULL,
    code       text      NOT NULL,
    expires    TIMESTAMP NOT NULL,
);