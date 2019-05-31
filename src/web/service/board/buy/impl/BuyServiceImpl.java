package web.service.board.buy.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import web.dao.board.buy.face.BuyDao;
import web.dao.board.buy.impl.BuyDaoImpl;


import web.dto.BuyBoard;
import web.dto.BuyFile;
import web.service.board.buy.face.BuyService;
import web.util.Paging;

public class BuyServiceImpl implements BuyService{
	
	private BuyDao buyDao = new BuyDaoImpl();
	
	@Override
	public List getList(Paging paging) {

		return buyDao.selectAll(paging);
	}
	
	@Override
	public Paging getCurPage(HttpServletRequest req) {
		
		// 전달파라미터 curPage 파싱
		String param = req.getParameter("curPage");
		int curPage = 0;
		if( param!=null && !"".equals(param) ) {
			curPage = Integer.parseInt(param);
		}
		
		// 전체 게시글 수
		int totalCount = buyDao.selectCntAll();
		
		// 페이징 객체 생성
		Paging paging = new Paging(totalCount, curPage);
//		System.out.println(paging); //TEST
		
		return paging;
	}

	@Override
	public BuyBoard getBoardno(HttpServletRequest req) {
		
		// 전달파라미터 boardno 파싱
				String param = req.getParameter("boardno");
				int boardno = 0;
				if( param!=null && !"".equals(param) ) {
					boardno = Integer.parseInt(param);
				}
				
				//Board 객체 생성
				BuyBoard board = new BuyBoard();
				board.setBoardno(boardno);
				
				return board;
	}

	@Override
	public BuyBoard view(BuyBoard viewBoard) {
		
		buyDao.updateHit(viewBoard);
		
		return buyDao.selectBoardByBoardno(viewBoard);
	}

	@Override
	public void write(HttpServletRequest req) {
		BuyBoard buyboard = null;
		BuyFile	 buyFile = null;
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		
		if(!isMultipart) {
			return;
			
		} else {
			//파일업로드를 사용하고 있을 경우
			buyboard = new BuyBoard();

			//디스크팩토리
			DiskFileItemFactory factory = new DiskFileItemFactory();

			//메모리처리 사이즈
			factory.setSizeThreshold(1 * 1024 * 1024); //1MB

			//임시 저장소
			File repository=new File(req.getServletContext().getRealPath("tmp"));
			factory.setRepository(repository);
			
			//업로드 객체 생성
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			//용량 제한 설정 : 10MB
			upload.setFileSizeMax(10 * 1024 * 1024);
			
			//form-data 추출 
			List<FileItem> items = null;
			try {
				items = upload.parseRequest(req);
				
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			
			//파싱된 데이터 처리 반복자
			Iterator<FileItem> iter = items.iterator();
			
			//요청정보 처리
			while( iter.hasNext() ) {
				FileItem item = iter.next();
				
				// 빈 파일 처리
				if( item.getSize() <= 0 )	continue;
				
				// 빈 파일이 아닐 경우
				if( item.isFormField() ) {
					
					try {
						
						//제목 처리
						if( "title".equals( item.getFieldName() ) ) {
								buyboard.setTitle( item.getString("utf-8") );
						}
						
						//본문 처리
						if( "content".equals( item.getFieldName() ) ) {
							buyboard.setContent( item.getString("utf-8") );
						}
						
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					
					//작성자id 처리
					buyboard.setWriter((String) req.getSession().getAttribute("member_id"));
					
				} else {
					UUID uuid = UUID.randomUUID();
//					System.out.println(uuid);
					
					String u = uuid.toString().split("-")[4];
//					System.out.println(u);
					// -----------------
					
					//로컬 저장소 파일
					String stored = item.getName() + "_" + u;
					File up = new File(
						req.getServletContext().getRealPath("upload")
						, stored);
					
					buyFile = new BuyFile();
					buyFile.setOriginName(item.getName());
					buyFile.setStoredName(stored);
					buyFile.setFilesize(item.getSize());
					
					try {
						// 실제 업로드
						item.write(up);
						
						// 임시 파일 삭제
						item.delete();
						
					} catch (Exception e) {
						e.printStackTrace();
					} // try end
				} //if end
			} //while end
		} //if(!isMultipart) end
		

		int boardno = buyDao.selectBoardno();
		
		if(buyboard != null) {
			buyboard.setBoardno(boardno);
			
			if(buyboard.getTitle()==null || "".equals(buyboard.getTitle())) {
				buyboard.setTitle("(제목없음)");

				//작성자id 처리
				buyboard.setWriter((String) req.getSession().getAttribute("member_id"));
			}

			buyDao.insert(buyboard);
		}
		
		if(buyFile != null) {
			buyFile.setBoardno(boardno);
			buyDao.insertFile(buyFile);
		}
		
	}

	@Override
	public BuyFile viewFile(BuyBoard buyboard) {
		
		return buyDao.selectFile(buyboard);
		
	}

	@Override
	public boolean checkWriter(HttpServletRequest req) {
		//로그인한 세션 ID 얻기
				String loginId = (String) req.getSession().getAttribute("member_id");

				//작성한 게시글 번호 얻기
				BuyBoard buyboard = getBoardno(req);
				
				//게시글 얻어오기
				buyboard = buyDao.selectBoardByBoardno(buyboard);

				//게시글의 작성자와 로그인 아이디 비교
				if( !loginId.equals(buyboard.getWriter()) ) {
					return false;
				}
				
				return true;
	}

	@Override
	public void update(HttpServletRequest req) {
		BuyBoard board = null;
		BuyFile boardFile = null;
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		
		if(!isMultipart) {
			//파일 첨부가 없을 경우
			board = new BuyBoard();
			
			board.setTitle(req.getParameter("title"));
			board.setWriter((String) req.getSession().getAttribute("writer"));
			board.setContent(req.getParameter("content"));
			
		} else {
			//파일업로드를 사용하고 있을 경우
			board = new BuyBoard();

			//디스크팩토리
			DiskFileItemFactory factory = new DiskFileItemFactory();

			//메모리처리 사이즈
			factory.setSizeThreshold(1 * 1024 * 1024); //1MB

			//임시 저장소
			File repository=new File(req.getServletContext().getRealPath("tmp"));
			factory.setRepository(repository);
			
			//업로드 객체 생성
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			//용량 제한 설정 : 10MB
			upload.setFileSizeMax(10 * 1024 * 1024);
			
			//form-data 추출 
			List<FileItem> items = null;
			try {
				items = upload.parseRequest(req);
				
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			
			//파싱된 데이터 처리 반복자
			Iterator<FileItem> iter = items.iterator();
			
			//요청정보 처리
			while( iter.hasNext() ) {
				FileItem item = iter.next();
				
				// 빈 파일 처리
				if( item.getSize() <= 0 )	continue;
				
				// 빈 파일이 아닐 경우
				if( item.isFormField() ) {
					try {
						if( "boardno".equals( item.getFieldName() ) ) {
							board.setBoardno( Integer.parseInt(item.getString()) );
						}
	
						if( "title".equals( item.getFieldName() ) ) {
							board.setTitle( item.getString("utf-8") ); 
						}
						if( "content".equals( item.getFieldName() ) ) {
							board.setContent( item.getString("utf-8") );
						}
						
						board.setWriter((String) req.getSession().getAttribute("writer"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} else {
					UUID uuid = UUID.randomUUID();
//					System.out.println(uuid);
					
					String u = uuid.toString().split("-")[4];
//					System.out.println(u);
					// -----------------
					
					//로컬 저장소 파일
					String stored = item.getName() + "_" + u;
					File up = new File(
						req.getServletContext().getRealPath("upload")
						, stored);
					
					boardFile = new BuyFile();
					boardFile.setOriginName(item.getName());
					boardFile.setStoredName(stored);
					boardFile.setFilesize(item.getSize());
					
					try {
						// 실제 업로드
						item.write(up);
						
						// 임시 파일 삭제
						item.delete();
						
					} catch (Exception e) {
						e.printStackTrace();
					} // try end
				} //if end
			} //while end
		} //if(!isMultipart) end
		

//		System.out.println(board);
//		System.out.println(boardFile);
		
		if(board != null) {
			buyDao.update(board);
		}
		
		if(boardFile != null) {
			boardFile.setBoardno(board.getBoardno());
			buyDao.insertFile(boardFile);
		}
		
	}

	@Override
	public void delete(BuyBoard board) {
		
		buyDao.deleteFile(board);
		
		buyDao.delete(board);
		
		
	}
	
}