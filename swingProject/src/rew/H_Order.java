package rew;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import DTO_DAO.H_FranchiseDAO;
import DTO_DAO.H_OrderDAO;
import DTO_DAO.H_OrderDTO;
import DTO_DAO.H_VenderDAO;
import DTO_DAO.H_VenderDTO;
import DTO_DAO.H_VenderpDAO;
import inter.BBQHead;
import inter.HeadCheckOrder;
import inter.HeadOrder;

public class H_Order extends JPanel implements HeadOrder, ActionListener, ItemListener, DocumentListener {

	private DefaultTableModel orderListModel = new DefaultTableModel(0, 5);
	private JTable orderListTable = new JTable(orderListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 가맹점의 발주 리스트(확인하지 않은..)를 넣을 Jtable / 오면서 셀을
	// 수정여부 메서드를 무조건 false값으로 리턴
	
	private JScrollPane orderScroll = new JScrollPane(orderListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 발주내용의 스크롤 기능 객체
	

	private DefaultTableModel venderListModel = new DefaultTableModel(0, 2);
	private JTable venderListTable = new JTable(venderListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 업체 연락처 보는 테이블
	
	private JScrollPane vendereScroll = new JScrollPane(venderListTable,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	//업체 연락처 테이블 스크롤


	private DefaultTableModel orderPlusListModel = new DefaultTableModel(0, 5);
	private JTable orderPlusListTable = new JTable(orderPlusListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 가맹점의 연락처 목록보기
	

	
	private JScrollPane orderPlusScroll = new JScrollPane(orderPlusListTable,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	//발주 추가 테이블의 스크롤
	
	private DefaultTableModel stockListModel = new DefaultTableModel(0, 2);
	private JTable stockListTable = new JTable(stockListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 업체 연락처 보는 테이블
	
	private JScrollPane stockScroll = new JScrollPane(stockListTable,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	//업체 연락처 테이블 스크롤

	private DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
	// Jtable의 가운데 정렬 객체

	private JButton previousBtn = new JButton();
	private JButton nowBtn = new JButton();
	private JButton nextBtn = new JButton();

	private int index = 1; // 처음 인덱스 번호
	private int count; // 전체 필드 수량
	private int listNum = 9; // 표시할 수

	private H_FranchiseDAO franchiseDAO = H_FranchiseDAO.getInstance();

	// 이거는 발주 리스트에 있는 것을 삭제.. 조만간 구현
	private JButton plusBtn = new JButton("추가");
	private JButton deletemBtn1 = new JButton("삭제");
	private JButton deletemBtn2 = new JButton("삭제");
	private JButton confirmOrderBtn = new JButton("확인");

	public static int goOrder;

	private JLabel nameLabel = new JLabel(); // 발주품목 레이블
	private JLabel venderLabel = new JLabel(); // 발주품목에 대한 업체 레이블
	private JLabel moneyLabel = new JLabel(); // 제품 선택시 가격 레이블
	private JLabel quantityLabel = new JLabel(); // 수량 입력란 레이블
	private JLabel totalPriceLabel = new JLabel(); // 총 가격 레이블
	private JLabel venderInfoLabel = new JLabel(); // 업체 연락처 레이블
	private JLabel stockInfoLabel = new JLabel(); // 업체 연락처 레이블

	private JComboBox<String> pNameBox = new JComboBox<>(); // 발주품목 선택
	private JComboBox<String> pVenderBox = new JComboBox<>(); // 발주품목에 대한 업체 선택
	private JTextField pMoneyField = new JTextField(); // 제품 선택시 가격 표시
	private JTextField pQuantityField = new JTextField(); // 수량 입력란
	private JTextField ptotalPriceField = new JTextField();

	private JLabel orderListLabel = new JLabel();
	// 발주목록 제목 레이블

	// 발주품목 , 업체 , 가격정보, 수량 , 최종가격

	private H_VenderpDAO venderpDAO = H_VenderpDAO.getInstance();
	// 싱글톤 업체제품의 DAO 선언
	private ArrayList<String> productList;
	// 제품군만 갖고오는 스트링 list
	private ArrayList<String[]> venderList;
	// 제품군에 맞는 업체를 갖고오는 리스트
	private H_OrderDAO h_orderDAO = H_OrderDAO.getInstance();
	// 발주를 데이터베이스에 넣는 싱글톤 DAO
	private ArrayList<H_OrderDTO> orderList;
	// 발주 기록을 갖고올 orderList
	private ArrayList uniqueNum;
	// 발주기록을 지우기 위한 유니크 넘버 갖기
	private H_VenderDAO h_venderDAO = H_VenderDAO.getInstance();

//	발주 넣는 리스트 배치 (2, 45, 560, 100)
	public H_Order() {// 생성자 시작
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

		venderInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		venderInfoLabel.setText("업체 연락처");
		
		stockInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		stockInfoLabel.setText("재고확인");

		pMoneyField.setHorizontalAlignment(SwingConstants.RIGHT);
		pMoneyField.setBounds(230, 23, 70, 20);
		pMoneyField.setEditable(false);

		pQuantityField.setHorizontalAlignment(SwingConstants.RIGHT);
		pQuantityField.setBounds(312, 23, 60, 20);

		ptotalPriceField.setHorizontalAlignment(SwingConstants.RIGHT);
		ptotalPriceField.setBounds(384, 23, 111, 20);
		ptotalPriceField.setEditable(false);

		stockInfoLabel.setBounds(625, 160, 80, 15);
		venderInfoLabel.setBounds(625, 7, 80, 15);
		nameLabel.setBounds(32, 7, 60, 15);
		venderLabel.setBounds(152, 7, 40, 15);
		moneyLabel.setBounds(244, 7, 40, 15);
		quantityLabel.setBounds(322, 7, 40, 15);
		totalPriceLabel.setBounds(420, 7, 40, 15);

		pNameBox.setBounds(12, 23, 100, 20);
		pVenderBox.setBounds(122, 23, 100, 20);
		// 이거 위에 2개는 콤보박스
		
//		orderPlusListTable.getColumnModel().seted 작업중

		orderListLabel.setHorizontalAlignment(SwingConstants.CENTER);
		orderListLabel.setText("발주목록");
		orderListLabel.setBounds(245, 155, 69, 15);
		// 발주목록 레이블

		pNameBox.addItemListener(this);
		pVenderBox.addItemListener(this);
		// 콤보박스 액션리스너
		pQuantityField.getDocument().addDocumentListener(this);

		insertIntoNameBox();
		// 발주품목을 콤보박스에 넣기
		insertIntoVenderBox();
		// 품목에 따른 업체를 콤보박스에 넣기

		orderInsert(index);
		// 발주 길록을 입력하는 메서드 index는 페이지 번호

		nowBtn.setText(String.valueOf(index));
		// 버튼의 초기값

		assignBtnIndex();
		// 초기 인덱스 번호에 따른 버튼 속성 부여
		
		venderInsert();
		// 업체들 전화번호 갖고오는 메서드

		orderListTable.getTableHeader().setResizingAllowed(false);
		orderListTable.getTableHeader().setReorderingAllowed(false);
		// 발주테이블의 헤더를 얻어서 사이즈 수정 불가 ,  컬럼 이동 금지  및 사이즈조절 금지

		venderListTable.getTableHeader().setResizingAllowed(false);
		venderListTable.getTableHeader().setReorderingAllowed(false);
		// 업체테이블의 헤더를 얻어서 사이즈 수정 불가, / 컬럼 이동 금지  및 사이즈조절 금지

		orderPlusListTable.getTableHeader().setResizingAllowed(false);
		orderPlusListTable.getTableHeader().setReorderingAllowed(false);
		// 발주품목 추가 테이블의 헤더를 얻어서 사이즈 수정 불가, / 컬럼 이동 금지  및 사이즈조절 금지
		
		stockListTable.getTableHeader().setResizingAllowed(false);
		stockListTable.getTableHeader().setReorderingAllowed(false);
		// 재고확인 테이블의 헤더를 얻어서.., / 컬럼 이동 금지  및 사이즈조절 금지

		
		venderListModel.setColumnIdentifiers(new String[] { "가맹점명", "전화번호" });
		orderListModel.setColumnIdentifiers(new String[] { "업체", "발주품목", "수량", "금액", "발주일"});
		orderPlusListModel.setColumnIdentifiers(new String[] { "발주품목", "업체", "가격정보", "수량", "총금액" });
	    stockListModel.setColumnIdentifiers(new String[] { "품목", "현재고/완료재고"});

		orderListTable.getColumnModel().getColumn(4).setPreferredWidth(180);
		stockListTable.getColumnModel().getColumn(1).setPreferredWidth(130);
		// 컬럼 너비를 수정하는 메서드 , 그러나 여기에서는 스크롤팬에 맞춰서 설정된듯 하다 ..

//		orderDAO = B_OrderDAO.getInstance().select_UnCheck(index);
//		데이터를 얻어오는 것

		nextBtn.addActionListener(this);
		previousBtn.addActionListener(this);
		deletemBtn2.addActionListener(this);
		deletemBtn1.addActionListener(this);
		confirmOrderBtn.addActionListener(this);
		// 인덱스 숫자 액션리스너

		plusBtn.addActionListener(this);

		previousBtn.setBounds(180, 336, 60, 20);
		nowBtn.setBounds(240, 336, 60, 20);
		nextBtn.setBounds(300, 336, 60, 20);
		deletemBtn2.setBounds(502, 336, 60, 20);
		plusBtn.setBounds(502, 23, 60, 20);
		deletemBtn1.setBounds(502, 133, 60, 20);
		// 버튼들의 배치

		orderScroll.setBounds(2, 170, 560, 165); // 발주목록
		vendereScroll.setBounds(565, 25, 200, 130);
		orderPlusScroll.setBounds(2, 44, 560, 88);
		stockScroll.setBounds(565, 180, 200, 155);
		// 3개 스크롤팬의 배치

		celAlignCenter.setHorizontalAlignment(SwingConstants.CENTER);
		// 가운데 정렬 설정의 객체

		for (int i = 0; i < 5; i++) {
			orderListTable.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅
		for (int i = 0; i < 2; i++) {
			venderListTable.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅
		for (int i = 0; i < 5; i++) {
			orderPlusListTable.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅
		for (int i = 0; i < 2; i++) {
			stockListTable.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅

//		orderListTable.getTableHeader().setBackground(Color.BLACK);
//		컬럼 배경설정
//		orderListTable.getColumnModel().getColumn(0)

		// 처음 시작시 버튼에 인덱스 번호 부여

		// ----------------------------------------------------위에는 발주목록 아래는 전화번호 목록

		add(deletemBtn2);
		add(vendereScroll);
		add(previousBtn);
		add(nowBtn);
		add(nextBtn);
		add(orderScroll);
		// 위에는 기존거

		add(pNameBox);
		add(pVenderBox);
		add(pMoneyField);
		add(pQuantityField);
		add(nameLabel);
		add(venderLabel);
		add(moneyLabel);
		add(quantityLabel);
		add(totalPriceLabel);
		add(ptotalPriceField);
		add(orderListLabel);
		add(plusBtn);
		add(deletemBtn1);
		add(orderPlusScroll);
		add(venderInfoLabel);
		add(stockInfoLabel);
		add(stockScroll);

		setLayout(null);
		setBounds(0, 0, 770, 358);
		confirmOrderBtn.setBounds(435, 133, 60, 20);

		add(confirmOrderBtn);

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

	public void insertIntoNameBox() {// 발주품목 넣기
		productList = venderpDAO.select_product();
		// productList에 제품군 넣기!
		for (int i = 0; i < productList.size(); i++) {
			pNameBox.addItem((String) productList.get(i));
		}
	}// insertIntoNameBox:메서드 종료

	public void insertIntoVenderBox() {// 발주품목에 따른 업체 넣기
		venderList = venderpDAO.select_vender();
		// 제품군에 맞는 고유번호,제품군,업체명,금액이 들어있다.
		pVenderBox.removeAllItems();
		for (int i = 0; i < venderList.size(); i++) {
			if (venderList.get(i)[1].equals((String) pNameBox.getSelectedItem())) {
				// 제품군과 업체를 매칭시는 과정
				pVenderBox.addItem(venderList.get(i)[2]);
			}
		}
	}// insertIntoVenderBox:메서드 종료

	public void insertMoneyField() {

		for (int i = 0; i < venderList.size(); i++) {

			if ((venderList.get(i)[1].equals(pNameBox.getSelectedItem()))
					&& (venderList.get(i)[2].equals(pVenderBox.getSelectedItem()))) {
				pMoneyField.setText(venderList.get(i)[3]);
			}
		}
	}// insertMoneyField:메서드 종료

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == pNameBox) {
			insertIntoVenderBox();
		}
		if (e.getSource() == pVenderBox) {
			insertMoneyField();
		}
	}// itemStateChanged:메서드 종료

	public void assignBtnIndex() {
		if (index == 1) { // 1을 기준으로 작은 쪽
			previousBtn.setText("");
			previousBtn.setEnabled(false);
		} else {
			previousBtn.setText(String.valueOf(index - 1));
			previousBtn.setEnabled(true);
		}

		if (index == (int) (count / listNum + 1) && count % listNum == 0) { // 마지막 번호에 대한 것
			nextBtn.setText("");
			nextBtn.setEnabled(false);
			previousBtn.doClick();
		} else if ((index == (int) (count / listNum + 1) && count % listNum != 0)) {
			nextBtn.setText("");
			nextBtn.setEnabled(false);
		} else {
			nextBtn.setText(String.valueOf(index + 1));
			nextBtn.setEnabled(true);
		}

	}// assignBtnIndex():메서드 끝

	public void orderInsert(int index) {

		// B_OrderDAO의 배열값 객체 반환
		count = h_orderDAO.selectAll().size();
		// 전체 리스트 수량
		orderList = h_orderDAO.selectAll();
		// 전체 데이터베이스 값을 갖고 있는 리스트

		int lastListNumBefore = orderListModel.getRowCount();
		// 기존의 마지막 페이지의 자료 갯수

		int lastListNumAfter = (count % listNum);
		// 마지막 index의 자료 갯수 , 전체필드%9 + 1

		uniqueNum = new ArrayList<>();
		// Interger타입으로 고유번호를 넣는 리스트

		if (orderListModel.getRowCount() > 0) {
			for (int i = 0; i < lastListNumBefore; i++) {
				orderListModel.removeRow(0);
			} // 마지막 페이지의 필드 수량만큼 삭제
		}

		if (((int) (count / listNum) + 1) == index) { // 마지막 페이지
			for (int i = 0; i < lastListNumAfter; i++) {
				orderListModel.insertRow(i,
						new Object[] { orderList.get((index - 1) * listNum + i).getVendername(),
								orderList.get((index - 1) * listNum + i).getName(),
								orderList.get((index - 1) * listNum + i).getQuantity(),
								orderList.get((index - 1) * listNum + i).getMoney(),
								orderList.get((index - 1) * listNum + i).getDate(),
								orderList.get((index - 1) * listNum + i).getConfirm() });
				uniqueNum.add(orderList.get((index - 1) * listNum + i).getNum());
			}
		} else { // 마지막 페이지가 아님
			for (int i = 0; i < listNum; i++) {
				orderListModel.insertRow(i,
						new Object[] { orderList.get((index - 1) * listNum + i).getVendername(),
								orderList.get((index - 1) * listNum + i).getName(),
								orderList.get((index - 1) * listNum + i).getQuantity(),
								orderList.get((index - 1) * listNum + i).getMoney(),
								orderList.get((index - 1) * listNum + i).getDate(),
								orderList.get((index - 1) * listNum + i).getConfirm() });
				uniqueNum.add(orderList.get((index - 1) * listNum + i).getNum());
			}
		}
	}// orderInsert():메서드 끝

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == nextBtn) {
			if ((index + 1) <= (int) (count / listNum + 1)) {
				++index;
				nowBtn.setText(String.valueOf(index));
				assignBtnIndex();
				orderInsert(index);
			}
		} // 다음 버튼을 누를 시 발생하는 액션

		if (e.getSource() == previousBtn) {
			if (!((index - 1) == 0)) {
				--index;
				nowBtn.setText(String.valueOf(index));
				assignBtnIndex();
				orderInsert(index);
			}
		} // 이전 버튼을 누를 시 발생하는 액션

		if (e.getSource() == deletemBtn2) { // 작업중

			int[] selectedIndex = orderListTable.getSelectedRows();

			int countNum = orderListTable.getSelectedRows().length;
			for (int i = 0; i < countNum; i++) {
				h_orderDAO.deleteSelected((int) uniqueNum.get(selectedIndex[i]));
			}
			orderInsert(index);

		} // 발주기록을 지우는 메서드

		// 추가버튼을 누르면 선택한 항목이 발주 바구니에 이동한다.
		// 이때 수량이 int값으로 전환을 시도하는데
		if (e.getSource() == plusBtn) {
			try {
				Integer.parseInt(pQuantityField.getText());
				orderPlusListModel.insertRow(0, new Object[] { pNameBox.getSelectedItem(), pVenderBox.getSelectedItem(),
						pMoneyField.getText(), pQuantityField.getText(), ptotalPriceField.getText() }); // 5개 입력임
				pQuantityField.setText("");
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "수량에 숫자를 입력해 주세요.");
			}
		} // plusBtn:버튼 if문 종료

		if (e.getSource() == deletemBtn1) {
			if (orderPlusListTable.getRowCount() == 0) {
				JOptionPane.showMessageDialog(this, "항목을 추가해 주세요!");
			} else if (orderPlusListTable.getSelectedRowCount() == 1) {
				orderPlusListModel.removeRow(orderPlusListTable.getSelectedRow());
			} else {
				JOptionPane.showMessageDialog(this, "하나만 선택해 주세요!");
			}
		} // deletemBtn:버튼 if문 종료

		if (e.getSource() == confirmOrderBtn) {
			ArrayList<H_OrderDTO> list = new ArrayList<>();
			// H_OrderDTO를 담을 리스트
			int[] a = orderListTable.getSelectedRows();
			System.out.println(a);
			
			H_OrderDTO tempDTO;
			int num = orderPlusListTable.getRowCount(); // 왜 ..그러지 ..?
			for (int i = 0; i < num; i++) {
				tempDTO = new H_OrderDTO();
				tempDTO.setVendername((String) orderPlusListModel.getValueAt(0, 1));
				tempDTO.setName((String) orderPlusListModel.getValueAt(0, 0));
				tempDTO.setQuantity(Integer.parseInt((String) orderPlusListModel.getValueAt(0, 3)));
				tempDTO.setMoney(Integer.parseInt((String) orderPlusListModel.getValueAt(0, 4)));
				orderPlusListModel.removeRow(0);
				list.add(tempDTO);
			} // DTO를 list에 넣는 과정

			h_orderDAO.insert(list);
			orderInsert(index);
		} // confirmOrderBtn:버튼 if문 종료

		if (e.getSource() == deletemBtn1) {

		} // deletemBtn : 버튼 if문 종료

	}// actionPerformed:메서드 끝

	@Override
	public void insertUpdate(DocumentEvent e) {
		try {
			Integer.parseInt(pQuantityField.getText());
			int temp2;
			if (pQuantityField.getText().equals("")) {
				temp2 = 0;
			} else {
				temp2 = Integer.parseInt(pQuantityField.getText());
			}
			int temp1 = Integer.parseInt(pMoneyField.getText());

			ptotalPriceField.setText((String.valueOf((temp1 * temp2))));
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(this, "숫자를 입력해 주세요.");
		}
	}// 수량을 입력할 때 값이 변경된다.

	@Override
	public void removeUpdate(DocumentEvent e) {
		try {
			int temp2;
			if (pQuantityField.getText().equals("")) {
				temp2 = 0;
			} else {
				temp2 = Integer.parseInt(pQuantityField.getText());
			}
			int temp1 = Integer.parseInt(pMoneyField.getText());

			ptotalPriceField.setText((String.valueOf((temp1 * temp2))));
		} catch (Exception e2) {
		}
	}// 수량을 지울때 때 값이 변경된다.

	@Override
	public void changedUpdate(DocumentEvent e) {
	}// 이 메서드는 정의하지 않습니다.

	public void venderInsert() {
		
		ArrayList<H_VenderDTO> list = h_venderDAO.selectALLVenderInfo();
		
		for (int i = 0; i <list.size(); i++) {
			venderListModel.insertRow(0, new Object[] {list.get(i).getName(),list.get(i).getTel()});
		}
	
		
	}
}// 클래스 끝
