package DTO_DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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

	private static B_SalesDAO b_salesDAO = new B_SalesDAO();
	// 싱긑톤 패턴

	public static B_SalesDAO getInstance() {
		return b_salesDAO;
	} // 싱긑톤 패턴 메서드

	public void connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// connectDB:메서드 끝

	public ArrayList<B_SalesDTO> menuAllSelect(String date) {
		ArrayList<B_SalesDTO> salesDTO = new ArrayList<>();
		ResultSet rs = null;

		try {
			connectDB();
			sql = "select * from bodysales where date like '%" + date + "%' order by date desc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				int num = rs.getInt("num");
				String id = rs.getString("id");
				date = rs.getString("date");
				int money = rs.getInt("money");
				int chickenF = rs.getInt("chickenF");
				int chickenH = rs.getInt("chickenH");
				int chickenS = rs.getInt("chickenS");
				int side = rs.getInt("side");

				B_SalesDTO sales = new B_SalesDTO(num, id, date, money, chickenF, chickenH, chickenS, side);
				salesDTO.add(sales);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return salesDTO;
	}

	// 매출 담는 메서드
	public void menuInsert(String id, int money, int chickenF, int chickenH, int chickenS, int side) {

		int rn = 0;

		try {
			connectDB();
			sql = "insert into bodysales values(default,?,default,?,?,?,?,?)";
			ps = con.prepareStatement(sql);

			ps.setString(1, id);
			ps.setInt(2, money);
			ps.setInt(3, chickenF);
			ps.setInt(4, chickenH);
			ps.setInt(5, chickenS);
			ps.setInt(6, side);

			rn = ps.executeUpdate();
			System.out.println("매출등록 체크" + rn);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public void menuUpdate(int num) {

	}

}// DAO클래스 끝
