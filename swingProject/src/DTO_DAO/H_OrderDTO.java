package DTO_DAO;

//가맹점 총 발주내역

public class H_OrderDTO {
	
	String id;
	int num;
	String name;
	int money;
	String date;
	String confirm;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	
	public H_OrderDTO(String id, int num, String name, int money, String date, String confirm) {
		super();
		this.id = id;
		this.num = num;
		this.name = name;
		this.money = money;
		this.date = date;
		this.confirm = confirm;
	} 
	
	public H_OrderDTO() {
		// TODO Auto-generated constructor stub
	}
	
	
}// 클래스 끝
