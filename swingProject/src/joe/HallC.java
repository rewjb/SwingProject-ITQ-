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
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class HallC extends JPanel implements ActionListener, BodyHall {

	// 테이블 버튼
	private JToggleButton jb;//1번테이블 버튼
	private JToggleButton jb1;//2번테이블 버튼
	private JToggleButton jb2;//3번테이블 버튼
	private JToggleButton jb3;//4번 테이블 버튼
	private JToggleButton jb4;//5번 테이블 버튼
	private JToggleButton jb5;//6번 테이블 버튼
	// 계산 버튼
	
	
	
	private DefaultTableModel model1 = new DefaultTableModel(15, 2);//1번테이블
	private DefaultTableModel model2 = new DefaultTableModel(15, 2);//2번테이블
	private DefaultTableModel model3 = new DefaultTableModel(15, 2);//3번테이블
	private DefaultTableModel model4 = new DefaultTableModel(15, 2);//4번 테이블
	private DefaultTableModel model5 = new DefaultTableModel(15, 2);//5번테이블
	private DefaultTableModel model6 = new DefaultTableModel(15, 2);//6번테이블
	
	
	private JButton button;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;
	private JButton button6;
	// 주문 선택버튼
	private JButton button7;
	// 버튼 그룹
	private ButtonGroup bg;


	HashMap<JToggleButton, DefaultTableModel> key = new HashMap<>();//해쉬맵 사용법
	
	// 테이블내 표
	private DefaultTableModel model = new DefaultTableModel(15, 2);
	
	// 표가 들어갈 테이블
	private JTable listTable = new JTable(model);// 주문하기 테이블
	private JTable table = new JTable(model1);//1번테이블
	private JTable table1 = new JTable(model2);//2번테이블
	private JTable table2 = new JTable(model3);//3번테이블
	private JTable table3 = new JTable(model4);//4번테이블
	private JTable table4 = new JTable(model5);//5번테이블
	private JTable table5 = new JTable(model6);//6번테이블

	// 스크롤바
	private JScrollPane scroll = new JScrollPane(listTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane scroll1 = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane scroll2 = new JScrollPane(table1, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane scroll3 = new JScrollPane(table2, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane scroll4 = new JScrollPane(table3, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane scroll5 = new JScrollPane(table4, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane scroll6 = new JScrollPane(table5, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 주문할 프레임
	private JFrame jframe;
	// 메뉴선택 콤보박스
	private JComboBox combo;

	private JTextField textfiled;
	private JLabel jl;
	private JLabel jl_1;
	
	Object temp = null;
	
	
	B_OrderDAO salesDAO = B_SalesDAO.getInstance();
	
	// 메뉴
	String[] menu = new String[] { "후라이드", "양념", "간장", "사이드메뉴" };

	public HallC() {
		
		
		jb = new JToggleButton("1번 테이블");
		jb1 = new JToggleButton("2번 테이블");
		jb2 = new JToggleButton("3번 테이블");
		jb3 = new JToggleButton("4번 테이블");
		jb4 = new JToggleButton("5번 테이블");
		jb5 = new JToggleButton("6번 테이블");
		
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
		// .setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 14)); 폰트설정
		jl_1 = new JLabel("수량 ");
		jl_1.setBounds(155, 10, 50, 30);

		textfiled = new JTextField();
		textfiled.setBounds(190, 10, 90, 30);

		combo = new JComboBox(menu);
		combo.setBounds(50, 10, 90, 30);
		// combo.addActionListener(this);
		button7 = new JButton("선택하기");
		button7.setBounds(30, 210, 100, 30);

		// 주문할
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

		bg = new ButtonGroup();


		button1 = new JButton("계산");
		button1.setBounds(71, 166, 60, 24);
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

		scroll1.setBounds(58, 68, 144, 97);
		scroll2.setBounds(299, 68, 144, 97);
		scroll3.setBounds(530, 68, 144, 97);
		scroll4.setBounds(58, 235, 144, 97);
		scroll5.setBounds(299, 235, 144, 97);
		scroll6.setBounds(530, 235, 144, 97);

		scroll.setBounds(0, 50, 285, 145);
		jb.setBounds(91, 38, 97, 28);
		jb1.setBounds(325, 38, 97, 29);
		jb2.setBounds(559, 38, 97, 29);
		jb3.setBounds(71, 201, 97, 29);
		jb4.setBounds(325, 201, 97, 29);
		jb5.setBounds(559, 201, 97, 29);

		model.setColumnIdentifiers(new Object[] { "메뉴", "수량" });
		model1.setColumnIdentifiers(new Object[] { "메뉴", "수량" });
		model2.setColumnIdentifiers(new Object[] { "메뉴", "수량" });
		model3.setColumnIdentifiers(new Object[] { "메뉴", "수량" });
		model4.setColumnIdentifiers(new Object[] { "메뉴", "수량" });
		model5.setColumnIdentifiers(new Object[] { "메뉴", "수량" });
		model6.setColumnIdentifiers(new Object[] { "메뉴", "수량" });

		bg.add(jb);
		bg.add(jb1);
		add(jb4);
		add(jb5);
		add(scroll1);
		bg.add(jb2);
		add(jb3);
		add(scroll3);
		add(scroll2);
		bg.add(jb3);
		add(jb2);
		add(scroll4);
		bg.add(jb4);
		add(jb1);
		add(scroll5);
		bg.add(jb5);
		add(jb);
		add(scroll6);
		add(button1);
		add(button6);
		add(button2);
		add(button3);
		add(button4);
		add(button5);

		jb.addActionListener(this);
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jb3.addActionListener(this);
		jb4.addActionListener(this);
		jb5.addActionListener(this);
		
		button.addActionListener(this);
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);
		button7.addActionListener(this);

		setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// System.out.println((String)combo.getSelectedItem());콤보박스 선택값 출력
		int chikenF = 0;
		int chikenH = 0;
		int chikenS = 0;
		int sideMenu = 0;
		int PriceSum = chikenS+chikenH+chikenS+sideMenu;
		
		if (e.getSource() == jb) {
			jframe.setVisible(true);
			temp = e.getSource();
		} else if (e.getSource() == jb1) {
			jframe.setVisible(true);
			temp =  e.getSource();
		} else if (e.getSource() == jb2) {
			jframe.setVisible(true);
			temp =  e.getSource();
		} else if (e.getSource() == jb3) {
			jframe.setVisible(true);
			temp =  e.getSource();
		} else if (e.getSource() == jb4) {
			jframe.setVisible(true);
			temp =  e.getSource();
		} else if (e.getSource() == jb5) {
			jframe.setVisible(true);
			temp =  e.getSource();
		} else if (e.getSource() == button) {// 주문하기
			
			while (true) {
				if (model.getValueAt(0, 0) == null) {
					model.removeRow(0);
					break;
				}
				
				DefaultTableModel temp2 = key.get(temp);
				temp2.insertRow(0, new Object[] {  model.getValueAt(0, 0),  model.getValueAt(0, 1) });
				model.removeRow(0);
			}

		} else if (e.getSource() == button1) {//1번테이블 계산
			
			while(true) {
				if (model1.getValueAt(0, 0) == null) {break;}
				
				if(model1.getValueAt(0, 0).equals("후라이드")){
					chikenF = (int) model.getValueAt(0, 1) * 20000;
				}else if (model1.getValueAt(0, 0).equals("양념")) {
					 chikenH = (int) model.getValueAt(0, 1) * 20000;
				}else if (model1.getValueAt(0, 0).equals("간장")) {
					chikenS = (int) model.getValueAt(0, 1) * 20000;
				}else if (model1.getValueAt(0, 0).equals("사이드메뉴")) {
					sideMenu = (int) model.getValueAt(0, 1) * 10000;
				}
				
				
		
		
			}
		} else if (e.getSource() == button2) {//2번테이블 계산

		} else if (e.getSource() == button3) {//3번테이블 계산

		} else if (e.getSource() == button4) {//4번테이블 계산

		} else if (e.getSource() == button5) {//5번테이블 계산

		} else if (e.getSource() == button6) {//6번테이블 계산

		} else if (e.getSource() == button7) {
			model.insertRow(0, new Object[] { (String) combo.getSelectedItem(), textfiled.getText() });
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
