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
SET updateDate = NOW(), title = "수정확인", `body`="수정확인"
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

##일정 수의 게시글 목록만 추출하기 위한 구문
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
create table `like`(
    id int(10) unsigned not null auto_increment,
    primary key(id),
    regDate datetime not null,
    updateDate datetime not null,
    articleId int(10) unsigned not null,
    memberId int(10) unsigned not null,
    likeType tinyint(1) not null
); ## liketype type 1은 추천, type 2는 비추천

drop table `like`;
desc `like`;
select * from `like`;
select * from article;

## likeCheckCnt
select count(*) From `like`
where articleId = 33 and memberId = 2;

## likeCheck CASE문 연습
select 
case when count(*) != 0
then likeType else 0 end
from `like`
where articleId = 30 and memberId = 2;

## like count
Select Count(*) from `like`
where articleId = 32 AND likeType = 2;

## comment 기능 부

CREATE TABLE `comment`(
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(id),
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    articleId INT(10) UNSIGNED NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    title char(100) not null,
    `body` CHAR(100) NOT NULL
);

desc `comment`;

select memberId from `comment`
where id = 1;

select * from `comment`;

## 댓글 수정
update `comment`
set updateDate = NOW(),
title = "새로운 제목",
`body` = "새로운 내용"
where id = 2;

select count(*) from `comment`
where id = 1 and articleId = 32;

select * from article;

## 수정부에 페이징 없이 전체 리스트 출력
SELECT c.*, m.name AS extra_writer
FROM `comment` AS c
LEFT JOIN `member` AS m
ON c.memberId = m.id
WHERE c.articleId = 32
ORDER BY c.id DESC;

##댓글 수 계산
select count(*) from `comment`
where articleId = 32;

## 댓글 페이징
select c.*, m.name as extra_writer
from `comment` as c
left join `member` as m
on c.memberId = m.id
where c.articleId = 32
order by c.id desc
limit 0, 5;

insert into `comment`
set regDate = now(), updateDate = NOW(),
articleID = 32, memberId = 2,
title = '페이징1', `body` = '페이징1';

INSERT INTO `comment`
SET regDate = NOW(), updateDate = NOW(),
articleID = 32, memberId = 2,
title = '페이징2', `body` = '페이징2';

INSERT INTO `comment`
SET regDate = NOW(), updateDate = NOW(),
articleID = 32, memberId = 2,
title = '페이징3', `body` = '페이징3';

INSERT INTO `comment`
SET regDate = NOW(), updateDate = NOW(),
articleID = 32, memberId = 2,
title = '페이징4', `body` = '페이징4';

select * from `comment`
where articleId = 32;