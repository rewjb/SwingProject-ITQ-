package joe;

import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;

import inter.BBQBody;
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

public class BodyFrame extends JFrame implements BBQBody, ActionListener {
	
	
	JToggleButton OrderBtn = new JToggleButton("발주관리");
	JToggleButton StockBtn = new JToggleButton("재고관리");
	JToggleButton SalesBtn = new JToggleButton("매출관리");
	JToggleButton hallButton = new JToggleButton("홀");

	ButtonGroup btnGroup = new ButtonGroup();
	// 버튼 그룹

	JPanel mainPanel = new JPanel();
	BodyOrderC orderC = new BodyOrderC();
	BodySalesC salesC = new BodySalesC();
	BodyStockC stockC = new BodyStockC();
	HallC hallc = new HallC();
	
	public BodyFrame() {
		// 사이즈 , 레이아웃 및 각종 설정
		setSize(820, 450);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		// 20 간격 !
		OrderBtn.setBounds(12, 30, 90, 23);
		StockBtn.setBounds(102, 30, 90, 23);
		SalesBtn.setBounds(193, 30, 90, 23);
		hallButton.setBounds(671, 30, 90, 23);
		

		mainPanel.setBounds(12, 53, 790, 364);
		mainPanel.setLayout(null);
		btnGroup.add(OrderBtn);
		btnGroup.add(StockBtn);
		btnGroup.add(SalesBtn);
		btnGroup.add(hallButton);

		getContentPane().add(OrderBtn);
		getContentPane().add(StockBtn);
		getContentPane().add(SalesBtn);
		getContentPane().add(hallButton);
		mainPanel.add(orderC);
		mainPanel.add(hallc);
		mainPanel.add(salesC);
		mainPanel.add(stockC);
		
		getContentPane().add(mainPanel);

		OrderBtn.addActionListener(this);
		StockBtn.addActionListener(this);
		SalesBtn.addActionListener(this);
		hallButton.addActionListener(this);

		
		setVisible(true);
	}// 생성자 종료

	@Override // 인터페이스 BBQHead로부터 받은 메서드 show 오버라이딩
	public void show(BBQBody bbqBody) {
		((Component) bbqBody).setVisible(true);
	}// show 메서드 끝

	@Override // 인터페이스 BBQHead로부터 받은 메서드 hide 오버라이딩
	public void hide(BBQBody bbqBody) {
		((Component) bbqBody).setVisible(false);
	}// hide 메서드 끝

	@Override // 인터페이스 ActionListener로부터 받은 메서드 hide 오버라이딩
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == OrderBtn) {// 본사 발주관리
			show(orderC);
			hide(salesC);
			hide(hallc);
			hide(stockC);
		} else if (e.getSource() == StockBtn) {// 재고 입출고 관리
			show(stockC);
			hide(orderC);
			hide(salesC);
			hide(hallc);
		} else if (e.getSource() == SalesBtn) {// 매출관리
			show(salesC);
			hide(orderC);
			hide(hallc);
			hide(stockC);
		} else if (e.getSource()== hallButton) {// 홀 관리 
			show(hallc);
			hide(orderC);
			hide(stockC);
			hide(salesC);
		}

	}// actionPerformed 메서드 끝

	public static void main(String[] args) throws Exception {
		new BodyFrame();
	}// main 메서드 종료

}// 클래스 종료
