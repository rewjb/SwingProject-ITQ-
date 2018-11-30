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

public class H_V_Company extends JPanel {
	public H_V_Company() {
		DefaultTableModel model = new DefaultTableModel(0, 4);
		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		Object[] column = { "업체ID.", "업체명", "사업자번호", "연락처" };
		
		JLabel lbId;
		JTextField tfId;
		JLabel lbName;
		JTextField tfName;
		JLabel lbCNum0;
		JTextField tfCNum0;
		JTextField tfCNum1;
		JTextField tfCNum2;
		JLabel lbCNum1;
		JLabel lbCNum2;
		JLabel lbTel0;
		JTextField tfTel0;
		JTextField tfTel1;
		JTextField tfTel2;
		JLabel lbTel1;
		JLabel lbTel2;

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

		lbId = new JLabel("업체ID");
		lbId.setHorizontalAlignment(SwingConstants.CENTER);
		lbId.setBounds(522, 60, 60, 30);
		add(lbId);

		tfId = new JTextField();
		tfId.setBounds(587, 60, 150, 30);
		add(tfId);
		tfId.setColumns(10);

		lbName = new JLabel("업체명");
		lbName.setHorizontalAlignment(SwingConstants.CENTER);
		lbName.setBounds(522, 20, 60, 30);
		add(lbName);

		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(587, 20, 150, 30);
		add(tfName);

		lbCNum0 = new JLabel("사업자번호");
		lbCNum0.setHorizontalAlignment(SwingConstants.CENTER);
		lbCNum0.setBounds(522, 100, 60, 30);
		add(lbCNum0);
		
		tfCNum0 = new JTextField();
		tfCNum0.setColumns(3);
		tfCNum0.setBounds(587, 100, 30, 30);
		add(tfCNum0);
		
		lbCNum1 = new JLabel("-");
		lbCNum1.setHorizontalAlignment(SwingConstants.CENTER);
		lbCNum1.setBounds(617, 100, 10, 30);
		add(lbCNum1);
		
		tfCNum1 = new JTextField();
		tfCNum1.setColumns(4);
		tfCNum1.setBounds(627, 100, 30, 30);
		add(tfCNum1);

		lbCNum2 = new JLabel("-");
		lbCNum2.setHorizontalAlignment(SwingConstants.CENTER);
		lbCNum2.setBounds(657, 100, 10, 30);
		add(lbCNum2);
		
		tfCNum2 = new JTextField();
		tfCNum2.setColumns(4);
		tfCNum2.setBounds(667, 100, 70, 30);
		add(tfCNum2);

		lbTel0 = new JLabel("연락처");
		lbTel0.setHorizontalAlignment(SwingConstants.CENTER);
		lbTel0.setBounds(522, 140, 60, 30);
		add(lbTel0);

		tfTel0 = new JTextField();
		tfTel0.setColumns(3);
		tfTel0.setBounds(587, 140, 40, 30);
		add(tfTel0);

		lbTel1 = new JLabel("-");
		lbTel1.setHorizontalAlignment(SwingConstants.CENTER);
		lbTel1.setBounds(627, 140, 10, 30);
		add(lbTel1);

		tfTel1 = new JTextField();
		tfTel1.setColumns(4);
		tfTel1.setBounds(637, 140, 45, 30);
		add(tfTel1);
		
		lbTel2 = new JLabel("-");
		lbTel2.setHorizontalAlignment(SwingConstants.CENTER);
		lbTel2.setBounds(682, 140, 10, 30);
		add(lbTel2);

		tfTel2 = new JTextField();
		tfTel2.setColumns(4);
		tfTel2.setBounds(692, 140, 45, 30);
		add(tfTel2);

		Object[] row = new Object[4];
		btAdd = new JButton("추가");
		btAdd.setBounds(527, 246, 70, 30);
		add(btAdd);
		btAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				row[0] = tfId.getText();
				row[1] = tfName.getText();
				String cNum = tfCNum0.getText() +"-"+ tfCNum1.getText() +"-"+ tfCNum2.getText();
				row[2] = cNum;
				String tel = tfTel0.getText() +"-"+ tfTel1.getText() +"-"+ tfTel2.getText();
				row[3] = tel;
				
				model.addRow(row);
			}
		});
		//클릭한 테이블의 row인덱스값 읽어오기
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i  = table.getSelectedRow();
				tfId.setText(model.getValueAt(i, 0).toString());
				tfName.setText(model.getValueAt(i, 1).toString());
				String tel = model.getValueAt(i, 2).toString();
				String[] t = tel.split("-");
				tfTel0.setText(t[0]);
				tfTel1.setText(t[1]);
				tfTel2.setText(t[2]);
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
					model.setValueAt(tfName.getText(), i, 1);
					String tel = tfTel0.getText() +"-"+ tfTel1.getText() +"-"+ tfTel2.getText();
					model.setValueAt(tel, i, 2);
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
