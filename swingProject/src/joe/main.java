package joe;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;

public class main {

	public static void main(String[] args) {
		Browser browser = new Browser();
		BrowserView view = new BrowserView();
		
		JFrame frame = new JFrame();
		frame.add(view, BorderLayout.CENTER);
		
		
		
	}

}
