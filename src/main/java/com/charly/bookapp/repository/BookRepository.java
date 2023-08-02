package com.charly.bookapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.charly.bookapp.model.Book;



@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
//	This is JPQL... helps us run mini sql
	@Query(value = "SELECT b FROM Book b WHERE b.adminUser.id=?1")
	List <Book> findByUser(Long adminOwned);
}
