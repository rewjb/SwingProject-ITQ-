package won;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class H_V_Product extends JPanel {
	public H_V_Product() {
		
		DefaultTableModel model = new DefaultTableModel(0, 4);
		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		Object[] column = { "No.", "업체ID", "이름", "가격" };

		JLabel lbNum;
		JTextField tfNum;
		JLabel lbId;
		JTextField tfId;
		JLabel lbName;
		JTextField tfName;
		JLabel lbMoney;
		JTextField tfMoney;

		JButton btAdd;
		JButton btUpdate;
		JButton btDelete;
		
		model.setColumnIdentifiers(column);
		table.setModel(model);
		scrollPane.setBounds(15, 10, 500, 280);

		table.setBackground(Color.LIGHT_GRAY);
		table.setForeground(Color.BLACK);
		table.setRowHeight(20);

		add(scrollPane);
		scrollPane.setVisible(true);

		lbNum = new JLabel("번호");
		lbNum.setHorizontalAlignment(SwingConstants.CENTER);
		lbNum.setBounds(522, 20, 60, 30);
		add(lbNum);
		
		tfNum = new JTextField();
		tfNum.setColumns(10);
		tfNum.setBounds(587, 20, 150, 30);
		add(tfNum);

		lbId = new JLabel("업체ID");
		lbId.setHorizontalAlignment(SwingConstants.CENTER);
		lbId.setBounds(522, 60, 60, 30);
		add(lbId);

		tfId = new JTextField();
		tfId.setText("combo Box");
		tfId.setBounds(587, 60, 150, 30);
		add(tfId);
		tfId.setColumns(10);


		lbName = new JLabel("이름");
		lbName.setHorizontalAlignment(SwingConstants.CENTER);
		lbName.setBounds(522, 100, 60, 30);
		add(lbName);

		tfName = new JTextField();
		tfName.setText("comboBox");
		tfName.setColumns(10);
		tfName.setBounds(587, 100, 150, 30);
		add(tfName);

		lbMoney = new JLabel("가격");
		lbMoney.setHorizontalAlignment(SwingConstants.CENTER);
		lbMoney.setBounds(522, 140, 60, 30);
		add(lbMoney);

		tfMoney = new JTextField();
		tfMoney.setColumns(10);
		tfMoney.setBounds(587, 140, 150, 30);
		add(tfMoney);

		Object[] row = new Object[4];
		btAdd = new JButton("추가");
		btAdd.setBounds(527, 246, 70, 30);
		add(btAdd);
		btAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				row[0] = tfNum.getText();
				row[1] = tfId.getText();
				row[2] = tfName.getText();
				row[3] = tfMoney.getText();
				
				model.addRow(row);
			}
		});
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i  = table.getSelectedRow();
				tfId.setText(model.getValueAt(i, 0).toString());
				tfNum.setText(model.getValueAt(i, 1).toString());
				tfName.setText(model.getValueAt(i, 2).toString());
				tfMoney.setText(model.getValueAt(i, 3).toString());
			}
		});

		btUpdate = new JButton("수정");
		btUpdate.setBounds(605, 246, 70, 30);
		add(btUpdate);
		btUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int i = table.getSelectedRow();
				if(i>=0) {
					model.setValueAt(tfId.getText(), i, 0);
					model.setValueAt(tfNum.getText(), i, 1);
					model.setValueAt(tfName.getText(), i, 2);
					model.setValueAt(tfMoney.getText(), i, 3);
				}else {
					System.out.println("Update Error");
				}
			}
		});

		btDelete = new JButton("삭제");
		btDelete.setBounds(683, 246, 70, 30);
		add(btDelete);
		btDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int i = table.getSelectedRow();
				if(i>=0){
					model.removeRow(i);
				}else {
					System.out.println("Delete Error");
				}
			}
		});

	}
}
