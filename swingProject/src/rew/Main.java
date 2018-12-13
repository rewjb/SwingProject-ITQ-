package rew;

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
		setResizable(false);

		browser.addLoadListener(new LoadAdapter() {

			@Override
			public void onFinishLoadingFrame(FinishLoadingEvent event) {
				if (event.isMainFrame()) {
					System.out.println("HTML 문서가 로드되었습니다.");
				}
			}
		}); // 액션리스너 종료
		String title = "하드웨어 판매량";
		ArrayList<PieElement> list = new ArrayList<>();
		list.add(new PieElement("모니터", 49));
		list.add(new PieElement("키보드", 10));
		list.add(new PieElement("마우스", 25));
		list.add(new PieElement("어뎁터", 37));
		browser.loadHTML(new GoogleAPI().getPiechart(title, list));
		add(browserView, BorderLayout.CENTER);
		setVisible(true);
	}

}
