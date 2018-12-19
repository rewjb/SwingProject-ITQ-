package body;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.temporal.JulianFields;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import DTO_DAO.B_OrderDAO;
import DTO_DAO.B_SalesDAO;
import DTO_DAO.B_StockDAO;
import inter.BBQBody;
import inter.BodyHall;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.ItemSelectable;

import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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

public class B_HallC extends JPanel implements ActionListener, BodyHall {

	// 테이블 버튼
	private JButton g_Jb1;// 1번테이블 버튼
	private JButton g_Jb2;// 2번테이블 버튼
	private JButton g_Jb3;// 3번테이블 버튼
	private JButton g_Jb4;// 4번 테이블 버튼
	private JButton g_Jb5;// 5번 테이블 버튼
	private JButton g_Jb6;// 6번 테이블 버튼

	// 기본 테이블 모델
	private DefaultTableModel g_Model1 = new DefaultTableModel(15, 2);// 1번테이블 표
	private DefaultTableModel g_Model2 = new DefaultTableModel(15, 2);// 2번테이블 표
	private DefaultTableModel g_Model3 = new DefaultTableModel(15, 2);// 3번테이블 표
	private DefaultTableModel g_Model4 = new DefaultTableModel(15, 2);// 4번 테이블 표
	private DefaultTableModel g_Model5 = new DefaultTableModel(15, 2);// 5번테이블 표
	private DefaultTableModel g_Model6 = new DefaultTableModel(15, 2);// 6번테이블 표

	// 계산 버튼
	private JButton g_OrderButton;// 주문하기 버튼
	private JButton g_Button1;// 1번 테이블 계산 버튼
	private JButton g_Button2;// 2번 테이블 계산 버튼
	private JButton g_Button3;// 3번 테이블 계산 버튼
	private JButton g_Button4;// 4번 테이블 계산 버튼
	private JButton g_Button5;// 5번 테이블 계산 버튼
	private JButton g_Button6;// 6번 테이블 계산 버튼
	// 주문 선택버튼
	private JButton g_SelectButton;// 선택하기 버튼

	HashMap<JButton, DefaultTableModel> g_Key = new HashMap<>();// 해쉬맵 사용법

	// 테이블내 표
	private DefaultTableModel g_OrderModel = new DefaultTableModel(15, 2);// 주문하기 표

	// 주문하기 테이블
	private JTable g_ListTable = new JTable(g_OrderModel) {
		public boolean isCellEditable(int row,int column){return false;};};

	// 1번테이블
	private JTable g_Table = new JTable(g_Model1) {
		public boolean isCellEditable(int row,int column){return false;};};

	// 2번테이블
	private JTable g_Table1 = new JTable(g_Model2) {
		public boolean isCellEditable(int row,int column){return false;};};

	// 3번테이블
	private JTable g_Table2 = new JTable(g_Model3) {
		public boolean isCellEditable(int row,int column){return false;};};

	// 4번테이블
	private JTable g_Table3 = new JTable(g_Model4) {
		public boolean isCellEditable(int row,int column){return false;};};

	// 5번테이블
	private JTable g_Table4 = new JTable(g_Model5) {
		public boolean isCellEditable(int row,int column){return false;};};

	// 6번테이블
	private JTable g_Table5 = new JTable(g_Model6) {
		public boolean isCellEditable(int row,int column){return false;};};

	// 스크롤바
	private JScrollPane g_Scroll = new JScrollPane(g_ListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 주문하기
																													// 스크롤바
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	private JScrollPane g_Scroll1 = new JScrollPane(g_Table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 1번테이블
																												// 스크롤바
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	private JScrollPane g_Scroll2 = new JScrollPane(g_Table1, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 2번테이블
																												// 스크롤바
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	private JScrollPane g_Scroll3 = new JScrollPane(g_Table2, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 3번테이블
																												// 스크롤바
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	private JScrollPane g_Scroll4 = new JScrollPane(g_Table3, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 4번테이블
																												// 스크롤바
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	private JScrollPane g_Scroll5 = new JScrollPane(g_Table4, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 5번테이블
																												// 스크롤바
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	private JScrollPane g_Scroll6 = new JScrollPane(g_Table5, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 6번테이블
																												// 스크롤바
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 주문할 프레임
	private JFrame g_OrderFrame;
	// 메뉴선택 콤보박스
	private JComboBox g_MenuCombo;
	// 메뉴 수량 텍스트 필드
	private JTextField g_MenuQuantityTextField;
	// 메뉴 라벨
	private JLabel g_MenuLabel;
	// 수량 라벨
	private JLabel g_QuantityLabel;

	// 각버튼의 주소값을 담아두는 변수
	Object g_Temp = null;

	// 메뉴별로 가격을 담아둘 변수
	int g_ChickenF = 0;
	int g_ChickenH = 0;
	int g_ChickenS = 0;
	int g_Side = 0;
	int g_PriceSum = 0;

	int g_Count;

	String[] g_Menu = new String[] { "후라이드", "양념", "간장", "음료" };// 메뉴 리스트

	public B_HallC() {// 생성자

		// 각 테이블 버튼 객체 생성
		g_Jb1 = new JButton("1번 테이블");
		g_Jb2 = new JButton("2번 테이블");
		g_Jb3 = new JButton("3번 테이블");
		g_Jb4 = new JButton("4번 테이블");
		g_Jb5 = new JButton("5번 테이블");
		g_Jb6 = new JButton("6번 테이블");
		// 각 테이블 버튼이랑 테이블 모델 짝 맞춰주기
		g_Key.put(g_Jb1, g_Model1);
		g_Key.put(g_Jb2, g_Model2);
		g_Key.put(g_Jb3, g_Model3);
		g_Key.put(g_Jb4, g_Model4);
		g_Key.put(g_Jb5, g_Model5);
		g_Key.put(g_Jb6, g_Model6);
		// 프레임 사이즈 및 레이아웃 설정
		setSize(790, 388);
		setLayout(null);
		setBackground(new Color(184, 207, 229));
		// 버튼 객체생성 및 위치지정
		g_OrderButton = new JButton("주문하기");
		g_OrderButton.setBounds(150, 210, 100, 30);
		// 라벨 객체생성 및 위치지정
		g_MenuLabel = new JLabel("메뉴  ");
		g_MenuLabel.setBounds(15, 10, 50, 30);
		g_QuantityLabel = new JLabel("수량 ");
		g_QuantityLabel.setBounds(155, 10, 50, 30);
		// 텍스트 필드 객체생성 및 위치지정
		g_MenuQuantityTextField = new JTextField();
		g_MenuQuantityTextField.setBounds(190, 10, 90, 30);
		// 콤보박스 객체생성, 메뉴리스트 넣기,위치지정
		g_MenuCombo = new JComboBox(g_Menu);
		g_MenuCombo.setBounds(50, 10, 90, 30);
		// 버튼 객체생성 및 위치지정
		g_SelectButton = new JButton("선택하기");
		g_SelectButton.setBounds(30, 210, 100, 30);

		// 주문창 프레임 객체 생성 ,레이아웃설정,위치지정,프레임위에 올릴 컴포넌트 넣기
		g_OrderFrame = new JFrame("주문하기");
		g_OrderFrame.getContentPane().setLayout(null);
		g_OrderFrame.setBounds(250, 295, 300, 300);
		g_OrderFrame.getContentPane().add(g_Scroll);
		g_OrderFrame.getContentPane().add(g_MenuCombo);
		g_OrderFrame.getContentPane().add(g_MenuQuantityTextField);
		g_OrderFrame.getContentPane().add(g_MenuLabel);
		g_OrderFrame.getContentPane().add(g_QuantityLabel);
		g_OrderFrame.getContentPane().add(g_OrderButton);
		g_OrderFrame.getContentPane().add(g_SelectButton);
		g_OrderFrame.setVisible(false);
		// 버튼 객체생성 및 위치 지정
		g_Button1 = new JButton("계산");
		g_Button1.setBounds(68, 166, 60, 24);
		g_Button2 = new JButton("계산");
		g_Button2.setBounds(312, 166, 60, 25);
		g_Button3 = new JButton("계산");
		g_Button3.setBounds(543, 166, 60, 24);
		g_Button4 = new JButton("계산");
		g_Button4.setBounds(71, 334, 60, 24);
		g_Button5 = new JButton("계산");
		g_Button5.setBounds(312, 336, 60, 25);
		g_Button6 = new JButton("계산");
		g_Button6.setBounds(543, 334, 60, 25);
		// 스크롤 위치지정
		g_Scroll1.setBounds(58, 59, 144, 97);
		g_Scroll2.setBounds(299, 59, 144, 97);
		g_Scroll3.setBounds(530, 59, 144, 97);
		g_Scroll4.setBounds(58, 235, 144, 97);
		g_Scroll5.setBounds(299, 235, 144, 97);
		g_Scroll6.setBounds(530, 235, 144, 97);
		g_Scroll.setBounds(0, 50, 285, 145);

		// 버튼 위치 지정
		g_Jb1.setBounds(91, 27, 97, 28);
		g_Jb2.setBounds(325, 27, 97, 29);
		g_Jb3.setBounds(559, 27, 97, 29);
		g_Jb4.setBounds(71, 201, 97, 29);
		g_Jb5.setBounds(325, 201, 97, 29);
		g_Jb6.setBounds(559, 201, 97, 29);

		g_OrderModel.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 주문하기 표 컬럼
		g_Model1.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 1번테이블 컬럼
		g_Model2.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 2번테이블 컬럼
		g_Model3.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 3번테이블 컬럼
		g_Model4.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 4번테이블 컬럼
		g_Model5.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 5번테이블 컬럼
		g_Model6.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 6번테이블 컬럼

		g_Table.setBackground(Color.LIGHT_GRAY);
		g_Table1.setBackground(Color.LIGHT_GRAY);
		g_Table2.setBackground(Color.LIGHT_GRAY);
		g_Table3.setBackground(Color.LIGHT_GRAY);
		g_Table4.setBackground(Color.LIGHT_GRAY);
		g_Table5.setBackground(Color.LIGHT_GRAY);

		// 각 컴포넌트 jpanel위에 올리기
		add(g_Jb5);
		add(g_Jb6);
		add(g_Scroll1);
		add(g_Jb4);
		add(g_Scroll3);
		add(g_Scroll2);
		add(g_Jb3);
		add(g_Scroll4);
		add(g_Jb2);
		add(g_Scroll5);
		add(g_Jb1);
		add(g_Scroll6);
		add(g_Button1);
		add(g_Button6);
		add(g_Button2);
		add(g_Button3);
		add(g_Button4);
		add(g_Button5);

		g_Jb1.addActionListener(this);// 1번 테이블 선택시
		g_Jb2.addActionListener(this);// 2번 테이블 선택시
		g_Jb3.addActionListener(this);// 3번 테이블 선택시
		g_Jb4.addActionListener(this);// 4번 테이블 선택시
		g_Jb5.addActionListener(this);// 5번 테이블 선택시
		g_Jb6.addActionListener(this);// 6번 테이블 선택시

		g_OrderButton.addActionListener(this); // 주문하기 버튼
		g_Button1.addActionListener(this);// 1번 테이블 계산
		g_Button2.addActionListener(this);// 2번 테이블 계산
		g_Button3.addActionListener(this);// 3번 테이블 계산
		g_Button4.addActionListener(this);// 4번 테이블 계산
		g_Button5.addActionListener(this);// 5번 테이블 계산
		g_Button6.addActionListener(this);// 6번 테이블 계산
		g_SelectButton.addActionListener(this);// 선택하기 버튼 누를때

		setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// System.out.println((String)combo.getSelectedItem());콤보박스 선택값 출력

		if (e.getSource() == g_Jb1) {
			g_OrderFrame.setVisible(true);
			g_Temp = e.getSource();
			g_OrderFrame.setTitle("1번테이블");
		} else if (e.getSource() == g_Jb2) {
			g_OrderFrame.setVisible(true);
			g_Temp = e.getSource();
			g_OrderFrame.setTitle("2번테이블");
		} else if (e.getSource() == g_Jb3) {
			g_OrderFrame.setVisible(true);
			g_Temp = e.getSource();
			g_OrderFrame.setTitle("3번테이블");
		} else if (e.getSource() == g_Jb4) {
			g_OrderFrame.setVisible(true);
			g_Temp = e.getSource();
			g_OrderFrame.setTitle("4번테이블");
		} else if (e.getSource() == g_Jb5) {
			g_OrderFrame.setVisible(true);
			g_Temp = e.getSource();
			g_OrderFrame.setTitle("5번테이블");
		} else if (e.getSource() == g_Jb6) {
			g_OrderFrame.setVisible(true);
			g_Temp = e.getSource();
			g_OrderFrame.setTitle("6번테이블");
		} else if (e.getSource() == g_OrderButton) {// 주문하기

			if (g_OrderModel.getValueAt(0, 0) == null) {
				JOptionPane.showMessageDialog(null, "주문목록이 없습니다.");
			} else {
				while (true) {
					DefaultTableModel temp2 = g_Key.get(g_Temp);
					temp2.insertRow(0, new Object[] { g_OrderModel.getValueAt(0, 0), g_OrderModel.getValueAt(0, 1) });
					g_OrderModel.removeRow(0);

					if (g_OrderModel.getValueAt(0, 0) == null) {
						break;
					}
				}
			}

		} else if (e.getSource() == g_Button1) {// 1번테이블 계산
			g_OrderFrame.setVisible(false);
			inputSales(g_Model1);
		} else if (e.getSource() == g_Button2) {// 2번테이블 계산
			g_OrderFrame.setVisible(false);
			inputSales(g_Model2);
		} else if (e.getSource() == g_Button3) {// 3번테이블 계산
			g_OrderFrame.setVisible(false);
			inputSales(g_Model3);
		} else if (e.getSource() == g_Button4) {// 4번테이블 계산
			g_OrderFrame.setVisible(false);
			inputSales(g_Model4);
		} else if (e.getSource() == g_Button5) {// 5번테이블 계산
			g_OrderFrame.setVisible(false);
			inputSales(g_Model5);
		} else if (e.getSource() == g_Button6) {// 6번테이블 계산
			g_OrderFrame.setVisible(false);
			inputSales(g_Model6);
		} else if (e.getSource() == g_SelectButton) {// 선택하기 버튼 누를시

			if (!(g_MenuQuantityTextField.getText().equals(""))) {// 수량을 쓰기만 했으면 선택한 메뉴를 주문테이블에 담아준다.
				g_OrderModel.insertRow(0,
						new Object[] { (String) g_MenuCombo.getSelectedItem(), g_MenuQuantityTextField.getText() });
			} else {// 수량을 입력하지않으면 선택 불가능
				JOptionPane.showMessageDialog(null, "수량을 입력해주세요");
			}
		}
	}

	@Override
	public void show(BBQBody bbqBody) {// 구현한 인터페이스 show 메서드 오버라이딩
		((Component) bbqBody).setVisible(true);
	}

	@Override
	public void hide(BBQBody bbqBody) {// 구현한 인터페이스 hide 메서드 오버라이딩
		((Component) bbqBody).setVisible(false);
	}

	@Override
	public void inputSales(DefaultTableModel tableMdel) {
		for (int i = 0; i >= 0; i++) {
			if (tableMdel.getValueAt(0, 0) == null) {
				g_Count = 0;
				break;
			}
			if (tableMdel.getValueAt(i - g_Count, 0).equals("후라이드")) {
				g_ChickenF += Integer.parseInt((String) tableMdel.getValueAt(i - g_Count, 1)) * 20000;
				g_PriceSum += g_ChickenF;
			} else if (tableMdel.getValueAt(i - g_Count, 0).equals("양념")) {
				g_ChickenH += Integer.parseInt((String) tableMdel.getValueAt(i - g_Count, 1)) * 20000;
				g_PriceSum += g_ChickenH;
			} else if (tableMdel.getValueAt(i - g_Count, 0).equals("간장")) {
				g_ChickenS += Integer.parseInt((String) tableMdel.getValueAt(i - g_Count, 1)) * 20000;
				g_PriceSum += g_ChickenS;
			} else if (tableMdel.getValueAt(i - g_Count, 0).equals("음료")) {
				g_Side += Integer.parseInt((String) tableMdel.getValueAt(i - g_Count, 1)) * 2000;
				g_PriceSum += g_Side;
			}
			tableMdel.removeRow(i - g_Count);
			g_Count++;
		}
		if (g_PriceSum != 0) {
			B_SalesDAO.getInstance().menuInsert(B_Frame.st_G_id, g_PriceSum, g_ChickenF, g_ChickenH, g_ChickenS,
					g_Side);
			if (g_ChickenF != 0) {
				B_StockDAO.getInstance().insertStock(B_Frame.st_G_id, "닭", -g_ChickenF / 20000);
			}
			if (g_ChickenH != 0) {
				B_StockDAO.getInstance().insertStock(B_Frame.st_G_id, "닭", -g_ChickenH / 20000);
			}
			if (g_ChickenS != 0) {
				B_StockDAO.getInstance().insertStock(B_Frame.st_G_id, "닭", -g_ChickenS / 20000);
			}
			g_PriceSum = 0;
			g_ChickenF = 0;
			g_ChickenH = 0;
			g_ChickenS = 0;
			g_Side = 0;
		}
	}
	

}// 클래스 끝

