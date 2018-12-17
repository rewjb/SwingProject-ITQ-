package won;

/* wHn 2018-12-17 주석 및 변수 정리 완료
 * H_V_Company에서 동작하는 그외 기능들을 만들어놓은 클래스 입니다.
 */
public class H_V_C_worker {

	/*
	 * 현재 DB에 있는 마지막 아이디를 가져오는 메서드 가맹점 동작하는 클래스에 마지막 아이디 값을 받아오는 변수가 있음(String id)
	 * DAO의 select all을 실행하면서 그 순서의 id를 매번 넣어줌(String input) 반복문이 한번 실행될때마다 받아져있는
	 * 마지막 아이디와 현재 아이디를 비교해서 마지막 순서의 아이디를 반환해주는 메서드
	 */
	String findLastId(String id, String input) {
		if (input == null || id == null) {
	/*
	 * 둘중 하나이상이 null일경우에는 비교하지 않고 input 값을 반환해준다. 가맹점 동작하는 클래스에서 이 메서드의 반환값을 받는 변수는
	 * id임. 둘다 null일경우 : 둘다 null이어서 그냥 null을 반환 id가 null일 경우 : input된 값을 반환해서 id에
	 * 넣어준다. input이 null일 경우 : DB에 데이터가 없다는 뜻이므로 null을 반환해준다.
	 */
		} else {
			// String 변수의 각 자리를 나눠서 담아놓는 문자 배열
			char id0 = id.charAt(0);	
			char id1 = id.charAt(1);
			char ipId0 = input.charAt(0);
			char ipId1 = input.charAt(1);
			
			// 위의 두 배열의 같은 인덱스에 해당하는 값을 비교
			if (id0 >= ipId0) {
				if (id1 > ipId1) {
					return id;
				}
			}
		}
		return input;
	}//end findLastId()
	
	/*
	 * id 생성 메서드 - 같은 패키지 내에서만 동작함. 
	 * DAO의 select all을 실행하면서 받아놓은 가장 마지막 아이디 값을 기준으로 
	 * 그 다음 순서 아이디를 생성함
	 */
	String makeId(String id) {
		if (id == null) {
			id = "AA";	//첫번째 업체 아이디
		} else {
			char id0 = id.charAt(0);
			char id1 = id.charAt(1);
		// 어떤 자리수의 값이 Z일 경우에는 윗자리를 1 올려주고 해당자리에는 A를 넣어줌
			if (id1 == 'Z') {
				id1 = 'A';
				id0++;
			}
		// 모든 자리에 z가 없을경우에는 맨 마지막 자리 수만 1올려줌
			id1++;
			id = "" + id0 + id1;
		}
		return id;
	}//end makeId()
}