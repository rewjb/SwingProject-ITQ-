package head;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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

public class H_CheckOrder extends JPanel implements HeadCheckOrder, ActionListener {

	private DefaultTableModel orderListModel = new DefaultTableModel(0, 5);
	private JTable orderListTable = new JTable(orderListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 가맹점의 발주 리스트(확인하지 않은..)를 넣을 Jtable / 오면서 셀을 수정여부 메서드를 무조건 false값으로 리턴

	private DefaultTableModel franchiseListModel = new DefaultTableModel(0, 2);
	private JTable franchiseListTable = new JTable(franchiseListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 가맹점의 연락처 목록보기

	private JScrollPane orderScroll = new JScrollPane(orderListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 발주내용의 스크롤 기능 객체

	private JScrollPane franchiseScroll = new JScrollPane(franchiseListTable,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	private DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
	// Jtable의 가운데 정렬 객체

	private JButton previousBtn = new JButton("이전");
	private JButton nowBtn = new JButton();
	private JButton nextBtn = new JButton("다음");
	private JButton reStartBtn = new JButton("새로고침");

	private int index = 1;
	private int count;
	private int listNum = 30;

	private B_OrderDAO b_orderDAO = B_OrderDAO.getInstance();
	// 가맹점 발주 기록 갖고오기
	private ArrayList<B_OrderDTO> orderList;

	private H_FranchiseDAO franchiseDAO = H_FranchiseDAO.getInstance();

	private JButton confirmBtn = new JButton("확인");

	private JLabel title1 = new JLabel("가맹점 발주내역");
	private JLabel title2 = new JLabel("가맹점 연락처");

	ArrayList<Integer> uniqueNum;

	public static int goOrder;

	public H_CheckOrder() {
		title1.setBackground(Color.RED);

		title1.setBounds(230, 1, 100, 20);
		title2.setBounds(625, 1, 100, 20);
		// 테이블당 제목 붙이기
		
		title2.setOpaque(true);

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
		orderListModel.setColumnIdentifiers(new String[] { "가맹점명", "상품명", "수량", "발주일", "확인여부" });

//		orderListTable.getColumnModel().getColumn(1).setPreferredWidth(300);
		// 컬럼 너비를 수정하는 메서드 , 그러나 여기에서는 스크롤팬에 맞춰서 설정된듯 하다 ..

//		orderDAO = B_OrderDAO.getInstance().select_UnCheck(index);
//		데이터를 얻어오는 것

		nextBtn.addActionListener(this);
		previousBtn.addActionListener(this);
		confirmBtn.addActionListener(this);
		reStartBtn.addActionListener(this);
		// 인덱스 숫자 액션리스너

		previousBtn.setBounds(176, 336, 60, 20);
		nowBtn.setBounds(233, 336, 60, 20);
		nextBtn.setBounds(293, 336, 60, 20);
		confirmBtn.setBounds(502, 336, 60, 20);
		reStartBtn.setBounds(668, 335, 97, 23);
		// 버튼들의 배치

		orderScroll.setBounds(2, 20, 560, 315);
		franchiseScroll.setBounds(565, 20, 200, 315);
		// 2개 스크롤팬의 배치

		celAlignCenter.setHorizontalAlignment(SwingConstants.CENTER);
		// 가운데 정렬 설정의 객체

		for (int i = 0; i < 5; i++) {
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
		
		title1.setForeground(Color.WHITE);
		title2.setForeground(Color.WHITE);

		title2.setBackground(Color.BLACK);
		
		
		add(title1);
		add(title2);
		add(confirmBtn);
		add(franchiseScroll);
		add(previousBtn);
		add(nowBtn);
		add(nextBtn);
		add(orderScroll);
		add(reStartBtn);
		setLayout(null);
		setBounds(0, 0, 770, 358);
		
		setBackground(Color.gray);
//		194 226 208
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
			previousBtn.setEnabled(false);
		} else {
			previousBtn.setEnabled(true);
		}

		if (index == (int) (count / listNum + 1)) {
			nextBtn.setEnabled(false);
		} else {
			nextBtn.setEnabled(true);
		}

		if ((index == (int) (count / listNum)) && ((count % listNum) == 0)) {
			nextBtn.setEnabled(false);
		}
	}// assignBtnIndex():메서드 끝

	public void orderInsert(int index) {

		orderList = b_orderDAO.selectAllPlusAlias();

		count = orderList.size();

		int temp = orderListModel.getRowCount();

		uniqueNum = new ArrayList<>();

		for (int i = 0; i < temp; i++) {
			orderListModel.removeRow(0);
		}

		if (((int) (count / listNum) + 1) == index) {
			int startNum = (int) (index - 1) * listNum;
			for (int i = 0; i < (count % listNum); i++) { // 마지막 index
				orderListModel.insertRow(orderListModel.getRowCount(),
						new Object[] { orderList.get(startNum + i).getAlias(), orderList.get(startNum + i).getName(),
								orderList.get(startNum + i).getQuantity(), orderList.get(startNum + i).getDate(),
								orderList.get(startNum + i).gethComfirm() });
				uniqueNum.add(orderList.get(startNum + i).getNum());
			}
		} else {
			int startNum = (int) (index - 1) * listNum;
			for (int i = 0; i < listNum; i++) { // 마지막 index가 아님
				orderListModel.insertRow(orderListModel.getRowCount(),
						new Object[] { orderList.get(startNum + i).getAlias(), orderList.get(startNum + i).getName(),
								orderList.get(startNum + i).getQuantity(), orderList.get(startNum + i).getDate(),
								orderList.get(startNum + i).gethComfirm(), orderList.get(startNum + i).gethComfirm() });
				uniqueNum.add(orderList.get(startNum + i).getNum());
			}
		}
		assignBtnIndex();
	}// orderInsert():메서드 끝

	public void aliasNtelInsert() {
		int count = franchiseListModel.getRowCount();
		for (int i = 0; i < count; i++) {
			franchiseListModel.removeRow(0);
		}

		ArrayList<H_FranchiseDTO> franchiseArray = franchiseDAO.select_AliasNTel();
		for (int i = 0; i < franchiseArray.size(); i++) {
			franchiseListModel.insertRow(i,
					new Object[] { franchiseArray.get(i).getAlias(), franchiseArray.get(i).getTel() });
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

			int[] selectedIndex = orderListTable.getSelectedRows();
			int selectedCount = orderListTable.getSelectedRowCount();

			for (int i = 0; i < selectedCount; i++) {
				b_orderDAO.checkUpdate(uniqueNum.get(selectedIndex[i]));
			}
			orderInsert(index);
		} // confirmBtn:버튼 액션 끝

		if (e.getSource() == reStartBtn) {
			aliasNtelInsert();
			orderInsert(index);
		}

	}// actionPerformed:메서드 끝
}// 클래스 끝
