package won;
/*
 * 2018-11-30 wonHn
 * 기본 틀 구성
 * H_Vender위에 H_V_Company와  H_V_Product가 동작하는 구조
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import inter.BBQHead;
import inter.HeadVender;

public class H_Vender extends JPanel implements HeadVender {

	
	private JPanel paneVdC;
	private JPanel paneVdP;
	private JButton btVdC;
	private JButton btVdP;

	public H_Vender() {

		setBounds(0, 0, 770, 368);
		setLayout(null);

		vCompanySetting();
		vProductSetting();
		
		paneVdC.setVisible(false);
		setVisible(false);
	}
	
	private void vCompanySetting() {
		paneVdC = new H_V_Company();
		paneVdC.setBounds(0, 20, 770, 315);
		add(paneVdC);
		paneVdC.setLayout(null);
		
		btVdC = new JButton("업체");
		add(btVdC);
		btVdC.setBounds(300, 0, 80, 20);
		btVdC.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				paneVdC.setVisible(true);
				paneVdP.setVisible(false);
			}
		});
	}
	
	private void vProductSetting() {
		paneVdP = new H_V_Product();
		paneVdP.setBounds(0, 20, 770, 315);
		add(paneVdP);
		paneVdP.setLayout(null);
		
		btVdP = new JButton("제품");
		btVdP.setBounds(380, 0, 80, 20);
		add(btVdP);
		btVdP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				paneVdC.setVisible(false);
				paneVdP.setVisible(true);
			}
		});
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
