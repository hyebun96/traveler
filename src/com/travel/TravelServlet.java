package com.travel;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.member.SessionInfo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.util.FileManager;
import com.util.MyUploadServlet;

@WebServlet("/travel/*")
@MultipartConfig
public class TravelServlet extends MyUploadServlet {
	
	private static final long serialVersionUID = 1L;
	private String pathname;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();

		HttpSession session = req.getSession();

		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "travel";

		if (uri.indexOf("seoul.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("created.do") != -1) {
			createdForm(req, resp);
		} else if (uri.indexOf("created_ok.do") != -1) {
			createdSubmit(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if (uri.indexOf("deleteFile.do") != -1) {
			deleteFile(req, resp);
		} else if(uri.indexOf("like.do") != -1){
			like(req,resp);
		}

	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TravelDAO dao = new TravelDAO();
		String cp = req.getContextPath();

		// 검색
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");

		if (condition == null) {
			condition = "subject";
			keyword = "";
		}

		if (req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "utf-8");
		}

		// 오늘 날짜 계산
		Date nowTime = new Date();
		SimpleDateFormat day = new SimpleDateFormat("MM dd, yyyy");
		String date = day.format(nowTime);

		int dataCount;
		List<TravelDTO> list = null;

		if (keyword.length() != 0)
			dataCount = dao.dataCount(condition, keyword);
		else
			dataCount = dao.dataCount();

		if (keyword.length() != 0)
			list = dao.listTravel(condition, keyword);
		else
			list = dao.listTravel();

		String query = "";
		String listUrl = "";
		String articleUrl = "";

		listUrl = cp + "/travel/list.do";
		articleUrl = cp + "/travel/article.do";

		if (keyword.length() != 0) {
			query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");

			listUrl += "?" + query;
			articleUrl += "?" + query;
		}

		// 포워딩 jsp에 넘길 데이터
		req.setAttribute("list", list);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("listUrl", listUrl);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		req.setAttribute("date", date);

		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/views/travel/list.jsp");

	}

	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		if(!info.getUserId().equals("admin")) {
			resp.sendRedirect(req.getContextPath()+"/travel/seoul.do");
			return;
		}
		
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/travel/created.jsp");
	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		String cp = req.getContextPath();

		HttpSession session=req.getSession(); 
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		if(!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp+"/travel/seoul.do"); return; 
		}
		 
		TravelDTO dto = new TravelDTO();
		TravelDAO dao = new TravelDAO();

		dto.setUserId(info.getUserId());
		
		dto.setPlace(req.getParameter("place"));
		dto.setInformation(req.getParameter("information"));
		
		dao.insertTravel(dto);
		
		List<Part> list = new ArrayList<Part>();
		
		for (Part part : req.getParts()) {
        	list.add(part);
        }
		
		Map<String, String[]> map = doFileUpload(list, pathname);
		if (map != null) {
			String[] saveFilenames = map.get("saveFilenames");
			
			for(String s : saveFilenames) {
				dto.setImageFilename(s);				
				dao.insertImage(dto);
			}
			
		}

		
		resp.sendRedirect(cp + "/travel/seoul.do");
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		TravelDAO dao = new TravelDAO();
		String cp = req.getContextPath();

		int num = Integer.parseInt(req.getParameter("num"));

		TravelDTO dto = dao.readTravel(num);

		if (dto == null) {
			resp.sendRedirect(cp + "/travel/seoul.do");
			return;
		}
		
		if(!info.getUserId().equals(dto.getUserId())){
			resp.sendRedirect(cp+"/travel/seoul.do");
			return;
		}

		req.setAttribute("dto", dto);
		req.setAttribute("mode", "update");

		forward(req, resp, "/WEB-INF/views/travel/created.jsp");

	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TravelDAO dao = new TravelDAO();
		String cp = req.getContextPath();

		TravelDTO dto = new TravelDTO();

		int num = Integer.parseInt(req.getParameter("num"));

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/travel/seoul.do");
		}

		dto.setNum(num);
		dto.setPlace(req.getParameter("place"));
		dto.setInformation(req.getParameter("information"));
		dto.setUserName(req.getParameter("name"));
		dto.setImageFilename(req.getParameter("imageFilename"));

		Part p = req.getPart("upload");
		Map<String, String> map = doFileUpload(p, pathname);
		if (map != null) {
			if (req.getParameter("imageFilename").length() != 0 && req.getParameter("imageFilename") != null) {
				FileManager.doFiledelete(pathname, req.getParameter("imageFilename"));
			}

			String imageFilename = map.get("saveFilename");
			dto.setImageFilename(imageFilename);
		}

		dao.updateTravel(dto);

		resp.sendRedirect(cp + "/travel/seoul.do");

	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		TravelDAO dao = new TravelDAO();
		String cp = req.getContextPath();

		int num = Integer.parseInt(req.getParameter("num"));

		TravelDTO dto = dao.readTravel(num);

		if (dto == null) {
			resp.sendRedirect(cp + "/travel/seoul.do");
			return;
		}
		
		if(! info.getUserId().equals(dto.getUserId())) {
			resp.sendRedirect(cp+"/travel/seoul.do?");
			return;
		}
		

		if (dto.getImageFilename() != null && dto.getImageFilename().length() != 0)
			FileManager.doFiledelete(pathname, dto.getImageFilename());

		dao.deleteTravel(num);

		resp.sendRedirect(cp + "/travel/seoul.do");

	}

	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void like(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		TravelDAO dao = new TravelDAO();
		
		int num = Integer.parseInt(req.getParameter("num"));
		
		dao.likeInsert(num);
		
		resp.sendRedirect(cp + "/travel/seoul.do");
	}

}
