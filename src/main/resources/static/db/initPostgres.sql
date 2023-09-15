DROP TABLE IF EXISTS card;
DROP TABLE IF EXISTS refresh_token;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;



DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq AS BIGINT START WITH 1000;


CREATE TABLE users
(
    id         BIGINT PRIMARY KEY DEFAULT nextval('global_seq'),
    user_name  VARCHAR(30)                           NOT NULL UNIQUE,
    password   VARCHAR(400)                          NOT NULL,
    registered TIMESTAMP          DEFAULT now()      NOT NULL,
    status     VARCHAR(10)        DEFAULT ('ACTIVE') NOT NULL
);


CREATE TABLE roles
(
    id   BIGINT PRIMARY KEY DEFAULT nextval('global_seq'),
    name VARCHAR(20) NOT NULL
);


CREATE table user_roles
(
    user_id bigint  NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON UPDATE CASCADE
);

CREATE TABLE card
(
    id          BIGINT PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id     BIGINT       NOT NULL,
    word        VARCHAR(100) NOT NULL,
    translation VARCHAR(200) NOT NULL,
    state       VARCHAR(11)  NOT NULL,
    last_touch date         NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE refresh_token
(
    id         BIGINT PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id    BIGINT              NOT NULL,
    token      VARCHAR(400) UNIQUE NOT NULL,
    expiry_date DATE                NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);





