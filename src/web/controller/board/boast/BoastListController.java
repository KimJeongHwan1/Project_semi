package web.controller.board.boast;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.service.board.boast.BoastService;
import web.service.board.boast.BoastServiceImpl;
import web.util.boast.BoastPaging;

@WebServlet("/board/boast/list")
public class BoastListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BoastService boardService = new BoastServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BoastPaging paging = boardService.getCurPage(req);
		
		//MODEL로 Paging 객체 넣기
		req.setAttribute("paging", paging);
		
		
		
		//게시판 목록 조회
		List list = boardService.getList(paging);
		
		//MODEL로 조회 결과 넣기
		req.setAttribute("list", list);
		
		
		req.getRequestDispatcher("/WEB-INF/views/board/boast/list.jsp").forward(req, resp);
	
	
	}
	
}
