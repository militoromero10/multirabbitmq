create database if not exists tweets_db;
use tweets_db;
CREATE TABLE tweets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tweet VARCHAR(255),
	tag VARCHAR(255),
    average INT
);