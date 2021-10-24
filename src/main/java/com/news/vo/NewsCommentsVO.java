/**
 * 
 */
package com.news.vo;

/**
 * @Package Name   : com.news.vo
 * @FileName  : NewsCommentsVO.java
 * @작성일       : 2021. 9. 9. 
 * @작성자       : 신혜지
 * @프로그램 설명 : 공지사항의 댓글을 확인합니다.
 */
public class NewsCommentsVO {
	private int comm_num;
	private int mem_num;
	private String comm_con;
	private String comm_date;
	private String comm_modifydate;
	private int news_num;
	private String id;
	private String photo;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getComm_modifydate() {
		return comm_modifydate;
	}
	public void setComm_modifydate(String comm_modifydate) {
		this.comm_modifydate = comm_modifydate;
	}
	public int getComm_num() {
		return comm_num;
	}
	public void setComm_num(int comm_num) {
		this.comm_num = comm_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getComm_con() {
		return comm_con;
	}
	public void setComm_con(String comm_con) {
		this.comm_con = comm_con;
	}
	public String getComm_date() {
		return comm_date;
	}
	public void setComm_date(String comm_date) {
		this.comm_date = comm_date;
	}
	public int getNews_num() {
		return news_num;
	}
	public void setNews_num(int news_num) {
		this.news_num = news_num;
	}
	
	
}
