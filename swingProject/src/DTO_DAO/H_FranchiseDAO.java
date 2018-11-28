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

	private static B_OrderDAO b_orderdao = new B_OrderDAO();
	// 싱긑톤 패턴

	public static B_OrderDAO getInstance() {
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

//	sql = "select id from member where id =" + id;
//	PreparedStatement ps = con.prepareStatement(sql);

	// 이건 본사에서 사용할 select문 담당자 : 유주빈
	public void select_tel(int index) {
		try {
			connectDB();
			sql = "SELECT alias,tel FROM headFranchise ORDER BY alias DESC;";
			ps = con.prepareStatement(sql);
			ResultSet result = ps.executeQuery();
			
			
			
//			H_FranchiseDTO[] franchiseDTO = H_FranchiseDTO[];
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//select_tel:메서드 끝

}
