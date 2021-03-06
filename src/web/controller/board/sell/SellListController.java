package web.controller.board.sell;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.dao.member.MemberDao;
import web.dao.member.MemberDaoImpl;
import web.service.board.sell.face.SellService;
import web.service.board.sell.impl.SellServiceImpl;
import web.util.Paging;


@WebServlet("/sell/list")
public class SellListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
		
	private SellService sellservice = new SellServiceImpl();
	private MemberDao memberDao = new MemberDaoImpl();
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	Paging paging = sellservice.getCurPage(req);
    	
    	// MODEL로 Paging 객체 넣기
    	req.setAttribute("paging", paging);
    	
    	// 게시판 목록 조회
    	List list = sellservice.getList(paging);
    	
    	req.setAttribute("list", list);
    	
    	
    	int res = 0;
    	
    	if( req.getSession().getAttribute("member_id") != null) {
    		
    		res = 1;
    	}
    	
    	req.setAttribute("res", res);
    	
    	
    	// VIEW 지정
    	req.getRequestDispatcher("/WEB-INF/views/board/sell/selllist.jsp").forward(req, resp);
    }

}
