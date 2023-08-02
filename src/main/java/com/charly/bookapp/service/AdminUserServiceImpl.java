package com.charly.bookapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charly.bookapp.error.BookNotFoundException;
import com.charly.bookapp.error.UserNotFoundException;
import com.charly.bookapp.model.AdminUser;
import com.charly.bookapp.model.Book;
import com.charly.bookapp.model.User;
import com.charly.bookapp.repository.AdminUserRepository;
import com.charly.bookapp.repository.BookRepository;
import com.charly.bookapp.repository.UserRepository;

@Service
public class AdminUserServiceImpl implements AdminUserService {
		
	@Autowired
	private AdminUserRepository adminUserRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private UserRepository userRepository;
//	1
	@Override
	public AdminUser createAdmin(Long id, Long libcode, String password) {
		AdminUser newUser = new AdminUser();
		newUser.setId(id);
		newUser.setLibcode(libcode);
		newUser.setName("admin");
		newUser.setPassword(password);
		
		return adminUserRepository.save(newUser);
	}
	
//	2
	public List<User> getUsers(){
		return userRepository.findAll();
	}
	
//	3	chk
	@Override
	public User addUser(Long id, String name, Long libcode, String password) {
		User newUser = new User();
		newUser.setId(id);
		newUser.setLibcode(libcode);
		newUser.setName(name);
		newUser.setPassword(password);
		
		return userRepository.save(newUser);
	}

//	4
	@Override
	public String removeUserById(Long id)  throws UserNotFoundException{
		User userToBeDeleted = userRepository.getById(id);
		userRepository.deleteById(id);
		return "User with id= "+ userToBeDeleted.getId() + " has been deleted!";
	}

//	5
	@Override
	public String removeUserByName(String name) throws UserNotFoundException{
		List <User> userMatch = userRepository.findByName(name);
		User userToBeDeleted = new User();
		for (User user : userMatch) {
			if (user.getName()==name) {
				userToBeDeleted = user;
				userRepository.deleteById(user.getId());
			}
		}
		return "User with name= "+ userToBeDeleted.getName() + " has been deleted!";
	}
	
//	6
	@Override
	public User findUserByLibcode(Long libcode) throws UserNotFoundException{
		List <User> userMatch = userRepository.findByLibcode(libcode);
		User theUser = new User();
		for (User user : userMatch) {
			if (user.getLibcode().equals(libcode)) {
				theUser = user;
			}
		}
		return theUser;
	}
	
//	7
	@Override
	public AdminUser updateAdminById(Long id, AdminUser adminUser) throws UserNotFoundException{
		AdminUser whichUser = adminUserRepository.getById(id);
		whichUser.setLibcode(adminUser.getLibcode());
		whichUser.setName(adminUser.getName());
		whichUser.setPassword(adminUser.getPassword());
		whichUser.setIsLoggedin(adminUser.getIsLoggedin());
		return adminUserRepository.save(whichUser);
	}
	
	
//	8	[Login & Logout]
//	========================================================================
	@Override
	public int adminLogin(Long libcode, String password) throws UserNotFoundException {
		
		int loginFlag = 0;
		List<AdminUser> userList = adminUserRepository.findByLibcode(libcode);
		AdminUser expectedUser = new AdminUser();
//		
		if(!userList.isEmpty()) {
			expectedUser = userList.get(0);
			if((expectedUser.getLibcode().equals(libcode)) && (expectedUser.getPassword().equals(password))) {
				loginFlag=1;
				expectedUser.setIsLoggedin(true);
//				loggedOnUsers.add(expectedUser.getId());
			}
		}
		
		if (loginFlag==1) {
//			call service to update userRecord
			updateAdminById(expectedUser.getId(),expectedUser);
		}
		return loginFlag;	//return flag
	}
	@Override
	public void adminLogout(Long id) {
		AdminUser theUser = adminUserRepository.getById(id);
		theUser.setIsLoggedin(false);
		adminUserRepository.save(theUser);
	}
//	========================================================================
	
	
//	9
	@Override									//admin func
	public Book addBook(Long bookid, String booktitle, String content) {
//		get adminUser
		AdminUser adminUser = adminUserRepository.findByName("admin").get(0);
//		get adminBooks, modify as needed
		Book oneBook = new Book();
		oneBook.setBookid(bookid);
		oneBook.setBooktitle(booktitle);
		oneBook.setContent(content);
		oneBook.setAdminUser(adminUser);
		bookRepository.save(oneBook);
		return oneBook;
	}
	
//	10
	@Override					//admin func
	public String removeBookById(Long bookId) throws BookNotFoundException{
		Book bookToBeDeleted = bookRepository.findById(bookId).get();
		bookRepository.deleteById(bookId);
		return "Book with id= "+ bookToBeDeleted.getBookid() + " has been deleted!";
	}
	
//	11
	@Override
	public List<Book> getAvailableBooks() {
//		admin ID..
		Long adminId = adminUserRepository.findByName("admin").get(0).getId();
		List <Book> adminBooks = bookRepository.findByUser(adminId);
//		compile available books
		List <Book> availableBooks = new ArrayList<Book>();
		for (Book book : adminBooks) {
			if(book.getUser()==null) {
				availableBooks.add(book);
			}
		}
		return availableBooks;
	}
	
	
}
