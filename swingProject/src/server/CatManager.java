package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CatManager extends Thread{

	private Socket m_socket;
	private String m_ID;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			BufferedReader tmpbuffer = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
			//요청온 소켓을 통해? 통해가 아니다. 요청온 소켓으로부터 이다!! 입력받을 스트림을 매개변수로 하여 읽기전용 버퍼를 얻었다.
			
			String text;
			//얻은 소킷으로부터 정보를 받을 문자열이다.
			
			while(true)
			{
				text = tmpbuffer.readLine();
				//문자열에 버퍼에 저장된 메세지를 받는다.
				//개행문자가 올때까지 블록킹 상태가 된다.
				//이와 동일한 메서드로는 accept가 있다.
				
				if(text == null)
				{//접속이 중단되면 server socket.accept이 반응하는 것 같다.
					System.out.println(m_ID + "이(가) 나갔습니다.");
					for(int i = 0; i < ChatServer.m_OutputList.size(); ++i)
					{
						ChatServer.m_OutputList.get(i).println(m_ID + "이(가) 나갔습니다.");
						ChatServer.m_OutputList.get(i).flush();
					}
					break;
				}
				
				String[] split = text.split("highkrs12345");
				if(split.length == 2 && split[0].equals("ID"))
				{ //이거  IDhighkrs12345+아이디 붙은 녀석이 처음에 입장할때 입자했다고 알려주는 메서드였네 ㅋㅋ 
					m_ID = split[1];
					System.out.println(m_ID + "이(가) 입장하였습니다.");
					for(int i = 0; i < ChatServer.m_OutputList.size(); ++i)
					{
						ChatServer.m_OutputList.get(i).println(m_ID + "이(가) 입장하였습니다.");
						ChatServer.m_OutputList.get(i).flush();
					}
					continue;
				}
				
				for(int i = 0; i < ChatServer.m_OutputList.size(); ++i)
				{
					ChatServer.m_OutputList.get(i).println(m_ID + "> "+ text);
					ChatServer.m_OutputList.get(i).flush();
				}
			}
			
			ChatServer.m_OutputList.remove(new PrintWriter(m_socket.getOutputStream()));
			m_socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setSocket(Socket _socket)
	{
		m_socket = _socket;
	}
}
