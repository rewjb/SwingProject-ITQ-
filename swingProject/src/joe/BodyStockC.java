package joe;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import DTO_DAO.B_OrderDAO;
import DTO_DAO.B_StockDAO;
import inter.BBQBody;
import inter.BodyStock;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BodyStockC extends JPanel implements BodyStock, ActionListener {

	private JButton button;// 미확인 재고
	private JButton button_1;// 확인 재고
	private JButton button_2;// 확인

	private DefaultTableModel model = new DefaultTableModel(15, 4);
	private DefaultTableModel model1 = new DefaultTableModel(15, 2);

	private JTable listTable = new JTable(model) {//
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private JTable listTable1 = new JTable(model1) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};

	private JScrollPane scroll = new JScrollPane(listTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane scroll1 = new JScrollPane(listTable1, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	B_OrderDAO orderDAO = B_OrderDAO.getInstance();
	B_StockDAO stockDAO = B_StockDAO.getInstance();

	String id = "조광재";

	public BodyStockC() {
		setLayout(null);
		setSize(781, 360);
		setBackground(Color.BLACK);

		button = new JButton("미확인 재고");
		button_1 = new JButton("확인 재고");

		button.setBounds(47, 309, 107, 35);
		button_1.setBounds(443, 309, 101, 34);
		scroll.setBounds(37, 37, 283, 262);
		scroll1.setBounds(380, 37, 223, 262);

		listTable.getTableHeader().setResizingAllowed(false);
		model.setColumnIdentifiers(new Object[] { "식자재", "수량", "본사 확인", "가맹점 확인" });
		listTable1.getTableHeader().setResizingAllowed(false);
		model1.setColumnIdentifiers(new Object[] { "식자재", "수량" });

		button_2 = new JButton("\uD655\uC778");
		button_2.setBounds(193, 309, 107, 35);

		button.addActionListener(this);
		button_1.addActionListener(this);
		button_2.addActionListener(this);
		add(scroll1);
		add(scroll);
		add(button);
		add(button_1);

		add(button_2);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {// 미확인재고 버튼 기능
			if(model.getValueAt(0, 0)==null) {
				for (int i = 0; i < orderDAO.hCheckSelect().size(); i++) {// 본사만 확인 한 재고들을 보여주는 반복문
					model.insertRow(0, new Object[] { orderDAO.hCheckSelect().get(i).getName(),
							orderDAO.hCheckSelect().get(i).getQuantity(), orderDAO.hCheckSelect().get(i).gethComfirm(),
							orderDAO.hCheckSelect().get(i).getbComfirm() });
				}
			}else {
				for (int i = 0; !(model.getValueAt(0, 0)==null); i++) {
					model.removeRow(0);
				}
				for (int i = 0; i < orderDAO.hCheckSelect().size(); i++) {
					model.insertRow(0, new Object[] { orderDAO.hCheckSelect().get(i).getName(),
							orderDAO.hCheckSelect().get(i).getQuantity(), orderDAO.hCheckSelect().get(i).gethComfirm(),
							orderDAO.hCheckSelect().get(i).getbComfirm() });
				}
			}
			
		} else if (e.getSource() == button_2) {// 확인 버튼 기능

			if (model.getValueAt(0, 0) == null) {// 표에 미확인재고들이 없으면 확인할수없습니다.
				JOptionPane.showMessageDialog(null, "확인할 목록이 없습니다.");
			} else {// 표에 미확인 재고가 있으면 확인할 수 있습니다.
				for (int i = 0; i < orderDAO.hCheckSelect().size(); i++) {// 확인버튼 누를때 발주dB에 있는 데이터를 재고 DB로 옮기는 반복문
					stockDAO.insertStock(id, orderDAO.hCheckSelect().get(i).getName(),
							orderDAO.hCheckSelect().get(i).getQuantity());
				}
//				for (int i = 0; i < orderDAO.hCheckSelect().size(); i++) {
//					model.removeRow(0);
//				}
				orderDAO.bConfirmUpdate();// 가맹점 확인 메서드
				for (int i = 0; i>=0; i++) {
					if(!(model.getValueAt(i, 0)==null)) {
						model.setValueAt("bk-1", i, 3);
					}else {
						break;
					}
				}
				if (!(model1.getValueAt(0, 0) == null)) {
					for (int i = 0; !(model1.getValueAt(0, 0) == null); i++) {// 확인재고 버튼을 누를때마다 실시간으로 업데이트 하기위해 표를 지워주는 반복문
						model1.removeRow(0);

					}
					for (int i = 0; i < stockDAO.stockSelectAll().size(); i++) {
						model1.insertRow(0, new Object[] { stockDAO.stockSelectAll().get(i).getName(),
								stockDAO.stockSelectAll().get(i).getQuantity() });
					}
				} else {

					for (int i = 0; i < stockDAO.stockSelectAll().size(); i++) {
						model1.insertRow(0, new Object[] { stockDAO.stockSelectAll().get(i).getName(),
								stockDAO.stockSelectAll().get(i).getQuantity() });
					}
				}
					
					


			}

		} else if (e.getSource() == button_1) {// 확인재고 버튼 기능
			if (!(model1.getValueAt(0, 0) == null)) {
				for (int i = 0; !(model1.getValueAt(0, 0) == null); i++) {// 확인재고 버튼을 누를때마다 실시간으로 업데이트 하기위해 표를 지워주는 반복문
					model1.removeRow(0);

				}
				for (int i = 0; i < stockDAO.stockSelectAll().size(); i++) {
					model1.insertRow(0, new Object[] { stockDAO.stockSelectAll().get(i).getName(),
							stockDAO.stockSelectAll().get(i).getQuantity() });
				}
			} else {

				for (int i = 0; i < stockDAO.stockSelectAll().size(); i++) {
					model1.insertRow(0, new Object[] { stockDAO.stockSelectAll().get(i).getName(),
							stockDAO.stockSelectAll().get(i).getQuantity() });
				}
			}
		}

	}
}// 클래스 끝
