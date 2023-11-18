CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    email    VARCHAR(255),
    password VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE cinema_halls
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    capacity    INT,
    description VARCHAR(255),
    CONSTRAINT pk_cinema_halls PRIMARY KEY (id)
);

CREATE TABLE films
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    title        VARCHAR(255),
    release_year INT,
    description  VARCHAR(255),
    CONSTRAINT pk_films PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    user_id    BIGINT,
    order_time TIMESTAMP,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE TABLE sessions
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    film_id        BIGINT,
    cinema_hall_id BIGINT,
    show_time      TIMESTAMP,
    CONSTRAINT pk_sessions PRIMARY KEY (id)
);

CREATE TABLE shopping_carts
(
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_shopping_carts PRIMARY KEY (user_id)
);

CREATE TABLE tickets
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    user_id         BIGINT,
    film_session_id BIGINT,
    CONSTRAINT pk_tickets PRIMARY KEY (id)
);

CREATE TABLE logger
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    message TEXT
);


ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE sessions
    ADD CONSTRAINT FK_SESSIONS_ON_CINEMA_HALL FOREIGN KEY (cinema_hall_id) REFERENCES cinema_halls (id);

ALTER TABLE sessions
    ADD CONSTRAINT FK_SESSIONS_ON_FILM FOREIGN KEY (film_id) REFERENCES films (id);

ALTER TABLE shopping_carts
    ADD CONSTRAINT FK_SHOPPING_CARTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE tickets
    ADD CONSTRAINT FK_TICKETS_ON_FILMSESSION FOREIGN KEY (film_session_id) REFERENCES sessions (id);

ALTER TABLE tickets
    ADD CONSTRAINT FK_TICKETS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);