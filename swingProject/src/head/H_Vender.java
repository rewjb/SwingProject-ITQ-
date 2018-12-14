package head;
/*
 * 2018-11-30 wonHn
 * 기본 틀 구성
 * H_Vender위에 H_V_Company와  H_V_Product가 동작하는 구조
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import inter.BBQHead;
import inter.HeadVender;

public class H_Vender extends JPanel implements HeadVender {

	
	private JPanel paneVdC;
	private JPanel paneVdP;
	private JToggleButton btVdC; //업체
	private JToggleButton btVdP; //제품
	
	ButtonGroup btnG = new ButtonGroup();

	Color color = new Color(128, 144, 160);
	
	public H_Vender() {
		            
		setBounds(0, 0, 770, 368);
		setLayout(null);

		vCompanySetting();
		vProductSetting();
		
		setBackground(new Color(184, 207, 229));
		companeySetVisible(false);
		setVisible(false);
		
		btnG.add(btVdC);
		btnG.add(btVdP);
		
		btVdP.setBackground(color);
		btVdC.setBackground(color);
		
		btVdC.doClick();
	}
	
	private void vCompanySetting() {
		paneVdC = new H_V_Company();
		paneVdC.setBounds(0, 0, 770, 340);
		add(paneVdC);
		paneVdC.setLayout(null);
		
		btVdC = new JToggleButton("업체");
		add(btVdC);
		btVdC.setBounds(610, 345, 80, 23);
		btVdC.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				companeySetVisible(true);
				productSetVisible(false);
			}
		});

	}
	
	private void vProductSetting() {
		paneVdP = new H_V_Product();
		paneVdP.setBounds(0, 0, 770, 340);
		add(paneVdP);
		paneVdP.setLayout(null);
		
		btVdP = new JToggleButton("제품");
		btVdP.setBounds(690, 345, 80, 23);
		add(btVdP);
		btVdP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				companeySetVisible(false);
				productSetVisible(true);
				//콤보박스 재설정
				((H_V_Product) paneVdP).remove(((H_V_Product) paneVdP).cbId);
				((H_V_Product) paneVdP).comboCId();
			}
		});

	}

	private void companeySetVisible(boolean b) {
		if(b) {
			paneVdC.setVisible(true);
//			clb1.setVisible(true);
//			clb2.setVisible(true);
		}else {
			paneVdC.setVisible(false);
//			clb1.setVisible(false);
//			clb2.setVisible(false);
		}
	}
	
	private void productSetVisible(boolean b) {
		if(b) {
			paneVdP.setVisible(true);
//			plb1.setVisible(true);
//			plb2.setVisible(true);
		}else {
			paneVdP.setVisible(false);
//			plb1.setVisible(false);
//			plb2.setVisible(false);
		}
	}

	@Override
	public void show(BBQHead bbqHead) {
		// TODO Auto-generated method stub
		((Component) bbqHead).setVisible(true);
	}
	
	@Override
	public void hide(BBQHead bbqHead) {
		// TODO Auto-generated method stub
		((Component) bbqHead).setVisible(false);
	}
}
