package board.controller;

import java.util.Scanner;

import board.Service.ExportService;
import board.container.Container;
import board.dto.Member;

public class ExportController extends Controller {
	private Scanner sc;
	private String command;
	private String actionMethodName;
	private ExportService exportService;
	
	public ExportController(Scanner sc) {
		this.sc = sc;
		this.exportService = Container.exportService;
	}

	public void doAction(String command, String actionMethodName) {
		this.command = command;
		this.actionMethodName = actionMethodName;

		switch (actionMethodName) {
		case "html":
			doHtml();
			break;

		default:
			System.out.printf("존재하지 않는 명령어 입니다.\n");
			break;
		}
	}
	public void doAction(String command, String actionMethodName, Member logonMember) {
		this.doAction(command, actionMethodName);
	}

	private void doHtml() {
		System.out.printf("** html 생성을 시작합니다. **\n");
		exportService.makeHtml();
	}


	public void makeTestData() {
	}
}
