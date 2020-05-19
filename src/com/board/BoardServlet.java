package com.board;

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

@WebServlet("/board/*")
public class BoardServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(req, resp);
	}
	
	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path)throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);	
	}
	protected void process(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		
		String uri = req.getRequestURI();
		
		if(uri.indexOf("board.do") != -1) {
			board(req, resp);
		}else if(uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		}else if(uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		}else if(uri.indexOf("viewBoard.do") != -1) {
			viewBoard(req, resp);
		}else if(uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		}else if(uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		}else if(uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		}
	}
	
	protected void board(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		BoardDAO dao = new BoardDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		int current_page = 1;
		if(page != null) {
			current_page = Integer.parseInt(page);
		}
		
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="title";
			keyword="";
		}
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword=URLDecoder.decode(keyword, "utf-8");
		}
		
		int dataCount;
		if(keyword.length() ==0) {
			dataCount = dao.dataCount();
		}else {
			dataCount = dao.dataCount(condition, keyword);
		}
		
		int rows=10;
		int total_page=util.pageCount(rows, dataCount);
		if(current_page>total_page)
			current_page=total_page;
		
		int offset=(current_page-1)*rows;
		
		List<BoardDTO> list = null;
		if(keyword.length() == 0) {
			list = dao.allBoard(offset, rows);
		}else {
			list = dao.allBoard(offset, rows, condition, keyword);
		}
		
		int listNum, n = 0;
		for(BoardDTO dto : list) {
			listNum = dataCount-(offset+n);
			dto.setListNum(listNum);
			n++;
		}
		String query="";
		if(keyword.length()!=0) {
			query="condition="+condition+ "&keyword="+URLEncoder.encode(keyword, "utf-8");
		}
		
		// 페이징 처리
		String listUrl=cp+"/board/list.do";
		String articleUrl=cp+"/board/viewBoard.do?page="+current_page;
		if(query.length()!=0) {
			listUrl+="?"+query;
			articleUrl+="&"+query;
		}
				
		String paging = util.paging(current_page, total_page, listUrl);
				
		req.setAttribute("list", list);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("paging", paging);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
				
		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/views/board/list.jsp");
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
			
	}
	
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
			
	}
	
	protected void viewBoard(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
			
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
			
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
			
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
			
	}
	
}
