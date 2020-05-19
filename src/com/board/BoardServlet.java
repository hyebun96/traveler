package com.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
