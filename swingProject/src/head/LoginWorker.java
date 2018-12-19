package head;
/*
 * 2018-12-17
 * login페이지의 계정 확인하는 기능을 위한 클래스 입니다.
 * DB는 H_FranchasDAO DTO DAO를 같이 사용합니다.
 * 구현 및 주석 수정까지 완료 하였습니다.
 */

import javax.swing.JOptionPane;

import DTO_DAO.*;
import body.B_Frame;

public class LoginWorker {
	H_FranchiseDAO fDAO = new H_FranchiseDAO();
	H_FranchiseDTO fDTO;

	// wonHn
	// 모든 메서드가 성공할시 페이지 띄워주는 메서드
	void findYourPage(String id, String pw) {
		if (pickOutRoot(id, pw)) {
			if (checkRpw(id, pw)) {
				H_Frame hf = new H_Frame();
			} // 관리자 비밀번호 맞을때
		} else {
			if (checkUpw(id, "1234")) {
				B_Frame bf = new B_Frame(id);
			} // 사용자 비밀번호 맞을때
		}
	}// end findYourPage()

	// wonHn
	// 받아온 아이디 분류하는 메서드
	boolean pickOutRoot(String id, String pw) {
		if (id.equals("root")) {
			return true; // 관리자일 경우 참
		} else {
			return false; // 관리자가 아닐경우 거짓
		}
	}// end pickOutRoot()

	// wonHn
	// 관리자 비밀번호 확인하는 메서드
	boolean checkRpw(String id, String pw) {
		fDTO = fDAO.selectFranchiseInfo(id);
		if (id.equals("root") && pw.equals("123")) { // fDTO반환값이 있을때만 실행
			return true;
		}
		JOptionPane.showMessageDialog(null, "관리자 비밀번호가 다릅니다");
		return false;
	}// end checkRpw()

	// wonHn
	// 가맹점 아이디 확인하는 메서드
	boolean checkUid(String id) {
		fDTO = fDAO.selectFranchiseInfo(id);
		if (fDTO != null) { // fDTO반환값이 있을때만 실행
			return true;
		}
		JOptionPane.showMessageDialog(null, "계정이 없습니다.\n 본사에 문의해주세요.");
		return false;
	}// end checkUid()

	// 가맹점 비밀번호 확인하는 메서드
	boolean checkUpw(String id, String pw) {
		if (checkUid(id)) {
			if (fDTO.getPw().equals(pw)) {
				return true;
			}
		}
		JOptionPane.showMessageDialog(null, "비밀번호를 확인해주세요.");
		return false;
	}// end checkUpw()
}
