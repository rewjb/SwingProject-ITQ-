package client;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ClientReceive extends Thread {

	Socket my_socket;

	public ClientReceive(Socket my_socket) {
		this.my_socket = my_socket;
	}// 생성자 종료

	@Override
	public void run() {
		super.run();
		try {
			BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(my_socket.getInputStream()));
			// 입력을 받을 버퍼
			String inputStr=null;

			while (true) {

				try {
					inputStr = 	inputBuffer.readLine();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,"서버와의 연결이 끊겼습니다.");
					System.exit(0);
				}
				

				if (inputStr.equals("Lsend")) {
					inputStr=""; // 초기화
					while (inputBuffer.ready()) {
						inputStr+=inputBuffer.readLine()+"/";
					}
					int temp = ClientFrame.model.getRowCount();
					//테이블의 현재 갯수
					for (int i = 0; i < temp; i++) {
						ClientFrame.model.removeRow(0);
					}
					temp = inputStr.split("/").length;
					for (int i = 0; i < temp; i++) {
						ClientFrame.model.insertRow(i, new Object[] {inputStr.split("/")[i]});
					}
				} //처음 접속시 리스트를 받는 메세지
				
				if (inputStr.equals("CHroom")) {
					System.out.println(inputStr);
					inputStr = 	inputBuffer.readLine();
					System.out.println(inputStr);
					if (inputStr.equals("true")) {
						ClientFrame.chattingPanel.setVisible(true);
						ClientFrame.selectRoomPanel.setVisible(false);
					}
				}//방에 대한 허가여부를 받는 메세지
				
				
				

			} // while문 종료

		} catch (IOException e) {
			System.out.println("ClientReceive 클래스 오류 ");
			e.printStackTrace();
		}

	}// run() : 메서드 종료

}// 클래스 종료
