package board.controller;

import board.dto.Member;

public abstract class Controller {
	public abstract void doAction(String command, String actionMethodName);

	public abstract void doAction(String command, String actionMethodName, Member logonMember);

	public abstract void makeTestData();
	// 추상함수로서 controller의 부모클래스를 만들고, article과 member로 파생시킨다.
	// 각자의 자식 클래스에 있는 기능에 대한 언급을 해줘야 한다.

	public static Member logonMember = null;

	public static boolean isLogon() {
		return logonMember != null;
	}
}