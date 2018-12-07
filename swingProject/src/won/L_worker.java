package won;

import javax.swing.JOptionPane;

/*
 * 2018-12-06
 * 계정 확인하는 페이징 입니다.
 * DB는 H_FranchasDAO DTO를 같이 사용합니다.
 */
import DTO_DAO.*;

public class L_worker {
	H_FranchiseDAO fDAO = new H_FranchiseDAO();
	H_FranchiseDTO fDTO;

	// wonHn
	// 관리자 비밀번호 확인하는 메서드
	protected boolean checkRpw(String id, String pw) {
		fDTO = fDAO.selectFranchiseInfo(id);
		if (fDTO != null) {	//fDTO반환값이 있을때만 실행
			fDTO.getPw().equals(pw);
			return true;
		}
		return false;
	}

	// wonHn
	// 가맹점 아이디 확인하는 메서드
	private boolean checkUid(String id) {
		fDTO = fDAO.selectFranchiseInfo(id);
		if (fDTO != null) {	//fDTO반환값이 있을때만 실행
			return true;
		}
		JOptionPane.showMessageDialog(null, "계정이 없습니다.\n 본사에 문의해주세요.");
		return false;
	}

	// 가맹점 비밀번호 확인하는 메서드
	protected boolean checkUpw(String id,String pw) {
		if(checkUid(id)) {
			fDTO.getPw().equals(pw);
			return true;
		}
		return false;
	}
}
