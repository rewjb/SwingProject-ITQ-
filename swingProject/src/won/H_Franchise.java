package won;
/*
 * 2018-12-07 
 * wonHn
 * 틀 작업은 다 끝났습니다. 버튼에 기능 들어가있습니다.
 * 아이디 자동생성 및 중복확인 기능, 주소 관련 처리는 아직 들어가지 않았습니다.
 * 
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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

	// 정보입력창에 올라갈 구성요소들
	private JFrame f = new JFrame();

	private JLabel lbId;
	private JTextField tfId;
	private JLabel lbPw;
	private JTextField tfPw;
	private JLabel lbON;
	private JTextField tfON;
	private JLabel lbCNum0;
	private JTextField tfCNum0;
	private JLabel lbCNum1;
	private JTextField tfCNum1;
	private JLabel lbCNum2;
	private JTextField tfCNum2;
	private JLabel lbTel0;
	private JTextField tfTel0;
	private JLabel lbTel1;
	private JTextField tfTel1;
	private JLabel lbTel2;
	private JTextField tfTel2;
	private JLabel lbAddr0;
	private JComboBox<String> cbAddr0;
	private JTextField tfAddr1;
	private JLabel lbAllias;
	private JTextField tfAllias;

	// 그 외
	Object[] row; // 테이블에 올릴 column이름 배열
	H_FranchiseDAO fDAO = new H_FranchiseDAO();
	H_FranchiseDTO fDTO;
	JButton btInAdd; // 팝업했을때 버튼
	JButton btsetEmpty;
	JButton btInModify;
	JButton btsetBefore;
	String addr;

	int i; // 표를 클릭했을때 받아놓는 인덱스 값

	// 생성자 construct
	public H_Franchise() {
		setBounds(0, 0, 770, 368);
		setLayout(null);

		buttonSetting();
		showAll();
		innerFrameSetting();
		mouseAction();

		setVisible(false);
		f.setVisible(false);
	} // end constructor

	// 표에 관련된 설정사항
	private void tableSetting() {
		model = new DefaultTableModel(0, 8) {@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}};
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

	// 정보입력창 설정
	private void innerFrameSetting() {
		f = new JFrame();
		f.setBounds(0, 0, 310, 420);
		f.getContentPane().setLayout(null);

		lbId = new JLabel("가맹점ID");
		lbId.setHorizontalAlignment(SwingConstants.CENTER);
		lbId.setBounds(10, 20, 60, 30);
		f.getContentPane().add(lbId);

		tfId = new JTextField();
		tfId.setBounds(75, 20, 150, 30);
		f.getContentPane().add(tfId);
		tfId.setColumns(10);

		lbPw = new JLabel("비밀번호");
		lbPw.setHorizontalAlignment(SwingConstants.CENTER);
		lbPw.setBounds(10, 60, 60, 30);
		f.getContentPane().add(lbPw);

		tfPw = new JTextField();
		tfPw.setColumns(10);
		tfPw.setBounds(75, 60, 150, 30);
		f.getContentPane().add(tfPw);

		lbON = new JLabel("점주명");
		lbON.setHorizontalAlignment(SwingConstants.CENTER);
		lbON.setBounds(10, 100, 60, 30);
		f.getContentPane().add(lbON);

		tfON = new JTextField();
		tfON.setColumns(10);
		tfON.setBounds(75, 100, 95, 30);
		f.getContentPane().add(tfON);

		lbTel0 = new JLabel("연락처");
		lbTel0.setHorizontalAlignment(SwingConstants.CENTER);
		lbTel0.setBounds(10, 140, 60, 30);
		f.getContentPane().add(lbTel0);

		tfTel0 = new JTextField();
		tfTel0.setColumns(3);
		tfTel0.setBounds(75, 140, 40, 30);
		f.getContentPane().add(tfTel0);

		lbTel1 = new JLabel("-");
		lbTel1.setHorizontalAlignment(SwingConstants.CENTER);
		lbTel1.setBounds(115, 140, 10, 30);
		f.getContentPane().add(lbTel1);

		tfTel1 = new JTextField();
		tfTel1.setColumns(4);
		tfTel1.setBounds(125, 140, 45, 30);
		f.getContentPane().add(tfTel1);

		lbTel2 = new JLabel("-");
		lbTel2.setHorizontalAlignment(SwingConstants.CENTER);
		lbTel2.setBounds(170, 140, 10, 30);
		f.getContentPane().add(lbTel2);

		tfTel2 = new JTextField();
		tfTel2.setColumns(4);
		tfTel2.setBounds(180, 140, 45, 30);
		f.getContentPane().add(tfTel2);

		lbCNum0 = new JLabel("사업자번호");
		lbCNum0.setHorizontalAlignment(SwingConstants.CENTER);
		lbCNum0.setBounds(10, 180, 60, 30);
		f.getContentPane().add(lbCNum0);

		tfCNum0 = new JTextField();
		tfCNum0.setColumns(3);
		tfCNum0.setBounds(75, 180, 30, 30);
		f.getContentPane().add(tfCNum0);

		lbCNum1 = new JLabel("-");
		lbCNum1.setHorizontalAlignment(SwingConstants.CENTER);
		lbCNum1.setBounds(105, 180, 10, 30);
		f.getContentPane().add(lbCNum1);

		tfCNum1 = new JTextField();
		tfCNum1.setColumns(4);
		tfCNum1.setBounds(115, 180, 30, 30);
		f.getContentPane().add(tfCNum1);

		lbCNum2 = new JLabel("-");
		lbCNum2.setHorizontalAlignment(SwingConstants.CENTER);
		lbCNum2.setBounds(145, 180, 10, 30);
		f.getContentPane().add(lbCNum2);

		tfCNum2 = new JTextField();
		tfCNum2.setColumns(4);
		tfCNum2.setBounds(155, 180, 70, 30);
		f.getContentPane().add(tfCNum2);

		lbAddr0 = new JLabel("매장주소");
		lbAddr0.setHorizontalAlignment(SwingConstants.CENTER);
		lbAddr0.setBounds(10, 220, 60, 30);
		f.getContentPane().add(lbAddr0);

		comboAddr();
		cbAddr0.setEnabled(true);
		
		tfAddr1 = new JTextField();
		tfAddr1.setColumns(10);
		tfAddr1.setBounds(75, 260, 197, 30);
		f.getContentPane().add(tfAddr1);

		lbAllias = new JLabel("별칭");
		lbAllias.setHorizontalAlignment(SwingConstants.CENTER);
		lbAllias.setBounds(10, 300, 60, 30);
		f.getContentPane().add(lbAllias);

		tfAllias = new JTextField();
		tfAllias.setColumns(10);
		tfAllias.setBounds(75, 300, 150, 30);
		f.getContentPane().add(tfAllias);

		btInAdd = new JButton("추가");
		btInAdd.setBounds(30, 340, 115, 31);
		f.getContentPane().add(btInAdd);
		btInAdd.addActionListener(this);

		btInModify = new JButton("수정");
		btInModify.setBounds(30, 340, 115, 31);
		f.getContentPane().add(btInModify);
		btInModify.addActionListener(this);

		btsetEmpty = new JButton("초기화");
		btsetEmpty.setBounds(157, 340, 115, 31);
		f.getContentPane().add(btsetEmpty);
		btsetEmpty.addActionListener(this);
		
		btsetBefore = new JButton("초기화");
		btsetBefore.setBounds(157, 340, 115, 31);
		f.getContentPane().add(btsetBefore);
		btsetBefore.addActionListener(this);

		f.setVisible(false);
	}
	
	//콤보박스 설정
	private void comboAddr() {
			String[] city = {"서울","광주", "대구", "부산", "울산", "인천", 
					"대전","경기","강원","충청","경상","전라","제주","그외"};
			addr = "서울";
			cbAddr0  = new JComboBox(city);
			cbAddr0.setBounds(75, 220, 197, 30);
			f.getContentPane().add(cbAddr0);
	}

	// 마우스 액션에 관한 메서드
	private void mouseAction() {

		// 클릭한 테이블의 row인덱스값 읽어오기
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				i = table.getSelectedRow();
			}
		});
	}// end mouseAction()

	// 버튼 액션에 관한 메서드
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btAdd) { // add setting
			f.setVisible(true);
			btInAdd.setVisible(true);
			btsetEmpty.setVisible(true);
			btInModify.setVisible(false);
			btsetBefore.setVisible(false);

			setEmpty();
		}
		if (e.getSource() == btModify) { // modify setting
			f.setVisible(true);
			btInAdd.setVisible(false);
			btsetEmpty.setVisible(false);
			btInModify.setVisible(true);
			btsetBefore.setVisible(true);

			setBefore();
		}
		if (e.getSource() == btDelete) { // delete, delete
			String id = model.getValueAt(i, 0).toString();
			int rs = fDAO.deleteFranchiseInfo(id);
			if (rs == 0) {
				System.out.println("H_Franchise delete실패");
			} else {
				System.out.println("H_Franchise delete성공");
			}
			showAll();
		}
		if (e.getSource() == btInAdd) { // add, insert
			fDTO = new H_FranchiseDTO();
			fDTO.setId(tfId.getText());
			fDTO.setPw(tfPw.getText());
			fDTO.setOwnername(tfON.getText());
			String tel = tfTel0.getText() + "-" + tfTel1.getText() + "-" + tfTel2.getText();
			fDTO.setTel(tel);
			String cNum = tfCNum0.getText() + "-" + tfCNum1.getText() + "-" + tfCNum2.getText();
			fDTO.setComnum(cNum);
			fDTO.setAddr((String)cbAddr0.getSelectedItem()+"-"+tfAddr1.getText());
			fDTO.setAlias(tfAllias.getText());

			int rs = fDAO.insertFranchiseInfo(fDTO);

			if (rs == 0) {
				System.out.println("H_Franchise insert실패");
			} else {
				System.out.println("H_Franchise insert성공");
			}
			showAll();
		}
		if (e.getSource() == btInModify) { // modify, update
			fDTO = new H_FranchiseDTO();
			fDTO.setId(tfId.getText());
			fDTO.setOwnername(tfON.getText());
			String tel = tfTel0.getText() + "-" + tfTel1.getText() + "-" + tfTel2.getText();
			fDTO.setTel(tel);

			int rs = fDAO.updateFranchiseInfo(fDTO);

			if (rs == 0) {
				System.out.println("H_Franchise update실패");
			} else {
				System.out.println("H_Franchise update성공");
			}
			showAll();

		}
		if (e.getSource() == btsetEmpty) { // 비워놓기
			setEmpty();
		}
		if (e.getSource() == btsetBefore) { // 초기화
			setBefore();
		}
	}

	// 입력 창을 텅 비워주는 메서드
	private void setEmpty() {
		tfId.setText("");
		tfPw.setText("");
		tfON.setText("");
		tfTel0.setText("");
		tfTel1.setText("");
		tfTel2.setText("");
		tfCNum0.setText("");
		tfCNum1.setText("");
		tfCNum2.setText("");
		tfAddr1.setText("");
		tfAllias.setText("");

		tfId.setEditable(true); // 수정 가능하게
		tfPw.setEditable(true);
		tfCNum0.setEditable(true);
		tfCNum1.setEditable(true);
		tfCNum2.setEditable(true);
		cbAddr0.setEnabled(true);
		tfAddr1.setEditable(true);
		tfAllias.setEditable(true);
	}

	// 수정하기 전 모습으로 돌려주는 메서드
	private void setBefore() {
		tfId.setText(model.getValueAt(i, 0).toString());
		tfId.setEditable(false); // 수정 불가능하게
		tfPw.setText(model.getValueAt(i, 1).toString());
		tfPw.setEditable(false);
		tfON.setText(model.getValueAt(i, 2).toString());
		String[] tel = (model.getValueAt(i, 3).toString()).split("-");
		tfTel0.setText(tel[0]);
		tfTel1.setText(tel[1]);
		tfTel2.setText(tel[2]);
		String[] cNum = (model.getValueAt(i, 4).toString()).split("-");
		tfCNum0.setText(cNum[0]);
		tfCNum1.setText(cNum[1]);
		tfCNum2.setText(cNum[2]);
		tfCNum0.setEditable(false);
		tfCNum1.setEditable(false);
		tfCNum2.setEditable(false);
		String[] addr = (model.getValueAt(i, 5).toString()).split("-");
		cbAddr0.setSelectedItem(addr[0]);
		cbAddr0.setEnabled(false);
		tfAddr1.setText(addr[1]);
		tfAddr1.setEditable(false);
		tfAllias.setText(model.getValueAt(i, 7).toString());
		tfAllias.setEditable(false);
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
