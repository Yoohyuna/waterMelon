package action.accountsAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//추가

import accounts.AccountsDAO;
import accounts.AccountsDTO;
import action.CommandAction;

public class SignUpAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub

		// 한글처리
		request.setCharacterEncoding("utf-8");
		// BoardDTO->Setter Method(5)+hidden (4)
		// BoardDAO 객체 필요
		AccountsDTO account = new AccountsDTO();
		account.setId(request.getParameter("id"));
		account.setPassword(request.getParameter("password"));
		account.setNickname(request.getParameter("nickname"));
		account.setName(request.getParameter("name"));
		account.setEmail(request.getParameter("email"));

		AccountsDAO dao = new AccountsDAO();
		boolean checkId = dao.checkId(request.getParameter("id"));
		if (checkId) {
			request.setAttribute("checkId", checkId);
			return "/account/SignUp.jsp";
		}

		boolean checkPass = dao.checkPassword(request.getParameter("password"),
				request.getParameter("password_confirm"));
		if (checkPass==false) {
			request.setAttribute("checkPass", checkPass);
			return "/account/SignUp.jsp";
		}

		boolean checkInsert = dao.memberInsert(account);
		if (checkInsert==false) {
			request.setAttribute("checkInsert", checkInsert);
			return "/account/SignUp.jsp";
		}

		String id = account.getId();
		
		request.setAttribute("id", id);
		request.setAttribute("check", true);
		
		return "/account/SignUp.jsp";
	}
}