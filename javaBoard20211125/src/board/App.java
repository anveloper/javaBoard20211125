package board;

import java.util.Scanner;

import board.controller.ArticleController;
import board.controller.Controller;
import board.controller.MemberController;
import board.dto.Member;

public class App {
	public Member logonMember = null;

	public void start() {
		// 메인 함수에서 기능을 받은 app함수는 명령어 처리만 실시하고, 세부 행동은 controller에게 이양한다.
		System.out.println("==프로그램 시작==");
		Scanner sc = new Scanner(System.in);
		MemberController memberController = new MemberController(sc);
		ArticleController articleController = new ArticleController(sc);

		articleController.makeTestData();
		memberController.makeTestData();

		while (true) {
			System.out.print("명령어(* help) : ");
			String command = sc.nextLine();

			if (command.equals("system exit")) {
				System.out.println("프로그램을 종료합니다.");
				break;
			} // 종료 기능 추가

			if (command.equals("help")) {
				showHelp();
				continue;
			}

			command.trim();
			if (command.length() == 0) {
				continue;
			} // 입력된 명령어의 앞뒤 여백 제거
			String[] commandBits = command.split(" ");

			if (commandBits.length == 1) {
				System.out.printf("존재하지 않는 명령어입니다.\n");
				continue;
			} // 입력된 명령어의 길이가 1단어이다. >> controller의 행동을 수행하기 위한 조건에 맞지 않음.

			Controller controller = null; // 기본 controller의 초기 상태

			String controllerName = commandBits[0]; // 입력된 명령어의 1단어 부분으로 controller 호출
			String actionMethodName = commandBits[1]; // controller가 수행해야 할 명령 입력

			if (controllerName.equals("article")) {
				controller = articleController;
			} else if (controllerName.equals("member")) {
				controller = memberController;
			} else {
				System.out.printf("존재하지 않는 명령어입니다.\n");
				continue;
			} // controller 이름 조건문

			this.logonMember = memberController.logonMember;

			String actionName = controller + "/" + actionMethodName;

			switch (actionName) {
			case "article/delete":
			case "article/modify":
			case "member/logout":
				if (Controller.isLogon() == false) {
					System.out.printf("로그인 후 이용해주세요...!\n");
					continue;
				}
				break;
			}
			switch (actionName) {
			case "member/login":
			case "member/join":
				if (Controller.isLogon()) {
					System.out.printf("로그아웃 후 이용해주세요...!\n");
					continue;
				}
				break;
			}

			if (logonMember == null) { // 비회원 기능을 별도로 작동
				controller.doAction(command, actionMethodName);
			} else { // 회원이 로그인 되었을때, controller에 회원 정보를 입력함.
				controller.doAction(command, actionMethodName, logonMember);
			}

		}
		sc.close();
		System.out.printf("프로그램 종료\n");
	}

	private void showHelp() {
		System.out.printf("** 도움말 **\n");
		System.out.printf("* help				: 도움말\n");
		System.out.printf("* system exit			: 프로그램 종료\n");
		System.out.printf("* article	--		: 게시글 기능\n");
		System.out.printf("		write		: 게시글 작성\n");
		System.out.printf("				  * 비회원은 작성만 가능, 관리자로 삭제/수정 가능\n");
		System.out.printf("		list		: 게시글 목록\n");
		System.out.printf("		list (검색어)	: 게시글 제목 검색\n");
		System.out.printf("		detail (번호)	: 게시글 확인\n");
		System.out.printf("		modify (번호)	: 게시글 수정(회원만 가능)\n");
		System.out.printf("		delete (번호)	: 게시글 삭제(회원만 가능)\n");
		System.out.printf("* member	--		: 회원 기능\n");
		System.out.printf("		join		: 회원 등록\n");
		System.out.printf("		login		: 회원 로그인\n");
		System.out.printf("		logout		: 회원 로그아웃\n");
		System.out.printf("		whoami		: 접속된 회원 정보\n");
		System.out.printf("		list		: 회원 목록(관리자만 가능)\n");

	}
}
