package rew;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import inter.BBQHead;
import inter.HeadCheckOrder;

public class H_Order extends JPanel implements HeadCheckOrder {
	
	
	private DefaultTableModel model = new DefaultTableModel(5,5);
	private JTable listTable = new JTable();
	//리스트를 넣을 Jtable
	
	private JScrollPane scroll = new JScrollPane(listTable,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	//Jtable의 스크롤 기능 객체 
	private DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
	//Jtable의 가운데 정렬 객체
	
	
	public H_Order() {
		
//		celAlignCenter.setHorizontalAlignment(SwingConstants.CENTER);
//		//가운데 정렬 설정의 객체
//		for (int i = 0; i < 4; i++) {
//			listTable.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
//		}//for문 끝 / 가운데 정렬 세팅
	
		
		scroll.setBounds(0, 0, 770, 335);
		
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
