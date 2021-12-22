package com.sbs.example.board.dao;

import java.sql.Connection;
import java.util.Map;

import com.sbs.example.board.util.DBUtil;
import com.sbs.example.board.util.SecSql;

public class MemberDao {
	Connection conn;

	public MemberDao(Connection conn) {
		this.conn = conn;
	}

	public int getMemberCntByLoginId(String loginId) {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*) FROM `member`");
		sql.append("WHERE loginId = ?", loginId);

		return DBUtil.selectRowIntValue(conn, sql);
	}

	public int getMemberIdByNewId(String loginId, String loginPw, String name) {

		SecSql sql = new SecSql();

		sql.append("INSERT INTO member");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", loginId = ?", loginId);
		sql.append(", loginPw = ?", loginPw);
		sql.append(", name = ?", name);

		return DBUtil.insert(conn, sql);
	}

	public Map<String, Object> getMemberByLoginId(String loginId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM member");
		sql.append("WHERE loginId = ?", loginId);
		
		return DBUtil.selectRow(conn, sql);
	}

}
