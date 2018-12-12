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

	private DefaultTableModel model = new DefaultTableModel(0, 4);//
	private DefaultTableModel model2 = new DefaultTableModel(0, 5);//
	private DefaultTableModel model3 = new DefaultTableModel(0, 2);//

	private JTable salesTable = new JTable(model) {// 전체 매출 테이블
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private JTable salesTableResult = new JTable(model2) {// 매출 종합 테이블
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};

	private JScrollPane scroll = new JScrollPane(salesTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, //
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 전체 매출 스크롤
	private JScrollPane scroll2 = new JScrollPane(salesTableResult, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, //
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 매출 종합 스크롤
	private JTextField textField;// 첫번째 날짜 입력칸
	private JTextField textField_1;// 두번째 날짜 입력칸
	private JTextField textField_2;// 세번째 날짜 입력칸
	private JLabel lblNewLabel;// 날짜검색
	private JButton btnNewButton_1;// 선택버튼
	private BodySalesDataChart bodySalesDataChart = new BodySalesDataChart();

	private ArrayList<Integer> value;// 월별 매출 갖고 오는 리스트

	public BodySalesC() {// 생성자
		// jpanel레이아웃 사이즈 배경설정
		setLayout(null);
		setSize(790, 364);
		scroll2.setBounds(439, 27, 314, 60);
		scroll.setBounds(30, 70, 314, 273);

		// 각 표 컬럼 설정
		model.setColumnIdentifiers(new Object[] { "메뉴", "수량", "합계", "날짜" });
		model2.setColumnIdentifiers(new Object[] { "후라이드", "양념", "간장", "음료", "합계" });
		model3.setColumnIdentifiers(new Object[] { "순위", "메뉴" });

		salesTable.getColumnModel().getColumn(3).setPreferredWidth(200);
		btnNewButton_1 = new JButton("\uAC80\uC0C9");
		// 선택버튼 폰트설정
		btnNewButton_1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		btnNewButton_1.setBounds(287, 37, 57, 23);
		bodySalesDataChart.setSize(422, 281);
		// 그래프 위치 지정
		bodySalesDataChart.setLocation(350, 86);

		// 각 텍스트 필드 위치지정
		textField = new JTextField();
		textField.setBounds(87, 37, 42, 21);
		add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(156, 37, 29, 21);
		add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(215, 37, 29, 21);
		add(textField_2);

		// 각 라벨 내용 폰트 위치 조정
		lblNewLabel = new JLabel("\uB0A0\uC9DC \uAC80\uC0C9");
		lblNewLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(30, 41, 57, 15);

		// 각 컴포턴트 jpanel에 더하기
		add(lblNewLabel);
		add(btnNewButton_1);
		add(scroll2);
		add(scroll);
		add(bodySalesDataChart);
		btnNewButton_1.addActionListener(this);

		JLabel label_1 = new JLabel("\uB9E4\uCD9C\uC885\uD569");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		label_1.setBounds(564, 8, 57, 15);
		add(label_1);

		JLabel label_2 = new JLabel("\uB9E4\uCD9C \uAC80\uC0C9");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		label_2.setBounds(146, 12, 67, 15);
		add(label_2);

		JLabel label_3 = new JLabel("\uB144");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		label_3.setBounds(112, 41, 57, 15);
		add(label_3);

		JLabel label = new JLabel("\uC6D4");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		label.setBounds(168, 41, 57, 15);
		add(label);

		JLabel label_4 = new JLabel("\uC77C");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		label_4.setBounds(228, 41, 57, 15);
		add(label_4);

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
		
		if (!(textField.getText().equals("")) && textField_1.getText().equals("")) {
			int size = B_SalesDAO.getInstance()
					.menuAllSelect(BodyFrame.id,textField.getText() + "-" + textField_1.getText()).size();
			ArrayList<B_SalesDTO> salesDTO = B_SalesDAO.getInstance()
					.menuAllSelect(BodyFrame.id,textField.getText() + "-" + textField_1.getText());
			for (int j = 0; j < size; j++) {
				if (!(salesDTO.get(j).getChickenF() == 0)) {
					model.insertRow(0, new Object[] { "후라이드", (salesDTO.get(j).getChickenF()) / 20000,
							salesDTO.get(j).getChickenF(), salesDTO.get(j).getDate().substring(0, 19) });
				} else if (!(salesDTO.get(j).getChickenH() == 0)) {
					model.insertRow(0, new Object[] { "양념", (salesDTO.get(j).getChickenH()) / 20000,
							salesDTO.get(j).getChickenH(), salesDTO.get(j).getDate().substring(0, 19) });
				} else if (!(salesDTO.get(j).getChickenS() == 0)) {
					model.insertRow(0, new Object[] { "간장", (salesDTO.get(j).getChickenS()) / 20000,
							salesDTO.get(j).getChickenS(), salesDTO.get(j).getDate().substring(0, 19) });
				}

			}
		}else {
		int size = B_SalesDAO.getInstance()
				.menuAllSelect(BodyFrame.id,textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText()).size();
		ArrayList<B_SalesDTO> salesDTO = B_SalesDAO.getInstance()
				.menuAllSelect(BodyFrame.id,textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText());
		for (int j = 0; j < size; j++) {
			if (!(salesDTO.get(j).getChickenF() == 0)) {
				model.insertRow(0, new Object[] { "후라이드", (salesDTO.get(j).getChickenF()) / 20000,
						salesDTO.get(j).getChickenF(), salesDTO.get(j).getDate().substring(0, 19) });
			} else if (!(salesDTO.get(j).getChickenH() == 0)) {
				model.insertRow(0, new Object[] { "양념", (salesDTO.get(j).getChickenH()) / 20000,
						salesDTO.get(j).getChickenH(), salesDTO.get(j).getDate().substring(0, 19) });
			} else if (!(salesDTO.get(j).getChickenS() == 0)) {
				model.insertRow(0, new Object[] { "간장", (salesDTO.get(j).getChickenS()) / 20000,
						salesDTO.get(j).getChickenS(), salesDTO.get(j).getDate().substring(0, 19) });
			}

		}
		}
	}

	public void salesResult() {// 종합매출 메서드
		int count = model.getRowCount();
		if (count==0) {
			
		}else {
		for (int i = 0; i < count; i++) {// 작업중
			if (model.getValueAt(i, 0).equals("후라이드")) {// 후라이드치킨 종합
				if (model2.getRowCount() == 0) {
					model2.insertRow(0, new Object[] {});
					model2.setValueAt((int) model.getValueAt(i, 2), 0, 0);
				} else {
					model2.setValueAt(
							(int) model2.getValueAt(0, 0) + (int) model.getValueAt(i, 2),
							0, 0);
				}

			} else if (model.getValueAt(i, 0).equals("양념")) {// 양념치킨 종합
				if (model2.getRowCount() == 0) {
					model2.insertRow(0, new Object[] {});
					model2.setValueAt((int) model.getValueAt(i, 2), 0, 1);
				} else if (model2.getValueAt(0, 1) == null) {
					model2.setValueAt((int) model.getValueAt(i, 2), 0, 1);
				} else {
					model2.setValueAt(
							(int) model2.getValueAt(0, 1) + (int) model.getValueAt(i, 2),
							0, 1);
				}
			} else if (model.getValueAt(i, 0).equals("간장")) {// 간장치킨 종합
				if (model2.getRowCount() == 0) {
					model2.insertRow(0, new Object[] {});
					model2.setValueAt((int) model.getValueAt(i, 2), 0, 2);
				} else if (model2.getValueAt(0, 1) == null) {
					model2.setValueAt((int) model.getValueAt(i, 2), 0, 2);
				} else {
					model2.setValueAt(
							(int) model2.getValueAt(0, 2) + (int) model.getValueAt(i, 2),
							0, 2);
				}
			} else if(model.getValueAt(i, 0).equals("음료")){// 음료 종합
				if (model2.getRowCount() == 0) {
					model2.insertRow(0, new Object[] {});
					model2.setValueAt((int) model.getValueAt(i, 2), 0, 3);
				} else if (model2.getValueAt(0, 3) == null) {
					model2.setValueAt((int) model.getValueAt(i, 2), 0, 3);
				} else {
					model2.setValueAt(
							(int) model2.getValueAt(0, 3) + (int) model.getValueAt(i, 2),
							0, 3);
				}
			}

		}
		for (int j = 0; j < 4; j++) {//합계를 낼때 빈칸은 null값이 되기때문에 0값을 넣어준다.
			if (model2.getValueAt(0, j)==null) {
				model2.setValueAt(0, 0, j);
			}
		}
			
			
			if (model2.getValueAt(0, 4) == null) {// 전체 합계
				model2.setValueAt((int) model2.getValueAt(0, 0) + (int) model2.getValueAt(0, 1)
						+ (int) model2.getValueAt(0, 2) + (int) model2.getValueAt(0, 3), 0, 4);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnNewButton_1) {// 검색을 통한 매출
			if (model.getRowCount() == 0) {
				searchSales();
			} else {
				int count = model.getRowCount();
				for (int i = 0; i < count; i++) {
					model.removeRow(0);
				}
				searchSales();
			}
			if (model2.getRowCount() == 0 && !(textField.getText().equals(""))) {
				salesResult();
			} else if(!(model2.getRowCount() == 0)){
				model2.removeRow(0);
				salesResult();
			}

			if (!(textField.getText().equals("")) && textField_1.getText().equals("")) {
				value = B_SalesDAO.getInstance().selectFranSalesYear(BodyFrame.id, textField.getText());
				bodySalesDataChart.monthChart(textField.getText(), value);
			}else if (!(textField.getText().equals("")) && !(textField_1.getText().equals(""))) {
				value = B_SalesDAO.getInstance().selectFranSalesMonth(BodyFrame.id, textField.getText(), textField_1.getText());
				bodySalesDataChart.dayChart(textField.getText(),textField_1.getText(), value);
				
			}

		}
	}// 액션리스너 끝
}// 클래스 끝
