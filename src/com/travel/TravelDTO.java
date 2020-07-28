package com.travel;

public class TravelDTO {
	private int num;			// DB 순서
	private int listNum;		// 게시물 번호
	private String userName;	// 사용자 이름
	private String userId;		// 사용자 아이디
	private String place;		// 여행지
	private String information;	// 여행자 정보	
	private String created;		// 생성일
	private int likeNum;		// 좋아요 수
	private String imageFilename[];	// 이미지 파일
	private String type;			// 지역    
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}

	public int getLikeNum() {
		return likeNum;
	}
	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
	}
	public String[] getImageFilename() {
		return imageFilename;
	}
	public void setImageFilename(String[] imageFilename) {
		this.imageFilename = imageFilename;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

	
}
