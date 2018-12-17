package won;
/*
 * 2018-12-17 wonHn
 * 로그인 페이지 구현하는 클래스 입니다.
 * 계정 조회는 L_worker에서 하고 
 * 구현 및 주석 수정까지 완료 하였습니다.
 */

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import inter.BBQ;
import java.awt.Image;
import java.awt.Color;

public class Login extends JFrame implements BBQ, ActionListener{
	//로그인 버튼
	private JButton btLoginU;
	
	//아이디 비밀번호 입력받는 필드
	private JTextField tfUId;
	private JPasswordField tfUPw;
	
	//그외
	LoginWorker loginWorker = new LoginWorker();
	int xpos;
	int ypos;
	
	Dimension point = Toolkit.getDefaultToolkit().getScreenSize();
	private JLabel lbLogin;
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image frameimg = toolkit.getImage("img/회사 프레임 아이콘.jpg");


	//생성자
	public Login() {
		getContentPane().setBackground(new Color(184, 207, 229));
		setIconImage(frameimg);
		setTitle("로그인");
		mainPageSetting();
		loginUserPageSetting();
	
		xpos = (int) (point.getWidth()/2 - getWidth()/2);
		ypos =  (int) (point.getHeight()/2 - getHeight()/2);
		setLocation(xpos, ypos);
		
		setVisible(true);
		
	}// 생성자 종료
	
	//메인판넬세팅
	private void mainPageSetting() {
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(290, 420);
		setResizable(false);
	}//end mainPSetting()

	//판넬 세팅
	private void loginUserPageSetting() {
		
		tfUId = new JTextField();
		tfUId.setBounds(50, 230, 180, 32);
		tfUId.setText("아이디를 입력하세요.");
		tfUId.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if (tfUId.getText().equals("")) {
					tfUId.setText("아이디를 입력하세요.");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if (tfUId.getText().equals("아이디를 입력하세요.")) {
					tfUId.setText("");
				}
			}
		});
		
		getContentPane().add(tfUId);
		
		tfUPw = new JPasswordField();
		tfUPw.setBounds(50, 265, 180, 32);
		tfUPw.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if (tfUPw.getText().equals("아이디를 입력하세요.")) {
					tfUPw.setText("");
				}
			}
		});
		tfUPw.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				tfUPw.setText("");
			}
		});
		getContentPane().add(tfUPw);
		
		btLoginU = new JButton("Login");
		btLoginU.setBounds(50, 310, 180, 35);
		getContentPane().add(btLoginU);
		btLoginU.addActionListener(this);
		
		JLabel lbLogo = new JLabel("");
		lbLogo.setIcon(new ImageIcon("img/logoSmall.png"));
		lbLogo.setBounds(10, 10, 55, 25);
		getContentPane().add(lbLogo);
		
		lbLogin = new JLabel("");
		lbLogin.setIcon(new ImageIcon("img/login.png"));
		lbLogin.setBounds(90, 110, 100, 85);
		getContentPane().add(lbLogin);
	}//end loginUserPageSetting()   

	public static void main(String[] args) {
		new Login();
	}// main 메서드 종료

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btLoginU) {
			loginWorker.findYourPage(tfUId.getText(), tfUPw.getText());
			dispose();
		}
	}//end actionPerformed()
}// 클래스 종료
