package com.sbs.example.board.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Comment {
	private int id;
	private LocalDateTime regDate;
	private LocalDateTime updateDate;
	private int articleId;
	private int memberId;
	private String title;
	private String body;
	private String extra_writer;

	public Comment(Map<String, Object> commmentMap) {
		this.id = (int) commmentMap.get("id");
		this.regDate = (LocalDateTime) commmentMap.get("regDate");
		this.updateDate = (LocalDateTime) commmentMap.get("updateDate");
		this.articleId = (int) commmentMap.get("articleId");
		this.memberId = (int) commmentMap.get("memberId");
		this.title = (String) commmentMap.get("title");
		this.body = (String) commmentMap.get("body");
		this.extra_writer = (String) commmentMap.get("extra_writer");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getRegDate() {
		return regDate;
	}

	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getExtra_writer() {
		return extra_writer;
	}

	public void setExtra_writer(String extra_writer) {
		this.extra_writer = extra_writer;
	}

}
