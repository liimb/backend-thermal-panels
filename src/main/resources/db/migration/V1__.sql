CREATE TABLE accounts
(
    id         UUID PRIMARY KEY,
    phone      TEXT NOT NULL,
    first_name TEXT NOT NULL,
    last_name  TEXT NOT NULL,
    patronymic TEXT NOT NULL,
    email      TEXT NOT NULL,
    role       TEXT NOT NULL
);

CREATE TABLE jwt_accounts
(
    id            UUID PRIMARY KEY,
    account_id    UUID NOT NULL,
    jwt_id        text NOT NULL,
    jwt           text NOT NULL,
    exp           TIMESTAMP WITHOUT TIME ZONE,
    nbf           TIMESTAMP WITHOUT TIME ZONE,
    iat           TIMESTAMP WITHOUT TIME ZONE,
    refresh_token UUID NOT NULL,
    refresh_exp   TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE sms_codes
(
    id      UUID PRIMARY KEY,
    phone   TEXT NOT NULL,
    code    TEXT NOT NULL,
    expires TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_sms_codes PRIMARY KEY (id)
);

CREATE TABLE materials
(
    id      UUID primary key,
    name    TEXT NOT NULL,
    unit    TEXT,
    price   FLOAT
);

CREATE TABLE temporary_accounts
(
    id            UUID PRIMARY KEY,
    phone         TEXT NOT NULL,
    register_uuid UUID NOT NULL
);

insert into accounts (phone, first_name, last_name, patronymic, email, role) VALUES
('79331841809', 'admin', 'admin', 'admin', 'admin','ADMIN');

insert into materials (name, unit, price) values
('Монтажная пена-клей для термопанелей', 'кв.м.', 850),
('Стартовый уголок от грызунов', 'м.п.', 350),
('Отливы на цоколь, цвет на выбор', 'м.п.', 450),
('Отливы на окна, цвет на выбор', 'м.п.', 450),
('Откосы на окна, цвет на выбор', 'м.п.', 550),
('Софиты пластиковые, металлические, цвет на выбор', NULL, NULL),
('Водосточные желоба, цвет на выбор', NULL, NULL),
('Водостоки с водоприемными воронками, цвет на выбор', NULL, NULL),
('Доставка материалов', NULL, NULL);