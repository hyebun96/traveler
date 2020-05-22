package com.photo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.member.MemberDTO;
import com.util.DBConn;

public class PhotoDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertPhoto(PhotoDTO dto) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;

		sql="INSERT INTO photo (photoNum, userId,subject, content, imageFilename ) VALUES (PHOTO_SEQ.NEXTVAL,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getImageFilename());
	
			result = pstmt.executeUpdate();

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
	
	public int dataCount() {	//Á¶È¸¼ö
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM photo";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next())
				result=rs.getInt(1);

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
	
	public List<PhotoDTO> listPhoto(int offset, int rows) {
		List<PhotoDTO> list=new ArrayList<PhotoDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("SELECT photoNum, p.userId ,subject, imageFilename  ");
			sb.append(" FROM photo p JOIN member m ON p.userId = m.userId  ");
			sb.append(" ORDER BY photoNum DESC  ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				PhotoDTO dto=new PhotoDTO();
				dto.setPhotoNum(rs.getInt("photoNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setContent(rs.getString("subject"));
				dto.setImageFilename(rs.getString("imageFilename"));
				
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
	
	public PhotoDTO readPhoto(int photonum) {
		PhotoDTO dto=null;
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("SELECT photoNum, userId, content, subject, imageFilename ");
			sb.append(" FROM photo ");
			sb.append(" WHERE photoNum=?");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, photonum);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new PhotoDTO();
				dto.setPhotoNum(rs.getInt("photoNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setContent(rs.getString("content"));
				dto.setSubject(rs.getString("subject"));
				dto.setImageFilename(rs.getString("imageFilename"));
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
	
	public List<PhotoDTO> listPhotoUser(int offset, int rows) {
		List<PhotoDTO> list=new ArrayList<PhotoDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("SELECT photoNum, p.userId ,subject, p.imageFilename, m.userName  ");
			sb.append(" FROM photo p JOIN member m ON p.userId = m.userId  ");
			sb.append(" ORDER BY photoNum DESC  ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				PhotoDTO dto=new PhotoDTO();
				dto.setPhotoNum(rs.getInt("photoNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setSubject(rs.getString("subject"));
				dto.setImageFilename(rs.getString("imageFilename"));
				dto.setUserName(rs.getString("userName"));
				
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
	
	public List<MemberDTO> listMemberUser(int offset, int rows) {
		List<MemberDTO> list=new ArrayList<MemberDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("SELECT userId , userName  ");
			sb.append(" FROM member ") ;
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				MemberDTO dto=new MemberDTO();
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				
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
	
	public List<PhotoDTO> listPhotoUser(String keyword) {
        List<PhotoDTO> list=new ArrayList<PhotoDTO>();

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        StringBuffer sb = new StringBuffer();

        try {
			sb.append("SELECT photonum, p.userId, p.imageFilename,  ");
			sb.append("       subject, content, m.userName  ");
			sb.append(" FROM photo p JOIN member m ON p.userId = m.userId  ");
			sb.append(" where p.userId = ? ");
			
            
			pstmt=conn.prepareStatement(sb.toString());
            
			pstmt.setString(1, keyword);
			
            
            rs=pstmt.executeQuery();
            
            while(rs.next()) {
            	PhotoDTO dto=new PhotoDTO();
				dto.setPhotoNum(rs.getInt("photonum"));
            	dto.setUserId(rs.getString("userId"));
            	dto.setImageFilename(rs.getString("imageFilename"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setUserName(rs.getString("userName"));
				
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
	
	public List<MemberDTO> listMemberUser(String keyword) {
        List<MemberDTO> list=new ArrayList<MemberDTO>();

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        StringBuffer sb = new StringBuffer();

        try {
			sb.append("SELECT userName, userId  ");
			sb.append(" FROM member  ");
			sb.append(" where userId = ? ");
			
            
			pstmt=conn.prepareStatement(sb.toString());
            
			pstmt.setString(1, keyword);
			
            
            rs=pstmt.executeQuery();
            
            while(rs.next()) {
            	MemberDTO dto=new MemberDTO();
            	
            	dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				
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
	
	
	public int updatePhoto(PhotoDTO dto) {
		int result=0;
		PreparedStatement pstmt=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("UPDATE photo SET subject=?, content=?,  imageFilename=? ");
			sb.append(" WHERE photonum=?");
			pstmt=conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getImageFilename());
			pstmt.setInt(4, dto.getPhotoNum());
			
			
			result = pstmt.executeUpdate();
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
	
	public int deletePhoto(int photonum) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="DELETE FROM photo WHERE photonum=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, photonum);
			result = pstmt.executeUpdate();
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
	
}
