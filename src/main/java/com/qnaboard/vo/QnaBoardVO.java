package com.qnaboard.vo;

import java.sql.Date;



public class QnaBoardVO {
	private int qna_num;
	private String qna_title;
	private String qna_content;
	private String qna_id;
	private String qna_passwd;
	private String qna_ip;
	private Date qna_date;
	
	//비밀번호 일치 여부 체크
	public boolean isCheckedPassword(String userPasswd) {
		if(qna_passwd.equals(userPasswd)) {
			return true;
		}
		return false;
	}
	
	public int getQna_num() {
		return qna_num;
	}
	public void setQna_num(int qna_num) {
		this.qna_num = qna_num;
	}
	public String getQna_title() {
		return qna_title;
	}
	public void setQna_title(String qna_title) {
		this.qna_title = qna_title;
	}
	public String getQna_content() {
		return qna_content;
	}
	public void setQna_content(String qna_content) {
		this.qna_content = qna_content;
	}
	public String getQna_id() {
		return qna_id;
	}
	public void setQna_id(String qna_id) {
		this.qna_id = qna_id;
	}
	public String getQna_passwd() {
		return qna_passwd;
	}
	public void setQna_passwd(String qna_passwd) {
		this.qna_passwd = qna_passwd;
	}
	public String getQna_ip() {
		return qna_ip;
	}
	public void setQna_ip(String qna_ip) {
		this.qna_ip = qna_ip;
	}
	public Date getQna_date() {
		return qna_date;
	}
	public void setQna_date(Date qna_date) {
		this.qna_date = qna_date;
	}
	
}
