package won;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import inter.BBQ;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import joe.BodyFrame;
import rew.HeadFrame;

public class Login extends JFrame implements BBQ, ActionListener{
	//판넬
	private JPanel pLogin;	//관리자,가맹점 선택
	private JPanel pR;		//관리자 로그인 화면
	private JPanel pU;		//가맹점 로그인 화면

	//버튼
	private JButton btHome;	//홈버튼
	private JButton btRoot;	//관리자계정 로그인화면 선택버튼
	private JButton btUser; //가맹점계정 로그인 화면 선택버튼
	private JButton btLoginR;	//관리자 로그인버튼
	private JButton btLoginU;	//관리자 로그인버튼
	
	private JTextField tfId;
	private JPasswordField tfPw;
	private JLabel lbId;
	private JLabel lbPw;

	public Login() {
		
		mainPSetting();
		subPSetting();
		loginRootPageSetting();
		loginUserPageSetting();
		
		
		setVisible(true);
		pR.setVisible(false);
		pU.setVisible(false);
		
	}// 생성자 종료
	
	private void mainPSetting() {
		getContentPane().setLayout(null);

		//홈버튼
		btHome = new JButton("Home");
		btHome.setBounds(700, 10, 80, 40);
		btHome.addActionListener(this);
		btHome.setVisible(false);
		
		getContentPane().add(btHome);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 450);
		setResizable(false);
	}
	
	private void subPSetting() {
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
		
		JLabel lbImg = new JLabel("이미지올릴예정");
		lbImg.setSize(100, 150);
		lbImg.setLocation(100, 10);
		lbImg.setIcon(new ImageIcon(""));
		pR.add(lbImg);
		
		tfPw = new JPasswordField();
		tfPw.setBounds(70, 190, 160, 25);
		pR.add(tfPw);
		tfPw.setColumns(10);
		
		btLoginR = new JButton("Login");
		btLoginR.setBounds(100, 230, 100, 25);
		pR.add(btLoginR);
		btLoginR.addActionListener(this);
	}
	
	private void loginUserPageSetting() {
		pU = new JPanel();
		pU.setBounds(250, 80, 300, 300);
		getContentPane().add(pU);
		pU.setLayout(null);
		
		lbId = new JLabel("ID");
		lbId.setHorizontalAlignment(SwingConstants.CENTER);
		lbId.setBounds(30, 98, 50, 25);
		pU.add(lbId);
		
		tfId = new JTextField();
		tfId.setBounds(80, 98, 150, 25);
		pU.add(tfId);
		tfPw.setColumns(10);
		
		lbPw = new JLabel("Pw");
		lbPw.setHorizontalAlignment(SwingConstants.CENTER);
		lbPw.setBounds(30, 133, 50, 25);
		pU.add(lbPw);

		tfPw = new JPasswordField();
		tfPw.setBounds(80, 133, 150, 25);
		pU.add(tfPw);
		tfPw.setColumns(10);
		
		btLoginU = new JButton("Login");
		btLoginU.setBounds(100, 178, 100, 25);
		pU.add(btLoginU);
		btLoginU.addActionListener(this);
	}

	public static void main(String[] args) {
		new Login();
	}// main 메서드 종료

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btRoot) {
			btHome.setVisible(true);
			pLogin.setVisible(false);
			pR.setVisible(true);
			pU.setVisible(false);
		}
		if(e.getSource() == btUser) {
			btHome.setVisible(true);
			pLogin.setVisible(false);
			pR.setVisible(false);
			pU.setVisible(true);
		}
		if(e.getSource() == btHome) {
			btHome.setVisible(false);
			pLogin.setVisible(true);
			pR.setVisible(false);
			pU.setVisible(false);
		}
		if(e.getSource() == btLoginR) {
			HeadFrame hf = new HeadFrame();
			//rew 일단 패키지에 headFrame에 걸어놨습니다.
		}
		if(e.getSource() == btLoginU) {
			BodyFrame bf = new BodyFrame();
		}
	}
}// 클래스 종료
