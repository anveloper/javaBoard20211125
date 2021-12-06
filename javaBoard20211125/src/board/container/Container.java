package board.container;

import board.Service.ArticleService;
import board.Service.ExportService;
import board.Service.MemberService;
import board.dao.ArticleDao;
import board.dao.MemberDao;

public class Container {
	public static ArticleDao articleDao;
	public static MemberDao memberDao;
	public static ArticleService articleService;
	public static MemberService memberService;
	public static ExportService exportService;
	static {
		articleDao = new ArticleDao();
		memberDao = new MemberDao();
		articleService = new ArticleService();
		memberService = new MemberService();
	}
}
