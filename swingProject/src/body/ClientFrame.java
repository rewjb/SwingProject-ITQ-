package body;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

public class ClientFrame extends JDialog implements ActionListener {

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

	private JButton exitBtn = new JButton("종료");

	public static JPanel selectRoomPanel = new JPanel();
	public static JPanel chattingPanel = new JPanel();

	private Socket c_socket;

	private JButton exitChattingRoomBtn = new JButton("나가기");
	private JTextField inputText = new JTextField();
	private JLabel roomLabel = new JLabel();
	public static JTextArea historyArea = new JTextArea();

	private Clinet clinet = new Clinet();

	public static String nowRoomName;

	private PrintWriter sendWriter;

	private String userName ;
	public ClientFrame(String id) {
		
		clinet.start();
		//이것이 가장 먼저 위에 있어야 한다.

		userName=id;
		
		exitChattingRoomBtn.setBounds(75, 295, 97, 23);
		inputText.setBounds(12, 265, 220, 23);
		roomLabel.setBounds(12, 2, 220, 23);
		historyArea.setBounds(12, 30, 220, 230);

		inputText.setHorizontalAlignment(JTextField.RIGHT);
		historyArea.setEditable(false);
		roomLabel.setHorizontalAlignment(JLabel.CENTER);

		chattingPanel.add(exitChattingRoomBtn);
		chattingPanel.add(inputText);
		chattingPanel.add(roomLabel);
		chattingPanel.add(historyArea);


		scroll.setBounds(12, 10, 220, 240);
		exitBtn.setBounds(75, 260, 97, 23);

		exitBtn.addActionListener(this);
		inputText.addActionListener(this);
		exitChattingRoomBtn.addActionListener(this);

		model.setColumnIdentifiers(new Object[] { "제목", "인원" });

		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		getContentPane().add(exitBtn);
		getContentPane().add(scroll);

		getContentPane().setLayout(null);

		exitBtn.addActionListener(this);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) { // 2번 클릭 시
					nowRoomName = (String) table.getValueAt(table.getSelectedRow(), 0);
					roomLabel.setText("방 제목 : " + nowRoomName);
					sendWriter.print("CHroom\n" + nowRoomName + "\n");
					sendWriter.flush();
				}
			}
		});

		model.setColumnIdentifiers(new Object[] { "제목", "인원" });

		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);

		selectRoomPanel.setLayout(null);
		selectRoomPanel.setBounds(0, 0, 250, 350);
		chattingPanel.setBounds(0, 0, 250, 350);
		chattingPanel.setLayout(null);

		selectRoomPanel.add(exitBtn);
		selectRoomPanel.add(scroll);

		chattingPanel.setVisible(false);

		setLayout(null);
		add(selectRoomPanel);  
		add(chattingPanel);
		setResizable(false);
		setTitle("접속자 아이디: "+ BodyFrame.id);
		setSize(250, 350);
		setVisible(false);
	}// 생성자 종료

	private void startClient() {
		sendWriter.print("Start\n"+userName+"\n");
		sendWriter.flush();
	}// 채팅서비스에 입장!

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == inputText) {
			sendWriter.print("Msend\n" + userName + "\n" + nowRoomName + "\n" + inputText.getText() + "\n");
			inputText.setText(null);
			sendWriter.flush();
		} // 메세지 전송

		if (e.getSource() == exitChattingRoomBtn) {
			historyArea.setText(null);
			sendWriter.print("EXroom\n" + nowRoomName + "\n");
			sendWriter.flush();
			selectRoomPanel.setVisible(true); ;
			chattingPanel.setVisible(false);

		}
		
		if (e.getSource()==exitBtn) {
			dispose();
		}
		

	}// actionPerformed:메서드 종료

	class Clinet extends Thread {

		@Override
		public void run() {
			super.run();
			try {
				c_socket = new Socket("127.0.0.1", 8000);
				// 기준이 되는 서버소켓 선언
				ClientReceive clientReceive = new ClientReceive(c_socket);
				clientReceive.start();
				
				sendWriter = new PrintWriter(c_socket.getOutputStream());
				sendWriter.print("Start\n"+userName+"\n");
				sendWriter.flush();
			} catch (Exception e) {
				System.out.println("내부 Server 클래스 오류");
				JOptionPane.showMessageDialog(null, "서버와의 연결에 실패하셨습니다.");
				dispose();
				e.printStackTrace();
			}

		}// run : 메서드 종료
	}// Server : 클래스 종료


}// 클래스 종료

// 아이디,방이름,위치(로비 or 방)>>