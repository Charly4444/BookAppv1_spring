package com.charly.bookapp.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="admin_user")
public class AdminUser{
	
	@Id
//	@GeneratedValue(generator = "gen1",strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private Long libcode;
	private String password;
	
	private Boolean isLoggedin = false;
	
//	the One side of OneToMany is referenced by Many, if it becomes an orphan it can be removed; as set here
	@OneToMany(mappedBy = "adminUser", orphanRemoval = true)	//as a reverse def we use 'mappedBy' attribute
	private List <Book> adminBooks;
//	These list of books this user owns
	
	
	
	public AdminUser() {
		// TODO Auto-generated constructor stub
	}

	public AdminUser(Long id, String name, Long libcode, String password, List<Book> books) {
		super();
		this.id = id;
		this.name = name;
		this.isLoggedin = false;
	}

	public AdminUser(Long id, String name, Long libcode, String password) {
		super();
		this.id = id;
		this.name = name;
		this.isLoggedin = false;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Long getLibcode() {
		return libcode;
	}
	public void setLibcode(Long libcode) {
		this.libcode = libcode;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsLoggedin() {
		return isLoggedin;
	}
	public void setIsLoggedin(Boolean isLoggedin) {
		this.isLoggedin = isLoggedin;
	}

	public List<Book> getAdminBooks() {
		return adminBooks;
	}
	public void setAdminBooks(List<Book> adminBooks) {
		this.adminBooks = adminBooks;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", libcode=" + libcode + ", adminBooks=" + adminBooks + "]";
	}
	
	
	
}
