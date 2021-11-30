package board.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import board.container.Container;
import board.dto.Member;
import board.util.Util;

public class MemberController extends Controller {
	private Scanner sc;
	private List<Member> members;
	private String command;
	private String actionMethodName;
	public int lastId;

	public MemberController(Scanner sc) {
		this.sc = sc;
		this.lastId = 0;
		members = Container.memberDao.members;
	}

	public void doAction(String command, String actionMethodName) {
		this.command = command;

		switch (actionMethodName) {
		case "join":
			if (isLogon() == false) {
				System.out.printf("로그아웃 후 이용가능합니다.\n");
				return;
			}
			doJoin();
			break;
		case "login":
			doLogin();
			break;
		case "logout":
			doLogout();
			break;
		case "whoami":
			checkId();
			break;
		case "list":
			showList();
			break;
		default:
			System.out.printf("존재하지 않는 명령어 입니다.\n");
			break;
		}
	}

	public void doAction(String command, String actionMethodName, Member logonMember) {
		this.command = command;
		this.doAction(command, actionMethodName); // article과 다르게 멤버 정보가 있으니 따로 명령문을 만들필요가 없을듯?
	}

	private void doJoin() {
		int id = ++lastId;
		String regDate = Util.getCurrentDate();

		String loginId = null;
		while (true) {
			System.out.printf("가입 ID : ");
			loginId = sc.nextLine();

			if (isJoinableLoginId(loginId) == false) {
				System.out.printf("%s(은)는 이미 사용중이인 아이디 입니다.\n", loginId);
				continue;
			}
			break;
		}
		String loginPw = null;
		while (true) {
			System.out.printf("가입 PW : ");
			loginPw = sc.nextLine();
			System.out.printf("가입 PW 재확인 : ");
			String loginPwConfirm = sc.nextLine();
			if (loginPw.equals(loginPwConfirm) == false) {
				System.out.printf("비밀번호 다름");
				continue;
			}
			break;
		}
		System.out.printf("이름 : ");
		String name = sc.nextLine();
		Member member = new Member(id, regDate, loginId, loginPw, name);
		members.add(member);
		System.out.printf("%d번 회원등록이 완료되었습니다.\n", id);
	}

	private void doLogin() {
		String loginId = null;
		Member currentMember = null;
		if (isLogon() == true) {
			System.out.printf("이미 %s(%s)로 로그인된 상태입니다.\n", logonMember.loginId, logonMember.name);
			return;
		}
		while (true) {
			System.out.printf("로그인 ID : ");
			loginId = sc.nextLine();

			if (isLoginId(loginId) == -1) {
				System.out.printf("%s(은)는 없는 아이디 입니다.\n", loginId);
				return;
			}
			int loginIndex = isLoginId(loginId);
			System.out.printf("%s(으)로 로그인 합니다.\n", loginId);
			currentMember = members.get(loginIndex);
			break;
		}
		String loginPw = null;
		while (true) {
			System.out.printf("%s PW : ", currentMember.loginId);
			loginPw = sc.nextLine();
			if (loginPw.equals(currentMember.loginPw) == false) {
				System.out.printf("비밀번호가 다릅니다.\n");
				continue;
			}
			break;
		}
		System.out.printf("%s님 환영합니다.\n", currentMember.name);
		logonMember = currentMember;
	}

	private void doLogout() {
		if (!isLogon()) {
			System.out.printf("로그아웃 상태입니다.\n");
			return;
		}
		System.out.printf("%s가 로그아웃 되었습니다.\n", logonMember.loginId);
		this.logonMember = null;
	}

	private void checkId() {
		if (this.logonMember == null) {
			System.out.printf("로그아웃 상태입니다.\n");
		} else {
			System.out.printf(" * 로그인된 회원정보 * \n");
			System.out.printf("로그인 ID : %s\n", logonMember.loginId);
			System.out.printf("로그인 이름 : %s\n", logonMember.name);
		}
	}

	private void showList() {
		if (isAdmin()) {
			System.out.printf(" * member list 기능은 관리자로 로그인 하셔야 이용할 수 있습니다.\n");
			return;
		}

		if (members.size() == 0) {
			System.out.printf("등록된 회원이 없습니다.\n");
			return;
		}
		List<Member> forListMember = members;
		String searchKeyword = command.substring("member list".length()).trim();
		if (searchKeyword.length() > 0) {
			System.out.printf("검색어 : %s\n", searchKeyword);
			forListMember = new ArrayList<>();

			for (Member member : members) {
				if (member.loginId.contains(searchKeyword)) {
					forListMember.add(member);
				}
			}
			if (forListMember.size() == 0) {
				System.out.printf("검색된 게시글이 없습니다.\n");
				return;
			}
		}
		System.out.printf(" * 등록된 회원 목록 * \n");
		System.out.println("번호	| 날짜		| 회원ID		| 회원명 ");
		for (int i = 0; i < forListMember.size(); i++) {
			Member currentMember = forListMember.get(i);
			System.out.printf("%d	| %s	| %-21s | %s \n", currentMember.id, currentMember.regDate,
					currentMember.loginId, currentMember.name);
		}
	}

// ==================================================================================================================	

//	private boolean isLogon() {
//		return logonMember != null;
//	}  // 상위 클래스에 정의됨.

	private boolean isAdmin() {
		return (logonMember == null) || (logonMember.id != members.get(0).id);
	}

	private int isLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);
		if (index == -1) {
			return -1;
		}
		return index;
	}

	private int getMemberIndexByLoginId(String loginId) {
		int i = 0;

		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	private boolean isJoinableLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);
		if (index == -1) {
			return true;
		}
		return false;
	}

	public void makeTestData() {
		System.out.printf("테스트를 위한 계정을 생성합니다.\n");
		members.add(new Member(0, Util.getCurrentDate(), "test1", "1234", "testId1"));
		members.add(new Member(0, Util.getCurrentDate(), "test2", "1234", "testId2"));
		members.add(new Member(0, Util.getCurrentDate(), "test3", "1234", "testId3"));
		members.add(new Member(0, Util.getCurrentDate(), "admin", "admin", "관리자"));
	}
}