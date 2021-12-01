package Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Board.controller.Controller;
import Board.controller.articleController;
import Board.dto.Article;

public class boardMain { // 다시 만들어 보기

	public static void main(String[] args) {
		List<Article> articles = new ArrayList<>();
		Scanner sc = new Scanner(System.in);

		System.out.printf("** 콘솔형 게시판 **\n");
		
		while (true) { // 시스템 구현
			System.out.printf("명령어 :");
			String command = sc.nextLine();
			
			command.trim();
			if(command.equals("exit")) {
				System.out.printf("프로그램을 종료합니다.\n");
				break;
			}
			
			if(command.length() == 0) {
				System.out.printf("명령어를 입력하세요.\n");
				continue;
			}
			String[] commandBits = command.split(" ");
			if(commandBits.length < 2) {
				System.out.printf("올바른 명령이 아닙니다.\n");
			}

			String controllerName = commandBits[0];
			String actionMethod = commandBits[1];
			
			Controller controller = null;
			
			if(controllerName.equals("article")) {
				controller = new articleController();
			} else {
				System.out.printf("올바른 명령이 아닙니다.\n");
				System.out.printf(" * article --	: 게시글 기능\n");
			}
			
			controller.doAction(actionMethod);
				
				
		}
		sc.close();
		System.out.printf("** 프로그램 종료 **\n");
	}

}
