package rew;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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
import DTO_DAO.H_FranchiseDTO;
import DTO_DAO.H_OrderDAO;
import DTO_DAO.H_OrderDTO;
import DTO_DAO.H_StockDAO;
import DTO_DAO.H_StockDTO;
import DTO_DAO.H_VenderDAO;
import DTO_DAO.H_VenderpDAO;
import inter.BBQHead;
import inter.HeadStockInOut;

public class H_Stock_InOut extends JPanel implements HeadStockInOut, ActionListener, DocumentListener, ItemListener {

	private DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();

	private DefaultTableModel inStockListModel = new DefaultTableModel(0, 5);
	private JTable inStockListTable = new JTable(inStockListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 입고에 관련된 정보를 담고 있는 테이블 및 모델

	private JScrollPane inStockScroll = new JScrollPane(inStockListTable,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 입고에 관련정보 스크롤

	private DefaultTableModel outStockListModel = new DefaultTableModel(0, 5);
	private JTable outStockListTable = new JTable(outStockListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 출고에 관련된 정보를 담고 있는 테이블 및 모델

	private JScrollPane outStockScroll = new JScrollPane(outStockListTable,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 출고에 관련정보 스크롤

	private DefaultTableModel StockListModel = new DefaultTableModel(0, 3);
	private JTable StockListTable = new JTable(StockListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 재고에 관련된 정보를 담고 있는 테이블 및 모델
	private JScrollPane stockScroll = new JScrollPane(StockListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 재고에 관련정보 스크롤

	private DefaultTableModel inputStockModel = new DefaultTableModel(0, 2);
	private JTable inputStockTable = new JTable(inputStockModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 다이얼로그에 들어가는 리스트

	private JScrollPane inputStockScroll = new JScrollPane(inputStockTable,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 다이얼로그에 들어가는 스크롤

	private JPanel factoryArrange = new JPanel();
	// 공장 배치도 panel
	private JButton[] PointBtn = new JButton[20];
	// 버튼의 20개 배열 선언

	private JLabel inInfoLabel = new JLabel("입고 정보");
	private JLabel outIInfoLabel = new JLabel("출고 정보");
	private JLabel factoryIInfoLabel = new JLabel("창고  배치도");
	private JLabel totalStockLabel = new JLabel("재고현황");

	private H_OrderDAO h_orderDAO = H_OrderDAO.getInstance();
	// 입고정보를 갖고 있는 객체
	private ArrayList<H_OrderDTO> h_orderLIst;
	// 입고정보를 갖고 갖고 있는 리스트

	private JDialog inOutStockFrame = new JDialog();
	// 재고 입고 출고 입력하는 창
	private JLabel stockLabel = new JLabel("재고");
	private JLabel inputLabel = new JLabel("입출고 입력");
	private JLabel pNameLabel = new JLabel("품목");
	private JLabel pQuantity = new JLabel("수량");
	private JLabel inputKindOfLabel = new JLabel("구분");
	private JLabel placeLabel = new JLabel("업체");
	// 다이얼로그에 들어갈 라벨들 배치
	private JComboBox<String> pNameBox;
	// 품목 선택
	JTextField Quantity = new JTextField();
	// 수량입력
	private JComboBox<String> kindOfBox = new JComboBox<>();
	// 입고 혹은 출고 선택
	private JComboBox<String> placeBox = new JComboBox<>();
	// 입고 혹은 출고에 따른 업체
	private JButton inputConfirm = new JButton("확인");
	// 입고 출고를 입력할때 최종 버튼
	// 제품 리스트를 갖고오는 리스트
	private String pointNum;

	private H_StockDTO set_stockDTO;
	// 입력을 위한 DTO
	ArrayList<String> venderName; // 업체 리스트
	ArrayList<H_FranchiseDTO> h_franchiseList; // 가맹점 리스트
	ArrayList<H_StockDTO> pointStockList; // point별 리스트
	ArrayList<H_StockDTO> inStockList; // 입고 재고 기록
	ArrayList<H_StockDTO> outStockList;// 출고 재고 기록
	ArrayList<H_StockDTO> totalStockList;// 전체 재고

	public H_Stock_InOut() {// 생성자 시작

		insertArrayBtn();
		// 버튼 배치 메서드
		
		pNameBox = new JComboBox(H_VenderpDAO.getInstance().select_product().toArray());
		kindOfBox.addItem("입고");
		kindOfBox.addItem("출고");

		insertInOutTotalStockHistoryList();

		inInfoLabel.setBounds(178, 200, 57, 15);
		outIInfoLabel.setBounds(560, 200, 57, 15);
		factoryIInfoLabel.setBounds(249, 10, 80, 15);
		totalStockLabel.setBounds(643, 10, 57, 15);

		inStockScroll.setBounds(10, 220, 369, 115);
		outStockScroll.setBounds(389, 220, 369, 115);
		stockScroll.setBounds(572, 25, 186, 170);
		inputStockScroll.setBounds(10, 20, 200, 145);

		factoryArrange.setBackground(Color.BLACK);
		factoryArrange.setBounds(10, 25, 550, 170);
		factoryArrange.setLayout(null);
		// 공장 배치도 판넬 설정

		outStockListModel.setColumnIdentifiers(new String[] { "품명", "수량", "위치", "출고지", "날짜" });
		inStockListModel.setColumnIdentifiers(new String[] { "품명", "수량", "위치", "입고지", "날짜" });
		StockListModel.setColumnIdentifiers(new String[] { "품명", "재고" });
		inputStockModel.setColumnIdentifiers(new String[] { "품명", "재고" });

		inStockListTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		inStockListTable.getColumnModel().getColumn(1).setPreferredWidth(25);
		inStockListTable.getColumnModel().getColumn(2).setPreferredWidth(25);
		inStockListTable.getColumnModel().getColumn(4).setPreferredWidth(100);
		outStockListTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		outStockListTable.getColumnModel().getColumn(1).setPreferredWidth(25);
		outStockListTable.getColumnModel().getColumn(2).setPreferredWidth(25);
		outStockListTable.getColumnModel().getColumn(4).setPreferredWidth(100);
		// 입고 및 출고 기록테이블의 컬럼 크기 조정

		for (int i = 0; i < 2; i++) {
			inputStockTable.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅
		for (int i = 0; i < 2; i++) {
			StockListTable.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅
		for (int i = 0; i < 5; i++) {
			inStockListTable.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅
		for (int i = 0; i < 5; i++) {
			outStockListTable.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅

		outStockListTable.getTableHeader().setResizingAllowed(false);
		outStockListTable.getTableHeader().setReorderingAllowed(false);
		// 입고 테이블의 헤더를 얻어서.., / 컬럼 이동 금지 및 사이즈조절 금지

		inStockListTable.getTableHeader().setResizingAllowed(false);
		inStockListTable.getTableHeader().setReorderingAllowed(false);
		// 출고 테이블의 헤더를 얻어서.., / 컬럼 이동 금지 및 사이즈조절 금지

		StockListTable.getTableHeader().setResizingAllowed(false);
		StockListTable.getTableHeader().setReorderingAllowed(false);
		// 출고 테이블의 헤더를 얻어서.., / 컬럼 이동 금지 및 사이즈조절 금지

		inputStockTable.getTableHeader().setResizingAllowed(false);
		inputStockTable.getTableHeader().setReorderingAllowed(false);
		// 다이얼로그안에 있는 스크롤!

		celAlignCenter.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < PointBtn.length; i++) {
			PointBtn[i].addActionListener(this);
		}

		insertVenderNfranchise();
		// 처음 시작할때 목적지 넣어주기

		inputConfirm.addActionListener(this);
		Quantity.getDocument().addDocumentListener(this);
		kindOfBox.addItemListener(this);
		pNameBox.addItemListener(this);

		add(factoryArrange);
		add(outStockScroll);
		add(inStockScroll);
		add(inInfoLabel);
		add(outIInfoLabel);
		add(factoryIInfoLabel);
		add(stockScroll);
		add(totalStockLabel);

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

		for (int i = 0; i < PointBtn.length; i++) {
			PointBtn[i] = new JButton();
		} // 버튼 20개 객체 부여
		for (int j = 0; j < 10; j++) {
			PointBtn[j].setBounds(x, y, 50, 50);
			x += 50;
		}

		y = 110; // 100만큼 내린다.
		x = 25; // 다시 0으로 초기값

		for (int j = 10; j < 20; j++) {
			PointBtn[j].setBounds(x, y, 50, 50);
			x += 50;
		}

		for (int i = 0; i < 10; i++) {
			PointBtn[i].setText(String.valueOf(10 + i));
		}
		for (int i = 10; i < 20; i++) {
			PointBtn[i].setText(String.valueOf(10 + i));
		}

		for (int i = 0; i < PointBtn.length; i++) {
			factoryArrange.add(PointBtn[i]);
		} // 20개 버튼 추가

	}// arrayBtn: 메서드 끝

	public void paintingArrayBtn() { // 작업중
		// 버튼의 색을 색칠하는 메서드

	}// paintingArrayBtn:메서드 끝

	public void inStockInsert() {
		// 입고정보 테이블에 들어갈 정보

		h_orderLIst = h_orderDAO.selectAll();

		for (int i = 0; i < h_orderLIst.size(); i++) {
			inStockListModel.insertRow(1, new Object[] {});
		}

	}

	public void inOutframe(String num) {// 작업중
		// 재고 입고 출고 입력하는 창

		inOutStockFrame.setTitle(num + "번 입출고");
		inOutStockFrame.setSize(400, 200);
		inOutStockFrame.getContentPane().setLayout(null);
		inOutStockFrame.setLocationRelativeTo(this);
		inOutStockFrame.setResizable(false);

		Quantity.setText(null);

		stockLabel.setBounds(98, 5, 57, 15); // 재고
		inputLabel.setBounds(260, 5, 100, 15); // 라벨:입출고 입력
		pNameLabel.setBounds(230, 25, 57, 15); // 라벨: 제품명
		pQuantity.setBounds(230, 50, 57, 15); // 라벨: 수량
		inputKindOfLabel.setBounds(230, 75, 57, 15); // 라벨:구분
		pNameBox.setBounds(265, 25, 90, 20); // 제품명 박스
		kindOfBox.setBounds(265, 75, 60, 20); // 입고 출고 여부 박스
		placeLabel.setBounds(230, 100, 60, 20); // 업체 라벨
		Quantity.setBounds(265, 50, 60, 20); // 수량 받는 텍스트 필드
		inputConfirm.setBounds(280, 140, 60, 20); // 확인 버튼
		placeBox.setBounds(265, 100, 100, 20);

		inOutStockFrame.getContentPane().add(placeLabel);
		inOutStockFrame.getContentPane().add(placeBox);
		inOutStockFrame.getContentPane().add(stockLabel);
		inOutStockFrame.getContentPane().add(inputLabel);
		inOutStockFrame.getContentPane().add(pNameLabel);
		inOutStockFrame.getContentPane().add(pQuantity);
		inOutStockFrame.getContentPane().add(inputKindOfLabel);
		inOutStockFrame.getContentPane().add(inputStockScroll);
		inOutStockFrame.getContentPane().add(inputConfirm);
		inOutStockFrame.getContentPane().add(pNameBox);
		inOutStockFrame.getContentPane().add(kindOfBox);
		inOutStockFrame.getContentPane().add(Quantity);

		inOutStockFrame.setVisible(true);
	}// inOutframe:메서드 끝

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

		if (e.getSource() == inputConfirm) {
			try {
				// 오류를 검출하기 위한 사전 문장
				set_stockDTO = new H_StockDTO(pointNum, (String) kindOfBox.getSelectedItem(),
						(String) pNameBox.getSelectedItem(), Integer.parseInt(Quantity.getText()),
						(String) placeBox.getSelectedItem(), null);

				if (H_StockDAO.getInstance().insert(set_stockDTO) == 1) {
					Quantity.setText(null);
				}
				insertPointList(pointNum);
				insertInOutTotalStockHistoryList();
			} catch (Exception e2) {
				e2.printStackTrace();
				JOptionPane.showMessageDialog(inOutStockFrame, "수량에 숫자를 입력해 주세요.");
			}
		} else {
			pointNum = ((JButton) e.getSource()).getText();
			insertPointList(pointNum);
			inOutframe(pointNum);
		} // inputConfirm:버튼 if문 종료

		// TODO Auto-generated method stub

	}// actionPerformed:메서드 종료

	@Override
	public void insertUpdate(DocumentEvent e) {
		// 수량에 숫자 이외에 입력시 오류 메세지를 알려준다.
		try {
			Integer.parseInt(Quantity.getText());
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(inOutStockFrame, "수량에 숫자를 입력해 주세요.");
		}
	}// insertUpdate:메서드 종료

	@Override
	public void removeUpdate(DocumentEvent e) {
	}// 이 메서드는 정의하지 않습니다.

	@Override
	public void changedUpdate(DocumentEvent e) {
	}// 이 메서드는 정의하지 않습니다.

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == kindOfBox) {
			insertVenderNfranchise();
		}

		if (e.getSource() == pNameBox) {
			insertVenderNfranchise();
		}
	}// itemStateChanged메서드 끝

	public void insertVenderNfranchise() {

		if (kindOfBox.getSelectedItem().equals("출고")) {
			h_franchiseList = H_FranchiseDAO.getInstance().select_AliasNTel();
			placeBox.removeAllItems();
			for (int i = 0; i < h_franchiseList.size(); i++) {
				placeBox.addItem(h_franchiseList.get(i).getAlias());
			}
		} else {
			venderName = H_VenderpDAO.getInstance().select_vender((String) pNameBox.getSelectedItem());
			placeBox.removeAllItems();
			for (int i = 0; i < venderName.size(); i++) {
				placeBox.addItem(venderName.get(i));
			}
		}
	}// insertVenderNfranchise:메서드 끝

	public void insertPointList(String point) {
		pointStockList = H_StockDAO.getInstance().selectPointAll(point);
		int count = inputStockModel.getRowCount();

		for (int i = 0; i < count; i++) {
			inputStockModel.removeRow(0);
		}

		for (int i = 0; i < pointStockList.size(); i++) {
			inputStockModel.insertRow(0,
					new Object[] { pointStockList.get(i).getName(), pointStockList.get(i).getQuantity() });
		}

	}// insertPointList:메서드 끝

	public void insertInOutTotalStockHistoryList() {
		inStockList = H_StockDAO.getInstance().selectInStockHistory();
		int count = inStockListModel.getRowCount();

		if (count > 0) {
			for (int i = 0; i < count; i++) {
				inStockListModel.removeRow(0);
			}
		}
		for (int i = 0; i < inStockList.size(); i++) {
			inStockListModel.insertRow(0,
					new Object[] { inStockList.get(i).getName(), inStockList.get(i).getQuantity(),
							inStockList.get(i).getPoint(), inStockList.get(i).getPlace(),
							inStockList.get(i).getDate().substring(0, 16) });
		}

		outStockList = H_StockDAO.getInstance().selectOutStockHistory();
		count = outStockListModel.getRowCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				outStockListModel.removeRow(0);
			}
		}
		for (int i = 0; i < outStockList.size(); i++) {
			outStockListModel.insertRow(0,
					new Object[] { outStockList.get(i).getName(), outStockList.get(i).getQuantity(),
							outStockList.get(i).getPoint(), outStockList.get(i).getPlace(),
							outStockList.get(i).getDate().substring(0, 16) });
		}

		totalStockList = H_StockDAO.getInstance().selectTotalStock();
		count = StockListModel.getRowCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				StockListModel.removeRow(0);
			}
		}
		for (int i = 0; i < totalStockList.size(); i++) {
			StockListModel.insertRow(0,
					new Object[] {totalStockList.get(i).getName(),totalStockList.get(i).getQuantity()});
		}
	}// insertInOutTotalStockHistoryList:메서드 종료

}// 클래스 끝
