package com.sbs.example.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		List<Article> articles = new ArrayList<>();
		int lastArticleId = 0;

		while (true) {
			System.out.printf("* 명령어 : ");
			String cmd = sc.nextLine();
			cmd = cmd.trim();

			if (cmd.equals("system exit")) {
				System.out.printf("* 시스템을 종료합니다.\n");
				break;
			}

			if (cmd.equals("article write")) {
				int id = ++lastArticleId;
				String title;
				String body;
				System.out.printf("* 게시글 작성 \n");
				System.out.printf("* 제목 : ");
				title = sc.nextLine();
				System.out.printf("* 내용 : ");
				body = sc.nextLine();
				Article article = new Article(id, title, body);

				articles.add(article);
				System.out.printf("* %d번 게시글이 추가되었습니다.\n", id);
			} else if (cmd.equals("article list")) {
				System.out.printf("* 게시글 목록\n");
				if (articles.size() == 0) {
					System.out.printf("* 게시글이 존재하지 않습니다.\n");
					continue;
				}
				System.out.printf("번호	| 제목		|\n");
				for (Article article : articles) {
					System.out.printf("%d	| %-20s		|\n", article.id, article.title);
				}
			} else {
				System.out.printf("* 잘못된 명령어입니다.\n");
			}

		}
		sc.close();
	}
}