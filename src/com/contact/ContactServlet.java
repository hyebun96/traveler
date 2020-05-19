package com.contact;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/contact/*")
public class ContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	
	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}
	
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri =req.getRequestURI();
		
		if(uri.indexOf("contact.do") != -1) {
			// 컨택트 글쓰기 폼
			contactForm(req, resp);
		} else if(uri.indexOf("contact_ok.do") != -1) {
			// 컨택트 글 등록
			contactSubmit(req, resp);			
		} else if(uri.indexOf("list.do") != -1) {
			// 컨택트리스트
			list(req, resp);
		} else if(uri.indexOf("view.do") != -1) {
			// 글 보기
			contactView(req, resp);
		} else if(uri.indexOf("delete.do") != -1) {
			// 글 삭제
			delete(req, resp);
		}
		
	}
	
	protected void contactForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forward(req, resp, "/WEB-INF/views/contact/contact.jsp");
	}
	
	protected void contactSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		
		ContactDAO dao = new ContactDAO();
		ContactDTO dto = new ContactDTO();
		
		dto.setCtName(req.getParameter("ctName"));
		dto.setCtSubject(req.getParameter("ctSubject"));
		dto.setCtContent(req.getParameter("ctContent"));
		dto.setCtSort(req.getParameter("ctSort"));
		dto.setCtTel(req.getParameter("ctTel"));
		dto.setCtEmail(req.getParameter("ctEmail"));
		
		dao.insertContact(dto);
		
		resp.sendRedirect(cp+"/contact/contact.do");
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void contactView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	

}
