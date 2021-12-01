package board.dao;

import java.util.ArrayList;
import java.util.List;

import board.dto.Member;

public class MemberDao extends Dao {
	public List<Member> members;
	public Member logonMember;
	
	public MemberDao() {
		members = new ArrayList<>();
		logonMember = null;
	}

	public void add(Member member) {
		members.add(member);
		lastId = member.id;
	}
}
