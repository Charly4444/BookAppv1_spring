package com.charly.bookapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.charly.bookapp.model.Book;

@Service
public interface BookService {
//	update page on
	void turnPage();	//update page after turning.
//	fr-end should take care of arranging the received string on page
	
//	open a book (if logged in)
	List<String> getBookContent();
	
//	close a book
	String closeBook();
}
