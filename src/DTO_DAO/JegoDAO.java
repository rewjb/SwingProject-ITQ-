package DTO_DAO;

public class JegoDAO {

	private String url ="";
	private String uid = "";
	private String upw = "";
	
	// 싱글톤 패턴 클래스 생성.
	// 1. 자신의 클래스 내부에 스스로의 객체를 생성함.
	private static JegoDAO dao = new JegoDAO();
	
	// 2. 외부에서 객체를 생성할 수 없도록 생성자의 private제한을 붙임.
	private JegoDAO() {
		try {
			Class.forName("");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		//3.외부에서 객체생성을 요구할 시 공개된 메서드를 통해 단 1개의 객체를 제공함.
		public static JegoDAO getInstance() {
			if(dao != null) {
				dao = new JegoDAO();
			}
			return dao;
		}
	
	
	
		
		//
		
		
		
		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
