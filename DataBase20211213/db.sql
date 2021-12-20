DROP DATABASE IF EXISTS text_board;

CREATE DATABASE text_board;
USE text_board;

CREATE TABLE article (

    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(id),
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

DESC article;
SELECT * FROM article;
## SELECT * FROM article WHERE id = 4;
## DELETE FROM article WHERE id > 1;

CREATE TABLE `member`(
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(id),
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(100) NOT NULL,
    loginPw CHAR(100) NOT NULL,
    `name` CHAR(100) NOT NULL
);

SELECT * FROM `member`;

DESC `member`;

INSERT INTO `member` 
SET regDate = NOW(), updateDate = NOW(),
loginId = 'admin', loginPw = 'admin', `name` = 'admin';

UPDATE article 
SET regDate = regDate, updateDate = NOW(), title = "수정확인", `body`="수정확인"
WHERE id = 2;

SELECT * FROM article;


