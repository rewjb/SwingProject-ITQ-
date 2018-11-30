package DTO_DAO;

public class B_SalesDTO {

	private int num;//일일 매출당 고유번호
	private String id;//가맹점 아이디
	private	String date;//날짜
	private int money;//매출 총액
	private	int chickenF;//후라이드 매출
	private int chickenH;//양념 매출
	private int chickenS;//간장 매출
	private int side;// 사이드 메뉴 매출
	
	public B_SalesDTO(int num, String id, String date, int money, int chickenF, int chickenH, int chickenS, int side) {
		super();
		this.num = num;
		this.id = id;
		this.date = date;
		this.money = money;
		this.chickenF = chickenF;
		this.chickenH = chickenH;
		this.chickenS = chickenS;
		this.side = side;
		
	}
	
	public B_SalesDTO() {//기본 생성자
		System.out.println("test");
	}
	
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getChickenF() {
		return chickenF;
	}
	public void setChickenF(int chickenF) {
		this.chickenF = chickenF;
	}
	public int getChickenH() {
		return chickenH;
	}
	public void setChickenH(int chickenH) {
		this.chickenH = chickenH;
	}
	public int getChickenS() {
		return chickenS;
	}
	public void setChickenS(int chickenS) {
		this.chickenS = chickenS;
	}
	public int getSide() {
		return side;
	}
	public void setSide(int side) {
		this.side = side;
	}

	
	
}//클래스 끝
