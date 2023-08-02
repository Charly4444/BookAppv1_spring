package com.charly.bookapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charly.bookapp.error.BookNotFoundException;
import com.charly.bookapp.error.UserNotFoundException;
import com.charly.bookapp.model.Book;
import com.charly.bookapp.model.User;
import com.charly.bookapp.repository.AdminUserRepository;
import com.charly.bookapp.repository.BookRepository;
import com.charly.bookapp.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	AdminUserRepository adminUserRepository;
	
//	7		[FOR USER]
	@Override
	public User updateUserById(Long id, User user) throws UserNotFoundException{
		User whichUser = userRepository.getById(id);
		whichUser.setLibcode(user.getLibcode());
		whichUser.setName(user.getName());
		whichUser.setPassword(user.getPassword());
		whichUser.setIsLoggedin(user.getIsLoggedin());
		return userRepository.save(whichUser);
	}

	
//	========================================================
//	[for USER]
	@Override
	public int userLogin(Long libcode, String password) throws UserNotFoundException {
		// TODO Auto-generated method stub
		int loginFlag = 0;
		List<User> userList = userRepository.findByLibcode(libcode);
		User expectedUser = new User();
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
			updateUserById(expectedUser.getId(),expectedUser);
		}
		return loginFlag;	//return flag
	}
	@Override
	public void userLogout(Long id) {
		User theUser = userRepository.getById(id);
		theUser.setIsLoggedin(false);
		userRepository.save(theUser);
	}
//	========================================================

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
	

//	12  [USER BASED]
	@Override
	public List<Book> getABook(Long userId, Long bookid) throws BookNotFoundException{
		// get the matching book from the general library
//		check if book available and get it ready --meaning its still admin props
		User userRequesting = userRepository.findById(userId).get();
		List<Book> userBooks = userRequesting.getUserBooks();
		
//		get available Books
		List<Book> availableBooks = getAvailableBooks();
		int validBookFlag = 0;
		for(Book book : availableBooks) {
			if (book.getBookid().equals(bookid));
			validBookFlag = 1;
		}
		if (validBookFlag == 1) {
//			now, get the book
			Book bookNeeded = bookRepository.findById(bookid).get();
//			change its user defs
			bookNeeded.setUser(userRequesting);
//			add it to usersBooks
			userBooks.add(bookNeeded);
			userRequesting.setUserBooks(userBooks);
			
//			bookRepository.save(bookNeeded);
			userRepository.save(userRequesting);
		}
		return userBooks;
	}

//	13	[USER BASED]
	@Override
	public Book keepbackBook(Long userId, Long bookid) {
		User userKeeping = userRepository.findById(userId).get();
//		does user have book
		Book bookToReturn = new Book();
		List <Book> userBooks = userKeeping.getUserBooks();
		for (Book book : userBooks) {
			if (book.getBookid().equals(bookid)) {
				bookToReturn = book;
			}
		}
		if(bookToReturn != null) {
			bookToReturn.setUser(null);
			userBooks.remove(bookToReturn);
			userKeeping.setUserBooks(userBooks);
			
			userRepository.save(userKeeping);
		}
		
		return bookToReturn;
	}

//	14	[-- user_SUB-FUNC]
	@Override
	public int canReadBook(Long userId, Long bookid) throws BookNotFoundException {
		int canReadFlag = 0;
		Book bookwantToRead = bookRepository.getById(bookid);
		List<Book> userBooks = userRepository.getById(userId).getUserBooks();
		for (Book book : userBooks) {
			if (book.equals(bookwantToRead)) {
				canReadFlag= 1;
			}
		}
		return canReadFlag;
	}
//	15 READ A BOOK	[FOR USER]
	@Override
	public String readABook(Long userId, Long bookid) throws BookNotFoundException {
		int canReadFlag = canReadBook(userId, bookid);
		Book bookToRead = new Book();
		if (canReadFlag == 1) {
			bookToRead = bookRepository.getById(bookid);
		}
		return bookToRead.getContent();
	}

}
