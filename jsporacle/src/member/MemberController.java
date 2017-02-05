package member;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.dao.MemberDAO;
import member.dto.MemberDTO;


@WebServlet("/member_servlet/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청한 url
		String url = request.getRequestURL().toString();
		//컨텍스트 패스
		String context = request.getContextPath();
		MemberDAO dao = MemberDAO.getInstance();
		if(url.indexOf("list.do")!= -1){
			Map<String, Object> map = new HashMap<String, Object>();
			List<MemberDTO> list = dao.memberList();
			//맵에 저장
			map.put("list", list);
			map.put("count", list.size());
			request.setAttribute("map", map);
			String page ="/member/member_list.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		}else if(url.indexOf("insert.do")!= -1){
			//입력데이터를 받아서 dto에 저장
			String id = request.getParameter("id");
			String passwd = request.getParameter("passwd");
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String tel = request.getParameter("tel");
			MemberDTO dto = new MemberDTO(id, passwd, name, address, tel);
			//레코드에 저장
			dao.memberInsert(dto);
		}else if(url.indexOf("view.do") != -1){
			//클릭한 아이디
			String id = request.getParameter("id");
			MemberDTO dto = dao.memberDetail(id);
			request.setAttribute("dto", dto);
			String page="/member/member_view.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
