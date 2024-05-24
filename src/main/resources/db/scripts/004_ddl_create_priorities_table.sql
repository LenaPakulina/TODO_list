CREATE TABLE priorities (
   id SERIAL PRIMARY KEY,
   name TEXT UNIQUE NOT NULL,
   position int,
   description VARCHAR(128) NOT NULL
);