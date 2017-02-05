package member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.dao.Member2DAO;
import member.dto.Member2DTO;



@WebServlet("/session_servlet/*")
public class SessionLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		Member2DAO dao = Member2DAO.getInstance();
		if(url.indexOf("login.do") != -1){ //로그인
			String userid = request.getParameter("userid");
			String passwd = request.getParameter("passwd");
			Member2DTO dto = new Member2DTO();
			dto.setUserid(userid);
			dto.setPasswd(passwd);
			String result=dao.loginCheck(dto);
			System.out.println(result);
			String page="/session/session_login.jsp";
			if(!result.equals("로그인 실패")){//성공하면 세션에 등록
				//세션객체 생성
				HttpSession session = request.getSession();
				//세션변수에 값 설정
				session.setAttribute("userid", userid);
				session.setAttribute("message", result);
				page="/session/main.jsp";
			}
			//페이지 이동
			response.sendRedirect(request.getContextPath()+page);
		}else if(url.indexOf("logout.do") != -1){ //로그아웃
			//세션 초기화(전체 삭제)
			//한줄 처리 : request.getSession().invalidate();
			HttpSession session = request.getSession();
			session.invalidate();
			//페이지 이동
			String page = request.getContextPath()+"/session/session_login.jsp?message=logout";
			response.sendRedirect(page);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
