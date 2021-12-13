package com.sbs.example.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

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
				//======================================================================
				Connection conn = null; // DB 접속 객체
				PreparedStatement pstat = null; // SQL구문을 실행하는 역할
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

					conn = DriverManager.getConnection(url, "root", "");
					String sql = "INSERT INTO article";
					sql += " SET regDate = NOW()";
					sql += ", updateDate = NOW()";
					sql += ", title = \"" + title + "\"";
					sql += ", `body` = \"" + body + "\"";
					pstat = conn.prepareStatement(sql);
					int affectedRows = pstat.executeUpdate();
					System.out.printf("affectedRows : %d\n", affectedRows);
				} catch (ClassNotFoundException e) {
					System.out.println("드라이버 로딩 실패");
				} catch (SQLException e) {
					System.out.println("에러: " + e);
				} finally { // 예외 상황이든 아니든 무조건 마지막에 실행하는 finally
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
				//======================================================================
				System.out.printf("* 게시글이 추가되었습니다.\n");
			} else {
				System.out.printf("* 잘못된 명령어입니다.\n");
			}

		}
		sc.close();
	}
}