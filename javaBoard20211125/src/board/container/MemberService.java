package board.container;

import java.util.List;

import board.dao.MemberDao;
import board.dto.Member;

public class MemberService {
	private MemberDao memberDao;
	
	public MemberService(){
		this.memberDao = Container.memberDao;
	}

	public int getNewId() {
		return memberDao.getNewId();
	}

	public int getMemberSize() {
		return memberDao.getMemberSize();
	}

	public List<Member> getMemberForPrint(String searchKeyword) {
		return memberDao.getMemberForPrint(searchKeyword);
	}

	public boolean isAdmin() {
		return memberDao.isAdmin();
	}

	public Object getMembers() {
		return memberDao.members;
	}

	public boolean isJoinableLoginId(String loginId) {
		return memberDao.isJoinableLoginId(loginId);
	}

	public int isLoginId(String loginId) {
		return memberDao.isLoginId(loginId);
	}

	public Member getMemberByLoginId(int loginIndex) {
		return memberDao.getMemberByLoginId(loginIndex);
	}

	public void add(Member member) {
		memberDao.add(member);
	}

	public void join(Member member) {
		memberDao.add(member);
	}
	
	

}
