package com.sbs.example.board;

import java.time.LocalDateTime;
import java.util.Map;

public class Member {
	public int id;
	public LocalDateTime regDate;
	public LocalDateTime updateDate;
	public String loginId;
	public String loginPw;
	public String name;
	
	public Member(Map<String, Object> foundMemberMap) {
		this.id = (int) foundMemberMap.get("id");
		this.regDate = (LocalDateTime) foundMemberMap.get("regDate");
		this.updateDate = (LocalDateTime) foundMemberMap.get("updateDate");
		this.loginId = (String) foundMemberMap.get("loginId");
		this.loginPw = (String) foundMemberMap.get("loginPw");
		this.name = (String) foundMemberMap.get("name");
	}
	
}
