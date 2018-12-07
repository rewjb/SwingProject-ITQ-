package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JTextField;

public class SendThread extends Thread{

	private Socket m_Socket;
	private String id;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(System.in));
			// 스트림을 만들고 그 스트림을 통해오는 데이터를 읽을 리더를 만들고 그 리더를 잠시 저장할 버퍼 리더를 만든다.
			
			PrintWriter sendWriter = new PrintWriter(m_Socket.getOutputStream());
			
			String sendString;
			
//			System.out.println("사용할 ID를 입력해주십시오 :");
			//이거 이제 불필요
			ChatClient.UserID = tmpbuf.readLine();//이거 실전에서는 삭제
			//아이디 지정하는 거임
			
//			sendWriter.print();
			sendWriter.println("IDhighkrs12345" + id);
			//?? 이거 암호화하는건가 .. ?
			//이거 아이디 입력하면 서버에 주는 거네 ..
			//왜냐면 sendwriter가 m_Socket.getOutputStream() 를  넘어가는 데이터임 .. 이거는 서버랑 연결도어 있음
			
			sendWriter.flush();
			
			while(true)
			{//위에 저거 하고 이제 여기서 대기
				sendString = tmpbuf.readLine();

				if(sendString.equals("exit"))
				{
					break;
				}
				
				sendWriter.println(sendString);
				sendWriter.flush();
			}
			
			sendWriter.close();
			tmpbuf.close();
			m_Socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setting(Socket _socket,String _id)
	{
		this.m_Socket = _socket;
		this.id = _id;
	}	
}
