package com.contact;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyUtil;

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
			// ����Ʈ �۾��� ��
			contactForm(req, resp);
		} else if(uri.indexOf("contact_ok.do") != -1) {
			// ����Ʈ �� ���
			contactSubmit(req, resp);			
		} else if(uri.indexOf("list.do") != -1) {
			// ����Ʈ����Ʈ
			list(req, resp);
		} else if(uri.indexOf("view.do") != -1) {
			// �� ����
			contactView(req, resp);
		} else if(uri.indexOf("update.do")!=-1) {
			// �ϷῩ�� ����
			finishSubmit(req, resp);
		} else if(uri.indexOf("delete.do") != -1) {
			// �� ����
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
		ContactDAO dao = new ContactDAO();
		MyUtil util = new MyUtil();
		
		// ���� ����Ʈ
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		int current_page=1;
		if(page!=null) {
			current_page=Integer.parseInt(page);
		}
		
		// �˻�
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="subject";
			keyword="";
		}
				
		// GET ����� ��� ���ڵ�
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword=URLDecoder.decode(keyword, "utf-8");
		}
				
		// ��ü ������ ����
		int dataCount;
		if(keyword.length()==0)
			dataCount=dao.dataCount();
		else
			dataCount=dao.dataCount(condition, keyword);
				
		// ��ü ������ ��
		int rows=20;
		int total_page=util.pageCount(rows, dataCount);
		if(current_page>total_page)
			current_page=total_page;
				
		int offset=(current_page-1)*rows;
		
		// �Խù� ��������
		
		List<ContactDTO> list = null;
		if(keyword.length()==0) {
			list=dao.listContact(offset, rows);
		} else {
			list=dao.listContact(offset, rows, condition, keyword);
		}
		
		String query="";
		if(keyword.length()!=0) {
			query="condition="+condition+ "&keyword="+URLEncoder.encode(keyword, "utf-8");
		}
		
		String listUrl =cp+"/contact/list.do";
		String viewUrl=cp+"/contact/view.do?page="+current_page;
		if(query.length()!=0) {
			listUrl+="?"+query;
			viewUrl+="&"+query;
		}
		
		String paging = util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("viewUrl", viewUrl);
		req.setAttribute("paging", paging);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		
		forward(req, resp, "/WEB-INF/views/contact/list.jsp");
	}
	
	protected void contactView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ContactDAO dao = new ContactDAO();
		String cp = req.getContextPath();
		
		int ctNum = Integer.parseInt(req.getParameter("ctNum"));
		String page= req.getParameter("page");
		String rows= req.getParameter("rows");
		
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="subject";
			keyword="";
		}
		keyword=URLDecoder.decode(keyword, "utf-8");
		
		String query="page="+page+"&rows="+rows;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
		}
		
		ContactDTO dto=dao.readContact(ctNum);
		if(dto==null) {
			resp.sendRedirect(cp+"/contact/list.do?"+query);
			return;
		}
		dto.setCtContent(dto.getCtContent().replaceAll("\n", "<br>"));
		
		req.setAttribute("dto", dto);
		req.setAttribute("query", query);
		req.setAttribute("page", page);
		req.setAttribute("rows", rows);
		
		forward(req, resp, "/WEB-INF/views/contact/view.jsp");
	}
	
	protected void finishSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ContactDAO dao = new ContactDAO();
		ContactDTO dto = new ContactDTO();
		String cp = req.getContextPath();
	
		String page=req.getParameter("page");
		String rows=req.getParameter("rows");
		
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="subject";
			keyword="";
		}
		keyword=URLDecoder.decode(keyword, "utf-8");
		
		String query="page="+page+"&rows="+rows;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
		}
		
		
		int ctNum=Integer.parseInt(req.getParameter("ctNum"));
		dto.setCtNum(ctNum);

		dao.updateContact(ctNum);
		
		
		resp.sendRedirect(cp+"/contact/list.do?"+query);

	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ContactDAO dao = new ContactDAO();
		String cp = req.getContextPath();
		
		int ctNum=Integer.parseInt(req.getParameter("ctNum"));		
		String page=req.getParameter("page");
		String rows=req.getParameter("rows");
		
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="subject";
			keyword="";
		}
		keyword=URLDecoder.decode(keyword, "utf-8");
		
		String query="page="+page+"&rows="+rows;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
		}
		
		dao.deleteContact(ctNum);
		
		resp.sendRedirect(cp+"/contact/list.do?"+query);

	}
	
	

}