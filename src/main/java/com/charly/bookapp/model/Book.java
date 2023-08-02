package com.charly.bookapp.model;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="books_in_library")
public class Book{
	@Id
	private Long bookid;
	private String booktitle;
	private String content;
//	many expenses mapped to one user	-> the Many side is the referencing side, can be deleted at will
//	and all cascade operations chained down also
	@JsonIgnore		//you Use @JsonIgnore on one side of a table to table relationship
	@ManyToOne(cascade = CascadeType.PERSIST)	//otherwise you get no output when you return the table; due to recursion
	@JoinColumn(name = "adminuser_fk")
	private AdminUser adminUser;
	@JsonIgnore		//you Use @JsonIgnore on one side of a table to table relationship; otherwise error due to recursion
	@ManyToOne(cascade = CascadeType.PERSIST)
//	@JoinColumn(name = "user_fk")	//plug-in user
	private User user = null;
	
	private int lastpage = 0;
	private int chars_per_page = 100;
	
	public Book() {
		// TODO Auto-generated constructor stub
	}
	public Book(Long bookid, String booktitle, String content) {
		super();
		this.bookid = bookid;
		this.booktitle = booktitle;
		this.content = content;
//		this.user = null;
	}
//	public Book(Long bookid, String booktitle, String content, AdminUser adminUser) {
//		super();
//		this.bookid = bookid;
//		this.booktitle = booktitle;
//		this.adminUser = adminUser;
//		this.content = content;
//	}
	
	public Long getBookid() {
		return bookid;
	}
	public void setBookid(Long bookid) {
		this.bookid = bookid;
	}
	
	public String getBooktitle() {
		return booktitle;
	}
	public void setBooktitle(String booktitle) {
		this.booktitle = booktitle;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public AdminUser getAdminUser() {
		return adminUser;
	}
	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public int getLastpage() {
		return lastpage;
	}
	public void setLastpage(int lastpage) {
		this.lastpage = lastpage;
	}
	
	public int getChars_per_page() {
		return chars_per_page;
	}
	public void setChars_per_page(int chars_per_page) {
		this.chars_per_page = chars_per_page;
	}
	
	@Override
	public String toString() {
		return "Book [bookid=" + bookid + ", booktitle=" + booktitle + ", adminUser=" + adminUser.getName() + ", user=" + user + "]";
	}
	
}
