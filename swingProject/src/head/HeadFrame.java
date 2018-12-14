package head;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
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

	private JToggleButton F_OrderCheckBtn = new JToggleButton("가맹점 발주관리", new ImageIcon("img/가맹점 발주관리 아이콘.png"));
	private JToggleButton OrderBtn = new JToggleButton("발주관리", new ImageIcon("img/발주관리 아이콘.png"));
	private JToggleButton StockBtn = new JToggleButton("입-출고관리", new ImageIcon("img/입출고 아이콘.png"));
	private JToggleButton VenderBtn = new JToggleButton("업체관리", new ImageIcon("img/업체관리 아이콘.png"));
	private JToggleButton FranchiseBtn = new JToggleButton("가맹점관리", new ImageIcon("img/가맹점 관리 아이콘.png"));
	private JToggleButton SalesBtn = new JToggleButton("매출관리", new ImageIcon("img/매출관리 아이콘.png"));
	private JButton chattingBtn = new JButton("채팅관리", new ImageIcon("img/채팅관리 아이콘.png"));

	private ButtonGroup btnGroup = new ButtonGroup();
	// 버튼 그룹

	private CardLayout card = new CardLayout();

	private JPanel mainPanel = new JPanel();
	private H_Vender H_vender = new H_Vender();
	private H_Franchise H_franchise = new H_Franchise();
	private H_Order h_order = new H_Order();
	private H_Stock_InOut h_stock = new H_Stock_InOut();
	private H_CheckOrder h_checkOrder = new H_CheckOrder();
	private H_Salses h_salses = new H_Salses();

	private ImageIcon img = new ImageIcon("img/logo.png");
	//회사 이미지 아이콘
	private JLabel logoLabel = new JLabel(img);
	//회사 이미지 아이콘을 넣은 라벨!
	
	private JLabel serviceInfo = new JLabel("전산팀 연락처 : 02-xxx-xxxx");
	//연락처 라벨

	int xpos;
	int ypos;
	
	private Dimension point = Toolkit.getDefaultToolkit().getScreenSize();

	private ServerFrame serverFrame = new ServerFrame();

	private Color color = new Color(128, 144, 160);

	public HeadFrame() {

		F_OrderCheckBtn.setBackground(color);
		OrderBtn.setBackground(color);
		StockBtn.setBackground(color);
		VenderBtn.setBackground(color);
		FranchiseBtn.setBackground(color);
		SalesBtn.setBackground(color);
		chattingBtn.setBackground(color);
		//버튼의 색상을 일괄적으로 변경

		F_OrderCheckBtn.setFocusPainted(false);
		OrderBtn.setFocusPainted(false);
		StockBtn.setFocusPainted(false);
		VenderBtn.setFocusPainted(false);
		FranchiseBtn.setFocusPainted(false);
		SalesBtn.setFocusPainted(false);
		chattingBtn.setFocusPainted(false);
		
		
		serviceInfo.setBounds(12, 448, 216, 23);
		F_OrderCheckBtn.setBounds(12, 60, 145, 23);
		OrderBtn.setBounds(158, 60, 110, 23);
		StockBtn.setBounds(269, 60, 125, 23);
		VenderBtn.setBounds(395, 60, 110, 23);
		FranchiseBtn.setBounds(506, 60, 120, 23);
		SalesBtn.setBounds(627, 60, 110, 23);
		chattingBtn.setBounds(680, 10, 110, 23);
		//화면을 구성하는 각조 컴포넌트의 사이즈 지정

		F_OrderCheckBtn.setBorder(null);
		OrderBtn.setBorder(null);
		StockBtn.setBorder(null);
		VenderBtn.setBorder(null);
		FranchiseBtn.setBorder(null);
		SalesBtn.setBorder(null);
                    
		mainPanel.setBounds(12, 83, 770, 368);

		logoLabel.setBounds(10, 10, 107, 46);

		serverFrame.setLocationRelativeTo(this);

		mainPanel.add(H_vender);
		mainPanel.add(H_franchise);
		mainPanel.add(h_order);
		mainPanel.add(h_stock);
		mainPanel.add(h_checkOrder);
		mainPanel.add(h_salses);
		//메인 판넬에 각 서비스 판넬 추가

		btnGroup.add(F_OrderCheckBtn);
		btnGroup.add(OrderBtn);
		btnGroup.add(StockBtn);
		btnGroup.add(VenderBtn);
		btnGroup.add(FranchiseBtn);
		btnGroup.add(SalesBtn);
		//토글버튼 전부다 넣기

		getContentPane().add(F_OrderCheckBtn);
		getContentPane().add(OrderBtn);
		getContentPane().add(StockBtn);
		getContentPane().add(VenderBtn);
		getContentPane().add(FranchiseBtn);
		getContentPane().add(SalesBtn);
		getContentPane().add(logoLabel);
		getContentPane().add(chattingBtn);
		getContentPane().add(serviceInfo);
		//전체 화면에 각종 서비스 버튼 및 이미지 라벨 추가

		getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
	
	

		F_OrderCheckBtn.addActionListener(this);
		OrderBtn.addActionListener(this);
		StockBtn.addActionListener(this);
		VenderBtn.addActionListener(this);
		FranchiseBtn.addActionListener(this);
		SalesBtn.addActionListener(this);
		chattingBtn.addActionListener(this);

		F_OrderCheckBtn.doClick();

		// 사이즈 , 레이아웃 및 각종 설정
		setSize(800, 500);
		xpos = (int) (point.getWidth() / 2 - getWidth() / 2);
		ypos = (int) (point.getHeight() / 2 - getHeight() / 2);
		setLocation(xpos, ypos);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		if (e.getSource() == F_OrderCheckBtn) {// 가맹점 발주관리
			show(h_checkOrder);
			hide(h_stock);
			hide(h_order);
			hide(h_salses);
			hide(H_vender);
			hide(H_franchise);
		} else if (e.getSource() == OrderBtn) {// 본사 발주관리
			show(h_order);
			hide(h_checkOrder);
			hide(h_stock);
			hide(h_salses);
			hide(H_vender);
			hide(H_franchise);
		} else if (e.getSource() == StockBtn) {// 재고 입출고 관리
			show(h_stock);
			hide(h_order);
			hide(h_salses);
			hide(h_checkOrder);
			hide(H_vender);
			hide(H_franchise);
		} else if (e.getSource() == VenderBtn) {// 업체관리
			show(H_vender);
			hide(h_checkOrder);
			hide(h_stock);
			hide(h_order);
			hide(h_salses);
			hide(H_franchise);
		} else if (e.getSource() == FranchiseBtn) {// 가맹점 관리
			show(H_franchise);
			hide(h_checkOrder);
			hide(h_stock);
			hide(h_order);
			hide(h_salses);
			hide(H_vender);
		} else if (e.getSource() == SalesBtn) {// 매출관리
			hide(h_checkOrder);
			hide(h_stock);
			hide(h_order);
			show(h_salses);
			hide(H_vender);
			hide(H_franchise);
		}

		if (e.getSource() == chattingBtn) {// 채팅서버 관리
			serverFrame.setVisible(true);
		}

	}// actionPerformed 메서드 끝

	public static void main(String[] args) {
		new HeadFrame();
	}

}// 클래스 종료
