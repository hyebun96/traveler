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
			sb.append("SELECT t.travelNum, place, information, t.userid, username, likeNum, ");
			sb.append("  	TO_CHAR(created, 'MM DD, YYYY') created, img.saveFilename saveFilename  ");
			sb.append("  FROM travel t JOIN member m ON t.userId = m.userId ");
			sb.append("  LEFT OUTER JOIN ( ");
			sb.append("  	SELECT travelNum, LISTAGG(SAVEFILENAME,',') WITHIN GROUP (order by SAVEFILENAME) saveFilename ");
			sb.append(" 	FROM TRAVELFILE f  ");
			sb.append("     GROUP BY travelNum");
			sb.append("   ) img ON t.travelNum = img.travelNum");
			
			
			pstmt = conn.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				TravelDTO dto = new TravelDTO();
				dto.setNum(rs.getInt("travelNum"));
				dto.setPlace(rs.getString("place"));
				dto.setInformation(rs.getString("information"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("username"));	
				dto.setImageFilename(rs.getString("saveFilename"));
				dto.setCreated(rs.getString("created"));
				dto.setLikeNum(rs.getInt("likeNum"));
				
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
			sb.append("SELECT travelNum, place, information, t.userid, username, likeNum, ");
			sb.append("         TO_CHAR(created, 'MM DD, YYYY') created, subquery1.saveFilename saveFilename ");
			sb.append("  FROM travel t JOIN member m ON t.userId = m.userId, ");
			sb.append("   (SELECT LISTAGG(SAVEFILENAME,',') WITHIN GROUP (order by SAVEFILENAME) saveFilename");
			sb.append(" FROM TRAVELFILE ");
			sb.append(" GROUP BY travelNum ");
			sb.append("   ) subquery1 ");
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
				dto.setInformation(rs.getString("information"));
				dto.setUserId(rs.getString("userId"));
				dto.setImageFilename(rs.getString("saveFilename"));
				dto.setCreated(rs.getString("created"));
				dto.setLikeNum(rs.getInt("likeNum"));
				
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
	
	public int insertTravel(TravelDTO dto) {
		int result = 0;
		StringBuilder sb = new StringBuilder();
		PreparedStatement pstmt = null;
		
		try {
			sb.append("INSERT INTO travel ");
			sb.append(" (travelNum, place, information, userid) ");
			sb.append(" VALUES(travel_seq.NEXTVAL,?,?,?) ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getPlace());
			pstmt.setString(2, dto.getInformation());
			pstmt.setString(3, dto.getUserId());
			
			pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return result;
	}
	
	public TravelDTO readTravel(int num) {
		TravelDTO dto = null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT t.travelNum, place, information, t.userid, userName , saveFilename  ");
			sb.append(" FROM travel t JOIN member m ON t.userid = m.userid ");
			sb.append(" LEFT OUTER JOIN ( ");
			sb.append("    SELECT travelNum, LISTAGG(SAVEFILENAME,',') WITHIN GROUP (order by SAVEFILENAME) saveFilename");
			sb.append("    FROM TRAVELFILE f ");
			sb.append("    WHERE f.travelNum = ? ");
			sb.append("    GROUP BY travelNum ");
			sb.append(") img ON t.travelNum = img.travelNum ");
			sb.append("	WHERE t.travelNum =? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			pstmt.setInt(2, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new TravelDTO();
				
				dto.setNum(rs.getInt("travelNum"));
				dto.setPlace(rs.getString("place"));
				dto.setInformation(rs.getString("information"));
				dto.setUserId(rs.getString("userid"));
				dto.setUserName(rs.getString("userName"));
				if(rs.getString("saveFilename")!=null) {
					dto.setImageFilename(rs.getString("saveFilename"));
				}
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
		return dto;
	}
	
	public int updateTravel(TravelDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE travel SET place=?, information=? WHERE travelNum=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPlace());
			pstmt.setString(2, dto.getInformation());
			pstmt.setInt(3, dto.getNum());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	public int deleteTravel(int num, String mode) {
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			sql = "DELETE FROM travelFile WHERE travelNum=? ";
				
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
				
			if(mode==null) {	
				
				pstmt.close();
				pstmt = null;
			
			
				sql = "DELETE FROM travel WHERE travelNum= ?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				result = pstmt.executeUpdate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
	
	public int likeInsert(int num) {
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE travel SET likeNum=likeNum+1 WHERE travelNum=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result=pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	public int insertImage(String s) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO travelFile(fNum,travelNum,saveFilename) VALUES(tFile_SEQ.NEXTVAL, TRAVEL_SEQ.CURRVAL, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, s);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}
	
	public int updateImage(TravelDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO travelFile(fNum,travelNum,saveFilename) VALUES(tFile_SEQ.NEXTVAL, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getNum());
			pstmt.setString(2, dto.getImageFilename());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}
	
}
