package com.sbs.example.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.sbs.example.board.controller.ArticleController;
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

				int actionResult = doAction(conn, sc, cmd, ss);
				if (actionResult == -1)
					break;
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

	private int doAction(Connection conn, Scanner sc, String cmd, Session ss) {
		
		MemberController memberController = new MemberController(conn, sc, cmd, ss);
		ArticleController articleController = new ArticleController(conn, sc, cmd, ss);
		
		
//==============member===============================================
		if (cmd.equals("member join") || cmd.equals("join")) {
			
			memberController.doJoin();
			
		} else if (cmd.equals("member login") || cmd.equals("login")) {
			
			memberController.doLogin(); 
			
		} else if (cmd.equals("member logout") || cmd.equals("logout")) {
			
			memberController.doLogout();

		} else if (cmd.equals("member whoami") || cmd.equals("whoami")) {
			
			memberController.whoAmI();
		}

//==============article===============================================		
		else if (cmd.equals("article write") || cmd.equals("write")) {

			articleController.doWrite();
			
		} else if (cmd.equals("article list") || cmd.equals("list")) {
		
			articleController.showList();
			
		} else if (cmd.startsWith("article detail ")) {

			articleController.showDetail();
			
		} else if (cmd.startsWith("article modify ")) {

			articleController.doModify();
			
		} else if (cmd.startsWith("article delete ")) {

			articleController.doDelete();
			
		} else {
			System.out.printf("* 잘못된 명령어입니다.\n");
		}
		return 0;
	}

//==========================================================================================================

}
