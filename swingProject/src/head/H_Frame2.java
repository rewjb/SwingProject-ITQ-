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
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import inter.BBQHead;

import javax.swing.JPanel;

/*
 * wonHn 2018-12-18
 * 제 컴퓨터에서 주빈씨 작업한 페이지가 연결이 안돼는 관계로 확인용 H_Frame 확인용 메인을 따로 만들었습니다.
 * 확인 이후에는 지우겠습니다.
 */

public class H_Frame2 extends JFrame implements BBQHead, ActionListener {

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
	private H_Vender h_vender = new H_Vender();
	private H_Franchise h_franchise = new H_Franchise();

	private ImageIcon img = new ImageIcon("img/logo.png");
	// 회사 이미지 아이콘
	private JLabel logoLabel = new JLabel(img);
	// 회사 이미지 아이콘을 넣은 라벨!

	private JLabel serviceInfo = new JLabel("전산팀 연락처 : 02-xxx-xxxx");
	// 연락처 라벨

	int xpos;
	int ypos;
	// 화면을 중앙에 나타나게 하기 위한 x와 y 좌표

	private Dimension point = Toolkit.getDefaultToolkit().getScreenSize();
	// 전체 스크린 사이즈를 갖는 객체 생성

	private H_ChattingFrame serverFrame = new H_ChattingFrame();
	// 채팅 프로그램 실행 객체

	private Color color = new Color(128, 144, 160);
	// 모든 버튼의 색상을 지정할 객체

	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image frameimg = toolkit.getImage("img/회사 프레임 아이콘.jpg");

	public H_Frame2() {  

		setTitle("JVQ 관리자  계정");
		setIconImage(frameimg);

		F_OrderCheckBtn.setBackground(color);
		OrderBtn.setBackground(color);
		StockBtn.setBackground(color);
		VenderBtn.setBackground(color);
		FranchiseBtn.setBackground(color);
		SalesBtn.setBackground(color);
		chattingBtn.setBackground(color);
		// 버튼의 색상을 일괄적으로 변경

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
		// 화면을 구성하는 각조 컴포넌트의 사이즈 지정

		F_OrderCheckBtn.setBorder(null);
		OrderBtn.setBorder(null);
		StockBtn.setBorder(null);
		VenderBtn.setBorder(null);
		FranchiseBtn.setBorder(null);
		SalesBtn.setBorder(null);

		mainPanel.setBounds(12, 83, 770, 368);

		logoLabel.setBounds(10, 10, 107, 46);

		serverFrame.setLocationRelativeTo(this);

		mainPanel.add(h_vender);
		mainPanel.add(h_franchise);
		// 메인 판넬에 각 서비스 판넬 추가

		btnGroup.add(F_OrderCheckBtn);
		btnGroup.add(OrderBtn);
		btnGroup.add(StockBtn);
		btnGroup.add(VenderBtn);
		btnGroup.add(FranchiseBtn);
		btnGroup.add(SalesBtn);
		// 토글버튼 전부다 넣기

		getContentPane().add(F_OrderCheckBtn);
		getContentPane().add(OrderBtn);
		getContentPane().add(StockBtn);
		getContentPane().add(VenderBtn);
		getContentPane().add(FranchiseBtn);
		getContentPane().add(SalesBtn);
		getContentPane().add(logoLabel);
		getContentPane().add(chattingBtn);
		getContentPane().add(serviceInfo);
		// 전체 화면에 각종 서비스 버튼 및 이미지 라벨 추가

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
		
		getContentPane().setBackground(new Color(202, 208, 213));

		setSize(800, 500);
		xpos = (int) (point.getWidth() / 2 - getWidth() / 2);
		ypos = (int) (point.getHeight() / 2 - getHeight() / 2);
		setLocation(xpos, ypos);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		// 사이즈 , 레이아웃 및 각종 설정

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
			hide(h_vender);
			hide(h_franchise);
		} else if (e.getSource() == OrderBtn) {// 본사 발주관리
			hide(h_vender);
			hide(h_franchise);
		} else if (e.getSource() == StockBtn) {// 재고 입출고 관리
			hide(h_vender);
			hide(h_franchise);
		} else if (e.getSource() == VenderBtn) {// 업체관리
			show(h_vender);
			hide(h_franchise);
		} else if (e.getSource() == FranchiseBtn) {// 가맹점 관리
			show(h_franchise);
			hide(h_vender);
		} else if (e.getSource() == SalesBtn) {// 매출관리
			hide(h_vender);
			hide(h_franchise);
		}

		if (e.getSource() == chattingBtn) {// 채팅서버 관리
			show(serverFrame);
		}

	}// actionPerformed 메서드 끝

	public static void main(String[] args) {
		new H_Frame2();
	}

}// 클래스 종료
