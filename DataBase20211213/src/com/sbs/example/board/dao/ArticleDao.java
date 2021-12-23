package com.sbs.example.board.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.board.dto.Article;
import com.sbs.example.board.util.DBUtil;
import com.sbs.example.board.util.SecSql;

public class ArticleDao {
	Connection conn;
	
	public ArticleDao(Connection conn){
		this.conn = conn;
		
	}

	public int doWrite(String title, String body, int logonMemberId) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", memberId = ?", logonMemberId);
		sql.append(", title = ?", title);
		sql.append(", body = ?", body);

		return DBUtil.insert(conn, sql);
	}


	public int getArticleCntById(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*)");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		return DBUtil.selectRowIntValue(conn, sql);
	}

	public List<Article> getArticles() {
		List<Article> articles = new ArrayList<>();
		SecSql sql = new SecSql();

		sql.append("SELECT a.*, m.name AS extra_writer");
		sql.append("FROM article AS a");
		sql.append("LEFT JOIN `member` AS m");
		sql.append("ON a.memberId = m.id");
		sql.append("ORDER BY a.id DESC");
		
		List<Map<String, Object>> articleListMap =  DBUtil.selectRows(conn, sql);
		
		for (Map<String, Object> articleMap : articleListMap) {
			articles.add(new Article(articleMap));
		}
		return articles;
	}

	public void doModify(String title, String body, int id) {
		SecSql sql = new SecSql();
		
		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()"); // regDate 입력을 안하면 됨.
		sql.append(", title = ?", title);
		sql.append(", body = ?", body);
		sql.append("WHERE id = ?", id);

		DBUtil.update(conn, sql);
	}

	public Article getArticleById(int id) {
		SecSql sql = new SecSql();
		
		sql.append("SELECT a.*, m.name AS extra_writer");
		sql.append("FROM article AS a");
		sql.append("LEFT JOIN `member` AS m");
		sql.append("ON a.memberId = m.id");
		sql.append("WHERE a.id = ?", id);
		
		
		Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
		
		Article article = new Article(articleMap);
		
		return article;
	}

	public void doDelete(int id) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", id);

		DBUtil.delete(conn, sql);
	}

	public int getMemberIdById(int id) {
		SecSql sql = new SecSql();
		
		sql.append("SELECT memberId FROM article");
		sql.append("WHERE id = ?", id);
				
		return DBUtil.selectRowIntValue(conn, sql);
	}

	public List<Article> getArticles(String keyword) {
		List<Article> articles = new ArrayList<>();
		SecSql sql = new SecSql();

		sql.append("SELECT a.*, m.name AS extra_writer");
		sql.append("FROM article AS a");
		sql.append("LEFT JOIN `member` AS m");
		sql.append("ON a.memberId = m.id");
		sql.append("ORDER BY a.id DESC");
		sql.append("WHERE a.title LIKE '%?%'", keyword);
		
		List<Map<String, Object>> articleListMap =  DBUtil.selectRows(conn, sql);
		
		for (Map<String, Object> articleMap : articleListMap) {
			articles.add(new Article(articleMap));
		}
		return articles;
	}

	public void increaseHit(int id) {
		SecSql sql = new SecSql();
		
		sql.append("UPDATE article");
		sql.append("SET hit = hit + 1");
		sql.append("WHERE id = ?", id);
		
		DBUtil.update(conn, sql);
	}
}
