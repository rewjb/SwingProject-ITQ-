package body;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import inter.BBQBody;
import inter.BodyOrder;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import DTO_DAO.B_OrderDAO;
import DTO_DAO.B_StockDAO;
import DTO_DAO.H_VenderpDAO;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;

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

public class B_OrderC extends JPanel implements BodyOrder, ActionListener, ItemListener {

	private JLabel g_ReservesLabel;//식자재 라벨
	private JLabel g_AmountLabel;//식자재 수량 라벨
	private JLabel g_UnitPriceLabel;//식자재 단가 라벨
	private JLabel g_OrderLabel;//발주 라벨
	private JLabel g_OrderListLabel;//발주목록 라벨
	private DefaultTableModel g_OrderModel = new DefaultTableModel(0, 3);// 발주할 표
	private DefaultTableModel g_StockModel = new DefaultTableModel(0, 2);// 재고 목록 표
	private DefaultTableModel g_OrderListModel = new DefaultTableModel(0, 4);// 발주 목록 표

	// 리스트를 넣을 Jtable
	private JTable g_OrderTable = new JTable(g_OrderModel) {// 발주할 테이블
		public boolean isCellEditable(int row, int column) {// 내부 표 수정불가
			return false;
		};
	};
	private JTable g_OrderListTable = new JTable(g_StockModel) {// 발주목록 테이블
		public boolean isCellEditable(int row, int column) {// 내부 표 수정불가
			return false;
		};
	};
	private JTable g_StockListTable = new JTable(g_OrderListModel) {// 재고 목록 테이블
		public boolean isCellEditable(int row, int column) {// 내부 표 수정불가
			return false;
		};
	};

	private JButton g_SelectBt;// 선택 버튼
	private JButton g_OrderBt;// 발주 버튼
	private JButton g_DeleteBt;// 삭제 버튼
	private JButton g_OrderListBt;// 발주목록 버튼
	private JButton g_OrderListDeleteBt;// 발주목록 지우기 버튼
	private JButton g_OrderListAllDeleteBt;// 발주 하기전 목록 전체 지우기
	private JButton g_StockListBt;// 재고 목록
	private JButton g_OrderDeleteBt;//발주 취소 버튼
	private JButton g_ResetButton;//새로고침 버튼
	

	private JComboBox g_ReservesComboBox;// 식자재 목록이 나오는 콤보박스
	private JTextField g_QuantityTextField;
	private File g_File;

	private JScrollPane g_OrderScroll = new JScrollPane(g_OrderTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 발주하는 테이블 스크롤
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	private JScrollPane g_OrderListScroll = new JScrollPane(g_OrderListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 발주목록 스크롤
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	private JScrollPane g_StockListScroll = new JScrollPane(g_StockListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 재고테이블 스크
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private ArrayList<Integer> g_ListNum;// 발주 목록을 볼 때 각행별 고유 num을 담고 있는 리스트
	private JTextField g_ReservesTextField;// 식자재 단가입력란
	private int[] g_Selects;// 발주 취소시 다중선택을 받는 배열
	private ArrayList<String> g_FileOutPutArrayList = new ArrayList<>();//파일입출력용 리스트  (-앞뒤로 스플릿해서 [0]을 갖고있음)
	private ArrayList<String> g_FileOutPutArrayListSecond = new ArrayList<>();//파일입출력용 리스트 2 (-앞뒤로 스플릿해서 [1]을 갖고있음)

	// Jtable의 스크롤 기능 객체 w
	// private DefaultTableCellRenderer celAlignCenter = new
	// DefaultTableCellRenderer();
	// Jtable의 가운데 정렬 객체

	public B_OrderC() {// 생성자

		setLayout(null);
		setSize(790, 399);
		setBackground(new Color(184,207,229));
		
		// 라벨의 위치 이름 폰트설정
		g_ReservesLabel = new JLabel("식자재 목록");
		g_ReservesLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		g_ReservesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_AmountLabel = new JLabel("수량");
		g_AmountLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		g_AmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_ReservesLabel.setBounds(0, 10, 80, 38);
		g_AmountLabel.setBounds(151, 19, 65, 20);

		//스크롤바에 위치 및 크기 조정 
		g_OrderScroll.setBounds(23, 94, 200, 239);
		g_OrderListScroll.setBounds(599, 58, 182, 275);
		g_StockListScroll.setBounds(263, 94, 293, 239);
		// 테이블 컬럼 드래그로 이동불가 
		g_OrderTable.getTableHeader().setResizingAllowed(false);
		g_OrderListTable.getTableHeader().setResizingAllowed(false);
		g_StockListTable.getTableHeader().setResizingAllowed(false);
		g_OrderTable.getTableHeader().setReorderingAllowed(false);
		g_OrderListTable.getTableHeader().setReorderingAllowed(false);
		g_StockListTable.getTableHeader().setReorderingAllowed(false);

		//각 표마다 들어갈 컬럼명 
		g_OrderModel.setColumnIdentifiers(new Object[] { "식자재", "수량", "금액" });
		g_StockModel.setColumnIdentifiers(new Object[] { "식자재", "수량" });
		g_OrderListModel.setColumnIdentifiers(new Object[] { "식자재", "수량", "발주일", "본사확인" });
		g_StockListTable.getColumnModel().getColumn(2).setPreferredWidth(90);//특정 컬럼 간격 조정 
		

		// listTable2.getColumnModel().getColumn(4).setPreferredWidth(-10);

		add(g_StockListScroll);
		add(g_OrderListScroll);
		add(g_OrderScroll);
		add(g_AmountLabel);
		add(g_ReservesLabel, BorderLayout.WEST);

		g_ReservesComboBox = new JComboBox(H_VenderpDAO.getInstance().select_product().toArray());
		g_ReservesComboBox.setBounds(78, 18, 80, 21);
		add(g_ReservesComboBox);

		g_QuantityTextField = new JTextField();
		g_QuantityTextField.setBounds(208, 18, 80, 21);
		add(g_QuantityTextField);
		//테이블 내부 색깔
		g_OrderTable.setBackground(Color.LIGHT_GRAY);
		g_OrderListTable.setBackground(Color.LIGHT_GRAY);
		g_StockListTable.setBackground(Color.LIGHT_GRAY);

		g_SelectBt = new JButton("\uC120\uD0DD");
		g_SelectBt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		g_SelectBt.setBounds(313, 18, 74, 23);
		add(g_SelectBt);

		g_OrderBt = new JButton("\uBC1C\uC8FC");
		g_OrderBt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		g_OrderBt.setBounds(23, 340, 84, 23);
		add(g_OrderBt);

		g_DeleteBt = new JButton("\uC0AD\uC81C");
		g_DeleteBt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		g_DeleteBt.setBounds(399, 18, 74, 23);
		add(g_DeleteBt);

		g_OrderListBt = new JButton("\uBC1C\uC8FC \uBAA9\uB85D");
		g_OrderListBt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		g_OrderListBt.setBounds(253, 340, 103, 23);
		add(g_OrderListBt);

		g_OrderListDeleteBt = new JButton("\uBAA9\uB85D \uC9C0\uC6B0\uAE30");
		g_OrderListDeleteBt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		g_OrderListDeleteBt.setBounds(355, 340, 110, 23);
		add(g_OrderListDeleteBt);

		g_OrderListAllDeleteBt = new JButton("\uBAA9\uB85D\uC9C0\uC6B0\uAE30");
		g_OrderListAllDeleteBt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		g_OrderListAllDeleteBt.setBounds(119, 340, 110, 23);
		add(g_OrderListAllDeleteBt);

		g_StockListBt = new JButton("\uC7AC\uACE0 \uBAA9\uB85D");
		g_StockListBt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		g_StockListBt.setBounds(642, 340, 113, 23);
		add(g_StockListBt);

		g_OrderDeleteBt = new JButton("\uBC1C\uC8FC \uCDE8\uC18C");
		g_OrderDeleteBt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		g_OrderDeleteBt.setBounds(465, 340, 103, 23);
		add(g_OrderDeleteBt);
		
		g_ResetButton = new JButton("\uC0C8\uB85C\uACE0\uCE68");
		g_ResetButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		g_ResetButton.setBounds(482, 17, 80, 23);
		add(g_ResetButton);

		g_SelectBt.addActionListener(this);
		g_OrderBt.addActionListener(this);
		g_DeleteBt.addActionListener(this);
		g_OrderListBt.addActionListener(this);
		g_OrderListDeleteBt.addActionListener(this);
		g_OrderListAllDeleteBt.addActionListener(this);
		g_StockListBt.addActionListener(this);
		g_OrderDeleteBt.addActionListener(this);
		g_ReservesComboBox.addItemListener(this);
		g_ResetButton.addActionListener(this);

		g_OrderLabel = new JLabel("\uBC1C\uC8FC");
		g_OrderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_OrderLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 14));
		g_OrderLabel.setBounds(90, 73, 57, 20);
		add(g_OrderLabel);
		
		g_OrderListLabel = new JLabel("\uBC1C\uC8FC \uBAA9\uB85D");
		g_OrderListLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_OrderListLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 14));
		g_OrderListLabel.setBounds(378, 73, 57, 20);
		add(g_OrderListLabel);

		g_ReservesTextField = new JTextField();
		g_ReservesTextField.setBounds(208, 44, 80, 21);
		g_ReservesTextField.setEditable(false);
		add(g_ReservesTextField);

		g_UnitPriceLabel = new JLabel("\uB2E8\uAC00");
		g_UnitPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_UnitPriceLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		g_UnitPriceLabel.setBounds(151, 45, 65, 20);
		add(g_UnitPriceLabel);
		
		try {
			reader();
		} catch (Exception e) {
			e.printStackTrace();
		}

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

	
	
	
	
	public void orderList() {// 발주목록 보는 메서드
		g_ListNum = new ArrayList<>();
		int selectAllArrayListSize = B_OrderDAO.getInstance().selectAll(B_Frame.st_G_id).size();
		for (int i = 0; i < selectAllArrayListSize; i++) {
			g_ListNum.add(B_OrderDAO.getInstance().selectAll(B_Frame.st_G_id).get(i).getNum());
		}
		if (g_OrderListModel.getRowCount() == 0) {
			for (int i = 0; i < selectAllArrayListSize; i++) {
				g_OrderListModel.insertRow(i,
						new Object[] { B_OrderDAO.getInstance().selectAll(B_Frame.st_G_id).get(i).getName(),
								B_OrderDAO.getInstance().selectAll(B_Frame.st_G_id).get(i).getQuantity(),
								B_OrderDAO.getInstance().selectAll(B_Frame.st_G_id).get(i).getDate().substring(0, 10),
								B_OrderDAO.getInstance().selectAll(B_Frame.st_G_id).get(i).gethComfirm() });
			}
		} 
	}
	
	public void reader() throws Exception {//단가를 가져오는 메서드 (파일 입출력)
		g_File = new File("H_VenderpName.txt");
		Scanner sc = new Scanner(g_File);
		if (g_File.exists()) {
			while (sc.hasNextLine()) {
				String[] read = sc.nextLine().split("-");
				g_FileOutPutArrayList.add(read[0]);
				g_FileOutPutArrayListSecond.add(read[1]);
			}
			if (g_ReservesComboBox.getSelectedItem()==null) {
				
			}else {
				for (int j = 0; j < g_FileOutPutArrayList.size(); j++) {
					if (g_ReservesComboBox.getSelectedItem().equals(g_FileOutPutArrayList.get(j))) {
						g_ReservesTextField.setText(g_FileOutPutArrayListSecond.get(j));
					}
				}
			}
		}
	}
	
	@Override
	public void reservesReset() {//식자재 목록 새로고침
		g_ReservesComboBox.removeAllItems();
		int count = H_VenderpDAO.getInstance().select_product().size();
		for (int i = 0; i <count; i++) {
			g_ReservesComboBox.addItem(H_VenderpDAO.getInstance().select_product().get(i));
		}
	}
	
	@Override
	public void orderDelete() {//발주 취소 메서드
		int checkCount = 0;
		g_Selects = g_StockListTable.getSelectedRows();
		for (int i = 0; i < g_Selects.length; i++) {
			if (g_OrderListModel.getValueAt(g_Selects[i], 3).equals("ck_1")) {
				JOptionPane.showMessageDialog(null, "본사확인이 완료된 발주는 취소할 수 없습니다.");
				checkCount++;
				break;
			}
		}
		for (int i = 0; i < g_Selects.length; i++) {
			if (g_OrderListModel.getValueAt(g_Selects[i], 3).equals("") && checkCount == 0 ) {
				B_OrderDAO.getInstance().orderDelete(g_ListNum.get(g_Selects[i]));
			}
		}  
		int count = g_OrderListModel.getRowCount();
		for (int i = 0; i < count;  i++) {
			g_OrderListModel.removeRow(0);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == g_SelectBt) {// 선택버튼기능
			
			if (!(g_QuantityTextField.getText().equals(""))) {
				g_OrderModel.insertRow(0, new Object[] { (String) g_ReservesComboBox.getSelectedItem(), g_QuantityTextField.getText(),(Integer.parseInt(g_QuantityTextField.getText())*Integer.parseInt(g_ReservesTextField.getText()))});
			}
			
		} else if (e.getSource() == g_DeleteBt) {// 삭제버튼기능

			if (g_OrderModel.getRowCount()==0) {
				JOptionPane.showMessageDialog(null, "지울 목록이 없습니다.");
			} else if (!(g_OrderModel.getRowCount()==0) && g_OrderTable.getSelectedRowCount() == 0) {
				JOptionPane.showMessageDialog(null, "지울 목록을 선택해주세요.");
			} else {
				int[] a = g_OrderTable.getSelectedRows();
				int b = g_OrderTable.getSelectedRowCount();
				for (int i = b - 1; i >= 0; i--) {
					g_OrderModel.removeRow(a[i]);
				}
			}
		} else if (e.getSource() == g_OrderBt) {// 발주버튼 기능

			for (int j = 0; j < g_OrderModel.getRowCount(); j++) {
				String test2 = (String) g_OrderModel.getValueAt(j, 0);
				int test3 = Integer.parseInt((String) g_OrderModel.getValueAt(j, 1));
				B_OrderDAO.getInstance().orderInsert(B_Frame.st_G_id, test2, test3);
			}
			int orderModelRowCount = g_OrderModel.getRowCount();
			for (int j = 0; j < orderModelRowCount; j++) {
				g_OrderModel.removeRow(0);
			}

		} else if (e.getSource() == g_OrderListBt) {// 발주목록 버튼기능

			if (g_OrderListModel.getRowCount() == 0) {
				orderList();
			} else {
				int count = g_OrderListModel.getRowCount();
				for (int i = 0; i < count; i++) {
					g_OrderListModel.removeRow(0);
				}
				orderList();
			}

		} else if (e.getSource() == g_OrderListDeleteBt) {// 발주후 목록지우기 버튼기능
			if (g_OrderListModel.getRowCount()==0) {

			} else {
				for (int i = 0; i < B_OrderDAO.getInstance().selectAll(B_Frame.st_G_id).size(); i++) {
					g_OrderListModel.removeRow(0);
				}

			}

		} else if (e.getSource() == g_OrderListAllDeleteBt) {// 발주하기전 목록 지우기기능
			int rowCount = g_OrderModel.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				g_OrderModel.removeRow(0);
			}
		} else if (e.getSource() == g_StockListBt) {// 재고 확인 버튼
			if (g_StockModel.getRowCount()==0) {// model1의 첫번째 줄에 아무값도 없으면 재고를 가져온다
				for (int j = 0; j < B_StockDAO.getInstance().stockSelectAll(B_Frame.st_G_id).size(); j++) {
					g_StockModel.insertRow(j,
							new Object[] { B_StockDAO.getInstance().stockSelectAll(B_Frame.st_G_id).get(j).getName(),
									B_StockDAO.getInstance().stockSelectAll(B_Frame.st_G_id).get(j).getQuantity() });
				}

			} else {
				int rowCount = g_StockModel.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					g_StockModel.removeRow(0);
				}
				for (int j = 0; j < B_StockDAO.getInstance().stockSelectAll(B_Frame.st_G_id).size(); j++) {
					g_StockModel.insertRow(j,
							new Object[] { B_StockDAO.getInstance().stockSelectAll(B_Frame.st_G_id).get(j).getName(),
									B_StockDAO.getInstance().stockSelectAll(B_Frame.st_G_id).get(j).getQuantity() });
				}

			}
  
		} else if (e.getSource() == g_OrderDeleteBt) {// 발주취소 버튼 
			orderDelete();
			orderList();
		}else if (e.getSource() == g_ResetButton) {//새로고침 버튼눌렀을때
			reservesReset();
		}
	}// 액션 리스터 끝


	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == g_ReservesComboBox) {
			try {
				reader();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
}// 클래스끝
