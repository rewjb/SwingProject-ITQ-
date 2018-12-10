package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer extends Thread {

	public static ArrayList<PrintWriter> m_OutputList;
	@Override
	public void run() {
		super.run();

		// TODO Auto-generated method stub

		m_OutputList = new ArrayList<PrintWriter>();
		// PrintWriter를 ArrayList에 받는다. 이것은 모든 이들에게 메세지를 나누어 줄때 편리하게 위함이다.

		try {
			ServerSocket s_socket = new ServerSocket(8000);
			// 서버소켓 번호를 8000번으로 설정하였다.
			while (true) {// 무한 루프

				Socket c_socket = s_socket.accept();
				// 무한루프 안에서 서버소켓은 신호가 오기를 기다린다.
				// 신호가 끊어지더라도 반응하는 것 같다..

				CatManager c_thread = new CatManager();
				// ClientManagerThread 스레드를 호출하였다. 여기에는 무슨 기능이 있을까?
				// 1. setSocket메서드 : 서버소켓.accept() 로 온 클라이언트 소켓을 잡아둔다.
				//

				c_thread.setSocket(c_socket);

				m_OutputList.add(new PrintWriter(c_socket.getOutputStream()));

				c_thread.start();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// run():메서드 종료

}
