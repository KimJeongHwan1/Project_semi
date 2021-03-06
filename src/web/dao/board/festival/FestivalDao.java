package web.dao.board.festival;

import java.util.List;

import web.dto.festival.FestivalBoard;
import web.util.festival.FestivalPaging;


public interface FestivalDao {
	
	public List selectAll(FestivalPaging paging);
	
	public List selectOngoing(FestivalPaging paging);
	
	public List selectClosed(FestivalPaging paging);
	
	public int selectCntAll();
	
	public int selectCntOngoing();
	
	public int selectCntClosed();
	
	public FestivalBoard selectBoardByBoardno(FestivalBoard board);
	
	public void insert(FestivalBoard board);
	
	public void update(FestivalBoard board);
	
	public void delete(FestivalBoard board);
	
	public FestivalBoard selectImgByBoardno(FestivalBoard board);

}
