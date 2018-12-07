package won;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import DTO_DAO.*;

public class H_V_Product extends JPanel implements ActionListener {
	// 표에 관련된 부분
	private DefaultTableModel model;
	private JTable table = new JTable(model);
	private JScrollPane scrollPane = new JScrollPane(table);
	private Object[] column = { "No.", "업체ID", "이름", "가격" };

	// 리벨과 텍스트필드 : 사용자가 입력하고 수정하는 부분
	private JLabel lbNum;
	private JTextField tfNum;
	private JLabel lbId;
	private JTextField tfId;
	private JLabel lbName;
	private JTextField tfName;
	private JLabel lbMoney;
	private JTextField tfMoney;
//	private JLabel lbPer;
//	private JTextField tfPer;
//	private JLabel lbPerM;
//	private JTextField tfPerM;

	// 버튼
	private JButton btAdd;
	private JButton btModify;
	private JButton btDelete;

	// 그외
	Object[] row;
	H_VenderpDAO pDAO = new H_VenderpDAO(); // DAO
	H_VenderpDTO pDTO; // DTO
	H_V_P_worker w = new H_V_P_worker(); // 기능을 넣어놓는 클래스

	// 생성자 constructor
	public H_V_Product() {
		labelSetting();
		buttonSetting();
		showAll();
		mouseAction();
	}

	// 표에 관련된 설정사항
	private void tableSetting() {
		model = new DefaultTableModel(0, 4) {@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}};
		model.setColumnIdentifiers(column);
		table.setModel(model);
		add(scrollPane);
		scrollPane.setBounds(15, 10, 500, 280);
		table.setBackground(Color.LIGHT_GRAY);
		table.setForeground(Color.BLACK);
		table.setRowHeight(20);
	}

	// refresh 표에 전체출력해주는 메서드
	private void showAll() {
		tableSetting();
		ArrayList<H_VenderpDTO> list = pDAO.selectALLVenderpInfo();
		for (int i = 0; i < list.size(); i++) {
			pDTO = list.get(i);
			row = new Object[4];
			row[0] = pDTO.getId();
			row[1] = pDTO.getNum();
			row[2] = pDTO.getName();
			row[3] = pDTO.getMoney();

			model.addRow(row);
		}
		tfId.setText("");
		tfId.setEditable(true);
		tfNum.setText("자동생성");
		tfNum.setEditable(false);
		tfName.setText("");
		tfName.setEditable(true);
		tfMoney.setText("");
	}

	// 라벨 및 텍스트필드 설정사항
	private void labelSetting() {
		lbId = new JLabel("업체ID");
		lbId.setHorizontalAlignment(SwingConstants.CENTER);
		lbId.setBounds(522, 20, 60, 30);
		add(lbId);

		tfId = new JTextField();
		tfId.setText("combo Box");
		tfId.setBounds(587, 20, 150, 30);
		add(tfId);
		tfId.setColumns(10);

		lbNum = new JLabel("번호");
		lbNum.setHorizontalAlignment(SwingConstants.CENTER);
		lbNum.setBounds(522, 60, 60, 30);
		add(lbNum);

		tfNum = new JTextField();
		tfNum.setColumns(10);
		tfNum.setBounds(587, 60, 150, 30);
		add(tfNum);

		lbName = new JLabel("이름");
		lbName.setHorizontalAlignment(SwingConstants.CENTER);
		lbName.setBounds(522, 100, 60, 30);
		add(lbName);

		tfName = new JTextField();
		tfName.setText("comboBox");
		tfName.setColumns(10);
		tfName.setBounds(587, 100, 150, 30);
		add(tfName);

		lbMoney = new JLabel("매입가");
		lbMoney.setHorizontalAlignment(SwingConstants.CENTER);
		lbMoney.setBounds(522, 140, 60, 30);
		add(lbMoney);

		tfMoney = new JTextField();
		tfMoney.setColumns(10);
		tfMoney.setBounds(587, 140, 150, 30);
		add(tfMoney);

//		lbPer = new JLabel("이윤");
//		lbPer.setHorizontalAlignment(SwingConstants.CENTER);
//		lbPer.setBounds(522, 180, 60, 30);
//		add(lbPer);
//		
//		tfPer = new JTextField();
//		tfPer.setColumns(10);
//		tfPer.setBounds(587, 180, 150, 30);
//		add(tfPer);
//		
//		lbPerM = new JLabel("발주가");
//		lbPerM.setHorizontalAlignment(SwingConstants.CENTER);
//		lbPerM.setBounds(522, 180, 60, 30);
//		add(lbPerM);
//				
//		tfPerM = new JTextField();
//		tfPerM.setColumns(10);
//		tfPerM.setBounds(587, 180, 150, 30);
//		add(tfPerM);
//		tfPerM.setText(""+Integer.parseInt(tfMoney.getText())*Integer.parseInt(tfPer.getText()));
	}

	// 버튼에 관련된 설정사항
	private void buttonSetting() {
		btAdd = new JButton("추가");
		btAdd.setBounds(527, 246, 70, 30);
		add(btAdd);
		btAdd.addActionListener(this);

		btModify = new JButton("수정");
		btModify.setBounds(605, 246, 70, 30);
		add(btModify);
		btModify.addActionListener(this);

		btDelete = new JButton("삭제");
		btDelete.setBounds(683, 246, 70, 30);
		add(btDelete);
		btDelete.addActionListener(this);
	}

	// 마우스 액션에 관한 메서드
	private void mouseAction() {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				tfId.setText(model.getValueAt(i, 0).toString());
				tfId.setEditable(false);
				tfNum.setText(model.getValueAt(i, 1).toString());
				tfNum.setEditable(false);
				tfName.setText(model.getValueAt(i, 2).toString());
				tfName.setEditable(false);
				tfMoney.setText(model.getValueAt(i, 3).toString());
			}
		});
	}

	// 버튼 액션에 관한 메서드
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btAdd) { //add, insert
			pDTO = new H_VenderpDTO();
			pDTO.setId(tfId.getText());
			// num은 자동생성 DAO의 sql에서 null값을 넣어줄 것입니다.
			pDTO.setName(tfName.getText());
			pDTO.setMoney(Integer.parseInt(tfMoney.getText()));

			int rs = pDAO.insertVenderpInfo(pDTO);
			if (rs == 0) {
				System.out.println("H_Venderp insert실패");
			} else {
				System.out.println("H_Venderp insert성공");
			}
			showAll();
		}
		if (e.getSource() == btModify) { //modify, update ==> 가격만 수정 가능
			pDTO = new H_VenderpDTO();
			pDTO.setNum(Integer.parseInt(tfName.getText()));
			pDTO.setMoney(Integer.parseInt(tfMoney.getText()));
			
			int rs = pDAO.updateVenderpInfo(pDTO);
			if (rs == 0) {
				System.out.println("H_Venderp update실패");
			} else {
				System.out.println("H_Venderp update성공");
			}
			showAll();
		}
		if (e.getSource() == btDelete) {
			int num = Integer.parseInt(tfNum.getText());
			int rs = pDAO.deleteVenderpInfo(num);
			if (rs == 0) {
				System.out.println("H_Venderp delete실패");
			} else {
				System.out.println("H_Venderp delete성공");
			}
			showAll();
		}
	}
}
