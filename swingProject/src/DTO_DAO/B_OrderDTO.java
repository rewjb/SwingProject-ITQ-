package DTO_DAO;

import java.util.Calendar;

public class B_OrderDTO {

	
	
	private int num;// 발주당 고유번호
	private String id;// 가맹점 아이디
	private String name;// 발주 제품
	private String quantity;// 발주 수량
	private String date;// 발주 날짜
	private String hComfirm;// 발주 후 본사 확인
	private String bComfirm;// 제품 수령 후 가맹점 확인

	public B_OrderDTO(int num, String id, String name, String quantity, String date, String hComfirm, String bComfirm) {
		super();
		this.num = num;
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.date = date;
		this.hComfirm = hComfirm;
		this.bComfirm = bComfirm;
	}

	public B_OrderDTO() {// 기본생성자
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String gethComfirm() {
		return hComfirm;
	}

	public void sethComfirm(String hComfirm) {
		this.hComfirm = hComfirm;
	}

	public String getbComfirm() {
		return bComfirm;
	}

	public void setbComfirm(String bComfirm) {
		this.bComfirm = bComfirm;
	}

	public B_OrderDTO(String bComfirm) {
		super();
		this.bComfirm = bComfirm;
	}

}// 클래스 끝
