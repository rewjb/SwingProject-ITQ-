package head;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import inter.BBQHead;
import inter.HeadSales;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;

import DTO_DAO.B_OrderDAO;
import DTO_DAO.B_SalesDAO;
import DTO_DAO.H_FranchiseDAO;
import DTO_DAO.H_FranchiseDTO;
import DTO_DAO.H_OrderDAO;
import DTO_DAO.H_VenderDAO;
import DTO_DAO.H_VenderDTO;

import javax.swing.JEditorPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.print.DocFlavor.STRING;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

//1. 계층도
//하위 내용은 클래스 계층도 입니다.
//기본적으로 인터페이스 계층도 이며 () 괄호안에 들어가 있는 것은 인터페이스 및 클래스 여부입니다.
//1) 기호정리
// 괄호 :  I(인터페이스) , C(클래스)
//  ↑ : 아래에 위치한 클래스를 위에 있는 클래스에서 객체로 만들어 사용
//  │ : 상속 및 구현
//                                                                                              BBQ(I)                                                                          
//                                                     ┌───────────────────────────────────────────┴──────────────────────────────────────────────────────────────────────────────────────────┐
//                                                 BBQHead(I)                                                                                                                             BBQBody(I) 
//   ┌───────────────────┌────────────────────┌────────┴────────────┐──────────────────┐─────────────────┐────────────────┐──────────────────────────┐                   ┌────────────┌───────┴───────┐───────────────┐ 
//HeadCheckOrder(I)    HeadOrder(I)       HeadStockInOut(I)       HeadVender(I)  HeadVenderProduct(I) HeadFranchise(I)   HeadSales(I)            H_ChattingFrame(C)     BodyHall(I)   BodyOrder(I)   BodySales(I)   BodyStock(I)
//   │                   │                    │                     │                  │                 │                │                          ↑                   │            │               │               │
//H_CheckOrder(C)      H_Order(C)         H_StockInOut(C)         H_Vender(C)       H_V_Product(C)    H_Franchise(C)     H_Salses(C)          ┌───────│─────────────┐ B_HallC(C)   B_OrderC(C)     B_SalesC(C)     B_StockC(C)
//                                                                                                          ┌─────────────↑────────────────┐  │H_ChattingJoin(C)    │                     ┌────────────↑─────────────┐
//                                                                                                          │ H_Salses_BodySalesBarChart(C)│  │       ↑             │                     │B_SalesC_SalesDataChart(C)│ 
//                                                                                                          │ H_Salses_BodySalesPieChart(C)│  │H_ChattingrManager(C)│                     └──────────────────────────┘
//                                                                                                          │H_Salses_HeadSalsesBarChart(C)│  └─────────────────────┘
//                                                                                                          └──────────────────────────────┘
//2. 변수명
//1) 전역변수 식별자 특징
//=> g_식별자  (g : 글로벌 변수)  ,  ex) g_BtnSelect

public class H_Salses extends JPanel implements HeadSales, ActionListener, ItemListener {

	private DefaultTableModel g_HeadSalesModel = new DefaultTableModel(0, 4);
	private JTable h_HeadSalesTable = new JTable(g_HeadSalesModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private JScrollPane g_HeadSalesScroll = new JScrollPane(h_HeadSalesTable,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 본사의 매출 내역을 볼 수 있는 테이블 및 스크롤

	private DefaultTableCellRenderer g_CelAlignCenter = new DefaultTableCellRenderer();
	// 매출 내역 테이블을 가운데 정렬하기 위한 객체

	private JRadioButton g_MonthRadio = new JRadioButton("월별");
	private JRadioButton g_DayRadio = new JRadioButton("일별");
	// 가맹점 매출에서 월별,일별의 라디오버튼

	private ButtonGroup g_RadioGroup = new ButtonGroup();
	// 가맹점 매출에서 월별, 일별의 버튼 그룹
	private ButtonGroup g_BodyOrHeadGroup = new ButtonGroup();
	// 가맹점 매출 or 본사 매출의 버튼 그룹

	private JComboBox g_YearComboBox = new JComboBox();
	private JComboBox g_MonthComboBox = new JComboBox();
	// 연도와 월을 고를 수 있는 콤보 박스

	private JComboBox g_FranSelectJComboBox = new JComboBox();
	// 가맹점을 선택하는 콤보 박스

	private JComboBox g_HeadSalesYearComboBox = new JComboBox();
	// 본사의 매출 확인 시 선택하는 연도 콤보박스

	private JLabel g_HeadSalesLabel = new JLabel("월별 본사 매입 및 매출  , 가맹점 총매출");
	private JLabel g_HeadSelectJLabel = new JLabel("년");
	// 본사 매출에서 문자열을 갖고 있는 레이블

	private JLabel g_FranSalesLabel = new JLabel("가맹점 매출");
	private JLabel g_YearLabel = new JLabel("년");
	private JLabel g_MonthLabel = new JLabel("월");
	private JLabel g_FranSelectJLabel = new JLabel("점");
	private JLabel g_IssueMenuLabel = new JLabel("가맹점 인기메뉴");
	// 가맹점 매출에서 문자열을 갖고 있는 레이블

	private JToggleButton g_HeadSalesBtn = new JToggleButton("본사 매출");
	private JToggleButton g_BodySalesBtn = new JToggleButton("가맹점 매출");
	// 본사매출 혹은 가맹점 매출로 전환하는 버튼

	private H_Salses_BodySalesBarChart g_FranSalesBarChart = new H_Salses_BodySalesBarChart();
	// 가맹점 매출을 표현하는 막대그래프 객체

	private H_Salses_BodySalesPieChart g_FranSalesPieChart = new H_Salses_BodySalesPieChart();
	// 가맹점 매출을 표현하는 파이그래프 객체

	private String g_BodyId;
	// 프랜차이즈 이름을 넣고 아이디를 얻어오는 문자열

	private ArrayList<Integer> g_BodySalsesvalue;
	// 월별 매출 갖고오는 list

	private H_Salses_HeadSalsesBarChart g_HeadGoogleBarChart;
	// 본점의 구글 막대 그래프

	private JPanel bodyPan = new JPanel();
	// 가맹점 매출관련 페이지
	private JPanel headPan = new JPanel();
	// 본사 매출관련 페이지

	private Color color = new Color(128, 144, 160);
	// 버튼색상

	public H_Salses() {

		g_CelAlignCenter.setHorizontalAlignment(SwingConstants.CENTER);
		// 가운데 셋팅값인 객체

		g_HeadSalesModel.setColumnIdentifiers(new Object[] { "월", "본사 매입", "본사 매출", "가맹점 총매출" });
		// 컬럼명 정하기

		h_HeadSalesTable.getTableHeader().setResizingAllowed(false);
		h_HeadSalesTable.getTableHeader().setReorderingAllowed(false);
		// 컬럼 고정하기

		for (int i = 0; i < 4; i++) {
			h_HeadSalesTable.getColumnModel().getColumn(i).setCellRenderer(g_CelAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅

		insertFranComboBox();
		// 초기에 가맹점 이름이 들어갈 콤보박스

		h_HeadSalesTable.getColumnModel().getColumn(2).setPreferredWidth(65);
		h_HeadSalesTable.getColumnModel().getColumn(1).setPreferredWidth(65);
		h_HeadSalesTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		// 본사 매출 테이블의 칼럼 사이즈 지정

		g_RadioGroup.add(g_MonthRadio);
		g_RadioGroup.add(g_DayRadio);
		// 가맹점 월별 , 일별 매출 선택 버튼을 버튼그룹에 추가

		for (int i = 0; i < 10; i++) {
			g_YearComboBox.addItem(String.valueOf(2018 - i));
		} // 가맹점 매출에서 연도를 콤보박스에 추가
		for (int i = 0; i < 12; i++) {
			g_MonthComboBox.addItem(String.valueOf(i + 1));
		} // 가맹점 매출에서 월을 콤보박스에 추가
		for (int i = 0; i < 10; i++) {
			g_HeadSalesYearComboBox.addItem(String.valueOf(2018 - i));
		} // 본사 매출에서 연도를 콤보박스에 추가

		showHeadBarChart((String) g_HeadSalesYearComboBox.getSelectedItem());
		// 본사의 매입 및 매출 그리고 가맹점의 총 매출을 표시하는 막대그래프 메서드 이다.
		// 위에 콤보박스에 값이 들어간 후 실행되어야 하므로 위 for문보다 아래에 위치해야한다.

		g_HeadGoogleBarChart = new H_Salses_HeadSalsesBarChart((String) g_HeadSalesYearComboBox.getSelectedItem());
		// 프로그램 실행 시 처음에 선택되어 있는 연도에 맞는 데이터 그래프 출력

		g_MonthRadio.addItemListener(this);
		g_DayRadio.addItemListener(this);
		g_MonthComboBox.addItemListener(this);
		g_YearComboBox.addItemListener(this);
		g_FranSelectJComboBox.addItemListener(this);
		// 가맹점 매출에서 버튼 및 콤보박스에 리스너 추가

		g_HeadSalesYearComboBox.addItemListener(this);
		// 본사 매출에서 콤보박스에 리스너 추가

		g_MonthRadio.doClick();
		// 가맹점 매출에서 시작시 월별 매출로 시작
		// 리스너로 인해 가맹점의 막대그래프는 실행

		showBodyPieChart();
		// 처음 시작시 파이 그래프르 실행

		g_FranSalesLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		g_IssueMenuLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		// 가맹점 매출에서 레이블 폰트 설정

		g_IssueMenuLabel.setHorizontalAlignment(SwingConstants.CENTER);
		// 가맹점 매출에서 인기메뉴 레이블 가운데 정렬

		g_MonthRadio.setBounds(38, 32, 60, 21);
		g_DayRadio.setBounds(104, 32, 60, 21);
		g_YearLabel.setBounds(358, 35, 20, 15);
		g_MonthLabel.setBounds(442, 35, 20, 15);
		g_FranSalesLabel.setBounds(189, 10, 86, 15);
		g_YearComboBox.setBounds(286, 32, 70, 21);
		g_MonthComboBox.setBounds(380, 32, 60, 21);
		g_FranSelectJLabel.setBounds(266, 35, 20, 15);
		g_FranSelectJComboBox.setBounds(178, 32, 86, 21);
		g_IssueMenuLabel.setBounds(566, 35, 111, 15);
		g_FranSalesBarChart.setSize(450, 275);
		// 가맹점 매출에서 GUI 컴포넌트의 사이즈 지정 및 배치

		g_HeadSalesYearComboBox.setBounds(490, 32, 86, 21);
		g_HeadSalesLabel.setBounds(310, 10, 250, 15);
		g_HeadSelectJLabel.setBounds(580, 35, 20, 15);
		g_HeadSalesScroll.setBounds(490, 60, 280, 230);
		// 본사 매출에서 GUI 컴포넌트의 사이즈 지정 및 배치

		g_FranSalesBarChart.setLocation(12, 59);
		g_FranSalesPieChart.setLocation(474, 59);
		// 가맹점 매출에서 파이 및 막대 그래프 배치

		g_HeadGoogleBarChart.setSize(470, 300);
		g_HeadGoogleBarChart.setLocation(12, 30);
		// 본사 매출에서 막대그래프 사이즈 조절 및 배치

		bodyPan.setBounds(0, 0, 770, 348);
		bodyPan.setLayout(null);
		// 가맹점 매출 판넬

		headPan.setBounds(0, 0, 770, 348);
		headPan.setLayout(null);
		// 본사 매출 판넬

		bodyPan.add(g_FranSalesBarChart);
		bodyPan.add(g_FranSalesPieChart);
		bodyPan.add(g_MonthComboBox);
		bodyPan.add(g_YearComboBox);
		bodyPan.add(g_DayRadio);
		bodyPan.add(g_YearLabel);
		bodyPan.add(g_MonthLabel);
		bodyPan.add(g_MonthRadio);
		bodyPan.add(g_FranSalesLabel);
		bodyPan.add(g_FranSelectJLabel);
		bodyPan.add(g_FranSelectJComboBox);
		bodyPan.add(g_IssueMenuLabel);
		// 가맹점 매출 판넬에 들어가는 컴포넌트

		headPan.add(g_HeadSelectJLabel);
		headPan.add(g_HeadSalesYearComboBox);
		headPan.add(g_HeadSalesLabel);
		headPan.add(g_HeadGoogleBarChart);
		headPan.add(g_HeadSalesScroll);
		// 본사 매출 판넬에 들어가는 컴포넌트

		add(g_HeadSalesBtn);
		add(g_BodySalesBtn);
		// 가맹점 및 본사 매출의 전환버튼 추가

		g_BodySalesBtn.doClick();
		// 처음 시작시 가맹점 매출로 시작

		add(headPan);
		add(bodyPan);
		// 가맹점 및 본사 매출의 판넬 추가

		g_HeadSalesBtn.addActionListener(this);
		g_BodySalesBtn.addActionListener(this);
		// 가맹점 및 본사 매출의 전환버튼 리스너

		g_BodyOrHeadGroup.add(g_BodySalesBtn);
		g_BodyOrHeadGroup.add(g_HeadSalesBtn);
		// 가맹점 및 본사 매출 전환버튼을 버튼그룹에 추가

		g_BodySalesBtn.setBackground(color);
		g_HeadSalesBtn.setBackground(color);
		// 가맹점 및 본사 매출 전환버튼 색상 지정

		headPan.setBackground(new Color(184, 207, 229));
		bodyPan.setBackground(new Color(184, 207, 229));
		// 가맹점 및 본사 판넬 배경색 지정

		setBackground(new Color(184, 207, 229));
		// 현재 클래스의 메인 판넬 배경색 지정

		g_BodySalesBtn.setBounds(566, 345, 111, 23);
		g_HeadSalesBtn.setBounds(673, 345, 97, 23);
		// 가맹점 및 본사 매출 전환버튼 사이즈 지정 및 배치

		setLayout(null);
		setBounds(0, 0, 770, 368);
		setVisible(false);
		// 현 클래스의 사이즈 및 배치, 레이아웃 설정

	}// 생성자 끝

	private void insertFranComboBox() {
		// 가맹점 매출에서 가맹점을 고를 수 있도록 콤보박스에
		// 데이터를 넣는 메서드

		ArrayList<H_FranchiseDTO> franList = H_FranchiseDAO.getInstance().select_AliasNTel();
		// 가맹점 정보를 갖고 있는 리스트

		int tempCount = franList.size();
		// 임시 int 변수, 위 문장에서는 가맹점 정보를 갖고 있는 리스트의 레코드 수량

		g_FranSelectJComboBox.removeAllItems();
		for (int i = 0; i < tempCount; i++) {
			g_FranSelectJComboBox.addItem(franList.get(i).getAlias());
		}

	}// insertFranComboBox:메서드 종료

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == g_BodySalesBtn) { // 가맹점 매출 버튼
			bodyPan.setVisible(true);
			headPan.setVisible(false);
		} else if (e.getSource() == g_HeadSalesBtn) { // 본사 매출 버튼
			headPan.setVisible(true);
			bodyPan.setVisible(false);
		}

	}// actionPerformed:메서드 종료

	@Override
	public void itemStateChanged(ItemEvent e) {

		if (e.getSource() == g_MonthRadio) {
			// 가맹점 매출에서 월별 버튼을 선택한 경우

			if (g_MonthRadio.isSelected()) {
				// 월별 선택을 했을 때 월별 혹은 일별 중 선택되어 있는 경우로 막대그래프 표현
				showBodyBarChart("월별");
			} else {
				showBodyBarChart("일별");
			}
		} else if (e.getSource() == g_MonthComboBox || e.getSource() == g_YearComboBox
				|| e.getSource() == g_FranSelectJComboBox) {
			// 가맹점 매출에서 월별 및 일별 매출 이외에 콤보박스를 선택했을 경우
			
			showBodyPieChart();
			
			if (g_MonthRadio.isSelected()) {
				showBodyBarChart("월별");
			} else {
				showBodyBarChart("일별");
			}
		}

		if (e.getSource() == g_HeadSalesYearComboBox) {
			// 본사 매출에서 연도콤보박스를 변경했을 경우
			
			showHeadBarChart((String) g_HeadSalesYearComboBox.getSelectedItem());
			// 본사 매출에서 년도의 콤보박스 변경시 테이블 변경
			g_HeadGoogleBarChart.inserHTML((String) g_HeadSalesYearComboBox.getSelectedItem());
			// 본사 매출에서 년도의 콤보박스 변경시 그래프변경
		}
	}// itemStateChanged:메서드 종료

	@Override
	public void showBodyBarChart(String str) {
		// 가맹점 매출의 바차트를 보여주는 메서드
		if (str.equals("월별")) {
			g_BodyId = H_FranchiseDAO.getInstance().selectFranchiseID((String) g_FranSelectJComboBox.getSelectedItem());
			// DB에서 가맹점 명을 기반으로 아이디를 얻어온다.
			g_BodySalsesvalue = B_SalesDAO.getInstance().selectFranSalesYear(g_BodyId,
					(String) g_YearComboBox.getSelectedItem());
			// 위에서 얻은 아이디를 통해 DB에서 해당 아이디의 연별 월 매출을 얻어온다,
			g_FranSalesBarChart.monthChartShow((String) g_FranSelectJComboBox.getSelectedItem(),
					(String) g_YearComboBox.getSelectedItem(), g_BodySalsesvalue);
			// 그래프에 값을 넣어준다.
		} else if (str.equals("일별")) {
			g_BodyId = H_FranchiseDAO.getInstance().selectFranchiseID((String) g_FranSelectJComboBox.getSelectedItem());
			// DB에서 가맹점 명을 기반으로 아이디를 얻어온다.
			g_BodySalsesvalue = B_SalesDAO.getInstance().selectFranSalesMonth(g_BodyId,
					(String) g_YearComboBox.getSelectedItem(), (String) g_MonthComboBox.getSelectedItem());
			// 위에서 얻은 아이디를 통해 DB에서 해당 아이디의 연별 월 매출을 얻어온다
			g_FranSalesBarChart.dayChartShow((String) g_FranSelectJComboBox.getSelectedItem(),
					(String) g_YearComboBox.getSelectedItem(), (String) g_MonthComboBox.getSelectedItem(),
					g_BodySalsesvalue);
			// 그래프에 값을 넣어준다.
		}
	}// barChart() : 메서드 종료

	@Override
	public void showBodyPieChart() {
		// 가맹점 메뉴 인기비율에 데이터를 넣는 메서드
		g_BodyId = H_FranchiseDAO.getInstance().selectFranchiseID((String) g_FranSelectJComboBox.getSelectedItem());
		g_BodySalsesvalue = B_SalesDAO.getInstance().selectFranSalePie(g_BodyId);
		g_FranSalesPieChart.pieChartShow((String) g_FranSelectJComboBox.getSelectedItem(), g_BodySalsesvalue);
	}// pieChart() : 메서드 종료

	@Override
	public void showHeadBarChart(String year) {
		// 본사 매출표시 테이블에 데이터 넣는 메서드

		double sumHeadPurchase = 0;
		double sumHeadSales = 0;
		double sumTotalBodySales = 0;
		// 각 데이터 테이블의 열별로 합산값을 받는 변수

		ArrayList<Integer> headPurchase = H_OrderDAO.getInstance().selectTotalMonthSalse(year);
		// 본사 매입 데이터를 갖고 있는 리스트
		ArrayList<Integer> headSales = B_OrderDAO.getInstance().selectMonthBodyOrder(year);
		// 본사 매출 데이터를 갖고 있는 리스트

		// 가맹점 발주목록에서 갖고온다.
		ArrayList<Integer> totalBodySales = B_SalesDAO.getInstance().selectMonthBodySales(year);
		// 가맹점 매출목록에서 갖고온다.

		for (int i = 0; i < 12; i++) {
			sumHeadPurchase += headPurchase.get(i);
			
			System.out.println(headSales.get(i));
			sumHeadSales += headSales.get(i);
			sumTotalBodySales += totalBodySales.get(i);
		} // 합산하여 데이터 넣기

		int tempCount = g_HeadSalesModel.getRowCount();
		// 기존에 있는 데이터 수량
		for (int i = 0; i < tempCount; i++) {
			g_HeadSalesModel.removeRow(0);
		} // 기존값 테이블에서 삭제하기
		for (int i = 0; i < 12; i++) {
			g_HeadSalesModel.insertRow(0,
					new Object[] { (i + 1) + "월", String.format("%.2f", (double) headPurchase.get(i) / 100000000) + "억",
							String.format("%.2f", (double) headSales.get(i) / 100000000) + "억",
							String.format("%.2f", (double) totalBodySales.get(i) / 100000000) + "억" });
		} // 데이터 insert 하기

		g_HeadSalesModel.insertRow(12,
				new Object[] { "합산", String.format("%.2f", sumHeadPurchase / 100000000) + "억",
						String.format("%.2f", sumHeadSales / 100000000) + "억",
						String.format("%.2f", sumTotalBodySales / 100000000) + "억" });

	}// insertDataInTable() : 메서드 종료

	@Override
	public void show(BBQHead bbqHead) {
	} // 이 메서드는 정의하지 않습니다.

	@Override
	public void hide(BBQHead bbqHead) {
	} // 이 메서드는 정의하지 않습니다.

}// 클래스 끝
