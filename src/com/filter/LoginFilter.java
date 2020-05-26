package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;

@WebFilter("/*")
public class LoginFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		if(info == null && isExclideUri(req)==false) {
			String path = "/WEB-INF/views/member/login.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(path);
			rd.forward(request, response);
			return;
		}
		
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	private boolean isExclideUri(HttpServletRequest req) {
		String uri = req.getRequestURI();
		String cp = req.getContextPath();
		uri = uri.substring(cp.length());
		
		String [] uris = {
				"/index.jsp", "/main.do",
				"/board/board.do", "/member/login.do", "/member/login_ok.do", "/member/member.do", "/member/member_ok.do", 
				"/notice/notice.do","/qna/list.do", "/contact/contact.do","/contact/contact_ok.do","/resource/**",
				"/travel/list.do", "/uploads/travel/**"
		};
		
		if(uri.length()<=1) {
			return true;
		}
		
		for(String s: uris) {
			if(s.lastIndexOf("/**") != -1) {
				s = s.substring(0, s.lastIndexOf("**"));
				if(uri.indexOf(s) == 0) {
					return true;
				}
			}else if(uri.equals(s)) {
				return true;
			}
		}
		
		return false;
	}

}
