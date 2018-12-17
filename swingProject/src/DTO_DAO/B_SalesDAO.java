package DTO_DAO;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

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

	public ArrayList<B_SalesDTO> menuAllSelect(String id, String date) {
		ArrayList<B_SalesDTO> salesDTO = new ArrayList<>();
		ResultSet rs = null;

		try {
			connectDB();
			sql = "select * from bodysales where id = '" + id + "' and date like '%" + date + "%' order by date desc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				int num = rs.getInt("num");
				id = rs.getString("id");
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

	public ArrayList<Integer> selectFranSalesYear(String id, String year) {
		ArrayList<Integer> salesMonth = new ArrayList<>();
		String date;
		try {
			connectDB();
			ResultSet result = null;
			for (int i = 0; i < 9; i++) {
				date = year + "-0" + (1 + i);
				sql = "select id,SUM(money) from bodysales where id=? and date like '%" + date + "%' GROUP BY id;";
				ps = con.prepareStatement(sql);
				ps.setString(1, id);
				result = ps.executeQuery();
				if (result.next()) {
					salesMonth.add(result.getInt(2));
				} else {
					salesMonth.add(0);
				}
			}

			for (int i = 0; i < 3; i++) {
				date = year + "-" + String.valueOf(10 + i);
				sql = "select id,SUM(money) from bodysales where id=? and date like '%" + date + "%' GROUP BY id;";
				ps = con.prepareStatement(sql);
				ps.setString(1, id);
				result = ps.executeQuery();
				if (result.next()) {
					salesMonth.add(result.getInt(2));
				} else {
					salesMonth.add(0);
				}
			}

			con.close();
			ps.close();
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("B_SalesDAO-selectFranSalesMonth() 에러");
		}
		return salesMonth;
	} // selectFranSalesMonth : 메서드 끝

	public ArrayList<Integer> selectFranSalesMonth(String id, String year, String month) {
		ArrayList<Integer> salesDay = new ArrayList<>();
		String date;
		try {
			connectDB();
			int[] days = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
			int month2 = Integer.parseInt(month);
			ResultSet result = null;
			for (int i = 0; i < days[month2 - 1]; i++) {
				if (String.valueOf(i + 1).length() == 1) {
					if (month.length() == 1) {
						month = "0" + month;
					}
					date = year + "-" + month + "-" + "0" + (i + 1);
					sql = "SELECT id,SUM(money) from bodysales where id=? and date like '%" + date + "%' GROUP BY id;";
					ps = con.prepareStatement(sql);
					ps.setString(1, id);
					result = ps.executeQuery();
					if (result.next()) {
						salesDay.add(result.getInt(2));
					} else {
						salesDay.add(0);
					}
				} else {
					if (month.length() == 1) {
						month = "0" + month;
					}
					date = year + "-" + month + "-" + (i + 1);
					sql = "SELECT id,SUM(money) from bodysales where id=? and date like '%" + date + "%' GROUP BY id;";
					ps = con.prepareStatement(sql);
					ps.setString(1, id);
					result = ps.executeQuery();
					if (result.next()) {
						salesDay.add(result.getInt(2));
					} else {
						salesDay.add(0);
					}
				}
			}
			con.close();
			ps.close();
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("B_SalesDAO-selectFranSalesMonth() 내부 에러");
		}
		return salesDay;
	} // selectFranSalesMonth : 메서드 끝

	public ArrayList<Integer> selectFranSalePie(String id) {
		ArrayList<Integer> salesList = new ArrayList<>();
		// 메뉴에 따른 총 매출 리스트
		String[] menu = new String[] { "chickenF", "chickenH", "chickenS" };
		// 치킨 메뉴 배열

		try {
			connectDB();
			ResultSet result = null;

			sql = "SELECT id,SUM(chickenF) from bodysales where id=? GROUP BY id;";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			result = ps.executeQuery();
			if (result.next()) {
				salesList.add(result.getInt(2));
			} else {
				salesList.add(0);
			}

			sql = "SELECT id,SUM(chickenH) from bodysales where id=? GROUP BY id;";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			result = ps.executeQuery();
			if (result.next()) {
				salesList.add(result.getInt(2));
			} else {
				salesList.add(0);
			}

			sql = "SELECT id,SUM(chickenS) from bodysales where id=? GROUP BY id;";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			result = ps.executeQuery();
			if (result.next()) {
				salesList.add(result.getInt(2));
			} else {
				salesList.add(0);
			}

			con.close();
			ps.close();
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return salesList;
	} // selectFranSalesMonth : 메서드 끝

	public ArrayList<Integer> selectMonthBodySales(String year) {// 마지막 작업중
		ArrayList<Integer> list = new ArrayList<>();
		int sum;
		try {
			String[] month = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
			connectDB();
			ResultSet rs = null;
			int total;
			for (int i = 0; i < month.length; i++) {
				sql = "SELECT SUM(money) FROM bodysales WHERE date LIKE '%" + year + "-" + month[i] + "%';";
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				total = 0;
				
				while (rs.next()) {
					total += rs.getInt(1);
				}
				list.add(total);
			}

			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println("B_SalesDAO-selectMonthBodySales 오류");
			e.printStackTrace();
		}

		return list;
	}// selectMonth() : 메서드 종료

}// DAO클래스 끝
