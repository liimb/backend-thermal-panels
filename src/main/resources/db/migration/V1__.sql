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
    expires TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE materials
(
    id      UUID PRIMARY KEY ,
    name    TEXT NOT NULL,
    unit    TEXT,
    price   FLOAT
);

CREATE TABLE works
(
    id      UUID PRIMARY KEY,
    name    TEXT NOT NULL,
    unit    TEXT,
    price   FLOAT
);

CREATE TABLE orders
(
    id      UUID PRIMARY KEY,
    user_id  UUID NOT NULL,
    name    TEXT NOT NULL,
    status  TEXT NOT NULL
);

CREATE TABLE user_materials
(
    id           UUID  PRIMARY KEY,
    material_id  UUID  NOT NULL,
    order_id     UUID  NOT NULL,
    count        FLOAT NOT NULL
);

CREATE TABLE user_works
(
    id           UUID  PRIMARY KEY,
    work_id  UUID  NOT NULL,
    order_id     UUID  NOT NULL,
    count        FLOAT NOT NULL,
    comment      TEXT
);

CREATE TABLE temporary_accounts
(
    id            UUID PRIMARY KEY,
    phone         TEXT NOT NULL,
    register_uuid UUID NOT NULL
);

INSERT INTO accounts (id, phone, first_name, last_name, patronymic, email, role) VALUES
(gen_random_uuid(), '79331841809', 'admin', 'admin', 'admin', 'admin','ADMIN');

INSERT INTO materials (id, name, unit, price) VALUES
(gen_random_uuid(), 'Монтажная пена-клей для термопанелей', 'кв.м.', 850),
(gen_random_uuid(), 'Стартовый уголок от грызунов', 'м.п.', 350),
(gen_random_uuid(), 'Отливы на цоколь, цвет на выбор', 'м.п.', 450),
(gen_random_uuid(), 'Отливы на окна, цвет на выбор', 'м.п.', 450),
(gen_random_uuid(), 'Откосы на окна, цвет на выбор', 'м.п.', 550),
(gen_random_uuid(), 'Софиты пластиковые, металлические, цвет на выбор', NULL, NULL),
(gen_random_uuid(), 'Водосточные желоба, цвет на выбор', NULL, NULL),
(gen_random_uuid(), 'Водостоки с водоприемными воронками, цвет на выбор', NULL, NULL),
(gen_random_uuid(), 'Доставка материалов', NULL, NULL);

INSERT INTO works (id, name, unit, price) VALUES
(gen_random_uuid(), 'Демонтажные работы, зачистка фасада, удаление наплывов, алмазная резка, удаление стальных элементов', 'кв.м.', NULL),
(gen_random_uuid(), 'Монтаж клинкерных термопанелей на клей и саморезы (нагеля) без затирки', 'кв.м.', 2000),
(gen_random_uuid(), 'Затирка швов термопанелей', 'кв.м.', 1000),
(gen_random_uuid(), 'Затирка швов термопанелей при условии смонтированных панелей сторонней организацией', 'кв.м.', 1500),
(gen_random_uuid(), 'Монтаж кварцевых термопанелей на клей без саморезов. Швы термопанелей затерты на заводе.', 'кв.м.', 2000),
(gen_random_uuid(), 'Формирование углов из прямых панелей с запилом 45 градусов.', 'м.п.', 2000),
(gen_random_uuid(), 'Запил панелей под угол исходя из конфигурации строения, не прямой угол.', 'м.п.', 2200),
(gen_random_uuid(), 'Монтаж угловых элементов термопанелей', 'м.п.', 2000),
(gen_random_uuid(), 'Монтаж термопанелей на откосы', 'м.п.', 3000),
(gen_random_uuid(), 'Монтаж клинкерной плитки поштучно без ППС на плиточный клей.', 'кв.м.', 3800),
(gen_random_uuid(), 'Монтаж металлических отливов на окна с герметизацией стыков герметиком. Цвет откосов на выбор.', 'м.п.', 1000),
(gen_random_uuid(), 'Монтаж металлических откосов на окна с герметизацией стыков герметиком. Цвет откосов на выбор.', 'м.п.', 1300),
(gen_random_uuid(), 'Монтаж металлических откосов на окна с утеплением и герметизацией стыков герметиком. Цвет откосов на выбор.', 'м.п.', 1800),
(gen_random_uuid(), 'Монтаж отливов на цоколь здания', 'м.п.', 1000),
(gen_random_uuid(), 'Монтаж металлического уголка от грызунов', 'м.п.', 750),
(gen_random_uuid(), 'Облицовка клинкерной плиткой цоколя', 'кв.м.', 3800),
(gen_random_uuid(), 'Монтаж угловых элементов наружных/внутренних на окна, примыкания.', 'м.п.', 2000),
(gen_random_uuid(), 'Монтаж каркаса для софитов', 'м.п.', 1400),
(gen_random_uuid(), 'Монтаж софитов без обрешетки', 'м.п.', 1400),
(gen_random_uuid(), 'Монтаж водосточных желобов', 'м.п.', 900),
(gen_random_uuid(), 'Установка кронштейна для желоба водосточного', 'шт.', 400),
(gen_random_uuid(), 'Монтаж водосточных труб', 'м.п.', 900),
(gen_random_uuid(), 'Огне-био защита деревянных конструкций из пульверизатора, распылителя.', 'кв.м.', 120),
(gen_random_uuid(), 'Утепление фундамента, цоколя Пеноплексом или ППС 50 мм. на рондоли до глубины 585-600 мм. с выемкой грунта.', 'м.п.', 1800),
(gen_random_uuid(), 'Утепление фундамента, цоколя Пеноплексом или ППС 50 мм. на рондоли  до глубины 585-600 мм. БЕЗ выемки грунта.', 'м.п.', 1500),
(gen_random_uuid(), 'Монтаж силового каркаса из дерева (брусок 50х50)', 'кв.м.', 1250),
(gen_random_uuid(), 'Монтаж силового каркаса из металла', 'кв.м.', 2300),
(gen_random_uuid(), 'Обшивка стен листами ОСП-3 (9-12 мм.)', 'кв.м.', 900),
(gen_random_uuid(), 'Монтаж проходных элементов газ труба, труба канализационная 50-100 мм, дымоход, вентиляция.', 'шт.', 2000),
(gen_random_uuid(), 'Вентиляционные отверстия и проходные элементы', 'шт.', 1500),
(gen_random_uuid(), 'Монтаж крупных проходных элементов', 'шт.', NULL),
(gen_random_uuid(), 'Монтаж светильников на фасад здания без прокладки кабеля', 'шт.', 3000),
(gen_random_uuid(), 'Строительные леса', NULL, NULL),
(gen_random_uuid(), 'Доставка материалов', NULL, NULL),
(gen_random_uuid(), 'Вывоз мусора', NULL, NULL);