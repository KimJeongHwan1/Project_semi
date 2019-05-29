package web.controller.board.notice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.dao.board.notice.NoticeDao;
import web.dao.board.notice.NoticeDaoImpl;
import web.dto.Notice;
import web.service.board.notice.NoticeService;
import web.service.board.notice.NoticeServiceImpl;


@WebServlet("/board/notice/view")
public class NoticeViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	private NoticeService noticeService = new NoticeServiceImpl();
	private NoticeDao noticeDao = new NoticeDaoImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Notice notice = noticeService.getNoticeno(req);
		notice = noticeService.viewNotice(notice);
		//    <--�Խñ۹�ȣ�� ������ �� ��ü�� �º��� ����
	
		req.setAttribute("notice", notice);
			
		//VIEW ����
		req.getRequestDispatcher("/WEB-INF/views/board/notice/view.jsp").forward(req, resp);
	
	
	}
	

}