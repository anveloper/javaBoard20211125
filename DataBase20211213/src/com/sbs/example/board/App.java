package com.sbs.example.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.sbs.example.board.session.Session;
import com.sbs.example.board.util.DBUtil;
import com.sbs.example.board.util.SecSql;

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
				System.out.printf("* 명령어 : ");
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
//==============member===============================================
		if (cmd.equals("member join")) {
			String loginId;
			String loginPw;
			String loginPwConfirm;
			String name;
			
			SecSql sql = new SecSql();
			
			System.out.printf("* 회원가입 \n");

			int joinTry = 0;

			while (true) {
				sql = new SecSql();
				
				if (joinTry > 2) {
					System.out.printf("* 회원가입을 다시 시도해 주세요.\n");
					return 0;
				}
				System.out.printf("> 로그인 아이디 : ");
				loginId = sc.nextLine();

				if (loginId.length() == 0) {
					System.out.printf("* 아이디를 입력해주세요.\n");
					joinTry++;
					continue;
				}				

				sql.append("SELECT COUNT(*) FROM `member`");
				sql.append("WHERE loginId = ?", loginId);

				int memberCount = DBUtil.selectRowIntValue(conn, sql);

				if (memberCount > 0) {
					System.out.printf("* 이미 존재하는 아이디 입니다.\n");
					joinTry++;
					continue;
				}
				System.out.printf("* 사용가능한 아이디 입니다.\n");
				break;
			}
			joinTry = 0;
			while (true) {
				if (joinTry > 2) {
					System.out.printf("* 회원가입을 다시 시도해 주세요.\n");
					return 0;
				}
				System.out.printf("> 로그인 비밀번호 : ");
				loginPw = sc.nextLine();
				if (loginPw.length() == 0) {
					System.out.printf("* 비밀번호를 입력해주세요.\n");
					joinTry++;
					continue;
				}
				System.out.printf("> 로그인 비밀번호 확인 : ");
				loginPwConfirm = sc.nextLine();
				if (!loginPw.equals(loginPwConfirm)) {
					System.out.printf("* 비밀번호가 서로 다릅니다.\n");
					joinTry++;
					continue;
				}
				System.out.printf("* 사용가능한 비밀번호 입니다.\n");
				break;
			}
			joinTry = 0;
			while (true) {
				if (joinTry > 2) {
					System.out.printf("* 회원가입을 다시 시도해 주세요.\n");
					return 0;
				}
				System.out.printf("> 로그인 이름 : ");
				name = sc.nextLine();
				if (name.length() == 0) {
					System.out.printf("* 아이디를 입력해주세요.\n");
					joinTry++;
					continue;
				}
				break;
			}

			sql = new SecSql();

			sql.append("INSERT INTO member");
			sql.append("SET regDate = NOW()");
			sql.append(", updateDate = NOW()");
			sql.append(", loginId = ?", loginId);
			sql.append(", loginPw = ?", loginPw);
			sql.append(", name = ?", name);

			int id = DBUtil.insert(conn, sql);

			System.out.printf("* %d번 회원이 추가되었습니다.\n", id);

		} else if (cmd.equals("member login")) {
			String loginId;
			String loginPw;
			SecSql sql = new SecSql();
			
			if(ss.isLogon() == true) {
				System.out.printf("* 로그인 상태입니다.\n");
				return 0;
			}
			
			System.out.printf("* 회원 로그인\n");
			
			int joinTry = 0;
			
			while (true) {
				sql = new SecSql();
				
				if (joinTry > 2) {
					System.out.printf("* 로그인을 다시 시도해 주세요.\n");
					return 0;
				}
				System.out.printf("> 로그인 아이디 : ");
				loginId = sc.nextLine();
				if (loginId.length() == 0) {
					System.out.printf("* 아이디를 입력해 주세요.\n");
					joinTry++;
					continue;
				}
				sql.append("SELECT COUNT(*) FROM member");
				sql.append("WHERE loginId = ?", loginId);

				int memberCnt = DBUtil.selectRowIntValue(conn, sql);
				if (memberCnt == 0) {
					System.out.println("* 아이디가 존재하지 않습니다.");
					joinTry++;
					continue;
				}
				break;
			}
			joinTry = 0;
			while (true) {
				if (joinTry > 2) {
					System.out.printf("* 로그인을 다시 시도해 주세요.\n");
					return 0;
				}
				System.out.printf("> 로그인 비밀번호 : ");
				loginPw = sc.nextLine();
				if (loginPw.length() == 0) {
					System.out.printf("* 비밀번호를 입력해 주세요.\n");
					joinTry++;
					continue;
				}
				break;
			}

			sql = new SecSql();

			sql.append("SELECT * FROM member");
			sql.append("WHERE loginId = ?", loginId);

			Map<String, Object> foundMember = DBUtil.selectRow(conn, sql);
			Member member = new Member(foundMember);
			
			if(!member.loginPw.equals(loginPw)) {
				System.out.printf("* 비밀번호가 일치하지 않습니다.\n");
				return 0;
			}
			
			ss.logonMember = member; // login 정보 유지를 위해 만들었는데..

			System.out.printf("* %s(%s)님 환영합니다.\n", ss.logonMember.name, ss.logonMember.loginId);

		} else if (cmd.equals("member logout")) {
			if (ss.isLogon() == false) {
				System.out.printf("* 로그인 상태가 아닙니다.\n");
				return 0;
			}

			System.out.printf("* %s(%s)가 로그아웃 되었습니다.\n", ss.logonMember.name, ss.logonMember.loginId);
			ss.logonMember = null;

		} else if (cmd.equals("member whoami")) {
			if (ss.isLogon() == false) {
				System.out.printf("* 로그인 상태가 아닙니다. 로그인 후 이용해 주세요.\n");
				return 0;
			}
			System.out.printf("* 로그인된 회원정보 ===============================================\n");
			System.out.printf("| 고유번호 : %d\n", ss.logonMember.id);
			System.out.printf("| 등록일자 : %-22s 갱신일자 : %s\n", ss.logonMember.regDate, ss.logonMember.updateDate);
			System.out.printf("| 계정명   : %-22s 이름     : %s\n", ss.logonMember.loginId, ss.logonMember.name);
			System.out.printf("* =================================================================\n");
		}

//==============article===============================================		
		else if (cmd.equals("article write")) {

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

		} else if (cmd.startsWith("article detail ")) {

			boolean isInt = cmd.split(" ")[2].matches("-?\\d+");
			if (!isInt) {
				System.out.println("* 게시글의 ID를 숫자로 입력해주세요.\n");
				return 0;
			}

			int id = Integer.parseInt(cmd.split(" ")[2].trim());

			SecSql sql = new SecSql();

			sql.append("SELECT COUNT(*)");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);

			int articleCount = DBUtil.selectRowIntValue(conn, sql);

			if (articleCount == 0) {
				System.out.printf("* %d번 게시글이 존재하지 않습니다.\n", id);
				return 0;
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

		} else if (cmd.startsWith("article modify ")) {

			boolean isInt = cmd.split(" ")[2].matches("-?\\d+");
			if (!isInt) {
				System.out.println("* 게시글의 ID를 숫자로 입력해주세요.\n");
				return 0;
			}

			int id = Integer.parseInt(cmd.split(" ")[2].trim());

			SecSql sql = new SecSql();

			sql.append("SELECT COUNT(*)");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);

			int articleCount = DBUtil.selectRowIntValue(conn, sql);

			if (articleCount == 0) {
				System.out.printf("* %d번 게시글이 존재하지 않습니다.\n", id);
				return 0;
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
			sql.append("SET regDate = NOW()");
			sql.append(", updateDate = NOW()");
			sql.append(", title = ?", title);
			sql.append(", body = ?", body);
			sql.append("WHERE id = ?", id);

			DBUtil.update(conn, sql);

			System.out.printf("* %d번 게시글이 수정되었습니다.\n", id);

		} else if (cmd.startsWith("article delete")) {
			int id = Integer.parseInt(cmd.split(" ")[2].trim());

			boolean isInt = cmd.split(" ")[2].matches("-?\\d+");
			if (!isInt) {
				System.out.println("* 게시글의 ID를 숫자로 입력해주세요.\n");
				return 0;
			}

			SecSql sql = new SecSql();

			sql.append("SELECT COUNT(*)");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);

			int articleCount = DBUtil.selectRowIntValue(conn, sql);

			if (articleCount == 0) {
				System.out.printf("* %d번 게시글이 존재하지 않습니다.\n", id);
				return 0;
			}

			sql = new SecSql();

			sql.append("DELETE FROM article");
			sql.append("WHERE id = ?", id);

			DBUtil.delete(conn, sql);

			System.out.printf("* %d번 게시글이 삭제되었습니다.\n", id);

		} else {
			System.out.printf("* 잘못된 명령어입니다.\n");
		}
		return 0;
	}

//==========================================================================================================

}
