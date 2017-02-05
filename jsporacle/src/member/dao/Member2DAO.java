package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.DB;
import member.dto.Member2DTO;

public class Member2DAO {
	private static Member2DAO instance;
	private Member2DAO(){
		
	}
	public static Member2DAO getInstance(){
		if(instance == null){
			instance = new Member2DAO();
		}
		return instance;
	}
	
	public String insert(Member2DTO dto){
		String result="";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = DB.dbConn();
			//oracle의 암호화 기법 :
			//	PACK_ENCRYPITION_DECRYPITION.FUNC_ENCRYPT('비번')
			String sql ="insert into member2 (userid, passwd, name) values (?, password(?), ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserid());
			pstmt.setString(2, dto.getPasswd());
			pstmt.setString(3, dto.getName());
			int rows=pstmt.executeUpdate();
			result = rows>0?"회원 가입 완료" : "로그인 실패";
		}catch(Exception e){
			e.printStackTrace();
			return "로그인 실패";
		}finally{
			try {
				if(pstmt != null) pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	public String loginCheck(Member2DTO dto){
		String result="";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DB.dbConn();
			//oracle 에서는 password(?) 대신 PACK_ENCRYPTION_DECRYPTION.FUNC_ENCRYPT(?)를 쓴다.
			String sql="select * from member2 where userid=? and passwd=password(?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserid());
			pstmt.setString(2, dto.getPasswd());
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getString("name")+"님 환영합니다.";
			}else{
				result = "로그인 실패...";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if( rs != null)rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if( pstmt != null)pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if( conn != null)conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
}


