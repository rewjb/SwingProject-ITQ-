package DTO_DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class B_StockDAO {

	private String url = "jdbc:mysql://localhost:3306/bbq";
	
	private String user = "root";
	
	private String password = "1234";
	
	private String sql;
	
	private Connection con;
	private PreparedStatement ps;
	
	private static B_StockDAO b_stockDAO = new B_StockDAO();
	

	public static B_StockDAO getInstance() {
		return b_stockDAO;
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
			sql = "insert into bodystock values(?,?,?,default)";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setInt(3, quantity);
			
			check = ps.executeUpdate();
			System.out.println("재고 등록  체크 : " + check);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}
	
	
	//재고에 있는 물품 확인
	public ArrayList<B_StockDTO> stockSelectAll() {
		ArrayList<B_StockDTO> stockDTO =  new ArrayList<>();
		ResultSet rs = null;
		try {
			connectDB();
			sql = "select * from bodystock";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				int quantity = rs.getInt("quantity");
				
				B_StockDTO dto = new B_StockDTO(id, name, quantity);
				stockDTO.add(dto);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(con!=null) {con.close();}
				if(ps!=null){ps.close();}
				if(rs!=null) {rs.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return stockDTO;
	}
	
	
}//클래스 끝
