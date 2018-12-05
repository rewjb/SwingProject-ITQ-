package rew;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import inter.BBQHead;
import inter.HeadSales;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.JEditorPane;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class H_Salses extends JPanel implements HeadSales, ActionListener, ItemListener {

	private JLabel franSalesLabel = new JLabel("가맹점 매출");
	private JRadioButton monthRadio = new JRadioButton("월별");
	private JRadioButton dayRadio = new JRadioButton("일별");
	private ButtonGroup radioGroup = new ButtonGroup();
	private JComboBox yearComboBox = new JComboBox();
	private JComboBox monthComboBox = new JComboBox();
	private JLabel yearLabel = new JLabel("년");
	private JLabel monthLabel = new JLabel("월");
	private JLabel franSelectJLabel = new JLabel("점");

	private int yearComboBoxNum;
	private int monthComboBoxNum;

	private final JComboBox franSelectJComboBox = new JComboBox();

	public H_Salses() {

		radioGroup.add(monthRadio);
		radioGroup.add(dayRadio);

		for (int i = 0; i < 10; i++) {
			yearComboBox.addItem(String.valueOf(2018 - i));
		}

		for (int i = 0; i < 12; i++) {
			monthComboBox.addItem(String.valueOf(i + 1));
		}

		monthRadio.addItemListener(this);
		dayRadio.addItemListener(this);

		setLayout(null);
		setBackground(Color.GREEN);
		setBounds(0, 0, 770, 358);
		setSize(770, 358);

		monthRadio.setBounds(133, 32, 60, 21);
		dayRadio.setBounds(199, 32, 60, 21);
		yearLabel.setBounds(230, 62, 20, 15);
		monthLabel.setBounds(314, 62, 20, 15);
		franSalesLabel.setBounds(160, 10, 70, 15);
		yearComboBox.setBounds(158, 59, 70, 21);
		monthComboBox.setBounds(252, 59, 60, 21);
		franSelectJLabel.setBounds(138, 62, 20, 15);

		add(monthComboBox);
		add(yearComboBox);
		add(dayRadio);
		add(yearLabel);
		add(monthLabel);
		add(monthRadio);
		add(franSalesLabel);
		add(franSelectJLabel);

		
	
		franSelectJComboBox.setBounds(50, 59, 86, 21);
		
		add(franSelectJComboBox);

		setVisible(false);// 마지막에는 false로 변경
	}// 생성자 끝

	@Override
	public void show(BBQHead bbqHead) {
	} // 이 메서드는 정의하지 않습니다.

	@Override
	public void hide(BBQHead bbqHead) {
	} // 이 메서드는 정의하지 않습니다.

	@Override
	public void actionPerformed(ActionEvent e) {
	}// actionPerformed:메서드 종료

	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if (monthRadio.isSelected()) {
			
		}else if (dayRadio.isSelected()) {
			
		}
	
		

	}// itemStateChanged:메서드 종료
}// 클래스 끝
