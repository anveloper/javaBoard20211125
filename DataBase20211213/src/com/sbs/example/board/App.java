package com.sbs.example.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.sbs.example.board.controller.ArticleController;
import com.sbs.example.board.controller.Controller;
import com.sbs.example.board.controller.MemberController;
import com.sbs.example.board.session.Session;

public class App {
	public void run() {
		Scanner sc = new Scanner(System.in);
		Session ss = new Session();
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			conn = DriverManager.getConnection(url, "root", "");

			while (true) {
				System.out.printf("> 명령어 : ");
				String cmd = sc.nextLine();
				cmd = cmd.trim();
				
				if (cmd.equals("system exit")) {
					System.out.printf("* 시스템을 종료합니다.\n");
					break;
				}
				String[] cmdBits = cmd.split(" ");
				Controller controller;
				
				MemberController memberController = new MemberController(conn, sc, cmd, ss);
				ArticleController articleController = new ArticleController(conn, sc, cmd, ss);
				
				if(cmdBits.length < 2) {
					System.out.printf("* 존재하지 않는 명령어입니다.\n");
					continue;
				}
				
				String controllerName = cmd.split(" ")[0];
				if(controllerName.equals("article")) {
					controller = articleController;
				} else if(controllerName.equals("member")){
					controller = memberController;
				} else {
					System.out.printf("* 잘못된 명령어입니다.\n");
					continue;
				}

				controller.doAction();
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

}
