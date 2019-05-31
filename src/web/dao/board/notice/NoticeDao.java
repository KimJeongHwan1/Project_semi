package web.dao.board.notice;

import java.util.List;

import web.dto.Notice;
import web.util.Paging;

public interface NoticeDao {

	//��ȸ��� ����¡ ��ü
	public List selectAll(Paging paging);
	
	//���̺� ��ü �� �� ��ȸ ���
	public int selectCntAll(String search);
	
	//�������� ��ȸ�� +1���ִ� �޼ҵ�
	public void updateHit(Notice notice);
	
	//�������� ������ �޼ҵ带 notice��ȣ�� ����
	public Notice selectNoticeByNoticeno(Notice notice);
	
	//�������� ��ȣ �������� �޼ҵ�
	public int selectNoticeno();
	
	public void insert(Notice notice);
	
	public void update(Notice notice);
	
	public void delete(Notice notice);
	
	
	
}
