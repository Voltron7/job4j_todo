ALTER TABLE tasks
ADD COLUMN if not exists user_id int REFERENCES users(id);