package transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import config.DB;

public class EmpDAO {
	public void insert(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DB.dbConn();
			//오토 커밋 옵션을 해제
			conn.setAutoCommit(false);
			long start = System.currentTimeMillis();	//commit 시간구하기 위해
			for(int i=1; i<=100; i++){
				//i가 50이 되면 강제 Exception이 돼서 try문을 탈출
				//49번까지는 잘되다 50번째 빠져나옴
				//if(i==50) throw new Exception();
				String sql = "insert into emp values(?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, Integer.toString(i));
				pstmt.setString(2, "부서"+i);
				pstmt.setString(3, "지역"+i);
				pstmt.executeUpdate();
				pstmt.close();
			}
			long end = System.currentTimeMillis();
			System.out.println("실행시간:"+(end-start));
			//for문 끝나고 한 번에 커밋
			conn.commit();//수동 커밋
			conn.setAutoCommit(true); //오토 커밋으로 전환
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if(conn != null)conn.rollback(); //에러가 발생하면 롤백
			} catch (SQLException e1) {
				e1.printStackTrace();
			} 
		}finally{
			
		}
	}
	
	//insert sql문을 for문에서 명령어당 하나씩 실행하지 말고 한번에 실행하는 
	public void insert_batch(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DB.dbConn();
			//오토 커밋 옵션을 해제
			conn.setAutoCommit(false);
			String sql = "insert into emp values(?,?,?)";
			pstmt = conn.prepareStatement(sql);
			long start = System.currentTimeMillis();	//commit 시간구하기 위해
			for(int i=101; i<=200; i++){
				pstmt.setString(1, Integer.toString(i));
				pstmt.setString(2, "부서"+i);
				pstmt.setString(3, "지역"+i);
				pstmt.addBatch();	//일괄처리 작업 예약
				
			}
			pstmt.executeBatch(); 	//일괄처리작업 실행
			long end = System.currentTimeMillis();
			System.out.println("실행시간:"+(end-start));
			//for문 끝나고 한 번에 커밋
			conn.commit();//수동 커밋
			conn.setAutoCommit(true); //오토 커밋으로 전환
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if(conn != null)conn.rollback(); //에러가 발생하면 롤백
			} catch (SQLException e1) {
				e1.printStackTrace();
			} 
		}finally{
			
		}
	}
}
