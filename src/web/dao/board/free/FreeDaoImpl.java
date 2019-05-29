package web.dao.board.free;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import web.dbutil.DBConn;
import web.dto.FreeBoard;
import web.dto.FreeFile;
import web.util.Paging;

public class FreeDaoImpl implements FreeDao {
	
	//DB관련 객체
	private Connection conn = DBConn.getConnection(); 

	private PreparedStatement ps = null;
	private ResultSet rs = null;

	@Override
	public List selectAll(Paging paging) {
		
		//게시글 목록 조회쿼리
		String sql = "";
		sql += "SELECT * FROM (";
		sql += " 	SELECT rownum rnum, B.* FROM (";
		sql += " 	 SELECT free_board_no, free_board_title, free_board_content, free_board_writer, ";
		sql += "    free_board_hit, free_board_written_date, free_board_comment_no, free_board_file_idx, member_id ";
		sql += " 	FROM Free_Board	ORDER BY free_board_no DESC";
		sql += " 	) B";
		sql += " 	ORDER BY rnum";
		sql += " ) Free_Board";
		sql += " WHERE rnum BETWEEN ? AND ?";

	
		List list = new ArrayList();
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, paging.getStartNo());
			ps.setInt(2, paging.getEndNo());
			
			rs = ps.executeQuery();
			
			while( rs.next() ) {
				FreeBoard board = new FreeBoard();
				
				board.setFree_board_no( rs.getInt("free_board_no") );
				board.setFree_board_title( rs.getString("free_board_title") );
				board.setFree_board_content( rs.getString("free_board_content") );
				board.setFree_board_writer( rs.getString("free_board_writer") );
				board.setFree_board_hit( rs.getInt("free_board_hit") );
				board.setFree_board_written_date( rs.getDate("free_board_written_date") );
				board.setFree_board_comment_no( rs.getInt("free_board_comment_no") );
				board.setFree_board_file_idx( rs.getInt("free_board_file_idx") );
				board.setMember_id( rs.getString("member_id") );
				
				list.add(board);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 자원 해제
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
		return list;
		
	}

	@Override
	public int selectCntAll() {
		//전체 게시글 수 조회 쿼리
		String sql = "";
		sql+="SELECT count(*)";
		sql+=" FROM board";
	
		int totalCount = 0;
		try {
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			while( rs.next() ) {
				totalCount = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 자원 해제
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
		return totalCount;
	}

	@Override
	public void updateHit(FreeBoard viewBoard) {
		//파일업로드 기록 조회쿼리
		String sql = "";
		sql+="UPDATE Free_Board";
		sql+=" SET free_board_hit = free_board_hit + 1";
		sql+=" WHERE free_board_no = ?";
	
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, viewBoard.getFree_board_no());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 자원 해제
				if(ps!=null)	ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
		
	}

	@Override
	public FreeBoard selectBoardByBoardno(FreeBoard viewBoard) {
		//게시글 조회쿼리
		String sql = "";
		sql += "SELECT free_board_no, free_board_title, free_board_content, free_board_writer, free_board_hit, free_board_written_date, free_board_comment_no, free_board_file_idx, member_id FROM Free_Board";
		sql += " WHERE free_board_no = ?";
	
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, viewBoard.getFree_board_no());
			
			rs = ps.executeQuery();
			
			while( rs.next() ) {
				
				viewBoard.setFree_board_no( rs.getInt("free_board_no") );
				viewBoard.setFree_board_title( rs.getString("free_board_title") );
				viewBoard.setFree_board_content( rs.getString("free_board_content") );
				viewBoard.setFree_board_writer( rs.getString("free_board_writer") );
				viewBoard.setFree_board_hit( rs.getInt("free_board_hit") );
				viewBoard.setFree_board_written_date( rs.getDate("free_board_written_date") );
				viewBoard.setFree_board_comment_no( rs.getInt("free_board_comment_no") );
				viewBoard.setFree_board_file_idx( rs.getInt("free_board_comment_no") );
				viewBoard.setMember_id( rs.getString("member_id") );
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 자원 해제
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
		return viewBoard;
	}

	@Override
	public int selectBoardno() {
		//다음 게시글 번호 조회 쿼리
		String sql = "";
		sql += "SELECT Free_Board_seq.nextval";
		sql += " FROM dual";
		
		//게시글번호
		int boardno = 0;
		
		try {
			//DB작업
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			//결과 담기
			while(rs.next()) {
				boardno = rs.getInt(1);
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
		
		//게시글 번호 반환
		return boardno;
	}

	@Override
	public void insert(FreeBoard board) {
		//다음 게시글 번호 조회 쿼리
		String sql = "";
		sql += "INSERT INTO Free_board ( free_board_no, free_board_title, free_board_content, free_board_writer, ";
		sql	+= " free_board_hit, free_board_written_date, free_board_comment_no, free_board_file_idx, member_id) ";
		sql += " VALUES (?, ?, ?, ?, 0, ?, ?, ?, ?)";
		
		try {
			//DB작업
			ps = conn.prepareStatement(sql);
			ps.setInt(1, board.getFree_board_no());
			ps.setString(2, board.getFree_board_title());
			ps.setString(3, board.getFree_board_content());
			ps.setString(4, board.getFree_board_writer());
			ps.setDate(5,(java.sql.Date) board.getFree_board_written_date());
			ps.setInt(6, board.getFree_board_comment_no());
			ps.setInt(7, board.getFree_board_file_idx());
			ps.setString(8, board.getMember_id());


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

	@Override
	public void insertFile(FreeFile boardFile) {
		//다음 게시글 번호 조회 쿼리
		String sql = "";
		sql += "INSERT INTO Free_File (  free_board_file_idx,free_board_file_no, free_board_file_origin_name, ";
		sql	+= " free_board_file_stored_name, free_board_file_upload_date,  free_board_no)";  
				
		sql += " VALUES (Freefile_seq.nextval, ?, ?, ?, ?, ?)";
		
		try {
			//DB작업
			ps = conn.prepareStatement(sql);
			ps.setInt(1, boardFile.getFree_board_file_no());
			ps.setString(2, boardFile.getFree_board_file_origin_name());
			ps.setString(3, boardFile.getFree_board_file_stored_name());
			ps.setDate(4, (java.sql.Date) boardFile.getFree_board_file_upload_date());

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

	@Override
	public FreeFile selectFile(FreeBoard board) {
		//다음 게시글 번호 조회 쿼리
		String sql = "";
		sql += "SELECT * FROM Free_File";
		sql += " WHERE free_board_no = ?";
		
		FreeFile boardFile = new FreeFile();
		
		try {
			//DB작업
			ps = conn.prepareStatement(sql);
			ps.setInt(1, board.getFree_board_no());

			rs = ps.executeQuery();
			
			while(rs.next()) {
			
				boardFile.setFree_board_file_idx( rs.getInt("free_board_file_idx") );
				boardFile.setFree_board_file_no( rs.getInt("free_board_file_no") );
				boardFile.setFree_board_file_origin_name( rs.getString("free_board_file_origin_name") );
				boardFile.setFree_board_file_stored_name( rs.getString("free_board_file_stored_name") );
				boardFile.setFree_board_file_upload_date( rs.getDate("free_board_file_written_date") );
				boardFile.setFree_board_no( rs.getInt("free_board_no") );
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				//DB객체 닫기
				if(ps!=null)	ps.close();
				if(rs!=null)	rs.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return boardFile;
		
	}

}
