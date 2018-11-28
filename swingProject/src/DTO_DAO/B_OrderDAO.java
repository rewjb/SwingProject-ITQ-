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
	
	public B_OrderDAO getInstance() {
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
	public void select_UnCheck() {
		try {
			connectDB();
			sql = "SELECT bodyorder.*,headmember.alias FROM bodyorder,headmember WHERE bodyorder.id=headmember.id ORDER BY DATE DESC;"; 
			ps= con.prepareStatement(sql);
			ResultSet result = ps.executeQuery();
			result.
			
			
			ps.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
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
