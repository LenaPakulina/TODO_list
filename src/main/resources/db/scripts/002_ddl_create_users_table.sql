CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name varchar NOT NULL,
    login varchar unique NOT NULL,
    password varchar     NOT NULL
);