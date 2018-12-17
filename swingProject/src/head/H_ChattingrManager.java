package head;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;

public class H_ChattingrManager extends Thread {

	Socket c_socket;
	String id;
	PrintWriter sendWriter;
	String sendMessage;

	@Override
	public void run() {
		super.run();
		try {
			BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(c_socket.getInputStream()));
			sendWriter = new PrintWriter(c_socket.getOutputStream());
			String inputStr = null;

			int sendMemberNum;


			
			while (true) {

				try {
					inputStr = inputBuffer.readLine();
		

				if (inputStr.equals("Start")) { // 처음 접속시 !
					id = inputBuffer.readLine();
					sendRoomList(sendWriter);
					H_ChattingFrame.allMemberList.add(sendWriter);
				} // 처음 접속 시도때 아이디를 받는다.

				if (inputStr.equals("CHroom")) {
					inputStr = inputBuffer.readLine();
					boolean exist = false;
					Set roomSet = H_ChattingFrame.room.keySet();
					for (int i = 0; i < roomSet.size(); i++) {
						if (roomSet.toArray()[i].equals(inputStr)) {
							exist = true;
						}
					}
					if (exist) {
						H_ChattingFrame.room.get(inputStr).add(sendWriter);
						int tempCount = H_ChattingFrame.model.getRowCount();
						int tempNum;
						for (int i = 0; i < tempCount; i++) {
							if (H_ChattingFrame.model.getValueAt(i, 0).equals(inputStr)) {
								tempNum = (int) H_ChattingFrame.model.getValueAt(i, 1);
								H_ChattingFrame.model.setValueAt(tempNum + 1, i, 1);
							}
						}
						sendWriter.print("CHroom\ntrue\n");
						sendWriter.flush();
					} else {
						sendWriter.print("CHroom\nfalse\n");
						sendWriter.flush();
					}
					
					int temp = H_ChattingFrame.allMemberList.size();
					PrintWriter tempPrint;
					for (int i = 0; i < temp; i++) {
						tempPrint = H_ChattingFrame.allMemberList.get(i);
						sendRoomList(tempPrint);
					}
					
				} // 클라이언트가 방에 입장하였을때

				if (inputStr.equals("Msend")) {
					// Msend\n사용자이름\n방이름\n보낼메시지
					inputStr = "";
					while (inputBuffer.ready()) {
						inputStr += inputBuffer.readLine() + "/";
					}
					// sendMessage 의 0번 : 사용자 이름
					// sendMessage 의 1번 : 방 이름
					// sendMessage 의 2번 : 보낼메세지
					sendMemberNum = H_ChattingFrame.room.get(inputStr.split("/")[1]).size();
					for (int j = 0; j < sendMemberNum; j++) {
						H_ChattingFrame.room.get(inputStr.split("/")[1]).get(j).print("Msend\n" + inputStr + "\n");
						H_ChattingFrame.room.get(inputStr.split("/")[1]).get(j).flush();
					}
				} // 특정 방에 접속 후 채팅을 전달할때

				if (inputStr.equals("EXroom")) {
					inputStr = inputBuffer.readLine();
					H_ChattingFrame.room.get(inputStr).remove(sendWriter);

					int roomCount = H_ChattingFrame.model.getRowCount();
					for (int i = 0; i < roomCount; i++) {
						if (inputStr.equals(H_ChattingFrame.model.getValueAt(i, 0))) {
							int tempMeberCount = (int) H_ChattingFrame.model.getValueAt(i, 1) - 1;
							H_ChattingFrame.model.setValueAt(tempMeberCount, i, 1);
						}
					} // 방에서 나갈 시 관리자 화면에서 사람 수가 변한다.

					// 사람 수가 변하고나면 모든 클라이언트에게도 전달을 해준다.
					int temp = H_ChattingFrame.allMemberList.size();
					PrintWriter tempPrint;
					for (int i = 0; i < temp; i++) {
						tempPrint = H_ChattingFrame.allMemberList.get(i);
						sendRoomList(tempPrint);
					}

				}

				
				if (inputStr.equals("EXchatting")) {
					//나가기 
					H_ChattingFrame.allMemberList.remove(sendMessage);
					c_socket.close();
					inputBuffer.close();
				}
				
				} catch (Exception e) {
					c_socket.close();
					inputBuffer.close();
					break;
				}

			} // while문 종료

		} catch (IOException e) {
			e.printStackTrace();
		}
	} // run() : 메서드 종료

	public void set_socket(Socket _socket) {
		c_socket = _socket;
	}// set_socket() : 메서드 종료

	private void sendRoomList(PrintWriter sendWriter) {
		sendMessage = "RoomList\n";
		// 초기화
		int temp = H_ChattingFrame.model.getRowCount();
		// 임시 int 값 , 생성된 방의 갯수 삽입
		for (int i = 0; i < temp; i++) {
			sendMessage += H_ChattingFrame.model.getValueAt(i, 0) + "/" + H_ChattingFrame.model.getValueAt(i, 1) + "\n";
		} // sendRoomList() : 메서드 종료
		sendWriter.print(sendMessage);
		sendWriter.flush();
		// 방 리스트 보내기
	}
}// 클래스 종료
