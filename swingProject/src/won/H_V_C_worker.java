package won;
/*
 * 20181203
 * H_V_Company에서 동작하는 그외 기능들을 만들어놓은 클래스 입니다.
 * 1205 완료했습니다. 이후 DB와 같이 연동해서 테스트 해야 합니다.
 */

import java.util.ArrayList;

import DTO_DAO.*;

public class H_V_C_worker {
	H_VenderDAO vDAO = new H_VenderDAO();
	H_VenderDTO vDTO;
	
	//id 생성 메서드 - 같은 패키지 내에서만 동작함
	protected String makeId(String id) {
		if(id == null) {
			id = "AA";
		}else {
			char id0 = id.charAt(0);
			char id1 = id.charAt(1);
			if (id1 == 'Z') {
				id1 = 'A';
				id0++;
			}
			id1++;
			id = ""+id0+id1;
		}
		return id;
	}
//	
//	//이름 중복확인 할 경우...
//	protected boolean checkInput(String name) {
//		vDTO = vDAO.selectVenderInfo("name",name);
//		if(vDTO == null) {
//			return true;
//		}else {
//			return false;
//		}
//	}
//
//	테스트...!	
//	public H_V_C_worker() {
//		String id = "";
//		System.out.println(id);
//		String id1 = makeId(id);
//		System.out.println(id1);
//		String id2 = makeId(id1);
//		System.out.println(id2);
//		String id3 = makeId(id2);
//		System.out.println(id3);
//	}
//	public static void main(String[] args) {
//		H_V_C_worker w = new H_V_C_worker();
//	}
}
