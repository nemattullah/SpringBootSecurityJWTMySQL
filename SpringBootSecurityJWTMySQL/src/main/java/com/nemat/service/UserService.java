package com.nemat.service;

import java.util.Optional;

import com.nemat.modal.User;

public interface UserService {
	
	Integer saveUser(User user);
	
	Optional<User> findByUserName(String username);

}
