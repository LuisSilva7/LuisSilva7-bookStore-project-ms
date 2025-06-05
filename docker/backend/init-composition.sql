CREATE DATABASE IF NOT EXISTS composition_db;

CREATE USER IF NOT EXISTS 'username'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON composition_db.* TO 'username'@'%';
FLUSH PRIVILEGES;