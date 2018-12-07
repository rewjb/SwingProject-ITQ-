package server;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;

public class Server extends Thread {

	@Override
	public void run() {
		super.run();
		try {
			ServerSocket s_socket = new ServerSocket(8000);
			// 기준이 되는 서버소켓 선언

			while (true) {

				Socket c_socket = s_socket.accept();
				// 접속을 시도하는 소켓
				
				Set roomSet = ServerFrame.room.keySet();
				String roomListStr = null;
				roomListStr = "Lsend\n"; // 이것이 처음 열어보는 사람의 구분 문자!
				// 서비스타입/방이름/아이디>> 서비스타입(Msend,Lsend)
				int temp = roomSet.size();
				for (int i = 0; i < temp; i++) {
					roomListStr += roomSet.toArray()[i] + "\n";
				}
				PrintWriter sendWriter = new PrintWriter(c_socket.getOutputStream());
				sendWriter.print(roomListStr);
				sendWriter.flush();
				// 처음접속 => 접속소켓 객체에 처음 보내는 문장! (방의 리스트)

				Manager clientGuide = new Manager();
				clientGuide.set_socket(c_socket);
				clientGuide.start();
				// 객체에 방 리스트를 보낸 후 대기 스레드 실행!
			} // while문 종료
		} catch (Exception e) {
			System.out.println("내부 Server 클래스 오류");
			e.printStackTrace();
		}

	}// run : 메서드 종료

}
