-- Create database
CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

-- Create readers table
CREATE TABLE IF NOT EXISTS readers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    membership_type VARCHAR(20) NOT NULL
);

-- Create books table
CREATE TABLE IF NOT EXISTS books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(100) NOT NULL,
    published_date DATE NOT NULL,
    genre VARCHAR(50) NOT NULL
);

-- Create reader_books table for many-to-many relationship
CREATE TABLE IF NOT EXISTS reader_books (
    reader_id INT,
    book_id INT,
    PRIMARY KEY (reader_id, book_id),
    FOREIGN KEY (reader_id) REFERENCES readers(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

-- Create user for the application
CREATE USER IF NOT EXISTS 'user'@'localhost' IDENTIFIED BY 'user';
GRANT ALL PRIVILEGES ON library_db.* TO 'user'@'localhost';
FLUSH PRIVILEGES; 