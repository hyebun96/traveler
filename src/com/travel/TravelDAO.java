package com.travel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class TravelDAO {
	private Connection conn=DBConn.getConnection();
	
	// 게시물 갯수
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*),0) FROM travel ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	// 검색후 게시물 갯수 
	public int dataCount(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*),0) FROM travel t JOIN member m ON t.userId = m.userId ";
			
			if(condition.equals("userName"))sql+= "WHERE INSTR(userName, ?) = 1 ";
			else if(condition.equals("created")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql+=  "WHERE TO_CHAR(created,'MM DD, YYYY') = ?";
			}else sql += "WHERE INSTR("+condition+",?) >= 1";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}

	public List<TravelDTO> listTravel() {
		List<TravelDTO> list = new ArrayList<TravelDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT travelNum, place, information, t.userid, username, imageFilename, ");
			sb.append(" TO_CHAR(created, 'MM DD, YYYY') created ");
			sb.append(" FROM travel t JOIN member m ON t.userId = m.userId ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				TravelDTO dto = new TravelDTO();
				dto.setNum(rs.getInt("travelNum"));
				dto.setPlace(rs.getString("place"));
				dto.setInfomation(rs.getString("information"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("username"));				
				dto.setImageFilename(rs.getString("imageFilename"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return list;
	}
	
	public List<TravelDTO> listTravel(String condition, String keyword) {
		List<TravelDTO> list = new ArrayList<TravelDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT travelNum, place, information, t.userid, imageFilename ");
			sb.append(" TO_CHAR(created, 'MM DD, YYYY') created ");
			sb.append(" FROM travel t ");
			sb.append(" JOIN member m ON t.userId = m.userId ");
			if(condition.equalsIgnoreCase("created")) {
				keyword=keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" WHERE TO_CHAR(created, 'YYYYMMDD') = ?   ");
			}else if(condition.equalsIgnoreCase("userid")) {
				sb.append(" WHERE INSTR(userid,?) > 0 ");
			}else {
				sb.append(" WHERE INSTR("+condition+",?) >= 1 ");
			}
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, keyword);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				TravelDTO dto = new TravelDTO();
				dto.setNum(rs.getInt("travelNum"));
				dto.setPlace(rs.getString("place"));
				dto.setInfomation(rs.getString("information"));
				dto.setUserId(rs.getString("userId"));
				dto.setImageFilename(rs.getString("imageFilename"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return list;
	}
}
