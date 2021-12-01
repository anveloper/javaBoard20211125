package Board.controller;

public class articleController extends Controller {
	private String actionMethod;
	
	public void doAction(String actionMethod) {
		this.actionMethod = actionMethod;
		switch(actionMethod) {
		case "write":
			doWrite();
			break;
		default:
			System.out.printf("잘못된 명령입니다.\n");
			break;
		}
		
	}

	private void doWrite() {
		System.out.printf("** 게시글 작성 **\n");
		while(true) {
			System.out.printf("* 제목 : ");
			String title = sc.nextLine();
			if (title.length() < 20) {
				break;
			}
			System.out.printf("제목의 크기는 [20]을 넘을 수 없습니다.\n");
		}
		System.out.printf("* 내용 : ");
		String body = sc.nextLine();
		
		String regDate = Util.getCurrentDate();
		
	}

}
