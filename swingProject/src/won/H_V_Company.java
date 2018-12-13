package won;
/*
 * 2018-12-05 업체에 관련된 Swing페에지 입니다.
 * 추가 수정 제거 버튼과 클릭해서 값을 받아오는 기능은 구현해놨습니다.
 * 아이디 자동생성이나 중복확인은 H_V_C_worker에 있습니다.
 * DB생성 후 테스트해봐야 합니다.
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import DTO_DAO.*;

public class H_V_Company extends JPanel implements ActionListener {
	// 표에 관련된 부분
	private DefaultTableModel model;
	private JTable table = new JTable(model);
	private JScrollPane scrollPane = new JScrollPane(table);
	private Object[] column = { "업체ID.", "업체명", "연락처", "사업자번호" };

	// 라벨과 텍스트필드 : 사용자가 입력하고 수정하는 부분
	private JLabel lbId;
	private JTextField tfId;
	private JLabel lbName;
	private JTextField tfName;
	private JLabel lbCNum0;
	private JTextField tfCNum0;
	private JTextField tfCNum1;
	private JTextField tfCNum2;
	private JLabel lbCNum1;
	private JLabel lbCNum2;
	private JLabel lbTel0;
	private JTextField tfTel0;
	private JTextField tfTel1;
	private JTextField tfTel2;
	private JLabel lbTel1;
	private JLabel lbTel2;

	// 버튼
	private JButton btAdd;
	private JButton btModify;
	private JButton btDelete;

	// 그외
	Object[] row;
	H_VenderDAO vDAO = new H_VenderDAO();	//DAO
	H_VenderDTO vDTO;						// DTO
	H_V_C_worker w = new H_V_C_worker(); //기능을 넣어놓는 클래스
	String id;	//전체 테이블에서 마지막 id를 받아놓는 변수

	// 생성자 construct
	public H_V_Company() {
		labelSetting();
		buttonSetting();
		showAll();
		mouseAction();
	}

	// 표에 관련된 설정사항
	private void tableSetting() {
		model = new DefaultTableModel(0, 4) {
			@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}};
		model.setColumnIdentifiers(column);
		table.setModel(model);
		add(scrollPane);
		scrollPane.setBounds(15, 10, 500, 300);
		table.setBackground(Color.LIGHT_GRAY);
		table.setForeground(Color.BLACK);
		table.setRowHeight(20);
	}

	// refresh 표에 전체 출력해주는 메서드
	private void showAll() {
		tableSetting();
		ArrayList<H_VenderDTO> list = vDAO.selectALLVenderInfo();
		for (int i = 0; i < list.size(); i++) {
			vDTO = list.get(i);
			row = new Object[4];
			row[0] = vDTO.getId();
			id = w.findLastId(id, vDTO.getId());
			row[1] = vDTO.getName();
			row[2] = vDTO.getTel();
			row[3] = vDTO.getComNum();

			model.addRow(row);
		}
		tfId.setText("자동생성");
		tfId.setEditable(false);
		tfName.setText("");
		tfName.setEditable(true);
		tfTel0.setText("");
		tfTel1.setText("");
		tfTel2.setText("");
		tfTel0.setEditable(true);
		tfTel1.setEditable(true);
		tfTel2.setEditable(true);
		tfCNum0.setText("");
		tfCNum1.setText("");
		tfCNum2.setText("");
		tfCNum0.setEditable(true);
		tfCNum1.setEditable(true);
		tfCNum2.setEditable(true);
	}

	// 라벨 및 텍스트필드 설정사항
	private void labelSetting() {
		lbId = new JLabel("업체ID");
		lbId.setHorizontalAlignment(SwingConstants.CENTER);
		lbId.setBounds(522, 20, 60, 30);
		add(lbId);

		tfId = new JTextField();
		tfId.setBounds(587, 20, 150, 30);
		add(tfId);
		tfId.setColumns(10);

		lbName = new JLabel("업체명");
		lbName.setHorizontalAlignment(SwingConstants.CENTER);
		lbName.setBounds(522, 60, 60, 30);
		add(lbName);

		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(587, 60, 150, 30);
		add(tfName);
		
		lbTel0 = new JLabel("연락처");
		lbTel0.setHorizontalAlignment(SwingConstants.CENTER);
		lbTel0.setBounds(522, 100, 60, 30);
		add(lbTel0);
		
		tfTel0 = new JTextField();
		tfTel0.setColumns(3);
		tfTel0.setBounds(587, 100, 40, 30);
		add(tfTel0);
		
		lbTel1 = new JLabel("-");
		lbTel1.setHorizontalAlignment(SwingConstants.CENTER);
		lbTel1.setBounds(627, 100, 10, 30);
		add(lbTel1);
		
		tfTel1 = new JTextField();
		tfTel1.setColumns(4);
		tfTel1.setBounds(637, 100, 45, 30);
		add(tfTel1);
		
		lbTel2 = new JLabel("-");
		lbTel2.setHorizontalAlignment(SwingConstants.CENTER);
		lbTel2.setBounds(682, 100, 10, 30);
		add(lbTel2);
		
		tfTel2 = new JTextField();
		tfTel2.setColumns(4);
		tfTel2.setBounds(692, 100, 45, 30);
		add(tfTel2);

		lbCNum0 = new JLabel("사업자번호");
		lbCNum0.setHorizontalAlignment(SwingConstants.CENTER);
		lbCNum0.setBounds(522, 140, 60, 30);
		add(lbCNum0);

		tfCNum0 = new JTextField();
		tfCNum0.setColumns(3);
		tfCNum0.setBounds(587, 140, 30, 30);
		add(tfCNum0);

		lbCNum1 = new JLabel("-");
		lbCNum1.setHorizontalAlignment(SwingConstants.CENTER);
		lbCNum1.setBounds(617, 140, 10, 30);
		add(lbCNum1);

		tfCNum1 = new JTextField();
		tfCNum1.setColumns(4);
		tfCNum1.setBounds(627, 140, 30, 30);
		add(tfCNum1);

		lbCNum2 = new JLabel("-");
		lbCNum2.setHorizontalAlignment(SwingConstants.CENTER);
		lbCNum2.setBounds(657, 140, 10, 30);
		add(lbCNum2);

		tfCNum2 = new JTextField();
		tfCNum2.setColumns(4);
		tfCNum2.setBounds(667, 140, 70, 30);
		add(tfCNum2);

	}

	// 버튼에 관련된 설정사항
	private void buttonSetting() {
		btAdd = new JButton("추가");
		btAdd.setBounds(527, 275, 70, 30);
		add(btAdd);
		btAdd.addActionListener(this);

		btModify = new JButton("수정");
		btModify.setBounds(605, 275, 70, 30);
		add(btModify);
		btModify.addActionListener(this);

		btDelete = new JButton("삭제");
		btDelete.setBounds(683, 275, 70, 30);
		add(btDelete);
		btDelete.addActionListener(this);
	}

	// 마우스 액션에 관한 메서드
	private void mouseAction() {
		// 클릭한 테이블의 row인덱스값 읽어오기
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//값을 가져와도 수정은 전화번호만 할꺼라 나머지는 수정 못하게 묶어놓았습니다.
				int i = table.getSelectedRow();
				tfId.setText(model.getValueAt(i, 0).toString());
				tfId.setEditable(false);
				tfName.setText(model.getValueAt(i, 1).toString());
				tfName.setEditable(false);
				String tel = model.getValueAt(i, 2).toString();
				String[] t = tel.split("-");
				tfTel0.setText(t[0]);
				tfTel1.setText(t[1]);
				tfTel2.setText(t[2]);
				String cNum = model.getValueAt(i, 3).toString();
				String[] c = cNum.split("-");
				tfCNum0.setText(c[0]);
				tfCNum1.setText(c[1]);
				tfCNum2.setText(c[2]);
				tfCNum0.setEditable(false);
				tfCNum1.setEditable(false);
				tfCNum2.setEditable(false);
			}
		});
	}

	// 버튼 액션에 관한 메서드
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btAdd) { //add, insert
//			if(w.checkInput(tfName.getText())) {
				vDTO = new H_VenderDTO();	
				//id는 자동생성
				String setId = w.makeId(id);	//자동생성 메서드
				vDTO.setId(setId);
				vDTO.setName(tfName.getText());
				String tel = tfTel0.getText()+"-"+tfTel1.getText()+"-"+tfTel2.getText();
				vDTO.setTel(tel);
				String CNum = tfCNum0.getText()+"-"+tfCNum1.getText()+"-"+tfCNum2.getText();
				vDTO.setComNum(CNum);
				
				int rs = vDAO.insertVenderInfo(vDTO);
				if( rs ==0 ) {
					System.out.println("H_Vender insert실패");
				}else {
					System.out.println("H_Vender insert성공");
				}
				showAll();
//			}else {
//				JOptionPane.showMessageDialog(null, "업체명 중복!");
//			}
		}
		if (e.getSource() == btModify) { //modify, update
			vDTO = new H_VenderDTO();
			vDTO.setId(tfId.getText());
			String tel = tfTel0.getText()+"-"+tfTel1.getText()+"-"+tfTel2.getText();
			vDTO.setTel(tel);
			
			int rs = vDAO.updateVenderInfo(vDTO);
			if (rs == 0) {
				System.out.println("H_Vender update실패");
			} else {
				System.out.println("H_Vender update성공");
			}
			showAll();
		}
		if (e.getSource() == btDelete) { //delete, delete
			String id = tfId.getText();
			int rs = vDAO.deleteVenderInfo(id);
			if (rs == 0) {
				System.out.println("H_Vender delete실패");
			} else {
				System.out.println("H_Vender delete성공");
			}
			showAll();
		}
	}
}
