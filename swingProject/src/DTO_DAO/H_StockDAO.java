package DTO_DAO;

/*
 * ~44라인까지의 기본 틀은 메인폴더.DTO_DAO.H_FranchiseDTO를 참고했습니다.
 * 코드테스트 아직 안했습니다..!
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import com.mysql.jdbc.Statement;

public class H_StockDAO {
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

	private static H_StockDAO h_stockDAO = new H_StockDAO();
	// 싱긑톤 패턴

	public static H_StockDAO getInstance() {
		return h_stockDAO ;
	} // 싱긑톤 패턴 메서드

	public void connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// connectDB:메서드 끝

	public ArrayList<H_VenderDTO> selectALLVenderInfo() {
		try {
			connectDB();
			sql = "SELECT * FROM headstock ORDER BY date DESC;";
			ps = con.prepareStatement(sql);
			ResultSet result = ps.executeQuery();

			ArrayList<H_VenderDTO> list = new ArrayList<>();
			H_VenderDTO venderDTO;

			while (result.next()) {
				venderDTO = new H_VenderDTO();
				venderDTO.setId(result.getString(1));
				venderDTO.setName(result.getString(2));
				venderDTO.setTel(result.getString(3));
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
	public void deleteVenderInfo() {
		connectDB();
		// sql = "delete from headmember where id='" + id + "';"

	}// end deleteVenderInfo

}// 클래스 종료
