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

public class H_Salses extends JPanel implements HeadSales, ActionListener, ItemListener {

	private DefaultTableModel headSalesModel = new DefaultTableModel(0, 4);
	private JTable headSalesTable = new JTable(headSalesModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 가맹점의 발주 리스트(확인하지 않은..)를 넣을 Jtable / 오면서 셀을
		// 수정여부 메서드를 무조건 false값으로 리턴

	private JScrollPane headSalesScroll = new JScrollPane(headSalesTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 발주내용의 스크롤 기능 객체

	private DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
	// Jtable의 가운데 정렬 객체

	private JRadioButton monthRadio = new JRadioButton("월별");
	private JRadioButton dayRadio = new JRadioButton("일별");

	private ButtonGroup radioGroup = new ButtonGroup();
	private ButtonGroup buttonGroup = new ButtonGroup();

	private JComboBox yearComboBox = new JComboBox();
	private JComboBox monthComboBox = new JComboBox();
	private JComboBox franSelectJComboBox = new JComboBox();

	private JComboBox headSalesYearComboBox = new JComboBox();
	private JLabel headSalesLabel = new JLabel("월별 본사 매입 및 매출  , 가맹점 총매출");
	private JLabel headSelectJLabel = new JLabel("년");

	private JLabel franSalesLabel = new JLabel("가맹점 매출");
	private JLabel yearLabel = new JLabel("년");
	private JLabel monthLabel = new JLabel("월");
	private JLabel franSelectJLabel = new JLabel("점");
	private JLabel label = new JLabel("가맹점 인기메뉴");

	private JToggleButton headSalesBtn = new JToggleButton("본사 매출");
	private JToggleButton bodySalesBtn = new JToggleButton("가맹점 매출");

	private ArrayList<H_FranchiseDTO> h_franList;
	// 가맹점 선택하는 콤보박스에 넣을 데이터 DTO리스느

	private BodySalesBarChart franSalesBarChart = new BodySalesBarChart();
	// 가맹점 막대그래프 판넬 객체
	private BodySalesPieChart franSalesPieChart = new BodySalesPieChart();
	private String franId;
	// 프랜차이즈 이름을 넣고 아이디를 얻어오는 문자열
	private ArrayList<Integer> value;
	// 월별 매출 갖고오는 list

	private HeadGoogleBarChart headGoogleBarChart;
	// 본점의 구글 막대 그래프

	private JPanel bodyPan = new JPanel();
	// 가맹점 매출관련 페이지
	private JPanel headPan = new JPanel();
	// 본사 매출관련 페이지

	private ArrayList<Integer> headPurchase;
	// 본사 매입 데이터를 갖고 있는 리스트
	private ArrayList<Integer> headSales;
	// 본사 매출 데이터를 갖고 있는 리스트
	private ArrayList<Integer> totalBodySales;
	// 가맹점 총 매출 데이터를 갖고 있는 리스트

	private Color color = new Color(128, 144, 160);

	public H_Salses() {

		celAlignCenter.setHorizontalAlignment(SwingConstants.CENTER);
		// 가운데 셋팅값인 객체

		headSalesModel.setColumnIdentifiers(new Object[] { "월", "본사 매입", "본사 매출", "가맹점 총매출" });
		// 컬럼명 정하기

		headSalesTable.getTableHeader().setResizingAllowed(false);
		headSalesTable.getTableHeader().setReorderingAllowed(false);
		// 컬럼 고정하기

		for (int i = 0; i < 4; i++) {
			headSalesTable.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅

		insertFranComboBox();
		// 초기에 가맹점 이름이 들어갈 콤보박스

		headSalesTable.getColumnModel().getColumn(2).setPreferredWidth(65);
		headSalesTable.getColumnModel().getColumn(1).setPreferredWidth(65);
		headSalesTable.getColumnModel().getColumn(0).setPreferredWidth(40);

		radioGroup.add(monthRadio);
		radioGroup.add(dayRadio);

		for (int i = 0; i < 10; i++) {
			yearComboBox.addItem(String.valueOf(2018 - i));
		}

		for (int i = 0; i < 12; i++) {
			monthComboBox.addItem(String.valueOf(i + 1));
		}

		for (int i = 0; i < 10; i++) {
			headSalesYearComboBox.addItem(String.valueOf(2018 - i));
		}

		insertDataInTable((String) headSalesYearComboBox.getSelectedItem());
		// table에 데이터 넣기
		// 위에 콤보박스에 값이 들어간 후 실행되어야 하므로 위 for문보다 아래에 위치해야한다.

		headGoogleBarChart = new HeadGoogleBarChart((String) headSalesYearComboBox.getSelectedItem());

		monthRadio.addItemListener(this);
		dayRadio.addItemListener(this);
		monthComboBox.addItemListener(this);
		yearComboBox.addItemListener(this);
		franSelectJComboBox.addItemListener(this);
		headSalesYearComboBox.addItemListener(this);

		monthRadio.doClick();
		showPieChart();

		

		franSalesLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		label.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));

		label.setHorizontalAlignment(SwingConstants.CENTER);

		monthRadio.setBounds(38, 32, 60, 21);
		dayRadio.setBounds(104, 32, 60, 21);
		yearLabel.setBounds(358, 35, 20, 15);
		monthLabel.setBounds(442, 35, 20, 15);
		franSalesLabel.setBounds(189, 10, 86, 15);
		yearComboBox.setBounds(286, 32, 70, 21);
		monthComboBox.setBounds(380, 32, 60, 21);
		franSelectJLabel.setBounds(266, 35, 20, 15);
		franSelectJComboBox.setBounds(178, 32, 86, 21);
		label.setBounds(566, 35, 111, 15);
		franSalesBarChart.setSize(450, 275);

		headSalesYearComboBox.setBounds(490, 32, 86, 21);
		headSalesLabel.setBounds(310, 10, 250, 15);
		headSelectJLabel.setBounds(580, 35, 20, 15);
		headSalesScroll.setBounds(490, 60, 280, 230);

		franSalesBarChart.setLocation(12, 59);
		franSalesPieChart.setLocation(474, 59);

		headGoogleBarChart.setSize(470, 300);
		headGoogleBarChart.setLocation(12, 30);

		bodyPan.setBounds(0, 0, 770, 368);
		bodyPan.setLayout(null);

		headPan.setBounds(0, 0, 770, 368);
		headPan.setLayout(null);

		bodyPan.add(franSalesBarChart);
		bodyPan.add(franSalesPieChart);
		bodyPan.add(monthComboBox);
		bodyPan.add(yearComboBox);
		bodyPan.add(dayRadio);
		bodyPan.add(yearLabel);
		bodyPan.add(monthLabel);
		bodyPan.add(monthRadio);
		bodyPan.add(franSalesLabel);
		bodyPan.add(franSelectJLabel);
		bodyPan.add(franSelectJComboBox);
		bodyPan.add(label);

		headPan.add(headSelectJLabel);
		headPan.add(headSalesYearComboBox);
		headPan.add(headSalesLabel);
		headPan.add(headGoogleBarChart);
		headPan.add(headSalesScroll);

		add(headPan);
		add(bodyPan);

		headPan.setBackground(new Color(184, 207, 229));
		buttonGroup.add(bodySalesBtn);
		bodySalesBtn.setBounds(566, 345, 111, 23);
		headPan.add(bodySalesBtn);
		bodySalesBtn.addActionListener(this);
		bodySalesBtn.setBackground(color);
		buttonGroup.add(headSalesBtn);
		headSalesBtn.setBounds(673, 345, 97, 23);
		headPan.add(headSalesBtn);
		headSalesBtn.addActionListener(this);
		
				headSalesBtn.setBackground(color);
		
				bodySalesBtn.doClick();
		bodyPan.setBackground(new Color(184, 207, 229));
		
		setLayout(null);
		setBounds(0, 0, 770, 368);
		setVisible(false);// 마지막에는 false로 변경
	}// 생성자 끝

	public void insertFranComboBox() {
		h_franList = H_FranchiseDAO.getInstance().select_AliasNTel();
		int count = h_franList.size();
		franSelectJComboBox.removeAllItems();
		for (int i = 0; i < count; i++) {
			franSelectJComboBox.addItem(h_franList.get(i).getAlias());
		}
	}// insertFranComboBox:메서드 종료

	@Override
	public void show(BBQHead bbqHead) {
	} // 이 메서드는 정의하지 않습니다.

	@Override
	public void hide(BBQHead bbqHead) {
	} // 이 메서드는 정의하지 않습니다.

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == bodySalesBtn) { // 가맹점 매출 버튼
			bodyPan.setVisible(true);
			headPan.setVisible(false);
		} else if (e.getSource() == headSalesBtn) { // 본사 매출 버튼
			headPan.setVisible(true);
			bodyPan.setVisible(false);
		}

	}// actionPerformed:메서드 종료

	@Override
	public void itemStateChanged(ItemEvent e) {// 작업중

		if (e.getSource() == monthRadio) {

			if (monthRadio.isSelected()) {
				showBarChart("월별");
			} else {
				showBarChart("일별");
			}
		} else if (e.getSource() == monthComboBox || e.getSource() == yearComboBox
				|| e.getSource() == franSelectJComboBox) {
			showPieChart();
			if (monthRadio.isSelected()) {
				showBarChart("월별");
			} else {
				showBarChart("일별");
			}
		}

		if (e.getSource() == headSalesYearComboBox) {
			insertDataInTable((String) headSalesYearComboBox.getSelectedItem());
			// 본사 매출에서 년도의 콤보박스 변경시 테이블 변경
			headGoogleBarChart.inserHTML((String) headSalesYearComboBox.getSelectedItem());
			// 본사 매출에서 년도의 콤보박스 변경시 그래프변경

		}

	}// itemStateChanged:메서드 종료

	private void showBarChart(String str) {
		// 가맹점 매출의 바차트를 보여주는 메서드
		if (str.equals("월별")) {
			franId = H_FranchiseDAO.getInstance().selectFranchiseID((String) franSelectJComboBox.getSelectedItem());
			// DB에서 가맹점 명을 기반으로 아이디를 얻어온다.
			value = B_SalesDAO.getInstance().selectFranSalesYear(franId, (String) yearComboBox.getSelectedItem());
			// 위에서 얻은 아이디를 통해 DB에서 해당 아이디의 연별 월 매출을 얻어온다,
			franSalesBarChart.monthChartShow((String) franSelectJComboBox.getSelectedItem(),
					(String) yearComboBox.getSelectedItem(), value);
			// 그래프에 값을 넣어준다.
		} else if (str.equals("일별")) {
			franId = H_FranchiseDAO.getInstance().selectFranchiseID((String) franSelectJComboBox.getSelectedItem());
			// DB에서 가맹점 명을 기반으로 아이디를 얻어온다.
			value = B_SalesDAO.getInstance().selectFranSalesMonth(franId, (String) yearComboBox.getSelectedItem(),
					(String) monthComboBox.getSelectedItem());
			// 위에서 얻은 아이디를 통해 DB에서 해당 아이디의 연별 월 매출을 얻어온다
			franSalesBarChart.dayChartShow((String) franSelectJComboBox.getSelectedItem(),
					(String) yearComboBox.getSelectedItem(), (String) monthComboBox.getSelectedItem(), value);
			// 그래프에 값을 넣어준다.
		}
	}// barChart() : 메서드 종료

	private void showPieChart() {
		// 가맹점 메뉴 인기비율에 데이터를 넣는 메서드
		franId = H_FranchiseDAO.getInstance().selectFranchiseID((String) franSelectJComboBox.getSelectedItem());
		value = B_SalesDAO.getInstance().selectFranSalePie(franId);
		franSalesPieChart.pieChartShow((String) franSelectJComboBox.getSelectedItem(), value);
	}// pieChart() : 메서드 종료

	private void insertDataInTable(String year) {
		// 본사 매출표시 테이블에 데이터 넣는 메서드

		double sumHeadPurchase = 0;
		double sumHeadSales = 0;
		double sumTotalBodySales = 0;
		// 각 데이터 테이블의 열별로 합산값을 받는 변수

		headPurchase = H_OrderDAO.getInstance().selectTotalMonthSalse(year);
		// 본사 발주목록에서 갖고온다.
		headSales = B_OrderDAO.getInstance().selectMonthBodyOrder(year);
		// 가맹점 발주목록에서 갖고온다.
		totalBodySales = B_SalesDAO.getInstance().selectMonthBodySales(year);
		// 가맹점 매출목록에서 갖고온다.

		for (int i = 0; i < 12; i++) {
			sumHeadPurchase += headPurchase.get(i);
			sumHeadSales += headSales.get(i);
			sumTotalBodySales += totalBodySales.get(i);
		} // 합산하여 데이터 넣기

		int tempCount = headSalesModel.getRowCount();
		// 기존에 있는 데이터 수량
		for (int i = 0; i < tempCount; i++) {
			headSalesModel.removeRow(0);
		} // 기존값 테이블에서 삭제하기
		for (int i = 0; i < 12; i++) {
			headSalesModel.insertRow(0,
					new Object[] { (i + 1) + "월", String.format("%.2f", (double) headPurchase.get(i) / 100000000) + "억",
							String.format("%.2f", (double) headSales.get(i) / 100000000) + "억",
							String.format("%.2f", (double) totalBodySales.get(i) / 100000000) + "억" });
		} // 데이터 insert 하기

		headSalesModel.insertRow(12, new Object[] { "합산", String.format("%.2f", sumHeadPurchase/100000000) + "억",
				String.format("%.2f", sumHeadSales/100000000) + "억", String.format("%.2f", sumTotalBodySales/100000000) + "억" });

	}// insertDataInTable() : 메서드 종료

}// 클래스 끝
