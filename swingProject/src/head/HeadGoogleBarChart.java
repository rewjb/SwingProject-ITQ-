package head;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import DTO_DAO.B_OrderDAO;
import DTO_DAO.B_SalesDAO;
import DTO_DAO.H_OrderDAO;

public class HeadGoogleBarChart extends JPanel {

	private Browser browser = new Browser();
	// 이것이 HTML을 Swing 형태로 바꿔주는 것인가 ??

	private BrowserView browserView = new BrowserView(browser);
	// 이것은 HTML을 Swing 형태로 받아준 browser을 화면에 배치해주는 것인가 ?

	ArrayList<Integer> headPurchase;
	ArrayList<Integer> headSales;
	ArrayList<Integer> totalBodySales;

	public HeadGoogleBarChart(String year) {

		setLayout(new BorderLayout());
		browser.addLoadListener(new LoadAdapter() {

			@Override
			public void onFinishLoadingFrame(FinishLoadingEvent event) {
				if (event.isMainFrame()) {
					System.out.println("HTML 문서가 로드되었습니다.");
				}
			}
		}); // 액션리스너 종료
		String title = "하드웨어 판매량";
		inserHTML(year);
		add(browserView, BorderLayout.CENTER);
		setVisible(true);
	}// 생성자 종료

	public void inserHTML(String year) {

		System.out.println();

		headPurchase = H_OrderDAO.getInstance().selectTotalMonthSalse(year);
		// 본사 발주목록에서 갖고온다.
		headSales = B_OrderDAO.getInstance().selectMonthBodyOrder(year);
		// 가맹점 발주목록에서 갖고온다.
		totalBodySales = B_SalesDAO.getInstance().selectMonthBodySales(year);
		// 가맹점 매출목록에서 갖고온다.

//		for (int i = 0; i < 12; i++) {
//			System.out.println(i+"번째 출력");
//			System.out.println("본점 발주목록"+headPurchase.get(i));
//			System.out.println("가맹점 발주목록"+headSales.get(i));
//			System.out.println("가맹점 매출목록"+totalBodySales.get(i));
//		}

		String[] month = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
		String htmlString = "<html>\r\n" + "  <head>\r\n"
				+ "    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\r\n"
				+ "    <script type=\"text/javascript\">\r\n"
				+ "      google.charts.load('current', {'packages':['corechart']});\r\n"
				+ "      google.charts.setOnLoadCallback(drawVisualization);\r\n" + "\r\n"
				+ "      function drawVisualization() {\r\n" + "        // Some raw data (not necessarily accurate)\r\n"
				+ "        var data = google.visualization.arrayToDataTable([\r\n"
				+ "          ['월', '본사 매입', '본사 매출', '가맹점 총 매출'],\r\n";

		for (int i = 0; i < 12; i++) {
			htmlString += "['" + month[i] + "'," + headPurchase.get(i) + "," + headSales.get(i) + ","
					+ totalBodySales.get(i) + "],";
		}
		htmlString += "        ]);\r\n" + "\r\n" + "        var options = {\r\n"
				+ "          title : '본사의 매입 및 매출 , 총 가맹점 매출',\r\n" + "          vAxis: {title: '금액'},\r\n"
				+ "          hAxis: {title: '월'},\r\n" + "          seriesType: 'bars',\r\n"
				+ "          series: {3: {type: 'line'}}\r\n" + "        };\r\n" + "\r\n"
				+ "        var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));\r\n"
				+ "        chart.draw(data, options);\r\n" + "      }\r\n" + "    </script>\r\n" + "  </head>\r\n"
				+ "  <body>\r\n" + "    <div id=\"chart_div\" style=\"width: 450px; height: 275px;\"></div>\r\n"
				+ "  </body>\r\n" + "</html>";
		
		browser.loadHTML(htmlString);
		//새롭게 갱신 하기!
		
	}
}
