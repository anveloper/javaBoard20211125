package Solution;

import java.util.Scanner;

public class Solution { // 내 풀이

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T;
		T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			int tcn = sc.nextInt();
			int[] arr = new int[tcn];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = sc.nextInt();
			} // 케이스 입력
			int benefit = 0;
			int arr_sum = 0;
			for (int i = 0; i < arr.length; i++) {
				arr_sum += arr[i];
			} // 초기 배열의 합 확인(조건문)

			int max_num = arr[0];
			int max_index = 0;
			int temp_sum = 0;
			int cnt = 0;

			while (arr_sum > 0) {
				max_num = 0;
				max_index = 0;
				temp_sum = 0;

				for (int i = 0; i < arr.length; i++) {
					if (arr[i] > max_num) {
						max_num = arr[i];
						max_index = i;
					}
				} // 최댓값 찾기

				arr[max_index] = 0; // 최댓값 0으로 변경
				cnt = 0;
				for (int i = 0; i < max_index; i++) {
					temp_sum += arr[i];
					if (arr[i] != 0) {
						cnt++; // 구매 횟수 확인
					}
					arr[i] = 0;
				} // 최댓값 이전값까지 임시 합에 빼고, 그 값을 제거
				temp_sum = (max_num * cnt) - temp_sum; // 소비한 금액에 구매 갯수 곱하기 최대값을 함
				benefit += temp_sum; // 현재까지 이익을 루프 밖에 저장
				arr_sum = 0;
				for (int i = 0; i < arr.length; i++) {
					arr_sum += arr[i];
				} // while 조건인 arr_sum을 확인(사용된 arr은 계속 0으로 변경되어 최종적으로 탈출함수)
			}
			System.out.printf("#%d %d\n", tc, benefit);
		}
		sc.close();
	}

}
