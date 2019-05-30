package web.controller.board.notice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.dao.board.notice.NoticeDao;
import web.dao.board.notice.NoticeDaoImpl;
import web.service.board.notice.NoticeService;
import web.service.board.notice.NoticeServiceImpl;

/**
 * Servlet implementation class NoticeWriteController
 */
@WebServlet("/board/notice/write")
public class NoticeWriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private NoticeService noticeService = new NoticeServiceImpl();
	private NoticeDao noticeDao = new NoticeDaoImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		HttpSession session = req.getSession();
		
		//���� ���ǿ� �α��� ��ü�� ���ٸ�,�۾��� ��ư ������ �� �������װԽ������� �����̷�Ʈ���� 
		if( req.getSession().getAttribute("login") == null ) {
			resp.sendRedirect("/board/notice/list");
			return;
		}
		
		//������ ����Ȯ�ε�����-->�۾��������� �̵���Ŵ
		req.getRequestDispatcher("/WEB-INF/views/board/notice/write.jsp").forward(req, resp);
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		

		
		//db�� insert�޼ҵ� ����
		noticeService.writeNotice(req);
		
		//post��ư ������ ������� �̵���Ŵ
		resp.sendRedirect("/board/notice/list");
		
	}
	
	
	
}
