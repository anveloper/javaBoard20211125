package com.sbs.example.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.sbs.example.board.util.DBUtil;
import com.sbs.example.board.util.SecSql;

public class App {
	public void run() {
		Scanner sc = new Scanner(System.in);

		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			conn = DriverManager.getConnection(url, "root", "");

			while (true) {
				System.out.printf("* 명령어 : ");
				String cmd = sc.nextLine();
				cmd = cmd.trim();

				if (cmd.equals("system exit")) {
					System.out.printf("* 시스템을 종료합니다.\n");
					break;
				}

				int actionResult = doAction(conn, sc, cmd);
				if (actionResult == -1) break;
			}
		} catch (ClassNotFoundException e) {
			System.out.printf("* 드라이버 로딩 실패\n");
		} catch (SQLException e) {
			System.out.println("* 에러 : " + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close(); // 연결 종료
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		sc.close();
	}

	private int doAction(Connection conn,Scanner sc, String cmd) {
		if (cmd.equals("article write")) {

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
			
		} else if (cmd.equals("article list")) {
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
				return 0;
			}

			System.out.printf("번호	| 등록날짜		| 수정날짜		| 제목			| 내용\n");
			System.out.println(
					"================================================================================================");
			for (Article article : articles) {
				System.out.printf("%d	| %s	| %s	| %-14s	| %s	\n", article.id, article.regDate,
						article.updateDate, article.title, article.body);
			}

		} else if (cmd.startsWith("article modify")) {
			int id = Integer.parseInt(cmd.split(" ")[2].trim());

			String title;
			String body;

			System.out.printf("* 게시글 수정\n");
			System.out.printf("* 새 제목 : ");
			title = sc.nextLine();
			System.out.printf("* 새 내용 : ");
			body = sc.nextLine();

			SecSql sql = new SecSql();

			sql.append("UPDATE article");
			sql.append("SET regDate = NOW()");
			sql.append(", updateDate = NOW()");
			sql.append(", title = ?", title);
			sql.append(", body = ?", body);
			sql.append("WHERE id = ?", id);
			
			DBUtil.update(conn, sql);

			System.out.printf("* %d번 게시글이 수정되었습니다.\n", id);
			
		} else if (cmd.startsWith("article delete")) {
			int id = Integer.parseInt(cmd.split(" ")[2].trim());
			
			SecSql sql = new SecSql();

			sql.append("DELETE FROM article");
			sql.append("WHERE id = ?", id);
			
			DBUtil.delete(conn, sql);
			
			System.out.printf("* %d번 게시글이 삭제되었습니다.\n", id);
			
		} else {
			System.out.printf("* 잘못된 명령어입니다.\n");
		}
		return 0;
	}
}
