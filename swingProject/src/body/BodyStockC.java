package body;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import DTO_DAO.B_OrderDAO;
import DTO_DAO.B_StockDAO;
import inter.BBQBody;
import inter.BodyStock;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import java.awt.Font;

public class BodyStockC extends JPanel implements BodyStock, ActionListener {

	private JButton notCheckStockBt;// 미확인 재고
	private JButton checkStockBt;// 확인 재고
	private JButton checkBt;// 확인

	private DefaultTableModel notCheckStockmodel = new DefaultTableModel(0, 4);//미확인 재고표 
	private DefaultTableModel checkStockmodel = new DefaultTableModel(0, 2);//확인 재고표 

	private JTable notCheckStockListTable = new JTable(notCheckStockmodel) {//미확인 재고 테이블
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private JTable checkStockListTable = new JTable(checkStockmodel)//확인 재고 테이블
	{
		public boolean isCellEditable(int row, int column) {
			if (column==0) {
				return false;
			} else {
				return true;
			}
		
		};
	};

	private JScrollPane notCheckStockScroll = new JScrollPane(notCheckStockListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,//미확인 재고 스크롤
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane checkStockScroll = new JScrollPane(checkStockListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,//확인재고 스크롤
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	
	private ArrayList<Integer> numList;
	private int[] selectRows;

	private final JLabel notCheckStockLabel = new JLabel("\uBBF8\uD655\uC778\uC7AC\uACE0 \uAD00\uB9AC");
	private final JLabel checkStockLabel = new JLabel("\uD1B5\uD569\uC7AC\uACE0 \uAD00\uB9AC");

	public BodyStockC() {
//		for (int i = 0; i < model1.getRowCount(); i++) { 잘모르겠다 나중에 알아봐야 겠음.
//			listTable1.isCellEditable(i, 1);
//	}
		setLayout(null);
		setSize(781, 360);
		setBackground(new Color(184,207,229));
		notCheckStockBt = new JButton("미확인 재고");
		checkStockBt = new JButton("\uD1B5\uD569 \uC7AC\uACE0");

		notCheckStockBt.setBounds(47, 309, 107, 35);
		checkStockBt.setBounds(443, 309, 101, 34);
		notCheckStockScroll.setBounds(37, 37, 283, 262);
		checkStockScroll.setBounds(380, 37, 223, 262);

		notCheckStockListTable.getTableHeader().setResizingAllowed(false);
		notCheckStockListTable.getTableHeader().setReorderingAllowed(false);
		notCheckStockmodel.setColumnIdentifiers(new Object[] { "식자재", "수량", "본사 확인", "가맹점 확인" });
		checkStockListTable.getTableHeader().setResizingAllowed(false);
		checkStockListTable.getTableHeader().setReorderingAllowed(false);
		checkStockmodel.setColumnIdentifiers(new Object[] { "식자재", "수량" });

	
		checkBt = new JButton("\uAC00\uB9F9\uC810 \uD655\uC778");
		checkBt.setBounds(193, 309, 107, 35);

		notCheckStockBt.addActionListener(this);
		checkStockBt.addActionListener(this);
		checkBt.addActionListener(this);
		add(checkStockScroll);
		add(notCheckStockScroll);
		add(notCheckStockBt);
		add(checkStockBt);

		add(checkBt);
		notCheckStockLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		notCheckStockLabel.setHorizontalAlignment(SwingConstants.CENTER);
		notCheckStockLabel.setBounds(121, 10, 117, 15);
		
		add(notCheckStockLabel);
		checkStockLabel.setHorizontalAlignment(SwingConstants.CENTER);
		checkStockLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		checkStockLabel.setBounds(430, 10, 117, 15);
		
		add(checkStockLabel);
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

	

	
	public void notCheckStock() {//미확인 재고 목록 보여주는 메서드
		numList = new ArrayList<>();
			for (int i = 0; i < B_OrderDAO.getInstance().hCheckSelect(BodyFrame.id).size(); i++) {// 본사만 확인 한 재고들을 보여주는 반복문
				numList.add( B_OrderDAO.getInstance().hCheckSelect(BodyFrame.id).get(i).getNum());
				notCheckStockmodel.insertRow(0, new Object[] { B_OrderDAO.getInstance().hCheckSelect(BodyFrame.id).get(i).getName(),
						B_OrderDAO.getInstance().hCheckSelect(BodyFrame.id).get(i).getQuantity(), B_OrderDAO.getInstance().hCheckSelect(BodyFrame.id).get(i).gethComfirm(),
						B_OrderDAO.getInstance().hCheckSelect(BodyFrame.id).get(i).getbComfirm() });
			}
	}
	
	
	public void stockCheck() {//미확인 재고 확인해주는 메서드
		selectRows = notCheckStockListTable.getSelectedRows();
		if (!(selectRows.length==0)) {
			for (int i = 0; i < selectRows.length; i++) {
				if (notCheckStockmodel.getValueAt(selectRows[i], 3).equals("")) {
					B_StockDAO.getInstance().insertStock(BodyFrame.id, (String)notCheckStockmodel.getValueAt(selectRows[i], 0), (int)notCheckStockmodel.getValueAt(selectRows[i], 1));
					notCheckStockmodel.setValueAt("bk-1", selectRows[i], 3);//확인한재고 bk_1로 표에다가 실제로 표시
					B_OrderDAO.getInstance().bConfirmUpdate(numList.get(selectRows[i]));// 가맹점 확인 메서드
					
				}
			}
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == notCheckStockBt) {// 미확인재고 버튼 기능
			if(notCheckStockmodel.getRowCount()==0) {
				notCheckStock();
			}else {
				int rowCount = notCheckStockmodel.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					notCheckStockmodel.removeRow(0);
				}
				notCheckStock();
				
			}
			
		} else if (e.getSource() == checkBt) {// 확인 버튼 기능

			if (notCheckStockmodel.getRowCount()==0) {// 표에 미확인재고들이 없으면 확인할수없습니다.
				JOptionPane.showMessageDialog(null, "확인할 목록이 없습니다.");
			} else {// 표에 미확인 재고가 있으면 확인할 수 있습니다.
				 stockCheck();
					
			}

		} else if (e.getSource() == checkStockBt) {// 통합재고 버튼 기능
			if (!(checkStockmodel.getRowCount()==0)) {
				int rowCount = checkStockmodel.getRowCount();
				for (int i = 0; i < rowCount; i++) {// 통합재고 버튼을 누를때마다 실시간으로 업데이트 하기위해 표를 지워주는 반복문
					checkStockmodel.removeRow(0);

				}
				for (int i = 0; i < B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).size(); i++) {
					checkStockmodel.insertRow(0, new Object[] { B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).get(i).getName(),
							B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).get(i).getQuantity() });
				}
			} else {

				for (int i = 0; i < B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).size(); i++) {
					checkStockmodel.insertRow(0, new Object[] { B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).get(i).getName(),
							B_StockDAO.getInstance().stockSelectAll(BodyFrame.id).get(i).getQuantity() });
				}
			}
		}

	}
}// 클래스 끝
