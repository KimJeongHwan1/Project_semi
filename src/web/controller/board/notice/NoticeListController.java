package web.controller.board.notice;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.service.board.notice.NoticeService;
import web.service.board.notice.NoticeServiceImpl;
import web.util.Paging;

/**
 * Servlet implementation class NoticeListController
 */
@WebServlet("/board/notice/list")
public class NoticeListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private NoticeService noticeService = new NoticeServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//��û�Ķ���Ϳ��� curPage ������
		Paging paging = noticeService.getCurPage(req);
				
		//MODEL�� Paging ��ü �ֱ�
		req.setAttribute("paging", paging);
							
		//�Խ��� ��� ��ȸ
		List list = noticeService.getList(paging);
				
		//MODEL�� ��ȸ ��� �ֱ�
		req.setAttribute("list", list);
		
		
		//view����
		req.getRequestDispatcher("/WEB-INF/views/board/notice/list.jsp").forward(req, resp);
	
	}
	
    
}
