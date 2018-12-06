package rew;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import inter.BBQHead;
import inter.HeadSales;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTree;

import DTO_DAO.B_SalesDAO;
import DTO_DAO.H_FranchiseDAO;
import DTO_DAO.H_FranchiseDTO;
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

public class H_Salses extends JPanel implements HeadSales, ActionListener, ItemListener {

	private JRadioButton monthRadio = new JRadioButton("월별");
	private JRadioButton dayRadio = new JRadioButton("일별");

	private ButtonGroup radioGroup = new ButtonGroup();
	private ButtonGroup buttonGroup = new ButtonGroup();

	private JComboBox yearComboBox = new JComboBox();
	private JComboBox monthComboBox = new JComboBox();
	private JComboBox franSelectJComboBox = new JComboBox();

	private JLabel franSalesLabel = new JLabel("가맹점 매출");
	private JLabel yearLabel = new JLabel("년");
	private JLabel monthLabel = new JLabel("월");
	private JLabel franSelectJLabel = new JLabel("점");
	private JLabel label = new JLabel("가맹점 인기메뉴");

	private JToggleButton headSalesBtn = new JToggleButton("본사 매출");
	private JToggleButton bodySalesBtn = new JToggleButton("가맹점 매출");

	private ArrayList<H_FranchiseDTO> h_franList;
	// 가맹점 선택하는 콤보박스에 넣을 데이터 DTO리스느

	private FranSalesBarChart franSalesBarChart = new FranSalesBarChart();
	// 가맹점 막대그래프 판넬 객체
	private FranSalesPieChart franSalesPieChart = new FranSalesPieChart();
	String franId;
	//프랜차이즈 이름을 넣고 아이디를 얻어오는 문자열
	ArrayList<Integer> value;
	//월별 매출 갖고오는 list

	public H_Salses() {

		insertFranComboBox();

		radioGroup.add(monthRadio);
		radioGroup.add(dayRadio);
		buttonGroup.add(headSalesBtn);
		buttonGroup.add(bodySalesBtn);

		for (int i = 0; i < 10; i++) {
			yearComboBox.addItem(String.valueOf(2018 - i));
		}

		for (int i = 0; i < 12; i++) {
			monthComboBox.addItem(String.valueOf(i + 1));
		}

		monthRadio.addItemListener(this);
		dayRadio.addItemListener(this);
		headSalesBtn.addActionListener(this);
		bodySalesBtn.addActionListener(this);

		setLayout(null);
		setBackground(Color.GREEN);
		setBounds(0, 0, 770, 358);

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
		bodySalesBtn.setBounds(566, 335, 111, 23);
		headSalesBtn.setBounds(673, 335, 97, 23);

		franSalesBarChart.setLocation(12, 59);
		franSalesPieChart.setLocation(474, 59);

		add(franSalesBarChart);
		add(franSalesPieChart);

		add(monthComboBox);
		add(yearComboBox);
		add(dayRadio);
		add(yearLabel);
		add(monthLabel);
		add(monthRadio);
		add(franSalesLabel);
		add(franSelectJLabel);
		add(franSelectJComboBox);
		add(label);
		add(headSalesBtn);
		add(bodySalesBtn);

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
//		headSalesBtn 가맹점 매출 버튼
//		bodySalesBtn 본사 매출 버튼

	}// actionPerformed:메서드 종료

	@Override
	public void itemStateChanged(ItemEvent e) {

		if (monthRadio.isSelected()) {
			franId = H_FranchiseDAO.getInstance().selectFranchiseID((String) franSelectJComboBox.getSelectedItem());
			value = B_SalesDAO.getInstance().selectFranSalesYear(franId,
					(String) yearComboBox.getSelectedItem());
			 franSalesBarChart .monthChartShow((String)franSelectJComboBox.getSelectedItem(),(String)yearComboBox.getSelectedItem(), value);
		} else if (dayRadio.isSelected()) {

		}

	}// itemStateChanged:메서드 종료
}// 클래스 끝
