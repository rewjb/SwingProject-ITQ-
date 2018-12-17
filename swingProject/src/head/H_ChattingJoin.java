package head;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;

public class H_ChattingJoin extends Thread {

	@Override
	public void run() {
		super.run();
		try {
			ServerSocket s_socket = new ServerSocket(8000);
			// 기준이 되는 서버소켓 선언

			while (true) {
				Socket c_socket = s_socket.accept();
				// 접속을 시도하는 소켓
				
				H_ChattingrManager clientGuide = new H_ChattingrManager();
				// 접속을 한 소켓과의 통신을 위한 객체 생성
				clientGuide.set_socket(c_socket);
				// 클라이언트 가이드 객체에 접속중인 소켓을 매개변수로 넘김
				clientGuide.start();
				// 클라이언트 가이드 객체 시작
			} // while문 종료
		} catch (Exception e) {
			e.printStackTrace();
		}

	}// run : 메서드 종료

} // 클래스 종료
