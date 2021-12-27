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

ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER updateDate;

SELECT a.*, m.name AS extra_writer
FROM article AS a
LEFT JOIN `member` AS m
ON a.memberId = m.id
WHERE a.id = 3;

SELECT a.*, m.name AS extra_writer
FROM article AS a
LEFT JOIN `member` AS m
ON a.memberId = m.id
ORDER BY a.id DESC;

SELECT memberId FROM article
WHERE id = 3;

ALTER TABLE article ADD COLUMN hit INT(10) UNSIGNED NOT NULL;
DESC article;

SELECT a.*, m.name AS extra_writer
FROM article AS a
LEFT JOIN `member` AS m
ON a.memberId = m.id
WHERE a.title LIKE '%새%'
ORDER BY a.id DESC;

SELECT a.*, m.name AS extra_writer
FROM article AS a
LEFT JOIN `member` AS m
ON a.memberId = m.id
ORDER BY a.id DESC
LIMIT 0, 500;

## page 수를 체크하기 위한 게시글 참고 구문
SELECT COUNT(*) FROM article
WHERE title LIKE '%33%';

## 게시글 추천을 위한 table 생성
CREATE TABLE `like`(
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(id),
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    articleId INT(10) UNSIGNED NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    liketype TINYINT(1) NOT NULL
); ## liketype type 1은 추천, type 2는 비추천
DESC `like`;
    