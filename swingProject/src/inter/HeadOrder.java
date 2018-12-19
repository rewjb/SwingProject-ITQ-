package inter;

public interface HeadOrder extends BBQHead {

	public void stockInsert();
	// 발주 넣을 때 참고용으로 재고를 볼 수 있도록 테이블에 현재 전체 재고를 보여주는 메서드

	public void orderInsert(int index);
	// 발주목록을 페이지에 맞게 보여주는 메서드

	public void assignBtnIndex();
	// 페이지에 따른 버튼의 속성 메서드

}
