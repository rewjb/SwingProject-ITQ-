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
//                                                                                                            ┌─────────────↑────────────────┐ │H_ChattingJoin(C)    │                     ┌────────────↑─────────────┐
//                                                                                                            │ H_Salses_BodySalesBarChart(C)│ │       ↑             │                     │B_SalesC_SalesDataChart(C)│ 
//                                                                                                            │ H_Salses_BodySalesPieChart(C)│ │H_ChattingrManager(C)│                     └──────────────────────────┘
//                                                                                                            │H_Salses_HeadSalsesBarChart(C)│ └─────────────────────┘
//                                                                                                            └──────────────────────────────┘
//2. 변수명
//1) 전역변수 식별자 특징
//  => g_식별자  (g : 글로벌 변수)  ,  ex) g_BtnSelect

public class B_SalesC extends JPanel implements BodySales, ActionListener {

	private DefaultTableModel g_AllSalesModel = new DefaultTableModel(0, 4);//전체 매출 표
	private DefaultTableModel g_SalesResultModel = new DefaultTableModel(0, 5);//매출 종합 표

	private JTable g_SalesTable = new JTable(g_AllSalesModel) {// 전체 매출 테이블
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private JTable g_SalesTableResult = new JTable(g_SalesResultModel) {// 매출 종합 테이블
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};

	private JScrollPane g_AllSalesScroll = new JScrollPane(g_SalesTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, //
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 전체 매출 스크롤
	private JScrollPane g_SalesResultscroll = new JScrollPane(g_SalesTableResult, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, //
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 매출 종합 스크롤
	private JTextField g_YearTextField;// 첫번째 날짜 입력칸
	private JTextField g_MonthTextField;// 두번째 날짜 입력칸
	private JTextField g_DayTextField;// 세번째 날짜 입력칸
	private JLabel g_DateSearchLabel;// 날짜검색
	private JLabel g_SalesResultLabel;//통합매출 라벨
	private JLabel g_SalesSearchLabel;//매출 검색 라벨
	private JLabel g_YearLabel;//년 라벨
	private JLabel g_MonthLabel;//달 라벨
	private JLabel g_DayLabel;//일 라벨
	private JButton g_SelecteBt;// 선택버튼
	private B_SalesC_SalesDataChart g_BodySalesDataChart = new B_SalesC_SalesDataChart();//검색한 매출을 그래프로 보여줄 클래스
	

	private ArrayList<Integer> g_MonthValue;// 월별 매출 갖고 오는 리스트

	public B_SalesC() {// 생성자
		// jpanel레이아웃 사이즈 배경설정
		setLayout(null);
		setSize(790, 370);
		setBackground(new Color(184,207,229));
		g_SalesResultscroll.setBounds(363, 25, 398, 55);
		g_AllSalesScroll.setBounds(12, 68, 314, 273);

		// 각 표 컬럼 설정
		g_AllSalesModel.setColumnIdentifiers(new Object[] { "메뉴", "수량", "합계", "날짜" });
		g_SalesResultModel.setColumnIdentifiers(new Object[] { "후라이드", "양념", "간장", "음료", "합계" });

		g_SalesTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		g_SelecteBt = new JButton("\uAC80\uC0C9");
		// 선택버튼 폰트설정
		g_SelecteBt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		g_SelecteBt.setBounds(275, 37, 57, 23);
		g_BodySalesDataChart.setSize(440, 281);
		// 그래프 위치 지정
		g_BodySalesDataChart.setLocation(338, 86);

		// 각 텍스트 필드 위치지정
		g_YearTextField = new JTextField();
		g_YearTextField.setBounds(69, 37, 42, 21);
		add(g_YearTextField);
		g_YearTextField.setColumns(10);

		g_MonthTextField = new JTextField();
		g_MonthTextField.setColumns(10);
		g_MonthTextField.setBounds(146, 37, 29, 21);
		add(g_MonthTextField);

		g_DayTextField = new JTextField();
		g_DayTextField.setColumns(10);
		g_DayTextField.setBounds(215, 37, 29, 21);
		add(g_DayTextField);

		// 각 라벨 내용 폰트 위치 조정
		g_DateSearchLabel = new JLabel("\uB0A0\uC9DC \uAC80\uC0C9");
		g_DateSearchLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		g_DateSearchLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_DateSearchLabel.setBounds(12, 41, 57, 15);
		
		g_SalesTable.setBackground(Color.LIGHT_GRAY);
		g_SalesTableResult.setBackground(Color.LIGHT_GRAY);
		
		
		g_SalesResultLabel = new JLabel("\uB9E4\uCD9C\uC885\uD569");
		g_SalesResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_SalesResultLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		g_SalesResultLabel.setBounds(535, 8, 57, 15);
		add(g_SalesResultLabel);
		
		g_SalesSearchLabel = new JLabel("\uB9E4\uCD9C \uAC80\uC0C9");
		g_SalesSearchLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_SalesSearchLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		g_SalesSearchLabel.setBounds(146, 12, 67, 15);
		add(g_SalesSearchLabel);
		
		g_YearLabel = new JLabel("\uB144");
		g_YearLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_YearLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		g_YearLabel.setBounds(99, 41, 57, 15);
		add(g_YearLabel);
		
		g_MonthLabel = new JLabel("\uC6D4");
		g_MonthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_MonthLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		g_MonthLabel.setBounds(169, 41, 57, 15);
		add(g_MonthLabel);
		
		g_DayLabel = new JLabel("\uC77C");
		g_DayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_DayLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		g_DayLabel.setBounds(228, 41, 57, 15);
		add(g_DayLabel);

		// 각 컴포턴트 jpanel에 더하기
		add(g_DateSearchLabel);
		add(g_SelecteBt);
		add(g_SalesResultscroll);
		add(g_AllSalesScroll);
		add(g_BodySalesDataChart);
		g_SelecteBt.addActionListener(this);


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
		
		if (!(g_YearTextField.getText().equals("")) && g_MonthTextField.getText().equals("") && g_DayTextField.getText().equals("") ) {//년도만 검색했을때 조건
			int size = B_SalesDAO.getInstance()
					.menuAllSelect(B_Frame.st_G_id,g_YearTextField.getText() + "-" + g_MonthTextField.getText()).size();
			System.out.println(size); 
			ArrayList<B_SalesDTO> salesDTO = B_SalesDAO.getInstance()
					.menuAllSelect(B_Frame.st_G_id,g_YearTextField.getText() + "-" + g_MonthTextField.getText());
//			System.out.println(g_MonthTextField.getText());
			for (int j = 0; j < size; j++) {
				if (!(salesDTO.get(j).getChickenF() == 0)) {
					g_AllSalesModel.insertRow(0, new Object[] { "후라이드", (salesDTO.get(j).getChickenF()) / 20000,
							salesDTO.get(j).getChickenF(), salesDTO.get(j).getDate().substring(0, 19) });
				}  if (!(salesDTO.get(j).getChickenH() == 0)) {
					g_AllSalesModel.insertRow(0, new Object[] { "양념", (salesDTO.get(j).getChickenH()) / 20000,
							salesDTO.get(j).getChickenH(), salesDTO.get(j).getDate().substring(0, 19) });
				}  if (!(salesDTO.get(j).getChickenS() == 0)) {
					g_AllSalesModel.insertRow(0, new Object[] { "간장", (salesDTO.get(j).getChickenS()) / 20000,
							salesDTO.get(j).getChickenS(), salesDTO.get(j).getDate().substring(0, 19) });
				}  if (!(salesDTO.get(j).getSide() == 0)) {
					g_AllSalesModel.insertRow(0, new Object[] { "음료", (salesDTO.get(j).getSide())/2000,
							salesDTO.get(j).getSide(), salesDTO.get(j).getDate().substring(0, 19)});
				}

			}
		}else {//달과 일만 검색했을때
		int size = B_SalesDAO.getInstance()
				.menuAllSelect(B_Frame.st_G_id,g_YearTextField.getText() + "-" + g_MonthTextField.getText() + "-" + g_DayTextField.getText()).size();
		ArrayList<B_SalesDTO> salesDTO = B_SalesDAO.getInstance()
				.menuAllSelect(B_Frame.st_G_id,g_YearTextField.getText() + "-" + g_MonthTextField.getText() + "-" + g_DayTextField.getText());
		for (int j = 0; j < size; j++) {
			if (!(salesDTO.get(j).getChickenF() == 0)) {
				g_AllSalesModel.insertRow(0, new Object[] { "후라이드", (salesDTO.get(j).getChickenF()) / 20000,
						salesDTO.get(j).getChickenF(), salesDTO.get(j).getDate().substring(0, 19) });
			}  if (!(salesDTO.get(j).getChickenH() == 0)) {
				g_AllSalesModel.insertRow(0, new Object[] { "양념", (salesDTO.get(j).getChickenH()) / 20000,
						salesDTO.get(j).getChickenH(), salesDTO.get(j).getDate().substring(0, 19) });
			}  if (!(salesDTO.get(j).getChickenS() == 0)) {
				g_AllSalesModel.insertRow(0, new Object[] { "간장", (salesDTO.get(j).getChickenS()) / 20000,
						salesDTO.get(j).getChickenS(), salesDTO.get(j).getDate().substring(0, 19) });
			} if (!(salesDTO.get(j).getSide() == 0)) {
				g_AllSalesModel.insertRow(0, new Object[] { "음료", (salesDTO.get(j).getSide())/2000,
						salesDTO.get(j).getSide(), salesDTO.get(j).getDate().substring(0, 19)});
			}

		}
		}
	}

	public void salesResult() {// 종합매출 메서드
		int count = g_AllSalesModel.getRowCount();
		if (count==0) {
			
		}else {
			g_SalesResultModel.insertRow(0, new Object[] {0,0,0,0,0});
		for (int i = 0; i < count; i++) {// 작업중
			if (g_AllSalesModel.getValueAt(i, 0).equals("후라이드")) {// 후라이드치킨 종합

					g_SalesResultModel.setValueAt(
				(int) g_SalesResultModel.getValueAt(0, 0) +(int) g_AllSalesModel.getValueAt(i, 2),0, 0);

			} else if (g_AllSalesModel.getValueAt(i, 0).equals("양념")) {// 양념치킨 종합

				g_SalesResultModel.setValueAt(
						(int) g_SalesResultModel.getValueAt(0, 1) + (int) g_AllSalesModel.getValueAt(i, 2),
						0, 1);
			} else if (g_AllSalesModel.getValueAt(i, 0).equals("간장")) {// 간장치킨 종합

				g_SalesResultModel.setValueAt(
				(int) g_SalesResultModel.getValueAt(0, 2) + (int) g_AllSalesModel.getValueAt(i, 2),
				0, 2);
			} else if(g_AllSalesModel.getValueAt(i, 0).equals("음료")){// 음료 종합

				g_SalesResultModel.setValueAt(
						(int) g_SalesResultModel.getValueAt(0, 3) + (int) g_AllSalesModel.getValueAt(i, 2),
						0, 3);
			}

		}
		for (int j = 0; j < 4; j++) {//합계를 낼때 빈칸은 null값이 되기때문에 0값을 넣어준다.
			if (g_SalesResultModel.getValueAt(0, j)==null) {
				g_SalesResultModel.setValueAt(0, 0, j);
			}
		}
			
			
			if (g_SalesResultModel.getValueAt(0, 4) == null) {// 전체 합계
				g_SalesResultModel.setValueAt((int) g_SalesResultModel.getValueAt(0, 0) + (int) g_SalesResultModel.getValueAt(0, 1)
						+ (int) g_SalesResultModel.getValueAt(0, 2) + (int) g_SalesResultModel.getValueAt(0, 3), 0, 4);
			}
		}
	}//종합 매출 메서드 끝

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == g_SelecteBt) {// 검색을 통한 매출
			if (g_AllSalesModel.getRowCount() == 0) {//표에 내용이없으면 그냥 출력
				searchSales();
			} else {//표에 내용이있으면 표를 클리어하고 출력
				int count = g_AllSalesModel.getRowCount();
				for (int i = 0; i < count; i++) {
					g_AllSalesModel.removeRow(0);
				}
				searchSales();   
			}
			if (g_SalesResultModel.getRowCount() == 0 && !(g_YearTextField.getText().equals(""))) {//
				salesResult();
			} else if(!(g_SalesResultModel.getRowCount() == 0)){
				g_SalesResultModel.removeRow(0);
				salesResult();
			}

			if (!(g_YearTextField.getText().equals("")) && g_MonthTextField.getText().equals("")) {
				g_MonthValue = B_SalesDAO.getInstance().selectFranSalesYear(B_Frame.st_G_id, g_YearTextField.getText());
				g_BodySalesDataChart.monthChart(g_YearTextField.getText(), g_MonthValue);
			}else if (!(g_YearTextField.getText().equals("")) && !(g_MonthTextField.getText().equals(""))) {
				g_MonthValue = B_SalesDAO.getInstance().selectFranSalesMonth(B_Frame.st_G_id, g_YearTextField.getText(), g_MonthTextField.getText());
				g_BodySalesDataChart.dayChart(g_YearTextField.getText(),g_MonthTextField.getText(), g_MonthValue);
				
			}
		}
	}// 액션리스너 끝
}// 클래스 끝
