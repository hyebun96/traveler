package com.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.notice.NoticeDAO;
import com.notice.NoticeDTO;
import com.util.MyServlet;

@WebServlet("/main.do")
public class MainServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		
		if(uri.indexOf("main.do")!=-1) {
			//forward(req, resp, "/WEB-INF/views/main/main.jsp");
			mainNotice(req, resp);
		}
		
	}
	
	
	protected void mainNotice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeDAO dao = new NoticeDAO();
		String cp = req.getContextPath();
		List<NoticeDTO> list = dao.importantList();
		
		String articleUrl = cp+"/notice/viewNotice.do";
		
		for (NoticeDTO dto : list) {
			dto.setCreated(dto.getCreated().substring(0, 10));
		}
		
		req.setAttribute("list", list);
		req.setAttribute("articleUrl", articleUrl);
		forward(req, resp, "/WEB-INF/views/main/main.jsp");
		
	}

}
