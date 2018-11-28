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

	private DefaultTableModel listmodel = new DefaultTableModel(0, 4);
	private JTable listTable = new JTable(listmodel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 리스트를 넣을 Jtable / 오면서 셀을 수정여부 메서드를 무조건 false값으로 리턴

	private JScrollPane scroll = new JScrollPane(listTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// Jtable의 스크롤 기능 객체
	private DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
	// Jtable의 가운데 정렬 객체

	JButton previousBtn = new JButton();
	JButton nowBtn = new JButton();
	JButton nextBtn = new JButton();

	private int index = 1;
	private int count;
	private int listNum = 10;

	B_OrderDTO[] orderDAO;

	public H_Order() {

		orderDAO = B_OrderDAO.getInstance().select_UnCheck(index);

		nowBtn.setText(String.valueOf(index));
		// 버튼의 초기값

		listTable.getTableHeader().setResizingAllowed(false);
		// 헤더를 얻어서 사이즈 수정 불가

//		listTable.getColumnModel().getColumn(1).setPreferredWidth(300);
		// 컬럼 너비를 수정하는 메서드 , 그러나 여기에서는 스크롤팬에 맞춰서 설정된듯 하다 ..

//		orderDAO = B_OrderDAO.getInstance().select_UnCheck(index);
//		데이터를 얻어오는 것
		count = B_OrderDAO.getInstance().LastIdex();
		
		listInsert();

		nextBtn.addActionListener(this);
		previousBtn.addActionListener(this);

		previousBtn.setBounds(200, 336, 42, 20);
		nowBtn.setBounds(242, 336, 42, 20);
		nextBtn.setBounds(284, 336, 42, 20);

		scroll.setBounds(0, 20, 560, 315);
		
		

		listTable.getTableHeader().setReorderingAllowed(false);

		listmodel.setColumnIdentifiers(new String[] { "가맹점명", "상품명", "수량", "발주일" });

//		listTable.getTableHeader().setBackground(Color.BLACK);
//		컬럼 배경설정
//		listTable.getColumnModel().getColumn(0)

		celAlignCenter.setHorizontalAlignment(SwingConstants.CENTER);
//		//가운데 정렬 설정의 객체
		for (int i = 0; i < 4; i++) {
			listTable.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅

		assignBtnIndex();

		add(previousBtn);
		add(nowBtn);
		add(nextBtn);
		add(scroll);

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

	public void listInsert() {
		
		orderDAO = B_OrderDAO.getInstance().select_UnCheck(index);
		
		if ((int) (count / listNum + 1) == index) {

			for (int i = 0; i < count % listNum + 1; i++) {
				listmodel.insertRow(i, new Object[] { orderDAO[i].getNum(), orderDAO[i].getName(),
						orderDAO[i].getQuantity(), orderDAO[i].getDate() });
				System.out.println("a");
			}
		} else {
			for (int i = 0; i < listNum; i++) {
				listmodel.insertRow(i, new Object[] { orderDAO[i].getNum(), orderDAO[i].getName(),
						orderDAO[i].getQuantity(), orderDAO[i].getDate() });
				System.out.println("b");
			}
		}
	}//listInsert():메서드 끝

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == nextBtn) {
			if ((index + 1) <= (int) (count / listNum + 1)) {
				++index;
				nowBtn.setText(String.valueOf(index));
				assignBtnIndex();
			}

		}
		if (e.getSource() == previousBtn) {
			if (!((index - 1) == 0)) {
				--index;
				nowBtn.setText(String.valueOf(index));
				assignBtnIndex();
			}

		}

	}

//	JButton previousBtn = new JButton();
//	JButton nowBtn = new JButton();
//	JButton nextBtn = new JButton();

}// 클래스 끝
