package joe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import DTO_DAO.B_SalesDAO;
import inter.BBQBody;
import inter.BodySales;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Component;

public class BodySalesC extends JPanel implements BodySales,ActionListener{

	
	
	private DefaultTableModel model = new DefaultTableModel(17, 4);// 
	private DefaultTableModel model2 = new DefaultTableModel(0, 5);// 
	private DefaultTableModel model3 = new DefaultTableModel(0, 2);// 
	
	
	private JTable salesTable = new JTable(model) {//
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private JTable salesTableResult = new JTable(model2) {//
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private JTable popularMenu = new JTable(model3) {//
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	
	private JScrollPane scroll = new JScrollPane(salesTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane scroll2 = new JScrollPane(salesTableResult, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane scroll3 = new JScrollPane(popularMenu, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // 
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	
	
	private JButton button_1;
	private JButton btnNewButton;
	private JButton button;
	
	public BodySalesC() {
		setLayout(null);
		setSize(790 ,364);
		setBackground(Color.YELLOW);
		
		
		
		
		scroll3.setBounds(382, 200, 150, 84);
		scroll2.setBounds(382, 56, 314, 60);
		scroll.setBounds(30, 56, 314, 228);
		model.setColumnIdentifiers(new Object[] { "메뉴", "수량","합계","날짜" });
		model2.setColumnIdentifiers(new Object[] { "후라이드", "양념","간장","사이드","합계" });
		model3.setColumnIdentifiers(new Object[] { "순위", "메뉴" });
		salesTable.getColumnModel().getColumn(3).setPreferredWidth(200);
		
		btnNewButton = new JButton("\uC77C\uC77C\uB9E4\uCD9C ");
		button = new JButton("\uB9E4\uCD9C \uC885\uD569");
		button_1 = new JButton("\uC778\uAE30\uC21C\uC704");
		
		
		btnNewButton.setBounds(138, 304, 90, 23);
		button.setBounds(500, 126, 90, 23);
		
		
		
		
		
		
		
		
		
		
		add(scroll3);
		add(scroll2); 
		add(scroll);
		add(btnNewButton);
		add(button);
		button_1.setBounds(417, 304, 90, 23);
		
		add(button_1);
		
		setVisible(false);
	}
	
	@Override
	public void show(BBQBody bbqBody) {
		
	}

	@Override
	public void hide(BBQBody bbqBody) {
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
