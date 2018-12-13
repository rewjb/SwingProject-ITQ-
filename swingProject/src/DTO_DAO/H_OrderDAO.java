package DTO_DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//가맹점 총 발주내역

//DTO는  6개임
//String id; 업체 아이디
//int num; 고유번호
//String name; 제품이름
//int money;
//String date;
//String confirm;

public class H_OrderDAO {

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

	private static H_OrderDAO b_orderdao = new H_OrderDAO();
	// 싱긑톤 패턴

	int count;

	public static H_OrderDAO getInstance() {
		return b_orderdao;
	} // 싱긑톤 패턴 메서드

	private void connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// connectDB:메서드 끝

	// 이건 본사에서 사용할 insert문 담당자 : 유주빈\H_OrderDTO orderDTO
	public void insert(ArrayList<H_OrderDTO> list) {// insert: DB에 발주기록 넣기
		try {
			connectDB();
			sql = "INSERT INTO headorder VALUES (?,default,?,?,?,default,'');";
			// 업체명,발주품명, 가격
			ps = con.prepareStatement(sql);

			for (int i = 0; i < list.size(); i++) {
				ps.setString(1, list.get(i).getVendername()); // 업체명
				ps.setString(2, list.get(i).getName()); // 발주품명
				ps.setInt(3, list.get(i).getQuantity()); // 발주수량
				ps.setInt(4, list.get(i).getMoney()); // 발주가격
				ps.executeUpdate();
			} // 발주 넣어준 만큼 리스트에 넣기
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println("H_OrderDAO-insert 오류");
		}
	}// insert:메서드 끝

	public ArrayList<H_OrderDTO> selectAll() { // selectAll:전부 다 선택하기
		try {
			connectDB();
			sql = "SELECT * FROM headorder ORDER BY  headorder.confirm , headorder.date DESC;";
			ps = con.prepareStatement(sql);
			ResultSet result = ps.executeQuery();

			ArrayList<H_OrderDTO> list = new ArrayList<>();
			H_OrderDTO orderDTO;

			while (result.next()) {
				orderDTO = new H_OrderDTO();
				orderDTO.setVendername(result.getString(1));
				orderDTO.setNum(result.getInt(2));
				orderDTO.setName(result.getString(3));
				orderDTO.setQuantity(result.getInt(4));
				orderDTO.setMoney(result.getInt(5));
				orderDTO.setDate(result.getString(6));
				orderDTO.setConfirm(result.getString(7));
				list.add(orderDTO);
			} // list에 DTO 넣기
			ps.close();
			con.close();
			result.close();
			return list;
		} catch (Exception e) {
			System.out.println("H_OrderDAO-selectAll 오류");
			return null;
		}
	} // selectAll:메서드 종료

	// 발주기록을 지우기!
	public void deleteSelected(int num) {
		try {
			connectDB();
			sql = "DELETE FROM headorder WHERE num=" + num + ";";
			ps = con.prepareStatement(sql);
			ps.executeUpdate();

			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println("H_OrderDAO-deleteSelected 오류");
		}
	}// deleteSelected:메서드 종료

	// 발주기록을 지우기!
	public ArrayList<Integer> selectTotalMonthSalse(String year) {
		ArrayList<Integer> list = new ArrayList<>();
		try {
			String[] month = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
			connectDB();
			ResultSet rs=null;

			for (int i = 0; i < month.length; i++) {
				sql = "SELECT SUM(money) FROM headorder WHERE date LIKE '%" +year+"-"+month[i]+"%';";
				ps = con.prepareStatement(sql);
				rs=ps.executeQuery();
				if (rs.next()) {
					list.add(rs.getInt(1));
				}else {
					list.add(0);
				}
			}
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println("H_OrderDAO-selectTotalMonthSalse 오류");
		}
		
		return list;
	}// selectTotalMonthSalse:메서드 종료

}// 클래스 끝
