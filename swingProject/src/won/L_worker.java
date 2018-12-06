package won;

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
	protected boolean checkRpw() {
		String input = "AA";
		
		return true;
	}

	// wonHn
	// 가맹점 아이디 확인하는 메서드
	protected boolean checkUid() {

		return true;
	}

	// 가맹점 비밀번호 확인하는 메서드
	protected boolean checkUpw() {

		return true;
	}
}
