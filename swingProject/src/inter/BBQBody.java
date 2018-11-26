package inter;

//수정일 : 2018-11-26
//가맹점의 최상위 인터페이스

public interface BBQBody extends BBQ
{
	 public void show(BBQBody bbqBody); //Panel 올리기
	 public void hide(BBQBody bbqBody); //Panel 내리기
}
