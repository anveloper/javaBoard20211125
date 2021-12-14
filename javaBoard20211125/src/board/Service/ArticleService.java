package board.Service;

import java.util.List;

import board.container.Container;
import board.dao.ArticleDao;
import board.dto.Article;

public class ArticleService {
	private ArticleDao articleDao;

	public ArticleService() {
		this.articleDao = Container.articleDao;
	}

	public List<Article> getForPrintArticles(String searchKeyword) {
		return articleDao.getArticles(searchKeyword);
	}

	public int getArticleIndexById(int id) {
		return articleDao.getArticleIndexById(id);
	}

	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}

	public void remove(Article foundArticle) {
		articleDao.remove(foundArticle);
	}

	public boolean isWriter(int id) {
		return articleDao.isWriter(id);
	}

	public int getArticlesSize() {
		return articleDao.getArticlesSize();
	}

	public int getNewId() {
		return articleDao.getNewId();
	}

	public void write(Article article) {
		articleDao.add(article);
	}

	public List<Article> getArticlesForExport() {
		return articleDao.getArticles(null);
	}

}