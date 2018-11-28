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

	private int count;

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

	// 이건 본사에서 사용할 select문  담당자 : 유주빈
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
			int lastIndex;

			while (result.next()) {
				++count;
			} // 전체 게시글 수량

			result.first();

			lastIndex = count / 10 + 1;
			
			System.out.println(listNum); //10
			System.out.println(count % listNum); //7
					
			
			B_OrderDTO[] orderDTO = new B_OrderDTO[listNum];
			B_OrderDTO[] lastorderDTO = new B_OrderDTO[count % listNum];
			

			if (lastIndex == index) { // 마지막 게시글이 아니면!

				// 반환할 DTO 배열

				for (int i = 0; i < (lastIndex-1) * listNum; i++) {
					
					lastorderDTO[1].setNum(100);
				} // 페이지에 따른 건너뛰기 메서드
				
				int a = result.getInt(1);
				lastorderDTO[1].setNum(10);
				lastorderDTO[1].setNum(a); // 식별자 번호
				
				for (int i = 0; i < 1; i++) {
					System.out.println(count % listNum);
					System.out.println(result.getInt(1));
					System.out.println(result.getInt(1));
					lastorderDTO[i].setId(result.getString(2)); // 아이디
					lastorderDTO[i].setName(result.getString(3)); // 제품명
					lastorderDTO[i].setQuantity(result.getInt(4));// 수량
					lastorderDTO[i].setDate(result.getString(5)); // 발주일
					lastorderDTO[i].sethComfirm(result.getString(6)); // 본사확인
					lastorderDTO[i].setbComfirm(result.getString(7)); // 가맹점확인
					lastorderDTO[i].setAlias(result.getString(8)); // 별칭
					
					
				} // 30개의 게시글의 내용을 담아오는 반복문
				ps.close();
				con.close();
				result.close();
				return lastorderDTO;
			} else {

				for (int i = 0; i < index * listNum; i++) {
					result.next();
				} // 페이지에 따른 건너뛰기 메서드

				for (int i = 0; i < listNum; i++) {
					orderDTO[i].setNum(result.getInt(0)); // 식별자 번호
					orderDTO[i].setId(result.getString(1)); // 아이디
					orderDTO[i].setName(result.getString(2)); // 제품명
					orderDTO[i].setQuantity(result.getInt(3));// 수량
					orderDTO[i].setDate(result.getString(4)); // 발주일
					orderDTO[i].sethComfirm(result.getString(5)); // 본사확인
					orderDTO[i].setbComfirm(result.getString(6)); // 가맹점확인
					orderDTO[i].setAlias(result.getString(7)); // 별칭
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

	// 가맹점에서 사용할 select문  담당자 : 조광재
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

	public void join_execute(String id, String pw, String name, String tel_total) // 로그인 체크
	{
		try {
			System.out.println("0");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("1");
			Connection con = DriverManager.getConnection(url, user, password);
			System.out.println("2");
			sql = "insert into member values (?,?,?,?,?,?)"; // 등급과 포트번호는 아직 구상중,...
			System.out.println("3");
			PreparedStatement ps = con.prepareStatement(sql);
			System.out.println("4");
			ps.setString(1, id);
			System.out.println("5");
			ps.setString(2, pw);
			System.out.println("6");
			ps.setString(3, name);
			System.out.println("7");
			ps.setString(4, tel_total);
			System.out.println("8");
			int rs = ps.executeUpdate();
			System.out.println("9");
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println("회원가입 입력되는 데이터 오류발생!");
			e.printStackTrace();
		}

	}

}// 클래스 끝