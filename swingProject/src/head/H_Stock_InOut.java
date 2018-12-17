package head;

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

//1. 계층도
//하위 내용은 클래스 계층도 입니다.
//기본적으로 인터페이스 계층도 이며 () 괄호안에 들어가 있는 것은 인터페이스 및 클래스 여부입니다.
//1) 기호정리
// 괄호 :  I(인터페이스) , C(클래스)
//  │ : 상속 및 구현
//  ↑ : 아래에 위치한 클래스를 위에 있는 클래스에서 객체로 만들어 사용
//                                                                                              BBQ(I)                                                                          
//                                                     ┌───────────────────────────────────────────┴──────────────────────────────────────────────────────────────────────────────────────────┐
//                                                 BBQHead(I)                                                                                                                             BBQBody(I) 
//   ┌───────────────────┌────────────────────┌────────┴────────────┐──────────────────┐─────────────────┐────────────────┐──────────────────────────┐                   ┌────────────┌───────┴───────┐───────────────┐ 
//HeadCheckOrder(I)    HeadOrder(I)       HeadStockInOut(I)       HeadVender(I)  HeadVenderProduct(I) HeadFranchise(I)   HeadSales(I)            H_ChattingFrame(C)     BodyHall(I)   BodyOrder(I)   BodySales(I)   BodyStock(I)
//   │                   │                    │                     │                  │                 │                │                           ↑                   │            │               │               │
//H_CheckOrder(C)      H_Order(C)         H_StockInOut(C)         H_Vender(C)       H_V_Product(C)    H_Franchise(C)     H_Salses(C)          ┌───────│─────────────┐ B_HallC(C)   B_OrderC(C)     B_SalesC(C)     B_StockC(C)
//                                                                                                          ┌─────────────↑────────────────┐  │H_ChattingJoin(C)    │                     ┌────────────↑─────────────┐
//                                                                                                          │ H_Salses_BodySalesBarChart(C)│  │       ↑             │                     │B_SalesC_SalesDataChart(C)│ 
//                                                                                                          │ H_Salses_BodySalesPieChart(C)│  │H_ChattingrManager(C)│                     └──────────────────────────┘
//                                                                                                          │H_Salses_HeadSalsesBarChart(C)│  └─────────────────────┘
//                                                                                                          └──────────────────────────────┘
//2. 변수명
//1) 전역변수 식별자 특징
//=> g_식별자  (g : 글로벌 변수)  ,  ex) g_BtnSelect

public class H_Stock_InOut extends JPanel implements HeadStockInOut, ActionListener, DocumentListener, ItemListener {

	private DefaultTableCellRenderer g_CelAlignCenter = new DefaultTableCellRenderer();
	// 테이블안에 있는 내용의 중앙정렬을 위한 객체

	private DefaultTableModel g_InStockListModel = new DefaultTableModel(0, 5);
	private JTable g_InStockListTable = new JTable(g_InStockListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private JScrollPane g_InStockScroll = new JScrollPane(g_InStockListTable,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 입고에 관련 정보의 테이블 및 스크롤

	private DefaultTableModel g_OutStockListModel = new DefaultTableModel(0, 5);
	private JTable g_OutStockListTable = new JTable(g_OutStockListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private JScrollPane g_OutStockScroll = new JScrollPane(g_OutStockListTable,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 출고에 관련 정보의 테이블 및 스크롤

	private DefaultTableModel g_StockListModel = new DefaultTableModel(0, 3);
	private JTable g_StockListTable = new JTable(g_StockListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; 
	private JScrollPane g_StockScroll = new JScrollPane(g_StockListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 재고에 관련 정보의 테이블 및 스크롤

	private DefaultTableModel g_InputStockModel = new DefaultTableModel(0, 2);
	private JTable g_InputStockTable = new JTable(g_InputStockModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; 
	private JScrollPane g_InputStockScroll = new JScrollPane(g_InputStockTable,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 다이얼로그 안에 있는 특정 위치의 재고 관련 정보 테이블 및 스크롤

	private JPanel g_FactoryArrangePanel = new JPanel();
	// 공장 내부 배치도 panel
	private JButton[] g_PointBtn = new JButton[20];
	// 공장 내부의 배치 버튼
	private String pointNum;
	//배치 포인트 문자열
	
	private JLabel g_InInfoLabel = new JLabel("입고 정보");
	private JLabel g_OutIInfoLabel = new JLabel("출고 정보");
	private JLabel g_FactoryIInfoLabel = new JLabel("창고  배치도");
	private JLabel g_TotalStockLabel = new JLabel("재고현황");
	// GUI에서 글자를 표시하고 있는 레이블들

	private JDialog inOutStockFrame = new JDialog();
	// 재고 입고 출고 입력하는 창
	
	private JLabel g_StockLabel = new JLabel("재고");
	private JLabel g_InputLabel = new JLabel("입출고 입력");
	private JLabel g_PnameLabel = new JLabel("품목");
	private JLabel g_Pquantity = new JLabel("수량");
	private JLabel g_InputKindOfLabel = new JLabel("구분");
	private JLabel g_PlaceLabel = new JLabel("업체");
	// 다이얼로그에 들어갈 라벨들 

	private JComboBox<String> g_PnameBox;
	// 품목 선택
	private JTextField g_Quantity = new JTextField();
	// 수량입력
	private JComboBox<String> g_KindOfBox = new JComboBox<>();
	// 입고 혹은 출고 선택
	private JComboBox<String> g_PlaceBox = new JComboBox<>();
	// 입고 혹은 출고에 따른 업체
	private JButton g_InputConfirm = new JButton("확인");
	// 입고 출고를 입력할때 최종 버튼
	// inOutStockFrame에 들어갈 부품들

	public H_Stock_InOut() {// 생성자 시작

		insertArrayBtn();
		// 공장 내부 버튼 배치 메서드
		// 처음 시작할 때 배치를 하는 메서드

		g_PnameBox = new JComboBox(H_VenderpDAO.getInstance().select_product().toArray());
		// 입출고 입력 시 제품군을 넣는 콤보 박스에 데이터 넣기
		
		g_KindOfBox.addItem("입고");
		g_KindOfBox.addItem("출고");
		// 입고 출고 여부 컴보박스 입출고 선택지 넣기

		insertInOutTotalStockHistoryList();
		// 총 재고, 입고 , 출고 테이블에 시작시 데이터 넣기

		g_InInfoLabel.setBounds(178, 200, 57, 15);
		g_OutIInfoLabel.setBounds(560, 200, 57, 15);
		g_FactoryIInfoLabel.setBounds(249, 10, 80, 15);
		g_TotalStockLabel.setBounds(643, 10, 57, 15);
		// 메인 판넬에 있는 라벨 사이즈 지정 및 배치

		g_InStockScroll.setBounds(10, 220, 369, 114);
		g_OutStockScroll.setBounds(389, 220, 369, 114);
		g_StockScroll.setBounds(572, 25, 186, 170);
		// 메인 판넬에 있는 스크롤 사이즈 지정 및 배치
		
		g_InputStockScroll.setBounds(10, 20, 200, 145);
		// 공장 내부 배치버튼 눌렀을 때 나오는 다이얼로그에 있는 재고 스크롤 사이즈 지정 및 배치

		g_FactoryArrangePanel.setBackground(Color.GRAY);
		g_FactoryArrangePanel.setBounds(10, 25, 550, 170);
		g_FactoryArrangePanel.setLayout(null);
		// 공장 배치도 판넬 설정

		g_OutStockListModel.setColumnIdentifiers(new String[] { "품명", "수량", "위치", "출고지", "날짜" });
		g_InStockListModel.setColumnIdentifiers(new String[] { "품명", "수량", "위치", "입고지", "날짜" });
		g_StockListModel.setColumnIdentifiers(new String[] { "품명", "재고" });
		g_InputStockModel.setColumnIdentifiers(new String[] { "품명", "재고" });
		// 현 클래스에 있는 테이블에 칼럼 지정하기

		g_InStockListTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		g_InStockListTable.getColumnModel().getColumn(1).setPreferredWidth(25);
		g_InStockListTable.getColumnModel().getColumn(2).setPreferredWidth(25);
		g_InStockListTable.getColumnModel().getColumn(4).setPreferredWidth(100);
		// 입고 기록테이블의 컬럼 크기 조정
		g_OutStockListTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		g_OutStockListTable.getColumnModel().getColumn(1).setPreferredWidth(25);
		g_OutStockListTable.getColumnModel().getColumn(2).setPreferredWidth(25);
		g_OutStockListTable.getColumnModel().getColumn(4).setPreferredWidth(100);
		// 출고 기록테이블의 컬럼 크기 조정
		
		g_CelAlignCenter.setHorizontalAlignment(SwingConstants.CENTER);
		// 테이블의 가운데 정렬을 위한 객체설정

		for (int i = 0; i < 2; i++) {
			g_InputStockTable.getColumnModel().getColumn(i).setCellRenderer(g_CelAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅
		for (int i = 0; i < 2; i++) {
			g_StockListTable.getColumnModel().getColumn(i).setCellRenderer(g_CelAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅
		for (int i = 0; i < 5; i++) {
			g_InStockListTable.getColumnModel().getColumn(i).setCellRenderer(g_CelAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅
		for (int i = 0; i < 5; i++) {
			g_OutStockListTable.getColumnModel().getColumn(i).setCellRenderer(g_CelAlignCenter);
		} // for문 끝 / 가운데 정렬 세팅
		// 상위 for문은 테이블에 있는 데이터를 가운데 정렬하는 과정

		g_OutStockListTable.getTableHeader().setResizingAllowed(false);
		g_OutStockListTable.getTableHeader().setReorderingAllowed(false);
		// 입고 테이블의 헤더를 얻어서.., / 컬럼 이동 금지 및 사이즈 조절 금지

		g_InStockListTable.getTableHeader().setResizingAllowed(false);
		g_InStockListTable.getTableHeader().setReorderingAllowed(false);
		// 출고 테이블의 헤더를 얻어서.., / 컬럼 이동 금지 및 사이즈 조절 금지

		g_StockListTable.getTableHeader().setResizingAllowed(false);
		g_StockListTable.getTableHeader().setReorderingAllowed(false);
		// 출고 테이블의 헤더를 얻어서.., / 컬럼 이동 금지 및 사이즈 조절 금지

		g_InputStockTable.getTableHeader().setResizingAllowed(false);
		g_InputStockTable.getTableHeader().setReorderingAllowed(false);
		// 다이얼로그안에 있는 테이블의 헤더를 얻어서.. / 컬럼 이동 금지 및 사이즈 조절 금지

		for (int i = 0; i < g_PointBtn.length; i++) {
			g_PointBtn[i].addActionListener(this);
		}

		insertVenderNfranchise();
		// 처음 시작시 입출고 입력 다이얼로그 안에 업체 및 가맹점 콤보박스에 데이터 넣기

		g_StockLabel.setBounds(98, 5, 57, 15); // 재고
		g_InputLabel.setBounds(260, 5, 100, 15); // 라벨:입출고 입력
		g_PnameLabel.setBounds(230, 25, 57, 15); // 라벨: 제품명
		g_Pquantity.setBounds(230, 50, 57, 15); // 라벨: 수량
		g_InputKindOfLabel.setBounds(230, 75, 57, 15); // 라벨:구분
		g_PnameBox.setBounds(265, 25, 90, 20); // 제품명 박스
		g_KindOfBox.setBounds(265, 75, 60, 20); // 입고 출고 여부 박스
		g_PlaceLabel.setBounds(230, 100, 60, 20); // 업체 라벨
		g_Quantity.setBounds(265, 50, 60, 20); // 수량 받는 텍스트 필드
		g_InputConfirm.setBounds(280, 140, 60, 20); // 확인 버튼
		g_PlaceBox.setBounds(265, 100, 100, 20); // 입출고에 따른 목적지 콤보박스
		// 다이얼로그 안에 들어 있는 부품들의 사이즈 지정 및 배치

		inOutStockFrame.getContentPane().add(g_PlaceLabel);
		inOutStockFrame.getContentPane().add(g_PlaceBox);
		inOutStockFrame.getContentPane().add(g_StockLabel);
		inOutStockFrame.getContentPane().add(g_InputLabel);
		inOutStockFrame.getContentPane().add(g_PnameLabel);
		inOutStockFrame.getContentPane().add(g_Pquantity);
		inOutStockFrame.getContentPane().add(g_InputKindOfLabel);
		inOutStockFrame.getContentPane().add(g_InputStockScroll);
		inOutStockFrame.getContentPane().add(g_InputConfirm);
		inOutStockFrame.getContentPane().add(g_PnameBox);
		inOutStockFrame.getContentPane().add(g_KindOfBox);
		inOutStockFrame.getContentPane().add(g_Quantity);
		// 다이얼로그 안에 들어 있는 부품들을 다이얼로그 안에 추가
		
		g_InputConfirm.addActionListener(this);
		g_Quantity.getDocument().addDocumentListener(this);
		g_KindOfBox.addItemListener(this);
		g_PnameBox.addItemListener(this);
		// 입고 및 출고 내용을 입력 시 콤보박스와 버튼의 리스너 

		add(g_FactoryArrangePanel);
		add(g_OutStockScroll);
		add(g_InStockScroll);
		add(g_InInfoLabel);
		add(g_OutIInfoLabel);
		add(g_FactoryIInfoLabel);
		add(g_StockScroll);
		add(g_TotalStockLabel);
		// 메인 판넬의 라벨 및 스크롤 추가

		g_InStockListTable.setBackground(Color.LIGHT_GRAY);
		g_OutStockListTable.setBackground(Color.LIGHT_GRAY);
		g_StockListTable.setBackground(Color.LIGHT_GRAY);
		// 테이블의 데이터 배경 색상 지정

		setBackground(new Color(184, 207, 229));
		setLayout(null);
		setBounds(0, 0, 770, 368);
		setSize(770, 358);
		setVisible(false);
		// 메인 판넬의 사이즈 지정 및 배치 , 배경색 지정
		
	}// 생성자 끝

	private void insertArrayBtn() {
		// 공장내부 버튼의 배치를 하는 메서드
		int x = 25;
		// x좌표 int 값
		int y = 10;
		// y좌표 int 값

		// 버튼의 이름을 정할

		for (int i = 0; i < g_PointBtn.length; i++) {
			g_PointBtn[i] = new JButton();
		} // 버튼 20개 객체 부여
		for (int j = 0; j < 10; j++) {
			g_PointBtn[j].setBounds(x, y, 50, 50);
			x += 50;
		}

		y = 110; // 100만큼 내린다.
		x = 25; // 다시 0으로 초기값

		for (int j = 10; j < 20; j++) {
			g_PointBtn[j].setBounds(x, y, 50, 50);
			x += 50;
		}

		for (int i = 0; i < 10; i++) {
			g_PointBtn[i].setText(String.valueOf(10 + i));
		}
		for (int i = 10; i < 20; i++) {
			g_PointBtn[i].setText(String.valueOf(10 + i));
		}

		for (int i = 0; i < g_PointBtn.length; i++) {
			g_FactoryArrangePanel.add(g_PointBtn[i]);
		} // 20개 버튼 추가

	}// arrayBtn() : 메서드 끝

	private void inOutframe(String num) {// 작업중
		// 재고 입고 출고 입력하는 창
		// 매개변수로 String num을 받는다.
		// 이때 num은 해당 버튼이다.

		inOutStockFrame.setTitle(num + "번 입출고");
		inOutStockFrame.setSize(400, 200);
		inOutStockFrame.getContentPane().setLayout(null);
		inOutStockFrame.setLocationRelativeTo(this);
		inOutStockFrame.setResizable(false);

		g_Quantity.setText(null);
		
		g_PnameBox.removeAllItems();
		//기존에 있는 데이터 삭제    
		
		int tempInt = H_VenderpDAO.getInstance().select_product().size();
		// 제품군의 수량
		ArrayList<String> list = H_VenderpDAO.getInstance().select_product();
		// 제품군이 들어 있는 리스트
		
		System.out.println(tempInt);
		for (int i = 0; i < tempInt; i++) {
			System.out.println((String) list.get(i));
			g_PnameBox.addItem((String) list.get(i));
		}// 제품군 넣기

		inOutStockFrame.setVisible(true);
	}// inOutframe:메서드 끝

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == g_InputConfirm) {
			// 위치버튼을 누르면 나오는 제이다이얼로그 , 거기에 있는 확인버튼이다.
			// 확인 버튼을 누르면 수량이 제대로 숫자가 들어가있는지 확인하고 만약 숫자가 아니면
			// 제이옵션패널을 나타나게 하여 경고를 준다.
			try {
				H_StockDTO set_stockDTO = new H_StockDTO(pointNum, (String) g_KindOfBox.getSelectedItem(),
						(String) g_PnameBox.getSelectedItem(), Integer.parseInt(g_Quantity.getText()),
						(String) g_PlaceBox.getSelectedItem(), null);

				if (H_StockDAO.getInstance().insert(set_stockDTO) == 1) {
					g_Quantity.setText(null);
				}
				insertPointList(pointNum);
				insertInOutTotalStockHistoryList();
				inOutStockFrame.dispose();
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(inOutStockFrame, "수량에 숫자를 입력해 주세요.");
				e2.printStackTrace();
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
        //g_Quantity의 리스너 역할이며
		// 수량에 숫자 이외에 입력시 오류 메세지를 알려준다.
		try {
			Integer.parseInt(g_Quantity.getText());
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(inOutStockFrame, "수량에 숫자를 입력해 주세요.");
		}
	}// insertUpdate:메서드 종료

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == g_KindOfBox) {
			//입고 및 출고, 품목에 따른 업체 및 가맹점 콤보박스 데이터 삽입
			insertVenderNfranchise();
		}

		if (e.getSource() == g_PnameBox) {
			//입고 및 출고, 품목에 따른 업체 및 가맹점 콤보박스 데이터 삽입
			insertVenderNfranchise();
		}
	}// itemStateChanged메서드 끝

	private void insertVenderNfranchise() {
		// 창고 특정 위치에 입출고를 입력하는 다이얼로그에 보면 장소를 결정하는 콤보박스가 있다.
		// 출고와 입고의 여부에 따라 들어가는 장소가 달라지는 메서드이다.
		
		if (g_KindOfBox.getSelectedItem().equals("출고")) {
			ArrayList<H_FranchiseDTO> h_franchiseList = H_FranchiseDAO.getInstance().select_AliasNTel();
			// 출고에 해당하는 가맹점 데이터를 갖고오는 리스트
			
			g_PlaceBox.removeAllItems();
			for (int i = 0; i < h_franchiseList.size(); i++) {
				g_PlaceBox.addItem(h_franchiseList.get(i).getAlias());
			}
		} else {
			
			ArrayList<String> venderName = H_VenderpDAO.getInstance().select_vender((String) g_PnameBox.getSelectedItem());
			//업체 이름을 갖고오는 리스트
			
			g_PlaceBox.removeAllItems();
			for (int i = 0; i < venderName.size(); i++) {
				g_PlaceBox.addItem(venderName.get(i));
			}
		}
	}// insertVenderNfranchise:메서드 끝

	private void insertPointList(String point) {
		// 창고의 특정 배치도 버튼을 누르면 나오는 다이얼로그에서 좌측에 보면
		// 특정 장소에 들어가 있는 재고를 알 수가 있다. 거기에 들어가는 재고내용이다.
		
		ArrayList<H_StockDTO> pointStockList = H_StockDAO.getInstance().selectPointAll(point);
		// 해당 위치에 있는 재고 데이터를 갖고 있는 리스트
		int count = g_InputStockModel.getRowCount();

		for (int i = 0; i < count; i++) {
			g_InputStockModel.removeRow(0);
		}

		for (int i = 0; i < pointStockList.size(); i++) {
			g_InputStockModel.insertRow(0,
					new Object[] { pointStockList.get(i).getName(), pointStockList.get(i).getQuantity() });
		}
	}// insertPointList:메서드 끝

	private void insertInOutTotalStockHistoryList() {
		// 입출고관리 화면에서 모든 테이블이 존재하는데 (다이얼로그에 있는 테이블은 제외)
		// 그 현황테이블들에 자료가 들어가는 메서드이다.
		
		ArrayList<H_StockDTO> inStockList = H_StockDAO.getInstance().selectInStockHistory();
		//입고 관련 정보를 갖고 있는 리스트
		
		int count = g_InStockListModel.getRowCount();

		if (count > 0) {
			for (int i = 0; i < count; i++) {
				g_InStockListModel.removeRow(0);
			}
		}
		for (int i = 0; i < inStockList.size(); i++) {
			g_InStockListModel.insertRow(0,
					new Object[] { inStockList.get(i).getName(), inStockList.get(i).getQuantity(),
							inStockList.get(i).getPoint(), inStockList.get(i).getPlace(),
							inStockList.get(i).getDate().substring(0, 16) });
		}

		ArrayList<H_StockDTO> outStockList = H_StockDAO.getInstance().selectOutStockHistory();
		//출고 관련 정보를 갖고 있는 리스트
		
		count = g_OutStockListModel.getRowCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				g_OutStockListModel.removeRow(0);
			}
		}
		for (int i = 0; i < outStockList.size(); i++) {
			g_OutStockListModel.insertRow(0,
					new Object[] { outStockList.get(i).getName(), outStockList.get(i).getQuantity(),
							outStockList.get(i).getPoint(), outStockList.get(i).getPlace(),
							outStockList.get(i).getDate().substring(0, 16) });
		}

		ArrayList<H_StockDTO> totalStokList = H_StockDAO.getInstance().selectTotalStock();
		//입고 및 출고를 다 합산한 자료를 갖고 있는 리스트
		
		count = g_StockListModel.getRowCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				g_StockListModel.removeRow(0);
			}
		}
		for (int i = 0; i < totalStokList.size(); i++) {
			g_StockListModel.insertRow(0,
					new Object[] { totalStokList.get(i).getName(), totalStokList.get(i).getQuantity() });
		}
	}// insertInOutTotalStockHistoryList:메서드 종료

	@Override
	public void removeUpdate(DocumentEvent e) {
	}// 이 메서드는 정의하지 않습니다.

	@Override
	public void changedUpdate(DocumentEvent e) {
	}// 이 메서드는 정의하지 않습니다.
	
	@Override // 인터페이스 BBQHead로부터 받은 메서드 show 오버라이딩
	public void show(BBQHead bbqHead) {
	}// 이 메서드는 정의하지 않습니다.

	@Override // 인터페이스 BBQHead로부터 받은 메서드 hide 오버라이딩
	public void hide(BBQHead bbqHead) {
	}// 이 메서드는 정의하지 않습니다.


}// 클래스 끝
