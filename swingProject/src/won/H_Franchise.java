package won;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
/*
 * 2018-11-30 
 * wonHn
 * 틀만 작업 끝난 상태입니다. 버튼에 기능 넣지 않았습니다.
 * 추가 수정은 새로운 창을 띄울 예정입니다.
 * 
 */
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import inter.BBQHead;
import inter.HeadFranchise;

public class H_Franchise extends JPanel implements HeadFranchise {
	
	public H_Franchise() {
		setBounds(0, 0, 770, 368);
		setLayout(null);

		JButton btAdd;
		JButton btUpdate;
		JButton btDelete;

		DefaultTableModel model = new DefaultTableModel(0, 8);
		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		Object[] column = { "가맹점ID", "pw", "점주명", "연락처", "사업자번호", "주소1", "주소2", "별칭" };
		// DB에는 주소1,2가 구분자로만 나눠져서 하나의 테이블로 들어감
		model.setColumnIdentifiers(column);
		table.setModel(model);
		scrollPane.setBounds(15, 54, 738, 304);
		add(scrollPane);

		Object[] row = new Object[8];
		btAdd = new JButton("추가");
		btAdd.setBounds(15, 14, 70, 30);
		add(btAdd);
		btAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				새로 창을 띄워서 연결...
//				row[0] = "가맹점ID";
//				row[1] = "pw";S
//				row[2] = "점주명";
//				String s = "연락처0" + "연락처1" + "연락처2";
//				row[3] = s;
//				s = "사업자번호0" + "사업자번호1" + "사업자번호2";
//				row[4] = s;
//				row[5] = "주소1";
//				row[6] = "주소2";
//				row[7] = "별칭";
//
//				model.addRow(row);
			}
		});
		// 클릭한 테이블의 row인덱스값 읽어오기
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
//				tfId.setText(model.getValueAt(i, 0).toString());

			}
		});

		btUpdate = new JButton("수정");
		btUpdate.setBounds(95, 14, 70, 30);
		add(btUpdate);
		btUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				새로 창을 띄워서 연결...
//				int i = table.getSelectedRow();
//				if (i >= 0) {
//					model.setValueAt(tfId.getText(), i, 0);
//
//				} else {
//					System.out.println("Update Error");
//				}
			}
		});

		btDelete = new JButton("삭제");
		btDelete.setBounds(175, 14, 70, 30);
		add(btDelete);
		btDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				새로 창을 띄워서 연결...
//				int i = table.getSelectedRow();
//				if (i >= 0) {
//					model.removeRow(i);
//				} else {
//					System.out.println("Delete Error");
//				}
			}
		});
	}
	
	
	@Override
	public void show(BBQHead bbqHead) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide(BBQHead bbqHead) {
		// TODO Auto-generated method stub

	}

}
