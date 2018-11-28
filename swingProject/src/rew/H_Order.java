package rew;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;

import inter.BBQHead;
import inter.HeadCheckOrder;

public class H_Order extends JPanel implements HeadCheckOrder {
	
	
	public H_Order() {
		
		
		
		
		
		setBackground(Color.BLACK);
		setBounds(0, 0, 770, 358);
		setSize(770, 358);
		setVisible(false); // 마지막에는 false로 변경
	}//생성자 끝

	@Override // 인터페이스 BBQHead로부터 받은 메서드 show 오버라이딩
	public void show(BBQHead bbqHead) {
		((Component) bbqHead).setVisible(true);
	}// show 메서드 끝

	@Override // 인터페이스 BBQHead로부터 받은 메서드 hide 오버라이딩
	public void hide(BBQHead bbqHead) {
		((Component) bbqHead).setVisible(false);
	}// hide 메서드 끝


}//클래스 끝
