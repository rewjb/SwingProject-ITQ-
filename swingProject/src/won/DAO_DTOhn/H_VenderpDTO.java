package DAO_DTO;

public class H_VenderpDTO {
	private String id;
	private int num;
	private String name;
	private int money;
	
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
	
	//default생성자
	public H_VenderpDTO() {
		
	}

	public H_VenderpDTO(String id, int num, String name, int money) {
		super();
		this.id = id;
		this.num = num;
		this.name = name;
		this.money = money;
	}
	
	@Override
	public String toString() {
		return "H_VenderpDTO [id=" + id + ", num=" + num + ", name=" + name + ", money=" + money + "]";
	}
	
	
	
}
