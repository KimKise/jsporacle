package config;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisService {
	//SqlSessionFactoryBuilder	=>	SqlSessionFactory	=>	SqlSession을 만들어냄
	//SqlSession 객체 생성기
	private static SqlSessionFactory factory;
	static {
		try { 
			//Resources는 java Resources의 src를 검색
			//sqlMapConfig.xml myBatis설정 파일을 읽어 들임
			Reader r = Resources.getResourceAsReader("config/sqlMapConfig.xml");
			//SqlSettionFactory 생성기
			factory = new SqlSessionFactoryBuilder().build(r);
			r.close();	//Reader를 닫음.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SqlSessionFactory getFactory() {
		return factory;
	}
}
