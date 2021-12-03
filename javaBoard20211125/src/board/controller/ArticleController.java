package board.controller;

import java.util.List;
import java.util.Scanner;

import board.container.ArticleService;
import board.container.Container;
import board.dto.Article;
import board.dto.Member;
import board.util.Util;

public class ArticleController extends Controller {
	private Scanner sc;
	private String command;
	private String actionMethodName;
	private ArticleService articleService;

	public ArticleController(Scanner sc) {
		this.sc = sc;
		this.articleService = Container.articleService;
	}

	public void doAction(String command, String actionMethodName) {
		this.command = command;

		switch (actionMethodName) {
		case "write":
			doWrite();
			break;
		case "list":
			showList();
			break;
		case "detail":
			showDetail();
			break;
		case "modify":
			doModify();
			break;
		case "delete":
			doDelete();
			break;
		default:
			System.out.printf("* 존재하지 않는 명령어 입니다.\n");
			break;
		}
	}

	public void doAction(String command, String actionMethodName, Member logonMember) {
		this.command = command;
		this.logonMember = logonMember;
		this.doAction(command, actionMethodName);
	}

	private void doWrite() {
		int id = articleService.getNewId();
		String currentDate = Util.getCurrentDate();
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();
		int writerId = 0; // 비회원의 게시글은 관리자만 삭제/수정가능, 관리자 id가 0번임
		String writerName = "비회원";
		if (Controller.logonMember != null) {
			writerId = Controller.logonMember.id; // null값이 아닌 logonMember 라면(로그인 되어있다면), 그 id로 변경
			writerName = Controller.logonMember.loginId; // 작성자 이름 또한 logon 아이디로 변경
		}
		Article article = new Article(id, currentDate, title, body, writerId, writerName);
		
		articleService.write(article);
		
		System.out.printf("* %d번 게시물 등록이 완료되었습니다.\n", id);
	}

	private void showList() {
		if (articleService.getArticlesSize() == 0) {
			System.out.printf("* 게시물이 없습니다.\n");
			return;
		}
		String searchKeyword = command.substring("article list".length()).trim();
		
		List<Article> forPrintArticles = articleService.getForPtintArticles(searchKeyword);
		
		if(forPrintArticles == null) {
			System.out.printf("* 검색된 게시글이 없습니다.\n");
			return;
		}
		
		System.out.printf("=== 게시물 목록 ===\n");
		System.out.println("번호	| 날짜		| 제목				     | 작성자	          | 조회수");
		for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
			Article currentArticle = forPrintArticles.get(i);
			System.out.printf("%d	| %s	| %-29s | %-10s	  | %d\n", currentArticle.id, currentArticle.regDate,
					currentArticle.title, currentArticle.writerName, currentArticle.hit);
		}
	}

	private void showDetail() {
		String[] commandBits = command.split(" ");

		if (commandBits.length < 3) {
			System.out.printf("* 게시글 번호를 입력하지 않았습니다.\n");
			return;
		}

		int id = Integer.parseInt(commandBits[2]);
		Article targetArticle = articleService.getArticleById(id);
		if (targetArticle == null) {
			System.out.printf("* %d번 게시물이 존재하지 않습니다\n", id);
			return;
		}
		targetArticle.increaseHit();
		System.out.printf("번호		: %d\n", targetArticle.id);
		System.out.printf("작성일		: %s\n", targetArticle.regDate);
		System.out.printf("작성자		: %s\n", targetArticle.writerName);
		System.out.printf("제목		: %s\n", targetArticle.title);
		System.out.printf("내용-----------------------------*\n| %s\n| \n| \n", targetArticle.body);
		System.out.printf("조회수		: %d\n", targetArticle.hit);
	}

	private void doDelete() {
		String[] commandBits = command.split(" ");

		if (commandBits.length < 3) {
			System.out.printf("* 게시글 번호를 입력하지 않았습니다.\n");
			return;
		}

		int id = Integer.parseInt(commandBits[2]);
		Article foundArticle = articleService.getArticleById(id);
		if (foundArticle == null) {
			System.out.printf("* %d번 게시물이 존재하지 않습니다\n", id);
			return;
		}
		if (Controller.logonMember.id == 0) { // 관리자로 게시물 삭제 권한 추가
			articleService.remove(foundArticle);
			System.out.printf("* %d번 게시물이 삭제되었습니다.\n", id);
			return;
		}
		if (!articleService.isWriter(foundArticle.id)) {
			System.out.println("* 작성자만 삭제할 수 있습니다.");
			return;
		}
// index가 아니고 article로 삭제 가능함. ↑ 현재 함수 내에서 article로 불러와도 됨.
		articleService.remove(foundArticle);
		System.out.printf("* %d번 게시물이 삭제되었습니다.\n", id);
	}

	private void doModify() {
		String[] commandBits = command.split(" ");

		if (commandBits.length < 3) {
			System.out.printf("* 게시글 번호를 입력하지 않았습니다.\n");
			return;
		}

		int id = Integer.parseInt(commandBits[2]);
		Article foundArticle = articleService.getArticleById(id);
		if (foundArticle == null) {
			System.out.printf("* %d번 게시물이 존재하지 않습니다\n", id);
			return;
		}
		if (Container.articleDao.isWriter(id)) {
			System.out.println("* 작성자만 수정할 수 있습니다.");
			return;
		}
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		foundArticle.title = title;
		foundArticle.body = body;

		System.out.printf("* %d번 게시물이 수정되었습니다.\n", id);
	}

// ============================================================================================================	

	public void makeTestData() {
		System.out.println("테스트를 위한 게시물을 생성합니다.");
		Container.articleDao.add(new Article(articleService.getNewId(), Util.getCurrentDate(), "테스트 제목11", "내용1", 1, "test1", 1));
		Container.articleDao.add(new Article(articleService.getNewId(), Util.getCurrentDate(), "테스트 제목22", "내용2", 2, "test2", 2));
		Container.articleDao.add(new Article(articleService.getNewId(), Util.getCurrentDate(), "테스트 제목32", "내용3", 3, "test3", 3));
	}

}