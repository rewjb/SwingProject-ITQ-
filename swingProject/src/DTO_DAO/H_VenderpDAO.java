package DAO_DTO;
/*
 * 2018-11-29 wonHn
 * ~44라인까지의 기본 틀은 메인폴더.DTO_DAO.H_FranchiseDTO를 참고했습니다.
 * 코드테스트 아직 안했습니다..!
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class H_VenderpDAO {
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

	private static H_VenderpDAO b_orderdao = new H_VenderpDAO();
	// 싱긑톤 패턴

	int count;

	public static H_VenderpDAO getInstance() {
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
	//재료정보 입력메서드 : 자동카운트 되는 경우 입력을 어떻게 해줘야 하는지 확인해야됨.
	public void insertVenderpInfo() {	
		connectDB();
	//	sql = "insert into headvenderp values('"+ id +"','고유번호 자동카운트','" + name + "','" + money + "');"
	}//end insertVenderpInfo()

	// wonHn
	//재료정보 수정 메서드
	public void updateVenderpInfo() {			
		connectDB();
	//	sql = "update headvenderp set money='" + 가격 + " where num = '"+번호+"';"
		
	}//end updateVenderpInfo()
	
	// wonHn
	//재료정보 수정 및 삭제를 위한 출력메서드
	public void selectVenderpInfo() {	
		connectDB();
	
	}//end selectVenderpInfo

	// wonHn
	//재료정보 전체 출력메서드
	public void selectALLVenderpInfo() {	
		connectDB();
		
	}//end selectALLVenderpInfo

	// wonHn
	//재료정보 삭제 메서드
	public void deleteVenderpInfo() {	
		connectDB();
		
	}//end deleteVenderpInfo
}