package com.nemat.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nemat.modal.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByUserName(String username);
	
}
