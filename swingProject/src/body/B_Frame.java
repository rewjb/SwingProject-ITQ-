package body;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
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

public class B_Frame extends JFrame implements BBQBody, ActionListener {

	JToggleButton OrderBtn = new JToggleButton("발주관리",new ImageIcon("img/발주관리 아이콘.png"));
	JToggleButton StockBtn = new JToggleButton("재고관리",new ImageIcon("img/입출고 아이콘.png"));
	JToggleButton SalesBtn = new JToggleButton("매출관리",new ImageIcon("img/매출관리 아이콘.png"));
	JToggleButton hallButton = new JToggleButton("홀");
	JButton btnNewButton = new JButton("채팅관리",new ImageIcon("img/채팅관리 아이콘.png"));
	
	
	
	
	ImageIcon logoImg = new ImageIcon("img/logo.png");
	JLabel logoLabel = new JLabel(logoImg);
	JLabel serviceInfo = new JLabel(logoImg);
	
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image frameimg = toolkit.getImage("img/회사 프레임 아이콘.jpg");


	ButtonGroup btnGroup = new ButtonGroup();
	// 버튼 그룹

	JPanel mainPanel = new JPanel();
	B_OrderC orderC = new B_OrderC();
	B_SalesC salesC = new B_SalesC();
	B_StockC stockC = new B_StockC();
	B_HallC hallc = new B_HallC();
	public static String id ;

	B_ChattingFrame clientFrame;
	
	private Color color = new Color(128, 144, 160);//버튼 색상
	
	public B_Frame(String id) {
		// 사이즈 , 레이아웃 및 각종 설정
		this.id = id;

		clientFrame = new B_ChattingFrame(id);
		clientFrame.setLocationRelativeTo(this);
		 
		
		setSize(820, 515);
		setTitle("JVQ "+id+" 계정");
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(frameimg);
		setResizable(false);
		
		
		// 20 간격 !
		OrderBtn.setBounds(10, 54, 107, 23);
		StockBtn.setBounds(119, 54, 107, 23);
		SalesBtn.setBounds(228, 54, 107, 23);
		hallButton.setBounds(337, 54, 107, 23);
		btnNewButton.setBounds(689, 10, 111, 23);
		logoLabel.setBounds(0, 10, 107, 46);
		OrderBtn.setBackground(color);
		StockBtn.setBackground(color);
		SalesBtn.setBackground(color);
		hallButton.setBackground(color);
		btnNewButton.setBackground(color);
		OrderBtn.setBorder(null);
		StockBtn.setBorder(null);
		SalesBtn.setBorder(null);
		hallButton.setBorder(null);
		btnNewButton.setBorder(null);
		
		

		mainPanel.setBounds(10, 77, 790, 370);
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.GRAY);
		btnGroup.add(OrderBtn);
		btnGroup.add(StockBtn);
		btnGroup.add(SalesBtn);
		btnGroup.add(hallButton);

		getContentPane().add(OrderBtn);
		getContentPane().add(StockBtn);
		getContentPane().add(SalesBtn);
		getContentPane().add(hallButton);
		getContentPane().add(logoLabel);
		getContentPane().setBackground(new Color(202, 208, 213));
		mainPanel.add(orderC);
		mainPanel.add(hallc);
		mainPanel.add(salesC);
		mainPanel.add(stockC);

		getContentPane().add(mainPanel);
		getContentPane().add(btnNewButton);

		btnNewButton.addActionListener(this);
		OrderBtn.addActionListener(this);
		StockBtn.addActionListener(this);
		SalesBtn.addActionListener(this);
		hallButton.addActionListener(this);

		OrderBtn.doClick(); 
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
		} else if (e.getSource() == hallButton) {// 홀 관리
			show(hallc);
			hide(orderC);
			hide(stockC);
			hide(salesC);
		}

		if (e.getSource() == btnNewButton) {
			// 채팅창 접속
			clientFrame.setVisible(true);
		}

	}// actionPerformed 메서드 끝

}// 클래스 종료
