package com.sbs.example.board.session;

import com.sbs.example.board.Member;

public class Session {
	public int logonMemberId;
	public Member logonMember;
	
	public Session() {
		this.logonMemberId = -1;
	}
	public boolean isLogon() {
		return logonMember != null;
	}
}
