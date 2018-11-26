package rew;

import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;

public class HeadFrame extends JFrame{
	
	JToggleButton SalesBtn = new JToggleButton("매출관리");
	JToggleButton OrderBtn = new JToggleButton("발주관리");
	JToggleButton StockBtn = new JToggleButton("입-출고관리");
	JToggleButton VenderBtn = new JToggleButton("업체관리");
	JToggleButton FranchiseBtn = new JToggleButton("가맹점관리");
	
	ButtonGroup bg = new ButtonGroup();

	public HeadFrame()
	{ 
		OrderBtn.setBounds(42, 71, 135, 23);
		StockBtn.setBounds(182, 71, 135, 23);
		VenderBtn.setBounds(171, 104, 135, 23);
		FranchiseBtn.setBounds(171, 138, 135, 23);
		SalesBtn.setBounds(74, 180, 135, 23);
		
		getContentPane().add(OrderBtn);
		getContentPane().add(StockBtn);
		getContentPane().add(VenderBtn);
		getContentPane().add(FranchiseBtn);
		getContentPane().add(SalesBtn);
		
		//사이즈 , 레이아웃 및 각종 설정
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 450);
		setResizable(false);
		setVisible(true);
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		new HeadFrame();
	}
}
