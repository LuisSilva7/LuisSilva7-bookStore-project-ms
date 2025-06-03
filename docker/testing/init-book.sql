CREATE DATABASE IF NOT EXISTS book_store_db;

CREATE USER IF NOT EXISTS 'username'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON book_store_db.* TO 'username'@'%';
FLUSH PRIVILEGES;