package rew;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import DTO_DAO.B_OrderDAO;
import DTO_DAO.B_OrderDTO;
import DTO_DAO.H_FranchiseDAO;
import DTO_DAO.H_FranchiseDTO;
import inter.BBQHead;
import inter.HeadCheckOrder;
import javax.swing.JLabel;

public class H_Order extends JPanel implements HeadCheckOrder, ActionListener {

	private DefaultTableModel orderListModel = new DefaultTableModel(0, 4);
	private JTable orderListTable = new JTable(orderListModel) {
		public boolean isCellEditable(int row,int column){return false;};}; // 가맹점의 발주 리스트(확인하지 않은..)를 넣을 Jtable / 오면서 셀을
																		// 수정여부 메서드를 무조건 false값으로 리턴

	private DefaultTableModel franchiseListModel = new DefaultTableModel(0, 2);
	private JTable franchiseListTable = new JTable(franchiseListModel) {
		public boolean isCellEditable(int row,int column){return false;};}; // 가맹점의 연락처 목록보기

	private JScrollPane orderScroll = new JScrollPane(orderListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 발주내용의 스크롤 기능 객체

	private JScrollPane franchiseScroll = new JScrollPane(franchiseListTable,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	private DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
	// Jtable의 가운데 정렬 객체

	private JButton previousBtn = new JButton();
	private JButton nowBtn = new JButton();
	private JButton nextBtn = new JButton();

	private int index = 1;
	private int count;
	private int listNum = 10;

	private B_OrderDTO[] orderDAO;

	private H_FranchiseDAO franchiseDAO = H_FranchiseDAO.getInstance();

	private JButton confirmBtn = new JButton("확인");

	public static int goOrder;
	
	
	
	private JLabel nameLabel = new JLabel(); //발주품목 레이블
	private JLabel venderLabel = new JLabel(); //발주품목에 대한 업체 레이블
	private JLabel moneyLabel = new JLabel(); //제품 선택시 가격 레이블
	private JLabel quantityLabel = new JLabel(); // 수량 입력란 레이블
	private JLabel totalPriceLabel = new JLabel(); // 총 가격 레이블
	
	
	private JComboBox<String> pNameBox = new JComboBox<>(); //발주품목 선택
	private JComboBox<String> pvenderBox = new JComboBox<>(); //발주품목에 대한 업체 선택
	private JTextField pMoneyField = new JTextField(); //제품 선택시 가격 표시
	private JTextField pQuantityField = new JTextField(); // 수량 입력란
	private JTextField ptotalPriceField = new JTextField();
	

//	JLabel

	// 발주품목 , 업체 , 가격정보, 수량 , 최종가격

	public H_Order() {
		
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setText("발주품목");
		
		venderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		venderLabel.setText("업체");
		
		moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		moneyLabel.setText("가격");
		
		quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
		quantityLabel.setText("수량");
		
		totalPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		totalPriceLabel.setText("총가격");
		
		pMoneyField.setHorizontalAlignment(SwingConstants.RIGHT);
		pMoneyField.setBounds(244, 23, 70, 20);
		pMoneyField.setEditable(false);
		
		pQuantityField.setHorizontalAlignment(SwingConstants.RIGHT);
		pQuantityField.setBounds(326, 23, 60, 20);
		
		ptotalPriceField.setHorizontalAlignment(SwingConstants.RIGHT);
		ptotalPriceField.setBounds(398, 23, 111, 20);
		ptotalPriceField.setEditable(false);
		
		nameLabel.setBounds(40, 7, 60, 15);
		venderLabel.setBounds(160, 7, 40, 15);
		moneyLabel.setBounds(256, 7, 40, 15);
		quantityLabel.setBounds(336, 7, 40, 15);
		totalPriceLabel.setBounds(428, 7, 40, 15);
		
		pNameBox.setBounds(20, 23, 100, 20);
		pvenderBox.setBounds(130, 23, 100, 20);
		//이거 위에 2개는 콤보박스
		
		
		
		

		

		orderInsert(index);
		// 발주 길록을 입력하는 메서드 index는 페이지 번호

		nowBtn.setText(String.valueOf(index));
		// 버튼의 초기값

		assignBtnIndex();
		// 초기 인덱스 번호에 따른 버튼 속성 부여
//		
		aliasNtelInsert();
		// 업체들 전화번호 갖고오는 메서드

		orderListTable.getTableHeader().setResizingAllowed(false);
		orderListTable.getTableHeader().setReorderingAllowed(false);
		// 발주테이블의 헤더를 얻어서 사이즈 수정 불가 , 발주테이블의 컬럼 이동 금지

		franchiseListTable.getTableHeader().setResizingAllowed(false);
		franchiseListTable.getTableHeader().setReorderingAllowed(false);
		// 업체테이블의 헤더를 얻어서 사이즈 수정 불가, / 업체테이블의 컬럼 이동 금지

		franchiseListModel.setColumnIdentifiers(new String[] { "가맹점명", "전화번호" });
		orderListModel.setColumnIdentifiers(new String[] { "가맹점명", "상품명", "수량", "발주일" });

//		orderListTable.getColumnModel().getColumn(1).setPreferredWidth(300);
		// 컬럼 너비를 수정하는 메서드 , 그러나 여기에서는 스크롤팬에 맞춰서 설정된듯 하다 ..

//		orderDAO = B_OrderDAO.getInstance().select_UnCheck(index);
//		데이터를 얻어오는 것

		nextBtn.addActionListener(this);
		previousBtn.addActionListener(this);
		confirmBtn.addActionListener(this);
		// 인덱스 숫자 액션리스너

		previousBtn.setBounds(200, 336, 42, 20);
		nowBtn.setBounds(242, 336, 42, 20);
		nextBtn.setBounds(284, 336, 42, 20);
		confirmBtn.setBounds(502, 336, 60, 20);
		// 버튼들의 배치

		orderScroll.setBounds(2, 20, 560, 315);
		franchiseScroll.setBounds(565, 20, 200, 315);
		// 2개 스크롤팬의 배치

		celAlignCenter.setHorizontalAlignment(SwingConstants.CENTER);
		// 가운데 정렬 설정의 객체

		for (int i = 0; i < 4; i++) {
			orderListTable.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅

		for (int i = 0; i < 2; i++) {
			franchiseListTable.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅

//		orderListTable.getTableHeader().setBackground(Color.BLACK);
//		컬럼 배경설정
//		orderListTable.getColumnModel().getColumn(0)

		// 처음 시작시 버튼에 인덱스 번호 부여

		// ----------------------------------------------------위에는 발주목록 아래는 전화번호 목록

//		add(confirmBtn);
//		add(franchiseScroll);
//		add(previousBtn);
//		add(nowBtn);
//		add(nextBtn);
		add(orderScroll);
		//위에는 기존거
		
		
		add(pNameBox);
		add(pvenderBox);
		add(pMoneyField);
		add(pQuantityField);
		
		add(nameLabel);
		add(venderLabel);
		add(moneyLabel);
		add(quantityLabel);
		add(totalPriceLabel);
		add(ptotalPriceField);
		
		setLayout(null);
		setBackground(Color.PINK);
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

	public void orderInsert(int index) {

		orderDAO = B_OrderDAO.getInstance().select_UnCheck(index);
		// B_OrderDAO의 배열값 객체 반환
		count = B_OrderDAO.getInstance().LastIdex();

		if ((int) (count / listNum + 1) == index) {

			for (int i = 0; i < count % listNum; i++) {
				orderListModel.insertRow(i, new Object[] { orderDAO[i].getAlias(), orderDAO[i].getName(),
						orderDAO[i].getQuantity(), orderDAO[i].getDate() });
			}
		} else {
			for (int i = 0; i < listNum; i++) {
				orderListModel.insertRow(i, new Object[] { orderDAO[i].getAlias(), orderDAO[i].getName(),
						orderDAO[i].getQuantity(), orderDAO[i].getDate() });
			}
		}
	}// orderInsert():메서드 끝

	public void aliasNtelInsert() {
		ArrayList<H_FranchiseDTO> franchiseArray = franchiseDAO.select_AliasNTel();
		for (int i = 0; i < franchiseArray.size(); i++) {
			franchiseListModel.insertRow(i,
					new Object[] { franchiseArray.get(i).getAlias(), franchiseArray.get(i).getTel() });
//			franchiseArray.get(i).getAddr()
//			툴팁으로 넣을지 말지 .. 고민 ..
		}

	}

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
				orderInsert(index);
			}
		} // 다음 버튼을 누를 시 발생하는 액션

		if (e.getSource() == previousBtn) {
			if (!((index - 1) == 0)) {
				--index;
				nowBtn.setText(String.valueOf(index));
				assignBtnIndex();
				int deNum = orderListModel.getRowCount();
				for (int i = 0; i < deNum; i++) {
					orderListModel.removeRow(0);
				}
				orderInsert(index);
			}
		} // 이전 버튼을 누를 시 발생하는 액션

		if (e.getSource() == confirmBtn) {

			int selectNum;
			// 해당 발주를 체크하는 테이블 행 번호
			int deNum = orderListModel.getRowCount();
			// 지울 횟수 숫자

			for (int i = 0; i < orderListTable.getSelectedRows().length; i++) {
				selectNum = orderListTable.getSelectedRows()[i];
				B_OrderDAO.getInstance().checkUpdate(orderDAO[selectNum].getNum());
			} // ck_1을 입력하는 과정

			if ((index + 1) <= (int) (count / listNum + 1)) {
				// 세팅을 다시 하기 위해 값을 지우는 for문
				for (int i = 0; i < deNum; i++) {
					orderListModel.removeRow(0);
				}
			} else if (!((index - 1) == 0)) {
				for (int i = 0; i < deNum; i++) {
					orderListModel.removeRow(0);
				}
			}
			orderInsert(index);
			// 값을 다시 세팅
			goOrder = JOptionPane.showConfirmDialog(this, "발주작업으로 이동하시겠습니까?", "발주안내 메세지", JOptionPane.YES_OPTION);
			// 0 : 예 / 1 : 아니
		}

	}// actionPerformed:메서드 끝
}// 클래스 끝
