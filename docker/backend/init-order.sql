CREATE DATABASE IF NOT EXISTS order_db;

CREATE USER IF NOT EXISTS 'username'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON order_db.* TO 'username'@'%';
FLUSH PRIVILEGES;