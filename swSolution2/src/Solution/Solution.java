package Solution;

import java.util.Scanner;

class Solution {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		int T;
		T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			int need_day = sc.nextInt();
			int[] arr = new int[7];
			int day = 0;
			for (int i = 0; i < arr.length; i++) {
				arr[i] = sc.nextInt();
			}
			while (need_day > 0) {
				for (int a : arr) {
					if (a == 1) {
						need_day--;
						if(need_day == 0) break;
					} 
					if (need_day > 0) {
						day++;
						System.out.printf("%d %d ",need_day, day);
					}
				}
				System.out.println("");
			}
			day++;// 종강일 기준은 맞췄으나, 과목의 시작일이 월요일이 아니면 최솟값이 아님.
			for (int i : arr) {
				if (i == 1) break;
				day--;
			}
			System.out.printf("#%d %d\n", tc, day);
		}
		sc.close();
	}
}