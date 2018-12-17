package won;
/*
 * 20181203
 * H_V_Company에서 동작하는 그외 기능들을 만들어놓은 클래스 입니다.
 * 1205 완료했습니다. 이후 DB와 같이 연동해서 테스트 해야 합니다.
 */



public class H_V_C_worker {

	// 마지막 아이디 가져오는 메서드
	String findLastId(String id, String input) {
		if (input == null || id == null) {
		} else {
			char id0 = id.charAt(0);
			char id1 = id.charAt(1);
			char ipId0 = input.charAt(0);
			char ipId1 = input.charAt(1);

			if (id0 >= ipId0) {
				if (id1 > ipId1) {
					return id;
				}
			}
		}
		return input;
	}

	// id 생성 메서드 - 같은 패키지 내에서만 동작함
	String makeId(String id) {
		if (id == null) {
			id = "AA";
		} else {
			char id0 = id.charAt(0);
			char id1 = id.charAt(1);
			if (id1 == 'Z') {
				id1 = 'A';
				id0++;
			}
			id1++;
			id = "" + id0 + id1;
		}
		return id;
	}
}
