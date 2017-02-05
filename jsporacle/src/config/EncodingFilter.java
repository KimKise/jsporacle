package config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

//어노테이션 (annotation, 코드에 대한 주석)
@WebFilter("/*")	//필터와 url pattern매핑, Ex) /*는 모든요청, /memo_servlet/*
public class EncodingFilter implements Filter {
	private String charset="utf-8";
//-----------------------------------------------------------------------------------------
	//서버가 멈추면 resource정리
	public void destroy() {
		System.out.println("필터가 종료되었습니다.");
	}
//-----------------------------------------------------------------------------------------
	//요청이 들어왔을 때 거쳐가는 코드
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// place your code here
		// 선처리할 코드
		request.setCharacterEncoding(charset);
		System.out.println("필터가 실행되었습니다.");
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
//-----------------------------------------------------------------------------------------
	//필터를 초기화시킴, 서버스타트하면 자동 실행
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("필터가 초기화되었습니다.");
	}

}
