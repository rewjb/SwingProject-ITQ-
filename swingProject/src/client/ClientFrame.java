package client;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JTextField;

public class ClientFrame extends JFrame implements ActionListener {

	private DefaultTableModel model = new DefaultTableModel(0, 2);
	private JTable table = new JTable(model) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 가맹점의 발주 리스트(확인하지 않은..)를 넣을 Jtable / 오면서 셀을
		// 수정여부 메서드를 무조건 false값으로 리턴

	private JScrollPane scroll = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 발주내용의 스크롤 기능 객체

	private JButton btnNewButton = new JButton("입장");
	private JButton button_1 = new JButton("종료");

	HashMap<String, ArrayList<PrintWriter>> room = new HashMap<>();;

	private final JTextField textField = new JTextField();

	ChatClient ChatClient = new ChatClient();

	String name = "유주빈";

	public ClientFrame() {

		ChatClient.name = name;
		ChatClient.start();

		textField.setColumns(10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnNewButton.setBounds(12, 288, 97, 23);
		scroll.setBounds(12, 10, 220, 268);
		button_1.setBounds(135, 288, 97, 23);
		textField.setBounds(10, 20, 97, 23);

		btnNewButton.addActionListener(this);
		button_1.addActionListener(this);

		model.setColumnIdentifiers(new Object[] { "제목", "인원" });

		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		getContentPane().add(btnNewButton);
		getContentPane().add(button_1);
		getContentPane().add(scroll);

		getContentPane().setLayout(null);

		setResizable(false);
		setTitle("가맹점");
		setSize(250, 350);
		setVisible(true);
	}// 생성자 종료

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnNewButton) {
			System.out.println("입장");

		} else if (e.getSource() == button_1) {
			System.out.println("종료");
		}

	}// actionPerformed:메서드 종료

	public static void main(String[] args) {
		new ClientFrame();
	}// main:메서드 종료
}// 클래스 종료
