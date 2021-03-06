package DTO_DAO;

/*
 * 2018-11-29 wonHn
 * ~44라인까지의 기본 틀은 [메인폴더.DTO_DAO.H_FranchiseDTO]를 참고했습니다.
 * 
 * 2018-12-17 wonHn
 * 구현 및 주석 수정까지 완료 하였습니다.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	// wHn
	// 재료정보 입력메서드
	public int insertVenderpInfo(H_VenderpDTO pDTO) {
		connectDB();
		int rs = 0;
		sql = "INSERT INTO headvenderp VALUES (?, NULL, ?, ?);";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, pDTO.getId());
			ps.setString(2, pDTO.getName());
			ps.setInt(3, pDTO.getMoney());
			rs = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("insertVenderpInfo() 오류");
			e.printStackTrace();
		}
		return rs;
	}// end insertVenderpInfo()

	// wonHn
	// 재료정보 수정 메서드 : 재료 이름과 가격만 수정 가능합니다.
	public int updateVenderpInfo(H_VenderpDTO pDTO) {
		connectDB();
		int rs = 0;
		sql = "UPDATE headvenderp SET name = ? , money = ? WHERE num = ?;";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, pDTO.getName());
			ps.setInt(2, pDTO.getMoney());
			ps.setInt(3, pDTO.getNum());

			rs = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("updateVenderpInfo() 오류");
			e.printStackTrace();
		}
		return rs;
	}// end updateVenderpInfo()

	// wonHn
	// 재료정보 전체 출력메서드
	public ArrayList<H_VenderpDTO> selectALLVenderpInfo() {
		ArrayList<H_VenderpDTO> list = null;
		try {
			connectDB();
			sql = "SELECT * FROM headvenderp;";
			ps = con.prepareStatement(sql);

			list = new ArrayList<>();
			H_VenderpDTO pDTO;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pDTO = new H_VenderpDTO();
				pDTO.setId(rs.getString(1));
				pDTO.setNum(rs.getInt(2));
				pDTO.setName(rs.getString(3));
				pDTO.setMoney(rs.getInt(4));

				list.add(pDTO);
			} // end while
		} catch (Exception e) {
			System.out.println("selectALLVenderpInfo() 오류");
			e.printStackTrace();
		} finally {

		} // end try catch
		return list;
	}// end selectALLVenderpInfo

	// wonHn
	// 재료정보 삭제 메서드
	public int deleteVenderpInfo(int num) {
		connectDB();
		int rs = 0;
		sql = "DELETE FROM headvenderp WHERE num = ?;";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);

			rs = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("deleteVenderpInfo() 오류");
			e.printStackTrace();
		}

		return rs;
	}// end deleteVenderpInfo

	// wonHn
	// 재료정보 삭제 메서드 - 재료명으로 삭제
	public int deleteVenderpInfo(String name) {
		connectDB();
		int rs = 0;
		sql = "DELETE FROM headvenderp WHERE name = ?;";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, name);

			rs = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("deleteVenderpInfo() 오류");
			e.printStackTrace();
		}

		return rs;
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
			// 업체정보를 담을 list
			String[] insertList;

			while (result.next()) {
				insertList = new String[4];
				insertList[0] = result.getString(1);
				insertList[1] = result.getString(2);
				insertList[2] = result.getString(3);
				insertList[3] = result.getString(4);
				list.add(insertList);
			} // 값을 list에 넣기

			con.close();
			ps.close();
			result.close();

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}// select_vender:메서드 종료

	public ArrayList<String> select_vender(String temp) {
		// 제품군에 맞은 업체를 갖고오는 메서드
		// 이거 나중에 한나씨가 만들면 이거 없애야 함 ..
		// 이거 기능은 재고에서 입출고 입력할때 업체 콤보박스에 들어가는 string을 반환함

		try {
			connectDB();
			sql = "SELECT headvenderp.name,headvender.name FROM headvenderp,headvender WHERE (headvender.id=headvenderp.id) and headvenderp.name=?  ORDER BY headvender.name;";
			ps = con.prepareStatement(sql);
			ps.setString(1, temp);
			ResultSet result = ps.executeQuery();
			// 기본셋팅 : sql문 문자열 , DB연결 객체의 statement를 받을 ps

			ArrayList<String> list = new ArrayList<>();
			// 업체정보를 담을 list
			String insertList;

			while (result.next()) {
				insertList = result.getString(2);
				list.add(insertList);
			} // 값을 list에 넣기

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