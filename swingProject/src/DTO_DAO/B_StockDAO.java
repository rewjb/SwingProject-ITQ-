package DTO_DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class B_StockDAO {

	private String url = "jdbc:mysql://localhost:3306/bbq";
	
	private String user = "root";
	
	private String password = "1234";
	
	private String sql;
	
	private Connection con;
	private PreparedStatement ps;
	
	private static B_SalesDAO b_salesDAO = new B_SalesDAO();
	

	public static B_SalesDAO getInstance() {
		return b_salesDAO;
	} // 싱긑톤 패턴 메서드 : 다른 클래스에서 DAO클래스 메서드의 접근하고 싶으면 getInstance로 밖에 접근 할 수 없다.
	
	public void connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// connectDB:메서드 끝
	
	
	//발주해온 식자재를 재고에 넣는 메서드
	public void insertStock(String id , String name , int quantity) {
		int check = 0;
	
		try {
			connectDB();
			sql = "insert into bodystock values(?,?,?)";
			ps = con.prepareStatement(sql);
			
			ps.setString(0, id);
			ps.setString(1, name);
			ps.setInt(2, quantity);
			
			check = ps.executeUpdate();
			System.out.println("업데이트 체크 : " + check);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}//클래스 끝
