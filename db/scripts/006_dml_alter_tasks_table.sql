ALTER TABLE tasks
ADD COLUMN if not exists priority_id int REFERENCES priorities(id);