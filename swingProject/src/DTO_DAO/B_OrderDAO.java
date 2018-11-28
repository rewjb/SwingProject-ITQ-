package DTO_DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//가맹점 총 발주내역

public class B_OrderDAO {

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

//	sql = "select id from member where id =" + id;
//	PreparedStatement ps = con.prepareStatement(sql);

	// 이건 본사에서 사용할 select문 담당자 : 유주빈
	public B_OrderDTO[] select_UnCheck(int index) {
		try {
			connectDB();
			sql = "SELECT bodyorder.*,headmember.alias FROM bodyorder,headmember WHERE (bodyorder.id=headmember.id) and (hconfirm='') ORDER BY DATE DESC;";
			ps = con.prepareStatement(sql);
			ResultSet result = ps.executeQuery();

			int listNum = 10;
			// 보여줄 리스트 수량
			count = 0;
			// 데이터 필드 수량
			
			

			while (result.next()) {
				++count;
			} // 전체 게시글 수량

			result.first();

			int lastIndex = count / 10 + 1;

			B_OrderDTO[] orderDTO = new B_OrderDTO[listNum];

			for (int i = 0; i < orderDTO.length; i++) {
				orderDTO[i] = new B_OrderDTO();
			}

			B_OrderDTO[] lastorderDTO = new B_OrderDTO[count % listNum];

			for (int i = 0; i < lastorderDTO.length; i++) {
				lastorderDTO[i] = new B_OrderDTO();
			}

			if (lastIndex == index) { // 마지막 게시글이 아니면!

				// 반환할 DTO 배열

				for (int i = 0; i < (lastIndex - 1) * listNum; i++) {
					result.next();
				} // 페이지에 따른 건너뛰기 메서드

				for (int i = 0; i < count % listNum; i++) {
					lastorderDTO[i].setNum(result.getInt(1));
					lastorderDTO[i].setId(result.getString(2)); // 아이디
					lastorderDTO[i].setName(result.getString(3)); // 제품명
					lastorderDTO[i].setQuantity(result.getInt(4));// 수량
					lastorderDTO[i].setDate(result.getString(5)); // 발주일
					lastorderDTO[i].sethComfirm(result.getString(6)); // 본사확인
					lastorderDTO[i].setbComfirm(result.getString(7)); // 가맹점확인
					lastorderDTO[i].setAlias(result.getString(8)); // 별칭
					result.next();
				} // 30개의 게시글의 내용을 담아오는 반복문
				ps.close();
				con.close();
				result.close();
				return lastorderDTO;
			} else { // 마지막 페이지가 아닐 경우!
				
				System.out.println(result.getInt(1));

				for (int i = 0; i < (index-1) * listNum; i++) {
					result.next();
				} // 페이지에 따른 건너뛰기 메서드


				for (int i = 0; i < listNum ; i++) {
					orderDTO[i].setNum(result.getInt(1)); // 식별자 번호
					orderDTO[i].setId(result.getString(2)); // 아이디
					orderDTO[i].setName(result.getString(3)); // 제품명
					orderDTO[i].setQuantity(result.getInt(4));// 수량
					orderDTO[i].setDate(result.getString(5)); // 발주일
					orderDTO[i].sethComfirm(result.getString(6)); // 본사확인
					orderDTO[i].setbComfirm(result.getString(7)); // 가맹점확인
					orderDTO[i].setAlias(result.getString(8)); // 별칭
					result.next();
				} // 30개의 게시글의 내용을 담아오는 반복문
				ps.close();
				con.close();
				result.close();
				return orderDTO;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("유주빈: 개망점에서 발주 내용갖고 오는거 오류");
			return null;
		}
	}

	// 가맹점에서 사용할 select문 담당자 : 조광재
	public ArrayList<B_OrderDTO> selectAll() {
		ArrayList<B_OrderDTO> orderlist = new ArrayList<>();
		ResultSet rs = null;

		try {
			connectDB();
			sql = "SELECT * from bodyorder";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				int num = rs.getInt("num");
				String id = rs.getString("id");
				String name = rs.getString("name");
				int quantity = rs.getInt("quantity");
				String date = rs.getString("date");
				String hComfirm = rs.getString("hcomfirm");
				String bComfirm = rs.getString("bcomfirm");

				B_OrderDTO orderDTO = new B_OrderDTO(num, id, name, quantity, date, hComfirm, bComfirm);

				orderlist.add(orderDTO);

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
		return orderlist;
	}

	public int LastIdex() {
		return count;
	}

}// 클래스 끝
