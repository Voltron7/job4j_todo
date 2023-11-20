CREATE TABLE if not exists tasks_categories
(
   id          SERIAL PRIMARY KEY,
   task_id     int not null REFERENCES tasks(id),
   category_id int not null REFERENCES categories(id)
);