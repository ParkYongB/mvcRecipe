package com.recipe.vo;

import java.sql.Date;

public class RecipeVO {
	
	private int board_num; // �۹�ȣ
	private String title; // ������
	private String sub_content;	// �Ұ���
	private String content; // ����
	private int hits; // ��ȸ��
	private int recom_count; // ��õ��
	private Date report_date; // �����
	private Date modify_date; // ������
	private String ip; // ip�ּ�
	private String filename; // ���ϸ�
	private String category; // ����
	private	int mem_num; // ȸ����ȣ
	private int book_num; // �ϸ�ũ ��ȣ
	private int comm_count; // ��� ����
	private String id;  // join�� ���� id ���
	private int auth;	// ȸ�����
	private int news_comments_count;	// ��ۼ�
	
	
	
	public int getNews_comments_count() {
		return news_comments_count;
	}

	public void setNews_comments_count(int news_comments_count) {
		this.news_comments_count = news_comments_count;
	}

	public int getBoard_num() {
		return board_num;
	}
	
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getSub_content() {
		return sub_content;
	}

	public void setSub_content(String sub_content) {
		this.sub_content = sub_content;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getHits() {
		return hits;
	}
	
	public void setHits(int hits) {
		this.hits = hits;
	}
	
	public int getRecom_count() {
		return recom_count;
	}
	
	public void setRecom_count(int recom_count) {
		this.recom_count = recom_count;
	}
	
	public Date getReport_date() {
		return report_date;
	}
	
	public void setReport_date(Date reg_date) {
		this.report_date = reg_date;
	}
	
	public Date getModify_date() {
		return modify_date;
	}
	
	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public int getMem_num() {
		return mem_num;
	}
	
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getBook_num() {
		return book_num;
	}

	public void setBook_num(int book_num) {
		this.book_num = book_num;
	}

	public int getComm_count() {
		return comm_count;
	}

	public void setComm_count(int comm_count) {
		this.comm_count = comm_count;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	
	
}
