package web.controller.board.festival;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.dao.board.festival.FestivalDao;
import web.dao.board.festival.FestivalDaoImpl;
import web.dto.festival.FestivalBoard;
import web.service.board.festival.FestivalService;
import web.service.board.festival.FestivalServiceImpl;

@WebServlet("/board/festival/detail")
public class FestivalDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private FestivalService festivalService = new FestivalServiceImpl();
	private FestivalDao festivalDao = new FestivalDaoImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		FestivalBoard board = festivalService.getBoardno(req);
		festivalDao.selectImgByBoardno(board);
		festivalService.view(board);
		req.setAttribute("board", board);
		
		
		req.getRequestDispatcher("/WEB-INF/views/board/festival/festival_detail.jsp").forward(req, resp);
	}

}
