package head;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import DTO_DAO.*;

public class H_V_Product extends JPanel implements ActionListener {
	// 표에 관련된 부분
	private DefaultTableModel model;
	private JTable table = new JTable(model);
	private JScrollPane scrollPane = new JScrollPane(table);
	private Object[] column = { "업체ID", "No.", "재료명", "입고가", "이윤", "발주가" };

	// 리벨과 텍스트필드 : 사용자가 입력하고 수정하는 부분
	private JLabel lbNum;
	private JTextField tfNum;
	private JLabel lbId;
	private JTextField tfId;
	private JComboBox cbId;
	private JLabel lbName;
	private JTextField tfName;
	private JComboBox cbName;
	private JLabel lbMoney;
	private JTextField tfMoney;
	private JLabel lbPer;
	private JTextField tfPer;
	private JLabel lbPerM;
	private JTextField tfPerM;

	// 버튼
	private JButton btAdd;
	private JButton btModify;
	private JButton btDelete;

	// 그외
	Object[] row;
	H_V_P_worker w = new H_V_P_worker(); // 기능을 넣어놓는 클래스
	H_VenderpDAO pDAO = new H_VenderpDAO(); // 제품 DAO
	H_VenderpDTO pDTO; // 제품 DTO
	H_VenderDAO vDAO = new H_VenderDAO(); // 업체 DAO
	H_VenderDTO vDTO; // 업체DTO
	boolean cbNtf = false; // 콤보박스 사용여부 확인
	String id;		//콤보박스 선택된 id 담아두는 변수
	String name;	//콤보박스 선택된 name 담아두는 변수
	HashMap<String, String> namePer = new HashMap<>(); //name에 해당하는 이윤 담아놓은 컬렉션
	String[] np;
	
	// 생성자 constructor
	public H_V_Product() {
		labelSetting();
		buttonSetting();
		showAll();
		mouseAction();
	}

	// 표에 관련된 설정사항
	private void tableSetting() {
		model = new DefaultTableModel(0, 4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		model.setColumnIdentifiers(column);
		table.setModel(model);
		add(scrollPane);
		scrollPane.setBounds(15, 10, 500, 300);
		table.setBackground(Color.LIGHT_GRAY);
		table.setForeground(Color.BLACK);
		table.setRowHeight(20);
	}
	// 업체명 Combobox받아오는 메서드

	// refresh 표에 전체출력해주는 메서드
	private void showAll() {
		tableSetting();
		ArrayList<H_VenderpDTO> list = pDAO.selectALLVenderpInfo();
		for (int i = 0; i < list.size(); i++) {
			pDTO = list.get(i);
			row = new Object[6];
			row[0] = pDTO.getId();
			row[1] = pDTO.getNum();
			row[2] = pDTO.getName();
			row[3] = pDTO.getMoney();
			Scanner sc;
			try {
				sc = new Scanner(new File("H_VenderpName.txt"));
				while(sc.hasNextLine()) {
					np = sc.nextLine().split("-");
					if(np[0].equals(pDTO.getName())) {
						row[4] = np[1];
						row[5] = (int)(pDTO.getMoney()*(1+Double.parseDouble(np[1])));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			model.addRow(row);
		}
		cbNtf = true;
		cbId.setVisible(true);
		tfId.setVisible(false);
		tfNum.setText("자동생성");
		tfNum.setEditable(false);
		tfName.setText("");
		cbName.setVisible(true);
		tfName.setVisible(false);
		tfMoney.setText("");
	}

	// 업체id 콤보박스에 담아주는 메서드
	private void comboCId() {
		Vector vec = new Vector();
		ArrayList<H_VenderDTO> list = vDAO.selectIdAllVenderInfo();
		for (int i = 0; i < list.size(); i++) {
			vec.add(list.get(i).getId());
			id = list.get(0).getId();
		}
		cbId = new JComboBox<H_VenderDTO>(vec);
		cbId.setBounds(587, 20, 150, 30);
		cbId.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				id = (String) cb.getSelectedItem();
			}
		});
		add(cbId);
	}
	
	//메뉴이름 콤보박스에 담아주는 메서드
	private void comboPName() {
//		String path = "\\txt\\";
		try {
			Scanner sc = new Scanner(new File("H_VenderpName.txt"));
			Vector vec = new Vector<>();
			int idx = 0;
			while(sc.hasNextLine()) {
				np = sc.nextLine().split("-");
				if(idx == 0) {	//아무것도 선택 안했을때는 첫번째 콤보박스 항목을 기본으로 선택
					name = np[0];
					idx++;
				}
				namePer.put(np[0], np[1]);
				vec.add(np[0]);	//콤보박스에는 이름만 출력해주기.
			}
			cbName = new JComboBox(vec);
			cbName.setBounds(587, 100, 150, 30);
			cbName.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBox cb = (JComboBox) e.getSource();
					name = (String) cb.getSelectedItem();
					tfPer.setText(namePer.get(name));

				}
			});
			add(cbName);
			sc.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//메뉴이름 수정 삭제해주는 메서드
	

	// 라벨 및 텍스트필드 설정사항
	private void labelSetting() {
		lbId = new JLabel("업체ID");
		lbId.setHorizontalAlignment(SwingConstants.CENTER);
		lbId.setBounds(522, 20, 60, 30);
		add(lbId);

		comboCId(); // 업체 아이디 콤보박스
		tfId = new JTextField();
		tfId.setBounds(587, 20, 150, 30);
		add(tfId);
		cbId.setVisible(true);
		tfId.setVisible(false);

		lbNum = new JLabel("번호");
		lbNum.setHorizontalAlignment(SwingConstants.CENTER);
		lbNum.setBounds(522, 60, 60, 30);
		add(lbNum);

		tfNum = new JTextField();
		tfNum.setColumns(10);
		tfNum.setBounds(587, 60, 150, 30);
		add(tfNum);

		lbName = new JLabel("이름");
		lbName.setHorizontalAlignment(SwingConstants.CENTER);
		lbName.setBounds(522, 100, 60, 30);
		add(lbName);
		
		comboPName();
		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(587, 100, 150, 30);
		add(tfName);
		cbName.setVisible(true);
		tfId.setVisible(false);

		lbMoney = new JLabel("매입가");
		lbMoney.setHorizontalAlignment(SwingConstants.CENTER);
		lbMoney.setBounds(522, 140, 60, 30);
		add(lbMoney);

		tfMoney = new JTextField();
		tfMoney.setColumns(10);
		tfMoney.setBounds(587, 140, 150, 30);
		add(tfMoney);

		lbPer = new JLabel("이윤");
		lbPer.setHorizontalAlignment(SwingConstants.CENTER);
		lbPer.setBounds(522, 180, 60, 30);
		add(lbPer);
		
		tfPer = new JTextField();
		tfPer.setColumns(10);
		tfPer.setBounds(587, 180, 150, 30);
		tfPer.setEditable(false);
		tfPer.setText(namePer.get(name));
		add(tfPer);
		
		lbPerM = new JLabel("발주가");
		lbPerM.setHorizontalAlignment(SwingConstants.CENTER);
		lbPerM.setBounds(522, 220, 60, 30);
		add(lbPerM);
				
		tfPerM = new JTextField();
		tfPerM.setColumns(10);
		tfPerM.setBounds(587, 220, 150, 30);
		tfPerM.setEditable(false);
		add(tfPerM);
	}

	// 버튼에 관련된 설정사항
	private void buttonSetting() {
		btAdd = new JButton("추가");
		btAdd.setBounds(527, 275, 70, 30);
		add(btAdd);
		btAdd.addActionListener(this);

		btModify = new JButton("수정");
		btModify.setBounds(605, 275, 70, 30);
		add(btModify);
		btModify.addActionListener(this);

		btDelete = new JButton("삭제");
		btDelete.setBounds(683, 275, 70, 30);
		add(btDelete);
		btDelete.addActionListener(this);
	}

	// 마우스 액션에 관한 메서드
	private void mouseAction() {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				cbNtf = false;
				cbId.setVisible(false);
				tfId.setVisible(true);
				tfId.setText(model.getValueAt(i, 0).toString());
				tfId.setEditable(false);
				tfNum.setText(model.getValueAt(i, 1).toString());
				tfNum.setEditable(false);
				cbName.setVisible(false);
				tfName.setVisible(true);
				tfName.setText(model.getValueAt(i, 2).toString());
				tfName.setEditable(false);
				String m = model.getValueAt(i, 3).toString();
				tfMoney.setText(m);
				tfPer.setText(namePer.get(name));
				double d = Integer.parseInt(m)*(1+Double.parseDouble(namePer.get(name)));
				tfPerM.setText(""+d);
			}
		});
	}

	// 버튼 액션에 관한 메서드
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btAdd) { //add, insert
			if(cbNtf) {
				pDTO = new H_VenderpDTO();
			    pDTO.setId(id);	//combobox에 선택된 값을 받아옵니다.
				// num은 자동생성 DAO의 sql에서 null값을 넣어줄 것입니다.
				pDTO.setName(name); //combobox에 선택된 값을 받아옵니다.
				pDTO.setMoney(Integer.parseInt(tfMoney.getText()));
				
				int rs = pDAO.insertVenderpInfo(pDTO);
				if (rs == 0) {
					System.out.println("H_Venderp insert실패");
				} else {
					System.out.println("H_Venderp insert성공");
				}
			}
			showAll();
		}
		if (e.getSource() == btModify) { //modify, update ==> 가격만 수정 가능
			pDTO = new H_VenderpDTO();
			pDTO.setNum(Integer.parseInt(tfName.getText()));
			pDTO.setMoney(Integer.parseInt(tfMoney.getText()));
			
			int rs = pDAO.updateVenderpInfo(pDTO);
			if (rs == 0) {
				System.out.println("H_Venderp update실패");
			} else {
				System.out.println("H_Venderp update성공");
			}
			showAll();
		}
		if (e.getSource() == btDelete) {
			int num = Integer.parseInt(tfNum.getText());
			int rs = pDAO.deleteVenderpInfo(num);
			if (rs == 0) {
				System.out.println("H_Venderp delete실패");
			} else {
				System.out.println("H_Venderp delete성공");
			}
			showAll();
		}
	}
}