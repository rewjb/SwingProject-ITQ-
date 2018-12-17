package head;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import inter.BBQHead;



public class H_ChattingFrame extends JDialog implements ActionListener,BBQHead {

	public static DefaultTableModel g_RoomListModel = new DefaultTableModel(0, 2);
	private JTable g_RoomListTable = new JTable(g_RoomListModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	}; 
	private JScrollPane g_RoomListscroll = new JScrollPane(g_RoomListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    //채팅방을 목록을 넣을 수 있는 테이블 및 스크롤
	
	private JButton g_CreateRoomBtn = new JButton("생성");
	private JButton g_DeleteRoomBtn = new JButton("삭제");
	private JButton g_ExitChattingBtn = new JButton("종료");
	// 채팅방 서비스에 관련 버튼들

	public static HashMap<String, ArrayList<PrintWriter>> room = new HashMap<>();
	// <방제목 , 방에 접속한 고객의 printWriter>
	// 해당 방에 접속한 사람들끼리 대화가 가능하도록 해주는 Hashmap

	public static ArrayList<PrintWriter> allMemberList = new ArrayList<>();
	// 방을 삭제하거나 생성할 때 모든 인원들에게 사실을 알려주기 위한 리스트

	private JDialog plusRoomForm = new JDialog(this, "방 제목 설정", false);
	// 방 생성시 제목을 설정하는 창
	
	private JButton plusRoomConfirmBtn = new JButton("완료");
	// 방 생성지 완료버튼
	
	private JTextField plusRoomNameField = new JTextField();
	// 방이름을 입력하는 텍스트 필드

	private H_ChattingJoin server = new H_ChattingJoin();
	// 채팅 서버를 시작하는 서버 클래스

	public H_ChattingFrame() {

		server.start();
		// 채팅서버 시작

		plusRoomNameField.setColumns(10);

		plusRoomForm.setLayout(new BorderLayout());
		plusRoomForm.setSize(100, 80);
		plusRoomForm.setResizable(false);
		plusRoomForm.setVisible(false);
		// 채팅방 생성 폼의 설정

		plusRoomForm.getContentPane().add(plusRoomConfirmBtn, BorderLayout.SOUTH);
		plusRoomForm.getContentPane().add(plusRoomNameField, BorderLayout.CENTER);
		// 채팅방 생성 폼에텍스트 필드 및 버튼 추가

		g_CreateRoomBtn.setBounds(12, 260, 97, 23);
		g_DeleteRoomBtn.setBounds(135, 260, 97, 23);
		g_ExitChattingBtn.setBounds(73, 288, 97, 23);
		plusRoomConfirmBtn.setBounds(10, 60, 97, 23);
		plusRoomNameField.setBounds(10, 20, 97, 23);
		// GUI 상 보이는 버튼의 사이즈 조절 및 배치
		
		g_RoomListscroll.setBounds(12, 10, 220, 240);
		// 방리스트 스크롤의 사이즈 조절 및 배치

		g_CreateRoomBtn.addActionListener(this);
		g_DeleteRoomBtn.addActionListener(this);
		g_ExitChattingBtn.addActionListener(this);
		plusRoomConfirmBtn.addActionListener(this);
		plusRoomNameField.addActionListener(this);
		// 액션리스너 추가

		g_RoomListModel.setColumnIdentifiers(new Object[] { "제목", "인원" });
		// 방 리스트의 칼람 설정

		g_RoomListTable.getTableHeader().setResizingAllowed(false);
		g_RoomListTable.getTableHeader().setReorderingAllowed(false);
		// 발주테이블의 헤더를 얻어서 사이즈 수정 불가 , 발주테이블의 컬럼 이동 금지

		getContentPane().add(g_DeleteRoomBtn);
		getContentPane().add(g_CreateRoomBtn);
		getContentPane().add(g_ExitChattingBtn);
		getContentPane().add(g_RoomListscroll);

		getContentPane().setLayout(null);

		setResizable(false);
		setTitle("관리자");
		setSize(250, 350);
		setVisible(false);
	}// 생성자 종료

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == g_CreateRoomBtn) {
			// 생성 => 제목설정 프레임 생기기
			plusRoomForm.setLocationRelativeTo(this);
			plusRoomForm.setVisible(true);
		} else if (e.getSource() == g_DeleteRoomBtn) {
			// 삭제 => 테이블에서 선택한 방 삭제
			deleteRoom();
		} else if (e.getSource() == g_ExitChattingBtn) { // 종료되는 리스너
			dispose();
		} else if (e.getSource() == plusRoomConfirmBtn || e.getSource() == plusRoomNameField) {
			// 엔터 + 완료 = > 동일 이름의 방이 없으면 생성
			createRoom();
		}
	}// actionPerformed:메서드 종료

	private void createRoom() {
		// 방을 만드는 메서드
		int tempCount = g_RoomListTable.getRowCount();
		// 임시 int 변수 , 현재 존재하는 테이블에 있는 방 수량을 삽입
		
		boolean tempBoolean = true;
		// 기존에 동일한 방이 있는지 없는지 여부를 확인하는 boolean 변수
		// true : 동일 방이 없으므로 생성 가능
		// false : 동일 방이 있으므로 생성 불가능

		for (int i = 0; i < tempCount; i++) {
			// 기존에 동일한 방이 존재하는 확인
			if (g_RoomListTable.getValueAt(i, 0).equals(plusRoomNameField.getText())) {
				tempBoolean = false;
			}
		}
		
		
		if (tempBoolean) { // 동일한 방이 존재하지 않을때
			room.put(plusRoomNameField.getText(), new ArrayList<PrintWriter>());
			// 방이 생성되며 해당 방에 대한 ArrayList<PrintWriter> 를 생성
			
			g_RoomListModel.insertRow(0, new Object[] { plusRoomNameField.getText(), 0 });
			// 리스트에 생성된 방 화면에  추가
			
			plusRoomNameField.setText("");
			// 입력이 완료된 방이름은 삭제
			
			plusRoomForm.dispose();
			// 입력 폼 끄기

			tempCount = allMemberList.size();
			// 
			
			String tempStr = "RoomList\n";
			// 초기화
			int tempInt = H_ChattingFrame.g_RoomListModel.getRowCount();
			// 임시 int 값 , 생성된 방의 갯수 삽입
			
			for (int i = 0; i < tempInt; i++) {
				tempStr += H_ChattingFrame.g_RoomListModel.getValueAt(i, 0) + "/" + H_ChattingFrame.g_RoomListModel.getValueAt(i, 1) + "\n";
			} // 현재 존재하는 방의 이름을 문자열에 규칙에 맞게 입력

			for (int i = 0; i < tempCount; i++) {
				allMemberList.get(i).print(tempStr);
				allMemberList.get(i).flush();
			} // 모든 연결된 유저들에게 방 제목을 전송

		} else { // 동일한 방이 존재할 때
			JOptionPane.showMessageDialog(this, "이미 존재합니다.");
			plusRoomNameField.setText("");
		} // createRoom() : 메서드 종료

	}// createRoom() : 메서드 종료

	private void deleteRoom() {
		// 방을 삭제하는 메서드
		
		int tempCount = g_RoomListTable.getSelectedRowCount();
		// 임시 int 변수, 위 문장에서는 현재 존재하는 방의 수량을 삽입
		
		String tempStr;
		// 삭제 되었다는 메세지를 보내기 위한 문자열 변수
		
		if (tempCount == 1) {
			// 방을 하나만 선택했을 경우
			
			tempStr = (String) g_RoomListModel.getValueAt(g_RoomListTable.getSelectedRow(), 0);
			// 내가 지울려고 선택한 방 이름
			
			tempCount = allMemberList.size();
			// 연결된 모든 유저들의 수를 저장
			
			for (int i = 0; i < tempCount; i++) {
				allMemberList.get(i).print("Delete\n" + tempStr + "\n");
				allMemberList.get(i).flush();
			} // 모든 사용자에게 방의 삭제를 알림

			room.remove((String) g_RoomListTable.getValueAt(g_RoomListTable.getSelectedRow(), 0));
			// HashMap에서 해당하는 방을 삭제
			
			g_RoomListModel.removeRow(g_RoomListTable.getSelectedRow());
			//선택한 방을 테이블에서 삭제

			tempStr = "RoomList\n";
			// 초기화
			tempCount = H_ChattingFrame.g_RoomListModel.getRowCount();
			// 삭제 후 현재 존재하는 방의 수량을 삽입
			
			for (int i = 0; i < tempCount; i++) {
				tempStr += H_ChattingFrame.g_RoomListModel.getValueAt(i, 0) + "/" + H_ChattingFrame.g_RoomListModel.getValueAt(i, 1) + "\n";
			} // 현존하는 방의 리스트를 문자열에 넣기

			for (int i = 0; i < tempCount; i++) {
				allMemberList.get(i).print(tempStr);
				allMemberList.get(i).flush();
			} // 모든 사용자에게 방의 삭제를 알림

		} else {
			// 방을 여러개 선택했을 경우
			JOptionPane.showMessageDialog(this, "하나만 선택해 주세요.");
		}
	}// deleteRoom() : 메서드 종료

	
	@Override
	public void show(BBQHead bbqHead) {}
	//이 메서드는 BBQHead로 부터 받았으며 이 클래스에서는 정의하지 않습니다.

	@Override
	public void hide(BBQHead bbqHead) {}
	//이 메서드는 BBQHead로 부터 받았으며 이 클래스에서는 정의하지 않습니다.

}// 클래스 종료
