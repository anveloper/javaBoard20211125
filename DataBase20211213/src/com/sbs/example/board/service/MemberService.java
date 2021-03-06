package com.sbs.example.board.service;

import java.sql.Connection;
import java.util.Map;

import com.sbs.example.board.dao.MemberDao;

public class MemberService {
	MemberDao memberDao;
	
	public MemberService(Connection conn) {
		memberDao = new MemberDao(conn);
		
	}

	public int getMemberCntByLoginId(String loginId) {
		return memberDao.getMemberCntByLoginId(loginId);
	}

	public int getMemberIdByNewId(String loginId, String loginPw, String name) {
		return memberDao.getMemberIdByNewId(loginId, loginPw, name);
	}

	public Map<String, Object> getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

}
