package joe;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.Position.Bias;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.FailLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.FrameLoadEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.LoadEvent;
import com.teamdev.jxbrowser.chromium.events.LoadListener;
import com.teamdev.jxbrowser.chromium.events.ProvisionalLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.StartLoadingEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

public class Main extends JFrame {

	private Browser browser = new Browser();
	private BrowserView view = new BrowserView(browser);
	
	public Main() {
		
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("JxBrowser");
		setSize(1280, 720);
		setVisible(true);
		
		browser.addLoadListener(new LoadAdapter() {
			@Override
			public void onFinishLoadingFrame(FinishLoadingEvent event) {
				System.out.println("html 문서가 로드외었습니다.");
			}
		});
		
		String title = "하드웨어 판매량";
		ArrayList<PieElement> list = new ArrayList<>();
		list.add(new PieElement("모니터", 49));
		list.add(new PieElement("키보드", 35));
		list.add(new PieElement("마우스", 24));
		list.add(new PieElement("어뎁터", 15));
		browser.loadHTML(new GoogleAPI().getPieChart(title, list));
		add(view, BorderLayout.CENTER);
		

	}

	public static void main(String[] args) {
		Main main = new Main();

	}

}
