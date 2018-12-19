package inter;

public interface HeadCheckOrder extends BBQHead {

	public void orderInsert(int index);
	// 페이지에 따라 가맹점 재고가 테이블에 추가되는 메서드

	public void assignBtnIndex();
	// 페이지에 따라 페이지 넘김버튼의 속성을 결정하는 메서드

	public void aliasNtelInsert();
	// 가맹점명과 가맹점 연락처를 테이블에 추가하는 메서드

}// 클래스 종료
