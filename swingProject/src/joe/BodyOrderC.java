package joe;

import java.awt.BorderLayout;
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
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;

public class BodyOrderC extends JPanel implements BodyOrder, ActionListener {

	// 콤보박스 안에 들어갈 목록

	private JLabel j;
	private JLabel j1;
	private DefaultTableModel model = new DefaultTableModel(0, 2);// 발주할 표
	private DefaultTableModel model1 = new DefaultTableModel(16, 2);// 재고 목록 표
	private DefaultTableModel model2 = new DefaultTableModel(0, 4);// 발주 목록 표

	// 리스트를 넣을 Jtable
	private JTable listTable = new JTable(model) {// 발주할 테이블
		public boolean isCellEditable(int row, int column) {// 내부 표 수정불가
			return false;
		};
	};
	private JTable listTable1 = new JTable(model1) {// 발주목록 테이블
		public boolean isCellEditable(int row, int column) {// 내부 표 수정불가
			return false;
		};
	};
	private JTable listTable2 = new JTable(model2) {// 재고 목록 테이블
		public boolean isCellEditable(int row, int column) {// 내부 표 수정불가
			return false;
		};
	};

	private JButton bt;// 선택 버튼
	private JButton bt1;// 발주 버튼
	private JButton bt2;// 삭제 버튼
	private JButton bt3;// 발주목록 버튼
	private JButton bt4;// 발주목록 지우기 버튼
	private JButton bt5;// 발주 하기전 목록 전체 지우기
	private JButton btJego;// 재고 목록
	private JButton btDelete;

	private JComboBox jCom;// 토글버튼의 기능을 사용할 수 있게 해주는 버튼 그룹
	private JTextField jf;

	private JScrollPane scroll = new JScrollPane(listTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 발주 테이블
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	private JScrollPane scroll1 = new JScrollPane(listTable1, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 발주목록
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	private JScrollPane scroll2 = new JScrollPane(listTable2, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 재고테이블
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JLabel label;
	private JLabel label_1;
	private ArrayList<Integer> listNum;//발주 목록을 볼 때 각행별 고유 num을 담고 있는 리스트
	private JTextField textField;//식자재 단가입력란
	private int[] selects;//발주 취소시 다중선택을 받는 배열

	// Jtable의 스크롤 기능 객체 w
	// private DefaultTableCellRenderer celAlignCenter = new
	// DefaultTableCellRenderer();
	// Jtable의 가운데 정렬 객체

	public BodyOrderC() {// 생성자

		setLayout(null);
		setSize(781, 399);

		// 라벨의 위치 이름 폰트설정
		j = new JLabel("식자재 목록");
		j.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		j.setHorizontalAlignment(SwingConstants.CENTER);
		j1 = new JLabel("수량");
		j1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		j1.setHorizontalAlignment(SwingConstants.CENTER);
		j.setBounds(0, 10, 80, 38);
		j1.setBounds(151, 19, 65, 20);

		scroll.setBounds(23, 94, 200, 239);
		scroll1.setBounds(599, 58, 182, 275);
		scroll2.setBounds(263, 94, 293, 239);
		// 테이블내
		listTable.getTableHeader().setResizingAllowed(false);
		listTable1.getTableHeader().setResizingAllowed(false);
		listTable2.getTableHeader().setResizingAllowed(false);
		listTable.getTableHeader().setReorderingAllowed(false);
		listTable1.getTableHeader().setReorderingAllowed(false);
		listTable2.getTableHeader().setReorderingAllowed(false);

		model.setColumnIdentifiers(new Object[] { "식자재", "수량" });
		model1.setColumnIdentifiers(new Object[] { "식자재", "수량" });
		model2.setColumnIdentifiers(new Object[] { "식자재", "수량", "발주일", "본사확인" });

		// listTable2.getColumnModel().getColumn(4).setPreferredWidth(-10);

		add(scroll2);
		add(scroll1);
		add(scroll);
		add(j1);
		add(j, BorderLayout.WEST);

		jCom = new JComboBox(H_VenderpDAO.getInstance().select_product().toArray());
		jCom.setBounds(78, 18, 80, 21);
		add(jCom);

		jf = new JTextField();
		jf.setBounds(208, 18, 80, 21);
		add(jf);

		JLabel lblNewLabel = new JLabel("\uC7AC\uACE0 \uBAA9\uB85D");
		lblNewLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 14));
		lblNewLabel.setBounds(656, 28, 57, 20);
		add(lblNewLabel);

		bt = new JButton("\uC120\uD0DD");
		bt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		bt.setBounds(313, 18, 74, 23);
		add(bt);

		bt1 = new JButton("\uBC1C\uC8FC");
		bt1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		bt1.setBounds(23, 340, 84, 23);
		add(bt1);

		bt2 = new JButton("\uC0AD\uC81C");
		bt2.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		bt2.setBounds(399, 18, 74, 23);
		add(bt2);

		bt3 = new JButton("\uBC1C\uC8FC \uBAA9\uB85D");
		bt3.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		bt3.setBounds(253, 340, 103, 23);
		add(bt3);

		bt4 = new JButton("\uBAA9\uB85D \uC9C0\uC6B0\uAE30");
		bt4.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		bt4.setBounds(355, 340, 110, 23);
		add(bt4);

		bt5 = new JButton("\uBAA9\uB85D\uC9C0\uC6B0\uAE30");
		bt5.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		bt5.setBounds(119, 340, 110, 23);
		add(bt5);

		btJego = new JButton("\uC7AC\uACE0 \uBAA9\uB85D");
		btJego.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		btJego.setBounds(642, 340, 113, 23);
		add(btJego);

		btDelete = new JButton("\uBC1C\uC8FC \uCDE8\uC18C");
		btDelete.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		btDelete.setBounds(465, 340, 103, 23);
		add(btDelete);

		bt.addActionListener(this);
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		bt3.addActionListener(this);
		bt4.addActionListener(this);
		bt5.addActionListener(this);
		btJego.addActionListener(this);
		btDelete.addActionListener(this);

		label = new JLabel("\uBC1C\uC8FC");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 14));
		label.setBounds(90, 73, 57, 20);
		add(label);

		label_1 = new JLabel("\uBC1C\uC8FC \uBAA9\uB85D");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 14));
		label_1.setBounds(378, 73, 57, 20);
		add(label_1);

		textField = new JTextField();
		textField.setBounds(208, 44, 80, 21);
		add(textField);

		JLabel lblEksrk = new JLabel("\uB2E8\uAC00");
		lblEksrk.setHorizontalAlignment(SwingConstants.CENTER);
		lblEksrk.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		lblEksrk.setBounds(151, 45, 65, 20);
		add(lblEksrk);

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


	public void orderList() {//발주목록 보는 메서드 
		listNum = new ArrayList<>();
		int a = B_OrderDAO.getInstance().selectAll(BodyFrame.id).size();
		System.out.println(a);
		for (int i = 0; i < a; i++) {
			listNum.add(B_OrderDAO.getInstance().selectAll(BodyFrame.id).get(i).getNum());
		}
		if (model2.getRowCount() == 0) {
			for (int i = 0; i < a; i++) {
				model2.insertRow(i,
						new Object[] { B_OrderDAO.getInstance().selectAll(BodyFrame.id).get(i).getName(),
								B_OrderDAO.getInstance().selectAll(BodyFrame.id).get(i).getQuantity(),
								B_OrderDAO.getInstance().selectAll(BodyFrame.id).get(i).getDate(),
								B_OrderDAO.getInstance().selectAll(BodyFrame.id).get(i).gethComfirm() });
			}
		} else {
			for (int i = 0; i < a; i++) {
				model2.removeRow(0);
			}
			for (int i = 0; i < a; i++) {
				model2.insertRow(i,
						new Object[] { B_OrderDAO.getInstance().selectAll(BodyFrame.id).get(i).getName(),
								B_OrderDAO.getInstance().selectAll(BodyFrame.id).get(i).getQuantity(),
								B_OrderDAO.getInstance().selectAll(BodyFrame.id).get(i).getDate(),
								B_OrderDAO.getInstance().selectAll(BodyFrame.id).get(i).gethComfirm() });
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt) {// 선택버튼기능
			model.insertRow(0, new Object[] { (String) jCom.getSelectedItem(), jf.getText() });
		} else if (e.getSource() == bt2) {// 삭제버튼기능

			if (model.getValueAt(0, 0) == null) {
				JOptionPane.showMessageDialog(null, "지울 목록이 없습니다.");
			} else if (!(model.getValueAt(0, 0) == null) && listTable.getSelectedRowCount() == 0) {
				JOptionPane.showMessageDialog(null, "지울 목록을 선택해주세요.");
			} else {
				int[] a = listTable.getSelectedRows();
				int b = listTable.getSelectedRowCount();
				for (int i = b - 1; i >= 0; i--) {
					model.removeRow(a[i]);
				}
			}
		} else if (e.getSource() == bt1) {// 발주버튼 기능

			for (int j = 0; j < model.getRowCount(); j++) {
				String test2 = (String) model.getValueAt(j, 0);
				int test3 = Integer.parseInt((String) model.getValueAt(j, 1));
				B_OrderDAO.getInstance().orderInsert(BodyFrame.id, test2, test3);
			}
			int a = model.getRowCount();
			for (int j = 0; j < a; j++) {
				model.removeRow(0);
			}

		} else if (e.getSource() == bt3) {// 발주목록 버튼기능

			if (model2.getRowCount() == 0) {
				orderList();
			} else {
				int count = model2.getRowCount();
				for (int i = 0; i < count; i++) {
					model2.removeRow(0);
				}
				orderList();
			}

		} else if (e.getSource() == bt4) {// 발주후 목록지우기 버튼기능
			if (model2.getValueAt(0, 0) == null) {

			} else {
				for (int i = 0; i < B_OrderDAO.getInstance().selectAll(BodyFrame.id).size(); i++) {
					model2.removeRow(0);
				}

			}

		} else if (e.getSource() == bt5) {// 발주하기전 목록 지우기기능
			for (int i = 0; i >= 0; i++) {
				if (model.getValueAt(0, 0) == null) {
					break;
				} else {
					model.removeRow(0);
				}
			}
		} else if (e.getSource() == btJego) {// 재고 확인 버튼
			if (model1.getValueAt(0, 0) == null) {// model1의 첫번째 줄에 아무값도 없으면 재고를 가져온다
				for (int j = 0; j < B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).size(); j++) {
					model1.insertRow(j,
							new Object[] { B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).get(j).getName(),
									B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).get(j).getQuantity() });
				}

			} else {
				for (int i = 0; i < B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).size(); i++) {
					model1.removeRow(0);
				}
				for (int j = 0; j < B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).size(); j++) {
					model1.insertRow(j,
							new Object[] { B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).get(j).getName(),
									B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).get(j).getQuantity() });
				}

			}

		} else if (e.getSource() == btDelete) {// 발주취소 버튼 작업중
			selects = listTable2.getSelectedRows();
			for (int i = 0; i < selects.length; i++) {
				if (model2.getValueAt(selects[i], 3).equals("")) {
					B_OrderDAO.getInstance().orderDelete(listNum.get(selects[i]));
				}
			}
			for (int j = selects.length - 1; j >= 0; j--) {
				model2.removeRow(selects[j]);
			}
			orderList();
		}
	}// 액션 리스터 끝
}// 클래스끝
