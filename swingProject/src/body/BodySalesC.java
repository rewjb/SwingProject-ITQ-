package body;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import DTO_DAO.B_SalesDAO;
import DTO_DAO.B_SalesDTO;
import DTO_DAO.B_StockDAO;
import inter.BBQBody;
import inter.BodySales;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;

public class BodySalesC extends JPanel implements BodySales, ActionListener {

	private DefaultTableModel allSalesModel = new DefaultTableModel(0, 4);//전체 매출 표
	private DefaultTableModel salesResultModel = new DefaultTableModel(0, 5);//매출 종합 표

	private JTable salesTable = new JTable(allSalesModel) {// 전체 매출 테이블
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private JTable salesTableResult = new JTable(salesResultModel) {// 매출 종합 테이블
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};

	private JScrollPane allSalesScroll = new JScrollPane(salesTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, //
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 전체 매출 스크롤
	private JScrollPane salesResultscroll = new JScrollPane(salesTableResult, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, //
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 매출 종합 스크롤
	private JTextField yearTextField;// 첫번째 날짜 입력칸
	private JTextField monthTextField;// 두번째 날짜 입력칸
	private JTextField dayTextField;// 세번째 날짜 입력칸
	private JLabel dateSearchLabel;// 날짜검색
	private JButton selecteBt;// 선택버튼
	private BodySalesDataChart bodySalesDataChart = new BodySalesDataChart();//검색한 매출을 그래프로 보여줄 클래스
	

	private ArrayList<Integer> value;// 월별 매출 갖고 오는 리스트

	public BodySalesC() {// 생성자
		// jpanel레이아웃 사이즈 배경설정
		setLayout(null);
		setSize(790, 370);
		setBackground(new Color(184,207,229));
		salesResultscroll.setBounds(363, 25, 398, 55);
		allSalesScroll.setBounds(12, 68, 314, 273);

		// 각 표 컬럼 설정
		allSalesModel.setColumnIdentifiers(new Object[] { "메뉴", "수량", "합계", "날짜" });
		salesResultModel.setColumnIdentifiers(new Object[] { "후라이드", "양념", "간장", "음료", "합계" });

		salesTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		selecteBt = new JButton("\uAC80\uC0C9");
		// 선택버튼 폰트설정
		selecteBt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		selecteBt.setBounds(275, 37, 57, 23);
		bodySalesDataChart.setSize(440, 281);
		// 그래프 위치 지정
		bodySalesDataChart.setLocation(338, 86);

		// 각 텍스트 필드 위치지정
		yearTextField = new JTextField();
		yearTextField.setBounds(69, 37, 42, 21);
		add(yearTextField);
		yearTextField.setColumns(10);

		monthTextField = new JTextField();
		monthTextField.setColumns(10);
		monthTextField.setBounds(146, 37, 29, 21);
		add(monthTextField);

		dayTextField = new JTextField();
		dayTextField.setColumns(10);
		dayTextField.setBounds(215, 37, 29, 21);
		add(dayTextField);

		// 각 라벨 내용 폰트 위치 조정
		dateSearchLabel = new JLabel("\uB0A0\uC9DC \uAC80\uC0C9");
		dateSearchLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		dateSearchLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateSearchLabel.setBounds(12, 41, 57, 15);
		
		salesTable.setBackground(Color.LIGHT_GRAY);
		salesTableResult.setBackground(Color.LIGHT_GRAY);
		
		
		JLabel salesResultLabel = new JLabel("\uB9E4\uCD9C\uC885\uD569");
		salesResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		salesResultLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		salesResultLabel.setBounds(535, 8, 57, 15);
		add(salesResultLabel);
		
		JLabel salesSearchLabel = new JLabel("\uB9E4\uCD9C \uAC80\uC0C9");
		salesSearchLabel.setHorizontalAlignment(SwingConstants.CENTER);
		salesSearchLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		salesSearchLabel.setBounds(146, 12, 67, 15);
		add(salesSearchLabel);
		
		JLabel yearLabel = new JLabel("\uB144");
		yearLabel.setHorizontalAlignment(SwingConstants.CENTER);
		yearLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		yearLabel.setBounds(99, 41, 57, 15);
		add(yearLabel);
		
		JLabel monthLabel = new JLabel("\uC6D4");
		monthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		monthLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		monthLabel.setBounds(169, 41, 57, 15);
		add(monthLabel);
		
		JLabel dayLabel = new JLabel("\uC77C");
		dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dayLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		dayLabel.setBounds(228, 41, 57, 15);
		add(dayLabel);

		// 각 컴포턴트 jpanel에 더하기
		add(dateSearchLabel);
		add(selecteBt);
		add(salesResultscroll);
		add(allSalesScroll);
		add(bodySalesDataChart);
		selecteBt.addActionListener(this);


		setVisible(false);
	}

	@Override
	public void show(BBQBody bbqBody) {
		((Component) bbqBody).setVisible(true);
	}

	@Override
	public void hide(BBQBody bbqBody) {
		((Component) bbqBody).setVisible(false);
	}

	public void searchSales() {// 매출 검색 메서드
		
		if (!(yearTextField.getText().equals("")) && monthTextField.getText().equals("")) {
			int size = B_SalesDAO.getInstance()
					.menuAllSelect(BodyFrame.id,yearTextField.getText() + "-" + monthTextField.getText()).size();
			ArrayList<B_SalesDTO> salesDTO = B_SalesDAO.getInstance()
					.menuAllSelect(BodyFrame.id,yearTextField.getText() + "-" + monthTextField.getText());
			for (int j = 0; j < size; j++) {
				if (!(salesDTO.get(j).getChickenF() == 0)) {
					allSalesModel.insertRow(0, new Object[] { "후라이드", (salesDTO.get(j).getChickenF()) / 20000,
							salesDTO.get(j).getChickenF(), salesDTO.get(j).getDate().substring(0, 19) });
				} else if (!(salesDTO.get(j).getChickenH() == 0)) {
					allSalesModel.insertRow(0, new Object[] { "양념", (salesDTO.get(j).getChickenH()) / 20000,
							salesDTO.get(j).getChickenH(), salesDTO.get(j).getDate().substring(0, 19) });
				} else if (!(salesDTO.get(j).getChickenS() == 0)) {
					allSalesModel.insertRow(0, new Object[] { "간장", (salesDTO.get(j).getChickenS()) / 20000,
							salesDTO.get(j).getChickenS(), salesDTO.get(j).getDate().substring(0, 19) });
				}

			}
		}else {
		int size = B_SalesDAO.getInstance()
				.menuAllSelect(BodyFrame.id,yearTextField.getText() + "-" + monthTextField.getText() + "-" + dayTextField.getText()).size();
		ArrayList<B_SalesDTO> salesDTO = B_SalesDAO.getInstance()
				.menuAllSelect(BodyFrame.id,yearTextField.getText() + "-" + monthTextField.getText() + "-" + dayTextField.getText());
		for (int j = 0; j < size; j++) {
			if (!(salesDTO.get(j).getChickenF() == 0)) {
				allSalesModel.insertRow(0, new Object[] { "후라이드", (salesDTO.get(j).getChickenF()) / 20000,
						salesDTO.get(j).getChickenF(), salesDTO.get(j).getDate().substring(0, 19) });
			} else if (!(salesDTO.get(j).getChickenH() == 0)) {
				allSalesModel.insertRow(0, new Object[] { "양념", (salesDTO.get(j).getChickenH()) / 20000,
						salesDTO.get(j).getChickenH(), salesDTO.get(j).getDate().substring(0, 19) });
			} else if (!(salesDTO.get(j).getChickenS() == 0)) {
				allSalesModel.insertRow(0, new Object[] { "간장", (salesDTO.get(j).getChickenS()) / 20000,
						salesDTO.get(j).getChickenS(), salesDTO.get(j).getDate().substring(0, 19) });
			}

		}
		}
	}

	public void salesResult() {// 종합매출 메서드
		int count = allSalesModel.getRowCount();
		if (count==0) {
			
		}else {
		for (int i = 0; i < count; i++) {// 작업중
			if (allSalesModel.getValueAt(i, 0).equals("후라이드")) {// 후라이드치킨 종합
				if (salesResultModel.getRowCount() == 0) {
					salesResultModel.insertRow(0, new Object[] {});
					salesResultModel.setValueAt((int) allSalesModel.getValueAt(i, 2), 0, 0);
				} else {
					salesResultModel.setValueAt(
							(int) salesResultModel.getValueAt(0, 0) + (int) allSalesModel.getValueAt(i, 2),
							0, 0);
				}

			} else if (allSalesModel.getValueAt(i, 0).equals("양념")) {// 양념치킨 종합
				if (salesResultModel.getRowCount() == 0) {
					salesResultModel.insertRow(0, new Object[] {});
					salesResultModel.setValueAt((int) allSalesModel.getValueAt(i, 2), 0, 1);
				} else if (salesResultModel.getValueAt(0, 1) == null) {
					salesResultModel.setValueAt((int) allSalesModel.getValueAt(i, 2), 0, 1);
				} else {
					salesResultModel.setValueAt(
							(int) salesResultModel.getValueAt(0, 1) + (int) allSalesModel.getValueAt(i, 2),
							0, 1);
				}
			} else if (allSalesModel.getValueAt(i, 0).equals("간장")) {// 간장치킨 종합
				if (salesResultModel.getRowCount() == 0) {
					salesResultModel.insertRow(0, new Object[] {});
					salesResultModel.setValueAt((int) allSalesModel.getValueAt(i, 2), 0, 2);
				} else if (salesResultModel.getValueAt(0, 1) == null) {
					salesResultModel.setValueAt((int) allSalesModel.getValueAt(i, 2), 0, 2);
				} else {
					salesResultModel.setValueAt(
							(int) salesResultModel.getValueAt(0, 2) + (int) allSalesModel.getValueAt(i, 2),
							0, 2);
				}
			} else if(allSalesModel.getValueAt(i, 0).equals("음료")){// 음료 종합
				if (salesResultModel.getRowCount() == 0) {
					salesResultModel.insertRow(0, new Object[] {});
					salesResultModel.setValueAt((int) allSalesModel.getValueAt(i, 2), 0, 3);
				} else if (salesResultModel.getValueAt(0, 3) == null) {
					salesResultModel.setValueAt((int) allSalesModel.getValueAt(i, 2), 0, 3);
				} else {
					salesResultModel.setValueAt(
							(int) salesResultModel.getValueAt(0, 3) + (int) allSalesModel.getValueAt(i, 2),
							0, 3);
				}
			}

		}
		for (int j = 0; j < 4; j++) {//합계를 낼때 빈칸은 null값이 되기때문에 0값을 넣어준다.
			if (salesResultModel.getValueAt(0, j)==null) {
				salesResultModel.setValueAt(0, 0, j);
			}
		}
			
			
			if (salesResultModel.getValueAt(0, 4) == null) {// 전체 합계
				salesResultModel.setValueAt((int) salesResultModel.getValueAt(0, 0) + (int) salesResultModel.getValueAt(0, 1)
						+ (int) salesResultModel.getValueAt(0, 2) + (int) salesResultModel.getValueAt(0, 3), 0, 4);
			}
		}
	}//종합 매출 메서드 끝

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == selecteBt) {// 검색을 통한 매출
			if (allSalesModel.getRowCount() == 0) {
				searchSales();
			} else {
				int count = allSalesModel.getRowCount();
				for (int i = 0; i < count; i++) {
					allSalesModel.removeRow(0);
				}
				searchSales();
			}
			if (salesResultModel.getRowCount() == 0 && !(yearTextField.getText().equals(""))) {
				salesResult();
			} else if(!(salesResultModel.getRowCount() == 0)){
				salesResultModel.removeRow(0);
				salesResult();
			}

			if (!(yearTextField.getText().equals("")) && monthTextField.getText().equals("")) {
				value = B_SalesDAO.getInstance().selectFranSalesYear(BodyFrame.id, yearTextField.getText());
				bodySalesDataChart.monthChart(yearTextField.getText(), value);
			}else if (!(yearTextField.getText().equals("")) && !(monthTextField.getText().equals(""))) {
				value = B_SalesDAO.getInstance().selectFranSalesMonth(BodyFrame.id, yearTextField.getText(), monthTextField.getText());
				bodySalesDataChart.dayChart(yearTextField.getText(),monthTextField.getText(), value);
				
			}

		}
	}// 액션리스너 끝
}// 클래스 끝
