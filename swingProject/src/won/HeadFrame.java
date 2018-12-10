package won;

import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import inter.BBQHead;
import javax.swing.JPanel;

// 담당 :유주빈
//수정일 : 2018-11-26 
// ---설명---
// Jpanel을 객체생성하여 해당버튼을 누를시 해당 내용을 보여지도록 한다.
// 현재 정의된 메서드 2개를 이용하여 화면구현을 하는데 그 2가지는 show와 hide 메서드이다.
// show와 hide에 Jpanel의 타입이 넘어가면 화면에 부품이 나타나거나 사라진다.

// -결론적으로 하기와 같이 코딩을 하면 된다.
// 현재 mainPanel이라는 JPanel이 있다. (2018-11-26일자로는 아무것도 add하지 않음)
// 1) 제작한 JPanel의 객체를 전역필드에 선언한다.
// 2) 생성한 객체를 HeadFrame클래스 생성자 내에서 mainPanel에 add 시킨다.
// 3) 아래에 ActionListener메서드에 해당하는 버튼의 if문 안에 show 메서드 매개변수로 add시킨 객체를 넣는다.
// 아래의 4번은 제작한 모든  Jpanel을 3번까지의 과정을 거친 후 4번을 실행한다.
// 4) 3번이 모든 부품이 입력되었다면 마지막으로 hide 메서드로 나머지를 감추는 마무리 작업을 한다.

public class HeadFrame extends JFrame implements BBQHead, ActionListener {

	JToggleButton F_OrderCheckBtn = new JToggleButton("가맹점 발주관리");
	JToggleButton OrderBtn = new JToggleButton("발주관리");
	JToggleButton StockBtn = new JToggleButton("입-출고관리");
	JToggleButton VenderBtn = new JToggleButton("업체관리");
	JToggleButton FranchiseBtn = new JToggleButton("가맹점관리");
	JToggleButton SalesBtn = new JToggleButton("매출관리");

	ButtonGroup btnGroup = new ButtonGroup();
	// 버튼 그룹

	CardLayout card = new CardLayout();
	
	JPanel mainPanel = new JPanel();
	H_Vender H_vender = new H_Vender();
	H_Franchise H_franchise = new H_Franchise();
	

	public HeadFrame() {
		//20 간격 !
		F_OrderCheckBtn.setBounds(12, 20, 130, 23);
		OrderBtn.setBounds(143, 20, 90, 23);
		StockBtn.setBounds(234, 20, 110, 23);
		VenderBtn.setBounds(345, 20, 90, 23);
		FranchiseBtn.setBounds(436, 20, 100, 23);
		SalesBtn.setBounds(537, 20, 90, 23);

		mainPanel.setBounds(12, 43, 770, 368);
		mainPanel.setLayout(card);

		mainPanel.add(H_vender);
		mainPanel.add(H_franchise);
		
		btnGroup.add(F_OrderCheckBtn);
		btnGroup.add(OrderBtn);
		btnGroup.add(StockBtn);
		btnGroup.add(VenderBtn);
		btnGroup.add(FranchiseBtn);
		btnGroup.add(SalesBtn);

		getContentPane().add(F_OrderCheckBtn);
		getContentPane().add(OrderBtn);
		getContentPane().add(StockBtn);
		getContentPane().add(VenderBtn);
		getContentPane().add(FranchiseBtn);
		getContentPane().add(SalesBtn);

		getContentPane().add(mainPanel);
		mainPanel.setLayout(null);

		F_OrderCheckBtn.addActionListener(this);
		OrderBtn.addActionListener(this);
		StockBtn.addActionListener(this);
		VenderBtn.addActionListener(this);
		FranchiseBtn.addActionListener(this);
		SalesBtn.addActionListener(this);

		// 사이즈 , 레이아웃 및 각종 설정
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 450);
		setResizable(false);
		setVisible(true);
	}// 생성자 종료

	@Override // 인터페이스 BBQHead로부터 받은 메서드 show 오버라이딩
	public void show(BBQHead bbqHead) {
		((Component) bbqHead).setVisible(true);
	}// show 메서드 끝

	@Override // 인터페이스 BBQHead로부터 받은 메서드 hide 오버라이딩
	public void hide(BBQHead bbqHead) {
		((Component) bbqHead).setVisible(false);
	}// hide 메서드 끝

	@Override // 인터페이스 ActionListener로부터 받은 메서드 hide 오버라이딩
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == F_OrderCheckBtn) {//가맹점 발주관리
			hide(H_vender);
			hide(H_franchise);
		} else
		if (e.getSource() == OrderBtn) {//본사 발주관리
			hide(H_vender);
			hide(H_franchise);
		} else
		if (e.getSource() == StockBtn) {//재고 입출고 관리
			hide(H_vender);
			hide(H_franchise);
		} else
		if (e.getSource() == VenderBtn) {//업체관리
			show(H_vender);
			hide(H_franchise);
		} else
		if (e.getSource() == FranchiseBtn) {//가맹점 관리
			show(H_franchise);
			hide(H_vender);
		} else
		if (e.getSource() == SalesBtn) {//매출관리
			hide(H_vender);
			hide(H_franchise);
		}

	}// actionPerformed 메서드 끝

	public static void main(String[] args) {
		new HeadFrame();
	}// main 메서드 종료

}// 클래스 종료
