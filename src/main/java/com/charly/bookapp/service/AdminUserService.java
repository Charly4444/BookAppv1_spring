package com.charly.bookapp.service;

import java.util.List;

import com.charly.bookapp.error.BookNotFoundException;
import com.charly.bookapp.error.UserNotFoundException;
import com.charly.bookapp.model.Book;
import com.charly.bookapp.model.AdminUser;
import com.charly.bookapp.model.User;

public interface AdminUserService {
//	create admin user
	AdminUser createAdmin(Long id, Long libcode, String password);
	
//	get all non admin users
	List<User> getUsers();
	
//	add a user to the system
	User addUser(Long id, String name, Long libcode, String password);
	
//	remove a User (byId)
	String removeUserById(Long id) throws UserNotFoundException;
	
//	remove a User (byName)
	String removeUserByName(String Name) throws UserNotFoundException;
	
//	get a User (bylibcode)
	User findUserByLibcode(Long libcode) throws UserNotFoundException;
	
//	update adminById
	AdminUser updateAdminById(Long id, AdminUser adminUser) throws UserNotFoundException;
	
	
	
//	Login & Logout
//	========================================================================
//	Login an Admin  -- return a login flag (NOT ADMIN SPECIFIC @THIS POINT !!)
	int adminLogin(Long libcode, String password) throws UserNotFoundException;															
//	Logout Admin	#back to greet pg   (NOT ADMIN SPECIFIC @THIS POINT !!)
	void adminLogout(Long id);
//	========================================================================


//	add a book to all library
	Book addBook(Long bookid, String booktitle, String content);
	
//	remove book by Id
	String removeBookById(Long bookId) throws BookNotFoundException;
	
//	see all available books
	List<Book> getAvailableBooks();
	
}
