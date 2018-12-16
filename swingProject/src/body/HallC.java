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

public class HallC extends JPanel implements ActionListener, BodyHall{


	// 테이블 버튼
	private JButton jb1;// 1번테이블 버튼
	private JButton jb2;// 2번테이블 버튼
	private JButton jb3;// 3번테이블 버튼
	private JButton jb4;// 4번 테이블 버튼
	private JButton jb5;// 5번 테이블 버튼
	private JButton jb6;// 6번 테이블 버튼

	// 기본 테이블 모델
	private DefaultTableModel model1 = new DefaultTableModel(15, 2);// 1번테이블 표
	private DefaultTableModel model2 = new DefaultTableModel(15, 2);// 2번테이블 표
	private DefaultTableModel model3 = new DefaultTableModel(15, 2);// 3번테이블 표
	private DefaultTableModel model4 = new DefaultTableModel(15, 2);// 4번 테이블 표
	private DefaultTableModel model5 = new DefaultTableModel(15, 2);// 5번테이블 표
	private DefaultTableModel model6 = new DefaultTableModel(15, 2);// 6번테이블 표

	// 계산 버튼
	private JButton orderButton;// 주문하기 버튼
	private JButton button1;// 1번 테이블 계산 버튼
	private JButton button2;// 2번 테이블 계산 버튼
	private JButton button3;// 3번 테이블 계산 버튼
	private JButton button4;// 4번 테이블 계산 버튼
	private JButton button5;// 5번 테이블 계산 버튼
	private JButton button6;// 6번 테이블 계산 버튼
	// 주문 선택버튼
	private JButton selectButton;// 선택하기 버튼

	HashMap<JButton, DefaultTableModel> key = new HashMap<>();// 해쉬맵 사용법

	// 테이블내 표
	private DefaultTableModel orderModel = new DefaultTableModel(15, 2);//주문하기 표

	// 표가 들어갈 테이블
	private JTable listTable = new JTable(orderModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	// 주문하기 테이블
	private JTable table = new JTable(model1) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};// 1번테이블
	private JTable table1 = new JTable(model2) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};// 2번테이블
	private JTable table2 = new JTable(model3) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};// 3번테이블
	private JTable table3 = new JTable(model4) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};// 4번테이블
	private JTable table4 = new JTable(model5) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};// 5번테이블
	private JTable table5 = new JTable(model6) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};// 6번테이블

	// 스크롤바
	private JScrollPane scroll = new JScrollPane(listTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 주문하기
			// 스크롤바
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane scroll1 = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 1번테이블 스크롤바
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane scroll2 = new JScrollPane(table1, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 2번테이블
																											// 스크롤바
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane scroll3 = new JScrollPane(table2, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 3번테이블
																											// 스크롤바
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane scroll4 = new JScrollPane(table3, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 4번테이블
																											// 스크롤바
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane scroll5 = new JScrollPane(table4, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 5번테이블
																											// 스크롤바
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane scroll6 = new JScrollPane(table5, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 6번테이블
																											// 스크롤바
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 주문할 프레임
	private JFrame orderFrame;
	// 메뉴선택 콤보박스
	private JComboBox menuCombo;

	private JTextField textfiled;
	private JLabel menuLabel;
	private JLabel quantityLabel;

	// 각버튼의 주소값을 담아두는 변수
	Object temp = null;

	// 메뉴별로 가격을 담아둘 변수
	int chickenF = 0;
	int chickenH = 0;
	int chickenS = 0;
	int side = 0;
	int priceSum = 0;

	int count;

	String[] menu = new String[] { "후라이드", "양념", "간장", "음료" };// 메뉴 리스트

	public HallC() {// 생성자

		// 각 테이블 버튼 객체 생성
		jb1 = new JButton("1번 테이블");
		jb2 = new JButton("2번 테이블");
		jb3 = new JButton("3번 테이블");
		jb4 = new JButton("4번 테이블");
		jb5 = new JButton("5번 테이블");
		jb6 = new JButton("6번 테이블");
		// 각 테이블 버튼이랑 테이블 모델 짝 맞춰주기
		key.put(jb1, model1);
		key.put(jb2, model2);
		key.put(jb3, model3);
		key.put(jb4, model4);
		key.put(jb5, model5);
		key.put(jb6, model6);
		// 프레임 사이즈 및 레이아웃 설정
		setSize(790, 388);
		setLayout(null);
		setBackground(new Color(184,207,229));
		// 버튼 객체생성 및 위치지정
		orderButton = new JButton("주문하기");
		orderButton.setBounds(150, 210, 100, 30);
		// 라벨 객체생성 및 위치지정
		menuLabel = new JLabel("메뉴  ");
		menuLabel.setBounds(15, 10, 50, 30);
		quantityLabel = new JLabel("수량 ");
		quantityLabel.setBounds(155, 10, 50, 30);
		// 텍스트 필드 객체생성 및 위치지정
		textfiled = new JTextField();
		textfiled.setBounds(190, 10, 90, 30);
		// 콤보박스 객체생성, 메뉴리스트 넣기,위치지정
		menuCombo = new JComboBox(menu);
		menuCombo.setBounds(50, 10, 90, 30);
		// 버튼 객체생성 및 위치지정
		selectButton = new JButton("선택하기");
		selectButton.setBounds(30, 210, 100, 30);

		// 주문창 프레임 객체 생성 ,레이아웃설정,위치지정,프레임위에 올릴 컴포넌트 넣기
		orderFrame = new JFrame("주문하기");
		orderFrame.getContentPane().setLayout(null);
		orderFrame.setBounds(250, 295, 300, 300);
		orderFrame.getContentPane().add(scroll);
		orderFrame.getContentPane().add(menuCombo);
		orderFrame.getContentPane().add(textfiled);
		orderFrame.getContentPane().add(menuLabel);
		orderFrame.getContentPane().add(quantityLabel);
		orderFrame.getContentPane().add(orderButton);
		orderFrame.getContentPane().add(selectButton);
		orderFrame.setVisible(false);
		// 버튼 객체생성 및 위치 지정
		button1 = new JButton("계산");
		button1.setBounds(68, 166, 60, 24);
		button2 = new JButton("계산");
		button2.setBounds(312, 166, 60, 25);
		button3 = new JButton("계산");
		button3.setBounds(543, 166, 60, 24);
		button4 = new JButton("계산");
		button4.setBounds(71, 334, 60, 24);
		button5 = new JButton("계산");
		button5.setBounds(312, 336, 60, 25);
		button6 = new JButton("계산");
		button6.setBounds(543, 334, 60, 25);
		// 스크롤 위치지정
		scroll1.setBounds(58, 59, 144, 97);
		scroll2.setBounds(299, 59, 144, 97);
		scroll3.setBounds(530, 59, 144, 97);
		scroll4.setBounds(58, 235, 144, 97);
		scroll5.setBounds(299, 235, 144, 97);
		scroll6.setBounds(530, 235, 144, 97);
		scroll.setBounds(0, 50, 285, 145);

		// 버튼 위치 지정
		jb1.setBounds(91, 27, 97, 28);
		jb2.setBounds(325, 27, 97, 29);
		jb3.setBounds(559, 27, 97, 29);
		jb4.setBounds(71, 201, 97, 29);
		jb5.setBounds(325, 201, 97, 29);
		jb6.setBounds(559, 201, 97, 29);

		orderModel.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 주문하기 표 컬럼
		model1.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 1번테이블 컬럼
		model2.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 2번테이블 컬럼
		model3.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 3번테이블 컬럼
		model4.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 4번테이블 컬럼
		model5.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 5번테이블 컬럼
		model6.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 6번테이블 컬럼
		
		//각 테이블 내부 컬러 설정
		table.setBackground(Color.LIGHT_GRAY);
		table1.setBackground(Color.LIGHT_GRAY);
		table2.setBackground(Color.LIGHT_GRAY);
		table3.setBackground(Color.LIGHT_GRAY);
		table4.setBackground(Color.LIGHT_GRAY);
		table5.setBackground(Color.LIGHT_GRAY);

		// 각 컴포넌트 jpanel위에 올리기
		add(jb5);
		add(jb6);
		add(scroll1);
		add(jb4);
		add(scroll3);
		add(scroll2);
		add(jb3);
		add(scroll4);
		add(jb2);
		add(scroll5);
		add(jb1);
		add(scroll6);
		add(button1);
		add(button6);
		add(button2);
		add(button3);
		add(button4);
		add(button5);

		jb1.addActionListener(this);// 1번 테이블 선택시
		jb2.addActionListener(this);// 2번 테이블 선택시
		jb3.addActionListener(this);// 3번 테이블 선택시
		jb4.addActionListener(this);// 4번 테이블 선택시
		jb5.addActionListener(this);// 5번 테이블 선택시
		jb6.addActionListener(this);// 6번 테이블 선택시

		orderButton.addActionListener(this); // 주문하기 버튼
		button1.addActionListener(this);// 1번 테이블 계산
		button2.addActionListener(this);// 2번 테이블 계산
		button3.addActionListener(this);// 3번 테이블 계산
		button4.addActionListener(this);// 4번 테이블 계산
		button5.addActionListener(this);// 5번 테이블 계산
		button6.addActionListener(this);// 6번 테이블 계산
		selectButton.addActionListener(this);// 선택하기 버튼 누를때

		setVisible(false);
	}

	public void inputSales(DefaultTableModel tableMdel) {//주문목록을 계산해주는 메서드

		for (int i = 0; i >= 0; i++) {
			if (tableMdel.getValueAt(0, 0) == null) {//주문목록이 없으면 반복문을 빠져나가는 조건문
				count = 0;
				break;
			}
			//각 메뉴에따른 금액과 종합 금액을 계산해주는 조건문
			if (tableMdel.getValueAt(i - count, 0).equals("후라이드")) {
				chickenF += Integer.parseInt((String) tableMdel.getValueAt(i - count, 1)) * 20000;
				priceSum += chickenF;
			} else if (tableMdel.getValueAt(i - count, 0).equals("양념")) {
				chickenH += Integer.parseInt((String) tableMdel.getValueAt(i - count, 1)) * 20000;
				priceSum += chickenH;
			} else if (tableMdel.getValueAt(i - count, 0).equals("간장")) {
				chickenS += Integer.parseInt((String) tableMdel.getValueAt(i - count, 1)) * 20000;
				priceSum += chickenS;
			} else if (tableMdel.getValueAt(i - count, 0).equals("음료")) {
				side += Integer.parseInt((String) tableMdel.getValueAt(i - count, 1)) * 2000;
				priceSum += side;
			}
			tableMdel.removeRow(i - count);
			count++;
		}
		if (priceSum != 0) {//각메뉴 금액과 종합금액을 매출에 넣어주는 조건문
			B_SalesDAO.getInstance().menuInsert(BodyFrame.id, priceSum, chickenF, chickenH, chickenS, side);
			if (chickenF != 0) {
				B_StockDAO.getInstance().insertStock(BodyFrame.id, "닭", -chickenF / 20000);
			}
			if (chickenH != 0) {
				B_StockDAO.getInstance().insertStock(BodyFrame.id, "닭", -chickenH / 20000);
			}
			if (chickenS != 0) {
				B_StockDAO.getInstance().insertStock(BodyFrame.id, "닭", -chickenS / 20000);
			}
			priceSum = 0;
			chickenF = 0;
			chickenH = 0;
			chickenS = 0;
			side = 0;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {//액션이벤트 시작
		// System.out.println((String)combo.getSelectedItem());콤보박스 선택값 출력

		if (e.getSource() == jb1) {//1번테이블 버튼 눌렀을때
			orderFrame.setVisible(true);
			temp = e.getSource();
			orderFrame.setTitle("1번테이블");
		} else if (e.getSource() == jb2) {//2번테이블 버튼 눌렀을때
			orderFrame.setVisible(true);
			temp = e.getSource();
			orderFrame.setTitle("2번테이블");
		} else if (e.getSource() == jb3) {//3번테이블 버튼 눌렀을때
			orderFrame.setVisible(true);
			temp = e.getSource();
			orderFrame.setTitle("3번테이블");
		} else if (e.getSource() == jb4) {//4번테이블 버튼 눌렀을때
			orderFrame.setVisible(true);
			temp = e.getSource();
			orderFrame.setTitle("4번테이블");
		} else if (e.getSource() == jb5) {//5번테이블 버튼 눌렀을때
			orderFrame.setVisible(true);
			temp = e.getSource();
			orderFrame.setTitle("5번테이블");
		} else if (e.getSource() == jb6) {//6번테이블 버튼 눌렀을때
			orderFrame.setVisible(true);
			temp = e.getSource();
			orderFrame.setTitle("6번테이블");
		} else if (e.getSource() == orderButton) {// 주문하기 버튼을 눌렀을때 

			if (orderModel.getValueAt(0, 0) == null) {//주문목록이 없으면 계산이 불가능한 조건문
				JOptionPane.showMessageDialog(null, "주문목록이 없습니다.");
			} else {//선택한 테이블별로 주문한 목록이 전달되는 조건문
				while (true) {
					DefaultTableModel temp2 = key.get(temp);
					temp2.insertRow(0, new Object[] { orderModel.getValueAt(0, 0), orderModel.getValueAt(0, 1) });
					orderModel.removeRow(0);

					if (orderModel.getValueAt(0, 0) == null) {
						break;
					}
				}
			}

		} else if (e.getSource() == button1) {// 1번테이블 계산
			orderFrame.setVisible(false);
			inputSales(model1);
		} else if (e.getSource() == button2) {// 2번테이블 계산
			orderFrame.setVisible(false);
			inputSales(model2);
		} else if (e.getSource() == button3) {// 3번테이블 계산
			orderFrame.setVisible(false);
			inputSales(model3);
		} else if (e.getSource() == button4) {// 4번테이블 계산
			orderFrame.setVisible(false);
			inputSales(model4);
		} else if (e.getSource() == button5) {// 5번테이블 계산
			orderFrame.setVisible(false);
			inputSales(model5);
		} else if (e.getSource() == button6) {// 6번테이블 계산
			orderFrame.setVisible(false);
			inputSales(model6);
		} else if (e.getSource() == selectButton) {// 선택하기 버튼 누를시
			if (!(textfiled.getText().equals(""))) {
				orderModel.insertRow(0, new Object[] { (String) menuCombo.getSelectedItem(), textfiled.getText() });
			} else {
				JOptionPane.showMessageDialog(null, "수량을 입력해주세요");
			}
		}
	}

	@Override
	public void show(BBQBody bbqBody) {
		((Component) bbqBody).setVisible(true);
	}

	@Override
	public void hide(BBQBody bbqBody) {
		((Component) bbqBody).setVisible(false);
	}

	
}
