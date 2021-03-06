package noticeAction;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import accounts.AccountsDTO;
import action.CommandAction;
//추가
import notice.*;
import java.util.*;

//list.water=action.ListAction 설정
public class ListAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {

		int role = Integer.parseInt(request.getParameter("role"));
		String pageNum = request.getParameter("pageNum"); // 추가 (검색분야, 검색어) String
		String search = request.getParameter("search");
		String searchtext = request.getParameter("searchtext");
		
		
		
		int count = 0;// 총레코드수
		List noticeList = null;// 화면에 출력할 레코드를 저장할 변수

		NoticeDAO dbPro = new NoticeDAO();
		count = dbPro.getNoticeCount(search, searchtext); // sql구문에 따라서 달라진다.
		System.out.println("현재 검색된 레코드수(count)=>" + count);

		Hashtable<String, Integer> pgList = dbPro.pageList(pageNum, count);

		if (count > 0) {
			System.out.println(pgList.get("startRow") + "," + pgList.get("endRow"));
			noticeList = dbPro.getNotice(pgList.get("startRow"), pgList.get("pageSize"), search, searchtext); // 첫번째
																												// 레코드번호,불러올갯수
			// endRow(X)
		} else {
			noticeList = Collections.EMPTY_LIST; // 아무것도 없는 빈 list 객체 반환
		}

		// 2. request 객체에 저장

		request.setAttribute("role", role);
		request.setAttribute("search", search); // 검색분야
		request.setAttribute("searchtext", searchtext); // 검색어
		request.setAttribute("pgList", pgList); // 페이징처리 10개정보
		request.setAttribute("noticeList", noticeList); // ${articleList}

		// 3. 공유해서 이동할 수 있도록 설정
		return "/notice/notice.jsp"; // /board/list.jsp request.getAttribute("currentPage")=${currentPage}
	}

}
