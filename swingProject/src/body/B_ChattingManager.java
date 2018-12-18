package body;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.mysql.jdbc.StandardSocketFactory;

//1. 계층도
//하위 내용은 클래스 계층도 입니다.
//기본적으로 인터페이스 계층도 이며 () 괄호안에 들어가 있는 것은 인터페이스 및 클래스 여부입니다.
//1) 기호정리
//   괄호 :  I(인터페이스) , C(클래스)
//    ↑ : 아래에 위치한 클래스를 위에 있는 클래스에서 객체로 만들어 사용
//    │ : 상속 및 구현
//                                                                                                BBQ(I)                                                                          
//                                                       ┌───────────────────────────────────────────┴──────────────────────────────────────────────────────────────────────────────────────────┐
//                                                   BBQHead(I)                                                                                                                             BBQBody(I) 
//     ┌───────────────────┌────────────────────┌────────┴────────────┐──────────────────┐─────────────────┐────────────────┐──────────────────────────┐                   ┌────────────┌───────┴───────┐───────────────┐ 
//HeadCheckOrder(I)    HeadOrder(I)       HeadStockInOut(I)       HeadVender(I)  HeadVenderProduct(I) HeadFranchise(I)   HeadSales(I)            H_ChattingFrame(C)     BodyHall(I)   BodyOrder(I)   BodySales(I)   BodyStock(I)
//     │                   │                    │                     │                  │                 │                │                          ↑                   │            │               │               │
//H_CheckOrder(C)      H_Order(C)         H_StockInOut(C)         H_Vender(C)       H_V_Product(C)    H_Franchise(C)     H_Salses(C)            ┌───────│─────────────┐ B_HallC(C)   B_OrderC(C)     B_SalesC(C)     B_StockC(C)
//                                                                                                            ┌─────────────↑────────────────┐ │H_ChattingJoin(C)    │                     ┌────────────↑─────────────┐
//                                                                                                            │ H_Salses_BodySalesBarChart(C)│ │       ↑             │                     │B_SalesC_SalesDataChart(C)│ 
//                                                                                                            │ H_Salses_BodySalesPieChart(C)│ │H_ChattingrManager(C)│                     └──────────────────────────┘
//                                                                                                            │H_Salses_HeadSalsesBarChart(C)│ └─────────────────────┘
//                                                                                                            └──────────────────────────────┘
//2. 변수명
//1) 전역변수 식별자 특징
//  => g_식별자  (g : 글로벌 변수)  ,  ex) g_BtnSelect

public class B_ChattingManager extends Thread {

	Socket g_My_socket;
	String nowRoomName;

	public B_ChattingManager(Socket my_socket) {
		this.g_My_socket = my_socket;
	}// 생성자 종료
	


	@Override
	public void run() {
		super.run();
		try {
			BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(g_My_socket.getInputStream()));
			// 입력을 받을 버퍼
			PrintWriter sendWriter = new PrintWriter(g_My_socket.getOutputStream());
			
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
						B_ChattingFrame.g_ChattingPanel.setVisible(true);
						B_ChattingFrame.g_SelectRoomPanel.setVisible(false);
						nowRoomName = new String(B_ChattingFrame.g_NowRoomName);
					} else {
						JOptionPane.showMessageDialog(null, "존재하지 않는 방입니다.");
					}
				} // 방에 대한 허가여부를 받는 메세지

				if (str.equals("Msend")) {
					str = inputBuffer.readLine();
					if (B_ChattingFrame.g_NowRoomName.equals(str.split("/")[1])) {
						str = str.split("/")[0] + " : " + str.split("/")[2];
						B_ChattingFrame.g_HistoryArea.append(str + "\n");
					}

				}

				if (str.equals("Delete")) {
					str = inputBuffer.readLine();
					if (str.equals(B_ChattingFrame.g_NowRoomName)) {
						JOptionPane.showMessageDialog(null, "방이 삭제되었습니다.");
						B_ChattingFrame.g_SelectRoomPanel.setVisible(true);
						B_ChattingFrame.g_ChattingPanel.setVisible(false);
					}
				} else {

				}
				
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "서버와의 연결이 끊겼습니다.");
					inputBuffer.close();
					g_My_socket.close();
					break;
				}

			} // while문 종료

		} catch (IOException e) {
			System.out.println("ClientReceive 클래스 오류 ");
			e.printStackTrace();
		}

	}// run() : 메서드 종료

}// 클래스 종료
