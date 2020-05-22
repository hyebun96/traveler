package com.notice;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@WebServlet("/notice/*")
@MultipartConfig
public class NoticeServlet extends MyUploadServlet {

	private static final long serialVersionUID = 1L;
	private String pathname;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();

		HttpSession session = req.getSession();

		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "notice";

		if (uri.indexOf("notice.do") != -1) {
			notice(req, resp);
		} else if (uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if (uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if (uri.indexOf("viewNotice.do") != -1) {
			viewNotice(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if (uri.indexOf("download.do") != -1) {
			download(req, resp);
		} else if (uri.indexOf("deleteFile.do") != -1) {
			deleteFile(req, resp);
		} else if (uri.indexOf("access.do") != -1) {
			access(req, resp);
		}
	}

	protected void notice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeDAO dao = new NoticeDAO();
		MyUtil util = new MyUtil();

		String cp = req.getContextPath();

		String page = req.getParameter("page");
		int current_page = 1;
		if (page != null) {
			current_page = Integer.parseInt(page);
		}

		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if (condition == null) {
			condition = "title";
			keyword = "";
		}

		if (req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "utf-8");
		}

		int dataCount;
		if (keyword.length() == 0) {
			dataCount = dao.dataCount();
		} else {
			dataCount = dao.dataCount(condition, keyword);
		}

		int rows = 5;
		int total_page = util.pageCount(rows, dataCount);
		if (current_page > total_page)
			current_page = total_page;

		int offset = (current_page - 1) * rows;
		List<NoticeDTO> list;
		if (keyword.length() != 0)
			list = dao.listNotice(offset, rows, condition, keyword);
		else
			list = dao.listNotice(offset, rows);

		int listNum, n = 0;
		for (NoticeDTO dto : list) {
			listNum = dataCount - (offset + n);
			dto.setListNum(listNum);
			n++;
		}
		String query = "";
		if (keyword.length() != 0) {
			query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
		}

		// 페이징 처리
		String listUrl = cp + "/notice/notice.do";
		String articleUrl = cp + "/notice/viewNotice.do?page=" + current_page;
		if (query.length() != 0) {
			listUrl += "?" + query;
			articleUrl += "&" + query;
		}

		List<NoticeDTO> importantList = null;
		importantList = dao.importantList();
		for (NoticeDTO dto : importantList) {
			dto.setCreated(dto.getCreated().substring(0, 10));
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
		req.setAttribute("importantList", importantList);

		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/views/notice/list.jsp");

	}

	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setAttribute("mode", "write");

		forward(req, resp, "/WEB-INF/views/notice/write.jsp");
	}

	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		NoticeDAO dao = new NoticeDAO();
		NoticeDTO dto = new NoticeDTO();

		dto.setId(info.getUserId());
		dto.setName(info.getUserName());
		dto.setTitle(req.getParameter("title"));
		dto.setContent(req.getParameter("content"));
		if (req.getParameter("important") != null) {
			dto.setImportant(Integer.parseInt(req.getParameter("important")));
		}
		dao.insertNotice(dto);

		Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
		if (map != null) {
			String[] saves = map.get("saveFilenames");
			String[] originals = map.get("originalFilenames");

			File f;
			if (saves != null) {
				for (int i = 0; i < saves.length; i++) {
					dto.setSaveFileName(saves[i]);
					dto.setOriginalFileName(originals[i]);

					f = new File(pathname + File.separator + saves[i]);
					dto.setFileSize(f.length());

					dao.insertNoticeFile(dto);
				}
			}
		}

		resp.sendRedirect(cp + "/notice/notice.do");
	}

	protected void viewNotice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeDAO dao = new NoticeDAO();
		String cp = req.getContextPath();

		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");

		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if (condition == null) {
			condition = "title";
			keyword = "";
		}
		keyword = URLDecoder.decode(keyword, "utf-8");

		String query = "page=" + page;

		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}

		dao.updateHitCount(num);
		NoticeDTO dto = dao.readNotice(num);
		List<NoticeDTO> list = dao.fileList(num);

		if (dto == null) {
			resp.sendRedirect(cp + "/notice/list.do?" + query);
			return;
		}
		dto.setContent(dto.getContent().replaceAll("\n", "<br>"));

		req.setAttribute("dto", dto);
		req.setAttribute("query", query);
		req.setAttribute("page", page);
		req.setAttribute("list", list);

		forward(req, resp, "/WEB-INF/views/notice/viewNotice.jsp");
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		NoticeDAO dao = new NoticeDAO();
		String cp = req.getContextPath();

		String page = req.getParameter("page");
		int num = Integer.parseInt(req.getParameter("num"));

		NoticeDTO dto = dao.readNotice(num);
		if (dto == null) {
			resp.sendRedirect(cp + "/notice/notice.do?page=" + page);
			return;
		}
		
		List<NoticeDTO> list = dao.fileList(num);
		
		req.setAttribute("list", list);
		req.setAttribute("dto", dto);
		req.setAttribute("mode", "update");
		req.setAttribute("page",page);
		
		forward(req, resp, "/WEB-INF/views/notice/write.jsp");
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeDAO dao=new NoticeDAO();
		String cp=req.getContextPath();
		
		NoticeDTO dto=new NoticeDTO();
		
		int num=Integer.parseInt(req.getParameter("num"));
		String page=req.getParameter("page");
		if (req.getParameter("important") != null) {
			dto.setImportant(Integer.parseInt(req.getParameter("important")));
		}
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/notice/notice.do?page="+page);
			return;
		}
		
		dto.setNum(num);
	    if(req.getParameter("notice")!=null) {
	    	dto.setImportant(Integer.parseInt(req.getParameter("important")));
	    }
		dto.setTitle(req.getParameter("title"));
		dto.setContent(req.getParameter("content"));
		
		dao.updateNotice(dto);
		Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
		if (map != null) {
			String[] saves = map.get("saveFilenames");
			String[] originals = map.get("originalFilenames");

			File f;
			if (saves != null) {
				for (int i = 0; i < saves.length; i++) {
					dto.setSaveFileName(saves[i]);
					dto.setOriginalFileName(originals[i]);

					f = new File(pathname + File.separator + saves[i]);
					dto.setFileSize(f.length());

					dao.insertNoticeFile(dto);
				}
			}
		}
		
		resp.sendRedirect(cp+"/notice/notice.do?page="+page);
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		NoticeDAO dao=new NoticeDAO();
		String cp=req.getContextPath();
		
		int num=Integer.parseInt(req.getParameter("num"));
		String page=req.getParameter("page");
		
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="title";
			keyword="";
		}
		keyword=URLDecoder.decode(keyword, "utf-8");

		String query="page="+page;
		
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
		}
		
		NoticeDTO dto=dao.readNotice(num);
		if(dto==null) {
			resp.sendRedirect(cp+"/notice/list.do?"+query);
			return;
		}
		
		if( ! info.getUserId().equals("admin")) {
			resp.sendRedirect(cp+"/notice/list.do?"+query);
			return;
		}
		
		List<NoticeDTO> list = dao.fileList(num);
		for(NoticeDTO dtoFile : list) {
			dao.deleteNoticeFile(dtoFile.getFileNum());
		}
		
		dao.deleteNotice(num);
		
		resp.sendRedirect(cp+"/notice/notice.do?"+query);
	}

	protected void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeDAO dao = new NoticeDAO();
		int fileNum = Integer.parseInt(req.getParameter("fileNum"));
		NoticeDTO dtofile = dao.readFileNum(fileNum);
		
		boolean b = false;
		if(dtofile != null) {
			b = FileManager.doFiledownload(dtofile.getSaveFileName(),
					dtofile.getOriginalFileName(), pathname, resp);
		}
		
		if(! b) {
			resp.setContentType("text/html; charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print("<script>alert('파일 다운로드가 불가능합니다.'); history.back();</script>");
		}
	}

	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정에서 파일만 삭제
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		NoticeDAO dao=new NoticeDAO();
		String cp=req.getContextPath();
	
		int num=Integer.parseInt(req.getParameter("num"));
		int fileNum = Integer.parseInt(req.getParameter("fileNum"));
		String page=req.getParameter("page");
		
		NoticeDTO dto=dao.readNotice(num); // 게시판번호를이용해서 dto찾기
		if(dto==null) {
			resp.sendRedirect(cp+"/notice/notice.do?page="+page);
			return;
		}
		
		if(! info.getUserId().equals(dto.getId())) {
			resp.sendRedirect(cp+"/notice/notice.do?page="+page);
			return;
		}
		
		NoticeDTO dtofile = dao.readFileNum(fileNum); // 파일번호를 이용해서 dto찾기
		
		// 파일삭제
		FileManager.doFiledelete(pathname, dtofile.getSaveFileName());
		dtofile.setOriginalFileName("");
		dtofile.setSaveFileName("");
		dtofile.setFileSize(0);
		
		
		dao.deleteNoticeFile(fileNum);
		List<NoticeDTO> list = dao.fileList(num);
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		req.setAttribute("list", list);
		req.setAttribute("mode", "update");
		
		forward(req, resp, "/WEB-INF/views/notice/write.jsp");
		
	}

	protected void access(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String page = req.getParameter("page");
		String query = "page=" + page;
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if (condition == null) {
			condition = "title";
			keyword = "";
		}
		keyword = URLDecoder.decode(keyword, "utf-8");
		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}

		req.setAttribute("query", query);

		forward(req, resp, "/WEB-INF/views/notice/access.jsp");
	}

}
