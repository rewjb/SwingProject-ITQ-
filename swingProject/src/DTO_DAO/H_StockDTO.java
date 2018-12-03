package DTO_DAO;

public class H_StockDTO {

	private String point;
	private String io;
	private String Name;
	private int quantity;
	private String date;
	
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getIo() {
		return io;
	}
	public void setIo(String io) {
		this.io = io;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public H_StockDTO(String point, String io, String name, int quantity, String date) {
		super();
		this.point = point;
		this.io = io;
		Name = name;
		this.quantity = quantity;
		this.date = date;
	}
	
	

	
	
	
}// 클래스 종료
