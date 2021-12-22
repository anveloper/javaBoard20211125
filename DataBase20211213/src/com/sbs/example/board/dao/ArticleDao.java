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

	public int doWrite(String title, String body) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
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

		sql.append("SELECT * FROM article");
		sql.append("ORDER BY id DESC");
		
		
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

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);

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
}
