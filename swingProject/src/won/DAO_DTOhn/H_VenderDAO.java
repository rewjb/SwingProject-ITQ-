package DAO_DTO;
/*
 * 2018-11-29 wonHn
 * ~44라인까지의 기본 틀은 메인폴더.DTO_DAO.H_FranchiseDTO를 참고했습니다.
 * 코드테스트 아직 안했습니다..!
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

import com.mysql.jdbc.Statement;

public class H_VenderDAO {
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

	private static H_VenderDAO b_orderdao = new H_VenderDAO();
	// 싱긑톤 패턴

	int count;

	public static H_VenderDAO getInstance() {
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

	public int LastIdex() {
		return count;
	}

	// wonHn
	public void insertVenderInfo() {	//업체정보 입력메서드
		try {

			connectDB();
			//String id = "";
			//String name = "";
			//String tel = "";
			
			//sql = "insert into headvender values ('" + id + "', '" + name + "', '" + tel + "')";
			ps = con.prepareStatement(sql);
			int resultNum = ps.executeUpdate(sql);
			if (resultNum == 1) {
				System.out.println("DB에 회원정보 저장 성공!");
			} else {
				System.out.println("DB에 회원정보 저장 실패!");
			}
			/*
			 * executeUpdate()는 실행에 성공했을 시 1, 실패했을 시 0을 리턴.
			 */

			if (resultNum == 1) {
				System.out.println("DB에 회원정보 저장 성공!");
			} else {
				System.out.println("DB에 회원정보 저장 실패!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	// wonHn
	public void updateVenderInfo() {	//업체정보 수정 메서드		
		connectDB();
		
	}
	
	public void selectVenderInfo() {	//업체정보 수정 및 출력을 위한 메서드
		connectDB();
		
	}

	// wonHn
	public void deleteVenderInfo() {	//업체정보 삭제 메서드
		connectDB();
		
		System.out.println("삭제할 회원의 ID를 입력하세요.");
		System.out.print("> ");
//		String id = "";
		
		String url = "jdbc:mysql://localhost:3306/practice";
		String uid = "jsp"; //"root"
		String upw = "jsp"; //"mysql"
//		String sql = "delete from member where id='" + id + "'";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");//드라이버 로드
			con = DriverManager.getConnection(url, uid, upw);
			ps = con.prepareStatement(sql);
			int resultNum = ps.executeUpdate(sql);
			
			if(resultNum == 1) {
				System.out.println("Delete 성공!");
//				System.out.println("회원 ID: " + id + "이(가) 정상 삭제되었습니다.");
			}else {
				System.out.println("Delete 실패!");				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}
}
