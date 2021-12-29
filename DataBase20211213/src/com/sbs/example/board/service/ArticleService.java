package com.sbs.example.board.service;

import java.sql.Connection;
import java.util.List;

import com.sbs.example.board.dao.ArticleDao;
import com.sbs.example.board.dto.Article;
import com.sbs.example.board.dto.Comment;

public class ArticleService {
	ArticleDao articleDao;

	public ArticleService(Connection conn) {
		this.articleDao = new ArticleDao(conn);
	}

	public int doWrite(String title, String body, int logonMemberId) {
		return articleDao.doWrite(title, body, logonMemberId);
	}

	public int getArticleCntById(int id) {
		return articleDao.getArticleCntById(id);
	}

	public List<Article> getArticles(int page, int itemInAPage) {
		int limitFrom = (page - 1) * itemInAPage;
		int limitTake = itemInAPage;

		return articleDao.getArticles(limitFrom, limitTake);
	}

	public List<Article> getArticlesByKey(String keyword, int page, int itemInAPage) {
		int limitFrom = (page - 1) * itemInAPage;
		int limitTake = itemInAPage;

		return articleDao.getArticles(keyword, limitFrom, limitTake);
	}

	public void doModify(String title, String body, int id) {
		articleDao.doModify(title, body, id);
	}

	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}

	public void doDelete(int id) {
		articleDao.doDelete(id);
	}

	public int getMemberIdById(int id) {
		return articleDao.getMemberIdById(id);
	}

	public void increaseHit(int id) {
		articleDao.increaseHit(id);
	}

	public int getArticlesCnt(String searchKey) {
		return articleDao.getArticlesCnt(searchKey);
	}

	public void insertLike(int id, int likeType, int logonMemberId) {
		articleDao.insertLike(id, likeType, logonMemberId);
	}

	public int likeCheck(int id, int logonMemberId) {
		return articleDao.likeCheck(id, logonMemberId);
	}

	public void updateLike(int id, int likeType, int logonMemberId) {
		articleDao.updateLike(id, likeType, logonMemberId);
	}

	public void cancelLike(int id, int logonMemberId) {
		articleDao.cancelLike(id, logonMemberId);
	}

	public int getLikeVal(int id, int likeType) {
		return articleDao.getLikeVal(id, likeType);
	}

	public int doWriteComment(int id, String title, String body, int logonMemberId) {
		return articleDao.doWriteComment(id, title, body, logonMemberId);
	}

	public int getCommentCnt(int commentId) {
		return articleDao.getCommentCnt(commentId);
	}

	public int getCommentMemberIdById(int commentId) {
		return articleDao.getCommentMemberIdById(commentId);
	}

	public void doModifyComment(int commentId, String title, String body) {
		articleDao.doModifyComment(commentId, title, body);
	}

	public boolean isMatchArticleIdtoCommentId(int id, int commentId) {
		return articleDao.isMatchArticleIdtoCommentId(id, commentId);
	}

	public List<Comment> getComments(int id) {
		return articleDao.getComments(id);
	}

	public void doDeleteComment(int commentId) {
		articleDao.doDeleteComment(commentId);
	}

	public int getCommentsCnt(int id) {
		return articleDao.getCommentsCnt(id);
	}

	public List<Comment> getCommentsByPage(int id, int page, int itemInAPage) {
		int limitFrom = (page - 1) * itemInAPage;
		int limitTake = itemInAPage;

		return articleDao.getCommentsByPage(id, limitFrom, limitTake);
	}

}
