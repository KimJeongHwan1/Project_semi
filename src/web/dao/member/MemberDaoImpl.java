package web.dao.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import web.dbutil.DBConn;
import web.dto.MemberDetail;

public class MemberDaoImpl implements MemberDao{

	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	private Connection conn = DBConn.getConnection();
	
	@Override
	public int selectCntMemberByMemberidAndMemberpw(MemberDetail member) {

		if( member.getMember_id() == null || member.getMember_pw() == null ) {
			
			return -1;
		} 

		//쿼리 작성
		String sql = "";
		sql += "SELECT COUNT(*) FROM Member_Detail";
		sql += " WHERE 1=1";
		sql += " AND member_id = ?";
		sql += " AND member_pw = ?";
		
		int cnt = -1;
		try {
			//DB작업
			ps = conn.prepareStatement(sql);
			ps.setString(1, member.getMember_id());
			ps.setString(2, member.getMember_pw());
			
			rs = ps.executeQuery();
			
			while( rs.next() ) {
				cnt = rs.getInt(1);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				//DB객체 닫기
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return cnt;
		
	}
	
	@Override
	public MemberDetail selectMemberByMemberid(MemberDetail member) {
		
		if( member.getMember_id() == null ) {
			return null;
		}

		//쿼리 작성
		String sql = "";
		sql += "SELECT * FROM Member_Detail";
		sql += " WHERE 1=1";
		sql += " AND member_id = ?";
		
		try {
			//DB작업
			ps = conn.prepareStatement(sql);
			ps.setString(1, member.getMember_id());
		
			rs = ps.executeQuery();
			
			while( rs.next() ) {
				member.setMember_code(rs.getInt("member_code"));
				member.setMember_id( rs.getString("member_id") );
				member.setMember_pw( rs.getString("member_pw") );
				member.setMember_nick( rs.getString("member_nick") );
				member.setMember_group(rs.getInt("member_group"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				//DB객체 닫기
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return member;
		
	}
	
	@Override
	public void insert(MemberDetail member) {
		
		//쿼리작성
		String sql = "";
		sql += "INSERT INTO Member_Detail ( member_id, member_pw, member_nick, member_code, member_name,";
		sql += " member_gender, member_birthday, member_email, member_phone )";
		sql += " VALUES( ?, ?, ?, Member_Detail_seq.nextval, ?, ?, ?, ?, ? )";
		
		try {
			//DB작업
			ps = conn.prepareStatement(sql);
			ps.setString(1, member.getMember_id());
			ps.setString(2, member.getMember_pw());
			ps.setString(3, member.getMember_nick());
			ps.setString(4, member.getMember_name());
			ps.setString(5, member.getMember_gender());
			ps.setString(6,member.getMember_birthday());
			ps.setString(7, member.getMember_email());
			ps.setString(8, member.getMember_phone());

			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				//DB객체 닫기
				if(ps!=null)	ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	
}
