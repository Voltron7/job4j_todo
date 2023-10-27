CREATE TABLE if not exists users
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR        not null,
    email     VARCHAR unique not null,
    password  VARCHAR        not null
);