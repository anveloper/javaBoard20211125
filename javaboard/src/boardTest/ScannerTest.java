package boardTest;

/*
import java.util.Scanner;

public class boardMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		String user_name = sc.nextLine();
		String user_password = sc.nextLine();
		String user_email = sc.nextLine();
				
		System.out.println("입력값 : " + user_name);
		System.out.println("입력값 : " + user_password);
		System.out.println("입력값 : " + user_email);
		
//		int num1 = sc.nextInt();  
//		짧아 보이지만, 입력 방식이 다르면 오류가 난다. 한가지로 맞춰야 한다.
		int num1 = Integer.parseInt(sc.nextLine());
		int num2 = Integer.parseInt(sc.nextLine());
		System.out.println(num1 + num2);
		
	}

}

//Scanner를 이용해서 아래와 같이 입출력 하여 사칙연산을 수행해주세요.
//입출력 예시
//첫번째 숫자를 입력해주세요: 12
//두번째 숫자를 입력해주세요: 33
//12 + 33 = 45

import java.util.Scanner;
//풀이
class boardMain {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.printf("첫번째 숫자를 입력해주세요 : ");
		int num1 = Integer.parseInt(sc.nextLine());
		System.out.printf("두번째 숫자를 입력해주세요 : ");
		int num2 = Integer.parseInt(sc.nextLine());
		System.out.printf("두 숫자의 합은 : %d + %d = %d",num1,num2,num1+num2);
	}
}

// 구구단을 원하는 단이 나오게 해주세요.
// 예시) 원하는 단을 입력해주세요 : 4 (입력후 엔터)
// 4단 출력.

import java.util.Scanner;
// 풀이
class boardMain {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.printf("원하는 구구단은 ? : ");
		int dan = Integer.parseInt(sc.nextLine());
		System.out.printf("===== %d단 =====\n", dan);
		for (int i = 1; i < 10; i++) {
			System.out.printf("%d X %d = %d\n", dan, i, dan * i);
		}

	}
}
 */

//이름과 나이를 입력값으로 받아 자기소개를 해주세요.
import java.util.Scanner;

class boardMain{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.printf("이름을 입력해 주세요 : ");
		String user_name = sc.nextLine();
		System.out.printf("나이를 입력해 주세요 : ");
		int user_age = Integer.parseInt(sc.nextLine());
		
		System.out.printf("안녕하세요. 저는 %d세 %s입니다.",user_age,user_name);
	}
}
