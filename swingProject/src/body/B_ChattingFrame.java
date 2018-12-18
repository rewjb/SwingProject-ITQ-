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

//1. 계층도
//하위 내용은 클래스 계층도 입니다.
//기본적으로 인터페이스 계층도 이며 () 괄호안에 들어가 있는 것은 인터페이스 및 클래스 여부입니다.
//1) 기호정리
//   괄호 :  I(인터페이스) , C(클래스)
//    ↑ : 아래에 위치한 클래스를 위에 있는 클래스에서 객체로 만들어 사용
//    │ : 상속 및 구현
//                                                                                                BBQ(I)                                                                          
//                                                       ┌───────────────────────────────────────────┴──────────────────────────────────────────────────────────────────────────────────────────┐
//                                                   BBQHead(I)                                                                                                                             BBQBody(I) 
//     ┌───────────────────┌────────────────────┌────────┴────────────┐──────────────────┐─────────────────┐────────────────┐──────────────────────────┐                   ┌────────────┌───────┴───────┐───────────────┐ 
//HeadCheckOrder(I)    HeadOrder(I)       HeadStockInOut(I)       HeadVender(I)  HeadVenderProduct(I) HeadFranchise(I)   HeadSales(I)            H_ChattingFrame(C)     BodyHall(I)   BodyOrder(I)   BodySales(I)   BodyStock(I)
//     │                   │                    │                     │                  │                 │                │                          ↑                   │            │               │               │
//H_CheckOrder(C)      H_Order(C)         H_StockInOut(C)         H_Vender(C)       H_V_Product(C)    H_Franchise(C)     H_Salses(C)            ┌───────│─────────────┐ B_HallC(C)   B_OrderC(C)     B_SalesC(C)     B_StockC(C)
//                                                                                                            ┌─────────────↑────────────────┐ │H_ChattingJoin(C)    │                     ┌────────────↑─────────────┐
//                                                                                                            │ H_Salses_BodySalesBarChart(C)│ │       ↑             │                     │B_SalesC_SalesDataChart(C)│ 
//                                                                                                            │ H_Salses_BodySalesPieChart(C)│ │H_ChattingrManager(C)│                     └──────────────────────────┘
//                                                                                                            │H_Salses_HeadSalsesBarChart(C)│ └─────────────────────┘
//                                                                                                            └──────────────────────────────┘
//2. 변수명
//1) 전역변수 식별자 특징
//  => g_식별자  (g : 글로벌 변수)  ,  ex) g_BtnSelect

public class B_ChattingFrame extends JDialog implements ActionListener {

	public static DefaultTableModel model = new DefaultTableModel(0, 2);
	private JTable g_Table = new JTable(model) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; // 가맹점의 발주 리스트(확인하지 않은..)를 넣을 Jtable / 오면서 셀을
		// 수정여부 메서드를 무조건 false값으로 리턴

	private JScrollPane g_Scroll = new JScrollPane(g_Table, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 발주내용의 스크롤 기능 객체

	private JButton g_ExitBtn = new JButton("종료");

	public static JPanel g_SelectRoomPanel = new JPanel();
	public static JPanel g_ChattingPanel = new JPanel();

	private Socket g_C_Socket;

	private JButton g_ExitChattingRoomBtn = new JButton("나가기");
	private JTextField g_InputText = new JTextField();
	private JLabel g_RoomLabel = new JLabel();
	public static JTextArea g_HistoryArea = new JTextArea();

	private Clinet g_Clinet = new Clinet();

	public static String g_NowRoomName;

	private PrintWriter g_SendWriter;

	private String g_UserName;

	public B_ChattingFrame(String id) {

		g_Clinet.start();
		// 이것이 가장 먼저 위에 있어야 한다.

		g_UserName = id;

		g_ExitChattingRoomBtn.setBounds(75, 295, 97, 23);
		g_InputText.setBounds(12, 265, 220, 23);
		g_RoomLabel.setBounds(12, 2, 220, 23);
		g_HistoryArea.setBounds(12, 30, 220, 230);

		g_InputText.setHorizontalAlignment(JTextField.RIGHT);
		g_HistoryArea.setEditable(false);
		g_RoomLabel.setHorizontalAlignment(JLabel.CENTER);

		g_ChattingPanel.add(g_ExitChattingRoomBtn);
		g_ChattingPanel.add(g_InputText);
		g_ChattingPanel.add(g_RoomLabel);
		g_ChattingPanel.add(g_HistoryArea);

		g_Scroll.setBounds(12, 10, 220, 240);
		g_ExitBtn.setBounds(75, 260, 97, 23);

		g_ExitBtn.addActionListener(this);
		g_InputText.addActionListener(this);
		g_ExitChattingRoomBtn.addActionListener(this);

		model.setColumnIdentifiers(new Object[] { "제목", "인원" });

		g_Table.getTableHeader().setResizingAllowed(false);
		g_Table.getTableHeader().setReorderingAllowed(false);
		getContentPane().add(g_ExitBtn);
		getContentPane().add(g_Scroll);

		getContentPane().setLayout(null);

		g_ExitBtn.addActionListener(this);
		g_Table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) { // 2번 클릭 시
					g_HistoryArea.setText("");
					g_NowRoomName = (String) g_Table.getValueAt(g_Table.getSelectedRow(), 0);
					g_RoomLabel.setText("방 제목 : " + g_NowRoomName);
					g_SendWriter.print("CHroom\n" + g_NowRoomName + "\n");
					g_SendWriter.flush();
				}
			}
		});

		model.setColumnIdentifiers(new Object[] { "제목", "인원" });

		g_Table.getTableHeader().setResizingAllowed(false);
		g_Table.getTableHeader().setReorderingAllowed(false);

		g_SelectRoomPanel.setLayout(null);
		g_SelectRoomPanel.setBounds(0, 0, 250, 350);
		g_ChattingPanel.setBounds(0, 0, 250, 350);
		g_ChattingPanel.setLayout(null);

		g_SelectRoomPanel.add(g_ExitBtn);
		g_SelectRoomPanel.add(g_Scroll);

		g_ChattingPanel.setVisible(false);

		setLayout(null);
		add(g_SelectRoomPanel);
		add(g_ChattingPanel);
		setResizable(false);
		setTitle("접속자 아이디: " + B_Frame.st_G_id);
		setSize(250, 350);
		setVisible(false);
	}// 생성자 종료

	private void startClient() {
		g_SendWriter.print("Start\n" + g_UserName + "\n");
		g_SendWriter.flush();
	}// 채팅서비스에 입장!

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == g_InputText) {
			g_SendWriter.print("Msend\n" + g_UserName + "\n" + g_NowRoomName + "\n" + g_InputText.getText() + "\n");
			g_InputText.setText(null);
			g_SendWriter.flush();
		} // 메세지 전송

		if (e.getSource() == g_ExitChattingRoomBtn) {
			g_HistoryArea.setText(null);
			g_SendWriter.print("EXroom\n" + g_NowRoomName + "\n");
			g_SendWriter.flush();
			g_SelectRoomPanel.setVisible(true);
			;
			g_ChattingPanel.setVisible(false);

		}

		if (e.getSource() == g_ExitBtn) {
			dispose();
		}

	}// actionPerformed:메서드 종료

	class Clinet extends Thread {

		@Override
		public void run() {
			super.run();
			try {
				g_C_Socket = new Socket("127.0.0.1", 8000);
				// 기준이 되는 서버소켓 선언
				B_ChattingManager clientReceive = new B_ChattingManager(g_C_Socket);
				clientReceive.start();

				g_SendWriter = new PrintWriter(g_C_Socket.getOutputStream());
				g_SendWriter.print("Start\n" + g_UserName + "\n");
				g_SendWriter.flush();
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