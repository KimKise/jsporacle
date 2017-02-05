package board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;

import com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker;

import board.dto.BoardCommentDTO;
import board.dto.BoardDTO;
import config.MybatisService;

public class BoardDAO {
	//싱글톤
	private static BoardDAO instance;
	private BoardDAO(){}
	public static BoardDAO getInstance(){
		if(instance==null){
			instance = new BoardDAO();
		}
		return instance;
	}
	
	//댓글 작성자, 내용 체크
	public BoardCommentDTO checkComment(BoardCommentDTO dto) {
		//태그 처리 ( < => &lt;   > => &gt;), Less Than/Greater Than의 약자이다
		String writer = dto.getWriter();
		if(writer.toLowerCase().indexOf("xmp") != -1 || writer.toLowerCase().indexOf("script") != -1 || writer.toLowerCase().indexOf("href") != -1){
			writer = writer.replace("<", "&lt;");
			writer = writer.replace(">", "&gt;");
		}
		String content = dto.getContent();
		if(content != null){
			if(content.toLowerCase().indexOf("xmp") != -1 || content.toLowerCase().indexOf("script") != -1 || content.toLowerCase().indexOf("href") != -1 ){
				content = content.replace("<", "&lt;");
				content = content.replace(">", "&gt;");
			}
			content = content.replace("  ", "&nbsp;&nbsp;");
			//줄바꿈 처리
			content = content.replace("\n", "<br>");
		}
		//공백 처리 (공백2개를 &nbsp;&nbsp;로) 
		writer = writer.replace("  ", "&nbsp;&nbsp;");
		dto.setWriter(writer);
		dto.setContent(content);
		return dto;
	}
	public BoardDTO checkArticle(BoardDTO dto) {
		//태그 처리 ( < => &lt;   > => &gt;), Less Than/Greater Than의 약자이다
		String writer = dto.getWriter();
		if(writer.toLowerCase().indexOf("xmp") != -1 || writer.toLowerCase().indexOf("script") != -1 || writer.toLowerCase().indexOf("href") != -1){
			writer = writer.replace("<", "&lt;");
			writer = writer.replace(">", "&gt;");
		}
		String subject = dto.getSubject();
		if(subject.toLowerCase().indexOf("xmp") != -1 || subject.toLowerCase().indexOf("script") != -1 || subject.toLowerCase().indexOf("href") != -1 ){
			subject = subject.replace("<", "&lt;");
			subject = subject.replace(">", "&gt;");
		}
		String content = dto.getContent();
		if(content != null){
			if(content.toLowerCase().indexOf("xmp") != -1 || content.toLowerCase().indexOf("script") != -1 || content.toLowerCase().indexOf("href") != -1 ){
				content = content.replace("<", "&lt;");
				content = content.replace(">", "&gt;");
			}
			content = content.replace("  ", "&nbsp;&nbsp;");
			//줄바꿈 처리
			content = content.replace("\n", "<br>");
		}
		//공백 처리 (공백2개를 &nbsp;&nbsp;로) 
		writer = writer.replace("  ", "&nbsp;&nbsp;");
		subject = subject.replace("  ", "&nbsp;&nbsp;");	
		dto.setSubject(subject);
		dto.setWriter(writer);
		dto.setContent(content);
		return dto;
	}
	
	public void commentAdd(BoardCommentDTO dto){
		SqlSession session = null;
		try {
			session = MybatisService.getFactory().openSession();
			session.insert("board.commentAdd",dto);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if( session != null) session.close();
		}
	}
	
	public List<BoardCommentDTO> commentList(int num){
		List<BoardCommentDTO> list= null;
		SqlSession session = null;
		try {
			session = MybatisService.getFactory().openSession();
			list = session.selectList("board.commentList",num);
			for(BoardCommentDTO dto : list){
				checkComment(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session !=null)session.close();
		}
		return list;
	}
	
	public void plusReadCount(int num, HttpSession count_session ){
		SqlSession session = null;
		try {
			//최근 열람 시간 조회(세션변수로), 일정시간 조회수 증가 처리를 위해 
			long update_time= 0;
			// 최근 열람 시간이 세션에 저장돼있지 않으면
			if(count_session.getAttribute("update_time_"+num)!=null){//--getAttribute
				update_time=(long)count_session.getAttribute("update_time_"+num);
			}
			//현재 시간
			long current_time = System.currentTimeMillis();
			session = MybatisService.getFactory().openSession();
			if(current_time - update_time > 5*1000){ //현재 시간과 비교하여 5초 이상이면 업데이트를 하세요
				session.update("board.plusReadCount", num);
				session.commit();
				//업데이트 끝나고 최근 열람 시간을 세션에 저장
				count_session.setAttribute("update_time_"+num, current_time);//--setAttribute
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null)session.close();
		}
	}
	
	public BoardDTO view(int num){
		BoardDTO dto =null;
		SqlSession session = null;
		try {
			session = MybatisService.getFactory().openSession();
			//selectOne() 레코드1개, selectList() 게코드2개 이상
			dto = session.selectOne("board.view",num);
			//줄바꿈, 공백, <>처리
			dto=checkArticle(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null) session.close();
		}
		return dto;
	}
	//전체 레코드 카운트
	public int count(){
		int result = 0;
		try(SqlSession session = MybatisService.getFactory().openSession()){
			result=session.selectOne("board.count");
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	//검색된  레코드 카운트
	public int count(String search_option, String keyword){
		int result = 0;
		try(SqlSession session = MybatisService.getFactory().openSession()){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("search_option", search_option);
			map.put("keyword", keyword);
			result=session.selectOne("board.search_count", map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public List<BoardDTO> list(int pageStart, int pageEnd){
		List<BoardDTO> list = null;
		SqlSession session = null;
		try {
			session=MybatisService.getFactory().openSession();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", pageStart);
			map.put("end", pageEnd);
			list = session.selectList("board.list", map);
			for(BoardDTO dto : list){
				//줄바꿈, 공백, <>처리
				dto = checkArticle(dto);
				//확장자 검사
				if(dto.getFilename() != null){
					String filename=dto.getFilename();
					// a.b.c.d.zip
					// 마지막 인덱스
					int start = filename.lastIndexOf(".")+1;
					if(start != -1){
						String ext = filename.substring(start, filename.length());
						System.out.println(ext);
						//파일 확장자 저장
						dto.setExt(ext);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null) session.close();
		}
		return list;
	}
	
	public void insert(BoardDTO dto){
		SqlSession session = null;
		try {
			// mybatis 실행객체 (SqlSession) 생성
			session = MybatisService.getFactory().openSession();
			//insert요청
			session.insert("board.insert", dto);
			// 커밋(insert, delete, update)는 커밋이 필요
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//세션 닫기
			if(session != null) session.close();
		}
	}
	
	
	public String getFileName(int num){
		String result = null;
		SqlSession session = null;
		try {
			session = MybatisService.getFactory().openSession();
			result = session.selectOne("board.getFileName", num);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null) session.close();
		}
		return result;
	}
	
	public void plusDown(int num){
		SqlSession session = null;
		try {
			session = MybatisService.getFactory().openSession();
			session.update("board.plusDown", num);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null) session.close();
		}
	}
	
	public void reply(BoardDTO dto){
		SqlSession session=null;
		try {
			session = MybatisService.getFactory().openSession();
			session.insert("board.reply", dto);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if( session != null) session.close();
		}
	}
	//답글 순서 조정
	public void updateStep(int ref, int re_step){
		SqlSession session = null;
		try {
			session = MybatisService.getFactory().openSession();
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("ref", ref);
//			map.put("re_step", re_step);
//			session.update("board.updateStep", map);
			BoardDTO dto = new BoardDTO();
			dto.setRef(ref);
			dto.setRe_step(re_step);
			session.update("board.updateStep", dto);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null) session.close();
		}
	}
	
	//비밀번호 체크
	public String passwdCheck(int num, String passwd){
		String result=null;
		SqlSession session = null;
		try {
			session = MybatisService.getFactory().openSession();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("num", num);
			map.put("passwd", passwd);
			result = session.selectOne("board.pass_check", map);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null) session.close();
		}
		return result;
	}
	
	public void update(BoardDTO dto){
		SqlSession session = null;
		try {
			session = MybatisService.getFactory().openSession();
			session.update("board.update", dto);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null) session.close();
		}
	}
	//게시물 검색
	public List<BoardDTO> searchList(String searchOption, String keyword, int start, int end){
		List<BoardDTO> list = null;
		//JDK 1.7이후부터 사용 가능
		//try~with라고 한다. try(임시객체) : try절 안에서만 쓸 수 있는 임시 객체 
		// finally절에서 닫지 않아도 됨
		try(SqlSession session = MybatisService.getFactory().openSession()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("search_option", searchOption);
			map.put("keyword",keyword);
			map.put("start", start);
			map.put("end", end);
			list= session.selectList("board.searchList", map);
			
			//키워드의 색상 변경 처리
			for(BoardDTO dto : list){
				String writer = dto.getWriter();
				String subject = dto.getSubject();
				writer = writer.replace(keyword, "<span style='color:red'>"+keyword+"</span>");
				subject = subject.replace(keyword, "<span style='color:red'>"+keyword+"</span>");
				dto.setWriter(writer);
				dto.setSubject(subject);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}return list;
	}
}
