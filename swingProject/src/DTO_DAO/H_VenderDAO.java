package DTO_DAO;
/*
 * 2018-11-29 wonHn
 * ~44라인까지의 기본 틀은 메인폴더.DTO_DAO.H_FranchiseDTO를 참고했습니다.
 * 코드테스트 아직 안했습니다..!
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

import com.mysql.jdbc.Statement;

public class H_VenderDAO {
	private String url = "jdbc:mysql://localhost:3306/bbq";
	// 데이터 베이스 url
	private String user = "root";
	// mysql root 계정 아이디
	private String password = "1234";
	// mysql root 계정 비밀번호
	private String sql;
	// sql문 문자열

	private Connection con;
	private PreparedStatement ps;
	// Connection 객체와 PreparedStatement 미리 선언

	private static H_VenderDAO b_orderdao = new H_VenderDAO();
	// 싱긑톤 패턴

	int count;

	public static H_VenderDAO getInstance() {
		return b_orderdao;
	} // 싱긑톤 패턴 메서드

	public void connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// connectDB:메서드 끝

	public int LastIdex() {
		return count;
	}


	// wonHn
	//업체정보 입력메서드
	public void insertVenderInfo() {
		connectDB();
	//	sql = "insert into headvender values ('" + id + "', '" + name + "', '" + tel +"');";
	}//end insertVenderInfo()

	// wonHn
	//업체정보 수정 메서드 : 전화번호만 수정할예정입니다.
	public void updateVenderInfo() {
		connectDB();
	//	sql = "update headvender set tel='" + tel + "' where id = '"+ id +"';";
	}//end updateVenderInfo()

	// wonHn
	//업체정보 한줄 출력메서드 : 클릭으로 받아지는 인덱스값에 해당하는 id값을 넘겨줄 예정입니다.
	public void selectVenderInfo() {
		connectDB();
	//	sql = "select * from headvender where id'" + id + "';"
	}//end selectVenderInfo

	// wonHn
	//업체정보 전체 출력메서드
	public void selectALLVenderInfo() {
		connectDB();
	//	sql = "select * from headvender;"

	}//end selectALLVenderInfo

	// wonHn
	//업체정보 삭제 메서드 
	public void deleteVenderInfo() {
		connectDB();
	//	sql = "delete from headmember where id='" + id + "';"	

	}//end deleteVenderInfo
}
