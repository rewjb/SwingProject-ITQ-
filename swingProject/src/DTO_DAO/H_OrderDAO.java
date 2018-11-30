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

	public void connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// connectDB:메서드 끝

	// 이건 본사에서 사용할 insert문 담당자 : 유주빈\H_OrderDTO orderDTO
	public void insert(ArrayList<H_OrderDTO> list) {
		try {
			connectDB();
			sql = "INSERT INTO headOrder VALUES (?,default,?,?,default,'');";
			ps = con.prepareStatement(sql);

			for (int i = 0; i < list.size(); i++) {
				ps.setString(1, list.get(i).getId()); // 업체 아이디
				ps.setString(2, list.get(i).getName()); // 고유번호
				ps.setInt(3,list.get(i).getMoney()); // 제품이름
				ps.executeUpdate();
			}//발주 넣어준 만큼 리스트에 넣기
			
//			=발주 관리=
//					create table headOrder(
//					id varchar(10) not null,
//					num int auto_increment unique,
//					name varchar(10) not null,  
//					money int,
//					date TIMESTAMP DEFAULT NOW(),
//					confirm varchar(10)
//					);

			int listNum = 10;
			// 보여줄 리스트 수량
			count = 0;
			// 데이터 필드 수량

	}// select_UnCheck:메서드 끝

	
}// 클래스 끝
