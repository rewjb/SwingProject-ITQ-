package won;
/*
 * wHn
 * 2018-12-13 수정완료
 * 
 */

import DTO_DAO.*;

public class H_F_worker {

	// 마지막 아이디 가져오는 메서드
	String findLastId(String id, String input) {
		if(input == "root") {
			return id;
		}
		if (id == null || input == null) {
		} else {
			int j = 0;	//id의 모든 자리수가 큰지 여부를 확인하기 위한 변수.
			char[] idArr = new char[3];
			char[] inputArr = new char[3];
			for (int i = 0; i < idArr.length; i++) {
				idArr[i] = id.charAt(i);
				inputArr[i] = input.charAt(i);
				if (idArr[i] > inputArr[i]) {
					j++;
					if(j==3) {
						return id;
					}
				} // end inner if
			} // end for
		} // end outer if
		return input;
	}

	// id 생성 메서드 - 같은 패키지 내에서만 동작함.
	String makeId(String id) {
		if (id == null) {
			id = "aaa";
		} else {
			char id0 = id.charAt(0);
			char id1 = id.charAt(1);
			char id2 = id.charAt(2);
			if (id2 == 'z') {
				id2 = 'a';
				id1++;
				if (id1 == 'z') {
					id1 = 'a';
					id0++;
				}
			}
			id2++;
			id = "" + id0 + id1 + id2;
		}
		return id;
	}
}
