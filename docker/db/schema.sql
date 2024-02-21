CREATE DATABASE IF NOT EXISTS tweets_db;
use tweets_db;
CREATE TABLE tweets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tweet VARCHAR(255),
	tag VARCHAR(255),
    average INT
);
CREATE VIEW analytics AS SELECT t.tag, AVG(t.average) AS average FROM tweets t GROUP BY t.tag;