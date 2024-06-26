CREATE TABLE IF NOT EXISTS users (id uuid PRIMARY KEY, "name" VARCHAR(70) NULL, email VARCHAR(255) NOT NULL, password_hash VARCHAR(255) NOT NULL, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL);
ALTER TABLE users ADD CONSTRAINT users_email_unique UNIQUE (email);
