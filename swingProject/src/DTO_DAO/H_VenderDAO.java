package DTO_DAO;

/*
 * 2018-11-29 wonHn
 * ~44라인까지의 기본 틀은 메인폴더.DTO_DAO.H_FranchiseDTO를 참고했습니다.
 * 코드테스트 아직 안했습니다..!
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


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

	private static H_VenderDAO h_venderDAO = new H_VenderDAO();
	// 싱긑톤 패턴

	public static H_VenderDAO getInstance() {
		return h_venderDAO;
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
	// 업체정보 입력메서드
	public int insertVenderInfo(H_VenderDTO vDTO) {
		connectDB();
		int rs = 0;
		sql = "INSERT INTO headvender VALUES(?,?,?,?);";
		try {
			// 3. SQL문 객체화
			ps = con.prepareStatement(sql);
			ps.setString(1, vDTO.getId());
			ps.setString(2, vDTO.getName());
			ps.setString(3, vDTO.getTel());
			ps.setString(4, vDTO.getComNum());
			// 4. SQL문 실행 요청
			rs = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("insertVenderInfo() 오류");
			e.printStackTrace();
		}
		return rs;
	}// end insertVenderInfo()

	// wonHn
	// 업체정보 수정 메서드 : 전화번호만 수정할예정입니다.
	public int updateVenderInfo(H_VenderDTO vDTO) {
		connectDB();
		int rs = 0;
		sql = "UPDATE headvender SET tel = ? WHERE id = ?;";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, vDTO.getTel());
			ps.setString(2, vDTO.getId());
			
			rs = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("updateVenderInfo() 오류");
			e.printStackTrace();
		}
		return rs;
	}// end updateVenderInfo()

	// wonHn
	// 업체정보 한줄 출력메서드 : 클릭으로 받아지는 인덱스값에 해당하는 id값을 넘겨줄 예정입니다.
	public H_VenderDTO selectVenderInfo(String column, String input) {
		connectDB();
		if(column.equals("id")) {
			sql = "select * from headvender where id=?;";
		}else if(column.equals("name")) {
			sql = "select * from headvender where name=?;";
		}
		H_VenderDTO vDTO = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, input);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				vDTO = new H_VenderDTO();
				vDTO.setId(rs.getString(1));
				vDTO.setName(rs.getString(2));
				vDTO.setTel(rs.getString(3));
				vDTO.setComNum(rs.getString(4));
			}
		} catch (Exception e) {
			System.out.println("selectVenderInfo() 오류");
			e.printStackTrace();
		}
		return vDTO;
	}// end selectVenderInfo

	// 이름을 오름차순으로 모든 벤더정보를 갖고오는 메서드입니다.
	// 이거 한나씨깨서 만드신 메서드인데 안에 제가 내용 채웠어요~
	// 반환 타입이 H_VenderDTO를 담고 있는 ArrayList 타입입니다~
	// 수정일 : 2018-12-03
	public ArrayList<H_VenderDTO> selectALLVenderInfo() {
		try {
			connectDB();
			sql = "SELECT * FROM  headvender  ORDER BY  name;";
			ps = con.prepareStatement(sql);
			ResultSet result = ps.executeQuery();

			ArrayList<H_VenderDTO> list = new ArrayList<>();
			H_VenderDTO venderDTO;

			while (result.next()) {
				venderDTO = new H_VenderDTO();
				venderDTO.setId(result.getString(1));
				venderDTO.setName(result.getString(2));
				venderDTO.setTel(result.getString(4));
				venderDTO.setComNum(result.getString(3));
				list.add(venderDTO);
			} // list에 DTO 넣기

			con.close();
			ps.close();
			result.close();
			return list;
		} catch (Exception e) {
			System.out.println("H_VenderDAO-");
			return null;
		}
	}// end selectALLVenderInfo

	// wonHn
	// 업체정보 삭제 메서드
	public int deleteVenderInfo(String id) {
		connectDB();
		int rs = 0;
		sql = "DELETE FROM headvender WHERE id = ?;";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("deleteVenderInfo() 오류");
			e.printStackTrace();
		}
		return rs;
	}// end deleteVenderInfo

}// 클래스 종료
