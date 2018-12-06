package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient extends Thread {

	public static String UserID;
	
	public String name;

	@Override
	public void run() {
		super.run();

		// TODO Auto-generated method stub
		try {
			Socket c_socket = new Socket("127.0.0.1", 8000);

			ReceiveThread rec_thread = new ReceiveThread();
			rec_thread.setSocket(c_socket);

			SendThread send_thread = new SendThread();
			send_thread.setting(c_socket, "유주빈");

			send_thread.start();
			rec_thread.start();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
