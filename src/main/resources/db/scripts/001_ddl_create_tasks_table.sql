CREATE TABLE tasks (
   id SERIAL PRIMARY KEY,
   title varchar(256) not null,
   description TEXT,
   created TIMESTAMP,
   done BOOLEAN not null
);