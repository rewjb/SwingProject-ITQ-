package joe;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.temporal.JulianFields;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import DTO_DAO.B_OrderDAO;
import DTO_DAO.B_SalesDAO;
import inter.BBQBody;
import inter.BodyHall;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class HallC extends JPanel implements ActionListener, BodyHall {
	String id = "1";// 로그인시 넘어올 아이디 임시 테스트용

	// 테이블 버튼
	private JButton jb;// 1번테이블 버튼
	private JButton jb1;// 2번테이블 버튼
	private JButton jb2;// 3번테이블 버튼
	private JButton jb3;// 4번 테이블 버튼
	private JButton jb4;// 5번 테이블 버튼
	private JButton jb5;// 6번 테이블 버튼
	

	private DefaultTableModel model1 = new DefaultTableModel(15, 2);// 1번테이블 표
	private DefaultTableModel model2 = new DefaultTableModel(15, 2);// 2번테이블 표
	private DefaultTableModel model3 = new DefaultTableModel(15, 2);// 3번테이블 표
	private DefaultTableModel model4 = new DefaultTableModel(15, 2);// 4번 테이블 표
	private DefaultTableModel model5 = new DefaultTableModel(15, 2);// 5번테이블 표
	private DefaultTableModel model6 = new DefaultTableModel(15, 2);// 6번테이블 표
	
	// 계산 버튼
	private JButton button;//주문하기 버튼
	private JButton button1;//1번 테이블 계산 버튼
	private JButton button2;//2번 테이블 계산 버튼
	private JButton button3;//3번 테이블 계산 버튼
	private JButton button4;//4번 테이블 계산 버튼
	private JButton button5;//5번 테이블 계산 버튼
	private JButton button6;//6번 테이블 계산 버튼
	// 주문 선택버튼
	private JButton button7;//선택하기 버튼                    
	// 버튼 그룹
	//private ButtonGroup bg;

	HashMap<JButton, DefaultTableModel> key = new HashMap<>();// 해쉬맵 사용법

	// 테이블내 표
	private DefaultTableModel model = new DefaultTableModel(15, 2);

	// 표가 들어갈 테이블
	private JTable listTable = new JTable(model){
	      public boolean isCellEditable(int row, int column) {
	          return false;
	       };
	    };
	// 주문하기 테이블
	private JTable table = new JTable(model1){
	      public boolean isCellEditable(int row, int column) {
	          return false;
	       };
	    };// 1번테이블
	private JTable table1 = new JTable(model2){
	      public boolean isCellEditable(int row, int column) {
	          return false;
	       };
	    };// 2번테이블
	private JTable table2 = new JTable(model3){
	      public boolean isCellEditable(int row, int column) {
	          return false;
	       };
	    };// 3번테이블
	private JTable table3 = new JTable(model4){
	      public boolean isCellEditable(int row, int column) {
	          return false;
	       };
	    };// 4번테이블
	private JTable table4 = new JTable(model5){
	      public boolean isCellEditable(int row, int column) {
	          return false;
	       };
	    };// 5번테이블
	private JTable table5 = new JTable(model6){
	      public boolean isCellEditable(int row, int column) {
	          return false;
	       };
	    };// 6번테이블

	// 스크롤바
	private JScrollPane scroll = new JScrollPane(listTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 주문하기
																												// 테이블
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
	private JFrame jframe;
	// 메뉴선택 콤보박스
	private JComboBox combo;

	private JTextField textfiled;
	private JLabel jl;
	private JLabel jl_1;
	
	//각버튼의 주소값을 담아두는 변수
	Object temp = null;

	//메뉴별로 가격을 담아둘 변수
	

	
	String[] menu = new String[] { "후라이드", "양념", "간장", "음료" };

	public HallC() {

		jb = new JButton("1번 테이블");
		jb1 = new JButton("2번 테이블");
		jb2 = new JButton("3번 테이블");
		jb3 = new JButton("4번 테이블");
		jb4 = new JButton("5번 테이블");
		jb5 = new JButton("6번 테이블");

		key.put(jb, model1);
		key.put(jb1, model2);
		key.put(jb2, model3);
		key.put(jb3, model4);
		key.put(jb4, model5);
		key.put(jb5, model6);

		setSize(790, 388);
		setLayout(null);

		button = new JButton("주문하기");
		button.setBounds(150, 210, 100, 30);

		jl = new JLabel("메뉴  ");
		jl.setBounds(15, 10, 50, 30);
		jl_1 = new JLabel("수량 ");
		jl_1.setBounds(155, 10, 50, 30);

		textfiled = new JTextField();
		textfiled.setBounds(190, 10, 90, 30);

		combo = new JComboBox(menu);
		combo.setBounds(50, 10, 90, 30);
		button7 = new JButton("선택하기");
		button7.setBounds(30, 210, 100, 30);

		// 
		jframe = new JFrame("주문하기");
		jframe.getContentPane().setLayout(null);
		jframe.setBounds(250, 295, 300, 300);
		jframe.getContentPane().add(scroll);
		jframe.getContentPane().add(combo);
		jframe.getContentPane().add(textfiled);
		jframe.getContentPane().add(jl);
		jframe.getContentPane().add(jl_1);
		jframe.getContentPane().add(button);
		jframe.getContentPane().add(button7);

		jframe.setVisible(false);

		//bg = new ButtonGroup();

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

		scroll1.setBounds(58, 59, 144, 97);
		scroll2.setBounds(299, 59, 144, 97);
		scroll3.setBounds(530, 59, 144, 97);
		scroll4.setBounds(58, 235, 144, 97);
		scroll5.setBounds(299, 235, 144, 97);
		scroll6.setBounds(530, 235, 144, 97);

		scroll.setBounds(0, 50, 285, 145);
		jb.setBounds(91, 27, 97, 28);
		jb1.setBounds(325, 27, 97, 29);
		jb2.setBounds(559, 27, 97, 29);
		jb3.setBounds(71, 201, 97, 29);
		jb4.setBounds(325, 201, 97, 29);
		jb5.setBounds(559, 201, 97, 29);

		model.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 주문하기 표 컬럼
		model1.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 1번테이블 컬럼
		model2.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 2번테이블 컬럼
		model3.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 3번테이블 컬럼
		model4.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 4번테이블 컬럼
		model5.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 5번테이블 컬럼
		model6.setColumnIdentifiers(new Object[] { "메뉴", "수량" });// 6번테이블 컬럼

		//bg.add(jb);
		//bg.add(jb1);
		add(jb4);
		add(jb5);
		add(scroll1);
		//bg.add(jb2);
		add(jb3);
		add(scroll3);
		add(scroll2);
		//bg.add(jb3);
		add(jb2);
		add(scroll4);
		//bg.add(jb4);
		add(jb1);
		add(scroll5);
		//bg.add(jb5);
		add(jb);
		add(scroll6);
		add(button1);
		add(button6);
		add(button2);
		add(button3);
		add(button4);
		add(button5);

		jb.addActionListener(this);// 1번 테이블 선택시
		jb1.addActionListener(this);// 2번 테이블 선택시
		jb2.addActionListener(this);// 3번 테이블 선택시
		jb3.addActionListener(this);// 4번 테이블 선택시
		jb4.addActionListener(this);// 5번 테이블 선택시
		jb5.addActionListener(this);// 6번 테이블 선택시

		button.addActionListener(this); // 주문하기 버튼
		button1.addActionListener(this);// 1번 테이블 계산
		button2.addActionListener(this);// 2번 테이블 계산
		button3.addActionListener(this);// 3번 테이블 계산
		button4.addActionListener(this);// 4번 테이블 계산
		button5.addActionListener(this);// 5번 테이블 계산
		button6.addActionListener(this);// 6번 테이블 계산
		button7.addActionListener(this);// 선택하기 버튼 누를때

		setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// System.out.println((String)combo.getSelectedItem());콤보박스 선택값 출력
		int chickenF = 0;
		int chickenH = 0;
		int chickenS = 0;
		int side = 0;
		int PriceSum = chickenS + chickenH + chickenS + side;
		
		int count=0;
		
		
		
		if (e.getSource() == jb) {
			jframe.setVisible(true);
			temp = e.getSource();
			jframe.setTitle("1번테이블");
		} else if (e.getSource() == jb1) {
			jframe.setVisible(true);
			temp = e.getSource();
			jframe.setTitle("2번테이블");
		} else if (e.getSource() == jb2) {
			jframe.setVisible(true);
			temp = e.getSource();
			jframe.setTitle("3번테이블");
		} else if (e.getSource() == jb3) {
			jframe.setVisible(true);
			temp = e.getSource();
			jframe.setTitle("4번테이블");
		} else if (e.getSource() == jb4) {
			jframe.setVisible(true);
			temp = e.getSource();
			jframe.setTitle("5번테이블");
		} else if (e.getSource() == jb5) {
			jframe.setVisible(true);
			temp = e.getSource();
			jframe.setTitle("6번테이블");
		} else if (e.getSource() == button) {// 주문하기

			if (model.getValueAt(0, 0)==null) {
				JOptionPane.showMessageDialog(null, "주문목록이 없습니다.");
			}else {
				while (true) {

					DefaultTableModel temp2 = key.get(temp);
					temp2.insertRow(0, new Object[] { model.getValueAt(0, 0), model.getValueAt(0, 1) });
					model.removeRow(0);

					
					if (model.getValueAt(0, 0) == null) {
						break;
					}
					
				}
			}

		} else if (e.getSource() == button1) {// 1번테이블 계산
			for (int i = 0; i >= 0; i++) {
				if (model1.getValueAt(0, 0) == null) {
					count = 0;
					break;
				}
				int cPrice = Integer.parseInt((String) model1.getValueAt(i-count, 1)) * 20000;
				int sPrice = Integer.parseInt((String) model1.getValueAt(i-count, 1)) * 2000;

				if (model1.getValueAt(i-count, 0).equals("후라이드")) {
					chickenF += cPrice;
				} else if (model1.getValueAt(i-count, 0).equals("양념")) {
					chickenH += cPrice;
				} else if (model1.getValueAt(i-count, 0).equals("간장")) {
					chickenS += cPrice;
				} else if (model1.getValueAt(i-count, 0).equals("음료")) {
					side += sPrice;
				}
				model1.removeRow(i-count);
				count++;
			}
			if(PriceSum!=0) {
			B_SalesDAO.getInstance().menuInsert(id, PriceSum, chickenF, chickenH, chickenS, side);
			}
		} else if (e.getSource() == button2) {// 2번테이블 계산
			for (int i = 0; i >= 0; i++) {
				if (model2.getValueAt(0, 0) == null) {
					count = 0;
					break;
				}

				if (model2.getValueAt(i-count, 0).equals("후라이드")) {
					chickenF += Integer.parseInt((String) model2.getValueAt(i-count, 1)) * 20000;
				} else if (model2.getValueAt(i-count, 0).equals("양념")) {
					chickenH += Integer.parseInt((String) model2.getValueAt(i-count, 1)) * 20000;
				} else if (model2.getValueAt(i-count, 0).equals("간장")) {
					chickenS += Integer.parseInt((String) model2.getValueAt(i-count, 1)) * 20000;
				} else if (model2.getValueAt(i-count, 0).equals("음료")) {
					side += Integer.parseInt((String) model2.getValueAt(i-count, 1)) * 2000;
				}
				model2.removeRow(i-count);
				count++;
			}
			if(PriceSum!=0) {
				B_SalesDAO.getInstance().menuInsert(id, PriceSum, chickenF, chickenH, chickenS, side);
				}
		} else if (e.getSource() == button3) {// 3번테이블 계산
			for (int i = 0; i >= 0; i++) {
				if (model3.getValueAt(0, 0) == null) {
					count = 0;
					break;
				}

				if (model3.getValueAt(i-count, 0).equals("후라이드")) {
					chickenF += Integer.parseInt((String) model3.getValueAt(i-count, 1)) * 20000;
				} else if (model3.getValueAt(i-count, 0).equals("양념")) {
					chickenH += Integer.parseInt((String) model3.getValueAt(i-count, 1)) * 20000;
				} else if (model3.getValueAt(i-count, 0).equals("간장")) {
					chickenS += Integer.parseInt((String) model3.getValueAt(i-count, 1)) * 20000;
				} else if (model3.getValueAt(i-count, 0).equals("음료")) {
					side += Integer.parseInt((String) model3.getValueAt(i-count, 1)) * 2000;
				}
				model3.removeRow(i-count);
				count++;
			}
			if(PriceSum!=0) {
				B_SalesDAO.getInstance().menuInsert(id, PriceSum, chickenF, chickenH, chickenS, side);
				}
		} else if (e.getSource() == button4) {// 4번테이블 계산
			for (int i = 0; i >= 0; i++) {
				if (model4.getValueAt(0, 0) == null) {
					count = 0;
					break;
				}

				if (model4.getValueAt(i-count, 0).equals("후라이드")) {
					chickenF += Integer.parseInt((String) model4.getValueAt(i-count, 1)) * 20000;
				} else if (model4.getValueAt(i-count, 0).equals("양념")) {
					chickenH += Integer.parseInt((String) model4.getValueAt(i-count, 1)) * 20000;
				} else if (model4.getValueAt(i-count, 0).equals("간장")) {
					chickenS += Integer.parseInt((String) model4.getValueAt(i-count, 1)) * 20000;
				} else if (model4.getValueAt(i-count, 0).equals("음료")) {
					side += Integer.parseInt((String) model4.getValueAt(i-count, 1)) * 2000;
				}
				model4.removeRow(i-count);
				count++;
			}
			if(PriceSum!=0) {
				B_SalesDAO.getInstance().menuInsert(id, PriceSum, chickenF, chickenH, chickenS, side);
				}
		} else if (e.getSource() == button5) {// 5번테이블 계산
			for (int i = 0; i >= 0; i++) {
				if (model5.getValueAt(0, 0) == null) {
					count = 0;
					break;
				}

				if (model5.getValueAt(i-count, 0).equals("후라이드")) {
					chickenF += Integer.parseInt((String) model5.getValueAt(i-count, 1)) * 20000;
				} else if (model5.getValueAt(i-count, 0).equals("양념")) {
					chickenH += Integer.parseInt((String) model5.getValueAt(i-count, 1)) * 20000;
				} else if (model5.getValueAt(i-count, 0).equals("간장")) {
					chickenS += Integer.parseInt((String) model5.getValueAt(i-count, 1)) * 20000;
				} else if (model5.getValueAt(i-count, 0).equals("음료")) {
					side += Integer.parseInt((String) model5.getValueAt(i-count, 1)) * 2000;
				}
				model5.removeRow(i-count);
				count++;
			}
			if(PriceSum!=0) {
				B_SalesDAO.getInstance().menuInsert(id, PriceSum, chickenF, chickenH, chickenS, side);
				}
		} else if (e.getSource() == button6) {// 6번테이블 계산
			for (int i = 0; i >= 0; i++) {
				if (model6.getValueAt(i-count, 0) == null) {
					count = 0;
					break;
				}

				if (model6.getValueAt(i-count, 0).equals("후라이드")) {
					chickenF += Integer.parseInt((String) model6.getValueAt(i-count, 1)) * 20000;
				} else if (model6.getValueAt(i-count, 0).equals("양념")) {
					chickenH += Integer.parseInt((String) model6.getValueAt(i-count, 1)) * 20000;
				} else if (model6.getValueAt(i-count, 0).equals("간장")) {
					chickenS += Integer.parseInt((String) model6.getValueAt(i-count, 1)) * 20000;
				} else if (model6.getValueAt(i-count, 0).equals("음료")) {
					side += Integer.parseInt((String) model6.getValueAt(i-count, 1)) * 2000;
				}
				model6.removeRow(i-count);
				count++;
			}
			if(PriceSum!=0) {
				B_SalesDAO.getInstance().menuInsert(id, PriceSum, chickenF, chickenH, chickenS, side);
				}
		} else if (e.getSource() == button7) {// 선택하기 버튼 누를시
			if(!(textfiled.getText().equals(""))) {
			model.insertRow(0, new Object[] { (String) combo.getSelectedItem(), textfiled.getText() });
			}else {
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
