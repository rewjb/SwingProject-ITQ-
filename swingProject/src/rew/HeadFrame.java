package rew;

import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import inter.BBQHead;

import javax.swing.JPanel;

public class HeadFrame extends JFrame implements BBQHead{
	
	JToggleButton F_OrderCheckBtn = new JToggleButton("가맹점 발주관리");
	JToggleButton OrderBtn = new JToggleButton("발주관리");
	JToggleButton StockBtn = new JToggleButton("입-출고관리");
	JToggleButton VenderBtn = new JToggleButton("업체관리");
	JToggleButton FranchiseBtn = new JToggleButton("가맹점관리");
	JToggleButton SalesBtn = new JToggleButton("매출관리");
	
	ButtonGroup btnGroup = new ButtonGroup();

	public HeadFrame()
	{ 
		
		
		F_OrderCheckBtn.setBounds(12, 20, 130, 23); 
		OrderBtn.setBounds(143, 20, 90, 23);
		StockBtn.setBounds(234, 20, 110, 23);
		VenderBtn.setBounds(345, 20, 90, 23);
		FranchiseBtn.setBounds(436, 20, 100, 23);
		SalesBtn.setBounds(537, 20, 90, 23);
		
		btnGroup.add(F_OrderCheckBtn);
		btnGroup.add(OrderBtn);
		btnGroup.add(StockBtn);
		btnGroup.add(VenderBtn);
		btnGroup.add(FranchiseBtn);
		btnGroup.add(SalesBtn);
		
		getContentPane().add(F_OrderCheckBtn);
		getContentPane().add(OrderBtn);
		getContentPane().add(StockBtn);
		getContentPane().add(VenderBtn);
		getContentPane().add(FranchiseBtn);
		getContentPane().add(SalesBtn);
		
//		F_OrderCheckBtn.addActionListener(l);
//		OrderBtn
//		StockBtn
//		VenderBtn
//		FranchiseBtn
//		SalesBtn
		
		
		//사이즈 , 레이아웃 및 각종 설정
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 53, 770, 358);
		getContentPane().add(panel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 450);
		setResizable(false);
		setVisible(true);
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		new HeadFrame();
	}
















}
