package joe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import DTO_DAO.B_SalesDAO;
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

	private DefaultTableModel model = new DefaultTableModel(17, 4);//
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
	private JTable popularMenu = new JTable(model3) {// 인기 순위 테이블
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};

	private JScrollPane scroll = new JScrollPane(salesTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, //
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 전체 매출 스크롤
	private JScrollPane scroll2 = new JScrollPane(salesTableResult, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, //
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 매출 종합 스크롤
	private JScrollPane scroll3 = new JScrollPane(popularMenu, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, //
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 인기 순위 스크롤

	private JButton button_1;// 인기순위
	private JButton button;// 매출 종합
	private JTextField textField;// 첫번째 날짜 입력칸
	private JTextField textField_1;// 두번째 날짜 입력칸
	private JTextField textField_2;// 세번째 날짜 입력칸
	private JLabel label;// "/"
	private JLabel lblNewLabel_1;// "/"
	private JLabel lblNewLabel;// 날짜검색
	private JButton btnNewButton_1;// 선택버튼

	public BodySalesC() {// 생성자
		// jpanel레이아웃 사이즈 배경설정
		setLayout(null);
		setSize(790, 364);
		setBackground(Color.YELLOW);

		// 스크롤 사이즈 설정
		scroll3.setBounds(382, 200, 150, 84);
		scroll2.setBounds(382, 56, 314, 60);
		scroll.setBounds(30, 56, 314, 228);

		// 각 표 컬럼 설정
		model.setColumnIdentifiers(new Object[] { "메뉴", "수량", "합계", "날짜" });
		model2.setColumnIdentifiers(new Object[] { "후라이드", "양념", "간장", "사이드", "합계" });
		model3.setColumnIdentifiers(new Object[] { "순위", "메뉴" });

		salesTable.getColumnModel().getColumn(3).setPreferredWidth(200);// 표 특정 컬럼 길이 증가

		// 버튼 객체 생성
		button = new JButton("\uB9E4\uCD9C \uC885\uD569");
		button_1 = new JButton("\uC778\uAE30\uC21C\uC704");
		btnNewButton_1 = new JButton("\uAC80\uC0C9");
		// 선택버튼 폰트설정
		btnNewButton_1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		// 버튼 위치 지정
		button.setBounds(500, 126, 90, 23);
		button_1.setBounds(409, 304, 90, 23);
		btnNewButton_1.setBounds(287, 27, 57, 23);

		// 각 텍스트 필드 위치지정
		textField = new JTextField();
		textField.setBounds(117, 27, 42, 21);
		add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(186, 27, 29, 21);
		add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(245, 27, 29, 21);
		add(textField_2);

		// 각 라벨 내용 폰트 위치 조정
		lblNewLabel = new JLabel("\uB0A0\uC9DC \uAC80\uC0C9");
		lblNewLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(59, 31, 57, 15);

		lblNewLabel_1 = new JLabel("/");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(144, 31, 57, 15);

		label = new JLabel("/");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		label.setBounds(201, 30, 57, 15);

		// 각 컴포턴트 jpanel에 더하기
		add(lblNewLabel);
		add(lblNewLabel_1);
		add(label);
		add(btnNewButton_1);
		add(scroll3);
		add(scroll2);
		add(scroll);
		add(button);

		add(button_1);

		// 각 버튼 액션리스너 기능 사용
		button_1.addActionListener(this);
		btnNewButton_1.addActionListener(this);
		button.addActionListener(this);

		setVisible(false);
	}

	@Override
	public void show(BBQBody bbqBody) {

	}

	@Override
	public void hide(BBQBody bbqBody) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNewButton_1) {

			
//			try {
				
		
			if (model.getValueAt(0, 0) == null) {

				for (int j = 0; j < B_SalesDAO.getInstance()
						.menuAllSelect(textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
						.size(); j++) {
					if (!(B_SalesDAO.getInstance()
							.menuAllSelect(
									textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
							.get(j).getChickenF() == 0)) {
						model.insertRow(0, new Object[] { "후라이드", (B_SalesDAO.getInstance()
								.menuAllSelect(
										textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
								.get(j).getChickenF()) / 20000,
								B_SalesDAO.getInstance()
										.menuAllSelect(textField.getText() + "-" + textField_1.getText() + "-"
												+ textField_2.getText())
										.get(j).getChickenF(),
								B_SalesDAO.getInstance().menuAllSelect(
										textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
										.get(j).getDate() });
					} else if (!(B_SalesDAO.getInstance()
							.menuAllSelect(
									textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
							.get(j).getChickenH() == 0)) {
						model.insertRow(0, new Object[] { "양념", (B_SalesDAO.getInstance()
								.menuAllSelect(
										textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
								.get(j).getChickenH()) / 20000,
								B_SalesDAO.getInstance()
										.menuAllSelect(textField.getText() + "-" + textField_1.getText() + "-"
												+ textField_2.getText())
										.get(j).getChickenH(),
								B_SalesDAO.getInstance().menuAllSelect(
										textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
										.get(j).getDate() });
					} else if (!(B_SalesDAO.getInstance()
							.menuAllSelect(
									textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
							.get(j).getChickenS() == 0)) {
						model.insertRow(0, new Object[] { "간장", (B_SalesDAO.getInstance()
								.menuAllSelect(
										textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
								.get(j).getChickenS()) / 20000,
								B_SalesDAO.getInstance()
										.menuAllSelect(textField.getText() + "-" + textField_1.getText() + "-"
												+ textField_2.getText())
										.get(j).getChickenS(),
								B_SalesDAO.getInstance().menuAllSelect(
										textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
										.get(j).getDate() });
					}

				}
			}else {
				for (int i = 0; !(model.getValueAt(0, 0)==null); i++) {
					model.removeRow(0);
				}

				for (int j = 0; j < B_SalesDAO.getInstance()
						.menuAllSelect(textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
						.size(); j++) {
					if (!(B_SalesDAO.getInstance()
							.menuAllSelect(
									textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
							.get(j).getChickenF() == 0)) {
						model.insertRow(0, new Object[] { "후라이드", (B_SalesDAO.getInstance()
								.menuAllSelect(
										textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
								.get(j).getChickenF()) / 20000,
								B_SalesDAO.getInstance()
										.menuAllSelect(textField.getText() + "-" + textField_1.getText() + "-"
												+ textField_2.getText())
										.get(j).getChickenF(),
								B_SalesDAO.getInstance().menuAllSelect(
										textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
										.get(j).getDate() });
					} else if (!(B_SalesDAO.getInstance()
							.menuAllSelect(
									textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
							.get(j).getChickenH() == 0)) {
						model.insertRow(0, new Object[] { "양념", (B_SalesDAO.getInstance()
								.menuAllSelect(
										textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
								.get(j).getChickenH()) / 20000,
								B_SalesDAO.getInstance()
										.menuAllSelect(textField.getText() + "-" + textField_1.getText() + "-"
												+ textField_2.getText())
										.get(j).getChickenH(),
								B_SalesDAO.getInstance().menuAllSelect(
										textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
										.get(j).getDate() });
					} else if (!(B_SalesDAO.getInstance()
							.menuAllSelect(
									textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
							.get(j).getChickenS() == 0)) {
						model.insertRow(0, new Object[] { "간장", (B_SalesDAO.getInstance()
								.menuAllSelect(
										textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
								.get(j).getChickenS()) / 20000,
								B_SalesDAO.getInstance()
										.menuAllSelect(textField.getText() + "-" + textField_1.getText() + "-"
												+ textField_2.getText())
										.get(j).getChickenS(),
								B_SalesDAO.getInstance().menuAllSelect(
										textField.getText() + "-" + textField_1.getText() + "-" + textField_2.getText())
										.get(j).getDate() });
					}

				}
				
				
			}
//			} catch (Exception e2) {
//				JOptionPane.showMessageDialog(null, "숫자만 입력가능합니다");
//			}
		}else if (e.getSource()==button) {
			
		}
	}// 액션리스너 끝

//	private JButton button_1;//인기순위
//	private JButton btnNewButton;//일일매출
//	private JButton button;//매출 종합
//	private JTextField textField;//첫번째 날짜 입력칸
//	private JTextField textField_1;//두번째 날짜 입력칸
//	private JTextField textField_2;//세번째 날짜 입력칸
//	private JLabel label;// "/"
//	private JLabel lblNewLabel_1;// "/"
//	private JLabel lblNewLabel;//날짜검색
//	private JButton btnNewButton_1;//선택버튼
}// 클래스 끝
