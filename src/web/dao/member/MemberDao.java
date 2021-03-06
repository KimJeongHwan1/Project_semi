package web.dao.member;

import java.util.List;

import web.dto.MemberDetail;
import web.util.Paging;

public interface MemberDao {

	public int selectCntMemberByMemberidAndMemberpw(MemberDetail member);

	public MemberDetail selectMemberByMemberid(MemberDetail member);

	/**
	 * �쉶�썝媛��엯
	 * 
	 * @param member - �쉶�썝媛��엯 �젙蹂�
	 */
	public void insert(MemberDetail member);


	//관리자인지 일반사용자인지 확인하는 메소드
	//리턴값이 1이면 관리자 / 0이면 일반사용자!!!
	public int checkMemberCode(int member_code);
	
	public boolean idCheck(MemberDetail member);

	
	public MemberDetail selectMemberByMemberpw(MemberDetail member);

	public void deleteMember(MemberDetail member);


	public void updateMember(MemberDetail member);

	
	public List selectAll(Paging paging);

	public int selectCntAll(String search);
	
	public void memberforceout(MemberDetail member);
}
