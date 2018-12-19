package DTO_DAO;

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
		return h_stockDAO;
	} // 싱긑톤 패턴 메서드

	public void connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// connectDB:메서드 끝

	public int insert(H_StockDTO stockDTO) {
		try {
			connectDB();
			int tr = -1;
			sql = "insert into headstock values(?,?,?,?,?,default);";
			ps = con.prepareStatement(sql);
			ps.setString(1, stockDTO.getPoint());
			ps.setString(3, stockDTO.getName());
			ps.setString(5, stockDTO.getPlace());
			if (stockDTO.getIo().equals("입고")) {
				ps.setString(2, "in");
				ps.setInt(4, +stockDTO.getQuantity());
			} else {
				ps.setString(2, "out");
				ps.setInt(4, -stockDTO.getQuantity());
			}
			tr = ps.executeUpdate();
			con.close();
			ps.close();
			return tr;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("H_StockDAO-insert()");
			return 0;
		}
	} // insert:메서드 종료

	public ArrayList<H_StockDTO> selectPointAll(String point) {
		try {
			connectDB();
			sql = "SELECT name,SUM(quantity) AS quantity  FROM headstock  WHERE point=?  GROUP BY name;";
			ps = con.prepareStatement(sql);
			ps.setString(1, point);
			ResultSet result = ps.executeQuery();

			ArrayList<H_StockDTO> list = new ArrayList<>();
			H_StockDTO stockDTO;

			while (result.next()) {
				stockDTO = new H_StockDTO();
				stockDTO.setName(result.getString("name"));
				stockDTO.setQuantity(result.getInt("quantity"));
				list.add(stockDTO);
			}
			con.close();
			ps.close();
			result.close();
			return list;
		} catch (Exception e) {
			System.out.println("H_StockDAO-selectPointAll");
			return null;
		}
	}// selectPointAll:메서드 끝
	
	public ArrayList<H_StockDTO> selectInStockHistory() {
		try {
			connectDB();
			sql = "SELECT *  FROM headstock where IO='in' ORDER BY date ;";
			ps = con.prepareStatement(sql);
			ResultSet result = ps.executeQuery();

			ArrayList<H_StockDTO> list = new ArrayList<>();
			H_StockDTO stockDTO;

			while (result.next()) {
				stockDTO = new H_StockDTO();
				
				stockDTO.setPoint(result.getString("point"));
				stockDTO.setIo(result.getString("IO"));
				stockDTO.setName(result.getString("name"));
				stockDTO.setQuantity(result.getInt("quantity"));
				stockDTO.setPlace(result.getString("place"));
				stockDTO.setDate(result.getString("date"));
				
				list.add(stockDTO);
			}

			con.close();
			ps.close();
			result.close();
			return list;
		} catch (Exception e) {
			System.out.println("H_StockDAO-selectInStockHistory");
			return null;
		}
	}// selectInStockHistory:메서드 끝
	
	public ArrayList<H_StockDTO> selectOutStockHistory() {
		try {
			connectDB();
			sql = "SELECT * FROM headstock where IO='out' ORDER BY date ;";
			ps = con.prepareStatement(sql);
			ResultSet result = ps.executeQuery();

			ArrayList<H_StockDTO> list = new ArrayList<>();
			H_StockDTO stockDTO;

			while (result.next()) {
				stockDTO = new H_StockDTO();
				
				stockDTO.setPoint(result.getString("point"));
				stockDTO.setIo(result.getString("IO"));
				stockDTO.setName(result.getString("name"));
				stockDTO.setQuantity(result.getInt("quantity"));
				stockDTO.setPlace(result.getString("place"));
				stockDTO.setDate(result.getString("date"));
				
				list.add(stockDTO);
			}

			con.close();
			ps.close();
			result.close();
			return list;
		} catch (Exception e) {
			System.out.println("H_StockDAO-selectOutStockHistory");
			return null;
		}
	}// selectOutStockHistory:메서드 끝
	
	public ArrayList<H_StockDTO> selectTotalStock() {
		try {
			connectDB();
			sql = "SELECT name,SUM(quantity) AS quantity FROM headstock GROUP BY name;";
			ps = con.prepareStatement(sql);
			ResultSet result = ps.executeQuery();

			ArrayList<H_StockDTO> list = new ArrayList<>();
			H_StockDTO stockDTO;

			while (result.next()) {
				stockDTO = new H_StockDTO();
				stockDTO.setName(result.getString("name"));
				stockDTO.setQuantity(result.getInt("quantity"));
				list.add(stockDTO);
			}

			con.close();
			ps.close();
			result.close();
			return list;
		} catch (Exception e) {
			System.out.println("H_StockDAO-selectPointAll");
			return null;
		}
	}// selectInStockHistory:메서드 끝
	
	

}// 클래스 종료
