package Solution;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Solution2 { // 가장 빠른 풀이

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine().replaceAll(" ", ""));
		for (int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());

			st = new StringTokenizer(br.readLine());
			long[] prices = new long[N];
			for (int i = 0; i < N; i++) {
				prices[i] = Integer.parseInt(st.nextToken());
			}

			Stack<Integer> stack = new Stack<Integer>();
			long highPrice = -1;
			for (int i = prices.length - 1; i >= 0; i--) {
				if (prices[i] > highPrice) {
					highPrice = prices[i];
					stack.push(i);
				}
			}

			int purchaseDate = 0;
			int saleDate = 0;
			long profit = 0;
			long result = 0;
			while (!stack.empty()) {
				saleDate = stack.pop();

				for (int i = purchaseDate; i < saleDate; i++) {
					profit += prices[i];
				}
				profit = (saleDate - purchaseDate) * prices[saleDate] - profit;
				purchaseDate = saleDate + 1;
				result += profit;
				profit = 0;
			}

			System.out.println("#" + test_case + " " + result);
		}
	}

}