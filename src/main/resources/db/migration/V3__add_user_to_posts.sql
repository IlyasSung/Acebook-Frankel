ALTER TABLE posts
ADD user_id INTEGER REFERENCES users(id);
