package Solution;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		long num;
		float n;
		int m;
		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			num = sc.nextLong();
			int cnt = 0;
			while(num>10) {
				num /= 10;
				cnt++;
			}
			
			

			System.out.printf("#%d %.1f*10^%d \n", tc, cnt, num);
//			System.out.printf("#%d %.1f*10^%d \n", tc, n, m);
		}
		sc.close();
	}

}
