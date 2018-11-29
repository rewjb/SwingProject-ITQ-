package DTO_DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class B_SalesDAO {

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
	
	int count;
	
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
	
	public void menuAllSelect() {
		
		ResultSet rs = null;
		
		try {
			connectDB();
			sql = "";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//매출 담는 메서드 
	public void menuInsert(String id, int money , int chickenF , int chickenH, int chickenS , int side) {
		
		int rn = 0;
		
		try {
			connectDB();
			sql = "insert into headOrder values(default,?,default,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, id);
			ps.setInt(2, money);
			ps.setInt(3, chickenF);
			ps.setInt(4, chickenH);
			ps.setInt(5, chickenS);
			ps.setInt(6, side);
			
			rn = ps.executeUpdate();
			System.out.println("insert 체크"+rn);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (ps!=null) {ps.close();}
				if (con!=null) {con.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	
	public void menuUpdate(int num) {
		
	}
	
	
	
	
	
	
	
}//DAO클래스 끝
