package head;

//1. 계층도
//하위 내용은 클래스 계층도 입니다.
//기본적으로 인터페이스 계층도 이며 () 괄호안에 들어가 있는 것은 인터페이스 및 클래스 여부입니다.
//1) 기호정리
//   괄호 :  I(인터페이스) , C(클래스)
//    ↑ : 아래에 위치한 클래스를 위에 있는 클래스에서 객체로 만들어 사용
//    │ : 상속 및 구현
//                                                                                                BBQ(I)                                                                          
//                                                       ┌───────────────────────────────────────────┴──────────────────────────────────────────────────────────────────────────────────────────┐
//                                                   BBQHead(I)                                                                                                                             BBQBody(I) 
//     ┌───────────────────┌────────────────────┌────────┴────────────┐──────────────────┐─────────────────┐────────────────┐──────────────────────────┐                   ┌────────────┌───────┴───────┐───────────────┐ 
//HeadCheckOrder(I)    HeadOrder(I)       HeadStockInOut(I)       HeadVender(I)  HeadVenderProduct(I) HeadFranchise(I)   HeadSales(I)            H_ChattingFrame(C)     BodyHall(I)   BodyOrder(I)   BodySales(I)   BodyStock(I)
//     │                   │                    │                     │                  │                 │                │                          ↑                   │            │               │               │
//H_CheckOrder(C)      H_Order(C)         H_StockInOut(C)         H_Vender(C)       H_V_Product(C)    H_Franchise(C)     H_Salses(C)            ┌───────│─────────────┐ B_HallC(C)   B_OrderC(C)     B_SalesC(C)     B_StockC(C)
//                                                                                                            ┌─────────────↑────────────────┐  │H_ChattingJoin(C)    │                     ┌────────────↑─────────────┐
//                                                                                                            │ H_Salses_BodySalesBarChart(C)│  │       ↑             │                     │B_SalesC_SalesDataChart(C)│ 
//                                                                                                            │ H_Salses_BodySalesPieChart(C)│  │H_ChattingrManager(C)│                     └──────────────────────────┘
//                                                                                                            │H_Salses_HeadSalsesBarChart(C)│  └─────────────────────┘
//                                                                                                            └──────────────────────────────┘
//2. 변수명
//1) 전역변수 식별자 특징
//  => g_식별자  (g : 글로벌 변수)  ,  ex) g_BtnSelect

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
import DTO_DAO.H_StockDAO;
import DTO_DAO.H_StockDTO;
import DTO_DAO.H_VenderDAO;
import DTO_DAO.H_VenderDTO;
import DTO_DAO.H_VenderpDAO;
import inter.BBQHead;
import inter.HeadCheckOrder;
import inter.HeadOrder;

public class H_Order extends JPanel implements HeadOrder, ActionListener, ItemListener, DocumentListener {

	private DefaultTableModel g_OrderListModel = new DefaultTableModel(0, 5);
	private JTable g_OrderListTable = new JTable(g_OrderListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private JScrollPane g_OrderScroll = new JScrollPane(g_OrderListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 발주 목록을 넣어줄 스크롤 및 테이블 선언 , 초기화

	private DefaultTableModel g_VenderListModel = new DefaultTableModel(0, 2);
	private JTable g_VenderListTable = new JTable(g_VenderListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private JScrollPane g_VendereScroll = new JScrollPane(g_VenderListTable,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 업체의 연락체 및 업체명을 넣어줄 스크롤 및 테이블 선언 , 초기화
	// 해당업체와의 연락이 자주 있으므로 연락처를 보여주는 테이블이다.

	private DefaultTableModel g_OrderPlusListModel = new DefaultTableModel(0, 5);
	private JTable g_OrderPlusListTable = new JTable(g_OrderPlusListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private JScrollPane g_OrderPlusScroll = new JScrollPane(g_OrderPlusListTable,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 데이터베이스에 있는 발주목록에 발주 레코드를 추가하기 전 발주 장바구니 테이블인 스크롤 및 테이블

	private DefaultTableModel g_StockListModel = new DefaultTableModel(0, 2);
	private JTable g_StockListTable = new JTable(g_StockListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private JScrollPane g_StockScroll = new JScrollPane(g_StockListTable,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 발주를 넣을 때 , 현재 재고현황을 참고사항으로 볼 수 있는 테이블이다.

	private DefaultTableCellRenderer g_CelAlignCenter = new DefaultTableCellRenderer();
	// 현재 클래스에서 테이블 내용의 가운데 정렬을 위한 선언 및 초기화이다.

	private JButton g_PreviousBtn = new JButton();
	private JButton g_NowBtn = new JButton();
	private JButton g_NextBtn = new JButton();
	// 발주목록의 이전,현재,다음 버튼이다.

	private int g_Btnindex = 1;
	// 발주목록에 들어가는 현재 인덱스 번호

	private int g_Ordercount;
	// 발주목록을 갖고오는 과정에서 발주 목록의 전체 레코드 수량을 넣을 변수

	private int g_ShowlistNum = 9;
	// 발주목록을 한 페이지당 몇개의 행을 표현할지 정하는 변수

	private H_FranchiseDAO franchiseDAO = H_FranchiseDAO.getInstance();

	private JButton g_OrderplusBtn = new JButton("추가");
	// 데이터베이스에 발주 데이터를 넣기 전 발주 장바구니에 추가하는 버튼
	private JButton g_ConfirmOrderBtn = new JButton("확인");
	// 장바구니에 있는 발주 목록을 데이터베이스인 발주목록으로 추가하는 버튼
	private JButton g_OrderPlusDeleteBtn = new JButton("삭제");
	// 발주 장바구니에 있는 목록을 삭제하는 버튼
	private JButton g_OrderDeleteBtn = new JButton("삭제");
	// 데이터 베이스에 있는 발주 목록을 삭제하는 버튼
	private JButton g_ReStartBtn = new JButton("새로고침");
	// 새로고침 버튼

	private JLabel g_NameLabel = new JLabel("발주품목");
	// "발주품목" 문자열이 들어 있는 레이블
	private JLabel g_VenderLabel = new JLabel("업체");
	// "업체" 문자열이 들어 있는 레이블
	private JLabel g_MoneyLabel = new JLabel("가격");
	// "가격" 문자열이 들어 있는 레이블
	private JLabel g_QuantityLabel = new JLabel("수량");
	// "수량" 문자열이 들어 있는 레이블
	private JLabel g_TotalPriceLabel = new JLabel("총가격");
	// "총가격" 문자열이 들어 있는 레이블
	private JLabel g_VenderInfoLabel = new JLabel("업체 연락처");
	// "업체 연락처" 문자열이 들어 있는 레이블
	private JLabel g_StockInfoLabel = new JLabel("재고확인");
	// "재고 확인" 문자열이 들어 있는 레이블
	private JLabel g_OrderListLabel = new JLabel("발주목록");
	// "발주목록" 문자열이 들어 있는 레이블

	private JComboBox<String> g_PnameComboBox = new JComboBox<>();
	// 발주품목 선택
	private JComboBox<String> g_PvenderComboBox = new JComboBox<>();
	// 발주품목에 대한 업체 선택
	private JTextField g_PmoneyField = new JTextField();
	// 제품 선택시 가격 표시
	private JTextField g_PquantityField = new JTextField();
	// 수량 입력란
	private JTextField g_PtotalPriceField = new JTextField();
	// 발주의 총 가격을 표시하는 텍스트 필드

	// 싱글톤 업체제품의 DAO 선언
	private ArrayList<String> g_ProductList;
	// 제품군만 갖고오는 스트링 list
	private ArrayList<String[]> g_VenderList;
	// 제품군에 맞는 업체를 갖고오는 리스트
	private ArrayList<H_OrderDTO> g_OrderList;
	// 발주 기록을 갖고올 orderList
	private ArrayList g_OrderuniqueNum;
	// 발주기록을 지우기 위한 유니크 넘버 갖기

	public H_Order() {

		g_NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_VenderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_MoneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_QuantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_TotalPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_VenderInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_StockInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_OrderListLabel.setHorizontalAlignment(SwingConstants.CENTER);
		// 현재 클래스에 존재하는 라벨의 중앙정렬

		g_OrderListLabel.setBounds(245, 155, 69, 15);
		g_StockInfoLabel.setBounds(625, 160, 80, 15);
		g_VenderInfoLabel.setBounds(625, 7, 80, 15);
		g_NameLabel.setBounds(32, 7, 60, 15);
		g_VenderLabel.setBounds(152, 7, 40, 15);
		g_MoneyLabel.setBounds(244, 7, 40, 15);
		g_QuantityLabel.setBounds(322, 7, 40, 15);
		g_TotalPriceLabel.setBounds(420, 7, 40, 15);
		// 현재 클래스에 존재하는 라벨의 사이즈 지정 및 배치

		g_PmoneyField.setHorizontalAlignment(SwingConstants.RIGHT);
		g_PquantityField.setHorizontalAlignment(SwingConstants.RIGHT);
		g_PtotalPriceField.setHorizontalAlignment(SwingConstants.RIGHT);
		// 현재 클래스에 존재하는 TextField의 오른쪽 정렬

		g_PmoneyField.setBounds(230, 23, 70, 20);
		g_PquantityField.setBounds(312, 23, 60, 20);
		g_PtotalPriceField.setBounds(384, 23, 111, 20);
		// 현재 클래스에 존재하는 TextField의 사이지 지정 및 배치

		g_PmoneyField.setEditable(false);
		g_PtotalPriceField.setEditable(false);
		// TextField 중 .. 가격 및 총가격 필드의 수정금지

		g_PnameComboBox.setBounds(12, 23, 100, 20);
		g_PvenderComboBox.setBounds(122, 23, 100, 20);
		// 품명 콤보박스, 업체 콤보박스 사이즈 지정 및 배치

		g_OrderScroll.setBounds(2, 170, 560, 165);
		g_VendereScroll.setBounds(565, 25, 200, 130);
		g_OrderPlusScroll.setBounds(2, 44, 560, 88);
		g_StockScroll.setBounds(565, 180, 200, 155);
		// 현재 클래스에 존재하는 스크롤 사이즈 지정 및 배치

		g_PreviousBtn.setBounds(180, 336, 60, 20);
		g_NowBtn.setBounds(240, 336, 60, 20);
		g_NextBtn.setBounds(300, 336, 60, 20);
		g_OrderDeleteBtn.setBounds(502, 336, 60, 20);
		g_OrderplusBtn.setBounds(502, 23, 60, 20);
		g_OrderPlusDeleteBtn.setBounds(502, 133, 60, 20);
		g_ConfirmOrderBtn.setBounds(435, 133, 60, 20);
		g_ReStartBtn.setBounds(668, 335, 97, 23);
		// 버튼들의 사이즈 지정 및 배치

		g_NowBtn.setText(String.valueOf(g_Btnindex));
		// 버튼의 초기값

		g_OrderListTable.getTableHeader().setResizingAllowed(false);
		g_OrderListTable.getTableHeader().setReorderingAllowed(false);
		// 발주테이블의 헤더를 얻어서 사이즈 수정 불가 , 컬럼 이동 금지 및 사이즈조절 금지

		g_VenderListTable.getTableHeader().setResizingAllowed(false);
		g_VenderListTable.getTableHeader().setReorderingAllowed(false);
		// 업체테이블의 헤더를 얻어서 사이즈 수정 불가, / 컬럼 이동 금지 및 사이즈조절 금지

		g_OrderPlusListTable.getTableHeader().setResizingAllowed(false);
		g_OrderPlusListTable.getTableHeader().setReorderingAllowed(false);
		// 발주품목 추가 테이블의 헤더를 얻어서 사이즈 수정 불가, / 컬럼 이동 금지 및 사이즈조절 금지

		g_StockListTable.getTableHeader().setResizingAllowed(false);
		g_StockListTable.getTableHeader().setReorderingAllowed(false);
		// 재고확인 테이블의 헤더를 얻어서.., / 컬럼 이동 금지 및 사이즈조절 금지

		g_VenderListModel.setColumnIdentifiers(new String[] { "가맹점명", "전화번호" });
		g_OrderListModel.setColumnIdentifiers(new String[] { "업체", "발주품목", "수량", "금액", "발주일" });
		g_OrderPlusListModel.setColumnIdentifiers(new String[] { "발주품목", "업체", "가격정보", "수량", "총금액" });
		g_StockListModel.setColumnIdentifiers(new String[] { "품목", "현재고/완료재고" });
		// 각 테이블의 컬럼을 결정하기

		g_OrderListTable.getColumnModel().getColumn(4).setPreferredWidth(180);
		g_StockListTable.getColumnModel().getColumn(1).setPreferredWidth(130);
		// 컬럼 너비를 수정하는 메서드 , 그러나 여기에서는 스크롤팬에 맞춰서 설정된듯 하다 ..

//		orderDAO = B_OrderDAO.getInstance().select_UnCheck(index);
//		데이터를 얻어오는 것

		g_NextBtn.addActionListener(this);
		g_PreviousBtn.addActionListener(this);
		g_OrderDeleteBtn.addActionListener(this);
		g_OrderPlusDeleteBtn.addActionListener(this);
		g_ConfirmOrderBtn.addActionListener(this);
		g_ReStartBtn.addActionListener(this);
		g_OrderplusBtn.addActionListener(this);
		// 현재 클래스의 버튼 액션리스너

		g_PnameComboBox.addItemListener(this);
		g_PvenderComboBox.addItemListener(this);
		// 현재 클래스의 콤보박스 액션리스너

		g_PquantityField.getDocument().addDocumentListener(this);
		// 수량을 입력할 때 작동하는 액션리스너

		g_CelAlignCenter.setHorizontalAlignment(SwingConstants.CENTER);
		// 가운데 정렬 설정의 객체

		for (int i = 0; i < 5; i++) {
			g_OrderListTable.getColumnModel().getColumn(i).setCellRenderer(g_CelAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅
		for (int i = 0; i < 2; i++) {
			g_VenderListTable.getColumnModel().getColumn(i).setCellRenderer(g_CelAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅
		for (int i = 0; i < 5; i++) {
			g_OrderPlusListTable.getColumnModel().getColumn(i).setCellRenderer(g_CelAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅
		for (int i = 0; i < 2; i++) {
			g_StockListTable.getColumnModel().getColumn(i).setCellRenderer(g_CelAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅
			// 상위 for문들은 테이블내에 있는 셀 내용을 중앙정렬을 시키는 과정입니다.

		g_OrderListTable.setBackground(Color.LIGHT_GRAY);
		g_VenderListTable.setBackground(Color.LIGHT_GRAY);
		g_OrderPlusListTable.setBackground(Color.LIGHT_GRAY);
		g_StockListTable.setBackground(Color.LIGHT_GRAY);
		// 모드 테이블의 데이터 배경을 Light Gray 로 변경

		assignBtnIndex();
		// 초기 인덱스 번호에 따른 버튼 속성 부여
		venderInsert();
		// 업체들 전화번호 갖고오는 메서드
		stockInsert();
		// 현재 재고현황을 갖고오는 메서드
		insertIntoNameBox();
		// 발주품목을 콤보박스에 넣기
		insertIntoVenderBox();
		// 품목에 따른 업체를 콤보박스에 넣기
		orderInsert(g_Btnindex);
		// 발주 길록을 입력하는 메서드 index는 페이지 번호

		add(g_PnameComboBox);
		add(g_PvenderComboBox);
		// 콤보박스 추가

		add(g_OrderDeleteBtn);
		add(g_VendereScroll);
		add(g_PreviousBtn);
		add(g_NowBtn);
		add(g_NextBtn);
		add(g_OrderplusBtn);
		add(g_OrderPlusDeleteBtn);
		add(g_ReStartBtn);
		add(g_ConfirmOrderBtn);
		// 버튼 추가

		add(g_OrderScroll);
		add(g_OrderPlusScroll);
		add(g_StockScroll);
		// 스크롤 추가

		add(g_PmoneyField);
		add(g_PquantityField);
		add(g_PtotalPriceField);
		// 텍스트 필드 추가

		add(g_NameLabel);
		add(g_VenderLabel);
		add(g_MoneyLabel);
		add(g_QuantityLabel);
		add(g_TotalPriceLabel);
		add(g_OrderListLabel);
		add(g_VenderInfoLabel);
		add(g_StockInfoLabel);
		// 레이블 추가

		setLayout(null);
		setBounds(0, 0, 770, 368);
		setBackground(new Color(184, 207, 229));
		setVisible(false);
		// 레이아웃 , 사이즈 지정 및 배치, 배경, 화면보이기 여부 설정

	}// 생성자 끝

	private void insertIntoNameBox() {
		// 이 메서드는 GUI에서 발주를 넣을 때 제품군을 고르는 콤보박스에 내용을 삽입하는 메서드 입니다.
		// 기존에 있던 아이템을 삭제하고 새로운 제품군을 넣는 메서드

		g_ProductList = H_VenderpDAO.getInstance().select_product();
		// 발주를 넣을 수 있는 제품군을 얻어오는 과정

		g_PnameComboBox.removeAllItems();
		// 기존에 콤보박스에 있는 목록 삭제

		for (int i = 0; i < g_ProductList.size(); i++) {
			g_PnameComboBox.addItem((String) g_ProductList.get(i));
		}
		// 제품군 콤보박스에 제품군 삽입하는 과정

	}// insertIntoNameBox:메서드 종료

	private void insertIntoVenderBox() {
		// GUI에서 제품군을 선택하는 콤보박스에서 제품군을 선택하면
		// 자동적으로 업체를 고르는 콤보박스에 내용이 들어가는 것을 볼 수 있는데..
		// 이때 제품군이 삽입되는 메서드

		g_VenderList = H_VenderpDAO.getInstance().select_vender();
		// 제품군에 맞는 전체 고유번호,제품군,업체명,금액이 들어있다.

		g_PvenderComboBox.removeAllItems();
		// 기존에 있던 업체내용을 삭제

		for (int i = 0; i < g_VenderList.size(); i++) {
			if (g_VenderList.get(i)[1].equals((String) g_PnameComboBox.getSelectedItem())) {
				// 제품군 콤보박스에 들어있는 제품을 판매하는 업체를 고르는 조건문

				g_PvenderComboBox.addItem(g_VenderList.get(i)[2]);
			}
		} // 업체명 콤보박스에 제품군 삽입하는 과정

	}// insertIntoVenderBox:메서드 종료

	private void insertMoneyField() {
		// GUI에서 제품군과 업체가 선정되었다면 가격이 자동을 입력되는 것을 볼 수 있다.
		// 이때 가격이 입력되는 메서드

		for (int i = 0; i < g_VenderList.size(); i++) {

			if ((g_VenderList.get(i)[1].equals(g_PnameComboBox.getSelectedItem()))
					&& (g_VenderList.get(i)[2].equals(g_PvenderComboBox.getSelectedItem()))) {
				// 제품군과 업체가 치하는 가격을 갖고 온다.
				g_PmoneyField.setText(g_VenderList.get(i)[3]);
			}
		}

	}// insertMoneyField:메서드 종료

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == g_PnameComboBox) {
			// 제품군 콤보박스가 작동할 때
			insertIntoVenderBox();
		}
		if (e.getSource() == g_PvenderComboBox) {
			// 업체명 콤보박스가 작동할 때
			insertMoneyField();
		}
	}// itemStateChanged:메서드 종료

	private void assignBtnIndex() {
		// 발주목록 테이블에서 현재 위치한 index에 따라 다음과 이전 버튼의
		// 속성이 결정되는 메서드이다.

		if (g_Btnindex == 1) {
			// 현재 위치가 1번 index일 때
			g_PreviousBtn.setText("");
			g_PreviousBtn.setEnabled(false);
		} else {
			g_PreviousBtn.setText(String.valueOf(g_Btnindex - 1));
			g_PreviousBtn.setEnabled(true);
		}

		if (g_Btnindex == (int) (g_Ordercount / g_ShowlistNum + 1) && g_Ordercount % g_ShowlistNum == 0) {
			// 현재 페이지가 마지막 페이지이며 보여줄 리스트에 딱 맞는 행의 수량일 경우
			g_NextBtn.setText("");
			g_NextBtn.setEnabled(false);
			g_PreviousBtn.doClick();
			// 마지막 페이지에 내용이 없을 경우 클릭시 다시 이전 페이지로 돌아와진다.
		} else if ((g_Btnindex == (int) (g_Ordercount / g_ShowlistNum + 1) && g_Ordercount % g_ShowlistNum != 0)) {
			// 마지막 페이지 이며 보여줄 리스트의 수량보다 적을 경우
			g_NextBtn.setText("");
			g_NextBtn.setEnabled(false);
		} else {
		    // 1페이지도 아니며 마지막 페이지도 아닐 경우
			g_NextBtn.setText(String.valueOf(g_Btnindex + 1));
			g_NextBtn.setEnabled(true);
		}
	}// assignBtnIndex():메서드 끝

	private void orderInsert(int index) {
		//발주 목록을 현재 위치한 index 번호에 따라 목록을 삽입하는 메서드

		// B_OrderDAO의 배열값 객체 반환
		g_Ordercount = H_OrderDAO.getInstance().selectAll().size();
		// 전체 리스트 수량
		g_OrderList = H_OrderDAO.getInstance().selectAll();
		// 전체 데이터베이스 값을 갖고 있는 리스트

		int lastListNumBefore = g_OrderListModel.getRowCount();
		// 기존의 마지막 페이지의 자료 갯수

		int lastListNumAfter = (g_Ordercount % g_ShowlistNum);
		// 마지막 index의 자료 갯수 , 전체필드%9 + 1

		g_OrderuniqueNum = new ArrayList<>();
		// Interger타입으로 고유번호를 넣는 리스트

		if (g_OrderListModel.getRowCount() > 0) {
			for (int i = 0; i < lastListNumBefore; i++) {
				g_OrderListModel.removeRow(0);
			} // 마지막 페이지의 필드 수량만큼 삭제
		}

		if (((int) (g_Ordercount / g_ShowlistNum) + 1) == index) {
			// 마지막 페이지일 경우
			for (int i = 0; i < lastListNumAfter; i++) {
				g_OrderListModel.insertRow(i,
						new Object[] { g_OrderList.get((index - 1) * g_ShowlistNum + i).getVendername(),
								g_OrderList.get((index - 1) * g_ShowlistNum + i).getName(),
								g_OrderList.get((index - 1) * g_ShowlistNum + i).getQuantity(),
								g_OrderList.get((index - 1) * g_ShowlistNum + i).getMoney(),
								g_OrderList.get((index - 1) * g_ShowlistNum + i).getDate().subSequence(0, 16),
								g_OrderList.get((index - 1) * g_ShowlistNum + i).getConfirm() });
				g_OrderuniqueNum.add(g_OrderList.get((index - 1) * g_ShowlistNum + i).getNum());
			}
		} else {
			// 마지막 페이지가 아닐 경우
			for (int i = 0; i < g_ShowlistNum; i++) {
				g_OrderListModel.insertRow(i,
						new Object[] { g_OrderList.get((index - 1) * g_ShowlistNum + i).getVendername(),
								g_OrderList.get((index - 1) * g_ShowlistNum + i).getName(),
								g_OrderList.get((index - 1) * g_ShowlistNum + i).getQuantity(),
								g_OrderList.get((index - 1) * g_ShowlistNum + i).getMoney(),
								g_OrderList.get((index - 1) * g_ShowlistNum + i).getDate().subSequence(0, 16),
								g_OrderList.get((index - 1) * g_ShowlistNum + i).getConfirm() });
				g_OrderuniqueNum.add(g_OrderList.get((index - 1) * g_ShowlistNum + i).getNum());
			}
		}
		
		assignBtnIndex();
		//발주목록 리스트에 레코드 삽입을 완료한 후 아래 페이지 버튼의 속성을 부여
		
	}// orderInsert():메서드 끝

	private void stockInsert() {
		// 발주를 넣을 때 현재 존재하는 전체 재고수량을 보고 무엇인가 잘못 되었다고 판단이 가능할때가 있다.
		// 그렇기 때문에 참고사항으로 넣어주는 테이블이다.
		
		ArrayList<H_StockDTO> totalStockList = H_StockDAO.getInstance().selectTotalStock();
		// 현재 총 재고내용을 갖고 있는 리스트
		
		int tempcount = g_StockListModel.getRowCount();
		// 다용도 int 변수 선언 , 위 문장에서는 재고 테이블에서 갖고 있는 행의 수량으로 초기화
		
		if (tempcount > 0) {
			for (int i = 0; i < tempcount; i++) {
				g_StockListModel.removeRow(0);
			}
		}// 기존 테이블에 있는 내용을 전부 삭제하는 for문 
		
		for (int i = 0; i < totalStockList.size(); i++) {
			g_StockListModel.insertRow(0,
					new Object[] { totalStockList.get(i).getName(), totalStockList.get(i).getQuantity() });
		}// 재고테이블에 레코드를 삽입
		
	}// stockInsert() : 메서드 종료

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == g_NextBtn) {
			if ((g_Btnindex + 1) <= (int) (g_Ordercount / g_ShowlistNum + 1)) {
				++g_Btnindex;
				g_NowBtn.setText(String.valueOf(g_Btnindex));
				assignBtnIndex();
				orderInsert(g_Btnindex);
			}
		} // 다음 버튼을 누를 시 발생하는 액션

		if (e.getSource() == g_PreviousBtn) {
			if (!((g_Btnindex - 1) == 0)) {
				--g_Btnindex;
				g_NowBtn.setText(String.valueOf(g_Btnindex));
				assignBtnIndex();
				orderInsert(g_Btnindex);
			}
		} // 이전 버튼을 누를 시 발생하는 액션

		if (e.getSource() == g_OrderDeleteBtn) {

			int[] selectedIndex = g_OrderListTable.getSelectedRows();

			int countNum = g_OrderListTable.getSelectedRows().length;
			for (int i = 0; i < countNum; i++) {
				H_OrderDAO.getInstance().deleteSelected((int) g_OrderuniqueNum.get(selectedIndex[i]));
			}
			
			orderInsert(g_Btnindex);
		} // 발주기록을 지우는 메서드

		// 추가버튼을 누르면 선택한 항목이 발주 바구니에 이동한다.
		// 이때 수량이 int값으로 전환을 시도하는데
		if (e.getSource() == g_OrderplusBtn) {
			try {
				Integer.parseInt(g_PquantityField.getText());
				g_OrderPlusListModel.insertRow(0,
						new Object[] { g_PnameComboBox.getSelectedItem(), g_PvenderComboBox.getSelectedItem(),
								g_PmoneyField.getText(), g_PquantityField.getText(), g_PtotalPriceField.getText() }); // 5개
																														// 입력임
				g_PquantityField.setText("");
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "수량에 숫자를 입력해 주세요.");
			}
		} // plusBtn:버튼 if문 종료

		if (e.getSource() == g_OrderPlusDeleteBtn) {
			if (g_OrderPlusListTable.getRowCount() == 0) {
				JOptionPane.showMessageDialog(this, "항목을 추가해 주세요!");
			} else if (g_OrderPlusListTable.getSelectedRowCount() == 1) {
				g_OrderPlusListModel.removeRow(g_OrderPlusListTable.getSelectedRow());
			} else {
				JOptionPane.showMessageDialog(this, "하나만 선택해 주세요!");
			}
		} // deletemBtn:버튼 if문 종료

		if (e.getSource() == g_ConfirmOrderBtn) {
			ArrayList<H_OrderDTO> list = new ArrayList<>();
			// H_OrderDTO를 담을 리스트
			int[] a = g_OrderListTable.getSelectedRows();
			System.out.println(a);

			H_OrderDTO tempDTO;
			int num = g_OrderPlusListTable.getRowCount(); // 왜 ..그러지 ..?
			for (int i = 0; i < num; i++) {
				tempDTO = new H_OrderDTO();
				tempDTO.setVendername((String) g_OrderPlusListModel.getValueAt(0, 1));
				tempDTO.setName((String) g_OrderPlusListModel.getValueAt(0, 0));
				tempDTO.setQuantity(Integer.parseInt((String) g_OrderPlusListModel.getValueAt(0, 3)));
				tempDTO.setMoney(Integer.parseInt((String) g_OrderPlusListModel.getValueAt(0, 4)));
				g_OrderPlusListModel.removeRow(0);
				list.add(tempDTO);
			} // DTO를 list에 넣는 과정

			H_OrderDAO.getInstance().insert(list);
			orderInsert(g_Btnindex);
		} // confirmOrderBtn:버튼 if문 종료

		if (e.getSource() == g_OrderPlusDeleteBtn) {

		} // deletemBtn : 버튼 if문 종료

		if (e.getSource() == g_ReStartBtn) {
			orderInsert(g_Btnindex);
			insertIntoNameBox();
			venderInsert();
			stockInsert();
		}
	}// actionPerformed:메서드 끝

	@Override
	public void insertUpdate(DocumentEvent e) {
		// 값을 입력할 때 숫자이외 다른 값을 입력하면 제이다이얼로그로 경고를 주며
		// 값을 입력할때마다 가격을 계산한다.
		try {
			Integer.parseInt(g_PquantityField.getText());
			int temp2;
			if (g_PquantityField.getText().equals("")) {
				temp2 = 0;
			} else {
				temp2 = Integer.parseInt(g_PquantityField.getText());
			}
			int temp1 = Integer.parseInt(g_PmoneyField.getText());

			g_PtotalPriceField.setText((String.valueOf((temp1 * temp2))));
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(this, "숫자를 입력해 주세요.");
		}
	}// insertUpdate(DocumentEvent e) : 메서드 종료

	@Override
	public void removeUpdate(DocumentEvent e) {
		// 수량을 입력할때 지속적으로 인지하며 총가격을 변경시킨다.
		try {
			int temp2;
			if (g_PquantityField.getText().equals("")) {
				temp2 = 0;
			} else {
				temp2 = Integer.parseInt(g_PquantityField.getText());
			}
			int temp1 = Integer.parseInt(g_PmoneyField.getText());

			g_PtotalPriceField.setText((String.valueOf((temp1 * temp2))));
		} catch (Exception e2) {
		}
	}// removeUpdate(DocumentEvent e) : 메서드 종료

	@Override
	public void changedUpdate(DocumentEvent e) {
	}// 이 메서드는 정의하지 않습니다.

	public void venderInsert() {
		// 발주를 할때 업체에 연락을 취해야하는 경우가 있다. 이를 위해 업체와 업체의 연락처의 정보를 테이블에 넣는 메서드이다.
		ArrayList<H_VenderDTO> list = H_VenderDAO.getInstance().selectALLVenderInfo();
		int count = g_VenderListModel.getRowCount();

		for (int i = 0; i < count; i++) {
			g_VenderListModel.removeRow(0);
		}

		for (int i = 0; i < list.size(); i++) {
			g_VenderListModel.insertRow(0, new Object[] { list.get(i).getName(), list.get(i).getTel() });
		}
	}// venderInsert() : 메서드 종료

	@Override
	public void show(BBQHead bbqHead) {
	}
	// 인터페이스 BBQHead로 부터 받은 메서드
	// 정의하지 않습니다.

	@Override
	public void hide(BBQHead bbqHead) {
	}
	// 인터페이스 BBQHead로 부터 받은 메서드
	// 정의하지 않습니다.
}// 클래스 끝
