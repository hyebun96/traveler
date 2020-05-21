package com.contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class ContactDAO {
	private Connection conn = DBConn.getConnection();
	
	public int insertContact(ContactDTO dto) {
		int result = 0;
		PreparedStatement pstmt=null;
		String sql;
		try { 
			sql="INSERT INTO contact (ctNum, ctSubject, ctContent, ctName, ctTel, ctEmail, ctSort)"
					+"VALUES (contact_seq.NEXTVAL,?,?,?,?,?,?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getCtSubject());
			pstmt.setString(2, dto.getCtContent());
			pstmt.setString(3, dto.getCtName());
			pstmt.setString(4, dto.getCtTel());
			pstmt.setString(5, dto.getCtEmail());
			pstmt.setString(6, dto.getCtSort());
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
	
	// 데이터 개수
	public int dataCount(String ctSort) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*),0) FROM contact";
			if(ctSort.length()!=0) {
				sql+=" WHERE ctSort= ? ";
			}
			
			pstmt=conn.prepareStatement(sql);
			if(ctSort.length()!=0) {
				pstmt.setString(1, ctSort);
			} 			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
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
		return result;
	}
	// 검색에서의 데이터 개수
	
	public int dataCount(String condition, String keyword, String ctSort) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*),0) FROM contact";
			if(condition.equals("ctDate")) {
				keyword=keyword.replaceAll("-", "");
				sql+=" WHERE TO_CHAR(ctDate, 'YYYYMMDD')=?";
			} else {
				sql+=" WHERE INSTR("+condition+", ?) >=1";
			}	
			if(ctSort.length()!=0) {
				sql+= " AND ctSort= ? ";
			}
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			if(ctSort.length()!=0) {
				pstmt.setString(2, ctSort);
			}
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
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
		return result;
	}
	
	// 컨택트 리스트 (admin만 출력)
	public List<ContactDTO> listContact(int offset, int rows, String ctSort){
		List<ContactDTO> list = new ArrayList<ContactDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT ctSort, ctNum, ctName, ctSubject, TO_CHAR(ctDate, 'YYYY-MM-DD') ctDate, fin ");
			sb.append(" FROM contact ");
			if(ctSort.length()!=0) {
				sb.append(" WHERE ctSort= ? ");
			}
			sb.append(" ORDER BY ctNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
			
			pstmt=conn.prepareStatement(sb.toString());
			
			if(ctSort.length()==0) {
				pstmt.setInt(1, offset);
				pstmt.setInt(2, rows);
			} else {
				pstmt.setString(1, ctSort);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, rows);
			}
			
			rs= pstmt.executeQuery();
			while(rs.next()) {
				ContactDTO dto = new ContactDTO();
				dto.setCtSort(rs.getString("ctSort"));
				dto.setCtNum(rs.getInt("ctNum"));
				dto.setCtName(rs.getString("ctName"));
				dto.setCtSubject(rs.getString("ctSubject"));
				dto.setCtDate(rs.getString("ctDate"));
				dto.setFin(rs.getInt("fin"));
				
				list.add(dto);
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
		return list;
	}
	
	public List<ContactDTO> listContact(int offset, int rows, String condition, String keyword, String ctSort){
		List<ContactDTO> list = new ArrayList<ContactDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT ctSort, ctNum, ctName, ctSubject, TO_CHAR(ctDate, 'YYYY-MM-DD') ctDate, fin ");
			sb.append(" FROM contact ");
			if(condition.equals("ctDate")) {
				keyword=keyword.replaceAll("-", "");
				sb.append(" WHERE TO_CHAR(ctDate, 'YYYYMMDD')=?");
			} else {
				sb.append(" WHERE INSTR("+condition+", ?) >=1");
			}	
			
			if(ctSort.length()!=0) {
				sb.append(" AND ctSort= ? ");
			}
			sb.append(" ORDER BY ctNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, keyword);
			if(ctSort.length()==0) {
				pstmt.setInt(2, offset);
				pstmt.setInt(3, rows);
			} else {
				pstmt.setString(2, ctSort);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, rows);
			}
			
			rs= pstmt.executeQuery();
			while(rs.next()) {
				ContactDTO dto = new ContactDTO();
				dto.setCtSort(rs.getString("ctSort"));
				dto.setCtNum(rs.getInt("ctNum"));
				dto.setCtName(rs.getString("ctName"));
				dto.setCtSubject(rs.getString("ctSubject"));
				dto.setCtDate(rs.getString("ctDate"));
				dto.setFin(rs.getInt("fin"));
				
				list.add(dto);
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
		return list;
	}
	
	// contact 내용 보기
	public ContactDTO readContact(int ctNum) {
		ContactDTO dto = null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		StringBuffer sb= new StringBuffer();
		
		try {
			sb.append("SELECT ctNum, ctSort, ctName, ctSubject, ctContent");
			sb.append(", ctTel, ctEmail, ctDate, fin");
			sb.append(" FROM contact WHERE ctNum=?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, ctNum);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new ContactDTO();
				dto.setCtNum(rs.getInt("ctNum"));
				dto.setCtSort(rs.getString("ctSort"));
				dto.setCtName(rs.getString("ctName"));
				dto.setCtSubject(rs.getString("ctSubject"));
				dto.setCtContent(rs.getString("ctContent"));
				dto.setCtTel(rs.getString("ctTel"));
				dto.setCtEmail(rs.getString("ctEmail"));
				dto.setCtDate(rs.getString("ctDate"));
				dto.setFin(rs.getInt("fin"));
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
	
	public int updateContact(int ctNum) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="UPDATE contact SET fin=1 WHERE ctNum=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, ctNum);
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
	
	public int deleteContact(int ctNum) {
		int result = 0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="DELETE FROM contact WHERE ctNum=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, ctNum);
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
	
}
