package server;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

public class ServerFrame extends JFrame implements ActionListener {

	public static DefaultTableModel model = new DefaultTableModel(0, 2);
	private JTable table = new JTable(model) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 가맹점의 발주 리스트(확인하지 않은..)를 넣을 Jtable / 오면서 셀을
		// 수정여부 메서드를 무조건 false값으로 리턴

	private JScrollPane scroll = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 발주내용의 스크롤 기능 객체

	private JButton btnNewButton = new JButton("생성");
	private JButton button = new JButton("삭제");
	private JButton button_1 = new JButton("종료");

	public static HashMap<String, ArrayList<PrintWriter>> room = new HashMap<>();
	// <방제목 , 방에 접속한 고객의 printWriter>

	private JDialog plusRoomForm = new JDialog(this, "방 제목 설정", false);
	private final JButton button_2 = new JButton("완료");
	private final JTextField textField = new JTextField();

	Server server= new Server();
	
	
	
	public ServerFrame() {
		
		server.start();

		textField.setColumns(10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		plusRoomForm.setLayout(new BorderLayout());
		plusRoomForm.setSize(100, 80);
		plusRoomForm.setResizable(false);
		plusRoomForm.setVisible(false);

		plusRoomForm.getContentPane().add(button_2, BorderLayout.SOUTH);
		plusRoomForm.getContentPane().add(textField, BorderLayout.CENTER);

		btnNewButton.setBounds(12, 260, 97, 23);
		button.setBounds(135, 260, 97, 23);
		scroll.setBounds(12, 10, 220, 240);
		button_1.setBounds(73, 288, 97, 23);
		button_2.setBounds(10, 60, 97, 23);
		textField.setBounds(10, 20, 97, 23);

		btnNewButton.addActionListener(this);
		button.addActionListener(this);
		button_1.addActionListener(this);
		button_2.addActionListener(this);
		textField.addActionListener(this);

		model.setColumnIdentifiers(new Object[] { "제목", "인원" });

		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		// 발주테이블의 헤더를 얻어서 사이즈 수정 불가 , 발주테이블의 컬럼 이동 금지

		getContentPane().add(button);
		getContentPane().add(btnNewButton);
		getContentPane().add(button_1);
		getContentPane().add(scroll);

		getContentPane().setLayout(null);

		setResizable(false);
		setTitle("관리자");
		setSize(250, 350);
		setVisible(true);
	}// 생성자 종료

	public static void main(String[] args) {
		new ServerFrame();
	}// main:메서드 종료

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNewButton) {
			// 생성 => 제목설정 프레임 생기기
			plusRoomForm.setLocationRelativeTo(this);
			plusRoomForm.setVisible(true);
		} else if (e.getSource() == button) {
			// 삭제 => 테이블에서 선택한 방 삭제
			deleteRoom();
		} else if (e.getSource() == button_1) { // 종료되는 리스너
		} else if (e.getSource() == button_2 || e.getSource() == textField) {
			// 엔터 + 완료 = > 동일 이름의 방이 없으면 생성
			createRoom();
		}
	}// actionPerformed:메서드 종료

	public void createRoom() {
		// 방을 만드는 메서드
		int count = table.getRowCount();
		boolean temp = true;

		for (int i = 0; i < count; i++) {
			if (table.getValueAt(i, 0).equals(textField.getText())) {
				temp = false;
			}
		}
		if (temp) {
			room.put(textField.getText(), new ArrayList<PrintWriter>());
			model.insertRow(0, new Object[] { textField.getText(),0 });
			textField.setText("");
			plusRoomForm.dispose();
		} else {
			JOptionPane.showMessageDialog(this, "이미 존재합니다.");
			textField.setText("");
		} // createRoom() : 메서드 종료

	}// 방을 만드는 메서드

	public void deleteRoom() {
		// 방을 삭제하는 메서드
		int count = table.getSelectedRowCount();
		if (count == 1) {
			room.remove((String) table.getValueAt(table.getSelectedRow(), 0));
			model.removeRow(table.getSelectedRow());
		} else {
			JOptionPane.showMessageDialog(this, "하나만 선택해 주세요.");
		}
	}// deleteRoom() : 메서드 종료


}// 클래스 종료

