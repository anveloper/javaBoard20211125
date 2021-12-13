package com.sbs.example.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
		
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		List<Article> articles = new ArrayList<>();
		
		while(true) {
			System.out.printf("* 명령어 : ");
			String cmd = sc.nextLine();
			cmd = cmd.trim();
			if(cmd.equals("system exit")) {
				System.out.printf("* 시스템을 종료합니다.\n");
				break;
			} else {
				System.out.printf("* 잘못된 명령어입니다.\n");
			}
			
			
		}
		sc.close();
	}
}