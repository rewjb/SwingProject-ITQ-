package head;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;

//1. 계층도
//하위 내용은 클래스 계층도 입니다.
//기본적으로 인터페이스 계층도 이며 () 괄호안에 들어가 있는 것은 인터페이스 및 클래스 여부입니다.
//1) 기호정리
//괄호 :  I(인터페이스) , C(클래스)
//↑ : 아래에 위치한 클래스를 위에 있는 클래스에서 객체로 만들어 사용
//│ : 상속 및 구현
//                                                                                               BBQ(I)                                                                          
//                                                      ┌───────────────────────────────────────────┴──────────────────────────────────────────────────────────────────────────────────────────┐
//                                                  BBQHead(I)                                                                                                                             BBQBody(I) 
//    ┌───────────────────┌────────────────────┌────────┴────────────┐──────────────────┐─────────────────┐────────────────┐──────────────────────────┐                   ┌────────────┌───────┴───────┐───────────────┐ 
//HeadCheckOrder(I)    HeadOrder(I)       HeadStockInOut(I)       HeadVender(I)  HeadVenderProduct(I) HeadFranchise(I)   HeadSales(I)            H_ChattingFrame(C)     BodyHall(I)   BodyOrder(I)   BodySales(I)   BodyStock(I)
//    │                   │                    │                     │                  │                 │                │                          ↑                   │            │               │               │
//H_CheckOrder(C)      H_Order(C)         H_StockInOut(C)         H_Vender(C)       H_V_Product(C)    H_Franchise(C)     H_Salses(C)          ┌───────│─────────────┐ B_HallC(C)   B_OrderC(C)     B_SalesC(C)     B_StockC(C)
//                                                                                                          ┌─────────────↑────────────────┐  │H_ChattingJoin(C)    │                     ┌────────────↑─────────────┐
//                                                                                                          │ H_Salses_BodySalesBarChart(C)│  │       ↑             │                     │B_SalesC_SalesDataChart(C)│ 
//                                                                                                          │ H_Salses_BodySalesPieChart(C)│  │H_ChattingrManager(C)│                     └──────────────────────────┘
//                                                                                                          │H_Salses_HeadSalsesBarChart(C)│  └─────────────────────┘
//                                                                                                          └──────────────────────────────┘
//2. 변수명
//1) 전역변수 식별자 특징
//=> g_식별자  (g : 글로벌 변수)  ,  ex) g_BtnSelect

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
