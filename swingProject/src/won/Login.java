package won;

import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;

import inter.BBQ;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class Login extends JFrame implements BBQ, ActionListener{
	//판넬
	JPanel pLogin;
	JPanel pR;
	JPanel pU;

	//버튼
	JButton btRoot;
	JButton btUser;
	JButton btLoginR;
	JButton btLoginU;
	
	private JTextField tfId;
	private JPasswordField tfPw;

	public Login() {
		
		mainSetting();
//		buttonSetting();
//		loginRootPageSetting();
		loginUserPageSetting();
		
		
		setVisible(true);
		pR.setVisible(false);
//		pU.setVisible(false);
		
	}// 생성자 종료
	
	private void mainSetting() {
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 450);
		setResizable(false);
	}
	
	private void buttonSetting() {
		pLogin = new JPanel();
		pLogin.setBounds(70, 30, 660, 360);
		getContentPane().add(pLogin);
		pLogin.setLayout(null);
		
		btRoot = new JButton("관리자");
		btRoot.setLocation(100, 50);
		btRoot.setSize(200, 250);
		pLogin.add(btRoot);
		btRoot.addActionListener(this);
		
		btUser = new JButton("가맹점");
		btUser.setLocation(350, 50);
		btUser.setSize(200, 250);
		pLogin.add(btUser);
		btUser.addActionListener(this);
	}
	private void loginRootPageSetting() {
		pR = new JPanel();
		pR.setBounds(250, 80, 300, 300);
		getContentPane().add(pR);
		pR.setLayout(null);
		
		JLabel lbImg = new JLabel("");
		lbImg.setSize(100, 150);
		lbImg.setLocation(100, 10);
		lbImg.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\\uC9C1\uBC15\uAD6C\uB9AC\\2015031602655_3.png"));
		pR.add(lbImg);
		
		tfPw = new JPasswordField();
		tfPw.setBounds(70, 190, 160, 25);
		pR.add(tfPw);
		tfPw.setColumns(10);
		
		btLoginR = new JButton("Login");
		btLoginR.setBounds(100, 230, 100, 25);
		pR.add(btLoginR);
	}
	private void loginUserPageSetting() {
		pU = new JPanel();
		pU.setBounds(250, 80, 300, 300);
		getContentPane().add(pU);
		pU.setLayout(null);
		
		
		tfId = new JTextField();
		tfId.setBounds(70, 150, 160, 25);
		pU.add(tfId);
		tfPw.setColumns(10);

		tfPw = new JPasswordField();
		tfPw.setBounds(70, 190, 160, 25);
		pU.add(tfPw);
		tfPw.setColumns(10);
		
		btLoginU = new JButton("Login");
		btLoginU.setBounds(100, 230, 100, 25);
		pU.add(btLoginU);
	}

	public static void main(String[] args) {
		new Login();
	}// main 메서드 종료

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btRoot) {
			pLogin.setVisible(false);
			pR.setVisible(true);
			pU.setVisible(false);
		}
		if(e.getSource() == btUser) {
			pLogin.setVisible(false);
			pR.setVisible(false);
			pU.setVisible(true);
		}
	}
}// 클래스 종료
