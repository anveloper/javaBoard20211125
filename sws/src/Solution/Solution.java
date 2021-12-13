package Solution;

import java.util.Scanner;

class Solution {
	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);

		int T = sc.nextInt();

		for (int tc = 1; tc <= T; tc++) {
			int n = sc.nextInt();
			int[][] arr = new int[n][n];

			int num = 0;
			int x = 0, y = 0;

			for (int i = x; i < n; i++) {
				arr[0][i] = ++num;
			}
			for (int i = x + 1; i < n; i++) {
				arr[i][n - 1] = ++num;
			}
			for (int i = n - 2; i >= 0; i--) {
				arr[n - 1][i] = ++num;
			}
			for (int i = n - 2; i >= 1; i--) {
				arr[i][0] = ++num;
			}

			x = 1;
			y = 1;
			while (num != n * n) {
				while (arr[x][y + 1] == 0) {
					arr[x][y] = ++num;
					y++;
				}
				while (arr[x+1][y] == 0) {
					arr[x][y] = ++num;
					x++;
				}
				while (arr[x][y - 1] == 0) {
					arr[x][y] = ++num;
					y--;
				}
				while (arr[x-1][y] == 0) {
					arr[x][y] = ++num;
					x--;
				}
				System.out.printf("%d %d\n", x, y);
				arr[x][y] = ++num;
			}
			System.out.printf("#%d\n", tc);
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					System.out.printf("%-3d ", arr[i][j]);
				}
				System.out.printf("\n");
			}

		}
		sc.close();
	}
}