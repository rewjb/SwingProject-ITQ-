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

//1. 계층도
//하위 내용은 클래스 계층도 입니다.
//기본적으로 인터페이스 계층도 이며 () 괄호안에 들어가 있는 것은 인터페이스 및 클래스 여부입니다.
//1) 기호정리
//   괄호 :  I(인터페이스) , C(클래스)
//    ↑ : 아래에 위치한 클래스를 위에 있는 클래스에서 객체로 만들어 사용
//    │ : 상속 및 구현
//                                                                                                BBQ(I)                                                                          
//                                                       ┌───────────────────────────────────────────┴──────────────────────────────────────────────────────────────────────────────────────────┐
//                                                   BBQHead(I)                                                                                                                             BBQBody(I) 
//     ┌───────────────────┌────────────────────┌────────┴────────────┐──────────────────┐─────────────────┐────────────────┐──────────────────────────┐                   ┌────────────┌───────┴───────┐───────────────┐ 
//HeadCheckOrder(I)    HeadOrder(I)       HeadStockInOut(I)       HeadVender(I)  HeadVenderProduct(I) HeadFranchise(I)   HeadSales(I)            H_ChattingFrame(C)     BodyHall(I)   BodyOrder(I)   BodySales(I)   BodyStock(I)
//     │                   │                    │                     │                  │                 │                │                          ↑                   │            │               │               │
//H_CheckOrder(C)      H_Order(C)         H_StockInOut(C)         H_Vender(C)       H_V_Product(C)    H_Franchise(C)     H_Salses(C)            ┌───────│─────────────┐ B_HallC(C)   B_OrderC(C)     B_SalesC(C)     B_StockC(C)
//                                                                                                            ┌─────────────↑────────────────┐ │H_ChattingJoin(C)    │                     ┌────────────↑─────────────┐
//                                                                                                            │ H_Salses_BodySalesBarChart(C)│ │       ↑             │                     │B_SalesC_SalesDataChart(C)│ 
//                                                                                                            │ H_Salses_BodySalesPieChart(C)│ │H_ChattingrManager(C)│                     └──────────────────────────┘
//                                                                                                            │H_Salses_HeadSalsesBarChart(C)│ └─────────────────────┘
//                                                                                                            └──────────────────────────────┘
//2. 변수명
//1) 전역변수 식별자 특징
//  => g_식별자  (g : 글로벌 변수)  ,  ex) g_BtnSelect

public class B_StockC extends JPanel implements BodyStock, ActionListener {

	private JButton g_NotCheckStockBt;// 미확인 재고
	private JButton g_CheckStockBt;// 확인 재고
	private JButton g_CheckBt;// 확인

	private DefaultTableModel g_NotCheckStockmodel = new DefaultTableModel(0, 4);//미확인 재고표 
	private DefaultTableModel g_CheckStockmodel = new DefaultTableModel(0, 2);//확인 재고표 

	private JTable g_NotCheckStockListTable = new JTable(g_NotCheckStockmodel) {//미확인 재고 테이블
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private JTable g_CheckStockListTable = new JTable(g_CheckStockmodel)//확인 재고 테이블
	{
		public boolean isCellEditable(int row, int column) {
			if (column==0) {
				return false;
			} else {
				return true;
			}
		
		};
	};

	private JScrollPane g_NotCheckStockScroll = new JScrollPane(g_NotCheckStockListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,//미확인 재고 스크롤
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane g_CheckStockScroll = new JScrollPane(g_CheckStockListTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,//확인재고 스크롤
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	
	private ArrayList<Integer> g_NumList;//재고에 담을 자재들의 고유번호를 담고 있는 리스트
	private int[] g_SelectRows;//여러개를 선택해서 가맹점 확인이 가능하도록 선택한 행들을 담는 배열

	private final JLabel g_NotCheckStockLabel = new JLabel("\uBBF8\uD655\uC778\uC7AC\uACE0 \uAD00\uB9AC");
	private final JLabel g_CheckStockLabel = new JLabel("\uD1B5\uD569\uC7AC\uACE0 \uAD00\uB9AC");

	public B_StockC() {//생성자시작
//		for (int i = 0; i < model1.getRowCount(); i++) { 잘모르겠다 나중에 알아봐야 겠음.
//			listTable1.isCellEditable(i, 1);
//	}
		setLayout(null);
		setSize(790, 370);
		setBackground(new Color(184,207,229));
		g_NotCheckStockBt = new JButton("미확인 재고");
		g_CheckStockBt = new JButton("\uD1B5\uD569 \uC7AC\uACE0");

		g_NotCheckStockBt.setBounds(47, 309, 107, 35);
		g_CheckStockBt.setBounds(443, 309, 101, 34);
		g_NotCheckStockScroll.setBounds(37, 37, 283, 262);
		g_CheckStockScroll.setBounds(380, 37, 223, 262);

		g_NotCheckStockListTable.getTableHeader().setResizingAllowed(false);
		g_NotCheckStockListTable.getTableHeader().setReorderingAllowed(false);
		g_NotCheckStockmodel.setColumnIdentifiers(new Object[] { "식자재", "수량", "본사 확인", "가맹점 확인" });
		g_CheckStockListTable.getTableHeader().setResizingAllowed(false);
		g_CheckStockListTable.getTableHeader().setReorderingAllowed(false);
		g_CheckStockmodel.setColumnIdentifiers(new Object[] { "식자재", "수량" });

	
		g_CheckBt = new JButton("\uAC00\uB9F9\uC810 \uD655\uC778");
		g_CheckBt.setBounds(193, 309, 107, 35);
		
		g_NotCheckStockListTable.setBackground(Color.LIGHT_GRAY);
		g_CheckStockListTable.setBackground(Color.LIGHT_GRAY);
		

		g_NotCheckStockBt.addActionListener(this);
		g_CheckStockBt.addActionListener(this);
		g_CheckBt.addActionListener(this);
		add(g_CheckStockScroll);
		add(g_NotCheckStockScroll);
		add(g_NotCheckStockBt);
		add(g_CheckStockBt);

		add(g_CheckBt);
		g_NotCheckStockLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		g_NotCheckStockLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_NotCheckStockLabel.setBounds(121, 10, 117, 15);
		
		add(g_NotCheckStockLabel);
		g_CheckStockLabel.setHorizontalAlignment(SwingConstants.CENTER);
		g_CheckStockLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		g_CheckStockLabel.setBounds(430, 10, 117, 15);
		
		add(g_CheckStockLabel);
		setVisible(false);
	}//생성자 끝

	@Override
	public void show(BBQBody bbqBody) {//인터페이스 오버라이딩
		((Component) bbqBody).setVisible(true);
	}

	@Override
	public void hide(BBQBody bbqBody) {//인터페이스 오버라이딩
		((Component) bbqBody).setVisible(false);
	}

	

	@Override
	public void notCheckStock() {//미확인 재고 목록 보여주는 메서드
		g_NumList = new ArrayList<>();
			for (int i = 0; i < B_OrderDAO.getInstance().hCheckSelect(B_Frame.st_G_id).size(); i++) {// 본사만 확인 한 재고들을 보여주는 반복문
				g_NumList.add( B_OrderDAO.getInstance().hCheckSelect(B_Frame.st_G_id).get(i).getNum());
				g_NotCheckStockmodel.insertRow(0, new Object[] { B_OrderDAO.getInstance().hCheckSelect(B_Frame.st_G_id).get(i).getName(),
						B_OrderDAO.getInstance().hCheckSelect(B_Frame.st_G_id).get(i).getQuantity(), B_OrderDAO.getInstance().hCheckSelect(B_Frame.st_G_id).get(i).gethComfirm(),
						B_OrderDAO.getInstance().hCheckSelect(B_Frame.st_G_id).get(i).getbComfirm() });
			}
	}
	
	@Override
	public void stockCheck() {//미확인 재고 확인해주는 메서드
		g_SelectRows = g_NotCheckStockListTable.getSelectedRows();
		if (!(g_SelectRows.length==0)) {
			for (int i = 0; i < g_SelectRows.length; i++) {
				if (g_NotCheckStockmodel.getValueAt(g_SelectRows[i], 3).equals("")) {
					B_StockDAO.getInstance().insertStock(B_Frame.st_G_id, (String)g_NotCheckStockmodel.getValueAt(g_SelectRows[i], 0), (int)g_NotCheckStockmodel.getValueAt(g_SelectRows[i], 1));
					g_NotCheckStockmodel.setValueAt("bk-1", g_SelectRows[i], 3);//확인한재고 bk_1로 표에다가 실제로 표시
					B_OrderDAO.getInstance().bConfirmUpdate(g_NumList.get(g_SelectRows[i]));// 가맹점 확인 메서드
					
				}
			}
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == g_NotCheckStockBt) {// 미확인재고 버튼 기능
			if(g_NotCheckStockmodel.getRowCount()==0) {
				notCheckStock();
			}else {
				int rowCount = g_NotCheckStockmodel.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					g_NotCheckStockmodel.removeRow(0);
				}
				notCheckStock();
				
			}
			
		} else if (e.getSource() == g_CheckBt) {// 확인 버튼 기능

			if (g_NotCheckStockmodel.getRowCount()==0) {// 표에 미확인재고들이 없으면 확인할수없습니다.
				JOptionPane.showMessageDialog(null, "확인할 목록이 없습니다.");
			} else {// 표에 미확인 재고가 있으면 확인할 수 있습니다.
				 stockCheck();
					
			}

		} else if (e.getSource() == g_CheckStockBt) {// 통합재고 버튼 기능
			if (!(g_CheckStockmodel.getRowCount()==0)) {
				int rowCount = g_CheckStockmodel.getRowCount();
				for (int i = 0; i < rowCount; i++) {// 통합재고 버튼을 누를때마다 실시간으로 업데이트 하기위해 표를 지워주는 반복문
					g_CheckStockmodel.removeRow(0);

				}
				for (int i = 0; i < B_StockDAO.getInstance().stockSelectAll(B_Frame.st_G_id).size(); i++) {
					g_CheckStockmodel.insertRow(0, new Object[] { B_StockDAO.getInstance().stockSelectAll(B_Frame.st_G_id).get(i).getName(),
							B_StockDAO.getInstance().stockSelectAll(B_Frame.st_G_id).get(i).getQuantity() });
				}
			} else {

				for (int i = 0; i < B_StockDAO.getInstance().stockSelectAll(B_Frame.st_G_id).size(); i++) {
					g_CheckStockmodel.insertRow(0, new Object[] { B_StockDAO.getInstance().stockSelectAll(B_Frame.st_G_id).get(i).getName(),
							B_StockDAO.getInstance().stockSelectAll(B_Frame.st_G_id).get(i).getQuantity() });
				}
			}
		}

	}
}// 클래스 끝
