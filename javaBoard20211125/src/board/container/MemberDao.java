package board.container;

import java.util.ArrayList;
import java.util.List;

import board.dto.Member;

public class MemberDao {
	public List<Member> members;
	public Member logonMember;
	public MemberDao() {
		members = new ArrayList<>();
		logonMember = null;
	}
}
