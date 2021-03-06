package com.sbs.example.board.controller;

import java.sql.Connection;
import java.util.Map;
import java.util.Scanner;

import com.sbs.example.board.dto.Member;
import com.sbs.example.board.service.MemberService;
import com.sbs.example.board.session.Session;

public class MemberController extends Controller {

	MemberService memberService;

	public MemberController(Connection conn, Scanner sc, String cmd, Session ss) {
		this.sc = sc;
		this.cmd = cmd; // cmd와 ss는 controller에서 처리하고 service로 넘긴다.
		this.ss = ss;
		memberService = new MemberService(conn);
	}

	public void doAction() {
		if (cmd.equals("member join")) {
			doJoin();
		} else if (cmd.equals("member login")) {
			doLogin();
		} else if (cmd.equals("member logout")) {
			doLogout();
		} else if (cmd.equals("member whoami")) {
			whoAmI();
		} else {
			System.out.printf("* 존재하지 않는 명령어입니다.\n");
		}
	}

	private void doJoin() {

		String loginId;
		String loginPw;
		String loginPwConfirm;
		String name;

		if (ss.isLogon() == true) {
			System.out.printf("* 로그인 상태입니다. 로그아웃 후 진행해주세요.\n");
			return;
		}

		System.out.printf("* 회원가입 \n");

		int joinTry = 0;

		while (true) {

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

			int memberCount = memberService.getMemberCntByLoginId(loginId);

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
		int id = memberService.getMemberIdByNewId(loginId, loginPw, name);

		System.out.printf("* %d번 회원이 추가되었습니다.\n", id);

	}

	private void doLogin() {

		String loginId;
		String loginPw;

		if (ss.isLogon() == true) {
			System.out.printf("* 로그인 상태입니다.\n");
			return;
		}

		System.out.printf("* 회원 로그인\n");

		int joinTry = 0;

		while (true) {

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

			int memberCnt = memberService.getMemberCntByLoginId(loginId);
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

		Map<String, Object> foundMember = memberService.getMemberByLoginId(loginId);
		Member member = new Member(foundMember);

		if (!member.getLoginPw().equals(loginPw)) {
			System.out.printf("* 비밀번호가 일치하지 않습니다.\n");
			return;
		}
		ss.setLogonMemberId(member.getId());
		ss.setLogonMember(member);
		
		System.out.printf("* %s(%s)님 환영합니다.\n", ss.getLogonMember().getName(), ss.getLogonMember().getLoginId());
	}

	private void doLogout() {

		if (ss.isLogon() == false) {
			System.out.printf("* 로그인 상태가 아닙니다.\n");
			return;
		}

		System.out.printf("* %s(%s)가 로그아웃 되었습니다.\n", ss.getLogonMember().getName(), ss.getLogonMember().getLoginId());
		ss.setLogonMemberId(-1);
		ss.setLogonMember(null);
	}

	private void whoAmI() {

		if (ss.isLogon() == false) {
			System.out.printf("* 로그인 상태가 아닙니다. 로그인 후 이용해 주세요.\n");
			return;
		}
		System.out.printf("* 로그인된 회원정보 ===============================================\n");
		System.out.printf("| 고유번호 : %d\n", ss.getLogonMember().getId());
		System.out.printf("| 등록일자 : %-22s 갱신일자 : %s\n", ss.getLogonMember().getRegDate(), ss.getLogonMember().getUpdateDate());
		System.out.printf("| 계정명   : %-22s 이름     : %s\n", ss.getLogonMember().getLoginId(), ss.getLogonMember().getName());
		System.out.printf("* =================================================================\n");
	}

}
