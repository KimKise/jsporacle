package common;

import javax.servlet.http.Cookie;

public class Util {	//쿠키배열과 변수명을 입력받아 쿠키값을 리턴하는 method
	public static String getCookie(Cookie[] cookies, String name){
		String result="";
		if(cookies != null){				
			for(int i=0; i<cookies.length; i++){	
				if(cookies[i].getName().equals(name)){
					result=cookies[i].getValue();
				}
			}
		}
		return result;
	}
}
