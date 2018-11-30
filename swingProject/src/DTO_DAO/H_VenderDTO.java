package DTO_DAO;
/*
 * 2018-11-29 wonHn
 */
public class H_VenderDTO {
	private String id;
	private String name;
	private String tel;
	
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
	
	//default 생성자
	public H_VenderDTO() {

	}
	
	//모든 값을 받는 생성자.
	public H_VenderDTO(String id, String name, String tel) {
		this.id = id;
		this.name = name;
		this.tel = tel;
	}
	
	@Override
	public String toString() {
		return "H_VenderDTO [id=" + id + ", name=" + name + ", tel=" + tel + "]";
	}
	
	
	
}
