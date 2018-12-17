package body;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.mysql.jdbc.StandardSocketFactory;

public class B_ChattingManager extends Thread {

	Socket my_socket;
	String nowRoomName;

	public B_ChattingManager(Socket my_socket) {
		this.my_socket = my_socket;
	}// 생성자 종료
	


	@Override
	public void run() {
		super.run();
		try {
			BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(my_socket.getInputStream()));
			// 입력을 받을 버퍼
			PrintWriter sendWriter = new PrintWriter(my_socket.getOutputStream());
			
			String str = null;

			while (true) {

				try {
					str = inputBuffer.readLine();
			

				if (str.equals("RoomList")) {
					int temp = B_ChattingFrame.model.getRowCount();
					// 테이블의 현재 갯수
					for (int i = 0; i < temp; i++) {
						B_ChattingFrame.model.removeRow(0);
					} // 기존 방 삭제
					while (inputBuffer.ready()) {
						str = inputBuffer.readLine();
						System.out.println(str.split("/")[0]);
						B_ChattingFrame.model.insertRow(0, new Object[] { str.split("/")[0], str.split("/")[1] });
					}
				} // 처음 접속시 리스트를 받는 메세지

				if (str.equals("CHroom")) {
					str = inputBuffer.readLine();
					if (str.equals("true")) {
						B_ChattingFrame.chattingPanel.setVisible(true);
						B_ChattingFrame.selectRoomPanel.setVisible(false);
						nowRoomName = new String(B_ChattingFrame.nowRoomName);
					} else {
						JOptionPane.showMessageDialog(null, "존재하지 않는 방입니다.");
					}
				} // 방에 대한 허가여부를 받는 메세지

				if (str.equals("Msend")) {
					str = inputBuffer.readLine();
					if (B_ChattingFrame.nowRoomName.equals(str.split("/")[1])) {
						str = str.split("/")[0] + " : " + str.split("/")[2];
						B_ChattingFrame.historyArea.append(str + "\n");
					}

				}

				if (str.equals("Delete")) {
					str = inputBuffer.readLine();
					if (str.equals(B_ChattingFrame.nowRoomName)) {
						JOptionPane.showMessageDialog(null, "방이 삭제되었습니다.");
						B_ChattingFrame.selectRoomPanel.setVisible(true);
						B_ChattingFrame.chattingPanel.setVisible(false);
					}
				} else {

				}
				
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "서버와의 연결이 끊겼습니다.");
					inputBuffer.close();
					my_socket.close();
					break;
				}

			} // while문 종료

		} catch (IOException e) {
			System.out.println("ClientReceive 클래스 오류 ");
			e.printStackTrace();
		}

	}// run() : 메서드 종료

}// 클래스 종료
