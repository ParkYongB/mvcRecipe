package com.recipe.vo;

import java.sql.Date;

/**
 * @Package Name   : com.recipe.vo
 * @FileName  : RecipeNewsVO.java
 * @작성일       : 2021. 9. 12. 
 * @작성자       : 오상준
 * @프로그램 설명 : 모두의 레시피 전용 공지사항 VO
 */
public class RecipeNewsVO {
	private int news_num;
	private String news_title;
	private String news_content;
	private int news_hits;
	private Date news_date;
	private Date news_modi;
	private String news_file;
	private String news_category;
	private String writer;
	private int news_comment_count;
	
	
	
	public int getNews_comment_count() {
		return news_comment_count;
	}
	public void setNews_comment_count(int news_comment_count) {
		this.news_comment_count = news_comment_count;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
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
	public void setNews_file(String news_file) {
		this.news_file = news_file;
	}
	public String getNews_category() {
		return news_category;
	}
	public void setNews_category(String news_category) {
		this.news_category = news_category;
	}
	
	
}
