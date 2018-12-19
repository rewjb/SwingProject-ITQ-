package inter;

public interface HeadStockInOut extends BBQHead{

	
	public void insertPointList(String point);
	// 해당 포인트에 해당하는 재고 테이블 넣기

	public void  insertInOutTotalStockHistoryList();
	// 입출고 및 전체 재고 테이블에 재고 넣기
	
}// 클래스 종료
