package com.charly.bookapp.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.charly.bookapp.error.BookNotFoundException;
import com.charly.bookapp.error.UserNotFoundException;
import com.charly.bookapp.model.AdminUser;
import com.charly.bookapp.model.Book;
import com.charly.bookapp.model.User;
import com.charly.bookapp.service.AdminUserService;
import com.charly.bookapp.service.UserService;

@RestController
@RequestMapping("/books")
@CrossOrigin
public class bookAppcontroller {
	
	@Autowired
	private AdminUserService adminUserService;
	
	@Autowired
	private UserService userService;
	
	
//	create admin user
//	@GetMapping("/admin")
	
	@GetMapping("/")
	public String greeting() {
		return "Welcome to the app.";
	}
	
	@PostMapping("/admin")
	AdminUser createAdmin(@RequestBody AdminUser userDetails){
		return adminUserService.createAdmin(userDetails.getId(),userDetails.getLibcode(),userDetails.getPassword());
	}
	
	@GetMapping("/users")
	List<User> getUsers(){
		return adminUserService.getUsers();
	}
	
	@PostMapping("/new-user")
	User addUser(@RequestBody User userDetails){
		return adminUserService.addUser(userDetails.getId(),userDetails.getName(),userDetails.getLibcode(),userDetails.getPassword());
	}
	
//	ADMIN LOGIN			[CHANGED! LINK!!]
	@PostMapping("/admin/login")
	public ResponseEntity<Void> adminLogin(@RequestBody AdminUser userDetails) throws UserNotFoundException{
		int loginFlag = adminUserService.adminLogin(userDetails.getLibcode(),userDetails.getPassword());
		ResponseEntity<Void> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).location(URI.create("http://localhost:8080/books/error")).build();
		if (loginFlag == 1) {
			response = ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8080/books/all-books")).build();
		}
		System.out.println(loginFlag);
		return response;
	}
	
//	USER LOGIN			[for USER -- edited to return flag]
	@PostMapping("/user/login")
	public int userLogin(@RequestBody User userDetails) throws UserNotFoundException{
//	public ResponseEntity<Void> userLogin(@RequestBody User userDetails) throws UserNotFoundException{
		int loginFlag = userService.userLogin(userDetails.getLibcode(),userDetails.getPassword());
		ResponseEntity<Void> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).location(URI.create("http://localhost:8080/books/error")).build();
		if (loginFlag == 1) {
			response = ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8080/books/all-books")).build();
		}
		System.out.println(loginFlag);
//		return response;
		return loginFlag;
	}
	
	@GetMapping("/all-books")
	public List<Book> allAdminBooks(){
		return userService.getAvailableBooks();
	}
	
	@GetMapping("/error")
	public String errorPage() {
		return "Bad Request from User";
	}
	
//	ADMIN LOGOUT
	@GetMapping("/logout/{id}")
	public ResponseEntity<Void> adminLogout(@PathVariable ("id") Long id) throws UserNotFoundException{
		adminUserService.adminLogout(id);
//		its defaults a GET request, follows the rule for that endpoint
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8080/books/")).build();
	}
	
//	USER LOGOUT		[for USER]
	@GetMapping("/users/logout/{id}")
	public void userLogout(@PathVariable ("id") Long id) throws UserNotFoundException{
//	public ResponseEntity<Void> userLogout(@PathVariable ("id") Long id) throws UserNotFoundException{
		userService.userLogout(id);
//		its defaults a GET request, follows the rule for that endpoint
//		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8080/books/")).build();
		
	}
	
	@PostMapping("/book/add")
	public Book addBook(@RequestBody Book book) {
		adminUserService.addBook(book.getBookid(), book.getBooktitle(), book.getContent());
		return book;
	}
	
//	ADMIN DELETE USER
	@GetMapping("/admin/remid/{id}")
	public String removeUserById(@ PathVariable ("id") Long id) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return adminUserService.removeUserById(id);
	}
	
//	ADMIN DELETE USER
	@PostMapping("/admin/remnm/{name}")
	public String removeUserByName(@ PathVariable ("name") String name) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return adminUserService.removeUserByName(name);
	}
	
//	ADMIN FIND USER
	@GetMapping("/user")		//if you fill values yourself @Requestparam during query remember "?" before start "lib="
	public User getUserByLibcode(@RequestParam Long lib) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return adminUserService.findUserByLibcode(lib);
	}
	
//	UPDATE ADMIN BY ID	*PROBLEM
	@PostMapping("/admin/update/{adminId}")
	public AdminUser updateAdmin(@PathVariable ("adminId") Long adminId, @RequestBody AdminUser adminUser) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return adminUserService.updateAdminById(adminId, adminUser);
	}
	
	
//	[FOR USER]
//	UPDATE USER BY ID
	@PostMapping("/user/{id}")
	public User updateUser(@PathVariable ("id") Long id, @RequestBody User user) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return userService.updateUserById(id, user);
	}
	
//	REMOVE BOOK BY ID
	@GetMapping("/book/remid/{id}")
	public String removeBook(@PathVariable ("id") Long id) throws BookNotFoundException {
		// TODO Auto-generated method stub
		return adminUserService.removeBookById(id);
	}
	
	
//	[FOR USER]
//	USER TAKES A BOOK FROM LIBRARY
	@PostMapping("/user/request")
	public List<Book> getABookFromLibrary(@RequestParam Long uid, @RequestParam Long bookid) throws BookNotFoundException{
		return userService.getABook(uid, bookid);
	}
	
//	[FOR USER]
//	KEEP BOOK USER
	@GetMapping("/user/keepbk")
	public Book keepBookInLibrary(@RequestParam Long uid, @RequestParam Long bookid) throws BookNotFoundException{
		return userService.keepbackBook(uid, bookid);
	}
	
//	READ A BOOK, CAN YOU?		[FOR USER]
	@GetMapping("/user/read")
	public String readABook(@RequestParam Long uid, @RequestParam Long bookid) throws BookNotFoundException{
		return userService.readABook(uid, bookid);
	}
	
}