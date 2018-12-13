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

	// 2자리 숫자만 받아오는 메서드
	void twoFigures(String s) {
		int a = Integer.parseInt(s);
		if(a>9&&a<100) {
			System.out.println("2자리수 맞음");
		}
		System.out.println("2자리수 아님");
	}
	
	// 3자리 숫자만 받아오는 메서드
	void threeFigures(String s) {
		int a = Integer.parseInt(s);
		if(a>99&&a<1000) {
			System.out.println("세자리수 맞음");
		}
		System.out.println("세자리수 아님");
	}
	
	// 4자리 숫자만 받아오는 메서드
	void fourFigures(String s) {
		int a = Integer.parseInt(s);
		if(a>999 && a<10000) {
			System.out.println("네자리수 맞음");
		}
		System.out.println("네자리수 아님");
	}
	
	// 5자리 숫자만 받아오는 메서드
	void fiveFigures(String s) {
		int a = Integer.parseInt(s);
		if(a>9999 && a<100000) {
			System.out.println("다섯자리 수 맞음");
		}
		System.out.println("다섯자리 아님");
	}

//	public H_V_C_worker() {
//	}
//
//	public static void main(String[] args) {
//		H_V_C_worker w = new H_V_C_worker();
//	}
}
