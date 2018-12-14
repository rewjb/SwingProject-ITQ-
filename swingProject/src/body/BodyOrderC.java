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

public class BodyOrderC extends JPanel implements BodyOrder, ActionListener, ItemListener {

	private JLabel reservesLabel;
	private JLabel amountLabel;
	private DefaultTableModel orderModel = new DefaultTableModel(0, 3);// 발주할 표
	private DefaultTableModel stockModel = new DefaultTableModel(16, 2);// 재고 목록 표
	private DefaultTableModel orderList = new DefaultTableModel(0, 4);// 발주 목록 표

	// 리스트를 넣을 Jtable
	private JTable orderTable = new JTable(orderModel) {// 발주할 테이블
		public boolean isCellEditable(int row, int column) {// 내부 표 수정불가
			return false;
		};
	};
	private JTable orderListTable = new JTable(stockModel) {// 발주목록 테이블
		public boolean isCellEditable(int row, int column) {// 내부 표 수정불가
			return false;
		};
	};
	private JTable stockListTable = new JTable(orderList) {// 재고 목록 테이블
		public boolean isCellEditable(int row, int column) {// 내부 표 수정불가
			return false;
		};
	};

	private JButton selectBt;// 선택 버튼
	private JButton orderBt;// 발주 버튼
	private JButton deleteBt;// 삭제 버튼
	private JButton orderListBt;// 발주목록 버튼
	private JButton orderListDeleteBt;// 발주목록 지우기 버튼
	private JButton orderListAllDeleteBt;// 발주 하기전 목록 전체 지우기
	private JButton stockListBt;// 재고 목록
	private JButton orderDeleteBt;//발주 취소 버튼
	private JButton resetButton;//새로고침 버튼
	

	private JComboBox reservesComboBox;// 식자재 목록이 나오는 콤보박스
	private JTextField quantityTextField;
	private File file;

	private JScrollPane orderScroll = new JScrollPane(orderTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 발주하는 테이블 스크롤
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	private JScrollPane orderListScroll = new JScrollPane(orderListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 발주목록 스크롤
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	private JScrollPane stockListScroll = new JScrollPane(stockListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 재고테이블 스크
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JLabel orderLabel;
	private JLabel orderListLabel;
	private ArrayList<Integer> listNum;// 발주 목록을 볼 때 각행별 고유 num을 담고 있는 리스트
	private JTextField reservesTextField;// 식자재 단가입력란
	private int[] selects;// 발주 취소시 다중선택을 받는 배열
	private ArrayList<String> fileOutPutArrayList = new ArrayList<>();//파일입출력용 리스트  (-앞뒤로 스플릿해서 [0]을 갖고있음)
	private ArrayList<String> fileOutPutArrayListSecond = new ArrayList<>();//파일입출력용 리스트 2 (-앞뒤로 스플릿해서 [1]을 갖고있음)

	// Jtable의 스크롤 기능 객체 w
	// private DefaultTableCellRenderer celAlignCenter = new
	// DefaultTableCellRenderer();
	// Jtable의 가운데 정렬 객체

	public BodyOrderC() {// 생성자

		setLayout(null);
		setSize(781, 399);
		setBackground(new Color(184,207,229));
		
		// 라벨의 위치 이름 폰트설정
		reservesLabel = new JLabel("식자재 목록");
		reservesLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		reservesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		amountLabel = new JLabel("수량");
		amountLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		reservesLabel.setBounds(0, 10, 80, 38);
		amountLabel.setBounds(151, 19, 65, 20);

		orderScroll.setBounds(23, 94, 200, 239);
		orderListScroll.setBounds(599, 58, 182, 275);
		stockListScroll.setBounds(263, 94, 293, 239);
		// 테이블내
		orderTable.getTableHeader().setResizingAllowed(false);
		orderListTable.getTableHeader().setResizingAllowed(false);
		stockListTable.getTableHeader().setResizingAllowed(false);
		orderTable.getTableHeader().setReorderingAllowed(false);
		orderListTable.getTableHeader().setReorderingAllowed(false);
		stockListTable.getTableHeader().setReorderingAllowed(false);

		orderModel.setColumnIdentifiers(new Object[] { "식자재", "수량", "금액" });
		stockModel.setColumnIdentifiers(new Object[] { "식자재", "수량" });
		orderList.setColumnIdentifiers(new Object[] { "식자재", "수량", "발주일", "본사확인" });

		// listTable2.getColumnModel().getColumn(4).setPreferredWidth(-10);

		add(stockListScroll);
		add(orderListScroll);
		add(orderScroll);
		add(amountLabel);
		add(reservesLabel, BorderLayout.WEST);

		reservesComboBox = new JComboBox(H_VenderpDAO.getInstance().select_product().toArray());
		reservesComboBox.setBounds(78, 18, 80, 21);
		add(reservesComboBox);

		quantityTextField = new JTextField();
		quantityTextField.setBounds(208, 18, 80, 21);
		add(quantityTextField);
		

		JLabel orderListLabel = new JLabel("\uC7AC\uACE0 \uBAA9\uB85D");
		orderListLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 14));
		orderListLabel.setBounds(656, 28, 57, 20);
		add(orderListLabel);

		selectBt = new JButton("\uC120\uD0DD");
		selectBt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		selectBt.setBackground(Color.BLACK);
		selectBt.setBounds(313, 18, 74, 23);
		add(selectBt);

		orderBt = new JButton("\uBC1C\uC8FC");
		orderBt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		orderBt.setBounds(23, 340, 84, 23);
		add(orderBt);

		deleteBt = new JButton("\uC0AD\uC81C");
		deleteBt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		deleteBt.setBounds(399, 18, 74, 23);
		add(deleteBt);

		orderListBt = new JButton("\uBC1C\uC8FC \uBAA9\uB85D");
		orderListBt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		orderListBt.setBounds(253, 340, 103, 23);
		add(orderListBt);

		orderListDeleteBt = new JButton("\uBAA9\uB85D \uC9C0\uC6B0\uAE30");
		orderListDeleteBt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		orderListDeleteBt.setBounds(355, 340, 110, 23);
		add(orderListDeleteBt);

		orderListAllDeleteBt = new JButton("\uBAA9\uB85D\uC9C0\uC6B0\uAE30");
		orderListAllDeleteBt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		orderListAllDeleteBt.setBounds(119, 340, 110, 23);
		add(orderListAllDeleteBt);

		stockListBt = new JButton("\uC7AC\uACE0 \uBAA9\uB85D");
		stockListBt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		stockListBt.setBounds(642, 340, 113, 23);
		add(stockListBt);

		orderDeleteBt = new JButton("\uBC1C\uC8FC \uCDE8\uC18C");
		orderDeleteBt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		orderDeleteBt.setBounds(465, 340, 103, 23);
		add(orderDeleteBt);
		
		resetButton = new JButton("\uC0C8\uB85C\uACE0\uCE68");
		resetButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		resetButton.setBounds(482, 17, 80, 23);
		add(resetButton);

		selectBt.addActionListener(this);
		orderBt.addActionListener(this);
		deleteBt.addActionListener(this);
		orderListBt.addActionListener(this);
		orderListDeleteBt.addActionListener(this);
		orderListAllDeleteBt.addActionListener(this);
		stockListBt.addActionListener(this);
		orderDeleteBt.addActionListener(this);
		reservesComboBox.addItemListener(this);
		resetButton.addActionListener(this);

		orderLabel = new JLabel("\uBC1C\uC8FC");
		orderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		orderLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 14));
		orderLabel.setBounds(90, 73, 57, 20);
		add(orderLabel);
		

		orderListLabel = new JLabel("\uBC1C\uC8FC \uBAA9\uB85D");
		orderListLabel.setHorizontalAlignment(SwingConstants.CENTER);
		orderListLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 14));
		orderListLabel.setBounds(378, 73, 57, 20);
		add(orderListLabel);

		reservesTextField = new JTextField();
		reservesTextField.setBounds(208, 44, 80, 21);
		reservesTextField.setEditable(false);
		add(reservesTextField);
		try {
			reader();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JLabel unitPriceLabel = new JLabel("\uB2E8\uAC00");
		unitPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		unitPriceLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		unitPriceLabel.setBounds(151, 45, 65, 20);
		add(unitPriceLabel);
		

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


	public void reservesReset() {//식자재 목록 새로고침
		reservesComboBox.removeAllItems();
		int count = H_VenderpDAO.getInstance().select_product().size();
		for (int i = 0; i <count; i++) {
			reservesComboBox.addItem(H_VenderpDAO.getInstance().select_product().get(i));
		}
	}
	
	
	
	
	public void orderList() {// 발주목록 보는 메서드
		listNum = new ArrayList<>();
		int selectAllArrayListSize = B_OrderDAO.getInstance().selectAll(BodyFrame.id).size();
		for (int i = 0; i < selectAllArrayListSize; i++) {
			listNum.add(B_OrderDAO.getInstance().selectAll(BodyFrame.id).get(i).getNum());
		}
		if (orderList.getRowCount() == 0) {
			for (int i = 0; i < selectAllArrayListSize; i++) {
				orderList.insertRow(i,
						new Object[] { B_OrderDAO.getInstance().selectAll(BodyFrame.id).get(i).getName(),
								B_OrderDAO.getInstance().selectAll(BodyFrame.id).get(i).getQuantity(),
								B_OrderDAO.getInstance().selectAll(BodyFrame.id).get(i).getDate(),
								B_OrderDAO.getInstance().selectAll(BodyFrame.id).get(i).gethComfirm() });
			}
		} 
//			else {
//
//			for (int i = 0; i < selectAllArrayListSize; i++) {
//				orderList.removeRow(0);
//			}
//			for (int i = 0; i < selectAllArrayListSize; i++) {
//				orderList.insertRow(i,
//						new Object[] { B_OrderDAO.getInstance().selectAll(BodyFrame.id).get(i).getName(),
//								B_OrderDAO.getInstance().selectAll(BodyFrame.id).get(i).getQuantity(),
//								B_OrderDAO.getInstance().selectAll(BodyFrame.id).get(i).getDate(),
//								B_OrderDAO.getInstance().selectAll(BodyFrame.id).get(i).gethComfirm() });
//			}
//		}
	}
	
	
	public void reader() throws Exception {//단가를 가져오는 메서드 (파일 입출력)
		file = new File("H_VenderpName.txt");
		Scanner sc = new Scanner(file);
		if (file.exists()) {
			while (sc.hasNextLine()) {
				String[] read = sc.nextLine().split("-");
				fileOutPutArrayList.add(read[0]);
				fileOutPutArrayListSecond.add(read[1]);
			}
			if (reservesComboBox.getSelectedItem()==null) {
				
			}else {
				for (int j = 0; j < fileOutPutArrayList.size(); j++) {
					if (reservesComboBox.getSelectedItem().equals(fileOutPutArrayList.get(j))) {
						reservesTextField.setText(fileOutPutArrayListSecond.get(j));
					}
				}
			}
		}
	}
	
	public void orderDelete() {//발주 취소 메서드
		selects = stockListTable.getSelectedRows();
		for (int i = 0; i < selects.length; i++) {
			if (orderList.getValueAt(selects[i], 3).equals("")) {
				B_OrderDAO.getInstance().orderDelete(listNum.get(selects[i]));
			}
		}
		int count = orderList.getRowCount();
		for (int i = 0; i < count;  i++) {
			orderList.removeRow(0);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == selectBt) {// 선택버튼기능
			
			if (!(quantityTextField.getText().equals(""))) {
				orderModel.insertRow(0, new Object[] { (String) reservesComboBox.getSelectedItem(), quantityTextField.getText(),(Integer.parseInt(quantityTextField.getText())*Integer.parseInt(reservesTextField.getText()))});
			}
			
		} else if (e.getSource() == deleteBt) {// 삭제버튼기능

			if (orderModel.getRowCount()==0) {
				JOptionPane.showMessageDialog(null, "지울 목록이 없습니다.");
			} else if (!(orderModel.getRowCount()==0) && orderTable.getSelectedRowCount() == 0) {
				JOptionPane.showMessageDialog(null, "지울 목록을 선택해주세요.");
			} else {
				int[] a = orderTable.getSelectedRows();
				int b = orderTable.getSelectedRowCount();
				for (int i = b - 1; i >= 0; i--) {
					orderModel.removeRow(a[i]);
				}
			}
		} else if (e.getSource() == orderBt) {// 발주버튼 기능

			for (int j = 0; j < orderModel.getRowCount(); j++) {
				String test2 = (String) orderModel.getValueAt(j, 0);
				int test3 = Integer.parseInt((String) orderModel.getValueAt(j, 1));
				B_OrderDAO.getInstance().orderInsert(BodyFrame.id, test2, test3);
			}
			int orderModelRowCount = orderModel.getRowCount();
			for (int j = 0; j < orderModelRowCount; j++) {
				orderModel.removeRow(0);
			}

		} else if (e.getSource() == orderListBt) {// 발주목록 버튼기능

			if (orderList.getRowCount() == 0) {
				orderList();
			} else {
				int count = orderList.getRowCount();
				for (int i = 0; i < count; i++) {
					orderList.removeRow(0);
				}
				orderList();
			}

		} else if (e.getSource() == orderListDeleteBt) {// 발주후 목록지우기 버튼기능
			if (orderList.getRowCount()==0) {

			} else {
				for (int i = 0; i < B_OrderDAO.getInstance().selectAll(BodyFrame.id).size(); i++) {
					orderList.removeRow(0);
				}

			}

		} else if (e.getSource() == orderListAllDeleteBt) {// 발주하기전 목록 지우기기능
			int rowCount = orderModel.getRowCount();
			for (int i = 0; i < rowCount; i++) {
					orderModel.removeRow(0);
			}
		} else if (e.getSource() == stockListBt) {// 재고 확인 버튼
			if (stockModel.getValueAt(0, 0) == null) {// model1의 첫번째 줄에 아무값도 없으면 재고를 가져온다
				for (int j = 0; j < B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).size(); j++) {
					stockModel.insertRow(j,
							new Object[] { B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).get(j).getName(),
									B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).get(j).getQuantity() });
				}

			} else {
				for (int i = 0; i < B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).size(); i++) {
					stockModel.removeRow(0);
				}
				for (int j = 0; j < B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).size(); j++) {
					stockModel.insertRow(j,
							new Object[] { B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).get(j).getName(),
									B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).get(j).getQuantity() });
				}

			}

		} else if (e.getSource() == orderDeleteBt) {// 발주취소 버튼 
			orderDelete();
			orderList();
		}else if (e.getSource() == resetButton) {//새로고침 버튼눌렀을때
			reservesReset();
		}
	}// 액션 리스터 끝


	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == reservesComboBox) {
			try {
				reader();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
}// 클래스끝
