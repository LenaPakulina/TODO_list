CREATE TABLE priorities (
   id SERIAL PRIMARY KEY,
   name TEXT UNIQUE NOT NULL,
   position int,
   description VARCHAR(128) NOT NULL
);

INSERT INTO priorities (name, position, description) VALUES ('urgently', 1, 'Срочный');
INSERT INTO priorities (name, position, description) VALUES ('normal', 2, 'Нормальный');

ALTER TABLE tasks ADD COLUMN priority_id int REFERENCES priorities(id);

UPDATE tasks SET priority_id = (SELECT id FROM priorities WHERE name = 'urgently');