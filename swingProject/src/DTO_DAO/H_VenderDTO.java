package DTO_DAO;
/*
 * 2018-12-17 wonHn
 * 구현 및 주석 수정까지 완료 하였습니다.
 */
public class H_VenderDTO {
	private String id;
	private String name;
	private String tel;
	private String comNum;
	
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getComNum() {
		return comNum;
	}
	public void setComNum(String comNum) {
		this.comNum = comNum;
	}
	
	
	
	//default 생성자
	public H_VenderDTO() {
	}
	
	//모든 값을 받는 생성자.
	public H_VenderDTO(String id, String name, String tel, String comNum) {
		super();
		this.id = id;
		this.name = name;
		this.tel = tel;
		this.comNum = comNum;
	}
	
	@Override
	public String toString() {
		return "H_VenderDTO [id=" + id + ", name=" + name + ", tel=" + tel + ", comNum=" + comNum + "]";
	}
	
	
	
}
