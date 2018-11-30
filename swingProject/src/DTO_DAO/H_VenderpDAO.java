package DTO_DAO;

/*
 * 2018-11-29 wonHn
 * ~44라인까지의 기본 틀은 [메인폴더.DTO_DAO.H_FranchiseDTO]를 참고했습니다.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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

	private static H_VenderpDAO h_venderpDAO = new H_VenderpDAO();
	// 싱긑톤 패턴

	public static H_VenderpDAO getInstance() {
		return h_venderpDAO;
	} // 싱긑톤 패턴 메서드

	public void connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// connectDB:메서드 끝

	// wonHn
	// 재료정보 입력메서드
	// *자동카운트 되는 경우 입력을 어떻게 해줘야 하는지 확인해야됨.*
	// *UI에서 int값으로 받아올 수 있게 해놔야함. DB에서 int값인경우 명령어에 작은따옴표 쓰는지 확인해야함*
	public void insertVenderpInfo() {
		connectDB();
		// sql = "insert into headvenderp values('"+ id +"','자동카운트','" + name + "','" +
		// money + "');"
	}// end insertVenderpInfo()

	// wonHn
	// 재료정보 수정 메서드 : 가격만 수정 가능합니다.
	public void updateVenderpInfo() {
		connectDB();
		// sql = "update headvenderp set money = '" + 가격 + "' where num = '"+번호+"';"
	}// end updateVenderpInfo()

	// wonHn
	// 재료정보 한줄 출력메서드 : 클릭으로 받아지는 인덱스값에 해당하는 num값을 넘겨줄 예정입니다.
	public void selectVenderInfo() {
		connectDB();
		// sql = "select * from headvenderp where num = '" + num + "';"
	}// end selectVenderInfo

	// wonHn
	// 재료정보 전체 출력메서드 : 처음부터 마지막 인덱스 값까지 한줄 출력메서드를 돌려줄 예정입니다.
	public void selectALLVenderpInfo() {
		connectDB();
		// sql = "select * from headvenderp;"
	}// end selectALLVenderpInfo

	// wonHn
	// 재료정보 삭제 메서드
	public void deleteVenderpInfo() {
		connectDB();
		// sql = "delete from headmember where num='" + num + "';"
	}// end deleteVenderpInfo

//	유주빈 구분선-----------------------------------------------------------------------------------------------------

	public ArrayList<String> select_product() {// select_product:메서드 시작
		// 업체의 제품의 중복없이 목록을 갖고오는 메서드
		try {
			connectDB();
			sql = "SELECT DISTINCT name FROM headvenderp ORDER BY name;";
			ps = con.prepareStatement(sql);
			ResultSet result = ps.executeQuery();
			ArrayList<String> list = new ArrayList<>();

			// ↓ : list에 값을 넣는 작업
			while (result.next()) {
				list.add(result.getString(1));
			} // while문 종료
			
			con.close();
			ps.close();
			result.close();
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("H_VenderpDAO-select_product() 오류");
			return null;
		}
	}// select_product:메서드 종료
	
	public ArrayList<String[]> select_vender() {
		// 제품군에 맞은 업체를 갖고오는 메서드

		try {
			connectDB();
			sql = "SELECT headvenderp.num,headvenderp.name,headvender.name,headvenderp.money FROM headvenderp,headvender WHERE (headvender.id=headvenderp.id) ORDER BY headvender.name;";
			ps = con.prepareStatement(sql);
			ResultSet result = ps.executeQuery();
			// 기본셋팅 : sql문 문자열 , DB연결 객체의 statement를 받을 ps
			
			ArrayList<String[]> list = new ArrayList<>();
			//업체정보를 담을 list
			String[] insertList;
			
			while (result.next()) {
				insertList = new String[4];
				insertList[0]=result.getString(1);
				insertList[1]=result.getString(2);
				insertList[2]=result.getString(3);
				insertList[3]=result.getString(4);
				list.add(insertList);
			}//값을 list에 넣기
			
			con.close();
			ps.close();
			result.close();
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}// select_vender:메서드 종료
	
	
	
	
	

}// end class