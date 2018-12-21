package head;
/* wHn 2018-12-17 주석 및 변수 정리 완료
 * 가맹점 아이디 생성에 관련된 동작을 하는 메서드들을 모아놓은 클래스입니다.
 */

//1. 계층도
//하위 내용은 클래스 계층도 입니다.
//기본적으로 인터페이스 계층도 이며 () 괄호안에 들어가 있는 것은 인터페이스 및 클래스 여부입니다.
//1) 기호정리
//괄호 :  I(인터페이스) , C(클래스)
//↑ : 아래에 위치한 클래스를 위에 있는 클래스에서 객체로 만들어 사용
//│ : 상속 및 구현
//                                                                                                BBQ(I)                                                                          
//                                                       ┌───────────────────────────────────────────┴──────────────────────────────────────────────────────────────────────────────────────────┐
//                                                   BBQHead(I)                                                                                                                             BBQBody(I) 
//     ┌───────────────────┌────────────────────┌────────┴────────────┐──────────────────┐─────────────────┐────────────────┐──────────────────────────┐                   ┌────────────┌───────┴───────┐───────────────┐ 
//HeadCheckOrder(I)    HeadOrder(I)       HeadStockInOut(I)       HeadVender(I)  HeadVenderProduct(I) HeadFranchise(I)   HeadSales(I)            H_ChattingFrame(C)     BodyHall(I)   BodyOrder(I)   BodySales(I)   BodyStock(I)
//     │                   │                    │                     │                  │                 │                │                            ↑                   │            │               │               │
//H_CheckOrder(C)      H_Order(C)         H_StockInOut(C)         H_Vender(C)       H_V_Product(C)    H_Franchise(C)     H_Salses(C)             ┌───────│─────────────┐ B_HallC(C)   B_OrderC(C)     B_SalesC(C)     B_StockC(C)
//                                                                                                             ┌─────────────↑────────────────┐  │H_ChattingJoin(C)    │                     ┌────────────↑─────────────┐
//                                                                                                             │ H_Salses_BodySalesBarChart(C)│  │       ↑             │                     │B_SalesC_SalesDataChart(C)│ 
//                                                                                                             │ H_Salses_BodySalesPieChart(C)│  │H_ChattingrManager(C)│                     └──────────────────────────┘
//                                                                                                             │H_Salses_HeadSalsesBarChart(C)│  └─────────────────────┘
//                                                                                                             └──────────────────────────────┘
//2. 변수명
//1) 전역변수 식별자 특징
//=> g_식별자  (g : 글로벌 변수)  ,  ex) g_BtnSelect
//=> sg_식별자   : 글로벌 변수 + public static

public class H_F_worker {

	/*
	 * 현재 DB에 있는 마지막 아이디를 가져오는 메서드 가맹점 동작하는 클래스에 마지막 아이디 값을 받아오는 변수가 있음(String id)
	 * DAO의 select all을 실행하면서 그 순서의 id를 매번 넣어줌(String input) 반복문이 한번 실행될때마다 받아져있는
	 * 마지막 아이디와 현재 아이디를 비교해서 마지막 순서의 아이디를 반환해주는 메서드
	 */
	String findLastId(String id, String input) {
		if (input == "root") { // 비교값이 root일 경우에는 비교하지 않고 id값을 반환해준다.
			return id;
		}
		if (id == null || input == null) {
	/*
	 * 둘중 하나이상이 null일경우에는 비교하지 않고 input 값을 반환해준다. 가맹점 동작하는 클래스에서 이 메서드의 반환값을 받는 변수는
	 * id임. 둘다 null일경우 : 둘다 null이어서 그냥 null을 반환 id가 null일 경우 : input된 값을 반환해서 id에
	 * 넣어준다. input이 null일 경우 : DB에 데이터가 없다는 뜻이므로 null을 반환해준다.
	 */
		} else {
			int j = 0; // id의 각 자리수가 모두 큰지 여부를 확인하기 위한 변수.
			char[] idArr = new char[3]; // id의 각 자리를 나눠서 담아놓는 배열
			char[] inputArr = new char[3]; // input의 각 자리를 나눠서 담아놓는 배열
			for (int i = 0; i < idArr.length; i++) {
				idArr[i] = id.charAt(i);
				inputArr[i] = input.charAt(i);
				if (idArr[i] > inputArr[i]) { // 위의 두 배열의 같은 인덱스에 해당하는 값을 비교
					j++;
					if (j == 3) {
						return id;
					}
				} // end inner if
			} // end for
		} // end outer if
		return input;
	}//end findLastId()

	/*
	 * id 생성 메서드 - 같은 패키지 내에서만 동작함. 
	 * DAO의 select all을 실행하면서 받아놓은 가장 마지막 아이디 값을 기준으로 
	 * 그 다음 순서 아이디를 생성함
	 */
	String makeId(String id) {
		if (id == null) {
			id = "aaa"; // 첫번째 가맹점 아이디
		} else {
			char id0 = id.charAt(0);
			char id1 = id.charAt(1);
			char id2 = id.charAt(2);
			// 어떤 자리수의 값이 z일 경우에는 윗자리를 1올려주고 해당자리에는 a를 넣어줌
			if (id2 == 'z') {
				id2 = 'a';
				id1++;
				if (id1 == 'z') {
					id1 = 'a';
					id0++;
				}
			}
			// 모든 자리에 z가 없을경우에는 맨 마지막 자리 수만 1올려줌
			id2++;
			id = "" + id0 + id1 + id2;
		}
		return id;
	}//end makeId()
}
