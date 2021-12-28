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
		} else if (cmd.startsWith("article like ")) {
			doLike();
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

		System.out.printf("* 게시글 목록\n");

		int page = 1;
		int itemInAPage = 5;

		while (true) {

			if (page == 0)
				break;

			if (cmdBits.length < 3) {
				if (cmd.length() != 12) { // 띄어 쓰기를 안하는 경우
					System.out.printf("* 명령어를 잘못 입력하였습니다.\n");
					return;
				}
				articles = articleService.getArticles(page, itemInAPage);
			} else {
				searchKey = cmd.substring("article list ".length());
				articles = articleService.getArticlesByKey(searchKey, page, itemInAPage);
			}

			if (articles.size() == 0) {
				System.out.printf("* 표시할 게시글이 없습니다.\n");
				return;
			}

			System.out.printf("번호 | 등록날짜            | 수정날짜            | 제목			| 작성자	| 조회수 | 추/비\n");
			System.out.println(
					"=========================================================================================================");
			for (Article article : articles) {

				int likeVal = articleService.getLikeVal(article.getId(), 1);
				int dislikeVal = articleService.getLikeVal(article.getId(), 2);

				System.out.printf("%-4d | %-19s | %-19s | %-14s	| %s		| %-6d | %d / %d \n", article.getId(),
						article.getRegDate(), article.getUpdateDate(), article.getTitle(), article.getExtra_writer(),
						article.getHit(), likeVal, dislikeVal);
			}
			System.out.println(
					"=========================================================================================================");

			// articles.size()도 가능하지만 sql 교육을 위해
			int articlesCnt = articleService.getArticlesCnt(searchKey);
			int lastPage = (int) Math.ceil(articlesCnt / (double) itemInAPage);

			System.out.printf("					          [현재 페이지 : %-2d, 마지막 페이지 : %-2d, 전체글 수 : %-3d]\n", page,
					lastPage, articlesCnt);
			System.out.printf("* 이동하려는 page 입력, 종료 시 0 이하 입력\n");
			while (true) {
				System.out.printf("[article list] > 명령어 : ");
				page = sc.nextInt();
				sc.nextLine(); // 버퍼 회수
				if (page > lastPage) {
					System.out.printf("* 없는 페이지 입니다.\n");
					continue;
				}
				break;
			}
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

		int likeVal = articleService.getLikeVal(id, 1);
		int dislikeVal = articleService.getLikeVal(id, 2);

		System.out.printf("* 게시글 상세보기===============================================================\n");
		System.out.printf("* 게시글 번호 : %d				작성자 : %s\n", article.getId(), article.getExtra_writer());
		System.out.printf("* 등록일자 : %s		갱신일자 : %s\n", article.getRegDate(), article.getUpdateDate());
		System.out.printf("* 제목 : %s\n", article.getTitle());
		System.out.printf("* 내용 =========================================================================\n");
		System.out.printf(
				"| >> %s \n| \n| \n* ======================================================== 추천 : %-3d 비추천 : %-3d\n",
				article.getBody(), likeVal, dislikeVal);
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

	private void doLike() {
		if (ss.isLogon() == false) { // 나중에 함수화
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

		// 추천/비추천 기능 구문
		System.out.printf("* 게시글 [추천] 1, [비추천] 2, [선택 해제] 3, [나기기] 0\n");

		while (true) {
			System.out.printf("[article like] > 명령어 : ");
			int likeType = sc.nextInt();
			sc.nextLine();

			if (likeType == 0) {
				System.out.printf("* [article like] 종료\n");
				break;
			}

			int likeCheck = articleService.likeCheck(id, ss.getLogonMemberId());

			String msg = (likeType == 1 ? "추천" : "비추천");

			if (likeType == 1 || likeType == 2) {

				if (likeCheck > 0) {
					if (likeCheck == likeType) {
						System.out.printf("* 이미 %s한 게시글입니다.\n", msg);
						continue;
					} else {
						articleService.updateLike(id, likeType, ss.getLogonMemberId());
					}
				} else {
					articleService.insertLike(id, likeType, ss.getLogonMemberId());
				}
				System.out.printf("* %s 완료\n");

			} else if (likeType == 3) {
				if (likeCheck < 1) {
					System.out.printf("* 아직 %s하지 않은 글입니다.\n", msg);
					continue;
				}
				articleService.cancelLike(id, ss.getLogonMemberId());
				System.out.printf("* %d번 게시글 %s을 해제하였습니다.\n", id, msg);
			} else {
				System.out.printf("* 잘못 입력하였습니다.(0 ~ 3 입력가능)\n");
				continue;
			}
		}
	}
}
