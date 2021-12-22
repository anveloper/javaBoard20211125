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
		} else if (cmd.equals("article list")) {
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
		System.out.printf("* 게시글 작성 \n");
		System.out.printf("* 제목 : ");
		title = sc.nextLine();
		System.out.printf("* 내용 : ");
		body = sc.nextLine();
		
		int id = articleService.doWrite(title, body);
		
		System.out.printf("* %d번 게시글이 추가되었습니다.\n", id);
	}

	private void showList() {
		System.out.printf("* 게시글 목록\n");
		List<Article> articles = articleService.getArticles(); 

		if (articles.size() == 0) {
			System.out.printf("* 표시할 게시글이 없습니다.\n");
			return;
		}

		System.out.printf("번호	| 등록날짜		| 수정날짜		| 제목			| 내용\n");
		System.out.println(
				"================================================================================================");
		for (Article article : articles) {
			System.out.printf("%d	| %s	| %s	| %-14s	| %s	\n", article.id, article.regDate,
					article.updateDate, article.title, article.body);
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
		
		Article article = articleService.getArticleById(id); 
		
		System.out.printf("* 게시글 상세보기\n");
		System.out.printf("* 게시글 번호 : %d\n", article.id);
		System.out.printf("* 등록일자 : %s		갱신일자 : %s\n", article.regDate, article.updateDate);
		System.out.printf("* 제목 : %s\n", article.title);
		System.out.printf("* 내용 =========================================================================\n");
		System.out.printf("| >> %s \n| \n| \n* \n", article.body);
	}

	private void doModify() {
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
		
		articleService.doDelete(id);
		
		System.out.printf("* %d번 게시글이 삭제되었습니다.\n", id);

	}
	
	

}
