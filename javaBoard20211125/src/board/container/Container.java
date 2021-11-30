package board.container;

public class Container {
	public static ArticleDao articleDao;
	public static MemberDao memberDao;
	static {
		articleDao = new ArticleDao();
		memberDao = new MemberDao();
	}
}
