package rew2;

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

public class Main extends JFrame {

	private Browser browser = new Browser();
	// 이것이 HTML을 Swing 형태로 바꿔주는 것인가 ??

	private BrowserView browserView = new BrowserView(browser);
	// 이것은 HTML을 Swing 형태로 받아준 browser을 화면에 배치해주는 것인가 ?

	public Main() {

		setSize(800, 600);
		setTitle("구글차트");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		browser.addLoadListener(new LoadAdapter() {

			@Override
			public void onFinishLoadingFrame(FinishLoadingEvent event) {
				if (event.isMainFrame()) {
					System.out.println("HTML 문서가 로드되었습니다.");
				}
			}
		}); // 액션리스너 종료
		String title = "하드웨어 판매량";
		browser.loadHTML(inserHTML(null, null, null));
		add(browserView, BorderLayout.CENTER);
		setVisible(true);
	}
	
//	ArrayList<Integer> headPurchase  ok
//	ArrayList<Integer> headSales  ok 
//	ArrayList<Integer> totalBodySales ok
	public String inserHTML(ArrayList<Integer> headPurchase,ArrayList<Integer> headSales,ArrayList<Integer> totalBodySales) {
		// 매입
		// 매출
		// 가맹점 총 매출
		
		String htmlString = "<html>\r\n" + 
				"  <head>\r\n" + 
				"    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\r\n" + 
				"    <script type=\"text/javascript\">\r\n" + 
				"      google.charts.load('current', {'packages':['corechart']});\r\n" + 
				"      google.charts.setOnLoadCallback(drawVisualization);\r\n" + 
				"\r\n" + 
				"      function drawVisualization() {\r\n" + 
				"        // Some raw data (not necessarily accurate)\r\n" + 
				"        var data = google.visualization.arrayToDataTable([\r\n" + 
				"          ['월', '매입', '매출', '가맹점 총 매출', 'Average'],\r\n";
		
		for (int i = 0; i < 12; i++) {   
			
		}
		htmlString+=
				"          ['2004/05',  165,      938,         522,             998],\r\n" + 
				"          ['2005/06',  135,      1120,        599,             1268],\r\n" + 
				"          ['2006/07',  157,      1167,        587,             807],\r\n" + 
				"          ['2007/08',  139,      1110,        615,             968],\r\n" + 
				"          ['2008/09',  136,      691,         629,             1026]\r\n" + 
				"        ]);\r\n" + 
				"\r\n" + 
				"        var options = {\r\n" + 
				"          title : '본사의 매입 및 매출 , 총 가맹점 매출',\r\n" + 
				"          vAxis: {title: '금액'},\r\n" + 
				"          hAxis: {title: 'Month'},\r\n" + 
				"          seriesType: 'bars',\r\n" + 
				"          series: {3: {type: 'line'}}\r\n" + 
				"        };\r\n" + 
				"\r\n" + 
				"        var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));\r\n" + 
				"        chart.draw(data, options);\r\n" + 
				"      }\r\n" + 
				"    </script>\r\n" + 
				"  </head>\r\n" + 
				"  <body>\r\n" + 
				"    <div id=\"chart_div\" style=\"width: 900px; height: 500px;\"></div>\r\n" + 
				"  </body>\r\n" + 
				"</html>";
		return  htmlString; 
	}
	
	
	
	
	

}
