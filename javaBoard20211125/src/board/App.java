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
			System.out.print("명령어 : ");
			String command = sc.nextLine();

			if (command.equals("system exit")) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}

			command.trim();
			if (command.length() == 0) {
				continue;
			}
			String[] commandBits = command.split(" ");

			if (commandBits.length == 1) {
				System.out.printf("존재하지 않는 명령어입니다.\n");
				continue;
			}

			Controller controller = null;

			String controllerName = commandBits[0];
			String actionMethodName = commandBits[1];

			if (controllerName.equals("article")) {
				controller = articleController;
			} else if (controllerName.equals("member")) {
				controller = memberController;
			} else {
				System.out.printf("존재하지 않는 명령어입니다.\n");
				continue;
			}

			this.logonMember = memberController.logonMember;

			if (logonMember == null) {
				controller.doAction(command, actionMethodName);
			} else {
				controller.doAction(command, actionMethodName, logonMember);
			}

		}
		sc.close();
		System.out.printf("프로그램 종료\n");
	}
}
