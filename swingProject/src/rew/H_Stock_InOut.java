package rew;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import inter.BBQHead;
import inter.HeadStockInOut;
import javax.swing.JLabel;

public class H_Stock_InOut extends JPanel implements HeadStockInOut, ActionListener {

	private DefaultTableModel inStockListModel = new DefaultTableModel(0, 5);
	private JTable inStockListTable = new JTable(inStockListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 입고에 관련된 정보를 담고 있는 테이블 및 모델

	private JScrollPane inStockScroll = new JScrollPane(inStockListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 입고에 관련정보 스크롤

	private DefaultTableModel outStockListModel = new DefaultTableModel(0, 5);
	private JTable outStockListTable = new JTable(outStockListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 출고에 관련된 정보를 담고 있는 테이블 및 모델

	private JScrollPane outStockScroll = new JScrollPane(outStockListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 출고에 관련정보 스크롤
	
	private DefaultTableModel StockListModel = new DefaultTableModel(0, 3);
	private JTable StockListTable = new JTable(outStockListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 재고에 관련된 정보를 담고 있는 테이블 및 모델
	private JScrollPane StockScroll = new JScrollPane(outStockListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 재고에 관련정보 스크롤
	
	
	
	

	JPanel factoryArrange = new JPanel();
	// 공장 배치도 panel
	JButton[] Point = new JButton[20];
	// 버튼의 20개 배열 선언

	JLabel inInfoLabel = new JLabel("입고 정보");
	JLabel outIInfoLabel = new JLabel("출고 정보");
	JLabel factoryIInfoLabel = new JLabel("공장 배치도");

	public H_Stock_InOut() {// 생성자 시작
		insertArrayBtn();

		factoryArrange.setBackground(Color.BLACK);
		factoryArrange.setBounds(10, 25, 550, 170);
		factoryArrange.setLayout(null);
		// 공장 배치도 판넬 설정

		inInfoLabel.setBounds(120, 200, 57, 15);
		outIInfoLabel.setBounds(397, 200, 57, 15);
		factoryIInfoLabel.setBounds(249, 10, 80, 15);

		inStockScroll.setBounds(10, 220, 270, 130);
		outStockScroll.setBounds(290, 220, 270, 130);
		
		outStockListModel.setColumnIdentifiers(new String[] {"업체명","품목","수량","출고여부","위치"});
		inStockListModel.setColumnIdentifiers(new String[] {"업체명","품목","수량","입고여부","위치"});
		StockListModel.setColumnIdentifiers(new String[] {"품명","재고","비고","입고여부","위치"});
		
		//outStockListTable.getTableHeader().setResizingAllowed(false);
		outStockListTable.getTableHeader().setReorderingAllowed(false);
		// 입고 테이블의 헤더를 얻어서.., / 컬럼 이동 금지  및 사이즈조절 금지
		
		inStockListTable.getTableHeader().setReorderingAllowed(false);
		// 출고 테이블의 헤더를 얻어서.., / 컬럼 이동 금지  및 사이즈조절 금지
		
		StockListTable.getTableHeader().setResizingAllowed(false);
		StockListTable.getTableHeader().setReorderingAllowed(false);
		// 출고 테이블의 헤더를 얻어서.., / 컬럼 이동 금지  및 사이즈조절 금지

		add(factoryArrange);
		add(outStockScroll);
		add(inStockScroll);
		add(inInfoLabel);
		add(outIInfoLabel);
		add(factoryIInfoLabel);

		setLayout(null);
		setBackground(Color.GRAY);
		setBounds(0, 0, 770, 358);
		setSize(770, 358);

		setVisible(false);// 마지막에는 false로 변경

	}// 생성자 끝

	public void insertArrayBtn() {
		// 버튼의 번호 , 배치 메서드

		int x = 25;
		// x좌표 int 값
		int y = 10;
		// y좌표 int 값

		// 버튼의 이름을 정할

		for (int i = 0; i < Point.length; i++) {
			Point[i] = new JButton();
		} // 버튼 20개 객체 부여
		for (int j = 0; j < 10; j++) {
			Point[j].setBounds(x, y, 50, 50);
			x += 50;
		}

		y = 110; // 100만큼 내린다.
		x = 25; // 다시 0으로 초기값

		for (int j = 10; j < 20; j++) {
			Point[j].setBounds(x, y, 50, 50);
			x += 50;
		}

		for (int i = 0; i < 10; i++) {
			Point[i].setText(String.valueOf(10 + i));
		}
		for (int i = 10; i < 20; i++) {
			Point[i].setText(String.valueOf(10 + i));
		}

		for (int i = 0; i < Point.length; i++) {
			factoryArrange.add(Point[i]);
		} // 20개 버튼 추가

	}// arrayBtn: 메서드 끝

	public void paintingArrayBtn() { // 작업중
		// 버튼의 색을 색칠하는 메서드

	}// paintingArrayBtn:메서드 끝

	@Override // 인터페이스 BBQHead로부터 받은 메서드 show 오버라이딩
	public void show(BBQHead bbqHead) {
		((Component) bbqHead).setVisible(true);
	}// show 메서드 끝

	@Override // 인터페이스 BBQHead로부터 받은 메서드 hide 오버라이딩
	public void hide(BBQHead bbqHead) {
		((Component) bbqHead).setVisible(false);
	}// hide 메서드 끝

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}// actionPerformed:메서드 종료
}// 클래스 끝
