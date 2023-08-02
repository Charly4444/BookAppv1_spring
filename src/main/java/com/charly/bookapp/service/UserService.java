package com.charly.bookapp.service;

import java.util.List;

import com.charly.bookapp.error.BookNotFoundException;
import com.charly.bookapp.error.UserNotFoundException;
import com.charly.bookapp.model.Book;
import com.charly.bookapp.model.User;

public interface UserService {
//	update userById
	User updateUserById(Long id, User user) throws UserNotFoundException;
	
//	================================================
//	Login a User  -- return a login flag (NOT ADMIN SPECIFIC @THIS POINT !!)
	int userLogin(Long libcode, String password) throws UserNotFoundException;																			
//	Logout a User	#back to greet pg   (NOT ADMIN SPECIFIC @THIS POINT !!)
	void userLogout(Long id);
//	================================================
	
//	get available books
	List<Book> getAvailableBooks();
	
//	get a book added to personal library	[USER FUNC]
	List <Book> getABook (Long userId, Long bookid) throws BookNotFoundException;
	
//	return a book back to Gen Library
	Book keepbackBook(Long userId, Long bookid);  //***
	
//	[for user -- SUB FUNC]
//	read A book from your library
	int canReadBook(Long userId, Long bookid) throws BookNotFoundException;
//	read BOOK after sub-FUNC
	String readABook(Long userId, Long bookid) throws BookNotFoundException;
	
}
