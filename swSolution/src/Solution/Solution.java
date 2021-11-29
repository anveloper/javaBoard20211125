package Solution;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T;
		T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			int tcn = sc.nextInt();
			int[] arr = new int[tcn];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = sc.nextInt();
			}
			int max_num = arr[0];
			int max_index = 0;
			int temp_sum = 0;
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] > max_num) {
					max_num = arr[i];
					max_index = i;
				}
			}
			
			System.out.printf("#%d %d\n", tc, 0);
		}
		sc.close();
	}

}
