package rew;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class H_Order extends JPanel implements HeadCheckOrder, ActionListener {

	private DefaultTableModel orderListModel = new DefaultTableModel(0, 4);
	private JTable orderListTable = new JTable(orderListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 가맹점의 발주 리스트(확인하지 않은..)를 넣을 Jtable / 오면서 셀을 수정여부 메서드를 무조건 false값으로 리턴
	
	private DefaultTableModel franchiseListModel = new DefaultTableModel(10, 2);
	private JTable franchiseListTable = new JTable(franchiseListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 가맹점의 연락처 목록보기

	private JScrollPane orderScroll = new JScrollPane(orderListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 발주내용의 스크롤 기능 객체
	
	private JScrollPane  franchiseScroll = new JScrollPane(franchiseListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	
	private DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
	// Jtable의 가운데 정렬 객체

	private JButton previousBtn = new JButton();
	private JButton nowBtn = new JButton();
	private JButton nextBtn = new JButton();

	private int index = 1;
	private int count;
	private int listNum = 10;

	private B_OrderDTO[] orderDAO;
	
	private JPanel bodyTelJpanel = new JPanel();

	public H_Order() {

		listInsert(index);

		nowBtn.setText(String.valueOf(index));
		// 버튼의 초기값

		orderListTable.getTableHeader().setResizingAllowed(false);
		orderListTable.getTableHeader().setReorderingAllowed(false);
		// 발주테이블의 헤더를 얻어서 사이즈 수정 불가 , 발주테이블의 컬럼 이동 금지
		
		franchiseListTable.getTableHeader().setResizingAllowed(false);
		franchiseListTable.getTableHeader().setReorderingAllowed(false);
		// 업체테이블의 헤더를 얻어서 사이즈 수정 불가, / 업체테이블의 컬럼 이동 금지
		
		orderListModel.setColumnIdentifiers(new String[] { "가맹점명", "상품명", "수량", "발주일" });

//		orderListTable.getColumnModel().getColumn(1).setPreferredWidth(300);
		// 컬럼 너비를 수정하는 메서드 , 그러나 여기에서는 스크롤팬에 맞춰서 설정된듯 하다 ..

//		orderDAO = B_OrderDAO.getInstance().select_UnCheck(index);
//		데이터를 얻어오는 것

		nextBtn.addActionListener(this);
		previousBtn.addActionListener(this);
		//인덱스 숫자 액션리스너

		previousBtn.setBounds(200, 336, 42, 20);
		nowBtn.setBounds(242, 336, 42, 20);
		nextBtn.setBounds(284, 336, 42, 20);
		//버튼들의 배치

		orderScroll.setBounds(2, 20, 560, 315);
		franchiseScroll.setBounds(565,20,200,315);
		//2개 스크롤팬의 배치

		celAlignCenter.setHorizontalAlignment(SwingConstants.CENTER);
        //가운데 정렬 설정의 객체
		
		for (int i = 0; i < 4; i++) {
			orderListTable.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅

		for (int i = 0; i < 2; i++) {
			franchiseListTable.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅
		
		
//		orderListTable.getTableHeader().setBackground(Color.BLACK);
//		컬럼 배경설정
//		orderListTable.getColumnModel().getColumn(0)


	

		assignBtnIndex();
		//처음 시작시 버튼에 인덱스 번호 부여
		
		//----------------------------------------------------위에는 발주목록 아래는 전화번호 목록
		
	
		

		
		

		add(franchiseScroll);
		add(previousBtn);
		add(nowBtn);
		add(nextBtn);
		add(orderScroll);

		setLayout(null);
		setBackground(Color.BLACK);
		setBounds(0, 0, 770, 358);
		setSize(770, 358);
		setVisible(false);// 마지막에는 false로 변경

	}// 생성자 끝

	@Override // 인터페이스 BBQHead로부터 받은 메서드 show 오버라이딩
	public void show(BBQHead bbqHead) {
		((Component) bbqHead).setVisible(true);
	}// show 메서드 끝

	@Override // 인터페이스 BBQHead로부터 받은 메서드 hide 오버라이딩
	public void hide(BBQHead bbqHead) {
		((Component) bbqHead).setVisible(false);
	}// hide 메서드 끝

	public void assignBtnIndex() {
		if (index == 1) {
			previousBtn.setText("");
			previousBtn.setEnabled(false);
		} else {
			previousBtn.setText(String.valueOf(index - 1));
			previousBtn.setEnabled(true);
		}

		if (index == (int) (count / listNum + 1)) {
			nextBtn.setText("");
			nextBtn.setEnabled(false);
		} else {
			nextBtn.setText(String.valueOf(index + 1));
			nextBtn.setEnabled(true);
		}
	}// assignBtnIndex():메서드 끝

	public void listInsert(int index) {

		orderDAO = B_OrderDAO.getInstance().select_UnCheck(index);

		count = B_OrderDAO.getInstance().LastIdex();

		if ((int) (count / listNum + 1) == index) {

			for (int i = 0; i < count % listNum; i++) {
				orderListModel.insertRow(i, new Object[] { orderDAO[i].getNum(), orderDAO[i].getName(),
						orderDAO[i].getQuantity(), orderDAO[i].getDate() });
			}
		} else {
			for (int i = 0; i < listNum; i++) {
				orderListModel.insertRow(i, new Object[] { orderDAO[i].getNum(), orderDAO[i].getName(),
						orderDAO[i].getQuantity(), orderDAO[i].getDate() });
			}
		}
	}// listInsert():메서드 끝

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == nextBtn) {
			if ((index + 1) <= (int) (count / listNum + 1)) {
				++index;
				nowBtn.setText(String.valueOf(index));
				assignBtnIndex();
				int deNum = orderListModel.getRowCount();
				for (int i = 0; i < deNum; i++) {
					orderListModel.removeRow(0);
				}
				listInsert(index);
			}

		}

		if (e.getSource() == previousBtn) {
			if (!((index - 1) == 0)) {
				--index;
				nowBtn.setText(String.valueOf(index));
				assignBtnIndex();
				int deNum = orderListModel.getRowCount();
				for (int i = 0; i < deNum; i++) {
					orderListModel.removeRow(0);
				}
				listInsert(index);
			}

		}

	}// actionPerformed:메서드 끝

}// 클래스 끝
