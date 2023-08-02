package com.charly.bookapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.charly.bookapp.model.AdminUser;



@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long>{

	List<AdminUser> findByName(String name);

	List<AdminUser> findByLibcode(Long libcode);
	
}
