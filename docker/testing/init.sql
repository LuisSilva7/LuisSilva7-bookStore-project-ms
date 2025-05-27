CREATE DATABASE IF NOT EXISTS book_store_db;
CREATE DATABASE IF NOT EXISTS order_query_db;

CREATE USER IF NOT EXISTS 'username'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON book_store_db.* TO 'username'@'%';
GRANT ALL PRIVILEGES ON order_query_db.* TO 'username'@'%';
FLUSH PRIVILEGES;