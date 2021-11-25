package board.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import board.dto.Article;
import board.dto.Member;
import board.util.Util;

public class ArticleController extends Controller {
	private Scanner sc;
	private List<Article> articles;
	private String command;
	private String actionMethodName;
	public Member logonMember;
	int lastId;

	public ArticleController(Scanner sc) {
		this.sc = sc;
		this.lastId = 0;
		articles = new ArrayList<>();

	}

	public void doAction(String command, String actionMethodName) {
		this.command = command;

		switch (actionMethodName) {
		case "list":
			showList();
			break;
		case "detail":
			showDetail();
			break;
		case "write":
			doWrite();
			break;
		case "modify":
			doModify();
			break;
		case "delete":
			doDelete();
			break;
		}
	}

	public void doAction(String command, String actionMethodName, Member logonMember) {
		this.command = command;
		this.logonMember = logonMember;
		this.doAction(command, actionMethodName);
	}

	private void showList() {
		if (articles.size() == 0) {
			System.out.printf("게시물이 없습니다.\n");
			return;
		}
		List<Article> forListArticle = articles;
		String searchKeyword = command.substring("article list".length()).trim();
		if (searchKeyword.length() > 0) {
			System.out.printf("검색어 : %s\n", searchKeyword);
			forListArticle = new ArrayList<>();

			for (Article article : articles) {
				if (article.title.contains(searchKeyword)) {
					forListArticle.add(article);
				}
			}
			if (forListArticle.size() == 0) {
				System.out.printf("검색된 게시글이 없습니다.\n");
				return;
			}
		}
		System.out.printf("=== 게시물 목록 ===\n");
		System.out.println("번호	| 날짜		| 제목					| 작성자	  | 조회수");
		for (int i = forListArticle.size() - 1; i >= 0; i--) {
			Article currentArticle = forListArticle.get(i);
			System.out.printf("%d	| %s	| %s				| %s	  | %d\n", currentArticle.id,
					currentArticle.regDate, currentArticle.title, currentArticle.writer, currentArticle.hit);
		}
	}

	private void doWrite() {
		int id = ++lastId;
		String currentDate = Util.getCurrentDate();
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();
		int writerId = 0;
		String writer = "비회원";
		if (logonMember != null) {
			writerId = logonMember.id;
			writer = logonMember.loginId;
		}
		Article article = new Article(id, currentDate, title, body, writerId, writer);
		articles.add(article);
		System.out.printf("%d번 게시물 등록이 완료되었습니다.\n", id);
	}

	private void showDetail() {
		String[] commandBits = command.split(" ");

		if (commandBits.length < 3) {
			System.out.printf("게시글 번호를 입력하지 않았습니다.\n");
			return;
		}

		int id = Integer.parseInt(commandBits[2]);
		Article targetArticle = getArticleById(id);
		if (targetArticle == null) {
			System.out.printf("%d번 게시물이 존재하지 않습니다\n", id);
			return;
		}
		targetArticle.increaseHit();
		System.out.printf("번호		: %d\n", targetArticle.id);
		System.out.printf("작성일		: %s\n", targetArticle.regDate);
		System.out.printf("제목		: %s\n", targetArticle.title);
		System.out.printf("내용-----------------------------*\n| %s\n| \n| \n", targetArticle.body);
		System.out.printf("작성자		: %s\n", targetArticle.writer);
		System.out.printf("조회수		: %d\n", targetArticle.hit);
	}

	private void doDelete() {
		String[] commandBits = command.split(" ");

		if (commandBits.length < 3) {
			System.out.printf("게시글 번호를 입력하지 않았습니다.\n");
			return;
		}

		int id = Integer.parseInt(commandBits[2]);
		int foundIndex = getArticleIndexById(id);
		if (foundIndex == -1) {
			System.out.printf("%d번 게시물이 존재하지 않습니다\n", id);
			return;
		}

		if (isWriter(foundIndex)) {
			System.out.println("작성자만 삭제할 수 있습니다.");
			return;
		}


		articles.remove(foundIndex);
		System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
	}

	private void doModify() {
		String[] commandBits = command.split(" ");

		if (commandBits.length < 3) {
			System.out.printf("게시글 번호를 입력하지 않았습니다.\n");
			return;
		}

		int id = Integer.parseInt(commandBits[2]);
		Article targetArticle = getArticleById(id);
		if (targetArticle == null) {
			System.out.printf("%d번 게시물이 존재하지 않습니다\n", id);
			return;
		}
		if (isWriter(id)) {
			System.out.println("작성자만 수정할 수 있습니다.");
			return;
		}
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		targetArticle.title = title;
		targetArticle.body = body;

		System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
	}

// ============================================================================================================	

	private boolean isWriter(int index) {
		return logonMember.id != articles.get(index).writerId;
	}

	private int getArticleIndexById(int id) {
		int i = 0;
		for (Article article : articles) {
			if (article.id == id) {
				return i;
			}
			i++;
		}
		return -1;
	}

	private Article getArticleById(int id) {
		int index = getArticleIndexById(id);
		if (index != -1) {
			return articles.get(index);
		}
		return null;
	}

	public void makeTestData() {
		System.out.println("테스트를 위한 게시물을 생성합니다.");
		articles.add(new Article(0, Util.getCurrentDate(), "테스트 제목1", "내용1", 0, "테스트1", 11));
		articles.add(new Article(0, Util.getCurrentDate(), "테스트 제목2", "내용2", 0, "테스트2", 22));
		articles.add(new Article(0, Util.getCurrentDate(), "테스트 제목3", "내용3", 0, "테스트3", 33));
	}

}