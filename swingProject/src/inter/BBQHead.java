package inter;

import java.awt.Component;

//수정일 : 2018-11-26
//본사의 최상위 인터페이스

public interface BBQHead extends BBQ
{
	 public void show(BBQHead bbqHead); //Panel 올리기
	 public void hide(BBQHead bbqHead); //Panel 내리기
}
