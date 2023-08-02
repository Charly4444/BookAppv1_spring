package com.charly.bookapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charly.bookapp.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	List<User> findByName(String name);

	List<User> findByLibcode(Long libcode);
}
