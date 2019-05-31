package web.controller.board.notice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.dto.Notice;
import web.service.board.notice.NoticeService;
import web.service.board.notice.NoticeServiceImpl;


@WebServlet("/board/notice/delete")
public class NoticeDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private NoticeService noticeService = new NoticeServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		
		req.getRequestDispatcher("/WEB-INF/views/board/notice/delete.jsp").forward(req, resp);
		//�Խñ� ��ü����
		Notice notice = new Notice();
	
		int notice_no = Integer.parseInt(req.getParameter("notice_no"));
		//�Ķ���ͷ� �Խñ� ��ȣ �����ͼ� int������ ����ȯ		
		notice.setNotice_no(notice_no);
		//������ ��ü�� notice_no ���� �־���.
		
		
		//�޼ҵ����
		noticeService.deleteNotice(notice);
		
		//�������� delete�������� �����̷�Ʈ
//		resp.sendRedirect("/board/notice/delete");
	
		
		
	}
	
	
}
