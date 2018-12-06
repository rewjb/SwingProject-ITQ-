package server;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ServerFrame extends JFrame implements ActionListener {

	private DefaultTableModel orderListModel = new DefaultTableModel(0, 5);
	private JTable orderListTable = new JTable(orderListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 가맹점의 발주 리스트(확인하지 않은..)를 넣을 Jtable / 오면서 셀을
		// 수정여부 메서드를 무조건 false값으로 리턴

	private JScrollPane orderScroll = new JScrollPane(orderListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 발주내용의 스크롤 기능 객체

	JScrollPane scrollPane = new JScrollPane();
	JButton btnNewButton = new JButton("생성");
	JButton button = new JButton("삭제");

	public ServerFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnNewButton.setBounds(12, 260, 97, 23);
		button.setBounds(135, 260, 97, 23);
		scrollPane.setBounds(12, 10, 220, 240);
		
		btnNewButton.addActionListener(this);
		button.addActionListener(this);

		getContentPane().add(button);
		getContentPane().add(scrollPane);
		getContentPane().add(btnNewButton);

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
			System.out.println("생성");
		} else if (e.getSource() == button) {
			System.out.println("삭제");
		}

	}// actionPerformed:메서드 종료
}// 클래스 종료
