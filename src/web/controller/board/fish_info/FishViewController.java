package web.controller.board.fish_info;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.dto.FishInfo;
import web.service.board.fish_info.FishService;
import web.service.board.fish_info.FishServiceImpl;
import web.util.Fish_Paging;

/**
 * Servlet implementation class FishViewController
 */
@WebServlet("/board/fish/info")
public class FishViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private FishService fishService = new FishServiceImpl() ;
       
	@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Fish_Paging paging = fishService.getCurPage(req) ;
		
		req.setAttribute( "paging", paging);
		
		List fishlist = fishService.getList( paging ) ;
		
		req.setAttribute( "fishlist", fishlist);
		
		req.getRequestDispatcher( "/WEB-INF/views/board/fish_info/fish_info.jsp" ).forward(req, resp);
		}
}
