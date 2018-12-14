package DTO_DAO;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

//가맹점 총 발주내역

public class B_OrderDAO {

	private String url = "jdbc:mysql://localhost:3306/bbq?useUnicode=true&characterEncoding=utf8";
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

	// 이건 본사에서 사용할 select문 담당자 : 유주빈

	// 이건 본사에서 사용할 update문 담당자 : 유주빈
	public void checkUpdate(int uniqueNum) {
		try {
			connectDB();
			sql = "UPDATE bodyorder SET hconfirm = 'ck_1' WHERE num= ? ;";
			ps = con.prepareStatement(sql);
			ps.setInt(1, uniqueNum);
			ps.executeUpdate();

			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println("checkUpdate:가맹점 발주를 확인하는 메서드가 오류입니다.");
		}
	}// checkUpdate:메서드 종료

	public ArrayList<B_OrderDTO> selectAllPlusAlias() {
		ArrayList<B_OrderDTO> orderlist = new ArrayList<>();
		ResultSet rs = null;
		try {
			connectDB();
			sql = "SELECT bodyorder.*,headFranchise.alias FROM bodyorder,headFranchise WHERE (bodyorder.id=headFranchise.id) ORDER BY bodyorder.hconfirm ASC, bodyorder.DATE  DESC;";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				int num = rs.getInt("num");
				String id = rs.getString("id");
				String name = rs.getString("name");
				int quantity = rs.getInt("quantity");
				String date = rs.getString("date");
				String hComfirm = rs.getString("hconfirm");
				String bComfirm = rs.getString("bconfirm");
				String alias = rs.getString("alias");
				B_OrderDTO orderDTO = new B_OrderDTO(num, id, name, quantity, date, hComfirm, bComfirm, alias);
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

	// 가맹점에서 사용할 select문 담당자 : 조광재
	public ArrayList<B_OrderDTO> selectAll(String id) {
		ArrayList<B_OrderDTO> orderlist = new ArrayList<>();
		ResultSet rs = null;

		try {
			connectDB();
			sql = "SELECT * from bodyorder where id = '" + id + "' order by hconfirm;";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				int num = rs.getInt("num");
				id = rs.getString("id");
				String name = rs.getString("name");
				int quantity = rs.getInt("quantity");
				String date = rs.getString("date");
				String hComfirm = rs.getString("hconfirm");
				String bComfirm = rs.getString("bconfirm");
				B_OrderDTO orderDTO = new B_OrderDTO(num, id, name, quantity, date, hComfirm, bComfirm, bComfirm);

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

	public void orderInsert(String id, String name, int quantity) {

		int rn = 0;

		try {
			connectDB();
			sql = "insert into bodyorder value (default,?,?,?,default,'','')";
			ps = con.prepareStatement(sql);

			ps.setString(1, id);
			ps.setString(2, name);
			ps.setInt(3, quantity);

			rn = ps.executeUpdate();
			System.out.println("발주완료" + rn);

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

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}// orderInsert() : 메서드 종료

	// 본사 확인 값이 존재하는 테이블만 가져오는 메서드
	public ArrayList<B_OrderDTO> hCheckSelect(String id) {
		ArrayList<B_OrderDTO> hOrderDTO = new ArrayList<>();
		ResultSet rs = null;
		try {
			connectDB();
			sql = "select * from bodyorder where hconfirm != ''and bconfirm =''and id ='" + id + "'";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {

				int num = rs.getInt("num");
				id = rs.getString("id");
				String name = rs.getString("name");
				int quantity = rs.getInt("quantity");
				String date = rs.getString("date");
				String hConfirm = rs.getString("hconfirm");
				String bConfirm = rs.getString("bconfirm");

				B_OrderDTO order = new B_OrderDTO(num, id, name, quantity, date, hConfirm, bConfirm);

				hOrderDTO.add(order);

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
					ps.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return hOrderDTO;
	}

	// 가맹점 확인이 끝난 테이블만 가져오는 메서드
	public ArrayList<B_OrderDTO> bCheckSelect() {
		ArrayList<B_OrderDTO> bOrderDTO = new ArrayList<>();
		ResultSet rs = null;
		try {
			connectDB();
			sql = "select * from bodyorder where bconfirm = 'bk_1'";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {

				int num = rs.getInt("num");
				String id = rs.getString("id");
				String name = rs.getString("name");
				int quantity = rs.getInt("quantity");
				String date = rs.getString("date");
				String hConfirm = rs.getString("hconfirm");
				String bConfirm = rs.getString("bconfirm");

				B_OrderDTO order = new B_OrderDTO(num, id, name, quantity, date, hConfirm, bConfirm);

				bOrderDTO.add(order);

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
					ps.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return bOrderDTO;
	}

	public void bConfirmUpdate(int num) {

		int rn = 0;
		try {
			connectDB();
			sql = "update bodyorder set bconfirm = 'bk_1' where hconfirm = 'ck_1'and bconfirm = ''and num = '" + num + "'";
			ps = con.prepareStatement(sql);

			rn = ps.executeUpdate();
			System.out.println("가맹점확인 업데이트 체크 : " + rn);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public void orderDelete(int num) {
		int rn = 0;
		try {
			System.out.println("d");
			connectDB();
			sql = "delete from bodyorder where num = " + num + "";
			ps = con.prepareStatement(sql);
			rn = ps.executeUpdate();
			System.out.println("딜리트 확인" + rn);

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
			} catch (Exception e2) {

			}
		}
	}

	public ArrayList<Integer> selectMonthBodyOrder(String year) {
		ArrayList<Integer> list = new ArrayList<>();
		File file = new File("H_VenderpName.txt");
		int sum;
		try {
			Scanner sc = new Scanner(file);
			String[] month = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
			connectDB();
			ResultSet rs = null;

			for (int i = 0; i < month.length; i++) {
				sql = "SELECT name,SUM(quantity) FROM headorder WHERE date LIKE '%" + year + "-" + month[i]
						+ "%' GROUP BY name;";
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				sum = 0;
				while (rs.next()) { // DB 테이블에 값이 존재하는지
					while (sc.hasNextLine()) { // 텍스트 문서에 읽을 내용이 있는지
						if (rs.getString(1).equals(sc.nextLine().split("-")[0])) { // 텍스트 문서와 DB테이블 항목이 일치하는지
							sum += rs.getInt(2) * Integer.parseInt(sc.nextLine().split("-")[1]);
						}
					}
					sc.reset();
				} // for 문 종료
				list.add(sum);
			}

			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println("B_OrderDAO-selectMonthBodyOrder 오류");
		}

		return list;
	}// selectMonth() : 메서드 종료
}// 클래스 끝
