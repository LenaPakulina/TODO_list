CREATE TABLE task_categories (
    id SERIAL PRIMARY KEY,
    task_id int NOT NULL REFERENCES tasks(id),
    category_id int NOT NULL REFERENCES categories(id),
    unique(task_id, category_id)
);