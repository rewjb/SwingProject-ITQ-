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
// 현재 g_MainPanel이라는 JPanel이 있다. (2018-11-26일자로는 아무것도 add하지 않음)
// 1) 제작한 JPanel의 객체를 전역필드에 선언한다.
// 2) 생성한 객체를 HeadFrame클래스 생성자 내에서 g_MainPanel에 add 시킨다.
// 3) 아래에 ActionListener메서드에 해당하는 버튼의 if문 안에 show 메서드 매개변수로 add시킨 객체를 넣는다.
// 아래의 4번은 제작한 모든  Jpanel을 3번까지의 과정을 거친 후 4번을 실행한다.
// 4) 3번이 모든 부품이 입력되었다면 마지막으로 hide 메서드로 나머지를 감추는 마무리 작업을 한다.


//1. 계층도
//하위 내용은 클래스 계층도 입니다.
//기본적으로 인터페이스 계층도 이며 () 괄호안에 들어가 있는 것은 인터페이스 및 클래스 여부입니다.
//1) 기호정리
//   괄호 :  I(인터페이스) , C(클래스)
//    ↑ : 아래에 위치한 클래스를 위에 있는 클래스에서 객체로 만들어 사용
//    │ : 상속 및 구현
//                                                                                                BBQ(I)                                                                          
//                                                       ┌───────────────────────────────────────────┴──────────────────────────────────────────────────────────────────────────────────────────┐
//                                                   BBQHead(I)                                                                                                                             BBQBody(I) 
//     ┌───────────────────┌────────────────────┌────────┴────────────┐──────────────────┐─────────────────┐────────────────┐──────────────────────────┐                   ┌────────────┌───────┴───────┐───────────────┐ 
//HeadCheckOrder(I)    HeadOrder(I)       HeadStockInOut(I)       HeadVender(I)  HeadVenderProduct(I) HeadFranchise(I)   HeadSales(I)            H_ChattingFrame(C)     BodyHall(I)   BodyOrder(I)   BodySales(I)   BodyStock(I)
//     │                   │                    │                     │                  │                 │                │                          ↑                   │            │               │               │
//H_CheckOrder(C)      H_Order(C)         H_StockInOut(C)         H_Vender(C)       H_V_Product(C)    H_Franchise(C)     H_Salses(C)            ┌───────│─────────────┐ B_HallC(C)   B_OrderC(C)     B_SalesC(C)     B_StockC(C)
//                                                                                                            ┌─────────────↑────────────────┐ │H_ChattingJoin(C)    │                     ┌────────────↑─────────────┐
//                                                                                                            │ H_Salses_BodySalesBarChart(C)│ │       ↑             │                     │B_SalesC_SalesDataChart(C)│ 
//                                                                                                            │ H_Salses_BodySalesPieChart(C)│ │H_ChattingrManager(C)│                     └──────────────────────────┘
//                                                                                                            │H_Salses_HeadSalsesBarChart(C)│ └─────────────────────┘
//                                                                                                            └──────────────────────────────┘
//2. 변수명
//1) 전역변수 식별자 특징
//  => g_식별자  (g : 글로벌 변수)  ,  ex) g_BtnSelect

public class B_Frame extends JFrame implements BBQBody, ActionListener {

	private 	JToggleButton g_OrderBtn = new JToggleButton("발주관리",new ImageIcon("img/발주관리 아이콘.png"));//발주관리 토클버튼
	private 	JToggleButton g_StockBtn = new JToggleButton("재고관리",new ImageIcon("img/입출고 아이콘.png"));//재고관리 토글버튼
	private 	JToggleButton g_SalesBtn = new JToggleButton("매출관리",new ImageIcon("img/매출관리 아이콘.png"));//매출관리 토글버튼
	private 	JToggleButton g_HallButton = new JToggleButton("홀");//홀 토글버튼
	private 	JButton g_ChettingBtn = new JButton("채팅관리",new ImageIcon("img/채팅관리 아이콘.png"));//채팅관리 버튼
	
	
	
	
	private 	ImageIcon g_LogoImg = new ImageIcon("img/logo.png");//회사 메인 로고 
	private 	JLabel g_LogoLabel = new JLabel(g_LogoImg);//메인로고를 담을 라벨
	//JLabel g_ServiceInfo = new JLabel(g_LogoImg);
	
	private Toolkit g_Toolkit = Toolkit.getDefaultToolkit();// 툴킷
	private 	Image g_Frameimg = g_Toolkit.getImage("img/회사 프레임 아이콘.jpg");//툴킷의 이미지 아이콘 

	// 버튼 그룹
	private 	ButtonGroup g_BtnGroup = new ButtonGroup();


	private 	JPanel g_MainPanel = new JPanel();//각 메뉴 판넬을 올릴 메인판넬
	private 	B_OrderC g_OrderC = new B_OrderC();//발주 판넬 객체
	private 	B_SalesC g_SalesC = new B_SalesC();//매출 판넬 객체
	private B_StockC g_StockC = new B_StockC();//재고 판넬 객체
	private B_HallC g_Hallc = new B_HallC();//홀 판넬 객체 
	public static String st_G_id ;//가맹점 로그인시 아이디를 담아두는 변수 

	private B_ChattingFrame g_ClientFrame;//채팅 클라이언트 객체 선언 
	
	private Color g_Color = new Color(128, 144, 160);//버튼 색상
	
	public B_Frame(String id) {
		// 사이즈 , 레이아웃 및 각종 설정
		this.st_G_id = id;

		g_ClientFrame = new B_ChattingFrame(id);//클라이언트 객체 생성 
		g_ClientFrame.setLocationRelativeTo(this);//띄우는 창을 화면의 정중앙에 띄우기
		 
		//프레임 셋팅
		setSize(820, 515);
		setTitle("JVQ "+id+" 계정");
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(g_Frameimg);
		setResizable(false);
		
		
		// 버튼 위치 색상 레이아웃 설정
		g_OrderBtn.setBounds(10, 54, 107, 23);
		g_StockBtn.setBounds(119, 54, 107, 23);
		g_SalesBtn.setBounds(228, 54, 107, 23);
		g_HallButton.setBounds(337, 54, 107, 23);
		g_ChettingBtn.setBounds(689, 10, 111, 23);
		g_LogoLabel.setBounds(0, 10, 107, 46);
		g_OrderBtn.setBackground(g_Color);
		g_StockBtn.setBackground(g_Color);
		g_SalesBtn.setBackground(g_Color);
		g_HallButton.setBackground(g_Color);
		g_ChettingBtn.setBackground(g_Color);
		g_OrderBtn.setBorder(null);
		g_StockBtn.setBorder(null);
		g_SalesBtn.setBorder(null);
		g_HallButton.setBorder(null);
		g_ChettingBtn.setBorder(null);
		
		
		//메인 판넬 위치 레이아웃 색깔 설정
		g_MainPanel.setBounds(10, 77, 790, 370);
		g_MainPanel.setLayout(null);
		g_MainPanel.setBackground(Color.GRAY);
		//버튼그룹에 들어갈 버튼들 add
		g_BtnGroup.add(g_OrderBtn);
		g_BtnGroup.add(g_StockBtn);
		g_BtnGroup.add(g_SalesBtn);
		g_BtnGroup.add(g_HallButton);

		//필요한 컴포넌트들 프레임에 넣어준다.
		getContentPane().add(g_OrderBtn);
		getContentPane().add(g_StockBtn);
		getContentPane().add(g_SalesBtn);
		getContentPane().add(g_HallButton);
		getContentPane().add(g_LogoLabel);
		getContentPane().setBackground(new Color(202, 208, 213));
		g_MainPanel.add(g_OrderC);
		g_MainPanel.add(g_Hallc);
		g_MainPanel.add(g_SalesC);
		g_MainPanel.add(g_StockC);
		getContentPane().add(g_MainPanel);
		getContentPane().add(g_ChettingBtn);

		//버튼 액션리스너 사용
		g_ChettingBtn.addActionListener(this);
		g_OrderBtn.addActionListener(this);
		g_StockBtn.addActionListener(this);
		g_SalesBtn.addActionListener(this);
		g_HallButton.addActionListener(this);

		g_OrderBtn.doClick(); //버튼을 누르고 있는 기능 
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

		if (e.getSource() == g_OrderBtn) {// 본사 발주관리
			show(g_OrderC);
			hide(g_SalesC);
			hide(g_Hallc);
			hide(g_StockC);
		} else if (e.getSource() == g_StockBtn) {// 재고 입출고 관리
			show(g_StockC);
			hide(g_OrderC);
			hide(g_SalesC);
			hide(g_Hallc);
		} else if (e.getSource() == g_SalesBtn) {// 매출관리
			show(g_SalesC);
			hide(g_OrderC);
			hide(g_Hallc);
			hide(g_StockC);
		} else if (e.getSource() == g_HallButton) {// 홀 관리
			show(g_Hallc);
			hide(g_OrderC);
			hide(g_StockC);
			hide(g_SalesC);
		}

		if (e.getSource() == g_ChettingBtn) {
			// 채팅창 접속
			g_ClientFrame.setVisible(true);
		}

	}// actionPerformed 메서드 끝

}// 클래스 종료
