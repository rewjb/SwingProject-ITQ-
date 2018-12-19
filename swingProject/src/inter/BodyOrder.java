package inter;

public interface BodyOrder extends  BBQBody{

	public void reservesReset();//발주클래스의 식자재 목록 새로고침 메서드
	public void orderDelete(); //발주클래스의 발주취소 메서드
	
}
