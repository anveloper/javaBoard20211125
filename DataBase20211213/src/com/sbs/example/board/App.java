package com.sbs.example.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
	public void run() {
		Scanner sc = new Scanner(System.in);

		Connection conn = null;
		PreparedStatement pstat = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			conn = DriverManager.getConnection(url, "root", "");

		} catch (ClassNotFoundException e) {
			System.out.printf("* 드라이버 로딩 실패\n");
		} catch (SQLException e) {
			System.out.println("* 에러 : " + e);
		}

		while (true) {
			System.out.printf("* 명령어 : ");
			String cmd = sc.nextLine();
			cmd = cmd.trim();

			if (cmd.equals("system exit")) {
				System.out.printf("* 시스템을 종료합니다.\n");
				break;
			}

			if (cmd.equals("article write")) {

				String title;
				String body;
				System.out.printf("* 게시글 작성 \n");
				System.out.printf("* 제목 : ");
				title = sc.nextLine();
				System.out.printf("* 내용 : ");
				body = sc.nextLine();

				try {
					String sql = "INSERT INTO article";
					sql += " SET regDate = NOW()";
					sql += ", updateDate = NOW()";
					sql += ", title = \"" + title + "\"";
					sql += ", `body` = \"" + body + "\"";
					pstat = conn.prepareStatement(sql);
					int affectedRows = pstat.executeUpdate();
					System.out.printf("affectedRows : %d\n", affectedRows);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.printf("* 게시글이 추가되었습니다.\n");
			} else if (cmd.equals("article list")) {
				System.out.printf("* 게시글 목록\n");
				List<Article> articles = new ArrayList<>();
				ResultSet rs = null;

				try {
					String sql = "SELECT * FROM article";
					sql += " ORDER BY id DESC";

					pstat = conn.prepareStatement(sql);
					rs = pstat.executeQuery(sql);
					System.out.printf("");
					while (rs.next()) {
						int id = rs.getInt("id");
						String regDate = rs.getString("regDate");
						String updateDate = rs.getString("updateDate");
						String title = rs.getString("title");
						String body = rs.getString("body");
						Article article = new Article(id, regDate, updateDate, title, body);
						articles.add(article);
					}
					if (articles.size() == 0) {
						System.out.printf("* 표시할 게시글이 없습니다.\n");
					}
					System.out.printf("번호	| 등록날짜	| 수정날짜	| 제목	|\n");
					for (Article article : articles) {
						System.out.printf("%d	| %s	| %s	| %s	| %s	\n", article.id, article.regDate,
								article.updateDate, article.title, article.body);
					}
				} catch (SQLException e) {
					e.printStackTrace();
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

				try {
					String sql = "UPDATE article";
					sql += " SET regDate = NOW()";
					sql += ", updateDate = NOW()";
					sql += ", title = \"" + title + "\"";
					sql += ", `body` = \"" + body + "\"";
					sql += " WHERE id =" + id;
					pstat = conn.prepareStatement(sql);
					pstat.executeUpdate();

				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.printf("* 게시글이 수정되었습니다.\n");
			} else {
				System.out.printf("* 잘못된 명령어입니다.\n");
			}

		}
		
		sc.close();
		
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close(); // 연결 종료
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (pstat != null && !pstat.isClosed()) {
				pstat.close(); // 연결 종료
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
