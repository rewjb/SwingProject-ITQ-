package DTO_DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class H_FranchiseDAO {

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

	private static H_FranchiseDAO h_franchiseDAO = new H_FranchiseDAO();
	// 싱긑톤 패턴

	public static H_FranchiseDAO getInstance() {
		return h_franchiseDAO;
	} // 싱긑톤 패턴 메서드

	public void connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// connectDB:메서드 끝


	// 이건 본사에서 사용할 select문 담당자 : 유주빈
	public ArrayList<H_FranchiseDTO> select_AliasNTel() {
		try {
			connectDB();
			sql = "SELECT * FROM headFranchise ORDER BY alias;";
			ps = con.prepareStatement(sql);
			ResultSet result = ps.executeQuery();

			ArrayList<H_FranchiseDTO> franchiseArray = new ArrayList<H_FranchiseDTO>();
			// 업체 내용의 DTO를 담을 ArrayList 선언

			while (result.next()) { // 1번째 데이터 선택
				franchiseArray.add(new H_FranchiseDTO(null, null, null, result.getString(4), null, result.getString(6),
						result.getString(7)));
//				 전화번호 , 주소 , 가맹점명 넣기
//				 result.getString(1) 아이디
//				 result.getString(2) 비밀번호
//				 result.getString(3) 점장이름
//				 result.getString(4) 전화번호
//				 result.getString(5) 사업자번호 
//				 result.getString(6) 주소
//				 result.getString(7) 가맹점 이름
			}
			result.close();
			con.close();
			ps.close();
			return franchiseArray;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}// select_tel:메서드 끝
	
//	-------------------------------------------------------------------------------------
	
	// wonHn
	//가맹점 정보 입력메서드
	public int insertFranchiseInfo(H_FranchiseDTO fDTO) {		
		connectDB();
		int rs = 0;
		sql = "INSERT INTO headfranchise VALUES( ?,?, ?,?, ?,?, ?);";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, fDTO.getId());
			ps.setString(2, fDTO.getPw());
			ps.setString(3, fDTO.getOwnername());
			ps.setString(4, fDTO.getTel());
			ps.setString(5, fDTO.getComnum());
			ps.setString(6, fDTO.getAddr());
			ps.setString(7, fDTO.getAlias());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rs;
		
	//	sql = "insert into headfranchise values ('"+ id +"','" + pw + "','"+ ownername 
	//				+ "','" + tel + "','" + comnum + "','"+ addr + "','" + alias + "');";					
	}//end insertFranchiseInfo()

	// wonHn
	//가맹점 정보 수정메서드 : 전화번호와 가맹점주 이름만 변경 가능합니다.
	//	*입력값을 어떻게 구분할껀지 정해야함*
	public void updateFranchiseInfo() {		
		connectDB();
	//	sql = "update headfranchise set ownername='" + ownerName + "' where id = '"+ id +"';";	
	//	sql = "update headfranchise set tel='" + tel + "' where id = '"+ id +"';";	
	//	sql = "update headfranchise set ownername='" + ownerName + "' tel='" + tel
	//												+ "' where id = '"+아이디+"';";	
	}//end insertFranchiseInfo()
	
	// wonHn
	//가맹점정보 한줄 출력메서드 
	public void selectFranchiseInfo() {		
		connectDB();
	//	sql = "select * from headfranchise where id'" + id + "';";
	}//end selectFranchiseInfo()
	
	// wonHn
	//가맹점 정보 전체 출력메서드
	public void selectALLFranchiseInfo() {	
		connectDB();
	//	sql = "select * from headfranchise;";
	}//end selectALLFranchiseInfo
	
	// wonHn
	//가맹점 정보 삭제메서드
	public void deleteFranchiseInfo() {		
		connectDB();
	//	sql = "delete from headmember where id='" + id + "';"	
	}//end deleteFranchiseInfo()
	
	
	

}
