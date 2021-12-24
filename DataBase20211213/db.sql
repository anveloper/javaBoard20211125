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

alter table article add column memberId int(10) unsigned not null after updateDate;

select a.*, m.name as extra_writer
from article as a
LEFT join `member` as m
on a.memberId = m.id
where a.id = 3;

select a.*, m.name as extra_writer
from article as a
left join `member` as m
on a.memberId = m.id
order by a.id desc;

select memberId from article
where id = 3;

alter table article add column hit int(10) unsigned not null;
desc article;

SELECT a.*, m.name AS extra_writer
FROM article AS a
LEFT JOIN `member` AS m
ON a.memberId = m.id
WHERE a.title LIKE '%새%'
ORDER BY a.id DESC;
