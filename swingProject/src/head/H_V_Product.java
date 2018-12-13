package head;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private Object[] column = { "업체ID", "No.", "재료명", "입고가", "발주가" };

	// 리벨과 텍스트필드 : 사용자가 입력하고 수정하는 부분
	private JLabel lbNum;
	private JTextField tfNum;
	private JLabel lbId;
	private JTextField tfId;
	private JComboBox cbId;
	private JLabel lbName;
	private JComboBox cbName;
	private JLabel lbMoney;
	private JTextField tfMoney;
	private JLabel lbOrderM;
	private JTextField tfOrderM;

	// 버튼
	private JButton btAdd;
	private JButton btModify;
	private JButton btDelete;
	private JButton btName; // 재료명, 이율 수정하는 버튼
	private JButton btRefresh;

	// 메뉴 수정에 관한 라벨,텍스트필드,버튼 테이블 등
	private JFrame nameF;
	private JTextField tfNameN;
	private JTextField tfNameP;
	private JButton btNameA;
	private JButton btNameM;
	private JButton btNameD;
	private DefaultTableModel nameM;
	private JTable nameT = new JTable(nameM);
	private JScrollPane nameS = new JScrollPane(nameT);
	private Object[] nameC = { "재료명", "발주가" };

	// 그외
	Object[] row;
	H_V_P_worker w = new H_V_P_worker(); // 기능을 넣어놓는 클래스
	H_VenderpDAO pDAO = new H_VenderpDAO(); // 제품 DAO
	H_VenderpDTO pDTO; // 제품 DTO
	H_VenderDAO vDAO = new H_VenderDAO(); // 업체 DAO
	H_VenderDTO vDTO; // 업체DTO
	String id; // 콤보박스 선택된 id 담아두는 변수
	String name; // 콤보박스 선택된 name 담아두는 변수
	HashMap<String, String> nameO = new HashMap<>(); // name에 해당하는 발주가격 담아놓은 컬렉션
	String[] np; // 텍스트에서 (재료)이름과 발주가 분리해주는 배열
	int tableIdx; // 메뉴명 추가 테이블 클릭시 해당 열의 인덱스값 받아오는 변수

	// 생성자 constructor
	public H_V_Product() {
		labelSetting();
		buttonSetting();
		showAll();
		mouseAction();
	}

	// 표에 관련된 설정사항
	private void tableSetting() {
		model = new DefaultTableModel(0, 5) {
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
				while (sc.hasNextLine()) {
					np = sc.nextLine().split("-");
					if (np[0].equals(pDTO.getName())) {
						row[4] = np[1];
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			model.addRow(row);
		}
		cbId.setVisible(true);
		tfId.setVisible(false);	//초기화시 업체아이디 입력받는 곳은 콤보박스로 설정
		tfNum.setText("자동생성");
		tfNum.setEditable(false);
		tfMoney.setText("");
	}

	// 업체id 콤보박스에 담아주는 메서드
	private void comboCId() {
		Vector vec = new Vector(); //콤보박스에 vector를 넣어야 합니다..!
		ArrayList<H_VenderDTO> list = vDAO.selectIdAllVenderInfo();
		for (int i = 0; i < list.size(); i++) {
			vec.add(list.get(i).getId());
			id = list.get(0).getId();
		}
		cbId = new JComboBox<H_VenderDTO>(vec);
		cbId.setBounds(587, 20, 120, 30);
		cbId.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				id = (String) cb.getSelectedItem();
			}
		});
		
		this.add(cbId);

	}

	// 메뉴이름 콤보박스에 담아주는 메서드
	private void comboPName() {
		try {
			Scanner sc = new Scanner(new File("H_VenderpName.txt"));
			Vector vec = new Vector<>();
			nameO = new HashMap<>();
			int idx = 0;
			while (sc.hasNextLine()) {
				np = sc.nextLine().split("-");
				if (idx == 0) { // 아무것도 선택 안했을때는 첫번째 콤보박스 항목을 기본으로 선택
					name = np[0];
					idx++;
				}
				nameO.put(np[0], np[1]);
				vec.add(np[0]); // 콤보박스에는 이름만 출력해주기.
			}
			cbName = new JComboBox(vec);
			cbName.setBounds(587, 100, 120, 30);
			cbName.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBox cb = (JComboBox) e.getSource();
					name = (String) cb.getSelectedItem();
					tfOrderM.setText(nameO.get(name)); //발주가 항목에 바로 값 들어가게 세팅
				}
			});
			add(cbName);
			sc.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

		lbName = new JLabel("재료명");
		lbName.setHorizontalAlignment(SwingConstants.CENTER);
		lbName.setBounds(522, 100, 60, 30);
		add(lbName);

		comboPName();
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

		lbOrderM = new JLabel("발주가");
		lbOrderM.setHorizontalAlignment(SwingConstants.CENTER);
		lbOrderM.setBounds(522, 180, 60, 30);
		add(lbOrderM);

		tfOrderM = new JTextField();
		tfOrderM.setColumns(10);
		tfOrderM.setBounds(587, 180, 150, 30);
		tfOrderM.setEditable(false);
		add(tfOrderM);
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

		btName = new JButton();
		btName.setBounds(710, 100, 30, 30);
		add(btName);
		btName.addActionListener(this);
	}

	// 마우스 액션에 관한 메서드 - 메인 테이블
	private void mouseAction() {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				cbId.setVisible(false);
				tfId.setVisible(true);
				tfId.setEditable(false);
				tfId.setText(model.getValueAt(i, 0).toString());

				tfNum.setEditable(false);
				tfNum.setText(model.getValueAt(i, 1).toString());

				cbName.setVisible(true);
				cbName.setSelectedItem(model.getValueAt(i, 2).toString());
				
				tfMoney.setText(model.getValueAt(i, 3).toString());
				
				tfOrderM.setText(model.getValueAt(i, 4).toString());
			}
		});
	}

	// 버튼 액션에 관한 메서드
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btAdd) { // add, insert
			pDTO = new H_VenderpDTO();
			pDTO.setId(id); // combobox에 선택된 값을 받아옵니다.
			// num은 자동생성 DAO의 sql에서 null값을 넣어줄 것입니다.
			pDTO.setName(name); // combobox에 선택된 값을 받아옵니다.
			pDTO.setMoney(Integer.parseInt(tfMoney.getText()));

			int rs = pDAO.insertVenderpInfo(pDTO);
			if (rs == 0) {
				System.out.println("H_Venderp insert실패");
			} else {
				System.out.println("H_Venderp insert성공");
			}
			showAll();
			
		}
		if (e.getSource() == btModify) { // modify, update ==> 가격만 수정 가능
			pDTO = new H_VenderpDTO();
			pDTO.setNum(Integer.parseInt(tfNum.getText()));
			pDTO.setName(name);
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
		if (e.getSource() == btName) {
			nameSetting();
		}
	}

//----------------------------------------------------------------------------------
	// 재료명 추가 수정 삭제 할 수 있는 프레임 설정
	private void nameSetting() {
		nameF = new JFrame();
		nameF.setSize(293, 350);
		nameF.getContentPane().setLayout(null);

		nameTBSetting(); // 재료명 프레임 내 테이블 세팅
		nameMouseAction(); // 재료명 프레임 내 마우스 액션
		nameTFSetting(); // 재료명 프레임 내 텍스트필드 세팅
		nameBTSetting(); // 재료명 프레임 내 버튼 세팅
		nameBTAction(); // 재료명 프레임 내임 버튼 액션

		tfNameN.setEditable(true);
		nameF.setVisible(true);

	}

	// 재료명 프레임 내 테이블 세팅
	private void nameTBSetting() {
		// 테이블 수정 못하게 막아주는 명령
		nameM = new DefaultTableModel(0, 2) {

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};
		nameM.setColumnIdentifiers(nameC);
		nameT.setModel(nameM);

		nameF.add(nameS);
		nameS.setBounds(10, 50, 255, 205);
		nameT.setBackground(Color.LIGHT_GRAY);
		nameT.setForeground(Color.BLACK);
		nameT.setRowHeight(20);

		Scanner sc;
		row = new Object[2];
		try {
			sc = new Scanner(new File("H_VenderpName.txt"));
			while (sc.hasNextLine()) {
				row = sc.nextLine().split("-");
				nameM.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	// 재료명 프레임 내 버튼 세팅
	private void nameBTSetting() {
		// 추가 버튼
		btNameA = new JButton("추가");
		btNameA.setBounds(10, 270, 60, 30);
		nameF.add(btNameA);

		// 수정 버튼
		btNameM = new JButton("수정");
		btNameM.setBounds(75, 270, 60, 30);
		nameF.add(btNameM);

		// 삭제 버튼
		btNameD = new JButton("삭제");
		btNameD.setBounds(140, 270, 60, 30);
		nameF.add(btNameD);

		// 적용 버튼
		btRefresh = new JButton("적용");
		btRefresh.setBounds(205, 270, 60, 30);
		nameF.add(btRefresh);

	}

	// 재료명 프레임 내임 버튼 액션
	private void nameBTAction() {

		// 추가버튼을 눌렀을때 : 파일에서 전체를 읽어오고 맨 마지막에 입력받은 값을 추가해서 저장
		btNameA.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Scanner sc;
				String s = "";
				try {
					sc = new Scanner(new File("H_VenderpName.txt"));
					while (sc.hasNextLine()) {
						s += sc.nextLine() + "\r\n";
					}
					Writer w = new FileWriter("H_VenderpName.txt");
					s += tfNameN.getText() + "-" + tfNameP.getText();
					System.out.println(s);
					w.write(s);
					sc.close();
					w.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				nameTBSetting();
			}
		});

		// 수정버튼 눌렀을때 : 파일에서 전체를 읽어오고 중간에 인덱스 순서가 같은 부분만 수정값을 넣어서 저장
		btNameM.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Scanner sc;
				String s = "";
				try {
					sc = new Scanner(new File("H_VenderpName.txt"));

					int i = -1;
					while (sc.hasNextLine()) {
						i++;
						if (i == tableIdx) {
							s += tfNameN.getText() + "-" + tfNameP.getText() + "\r\n";
							sc.nextLine();
						} else {
							s += sc.nextLine() + "\r\n";
						}
					}
					System.out.println(s);
					Writer w = new FileWriter("H_VenderpName.txt");
					w.write(s);
					sc.close();
					w.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				nameTBSetting();
			}
		});

		// 삭제버튼 눌렀을때 : 파일에서 전체를 읽어오고 중간에 인덱스 순서가 같은 부분만 건너뛰고 나머지를 넣어서 저장
		btNameD.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Scanner sc;
				String s = "";
				try {
					sc = new Scanner(new File("H_VenderpName.txt"));
					int i = -1;
					while (sc.hasNextLine()) {
						i++;
						if (i == tableIdx) {
							sc.nextLine();
							s += "";
						} else {
							s += sc.nextLine() + "\r\n";
						}
					}
					System.out.println(s);
					Writer w = new FileWriter("H_VenderpName.txt");
					w.write(s);
					sc.close();
					w.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				nameTBSetting();
			}
		});

		// 적용버튼 눌렀을때
		btRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("dd");
				nameF.dispose();
			}
		});
	}

	// 재료명 프레임 내 마우스 액션
	private void nameMouseAction() {
		nameT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableIdx = nameT.getSelectedRow();
				tfNameN.setEditable(false);
				tfNameN.setText(nameM.getValueAt(tableIdx, 0).toString());
				tfNameP.setText(nameM.getValueAt(tableIdx, 1).toString());
			}
		});
	}

	// 재료명 프레임 내 텍스트필드 세팅
	private void nameTFSetting() {
		tfNameN = new JTextField();
		tfNameN.setText("제품명");
		nameF.add(tfNameN);
		tfNameN.setBounds(10, 10, 125, 30);
		// 재료 명을 클릭했을때 한번에 비워지는 액션
		tfNameN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tfNameN.setText("");
			}
		});

		tfNameP = new JTextField();
		tfNameP.setText("이윤");
		nameF.add(tfNameP);
		tfNameP.setBounds(140, 10, 125, 30);
		// 이윤 항목을 틀릭했을때 한번에 비워지는 액션
		tfNameP.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tfNameP.setText("");
			}
		});

	}

}
