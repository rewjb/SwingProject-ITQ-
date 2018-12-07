package won;
/*
 * 2018-12-07
 */

import DTO_DAO.*;

public class H_F_worker {
	
	// 마지막 아이디 가져오는 메서드
	protected String findLastId(String id, String input) {
		if (id != null) {
			char[] idArr = new char[5];
			char[] inputArr = new char[5];
			for (int i = 0; i < idArr.length; i++) {
				idArr[i] = id.charAt(i);
				inputArr[i] = input.charAt(i);
				if(idArr[i]<inputArr[i]) {
					return input;
				}//end inner if
			}//end for
		}//end outer if
		return id;
	}

	// id 생성 메서드 - 같은 패키지 내에서만 동작함
	protected String makeId(String id) {
		if (id == null) {
			id = "aaa";
		} else {
			char id0 = id.charAt(0);
			char id1 = id.charAt(1);
			if (id1 == 'z') {
				id1 = 'a';
				id0++;
			}
			id1++;
			id = "" + id0 + id1;
		}
		return id;
	}
}
