package won.panel2;
/*2018-11-29
 * 메인 대용
 * 작업 끝나고 각각 클래스로 찢어놓을 예정입니다.
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class HeadVenderJFClass extends JFrame {

	
	JPanel panel1st;
	public HeadVenderJFClass() {

		setTitle("HeadVender JF임시판넬~~~~~ 21081129 15:42");
		setSize(800, 400);
		setLayout(null);

		panel1st = new JPanel();
		getContentPane().add(panel1st);
		panel1st.setBounds(7, 0, 770, 360);
		panel1st.setLayout(null);

		hHeadVenderClass();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	JPanel paneVdC;
	JPanel paneVdP;

	public void hHeadVenderClass() {
		JButton btVdC;
		JButton btVdP;

		JPanel panel = new JPanel();
		panel1st.add(panel);
		panel.setBounds(0, 25, 770, 335);
		panel.setLayout(null);

		btVdC = new JButton("업체");
		panel.add(btVdC);
		btVdC.setBounds(350, 0, 80, 20);
		btVdC.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				paneVdC.setVisible(true);
				paneVdP.setVisible(false);
			}
		});

		btVdP = new JButton("제품");
		btVdP.setBounds(430, 0, 80, 20);
		panel.add(btVdP);
		btVdP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				paneVdC.setVisible(false);
				paneVdP.setVisible(true);
			}
		});

		paneVdC = new JPanel();
		paneVdC.setBounds(0, 20, 770, 315);
		panel.add(paneVdC);
		paneVdC.setLayout(null);

		paneVdP = new JPanel();
		paneVdP.setBounds(0, 20, 770, 315);
		panel.add(paneVdP);
		paneVdP.setLayout(null);

		hHeadVenderProductClass();
		hHeadVenderCompanyClass();
		
		paneVdC.setVisible(false);
		paneVdP.setVisible(true);
		setVisible(true);
	}

	public void hHeadVenderProductClass() {
		
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
		table.setRowHeight(30);

		paneVdP.add(scrollPane);
		scrollPane.setVisible(true);

		lbId = new JLabel("업체ID");
		lbId.setHorizontalAlignment(SwingConstants.CENTER);
		lbId.setBounds(522, 60, 60, 30);
		paneVdP.add(lbId);

		tfId = new JTextField();
		tfId.setText("combo Box");
		tfId.setBounds(587, 60, 150, 30);
		paneVdP.add(tfId);
		tfId.setColumns(10);

		lbNum = new JLabel("번호");
		lbNum.setHorizontalAlignment(SwingConstants.CENTER);
		lbNum.setBounds(522, 20, 60, 30);
		paneVdP.add(lbNum);

		tfNum = new JTextField();
		tfNum.setColumns(10);
		tfNum.setBounds(587, 20, 150, 30);
		paneVdP.add(tfNum);

		lbName = new JLabel("이름");
		lbName.setHorizontalAlignment(SwingConstants.CENTER);
		lbName.setBounds(522, 99, 60, 30);
		paneVdP.add(lbName);

		tfName = new JTextField();
		tfName.setText("comboBox");
		tfName.setColumns(10);
		tfName.setBounds(587, 99, 150, 30);
		paneVdP.add(tfName);

		lbMoney = new JLabel("가격");
		lbMoney.setHorizontalAlignment(SwingConstants.CENTER);
		lbMoney.setBounds(522, 159, 60, 30);
		paneVdP.add(lbMoney);

		tfMoney = new JTextField();
		tfMoney.setColumns(10);
		tfMoney.setBounds(587, 159, 150, 30);
		paneVdP.add(tfMoney);

		Object[] row = new Object[4];
		btAdd = new JButton("추가");
		btAdd.setBounds(527, 246, 70, 30);
		paneVdP.add(btAdd);
		btAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				row[0] = tfId.getText();
				row[1] = tfNum.getText();
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
		paneVdP.add(btUpdate);
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
		btDelete.setBounds(688, 246, 70, 30);
		paneVdP.add(btDelete);
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
	
	public void hHeadVenderCompanyClass() {
		DefaultTableModel model = new DefaultTableModel(0, 3);
		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		Object[] column = { "업체ID.", "업체명", "연락처" };
		
		JLabel lbId;
		JTextField tfId;
		JTextField tfName;
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
		table.setRowHeight(30);

		paneVdC.add(scrollPane);
		scrollPane.setVisible(true);

		lbId = new JLabel("업체ID");
		lbId.setHorizontalAlignment(SwingConstants.CENTER);
		lbId.setBounds(522, 22, 60, 30);
		paneVdC.add(lbId);

		tfId = new JTextField();
		tfId.setBounds(587, 22, 150, 30);
		paneVdC.add(tfId);
		tfId.setColumns(10);

		JLabel lbName = new JLabel("업체명");
		lbName.setHorizontalAlignment(SwingConstants.CENTER);
		lbName.setBounds(522, 68, 60, 30);
		paneVdC.add(lbName);

		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(587, 68, 150, 30);
		paneVdC.add(tfName);

		JLabel lbTel0 = new JLabel("연락처");
		lbTel0.setHorizontalAlignment(SwingConstants.CENTER);
		lbTel0.setBounds(522, 114, 60, 30);
		paneVdC.add(lbTel0);

		lbTel1 = new JLabel("-");
		lbTel1.setHorizontalAlignment(SwingConstants.CENTER);
		lbTel1.setBounds(628, 114, 10, 30);
		paneVdC.add(lbTel1);

		lbTel2 = new JLabel("-");
		lbTel2.setHorizontalAlignment(SwingConstants.CENTER);
		lbTel2.setBounds(683, 114, 10, 30);
		paneVdC.add(lbTel2);

		tfTel0 = new JTextField();
		tfTel0.setColumns(3);
		tfTel0.setBounds(587, 114, 40, 30);
		paneVdC.add(tfTel0);

		tfTel1 = new JTextField();
		tfTel1.setColumns(4);
		tfTel1.setBounds(640, 114, 42, 30);
		paneVdC.add(tfTel1);

		tfTel2 = new JTextField();
		tfTel2.setColumns(4);
		tfTel2.setBounds(695, 114, 42, 30);
		paneVdC.add(tfTel2);

		Object[] row = new Object[3];
		btAdd = new JButton("추가");
		btAdd.setBounds(637, 190, 100, 30);
		paneVdC.add(btAdd);
		btAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				row[0] = tfId.getText();
				row[1] = tfName.getText();
				String tel = tfTel0.getText() +"-"+ tfTel1.getText() +"-"+ tfTel2.getText();
				row[2] = tel;
				
				model.addRow(row);
			}
		});

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
		btUpdate.setBounds(637, 230, 100, 30);
		paneVdC.add(btUpdate);
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
		btDelete.setBounds(637, 270, 100, 30);
		paneVdC.add(btDelete);
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

	public static void main(String[] args) {
		HeadVenderJFClass hvjf = new HeadVenderJFClass(); // panel2
	}

}
