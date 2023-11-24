package com.nemat.service.impl;

import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nemat.modal.User;
import com.nemat.repo.UserRepo;
import com.nemat.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public Integer saveUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepo.save(user).getId();
	}

	@Override
	public Optional<User> findByUserName(String username) {
		return userRepo.findByUserName(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optUser = findByUserName(username);
		if(optUser.isEmpty())
			throw new UsernameNotFoundException("User do not exist.");
		User user = optUser.get();
		
		return new org.springframework.security.core.userdetails.User(
				username, user.getPassword()
					, user.getRoles().stream()
					.map(role->new SimpleGrantedAuthority(role))
					.collect(Collectors.toList()));
	}

}
