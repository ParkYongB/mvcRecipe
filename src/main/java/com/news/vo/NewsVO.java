package com.news.vo;

import java.sql.Date;


/**
 * @Package Name   : com.news.vo
 * @FileName  : NewsVO.java
 * @작성일       : 2021. 9. 7. 
 * @작성자       : 신혜지
 * @프로그램 설명 : 뉴스 vo 입니다. newDetail.jsp, newsForm.jsp, newsList.jsp,newWrite.jsp에서 씁니다.
 */

public class NewsVO {
	
	private int news_num;
	private String news_title;
	private String news_content;
	private int mem_num;
	private int news_hits;
	private Date news_date;
	private Date news_modi;
	private String news_file;
	private String id;
	private String news_category;
	private int auth;
	private int news_comment_count;
	
	
	
	public int getNews_comment_count() {
		return news_comment_count;
	}
	public void setNews_comment_count(int news_comment_count) {
		this.news_comment_count = news_comment_count;
	}
	public int getAuth() {
		return auth;
	}
	public void setAuth(int auth) {
		this.auth = auth;
	}
	public String getNews_category() {
		return news_category;
	}
	public void setNews_category(String news_category) {
		this.news_category = news_category;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNews_num() {
		return news_num;
	}
	public void setNews_num(int news_num) {
		this.news_num = news_num;
	}
	public String getNews_title() {
		return news_title;
	}
	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}
	public String getNews_content() {
		return news_content;
	}
	public void setNews_content(String news_content) {
		this.news_content = news_content;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public int getNews_hits() {
		return news_hits;
	}
	public void setNews_hits(int news_hits) {
		this.news_hits = news_hits;
	}
	public Date getNews_date() {
		return news_date;
	}
	public void setNews_date(Date news_date) {
		this.news_date = news_date;
	}
	public Date getNews_modi() {
		return news_modi;
	}
	public void setNews_modi(Date news_modi) {
		this.news_modi = news_modi;
	}
	public String getNews_file() {
		return news_file;
	}
	public void setNews_file(String new_file) {
		this.news_file = new_file;
	}
}
