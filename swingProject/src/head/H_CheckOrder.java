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

//1. 계층도
//하위 내용은 클래스 계층도 입니다.
//기본적으로 인터페이스 계층도 이며 () 괄호안에 들어가 있는 것은 인터페이스 및 클래스 여부입니다.
// 1) 기호정리
//     괄호 :  I(인터페이스) , C(클래스)
//      ↑ : 아래에 위치한 클래스를 위에 있는 클래스에서 객체로 만들어 사용
//      │ : 상속 및 구현
//                                                                                                  BBQ(I)                                                                          
//                                                         ┌───────────────────────────────────────────┴──────────────────────────────────────────────────────────────────────────────────────────┐
//                                                     BBQHead(I)                                                                                                                             BBQBody(I) 
//       ┌───────────────────┌────────────────────┌────────┴────────────┐──────────────────┐─────────────────┐────────────────┐──────────────────────────┐                   ┌────────────┌───────┴───────┐───────────────┐ 
//HeadCheckOrder(I)    HeadOrder(I)       HeadStockInOut(I)       HeadVender(I)  HeadVenderProduct(I) HeadFranchise(I)   HeadSales(I)            H_ChattingFrame(C)     BodyHall(I)   BodyOrder(I)   BodySales(I)   BodyStock(I)
//       │                   │                    │                     │                  │                 │                │                          ↑                   │            │               │               │
// H_CheckOrder(C)      H_Order(C)         H_StockInOut(C)         H_Vender(C)       H_V_Product(C)    H_Franchise(C)     H_Salses(C)            ┌───────│─────────────┐ B_HallC(C)   B_OrderC(C)     B_SalesC(C)     B_StockC(C)
//                                                                                                              ┌─────────────↑────────────────┐ │H_ChattingJoin(C)    │                     ┌────────────↑─────────────┐
//                                                                                                              │ H_Salses_BodySalesBarChart(C)│ │       ↑             │                     │B_SalesC_SalesDataChart(C)│ 
//                                                                                                              │ H_Salses_BodySalesPieChart(C)│ │H_ChattingrManager(C)│                     └──────────────────────────┘
//                                                                                                              │H_Salses_HeadSalsesBarChart(C)│ └─────────────────────┘
//                                                                                                              └──────────────────────────────┘
//2. 변수명
// 1) 전역변수 식별자 특징
//    => g_식별자  (g : 글로벌 변수)  ,  ex) g_BtnSelect

public class H_CheckOrder extends JPanel implements HeadCheckOrder, ActionListener {

	private DefaultTableModel g_OrderListModel = new DefaultTableModel(0, 5);
	private JTable g_OrderListTable = new JTable(g_OrderListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private JScrollPane g_OrderScroll = new JScrollPane(g_OrderListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 가맹점의 발주 기록을 넣을 테이블 및 스크롤 객체

	private DefaultTableModel g_BodyListModel = new DefaultTableModel(0, 2);
	private JTable g_BodyListTable = new JTable(g_BodyListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private JScrollPane g_BodyScroll = new JScrollPane(g_BodyListTable,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 가맹점명과 가맹점 연락처를 넣을 테이블 및 스크롤 객체

	private DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
	// 테이블 내용을 가운데 정렬을 위한 객체

	private JButton g_PreviousBtn = new JButton("이전");
	private JButton g_NowBtn = new JButton();
	private JButton g_NextBtn = new JButton("다음");
	private JButton g_ReStartBtn = new JButton("새로고침");

	private int g_OrderTableindex = 1;
	// 가맹점 발주 테이블의 페이지 번호
	private int g_OrderListcount;
	// 가맹점 발주 목록의 수량을 넣을 변수
	private int g_ShowListNum = 30;
	// 가맹점 발주 목록을 얼마나 목록에 출력할지 수량을 정하는 변수

	private JButton g_ConfirmBtn = new JButton("확인");
	//가맹점 발주를 확인하는 버튼

	private JLabel g_BodyOrderLabel = new JLabel("가맹점 발주내역");
	private JLabel g_BodyInfoLabel = new JLabel("가맹점 연락처");
	// 문자열을 갖고 있는 레이블

	ArrayList<Integer> g_UniqueNum;
	//발주목록을 넣을 때 DB에 저장된 유니크 넘버를 넣는 리스트

	public H_CheckOrder() {

		g_BodyOrderLabel.setBounds(230, 1, 100, 20);
		g_BodyInfoLabel.setBounds(625, 1, 100, 20);
		// 테이블당 제목 붙이기

		orderInsert(g_OrderTableindex);
		// 발주 길록을 입력하는 메서드 index는 페이지 번호

		g_NowBtn.setText(String.valueOf(g_OrderTableindex));
		// 버튼의 초기값

		assignBtnIndex();
		// 초기 인덱스 번호에 따른 버튼 속성 부여
		
		aliasNtelInsert();
		// 업체들 전화번호 갖고오는 메서드

		g_OrderListTable.getTableHeader().setResizingAllowed(false);
		g_OrderListTable.getTableHeader().setReorderingAllowed(false);
		// 발주테이블의 헤더를 얻어서 사이즈 수정 불가 , 발주테이블의 컬럼 이동 금지

		g_BodyListTable.getTableHeader().setResizingAllowed(false);
		g_BodyListTable.getTableHeader().setReorderingAllowed(false);
		// 업체테이블의 헤더를 얻어서 사이즈 수정 불가, / 업체테이블의 컬럼 이동 금지

		g_BodyListModel.setColumnIdentifiers(new String[] { "가맹점명", "전화번호" });
		g_OrderListModel.setColumnIdentifiers(new String[] { "가맹점명", "상품명", "수량", "발주일", "확인여부" });
		// 테이블들의 컬럼 설정

		g_OrderListTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		// 발주일 컬럼의 너비설정

		g_NextBtn.addActionListener(this);
		g_PreviousBtn.addActionListener(this);
		g_ConfirmBtn.addActionListener(this);
		g_ReStartBtn.addActionListener(this);
		// 인덱스 숫자 액션리스너

		g_PreviousBtn.setBounds(176, 336, 60, 20);
		g_NowBtn.setBounds(233, 336, 60, 20);
		g_NextBtn.setBounds(293, 336, 60, 20);
		g_ConfirmBtn.setBounds(502, 336, 60, 20);
		g_ReStartBtn.setBounds(668, 335, 97, 23);
		// 버튼들의 배치

		g_OrderScroll.setBounds(2, 20, 560, 315);
		g_BodyScroll.setBounds(565, 20, 200, 315);
		// 2개 스크롤팬의 배치

		celAlignCenter.setHorizontalAlignment(SwingConstants.CENTER);
		// 가운데 정렬 설정의 객체

		for (int i = 0; i < 5; i++) {
			g_OrderListTable.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅
		for (int i = 0; i < 2; i++) {
			g_BodyListTable.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅
		
		g_OrderListTable.setBackground(Color.LIGHT_GRAY);
		g_BodyListTable.setBackground(Color.LIGHT_GRAY);
		//테이블의 배경 지정

		add(g_BodyOrderLabel);
		add(g_BodyInfoLabel);
		add(g_ConfirmBtn);
		add(g_BodyScroll);
		add(g_PreviousBtn);
		add(g_NowBtn);
		add(g_NextBtn);
		add(g_OrderScroll);
		add(g_ReStartBtn);
		// GUI컴포넌트들 추가
		
		setLayout(null);
		setBounds(0, 0, 770, 368);
		setBackground(new Color(184, 207, 229));
		setVisible(false);
		// 현재 클래스의 레이아웃 , 사이즈 지정 및 배치, 배경 설정

	}// 생성자 끝

	private void assignBtnIndex() {
		//가맹점의 발주 목록 페이지에 따라 다음 및 이전 버튼의 속성을 부여하는 메서드
		
		if (g_OrderTableindex == 1) {
			// 현재 페이지가 1페이지 일 경우의
			// 이전 버튼에 대한 속성
			g_PreviousBtn.setEnabled(false);
		} else {
			g_PreviousBtn.setEnabled(true);
		}

		if (g_OrderTableindex == (int) (g_OrderListcount / g_ShowListNum + 1)) {
			// 현재 페이지가 마지막 페이지 일 경우의
			// 다음 버튼에 대한 속성
			g_NextBtn.setEnabled(false);
		} else {
			g_NextBtn.setEnabled(true);
		}

		if ((g_OrderTableindex == (int) (g_OrderListcount / g_ShowListNum)) && ((g_OrderListcount % g_ShowListNum) == 0)) {
			// 현재 페이지의 행의 갯수가 표현하고자 하는 수량과 일치할때의
			// 다음 버튼에 대한 속성
			g_NextBtn.setEnabled(false);
		}
	}// assignBtnIndex():메서드 끝

	private void orderInsert(int index) {

		ArrayList<B_OrderDTO> orderList = B_OrderDAO.getInstance().selectAllPlusAlias();
		// 발주목록에 넣을 데이터를 갖고 있는 리스트

		g_OrderListcount = orderList.size();
		// 발주목록에 들어갈 총 데이터의 수량

		int tempCount = g_OrderListModel.getRowCount();
		// 임시 int 변수 , 위 문장에서는 기존에 발주테이블에 있는 데이터의 행 수량을 삽입

		g_UniqueNum = new ArrayList<>();
		// 레코드에 대한 고유번호를 넣을 리스트

		for (int i = 0; i < tempCount; i++) {
			g_OrderListModel.removeRow(0);
		}// 기존 테이블의 행 전부 삭제

		if (((int) (g_OrderListcount / g_ShowListNum) + 1) == index) {
			 // 마지막 index일 경우 테이블에 값 넣기
			int startNum = (int) (index - 1) * g_ShowListNum;
			for (int i = 0; i < (g_OrderListcount % g_ShowListNum); i++) {
				g_OrderListModel.insertRow(g_OrderListModel.getRowCount(),
						new Object[] { orderList.get(startNum + i).getAlias(), orderList.get(startNum + i).getName(),
								orderList.get(startNum + i).getQuantity(),
								orderList.get(startNum + i).getDate().substring(0, 16),
								orderList.get(startNum + i).gethComfirm() });
				g_UniqueNum.add(orderList.get(startNum + i).getNum());
			}
		} else {
			 // 마지막 index가 아닌 경우 테이블에 값 넣기
			int startNum = (int) (index - 1) * g_ShowListNum;
			for (int i = 0; i < g_ShowListNum; i++) { 
				g_OrderListModel.insertRow(g_OrderListModel.getRowCount(),
						new Object[] { orderList.get(startNum + i).getAlias(), orderList.get(startNum + i).getName(),
								orderList.get(startNum + i).getQuantity(),
								orderList.get(startNum + i).getDate().substring(0, 16),
								orderList.get(startNum + i).gethComfirm(), orderList.get(startNum + i).gethComfirm() });
				g_UniqueNum.add(orderList.get(startNum + i).getNum());
			}
		}
		
		assignBtnIndex();
		//값을 전부 입력 후 이전 및 다음 버튼에 대한 속성 지정
		
	}// orderInsert():메서드 끝

	private void aliasNtelInsert() {
		//가맹점의 이름과 연락처를 테이블에 넣는 메서드
		
		int tempCount = g_BodyListModel.getRowCount();
		// 임시 int 변수 , 위 문장에서는 기존에 있던 테이블의 데이터 수량을 삽입
		
		for (int i = 0; i < tempCount; i++) {
			g_BodyListModel.removeRow(0);
		}// 기존에 있던 테이블 데이터 삭제

		ArrayList<H_FranchiseDTO> franchiseArray = H_FranchiseDAO.getInstance().select_AliasNTel();
		
		for (int i = 0; i < franchiseArray.size(); i++) {
			g_BodyListModel.insertRow(i,
					new Object[] { franchiseArray.get(i).getAlias(), franchiseArray.get(i).getTel() });
		}// 가맹점의 이름과 연락처를 넣는 for 문

	}// aliasNtelInsert() : 메서드 종료

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == g_NextBtn) {
			if ((g_OrderTableindex + 1) <= (int) (g_OrderListcount / g_ShowListNum + 1)) {
				++g_OrderTableindex;
				g_NowBtn.setText(String.valueOf(g_OrderTableindex));
				assignBtnIndex();
				int deNum = g_OrderListModel.getRowCount();
				for (int i = 0; i < deNum; i++) {
					g_OrderListModel.removeRow(0);
				}
				orderInsert(g_OrderTableindex);
			}
		} // 다음 버튼을 누를 시 발생하는 액션

		if (e.getSource() == g_PreviousBtn) {
			if (!((g_OrderTableindex - 1) == 0)) {
				--g_OrderTableindex;
				g_NowBtn.setText(String.valueOf(g_OrderTableindex));
				assignBtnIndex();
				int deNum = g_OrderListModel.getRowCount();
				for (int i = 0; i < deNum; i++) {
					g_OrderListModel.removeRow(0);
				}
				orderInsert(g_OrderTableindex);
			}
		} // 이전 버튼을 누를 시 발생하는 액션

		if (e.getSource() == g_ConfirmBtn) {

			int[] selectedIndex = g_OrderListTable.getSelectedRows();
			int selectedCount = g_OrderListTable.getSelectedRowCount();

			for (int i = 0; i < selectedCount; i++) {
				B_OrderDAO.getInstance().checkUpdate(g_UniqueNum.get(selectedIndex[i]));
			}
			orderInsert(g_OrderTableindex);
		} // confirmBtn:버튼 액션 끝

		if (e.getSource() == g_ReStartBtn) {
			aliasNtelInsert();
			orderInsert(g_OrderTableindex);
		}

	}// actionPerformed:메서드 끝
	
	
	@Override // 인터페이스 BBQHead로부터 받은 메서드 show 오버라이딩
	public void show(BBQHead bbqHead) {}
	//이 메서드는 정의하지 않습니다.

	@Override // 인터페이스 BBQHead로부터 받은 메서드 hide 오버라이딩
	public void hide(BBQHead bbqHead) {}
	//이 메서드는 정의하지 않습니다.
	
}// 클래스 끝
