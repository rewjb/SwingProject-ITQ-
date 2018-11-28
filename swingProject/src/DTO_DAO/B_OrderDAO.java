package DTO_DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.transform.Result;

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

	//이건 본사에서 사용할 select문
	public B_OrderDTO[] select_UnCheck(int index) {
		try {
			connectDB();
			sql = "SELECT bodyorder.*,headmember.alias FROM bodyorder,headmember WHERE (bodyorder.id=headmember.id) and (hconfirm='') ORDER BY DATE DESC;;"; 
			ps= con.prepareStatement(sql);
			ResultSet result = ps.executeQuery();
			
			B_OrderDTO[] orderDTO = new B_OrderDTO[10];
			
			for (int i = 0; i < index*10; i++) {
				result.next();
			} //페이지에 따른 건너뛰기 메서드
			
			for (int i = 0; i <10; i++) {
				orderDTO[i].setNum(result.getInt(0)); // 식별자 번호
				orderDTO[i].setId(result.getString(1)); // 아이디
				orderDTO[i].setName(result.getString(2)); // 제품명
				orderDTO[i].setQuantity(result.getInt(3));// 수량
				orderDTO[i].setDate(result.getString(4)); // 발주일 
				orderDTO[i].sethComfirm(result.getString(5)); // 본사확인
				orderDTO[i].setbComfirm(result.getString(6)); // 가맹점확인
				orderDTO[i].setAlias(result.getString(7)); // 별칭
			}// 30개의 게시글의 내용을 담아오는 반복문
			ps.close();
			con.close();
			
			return orderDTO; 
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("유주빈: 개망점에서 발주 내용갖고 오는거 오류");
			return null;
		}
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
