package board;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sun.org.apache.bcel.internal.classfile.ConstantString;

import board.dao.BoardDAO;
import board.dao.PageDAO;
import board.dto.BoardCommentDTO;
import board.dto.BoardDTO;
import common.Constants;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("/board_servlet/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//사용자가 요청한 주소
		String url=request.getRequestURL().toString();
		//컨텍스트패스
		String contextPath = request.getContextPath();
		//DAO객체 생성
		BoardDAO dao = BoardDAO.getInstance();
		if(url.indexOf("list.do") != -1){
			int count = dao.count();			//레코드 갯수 계산
			int curPage=1;						//현재 페이지 번호 설정
			if(request.getParameter("curPage") != null){
				curPage=Integer.parseInt(request.getParameter("curPage"));
			}
			PageDAO pageDao = new PageDAO(count, curPage);
			//현재 페이지의 시작, 끝 번호 계산
			int start = pageDao.getPageBegin();
			int end = pageDao.getPageEnd();
			
			//게시물 목록을 리턴받음
			List<BoardDTO> list = dao.list(start,end);
			//저장
			request.setAttribute("list", list);
			request.setAttribute("page", pageDao);
			//포워드
			String page ="/board/list.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			
		}else if(url.indexOf("insert.do") != -1){
			//폼에서 입력한 값을 dto에 저장
			BoardDTO dto = new BoardDTO();
			//첨부파일처리를 위한 MultipartRequest 선언
			//request객체를 multipartrequest객체로 확장
			MultipartRequest multi = new MultipartRequest(request, Constants.UPLOAD_PATH, Constants.MAX_UPLOAD, "utf-8", new DefaultFileRenamePolicy());
			String filename="";
			int filesize=0;
			try {
				Enumeration files = multi.getFileNames();
				//첨부파일이 없으면 while문은 통과, 여러개면 while안에서 처리됨
				while(files.hasMoreElements()){
					String file1=(String)files.nextElement();
					//첨부파일의 이름
					filename=multi.getFilesystemName(file1);
					File f1 = multi.getFile(file1);
					//첨부파일의 size
					if(f1 != null){
						filesize=(int)f1.length();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//request => multi로 변경 MultipartRequest의 requeset객체와 같이 사용 못함
			String writer = multi.getParameter("writer");
			String subject = multi.getParameter("subject");
			String content = multi.getParameter("content");
			String passwd = multi.getParameter("passwd");
			String ip = request.getRemoteAddr();	//ip 주소
			dto.setWriter(writer);
			dto.setSubject(subject);
			dto.setContent(content);
			dto.setPasswd(passwd);;
			dto.setIp(ip);
			//trim()은 좌우 공백을 지우는 함수
			if(filename ==null || filename.trim().equals("")) filename="-";
			
			//확장자 검사
			int start = filename.lastIndexOf(".")+1;
			if(start != -1){
				String ext = filename.substring(start, filename.length());
				//업로드 금지 파일 확장자 목록
				//white list(허용할 목록)
				//black list(금지할 목록)
				String[] bad_ext = {"exe", "com","bat","sh","jsp","php","aspx"};
				for(String str:bad_ext){
					if(ext.contentEquals(str)){

						response.sendRedirect(request.getContextPath()+"/board/write.jsp?message=error");
						return;
					}
				}
			}
			
			dto.setFilename(filename);
			dto.setFilesize(filesize);
			//dao에 insert 요청
			System.out.println(dto);
			dao.insert(dto);
			// list.do로 이동
			String page=contextPath+"/board_servlet/list.do";
			response.sendRedirect(page);
		}else if(url.indexOf("view.do") != -1){
			//게시물 번호
			int num = Integer.parseInt(request.getParameter("num"));
			//세션 객체 생성
			HttpSession session = request.getSession();
			//조회수 증가 처리, view를 꺼내기 전에 증가 처리하고 뿌려줌
			dao.plusReadCount(num, session);
			//dto를 리턴받아 저장
			BoardDTO dto = dao.view(num);

			request.setAttribute("dto", dto);
			//포워드(화면전환 +데이터전달)
			String page = "/board/view.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		}else if(url.indexOf("commentList.do") != -1){
			//게시물 번호
			int num = Integer.parseInt(request.getParameter("num"));
			//댓글 목록을 리턴받음
			List<BoardCommentDTO> list = dao.commentList(num);
			request.setAttribute("list", list);
			//포워딩
			String page="/board/comment_list.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		}else if(url.indexOf("comment_add.do") != -1){
			//댓글폼에 입력한 값을 dto에 저장
			BoardCommentDTO dto = new BoardCommentDTO();
			int board_num = Integer.parseInt(request.getParameter("board_num"));
			String writer = request.getParameter("writer");
			String content = request.getParameter("content");
			dto.setBoard_num(board_num);
			dto.setWriter(writer);
			dto.setContent(content);
			//테이블에 입력
			dao.commentAdd(dto);
			//처리가 완료되면 ajax를 호출한 곳으로 돌아감
		}else if(url.indexOf("download.do") != -1){
			//게시물 번호
			int num=Integer.parseInt(request.getParameter("num"));
			//파일이름
			String filename = dao.getFileName(num);
			System.out.println(filename);
			//파일의 경로
			String path=Constants.UPLOAD_PATH+filename;
			//바이트 배열 선언
			byte b[] = new byte[4096];
			//입력스트림 생성, 서버에 있는 파일을 읽을때는 inputstream, 클라이언트로 보낼때는 outputstream 
			FileInputStream fis = new FileInputStream(path);
			//파일의 마임타입(파일의 종류)
			//Multipurpose Internet Mail Extension : 웹사이트상에 표현되는 멀티미디어 데이터에 대한 형식
			String mimeType = getServletContext().getMimeType(path);
			System.out.println("mimeType:"+mimeType);
			if(mimeType ==null){//마임타입이 지정되지 않았을 때는 아래로 하겠다
				mimeType="application/octet-stream;charset=utf-8";
			}
//			//한글파일이름 처리
//			//스트링.getByte() 스트링을 바이트배열로 변환
//			//new String(바이트배열) 바이트배열을 스트링으로 변환
//			//new String(바이트배열("언어셋"), "변경할 언어셋")
//			//	8859_1 서유럽언어 인코딩 방식, 한글 및 특수문자를 서유럽언어(8859_1)로 변경
			filename = new String(filename.getBytes("utf-8"),"8859_1");
			
			//http header에 첨부파일 정보 추가
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			//출력스트림으로 서버의 파일을 클라이언트로 전송
			ServletOutputStream out = response.getOutputStream();
			int numRead;
			// 읽은 바이트수 = fis.read(바이트배열, 시작위치, 길이)	//읽어 들인 바이트수를 리턴, 읽어들일 것이 없으면 -1을 리턴
			while(true){
				//서버의 파일을 읽어서 바이트배여에 저장 
				numRead = fis.read(b, 0, b.length);
				if(numRead == -1) break;
				//클라이언트에 전송
				out.write(b, 0, numRead);
			}
			//리소스 닫기
			out.flush();	//버퍼를 비움
			out.close();	//출력스트림 닫기
			fis.close();	//입력스트림 닫기
			//다운로드 횟수 증가 처리
			dao.plusDown(num);
		}else if(url.indexOf("reply.do") != -1){
			//게시물 번호
			int num = Integer.parseInt(request.getParameter("num"));
			//게시물 내용
			BoardDTO dto = dao.view(num);
			dto.setContent("========게시물의 내용========\n"+dto.getContent());
			request.setAttribute("dto", dto);
			//페이지 이동
			String page="/board/reply.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		}else if(url.indexOf("insertReply.do") != -1){
			//게시물 번호
			int num = Integer.parseInt(request.getParameter("num"));
			//게시물 정보
			BoardDTO dto = dao.view(num);
			int ref = dto.getRef(); //게시물그룹 id
			//게시물 그룹 내에서의 출력 순서
			int re_step = dto.getRe_step()+1;
			//답변 단계
			int re_level = dto.getRe_level()+1;
			//작성 내용
			String writer = request.getParameter("writer");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			String passwd = request.getParameter("passwd");
			dto.setWriter(writer);
			dto.setSubject(subject);
			dto.setContent(content);
			dto.setPasswd(passwd);
			dto.setRef(ref);
			dto.setRe_level(re_level);
			dto.setRe_step(re_step);
			//첨부파일 정보 초기화
			dto.setFilename("-");
			dto.setFilesize(0);
			dto.setDown(0);
			//답글의 순서 조정
			dao.updateStep(ref, re_step);
			//답글 쓰기
			dao.reply(dto);
			String page="/board_servlet/list.do";
			//String page="/board_servlet/view.do?num="+num;
			response.sendRedirect(request.getContextPath()+page);
		}else if(url.indexOf("pass_check.do") != -1){
			//게시물 번호
			int num = Integer.parseInt(request.getParameter("num"));
			//사용자가 입력한 비밀번호
			String passwd = request.getParameter("passwd");
			//dao에 확인 요청
			String result = dao.passwdCheck(num, passwd);
			String page="";
			if(result != null){//맞으면 패스워드가 넘어오고 틀리면 null이 넘어온다
				//비밀번호가 맞으면 수정/삭제폼으로 이동
				page="/board/edit.jsp";
				request.setAttribute("dto", dao.view(num));
				RequestDispatcher rd = request.getRequestDispatcher(page);
				rd.forward(request, response);
			}else{
				//비밀번호가 틀리면 에러메시지 리턴
				page="/board_servlet/view.do?num="+num+"&message=error";
				response.sendRedirect(request.getContextPath()+page);
			}
		}else if(url.indexOf("update.do") != -1){
			//폼에서 입력한 값을 dto에 저장
			BoardDTO dto = new BoardDTO();
			//첨부파일처리를 위한 MultipartRequest 선언
			//request객체를 multipartrequest객체로 확장
			MultipartRequest multi = new MultipartRequest(request, Constants.UPLOAD_PATH, Constants.MAX_UPLOAD, "utf-8", new DefaultFileRenamePolicy());
			String filename=" ";
			int filesize=0;
			try {
				Enumeration files = multi.getFileNames();
				//첨부파일이 없으면 while문은 통과, 여러개면 while안에서 처리됨
				while(files.hasMoreElements()){
					String file1=(String)files.nextElement();
					//첨부파일의 이름
					filename=multi.getFilesystemName(file1);
					File f1 = multi.getFile(file1);
					//첨부파일의 size
					if(f1 != null){
						filesize=(int)f1.length();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//request => multi로 변경 MultipartRequest의 requeset객체와 같이 사용 못함
			int num = Integer.parseInt(request.getParameter("num"));
			String writer = multi.getParameter("writer");
			String subject = multi.getParameter("subject");
			String content = multi.getParameter("content");
			String passwd = multi.getParameter("passwd");
			String ip = request.getRemoteAddr();	//ip 주소

			dto.setNum(num);
			dto.setWriter(writer);
			dto.setSubject(subject);
			dto.setContent(content);
			dto.setPasswd(passwd);;
			dto.setIp(ip);
			//trim()은 좌우 공백을 지우는 함수
			//첨부파일이 없을 때
			if(filename ==null || filename.trim().equals("")) {
//				BoardDTO dto2 = dao.view(num);
//				String fName= dto2.getFilename();
//				int fSize = dto2.getFilesize();
//				int fDown = dto2.getFilesize();
//				dto.setFilename(fName);
//				dto.setFilesize(fSize);
//				dto.setDown(fDown);
				filename="-";
			}
//			else{//새로운 첨부파일이 있을 때
//				dto.setFilename(filename);
//				dto.setFilesize(filesize);
//			}
//			
			//첨부파일 삭제처리
			String fileDel=multi.getParameter("fileDel");
			//체크가 되지 않으면 null, 체크되면 on
			if(fileDel != null && fileDel.equals("on")){//체크되면
				//테이블에 저장된 파일 이름
				String fileName = dao.getFileName(num);
				File f = new File(Constants.UPLOAD_PATH+fileName);
				f.delete();//파일 삭제
				//첨부파일 관련 레코드 정보 수정
				dto.setFilename("-");
				dto.setFilesize(0);
				dto.setDown(0);
			}
			
			//확장자 검사
			int start = filename.lastIndexOf(".")+1;
			if(start != -1){
				String ext = filename.substring(start, filename.length());
				//업로드 금지 파일 확장자 목록
				//white list(허용할 목록)
				//black list(금지할 목록)
				String[] bad_ext = {"exe", "com","bat","sh","jsp","php","aspx"};
				for(String str:bad_ext){
					if(ext.contentEquals(str)){
						response.sendRedirect(request.getContextPath()+"/board/write.jsp?message=error");
						return;
					}
				}
			}
			
			dto.setFilename(filename);
			dto.setFilesize(filesize);
			
			String result = dao.passwdCheck(num, passwd);
			if(result != null){//비밀번호가 맞을 때
			//dao에 update 요청
			System.out.println(dto);
			dao.update(dto);
			// list.do로 이동
			String page=contextPath+"/board_servlet/list.do";
			response.sendRedirect(page);
			}else{//비밀번호가 틀렸을 때
				String page = request.getContextPath()+"/board_servlet/view.do?num="+num+"&message=error";
				response.sendRedirect(page);
			}
		}else if(url.indexOf("search.do") != -1){
			//검색옵션
			String search_option = request.getParameter("search_option");
			//검색 키워드
			String keyword = request.getParameter("keyword");
			int count = dao.count(search_option, keyword);			//검색된 레코드 갯수 계산
			int curPage=1;						//현재 페이지 번호 설정
			if(request.getParameter("curPage") != null){
				curPage=Integer.parseInt(request.getParameter("curPage"));
			}
			PageDAO pageDao = new PageDAO(count, curPage);
			//현재 페이지의 시작, 끝 번호 계산
			int start = pageDao.getPageBegin();
			int end = pageDao.getPageEnd();
			
			
			//리스트 저장
			List<BoardDTO> list = dao.searchList(search_option, keyword, start, end);
			request.setAttribute("list", list);
			request.setAttribute("search_option", search_option);
			request.setAttribute("keyword", keyword);
			request.setAttribute("page", pageDao);
			//포워딩
			String page="/board/search.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
