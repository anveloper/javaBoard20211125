package com.sbs.example.board.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.sbs.example.board.dto.Article;
import com.sbs.example.board.service.ArticleService;
import com.sbs.example.board.session.Session;

public class ArticleController extends Controller {

	ArticleService articleService;

	public ArticleController(Connection conn, Scanner sc, String cmd, Session ss) {
		this.sc = sc;
		this.cmd = cmd;
		this.ss = ss;

		articleService = new ArticleService(conn);
	}

	public void doAction() {
		if (cmd.equals("article write")) {
			doWrite();
		} else if (cmd.startsWith("article list")) {
			showList();
		} else if (cmd.startsWith("article detail ")) {
			showDetail();
		} else if (cmd.startsWith("article modify ")) {
			doModify();
		} else if (cmd.startsWith("article delete ")) {
			doDelete();
		} else {
			System.out.printf("* 존재하지 않는 명령어입니다.\n");
		}
	}

	private void doWrite() {
		String title;
		String body;

		if (ss.isLogon() == false) {
			System.out.printf("* 로그인 상태가 아닙니다. 로그인 후 이용해 주세요.\n");
			return;
		}

		System.out.printf("* 게시글 작성 \n");
		System.out.printf("* 제목 : ");
		title = sc.nextLine();
		System.out.printf("* 내용 : ");
		body = sc.nextLine();

		int id = articleService.doWrite(title, body, ss.getLogonMemberId());

		System.out.printf("* %d번 게시글이 추가되었습니다.\n", id);
	}

	private void showList() {
		String[] cmdBits = cmd.split(" ");
		String searchKey = "";
		List<Article> articles = null;

		if (cmdBits.length < 3) {
			if (cmd.length() != 12) { //띄어 쓰기를 안하는 경우
				System.out.printf("* 명령어를 잘못 입력하였습니다.\n");
				return;
			}
			articles = articleService.getArticles();
		} else {
			searchKey = cmd.substring("article list ".length());
			articles = articleService.getArticlesByKey(searchKey);
		}

		System.out.printf("* 게시글 목록\n");

		if (articles.size() == 0) {
			System.out.printf("* 표시할 게시글이 없습니다.\n");
			return;
		}

		System.out.printf("번호	| 등록날짜		| 수정날짜		| 제목			| 작성자	| 조회수\n");
		System.out.println(
				"=========================================================================================================");
		for (Article article : articles) {
			System.out.printf("%d	| %s	| %s	| %-14s	| %s		| %d \n", article.getId(), article.getRegDate(),
					article.getUpdateDate(), article.getTitle(), article.getExtra_writer(), article.getHit());
		}
	}

	private void showDetail() {

		boolean isInt = cmd.split(" ")[2].matches("-?\\d+");
		if (!isInt) {
			System.out.println("* 게시글의 ID를 숫자로 입력해주세요.\n");
			return;
		}

		int id = Integer.parseInt(cmd.split(" ")[2].trim());

		int articleCount = articleService.getArticleCntById(id);

		if (articleCount == 0) {
			System.out.printf("* %d번 게시글이 존재하지 않습니다.\n", id);
			return;
		}

		articleService.increaseHit(id);

		Article article = articleService.getArticleById(id);

		System.out.printf("* 게시글 상세보기===============================================================\n");
		System.out.printf("* 게시글 번호 : %d				작성자 : %s\n", article.getId(), article.getExtra_writer());
		System.out.printf("* 등록일자 : %s		갱신일자 : %s\n", article.getRegDate(), article.getUpdateDate());
		System.out.printf("* 제목 : %s\n", article.getTitle());
		System.out.printf("* 내용 =========================================================================\n");
		System.out.printf("| >> %s \n| \n| \n* \n", article.getBody());
	}

	private void doModify() {
		if (ss.isLogon() == false) {
			System.out.printf("* 로그인 상태가 아닙니다. 로그인 후 이용해 주세요.\n");
			return;
		}

		boolean isInt = cmd.split(" ")[2].matches("-?\\d+");
		if (!isInt) {
			System.out.println("* 게시글의 ID를 숫자로 입력해주세요.\n");
			return;
		}

		int id = Integer.parseInt(cmd.split(" ")[2].trim());

		int articleCount = articleService.getArticleCntById(id);

		if (articleCount == 0) {
			System.out.printf("* %d번 게시글이 존재하지 않습니다.\n", id);
			return;
		}

		if (ss.getLogonMemberId() != articleService.getMemberIdById(id)) {
			System.out.printf("* 작성자만 수정할 수 있습니다.\n");
			return;
		}

		String title;
		String body;

		System.out.printf("* 게시글 수정\n");
		System.out.printf("* 새 제목 : ");
		title = sc.nextLine();
		System.out.printf("* 새 내용 : ");
		body = sc.nextLine();

		articleService.doModify(title, body, id);

		System.out.printf("* %d번 게시글이 수정되었습니다.\n", id);
	}

	private void doDelete() {
		if (ss.isLogon() == false) {
			System.out.printf("* 로그인 상태가 아닙니다. 로그인 후 이용해 주세요.\n");
			return;
		}

		int id = Integer.parseInt(cmd.split(" ")[2].trim());

		boolean isInt = cmd.split(" ")[2].matches("-?\\d+");
		if (!isInt) {
			System.out.println("* 게시글의 ID를 숫자로 입력해주세요.\n");
			return;
		}

		int articleCount = articleService.getArticleCntById(id);

		if (articleCount == 0) {
			System.out.printf("* %d번 게시글이 존재하지 않습니다.\n", id);
			return;
		}

		if (ss.getLogonMemberId() != articleService.getMemberIdById(id)) {
			System.out.printf("* 작성자만 삭제할 수 있습니다.\n");
			return;
		}

		articleService.doDelete(id);

		System.out.printf("* %d번 게시글이 삭제되었습니다.\n", id);
	}
}
