package won;
/*
 * 20181203
 * 각 페이지에 동작을 담아놓는 클래스...
 * 추가(insert)
 * 
 */

import java.util.ArrayList;

import DTO_DAO.H_VenderDAO;
import DTO_DAO.H_VenderDTO;

public class H_V_C_worker {
	H_VenderDAO vDAO = new H_VenderDAO();
	H_VenderDTO vDTO;
	ArrayList<H_VenderDTO> list = new ArrayList<>();

	private static char id0 = 'A';
	private static char id1 = 'A';
	
	//id 생성 메서드
	private String makeId() {
		String id = ""+id0+id1;
		if(id1=='Z') {
			id1 = 'A';
			id0++;
		}
		id1++;
		return id;
	}
	
	//이름과 사업자번호 중복확인 할 경우...
	private boolean checkInput(String name, String comNum) {
		return true;
	}
	public void save(String id, String name, String comNum, String tel) {
		if(checkInput(name,comNum)){
			return;
		}
		
		checkInput(name,comNum);
		vDTO.setId(id+id0+id1);
		vDTO.setName(name);
//		vDto.setcomNum(comNum);
		vDTO.setTel(tel);
		
		vDAO.insertVenderInfo(vDTO);
		list = vDAO.selectALLVenderInfo();
	}
}
