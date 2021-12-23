package com.sbs.example.board.session;

import com.sbs.example.board.dto.Member;

public class Session { // getter setter로 만들어야 안전하다.
	private int logonMemberId;
	private Member logonMember;
	
	public Session() {
		this.logonMemberId = -1;
		this.logonMember = null;
	}
	public boolean isLogon() {
		return logonMember != null;
	}
	
	public int getLogonMemberId() {
		return logonMemberId;
	}
	
	public void setLogonMemberId(int logonMemberId) {
		this.logonMemberId = logonMemberId;
	}
	
	public Member getLogonMember() {
		return logonMember;
	}
	
	public void setLogonMember(Member logonMember) {
		this.logonMember = logonMember;
	}
}
