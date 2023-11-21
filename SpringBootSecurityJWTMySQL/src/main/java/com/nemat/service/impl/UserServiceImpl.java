package com.nemat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemat.modal.User;
import com.nemat.repo.UserRepo;
import com.nemat.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo useRepo;
	
	@Override
	public Integer saveUser(User user) {
		return useRepo.save(user).getId();
	}

}
