package board.dao;

import java.util.ArrayList;
import java.util.List;

import board.controller.Controller;
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
	public List<Member> getMemberForPrint(String searchKeyword) {
		List<Member>forListMember = new ArrayList<>();
		if (searchKeyword.length() > 0) {
			System.out.printf("검색어 : %s\n", searchKeyword);
			
			for (Member member : members) {
				if (member.loginId.contains(searchKeyword)) {
					forListMember.add(member);
				}
			}
			if (forListMember.size() == 0) {
				System.out.printf("검색된 회원이 없습니다.\n");
				return null;
			}
			return forListMember;
		}
		else return members;
	}
	
	
	public boolean isAdmin() {
		if (Controller.logonMember == null) return false;
		return Controller.logonMember.id == 0;
	}

	public int isLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);
		if (index == -1) {
			return -1;
		}
		return index;
	}

	private int getMemberIndexByLoginId(String loginId) {
		int i = 0;

		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public boolean isJoinableLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);
		if (index == -1) {
			return true;
		}
		return false;
	}

	public int getMemberSize() {
		return members.size();
	}

	public Member getMemberByLoginId(int loginIndex) {
		return members.get(loginIndex);
	}
}
