package com.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/member/*")

public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req, resp);
	}

	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path)
			throws ServletException, IOException {
		RequestDispatcher rd=req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}
	
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
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
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("userIdCheck.do")!=-1) {
			userIdCheck(req, resp);
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
		req.setAttribute("title", "회원 가입");
		req.setAttribute("mode", "created");
		
		forward(req, resp, "/WEB-INF/views/member/member.jsp");
	}
	
//회원가입검증	
	private void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO dao=new MemberDAO();
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
		
		try {
			dao.insertMember(dto);
		} catch (Exception e) {
			String message = "회원 가입이 실패 했습니다.";
			
			req.setAttribute("title", "회원 가입");
			req.setAttribute("mode", "created");
			req.setAttribute("message", message);
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;
		}
		String cp=req.getContextPath();
		resp.sendRedirect(cp);				
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
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		 MemberDTO dto=dao.readMember(info.getUserId());
		 if(dto==null) {
			 session.invalidate();
			 resp.sendRedirect(cp);
			 return;
		 }
		 String userPwd=req.getParameter("userPwd");
		 String mode=req.getParameter("mode");
		 if(!dto.getUserPwd().equals(userPwd)) {
			if(mode.equals("update")) {
				 req.setAttribute("title", "회원정보 수정");
			}else {
				req.setAttribute("title", "회원탈퇴");
			}
			req.setAttribute("mode", mode);
			req.setAttribute("message", 	"<span style='color:red;'>패스워드가 일치하지 않습니다.</span>");
			forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
			return;
		 }
		 if(mode.equals("delete")) {
			 try {
				 dao.deleteMember(info.getUserId());
			 } catch(Exception e) {
			 }
			 session.removeAttribute("member");
			 session.invalidate();
			 
			 resp.sendRedirect(cp);
			 
			 return;
		 }
		 
		 //회원정보 수정
		 req.setAttribute("title", "회원정보 수정");
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
		dto.setUserPwd(req.getParameter("userName"));
		String Tel1 = req.getParameter("Tel1");
		String Tel2 = req.getParameter("Tel2");
		String Tel3 = req.getParameter("Tel3");
		if (Tel1.length() != 0 && Tel2.length() != 0 && Tel3.length() != 0) {
			dto.setUserTel(Tel1 + "-" + Tel2 + "-" + Tel3);
		}
		String Email1 =req.getParameter("Eamil1");
		String Email2 =req.getParameter("Eamil2");
		if (Email1.length() != 0 && Email2.length() != 0) {
			dto.setUserEmail(Email1 + "@" + Email2);
		}
		dto.setUserBirth(req.getParameter("birth"));
		
		try {
			dao.updateMember(dto);
		} catch (Exception e) {
		}
		resp.sendRedirect(cp);
	}
	private void userIdCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
