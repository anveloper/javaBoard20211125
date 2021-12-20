package com.sbs.example.board.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.sbs.example.board.Article;
import com.sbs.example.board.session.Session;
import com.sbs.example.board.util.DBUtil;
import com.sbs.example.board.util.SecSql;

public class ArticleController extends Controller {

	public ArticleController(Connection conn, Scanner sc, String cmd, Session ss) {
		this.conn = conn;
		this.sc = sc;
		this.cmd = cmd;
		this.ss = ss;
	}

	public void doWrite() {
		String title;
		String body;
		System.out.printf("* 게시글 작성 \n");
		System.out.printf("* 제목 : ");
		title = sc.nextLine();
		System.out.printf("* 내용 : ");
		body = sc.nextLine();

		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", body = ?", body);

		int id = DBUtil.insert(conn, sql);

		System.out.printf("* %d번 게시글이 추가되었습니다.\n", id);
	}

	public void showList() {
		System.out.printf("* 게시글 목록\n");
		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();

		sql.append("SELECT * FROM article");
		sql.append("ORDER BY id DESC");

		List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);
		for (Map<String, Object> articleMap : articleListMap) {
			articles.add(new Article(articleMap));
		}

		if (articles.size() == 0) {
			System.out.printf("* 표시할 게시글이 없습니다.\n");
			return;
		}

		System.out.printf("번호	| 등록날짜		| 수정날짜		| 제목			| 내용\n");
		System.out.println(
				"================================================================================================");
		for (Article article : articles) {
			System.out.printf("%d	| %s	| %s	| %-14s	| %s	\n", article.id, article.regDate,
					article.updateDate, article.title, article.body);
		}
	}

	public void showDetail() {

		boolean isInt = cmd.split(" ")[2].matches("-?\\d+");
		if (!isInt) {
			System.out.println("* 게시글의 ID를 숫자로 입력해주세요.\n");
			return;
		}

		int id = Integer.parseInt(cmd.split(" ")[2].trim());

		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*)");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);

		int articleCount = DBUtil.selectRowIntValue(conn, sql);

		if (articleCount == 0) {
			System.out.printf("* %d번 게시글이 존재하지 않습니다.\n", id);
			return;
		}

		sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);

		Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
		Article article = new Article(articleMap);
		System.out.printf("* 게시글 상세보기\n");
		System.out.printf("* 게시글 번호 : %d\n", article.id);
		System.out.printf("* 등록일자 : %s		갱신일자 : %s\n", article.regDate, article.updateDate);
		System.out.printf("* 제목 : %s\n", article.title);
		System.out.printf("* 내용 =========================================================================\n");
		System.out.printf(" >> %s \n\n\n", article.body);
	}

	public void doModify() {
		SecSql sql = new SecSql();
		boolean isInt = cmd.split(" ")[2].matches("-?\\d+");
		if (!isInt) {
			System.out.println("* 게시글의 ID를 숫자로 입력해주세요.\n");
			return;
		}

		int id = Integer.parseInt(cmd.split(" ")[2].trim());

		
		sql.append("SELECT COUNT(*)");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);

		int articleCount = DBUtil.selectRowIntValue(conn, sql);

		if (articleCount == 0) {
			System.out.printf("* %d번 게시글이 존재하지 않습니다.\n", id);
			return;
		}

		sql = new SecSql();

		String title;
		String body;

		System.out.printf("* 게시글 수정\n");
		System.out.printf("* 새 제목 : ");
		title = sc.nextLine();
		System.out.printf("* 새 내용 : ");
		body = sc.nextLine();

		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()"); // regDate 입력을 안하면 됨.
		sql.append(", title = ?", title);
		sql.append(", body = ?", body);
		sql.append("WHERE id = ?", id);

		DBUtil.update(conn, sql);

		System.out.printf("* %d번 게시글이 수정되었습니다.\n", id);
	}

	public void doDelete() {
		SecSql sql = new SecSql();
		int id = Integer.parseInt(cmd.split(" ")[2].trim());

		boolean isInt = cmd.split(" ")[2].matches("-?\\d+");
		if (!isInt) {
			System.out.println("* 게시글의 ID를 숫자로 입력해주세요.\n");
			return;
		}
		sql.append("SELECT COUNT(*)");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);

		int articleCount = DBUtil.selectRowIntValue(conn, sql);

		if (articleCount == 0) {
			System.out.printf("* %d번 게시글이 존재하지 않습니다.\n", id);
			return;
		}

		sql = new SecSql();

		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", id);

		DBUtil.delete(conn, sql);

		System.out.printf("* %d번 게시글이 삭제되었습니다.\n", id);

	}
	
	

}
