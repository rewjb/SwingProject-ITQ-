package joe;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import inter.BBQBody;
import inter.BodyOrder;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class BodyOrderC extends JPanel implements BodyOrder,ActionListener{
	
	private JLabel j;
	private JLabel j1;
	private DefaultTableModel model = new DefaultTableModel(20,3);
	private DefaultTableModel model1 = new DefaultTableModel(20,2);
	private JTable listTable = new JTable(model);
	private JTable listTable1 = new JTable(model1);
	private JButton bt;
	private JButton bt1;
	//리스트를 넣을 Jtable
	
	private JScrollPane scroll = new JScrollPane(listTable,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane scroll1 = new JScrollPane(listTable1,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	//Jtable의 스크롤 기능 객체 w
	//private DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
	//Jtable의 가운데 정렬 객체
	
	public BodyOrderC() {
		
		setLayout(null);
		setSize(781, 399);
		
		j = new JLabel("식자재 목록");
		j.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		j.setHorizontalAlignment(SwingConstants.CENTER);
		j1 = new JLabel("수량");
		j1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		j1.setHorizontalAlignment(SwingConstants.CENTER);
		j.setBounds(12,10,80,38);
		j1.setBounds(198,19,65,20);
		
		
		
		scroll.setBounds(0, 58, 560, 275);
		scroll1.setBounds(581, 58, 200, 275);
		
		model.setColumnIdentifiers(new Object[] {"상품명","수량","발주일"});
		model1.setColumnIdentifiers(new Object[] {"식자재","수량"});
		
		add(scroll1);
		add(scroll);
		add(j1);
		add(j, BorderLayout.WEST);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(96, 18, 80, 21);
		add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(263, 18, 80, 21);
		add(comboBox_1);
		
		JLabel lblNewLabel = new JLabel("\uC7AC\uACE0 \uBAA9\uB85D");
		lblNewLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 14));
		lblNewLabel.setBounds(648, 18, 57, 20);
		add(lblNewLabel);
		
		bt = new JButton("\uC120\uD0DD");
		bt.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		bt.setBounds(437, 17, 97, 23);
		add(bt);
		
		bt1 = new JButton("\uBC1C\uC8FC");
		bt1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		bt1.setBounds(672, 332, 97, 20);
		add(bt1);
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
		if (e.getSource()==bt) {
			
		}else if(e.getSource()==bt1) {
			
		}
	}
}
