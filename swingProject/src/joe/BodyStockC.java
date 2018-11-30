package joe;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import DTO_DAO.B_OrderDAO;
import inter.BBQBody;
import inter.BodyStock;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BodyStockC extends JPanel implements BodyStock,ActionListener{
	
	private JButton button;//미확인 재고
	private JButton button_1;//확인 재고 
	private JButton button_2;//확인
	
	private DefaultTableModel model = new DefaultTableModel(15, 4);
	private DefaultTableModel model1 = new DefaultTableModel(15, 3);
	
	private JTable listTable = new JTable(model){//
	      public boolean isCellEditable(int row, int column) {
	          return false;
	       };
	    };
	private JTable listTable1 = new JTable(model1){
	      public boolean isCellEditable(int row, int column) {
	          return false;
	       };
	    };
	
	private JScrollPane scroll = new JScrollPane(listTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane scroll1 = new JScrollPane(listTable1,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	B_OrderDAO dao = B_OrderDAO.getInstance();
	
	public BodyStockC() {
		
		setLayout(null);
		setSize(781,360);
		setBackground(Color.GREEN);
		
		button = new JButton("미확인 재고");
		button_1 = new JButton("확인 재고");
		
		
		button.setBounds(47, 309, 107, 35);
		button_1.setBounds(443, 309, 101, 34);
		scroll.setBounds(37, 37, 283, 262);
		scroll1.setBounds(380, 37, 223, 262);
		
		
		listTable.getTableHeader().setResizingAllowed(false);
		model.setColumnIdentifiers(new Object[] { "식자재", "수량", "본사 확인","가맹점 확인"});
		listTable1.getTableHeader().setResizingAllowed(false);
		model1.setColumnIdentifiers(new Object[] { "식자재", "수량","가맹점 확인"});
		
		button_2 = new JButton("\uD655\uC778");
		button_2.setBounds(193, 309, 107, 35);
		
		button.addActionListener(this);
		button_1.addActionListener(this);
		button_2.addActionListener(this);
		add(scroll1);
		add(scroll);
		add(button);
		add(button_1);
		
		add(button_2);
		setVisible(false);
	}
	
	
	@Override
	public void show(BBQBody bbqBody) {
		((Component) bbqBody).setVisible(true);
	}

	@Override
	public void hide(BBQBody bbqBody) {
		((Component) bbqBody).setVisible(false);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==button) {//미확인재고 버튼 기능
			
			for (int i = 0; i<dao.hCheckSelect().size(); i++) {
				model.insertRow(0, new Object[] {dao.hCheckSelect().get(i).getName(),
						dao.hCheckSelect().get(i).getQuantity(),
						dao.hCheckSelect().get(i).gethComfirm(),
						dao.hCheckSelect().get(i).getbComfirm()});
			}
		}else if (e.getSource()==button_2) {//확인 버튼 기능
			dao.bConfirmUpdate();
			for (int i = 0; i < dao.bCheckSelect().size(); i++) {
				model.removeRow(0);
			}
			for (int i = 0; i<dao.bCheckSelect().size(); i++) {
				       model.insertRow(0, new Object[] { dao.bCheckSelect().get(i).getName(),dao.bCheckSelect().get(i).getQuantity(),
				       dao.bCheckSelect().get(i).gethComfirm(),dao.bCheckSelect().get(i).getbComfirm()
				       });
			}
				
		}else if (e.getSource() == button_1) {//확인재고 버튼 기능
			for (int i = 0; i < dao.bCheckSelect().size(); i++) {
				model1.insertRow(0, new Object[] { dao.bCheckSelect().get(i).getName(),dao.bCheckSelect().get(i).getQuantity(),
						dao.bCheckSelect().get(i).getbComfirm()});
			}
		} 
		
	}
}
