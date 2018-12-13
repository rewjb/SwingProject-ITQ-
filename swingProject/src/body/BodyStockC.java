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
import javax.swing.SwingConstants;
import java.awt.Font;

public class BodyStockC extends JPanel implements BodyStock, ActionListener {

	private JButton notCheckStockBt;// 미확인 재고
	private JButton checkStockBt;// 확인 재고
	private JButton checkBt;// 확인

	private DefaultTableModel notCheckStockmodel = new DefaultTableModel(15, 4);//미확인 재고표 
	private DefaultTableModel checkStockmodel = new DefaultTableModel(15, 2);//확인 재고표 

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
	B_OrderDAO orderDAO = B_OrderDAO.getInstance();
	B_StockDAO stockDAO = B_StockDAO.getInstance();

	private final JLabel notCheckStockLabel = new JLabel("\uBBF8\uD655\uC778\uC7AC\uACE0 \uAD00\uB9AC");
	private final JLabel checkStockLabel = new JLabel("\uD1B5\uD569\uC7AC\uACE0 \uAD00\uB9AC");

	public BodyStockC() {
//		for (int i = 0; i < model1.getRowCount(); i++) { 잘모르겠다 나중에 알아봐야 겠음.
//			listTable1.isCellEditable(i, 1);
//	}
		setLayout(null);
		setSize(781, 360);
		//setBackground(Color.BLUE);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == notCheckStockBt) {// 미확인재고 버튼 기능
			if(notCheckStockmodel.getValueAt(0, 0)==null) {
				for (int i = 0; i < orderDAO.hCheckSelect(BodyFrame.id).size(); i++) {// 본사만 확인 한 재고들을 보여주는 반복문
					notCheckStockmodel.insertRow(0, new Object[] { orderDAO.hCheckSelect(BodyFrame.id).get(i).getName(),
							orderDAO.hCheckSelect(BodyFrame.id).get(i).getQuantity(), orderDAO.hCheckSelect(BodyFrame.id).get(i).gethComfirm(),
							orderDAO.hCheckSelect(BodyFrame.id).get(i).getbComfirm() });
				}
			}else {
				for (int i = 0; !(notCheckStockmodel.getValueAt(0, 0)==null); i++) {
					notCheckStockmodel.removeRow(0);
				}
				for (int i = 0; i < orderDAO.hCheckSelect(BodyFrame.id).size(); i++) {
					notCheckStockmodel.insertRow(0, new Object[] { orderDAO.hCheckSelect(BodyFrame.id).get(i).getName(),
							orderDAO.hCheckSelect(BodyFrame.id).get(i).getQuantity(), orderDAO.hCheckSelect(BodyFrame.id).get(i).gethComfirm(),
							orderDAO.hCheckSelect(BodyFrame.id).get(i).getbComfirm() });
				}
			}
			
		} else if (e.getSource() == checkBt) {// 확인 버튼 기능

			if (notCheckStockmodel.getValueAt(0, 0) == null) {// 표에 미확인재고들이 없으면 확인할수없습니다.
				JOptionPane.showMessageDialog(null, "확인할 목록이 없습니다.");
			} else {// 표에 미확인 재고가 있으면 확인할 수 있습니다.
				for (int i = 0; i < orderDAO.hCheckSelect(BodyFrame.id).size(); i++) {// 확인버튼 누를때 발주dB에 있는 데이터를 재고 DB로 옮기는 반복문
					stockDAO.insertStock(BodyFrame.id, orderDAO.hCheckSelect(BodyFrame.id).get(i).getName(),
							orderDAO.hCheckSelect(BodyFrame.id).get(i).getQuantity());
				}
//				for (int i = 0; i < orderDAO.hCheckSelect().size(); i++) {
//					model.removeRow(0);
//				}
				orderDAO.bConfirmUpdate();// 가맹점 확인 메서드
				for (int i = 0; i>=0; i++) {
					if(!(notCheckStockmodel.getValueAt(i, 0)==null)) {
						notCheckStockmodel.setValueAt("bk-1", i, 3);
					}else {
						break;
					}
				}
				if (!(checkStockmodel.getValueAt(0, 0) == null)) {
					for (int i = 0; !(checkStockmodel.getValueAt(0, 0) == null); i++) {// 확인재고 버튼을 누를때마다 실시간으로 업데이트 하기위해 표를 지워주는 반복문
						checkStockmodel.removeRow(0);

					}
					for (int i = 0; i < stockDAO.stockSelectAll(BodyFrame.id).size(); i++) {
						checkStockmodel.insertRow(0, new Object[] { stockDAO.stockSelectAll(BodyFrame.id).get(i).getName(),
								stockDAO.stockSelectAll(BodyFrame.id).get(i).getQuantity() });
					}
				} else {

					for (int i = 0; i < stockDAO.stockSelectAll(BodyFrame.id).size(); i++) {
						checkStockmodel.insertRow(0, new Object[] { stockDAO.stockSelectAll(BodyFrame.id).get(i).getName(),
								stockDAO.stockSelectAll(BodyFrame.id).get(i).getQuantity() });
					}
				}
					
			}

		} else if (e.getSource() == checkStockBt) {// 통합재고 버튼 기능
			if (!(checkStockmodel.getValueAt(0, 0) == null)) {
				for (int i = 0; !(checkStockmodel.getValueAt(0, 0) == null); i++) {// 통합재고 버튼을 누를때마다 실시간으로 업데이트 하기위해 표를 지워주는 반복문
					checkStockmodel.removeRow(0);

				}
				for (int i = 0; i < stockDAO.stockSelectAll(BodyFrame.id).size(); i++) {
					checkStockmodel.insertRow(0, new Object[] { stockDAO.stockSelectAll(BodyFrame.id).get(i).getName(),
							stockDAO.stockSelectAll(BodyFrame.id).get(i).getQuantity() });
				}
			} else {

				for (int i = 0; i < stockDAO.stockSelectAll(BodyFrame.id).size(); i++) {
					checkStockmodel.insertRow(0, new Object[] { stockDAO.stockSelectAll(BodyFrame.id).get(i).getName(),
							stockDAO.stockSelectAll(BodyFrame.id).get(i).getQuantity() });
				}
			}
		}

	}
}// 클래스 끝
