package Solution;

import java.util.Scanner;

class Solution {
	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);

		int T = sc.nextInt();

		for (int tc = 1; tc <= T; tc++) {
			String n = sc.nextLine();
			String[] n_s = n.split(" ");
			String[] a = n_s[0].split("");
			int[] arr = new int[n_s[0].length()];
			for (int i = 0; i < n_s[0].length(); i++) {
				arr[i] = Integer.parseInt(a[i]);
			}
			int b = Integer.parseInt(n_s[1]);
			for (int i = 0; i < b; i++) {
				
			}
			System.out.printf("#%d %d\n", tc, b);
		}
		sc.close();
	}
}