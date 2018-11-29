package DTO_DAO;

public class H_FranchiseDTO {

	private String id;
	private String pw;
	private String ownername;
	private String tel;
	private String comnum;
	private String addr;
	private String alias;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getOwnername() {
		return ownername;
	}

	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getComnum() {
		return comnum;
	}

	public void setComnum(String comnum) {
		this.comnum = comnum;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public H_FranchiseDTO() {
	}

	public H_FranchiseDTO(String id, String pw, String ownername, String tel, String comnum, String addr,
			String alias) {
		super();
		this.id = id;
		this.pw = pw;
		this.ownername = ownername;
		this.tel = tel;
		this.comnum = comnum;
		this.addr = addr;
		this.alias = alias;
	}

}
