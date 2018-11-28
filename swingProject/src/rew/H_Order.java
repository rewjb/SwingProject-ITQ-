package rew;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import DTO_DAO.B_OrderDAO;
import DTO_DAO.B_OrderDTO;
import inter.BBQHead;
import inter.HeadCheckOrder;

public class H_Order extends JPanel implements HeadCheckOrder {
	
	
	private DefaultTableModel model = new DefaultTableModel(10,4);
	private JTable listTable = new JTable(model);
	//리스트를 넣을 Jtable
	
	private JScrollPane scroll = new JScrollPane(listTable,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	//Jtable의 스크롤 기능 객체 
	private DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
	//Jtable의 가운데 정렬 객체
	
	JButton previousBtn = new JButton();
	JButton nowBtn = new JButton();
	JButton nextBtn = new JButton();
	
	int index=1;
	
	B_OrderDTO[] orderDAO ; 
	
	public H_Order() {
		
		previousBtn.setBounds(323, 336, 42, 20);
		nowBtn.setBounds(365, 336, 42, 20);
		nextBtn.setBounds(407, 336, 42, 20);
		
		scroll.setBounds(0, 20, 560, 315);
		
		model.setColumnIdentifiers(new Object[] {"가맹점명","상품명","수량","발주일"});
		
		
		
		
//		listTable.getTableHeader().setBackground(Color.BLACK);
//		컬럼 배경설정
//		listTable.getColumnModel().getColumn(0)
		
		
//		orderDAO = B_OrderDAO.getInstance().select_UnCheck(index);
//		
//		
//		celAlignCenter.setHorizontalAlignment(SwingConstants.CENTER);
//		//가운데 정렬을 시킬 수 있는 속성을 지닌 객체이다.
//		
//		//가운데 정렬 설정의 객체
//		for (int i = 0; i < 4; i++) {
//			listTable.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
//		}//for문 끝 / 가운데 정렬 세팅
	
		add(previousBtn);
		add(nowBtn);
		add(nextBtn);
		add(scroll);
		
		
		
	
		setLayout(null);
		setBackground(Color.BLACK);
		setBounds(0, 0, 770, 358);
		setSize(770, 358);
		setVisible(false); // 마지막에는 false로 변경
	}//생성자 끝
	
	@Override // 인터페이스 BBQHead로부터 받은 메서드 show 오버라이딩
	public void show(BBQHead bbqHead) {
		((Component) bbqHead).setVisible(true);
	}// show 메서드 끝

	@Override // 인터페이스 BBQHead로부터 받은 메서드 hide 오버라이딩
	public void hide(BBQHead bbqHead) {
		((Component) bbqHead).setVisible(false);
	}// hide 메서드 끝


}//클래스 끝
