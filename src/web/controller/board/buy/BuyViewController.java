package web.controller.board.buy;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.dto.BuyBoard;
import web.dto.BuyFile;
import web.dto.Comment;
import web.service.board.buy.face.BuyService;
import web.service.board.buy.impl.BuyServiceImpl;


@WebServlet("/buy/view")
public class BuyViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private BuyService buyservice = new BuyServiceImpl();
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	// 게시글 번호 파싱
    	BuyBoard viewBoard = buyservice.getBoardno(req);
    	
    	// 게시글 조회 및 MODEL로 게시글 전달
    	viewBoard = buyservice.view(viewBoard);
    	req.setAttribute("viewBoard", viewBoard);
    	
    	// 첨부 파일 전달
    	BuyFile buyFile = buyservice.viewFile(viewBoard);
    	req.setAttribute("buyFile", buyFile);
    	
    	
    	//댓글 리스트 전달
    	Comment comment = new Comment();
    	List<Comment> commentList = buyservice.getCommentList(viewBoard);
    	req.setAttribute("commentList", commentList);
    			
    	
    	// 이전굴, 다음글
    	HashMap map = buyservice.getPrevNext(viewBoard);
    	req.setAttribute("prev_next", map);
    	
    	// title
    	HashMap name = buyservice.getPrevNextName(viewBoard);
    	req.setAttribute("prev_next_name", name);
    	
    	req.getRequestDispatcher("/WEB-INF/views/board/buy/buyview.jsp").forward(req, resp);
    	
    	
    	
    }
    
    
    
    

}
