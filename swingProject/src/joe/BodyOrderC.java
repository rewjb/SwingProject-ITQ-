package joe;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import inter.BBQBody;
import inter.BodyOrder;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import DTO_DAO.B_OrderDAO;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class BodyOrderC extends JPanel implements BodyOrder, ActionListener {

	String[] list = { "닭", "음료", "무" };
	String a = "1";

	private JLabel j;
	private JLabel j1;
	private DefaultTableModel model = new DefaultTableModel(16, 2);
	private DefaultTableModel model1 = new DefaultTableModel(16, 2);
	private DefaultTableModel model2 = new DefaultTableModel(16, 3);

	// 리스트를 넣을 Jtable
	private JTable listTable = new JTable(model);
	private JTable listTable1 = new JTable(model1);
	private JTable listTable2 = new JTable(model2);

	private JButton bt;
	private JButton bt1;
	private JButton bt2;
	private JButton bt3;
	private JButton bt4;
	

	private JComboBox jcom;
	private JTextField jf;

	private JScrollPane scroll = new JScrollPane(listTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane scroll1 = new JScrollPane(listTable1, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane scroll2 = new JScrollPane(listTable2, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	B_OrderDAO b = B_OrderDAO.getInstance();

	// Jtable의 스크롤 기능 객체 w
	// private DefaultTableCellRenderer celAlignCenter = new
	// DefaultTableCellRenderer();
	// Jtable의 가운데 정렬 객체

	public BodyOrderC() {

		setLayout(null);
		setSize(781, 399);

		j = new JLabel("식자재 목록");
		j.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		j.setHorizontalAlignment(SwingConstants.CENTER);
		j1 = new JLabel("수량");
		j1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		j1.setHorizontalAlignment(SwingConstants.CENTER);
		j.setBounds(0, 10, 80, 38);
		j1.setBounds(151, 19, 65, 20);

		scroll.setBounds(23, 58, 206, 275);
		scroll1.setBounds(552, 58, 229, 275);
		scroll2.setBounds(263, 58, 261, 275);
		listTable.getTableHeader().setResizingAllowed(false);
		listTable.getTableHeader().setReorderingAllowed(false);

		model.setColumnIdentifiers(new Object[] { "식자재", "수량" });
		model1.setColumnIdentifiers(new Object[] { "식자재", "수량" });
		model2.setColumnIdentifiers(new Object[] { "식자재", "수량", "발주일" });

		add(scroll2);
		add(scroll1);
		add(scroll);
		add(j1);
		add(j, BorderLayout.WEST);

		jcom = new JComboBox(list);
		jcom.setBounds(78, 18, 80, 21);
		add(jcom);

		jf = new JTextField();
		jf.setBounds(208, 18, 80, 21);
		add(jf);

		JLabel lblNewLabel = new JLabel("\uC7AC\uACE0 \uBAA9\uB85D");
		lblNewLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 14));
		lblNewLabel.setBounds(648, 18, 57, 20);
		add(lblNewLabel);

		bt = new JButton("\uC120\uD0DD");
		bt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		bt.setBounds(313, 18, 74, 23);
		add(bt);

		bt1 = new JButton("\uBC1C\uC8FC");
		bt1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		bt1.setBounds(76, 340, 97, 23);
		add(bt1);

		bt2 = new JButton("\uC0AD\uC81C");
		bt2.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		bt2.setBounds(399, 18, 74, 23);
		add(bt2);

		bt3 = new JButton("\uBC1C\uC8FC \uBAA9\uB85D");
		bt3.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		bt3.setBounds(273, 340, 106, 23);
		add(bt3);
		
		bt4 = new JButton("\uBAA9\uB85D \uC9C0\uC6B0\uAE30");
		bt4.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		bt4.setBounds(395, 340, 113, 23);
		add(bt4);
		
		

		bt.addActionListener(this);
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		bt3.addActionListener(this);
		bt4.addActionListener(this);

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
		if (e.getSource() == bt) {
			model.insertRow(0, new Object[] { (String) jcom.getSelectedItem(), jf.getText() });
		} else if (e.getSource() == bt2) {
			model.removeRow(0);
		} else if (e.getSource() == bt1) {

			int i = 0;

			while (true) {
				if ((String) model.getValueAt(i, 1) == null) {
					break;
				}
				String test2 = (String) model.getValueAt(i, 0);
				int test3 = Integer.parseInt((String) model.getValueAt(i, 1));
				b.orderInsert(a, test2, test3);
				model.removeRow(0);
			}

		} else if (e.getSource() == bt3) {

			for (int i = 0; i < b.selectAll().size(); i++) {
				model2.insertRow(i,new Object[] { b.selectAll().get(i).getName(), b.selectAll().get(i).getQuantity(),b.selectAll().get(i).getDate() });
			}

		}else if (e.getSource() == bt4) {
			for (int i = 0; i < b.selectAll().size(); i++) {
				model2.removeRow(0);
			}
		}
	}
}
