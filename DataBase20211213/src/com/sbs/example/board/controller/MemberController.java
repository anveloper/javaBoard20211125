package com.sbs.example.board.controller;

import java.sql.Connection;
import java.util.Map;
import java.util.Scanner;

import com.sbs.example.board.Member;
import com.sbs.example.board.session.Session;
import com.sbs.example.board.util.DBUtil;
import com.sbs.example.board.util.SecSql;

public class MemberController extends Controller {
	
	public MemberController(Connection conn, Scanner sc, String cmd, Session ss) {
		this.conn = conn;
		this.sc = sc;
		this.cmd = cmd;
		this.ss = ss;
	}

	public void doJoin() {

		String loginId;
		String loginPw;
		String loginPwConfirm;
		String name;
		
		if(ss.isLogon() == true) {
			System.out.printf("* 로그인 상태입니다. 로그아웃 후 진행해주세요.\n");
			return;
		}
		
		SecSql sql = new SecSql();
		
		System.out.printf("* 회원가입 \n");

		int joinTry = 0;

		while (true) {
			sql = new SecSql();
			
			if (joinTry > 2) {
				System.out.printf("* 회원가입을 다시 시도해 주세요.\n");
				return;
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
				return;
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
				return;
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

	}

	public void doLogin() {

		String loginId;
		String loginPw;
		SecSql sql = new SecSql();
		
		if(ss.isLogon() == true) {
			System.out.printf("* 로그인 상태입니다.\n");
			return;
		}
		
		System.out.printf("* 회원 로그인\n");
		
		int joinTry = 0;
		
		while (true) {
			sql = new SecSql();
			
			if (joinTry > 2) {
				System.out.printf("* 로그인을 다시 시도해 주세요.\n");
				return;
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
				return;
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
			return;
		}
		ss.logonMemberId = member.id;
		ss.logonMember = member;

		System.out.printf("* %s(%s)님 환영합니다.\n", ss.logonMember.name, ss.logonMember.loginId);
	}

	public void doLogout() {
		
		if (ss.isLogon() == false) {
			System.out.printf("* 로그인 상태가 아닙니다.\n");
			return;
		}

		System.out.printf("* %s(%s)가 로그아웃 되었습니다.\n", ss.logonMember.name, ss.logonMember.loginId);
		ss.logonMemberId = -1;
		ss.logonMember = null;
	}

	public void whoAmI() {

		if (ss.isLogon() == false) {
			System.out.printf("* 로그인 상태가 아닙니다. 로그인 후 이용해 주세요.\n");
			return;
		}
		System.out.printf("* 로그인된 회원정보 ===============================================\n");
		System.out.printf("| 고유번호 : %d\n", ss.logonMember.id);
		System.out.printf("| 등록일자 : %-22s 갱신일자 : %s\n", ss.logonMember.regDate, ss.logonMember.updateDate);
		System.out.printf("| 계정명   : %-22s 이름     : %s\n", ss.logonMember.loginId, ss.logonMember.name);
		System.out.printf("* =================================================================\n");
	}
	
}
