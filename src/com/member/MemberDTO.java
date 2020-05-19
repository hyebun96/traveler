package com.member;

import java.util.Date;

public class MemberDTO {
	private String userId;
	private String userPwd;
	private String userName;
	private String userTel,Tel1,Tel2,Tel3;
	private String userEmail,Email1,Email2;
	private String userBirth;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public String getTel1() {
		return Tel1;
	}
	public void setTel1(String tel1) {
		Tel1 = tel1;
	}
	public String getTel2() {
		return Tel2;
	}
	public void setTel2(String tel2) {
		Tel2 = tel2;
	}
	public String getTel3() {
		return Tel3;
	}
	public void setTel3(String tel3) {
		Tel3 = tel3;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getEmail1() {
		return Email1;
	}
	public void setEmail1(String email1) {
		Email1 = email1;
	}
	public String getEmail2() {
		return Email2;
	}
	public void setEmail2(String email2) {
		Email2 = email2;
	}
	public String getUserBirth() {
		return userBirth;
	}
	public void setUserBirth(String userBirth) {
		this.userBirth = userBirth;
	}
	
	
}
