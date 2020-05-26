package com.member;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.notice.NoticeDTO;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/member/*")

public class MemberServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;
	
	private String pathname;


	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
				
		HttpSession session=req.getSession();
		
		// 이미지를 저장할 경로(pathname)
		String root = session.getServletContext().getRealPath("/");
     	pathname = root + "uploads" + File.separator + "travel";
		
		
		String uri=req.getRequestURI();
		if(uri.indexOf("login.do")!=-1) {
			loginForm(req, resp);
		} else if(uri.indexOf("login_ok.do")!=-1) {
			loginSubmit(req, resp);
		} else if(uri.indexOf("logout.do")!=-1) {
			logout(req, resp);
		} else if(uri.indexOf("member.do")!=-1) {
			memberForm(req, resp);
		} else if(uri.indexOf("member_ok.do")!=-1) {
			memberSubmit(req, resp);
		} else if(uri.indexOf("pwd.do")!=-1) {
			pwdForm(req, resp);
		} else if(uri.indexOf("pwd_ok.do")!=-1) {
			pwdSubmit(req, resp);
		} else if(uri.indexOf("update.do")!=-1) {
			updateForm(req,resp);
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("delete.do")!=-1) {
			deleteSubmit(req, resp);
		} else if(uri.indexOf("myPage.do")!=-1) {
			myPage(req, resp);
		} else if(uri.indexOf("list.do")!=-1) {
			listForm(req, resp);
		}
	}
//로그인 폼
	private void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		String path="/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
	}
	
//로그인 검증	
	private void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		
		MemberDAO dao=new MemberDAO();
		
		String userId=req.getParameter("userId");
		String userPwd=req.getParameter("userPwd");
		
		MemberDTO dto=dao.readMember(userId);
		
		if(dto==null || !dto.getUserPwd().equals(userPwd)) {
			String s="아이디 또는 패스워드가 일치하지 않습니다. 다시 입력해주세요.";
			req.setAttribute("messege", s);
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		//로그인 성공
		HttpSession session = req.getSession();
		
		//세션 유지시간 30분
		session.setMaxInactiveInterval(30*60);
		
		//세션에 로그인 정보 저장
		SessionInfo info=new SessionInfo();
		info.setUserId(dto.getUserId());
		info.setUserName(dto.getUserName());
		
		session.setAttribute("member", info);
	
		resp.sendRedirect(cp);//첫화면으로 돌아가라ㅏㅏㅏㅏㅏㅏ
	}
	
//로그아웃
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		
		session.invalidate();
		
		resp.sendRedirect(cp);
	}
	
//회원가입폼	
	private void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("title", "Sign up");
		req.setAttribute("mode", "created");
		
		forward(req, resp, "/WEB-INF/views/member/member.jsp");
	}
	
//회원가입 완료
	private void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO dao=new MemberDAO();
		MemberDTO dto = new MemberDTO();
		String cp=req.getContextPath();
		
		dto.setUserId(req.getParameter("userId"));
		dto.setUserPwd(req.getParameter("userPwd"));
		dto.setUserName(req.getParameter("userName"));
		
		String tel1 = req.getParameter("tel1");
		String tel2 = req.getParameter("tel2");
		String tel3 = req.getParameter("tel3");	
		if (tel1.length() != 0 && tel2.length() != 0 && tel3.length() != 0) {
			dto.setUserTel(tel1 + "-" + tel2 + "-" + tel3);
		}
		
		String email1 =req.getParameter("email1");
		String email2 =req.getParameter("email2");		
		if (email1.length() != 0 && email2.length() != 0) {
			dto.setUserEmail(email1 + "@" + email2);
		}
		
		dto.setUserBirth(req.getParameter("userBirth"));
	
		Part p = req.getPart("upload"); 
		Map<String, String> map = doFileUpload(p,pathname);
		  
		 // map이 null이면 던져야함 파일이 없는 것임으로 
		if(map!=null) { 
			String saveFilename = map.get("saveFilename");
			if(saveFilename!=null) {
				dto.setImageFilename(saveFilename);
			}	  
		}
		
		try {
			dao.insertMember(dto);
		}catch(Exception e){
			String message = "회원 가입이 실패 했습니다.";
						
			req.setAttribute("title", "Sign up");
			req.setAttribute("mode", "created");	
			req.setAttribute("message", message);
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
					  
			return;
		}
		
		resp.sendRedirect(cp+"/member/main.do");
	}		 

		
// 패스워드 확인 폼	
	private void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session=req.getSession();
		String cp=req.getContextPath();
				
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		// 로그아웃상태이면
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
				
		String mode=req.getParameter("mode");
		if(mode.equals("update")) {
			req.setAttribute("title", "회원 정보 수정");
		}else {
			req.setAttribute("title", "회원 탈퇴");
		}
		
		req.setAttribute("mode", mode);
		forward(req, resp, "/WEB-INF/views/member/pwd.jsp");	
	}
	
//패스워드확인	
	private void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		String cp=req.getContextPath();
		MemberDAO dao=new MemberDAO();
		
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) { //로그아웃 된 경우
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		// DB에서 해당 회원 정보 가져오기
		MemberDTO dto=dao.readMember(info.getUserId());
		if(dto==null) {
			session.invalidate();
			resp.sendRedirect(cp);
			return;
		}
		String userPwd=req.getParameter("userPwd");
		String mode=req.getParameter("mode");
		
		if(! dto.getUserPwd().equals(userPwd)) {
			if(mode.equals("update")) {
				req.setAttribute("title", "회원 정보 수정");
			}else {
				req.setAttribute("title", "회원 탈퇴");
			}
			req.setAttribute("mode", mode);
			req.setAttribute("message","<span style='color:red;'>패스워드가 일치하지 않습니다.</span>");
			forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
			return;
		}else {
			myPage(req,resp);
		}
		
		if(mode.equals("delete")) {
			// 회원탈퇴
			try {
				dao.deleteMember(info.getUserId());
			} catch (Exception e) {
			}
			
			session.removeAttribute("member");
			session.invalidate();
			
			resp.sendRedirect(cp);
			
			return;
		}
	}	
	
//회원정보수정	
	private void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//세션 이용해서 회원정보 검색하기 (DAO)
		// 회원정보수정 - 회원수정폼으로 이동
		HttpSession session=req.getSession();
		MemberDAO dao=new MemberDAO();
		String cp=req.getContextPath();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		if(info==null) { //로그아웃 된 경우
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		// DB에서 해당 회원 정보 가져오기
		MemberDTO dto=dao.readMember(info.getUserId());
		if(dto==null) {
			session.invalidate();
			resp.sendRedirect(cp);
			return;
		}
			
		req.setAttribute("title", "회원 정보 수정");
		req.setAttribute("dto", dto);
		req.setAttribute("mode", "update");

		forward(req, resp, "/WEB-INF/views/member/member.jsp");
	}
	
	
//회원정보 수정완료	
	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		String cp=req.getContextPath();
		MemberDAO dao=new MemberDAO();
		
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) { //로그아웃 된 경우
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		MemberDTO dto = new MemberDTO();
	
		dto.setUserId(req.getParameter("userId"));
		dto.setUserPwd(req.getParameter("userPwd"));
		dto.setUserName(req.getParameter("userName"));
		String tel1 = req.getParameter("tel1");
		String tel2 = req.getParameter("tel2");
		String tel3 = req.getParameter("tel3");
		if (tel1.length() != 0 && tel2.length() != 0 && tel3.length() != 0) {
			dto.setUserTel(tel1 + "-" + tel2 + "-" + tel3);
		}
		String email1 =req.getParameter("email1");
		String email2 =req.getParameter("email2");
		if (email1.length() != 0 && email2.length() != 0) {
			dto.setUserEmail(email1 + "@" + email2);
		}
		dto.setUserBirth(req.getParameter("userBirth"));

	//파일	

		dto.setUserId(info.getUserId());
		
		Part p =req.getPart("upload");
		Map<String, String> map = doFileUpload(p, pathname);
		if(map!=null) {
			// 기존 파일 삭제
			if(req.getParameter("imageFilename").length()!=0) {
				FileManager.doFiledelete(pathname, req.getParameter("imageFilename"));
			}
			
			//새로운 파일
			String saveFilename = map.get("saveFilename");
			dto.setImageFilename(saveFilename);
		}
		
		try {
			dao.updateMember(dto);
			resp.sendRedirect(cp+"/member/myPage.do");
		} catch (Exception e) {
			String message = "회원 수정이 실패 했습니다.";
			
			dto=dao.readMember(info.getUserId());
			if(dto==null) {
				session.invalidate();
				resp.sendRedirect(cp);
				return;
			}
				
			req.setAttribute("title", "회원 정보 수정");
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			req.setAttribute("message", message);
			
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
		}
	}
//회원탈퇴	
	private void deleteSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		MemberDAO dao=new MemberDAO();
		String cp=req.getContextPath();
		
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) { //로그아웃 된 경우
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		// DB에서 해당 회원 정보 가져오기
		MemberDTO dto=dao.readMember(info.getUserId());
		if(dto==null) {
			session.invalidate();
			resp.sendRedirect(cp);
			return;
		}
			try {
				dao.deleteMember(info.getUserId());
			} catch (Exception e) {
			}
			
			session.removeAttribute("member");
			session.invalidate();
			
			resp.sendRedirect(cp);
			
			return;
		//}
	}
//myPage(내정보 폼)	
	private void myPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		MemberDAO dao=new MemberDAO();
		String cp = req.getContextPath();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) { //로그아웃 된 경우
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		// DB에서 해당 회원 정보 가져오기
		MemberDTO dto = dao.readMember(info.getUserId());
		
		if(dto==null) {
			session.invalidate();
			resp.sendRedirect(cp);
			return;
		}
		
		req.setAttribute("title", "MyPage");
		 
		req.setAttribute("dto", dto);	

		forward(req, resp, "/WEB-INF/views/member/myPage.jsp");
	}
//회원리스트
	private void listForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		HttpSession session=req.getSession();
		MemberDAO dao = new MemberDAO();
		String cp=req.getContextPath();
		MyUtil myUtil=new MyUtil();
		
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) { //로그아웃 된 경우
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String page=req.getParameter("page");
		int current_page=1;
		if(page!=null) {
			current_page=Integer.parseInt(page);
		}
	
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {  //condition가 null일때 keyword가 검색이 아님...
			condition="userId";
			keyword="";
		} 
		//검색버튼 누르면 Post로 넘어옴
		
		//인코딩을 해야하니까 디코딩을 해줌
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword=URLDecoder.decode(keyword,"utf-8");
		}
		
		int dataCount;
		if(keyword.length()==0) {
			dataCount=dao.dataCount();
		}else {
			dataCount=dao.dataCount(condition,keyword);
		}
		
		int rows=10;
		int total_page=myUtil.pageCount(rows, dataCount);
		if(current_page>total_page) {
			current_page=total_page;
		}
		
		int offset = (current_page - 1) * rows;
	
		List<MemberDTO> list;
		if (keyword.length() != 0)
			list = dao.listBoard(offset, rows, condition, keyword);
		else
			list = dao.listBoard(offset, rows);
		
		// 게시물 번호를 재정의, 시퀀스가 삭제되면 중구난방이라서
		String query="";
		if(keyword.length()!=0) {
			query="condition="+condition+"&keyword="+URLEncoder.encode(keyword,"utf-8");
		}
		//페이징처리
		String listUrl=cp+"/member/list.do";
		if (query.length() != 0) {
			listUrl += "?" + query;
		}
		
		String paging = myUtil.paging(current_page, total_page, listUrl);
		
		// DB에서 해당 회원 정보 가져오기
		MemberDTO dto=dao.readMember(info.getUserId());
		if(dto==null) {
			session.invalidate();
			resp.sendRedirect(cp);
			return;
		}
		
		//list.jsp에 넘겨줄 데이터
		
		req.setAttribute("dto", dto);	
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		req.setAttribute("page", current_page);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		
		forward(req, resp, "/WEB-INF/views/member/list.jsp");
	}
	
}
