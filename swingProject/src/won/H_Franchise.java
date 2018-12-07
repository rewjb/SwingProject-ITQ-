package won;

import java.awt.Color;
import java.awt.Component;
/*
 * 2018-11-30 
 * wonHn
 * 틀만 작업 끝난 상태입니다. 버튼에 기능 넣지 않았습니다.
 * 추가 수정은 새로운 창을 띄울 예정입니다.
 * 
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import DTO_DAO.*;
import inter.BBQHead;
import inter.HeadFranchise;

public class H_Franchise extends JPanel implements HeadFranchise, ActionListener {
	// 표에 관련된 부분
	DefaultTableModel model;
	JTable table = new JTable(model);
	JScrollPane scrollPane = new JScrollPane(table);
	Object[] column = { "가맹점ID", "pw", "점주명", "연락처", "사업자번호", "주소1", "주소2", "별칭" };
	// DB에는 주소1에 지역이 2에 나머지 주소가 들어갈 예정입니다.

	// 버튼
	JButton btAdd;
	JButton btModify;
	JButton btDelete;
	
	//새로 올리는 다이얼로그에 올라갈 구성요소들
	private JDialog d;
	private JLabel lbId;
	private JTextField tfId;
	private JLabel lbPw;
	private JTextField tfPw;
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
	private JTextField tfON;
	private JTextField tfAddr0;
	private JTextField tfAddr1;
	private JTextField tfAllias;

	// 그 외
	Object[] row;
	H_FranchiseDAO fDAO = new H_FranchiseDAO();
	H_FranchiseDTO fDTO;

	// 생성자 construct
	public H_Franchise() {
		buttonSetting();
		showAll();
		mouseAction();
		
		setVisible(true);
	} // end constructor

	// 표에 관련된 설정사항
	private void tableSetting() {
		model = new DefaultTableModel(0, 8);
		model.setColumnIdentifiers(column);
		table.setModel(model);
		add(scrollPane);
		scrollPane.setBounds(15, 54, 738, 304);
		table.setBackground(Color.LIGHT_GRAY);
		table.setForeground(Color.BLACK);
		table.setRowHeight(20);
	}// end tableSetting()

	private void showAll() {
		tableSetting();
		ArrayList<H_FranchiseDTO> list = fDAO.selectALLFranchiseInfo();
		for (int i = 0; i < list.size(); i++) {
			fDTO = list.get(i);
			row = new Object[8];
			row[0] = fDTO.getId();
			row[1] = fDTO.getPw();
			row[2] = fDTO.getOwnername();
			row[3] = fDTO.getTel();
			row[4] = fDTO.getComnum();
			row[5] = fDTO.getAddr();
//			row[6] = fDTO.getId();
			row[7] = fDTO.getAlias();

			model.addRow(row);
		}
	}// end showAll

	// 버튼에 관련된 설정사항
	private void buttonSetting() {
		btAdd = new JButton("추가");
		btAdd.setBounds(15, 14, 70, 30);
		add(btAdd);
		btAdd.addActionListener(this);

		btModify = new JButton("수정");
		btModify.setBounds(95, 14, 70, 30);
		add(btModify);
		btModify.addActionListener(this);

		btDelete = new JButton("삭제");
		btDelete.setBounds(175, 14, 70, 30);
		add(btDelete);
		btDelete.addActionListener(this);
	}// end buttonSetting()
	
	private void dialogSetting(){
		d.setTitle("테스트지만 잘되면 걍 올리려고용~~~");
		setSize(310, 420);
		d.setLayout(null);
		
		lbId = new JLabel("가맹점ID");
		lbId.setHorizontalAlignment(SwingConstants.CENTER);
		lbId.setBounds(10, 20, 60, 30);
		d.add(lbId);

		tfId = new JTextField();
		tfId.setBounds(75, 20, 150, 30);
		d.add(tfId);
		tfId.setColumns(10);

		lbPw = new JLabel("비밀번호");
		lbPw.setHorizontalAlignment(SwingConstants.CENTER);
		lbPw.setBounds(10, 60, 60, 30);
		d.add(lbPw);

		tfPw = new JTextField();
		tfPw.setColumns(10);
		tfPw.setBounds(75, 60, 150, 30);
		d.add(tfPw);
		
		JLabel lbON = new JLabel("점주명");
		lbON.setHorizontalAlignment(SwingConstants.CENTER);
		lbON.setBounds(10, 100, 60, 30);
		d.add(lbON);
		
		tfON = new JTextField();
		tfON.setColumns(10);
		tfON.setBounds(75, 100, 95, 30);
		d.add(tfON);
		
		lbTel0 = new JLabel("연락처");
		lbTel0.setHorizontalAlignment(SwingConstants.CENTER);
		lbTel0.setBounds(10, 140, 60, 30);
		d.add(lbTel0);
		
		tfTel0 = new JTextField();
		tfTel0.setColumns(3);
		tfTel0.setBounds(75, 140, 40, 30);
		d.add(tfTel0);
		
		lbTel1 = new JLabel("-");
		lbTel1.setHorizontalAlignment(SwingConstants.CENTER);
		lbTel1.setBounds(115, 140, 10, 30);
		d.add(lbTel1);
		
		tfTel1 = new JTextField();
		tfTel1.setColumns(4);
		tfTel1.setBounds(125, 140, 45, 30);
		d.add(tfTel1);
		
		lbTel2 = new JLabel("-");
		lbTel2.setHorizontalAlignment(SwingConstants.CENTER);
		lbTel2.setBounds(170, 140, 10, 30);
		d.add(lbTel2);
		
		tfTel2 = new JTextField();
		tfTel2.setColumns(4);
		tfTel2.setBounds(180, 140, 45, 30);
		d.add(tfTel2);

		lbCNum0 = new JLabel("사업자번호");
		lbCNum0.setHorizontalAlignment(SwingConstants.CENTER);
		lbCNum0.setBounds(10, 180, 60, 30);
		d.add(lbCNum0);

		tfCNum0 = new JTextField();
		tfCNum0.setColumns(3);
		tfCNum0.setBounds(75, 180, 30, 30);
		d.add(tfCNum0);

		lbCNum1 = new JLabel("-");
		lbCNum1.setHorizontalAlignment(SwingConstants.CENTER);
		lbCNum1.setBounds(105, 180, 10, 30);
		d.add(lbCNum1);

		tfCNum1 = new JTextField();
		tfCNum1.setColumns(4);
		tfCNum1.setBounds(115, 180, 30, 30);
		d.add(tfCNum1);

		lbCNum2 = new JLabel("-");
		lbCNum2.setHorizontalAlignment(SwingConstants.CENTER);
		lbCNum2.setBounds(145, 180, 10, 30);
		d.add(lbCNum2);

		tfCNum2 = new JTextField();
		tfCNum2.setColumns(4);
		tfCNum2.setBounds(155, 180, 70, 30);
		d.add(tfCNum2);
		
		JLabel lbAddr0 = new JLabel("매장주소");
		lbAddr0.setHorizontalAlignment(SwingConstants.CENTER);
		lbAddr0.setBounds(10, 220, 60, 30);
		d.add(lbAddr0);
		
		tfAddr0 = new JTextField();
		tfAddr0.setText("combo box");
		tfAddr0.setColumns(10);
		tfAddr0.setBounds(75, 220, 197, 30);
		d.add(tfAddr0);
		
		tfAddr1 = new JTextField();
		tfAddr1.setColumns(10);
		tfAddr1.setBounds(75, 260, 197, 30);
		d.add(tfAddr1);
		
		JLabel lbAllias = new JLabel("별칭");
		lbAllias.setHorizontalAlignment(SwingConstants.CENTER);
		lbAllias.setBounds(10, 300, 60, 30);
		d.add(lbAllias);
		
		tfAllias = new JTextField();
		tfAllias.setColumns(10);
		tfAllias.setBounds(75, 300, 150, 30);
		d.add(tfAllias);
		
		d.setVisible(false);
	}

	// 마우스 액션에 관한 메서드
	private void mouseAction() {
		// 클릭한 테이블의 row인덱스값 읽어오기
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
//				tfId.setText(model.getValueAt(i, 0).toString());

			}
		});
	}// end mouseAction()

	// 버튼 액션에 관한 메서드
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btAdd) { // add, insert
			dialogSetting();
			d.setVisible(true);
		}
		if (e.getSource() == btModify) { //modify, update
			dialogSetting();
			d.setVisible(true);
		}
		if(e.getSource() == btDelete) {
			d.setVisible(true);
		}
	}

	// BBQHead 인터페이스 구현으로 강제생성된 show / hide메서드
	@Override
	public void show(BBQHead bbqHead) {
		// TODO Auto-generated method stub
		((Component) bbqHead).setVisible(true);
	}

	@Override
	public void hide(BBQHead bbqHead) {
		// TODO Auto-generated method stub
		((Component) bbqHead).setVisible(false);
	}
}
