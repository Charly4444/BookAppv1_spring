package com.charly.bookapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookappApplication {
	/*
	 * General collection of books (Library)
	 * Each user can access books taken off from library to their library
	 * Each user can read books
	 * Each book owned by user remembers where the user left off
	 * when returned book to gen library last page defaults to one
	 * 
	 * so theres a user called Admin
	 * admin can add User to lib_users
	 * admin should be able to add books to the system
	 * Each book contains a long string as its content:
	 * it has split its characters into no of characters per page to make a list. Each list is accessed when a page is called !*/
	public static void main(String[] args) {
		SpringApplication.run(BookappApplication.class, args);
	}

}
