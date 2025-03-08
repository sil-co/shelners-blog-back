
-- blog-spring

CREATE TABLE users (
    id VARCHAR(50) PRIMARY KEY DEFAULT (UUID()),
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE posts (
    id VARCHAR(50) PRIMARY KEY DEFAULT (UUID()),
    user_id VARCHAR(50) NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert
INSERT INTO users (id, username, email, password_hash)
VALUES
('38962f8b-faf6-11ef-ba73-111111111111', 'John_Smith', 'John_Smith@example.com', 'hashed_password_1'),
('38962f8b-faf6-11ef-ba73-0242ac130002', 'Shelner', 'shelner.book@gmail.com', 'shelner_hashed_password');

INSERT INTO posts (user_id, title, content)
VALUES
('38962f8b-faf6-11ef-ba73-0242ac130002', 'My First Post', 'This is the content of the first post.'),
('38962f8b-faf6-11ef-ba73-0242ac130002', 'About Spring Boot', '## What is Spring Boot');

-- ALTER TABLE posts MODIFY COLUMN id CHAR(36);
