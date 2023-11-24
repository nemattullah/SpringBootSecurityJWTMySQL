package com.nemat.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nemat.modal.User;
import com.nemat.modal.UserRequest;
import com.nemat.modal.UserResponse;
import com.nemat.service.UserService;
import com.nemat.util.JwtUtil;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtil util;
	
	@Autowired
	private AuthenticationManager athenticationManager;
	
//	1. Save user data in database
	@PostMapping("/save")
	public ResponseEntity<String> saveUser(@RequestBody User user) {
		Integer id = userService.saveUser(user);
		String body = "User with id : "+id+" created";
//		return new ResponseEntity<String>(body, HttpStatus.OK);
		return ResponseEntity.ok(body);
	}
	
//	2. Validate user and generate token(login)
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(
			@RequestBody UserRequest request){
//		[---validate with database rows--]
		athenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUserName(), request.getPassword()));
		
		String token = util.generateToken(request.getUserName());
		return ResponseEntity.ok(new UserResponse(token,"Success!! Generated by "+request.getUserName()));
	}
	
//	3. Test
	@PostMapping("/test")
	public ResponseEntity<String> testData(Principal principal){
		return ResponseEntity.ok( "Hello : " + principal.getName() );
	}

}
