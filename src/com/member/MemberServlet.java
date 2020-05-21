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
//稽益昔 廿
	private void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		String path="/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
	}
	
//稽益昔 伊装	
	private void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		
		MemberDAO dao=new MemberDAO();
		
		String userId=req.getParameter("userId");
		String userPwd=req.getParameter("userPwd");
		
		MemberDTO dto=dao.readMember(userId);
		
		if(dto==null || !dto.getUserPwd().equals(userPwd)) {
			String s="焼戚巨 暁澗 鳶什趨球亜 析帖馬走 省柔艦陥. 陥獣 脊径背爽室推.";
			req.setAttribute("messege", s);
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		//稽益昔 失因
		HttpSession session = req.getSession();
		
		//室芝 政走獣娃 30歳
		session.setMaxInactiveInterval(30*60);
		
		//室芝拭 稽益昔 舛左 煽舌
		SessionInfo info=new SessionInfo();
		info.setUserId(dto.getUserId());
		info.setUserName(dto.getUserName());
		
		session.setAttribute("member", info);
	
		resp.sendRedirect(cp);//湛鉢檎生稽 宜焼亜虞たたたたたた
	}
	
//稽益焼数
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		
		session.invalidate();
		
		resp.sendRedirect(cp);
	}
	
//噺据亜脊廿	
	private void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("title", "噺据 亜脊");
		req.setAttribute("mode", "created");
		
		forward(req, resp, "/WEB-INF/views/member/member.jsp");
	}
	
//噺据亜脊伊装	
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
			String message = "噺据 亜脊戚 叔鳶 梅柔艦陥.";
			
			req.setAttribute("title", "噺据 亜脊");
			req.setAttribute("mode", "created");
			req.setAttribute("message", message);
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;
		}
		String cp=req.getContextPath();
		resp.sendRedirect(cp);				
	}
	
// 鳶什趨球 溌昔 廿	
	private void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session=req.getSession();
		String cp=req.getContextPath();
				
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		// 稽益焼数雌殿戚檎
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
				
		String mode=req.getParameter("mode");
		if(mode.equals("update")) {
			req.setAttribute("title", "噺据 舛左 呪舛");
		}else {
			req.setAttribute("title", "噺据 纏盗");
		}
		
		req.setAttribute("mode", mode);
		forward(req, resp, "/WEB-INF/views/member/pwd.jsp");	
	}
//鳶什趨球溌昔	
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
				 req.setAttribute("title", "噺据舛左 呪舛");
			}else {
				req.setAttribute("title", "噺据纏盗");
			}
			req.setAttribute("mode", mode);
			req.setAttribute("message", 	"<span style='color:red;'>鳶什趨球亜 析帖馬走 省柔艦陥.</span>");
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
		 
		 //噺据舛左 呪舛
		 req.setAttribute("title", "噺据舛左 呪舛");
		 req.setAttribute("dto", dto);
		 req.setAttribute("mode", "update");
		 forward(req, resp, "/WEB-INF/views/member/member.jsp");
		 
	}
//噺据舛左 呪舛刃戟	
	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		String cp=req.getContextPath();
		MemberDAO dao=new MemberDAO();
		
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) { //稽益焼数 吉 井酔
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
