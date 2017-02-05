package member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.dao.Member2DAO;
import member.dto.Member2DTO;


@WebServlet("/login_servlet/*")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		String context = request.getContextPath();
		Member2DAO dao = Member2DAO.getInstance();
		System.out.println(url);
		if(url.indexOf("join.do")!= -1){
			String userid = request.getParameter("userid");
			String passwd = request.getParameter("passwd");
			String name = request.getParameter("name");
			Member2DTO dto = new Member2DTO(userid, passwd, name);
			System.out.println(dto);
			String result =dao.insert(dto);
			request.setAttribute("result", result);
			String page="/member/login_result.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		}else if(url.indexOf("login.do") != -1){
			String userid = request.getParameter("userid");
			String passwd = request.getParameter("passwd");
			Member2DTO dto = new Member2DTO(userid, passwd, "");
			System.out.println(dto);
			String result = dao.loginCheck(dto);
			request.setAttribute("result", result);
			String page="/member/login_result.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
