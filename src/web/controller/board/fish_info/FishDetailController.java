package web.controller.board.fish_info;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.dao.member.MemberDao;
import web.dao.member.MemberDaoImpl;
import web.dto.FishInfo;
import web.service.board.fish_info.FishService;
import web.service.board.fish_info.FishServiceImpl;

/**
 * Servlet implementation class FishDetailController
 */
@WebServlet("/board/fish/info/detail")
public class FishDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private FishService fishService = new FishServiceImpl() ;
	private MemberDao memberDao = new MemberDaoImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 상세보기
		FishInfo fishInfo = fishService.getBoardno(req) ;
		
		fishInfo = fishService.view(fishInfo) ;
		
		req.setAttribute( "fishInfo" , fishInfo ) ;
		
	//	System.out.println( fishInfo );
		
		// 이전글 / 다음글 Fish_no
		HashMap map = fishService.getPrevNext(fishInfo);
//		System.out.println(map);
		req.setAttribute("prev_next", map);
		
		// 이전글 / 다음글 Fish_name
		HashMap name = fishService.getPrevNextName(fishInfo) ;
		req.setAttribute( "prev_next_name", name);
		
		// 권한 Hide
		int res = 0 ;
		
		if( req.getSession().getAttribute("member_id") != null) {
			
			int memCode = (int) req.getSession().getAttribute("member_code");
	
			res = memberDao.checkMemberCode(memCode);
		
		}
		req.setAttribute("res", res);
		
		req.getRequestDispatcher( "/WEB-INF/views/board/fish_info/fish_info_detail.jsp" ).forward(req, resp);
	}
}
