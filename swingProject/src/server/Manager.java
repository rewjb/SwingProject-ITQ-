package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;

public class Manager extends Thread {

	Socket c_socket;

	@Override
	public void run() {
		super.run();
		try {
			BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(c_socket.getInputStream()));
			PrintWriter sendWriter = new PrintWriter(c_socket.getOutputStream());
			String inputStr = "";

			while (true) {

				inputStr = inputBuffer.readLine();

				if (inputStr.equals("CHroom")) {
					inputStr = inputBuffer.readLine();
					boolean exist = false;
					Set roomSet = ServerFrame.room.keySet();
					for (int i = 0; i < roomSet.size(); i++) {
						if (roomSet.toArray()[i].equals(inputStr)) {
							exist = true;
						}
					}
					if (exist) {
						sendWriter.print("CHroom\ntrue\n");
						sendWriter.flush();
					} else {
						sendWriter.print("CHroom\nfalse\n");
						sendWriter.flush();
					}
				}

			} // while문 종료

		} catch (IOException e) {
			e.printStackTrace();
		}
	} // run() : 메서드 종료

	public void set_socket(Socket _socket) {
		c_socket = _socket;
	}// set_socket() : 메서드 종료

}// 클래스 종료

//아이디,방이름,위치(로비 or 방)>>