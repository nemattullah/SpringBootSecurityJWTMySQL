package com.nemat.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nemat.util.JwtUtil;

@Component
public class SecurityFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtil util;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = request.getHeader("Authorization");
		
		if(token!=null) {
//			Do Validation
			String userName = util.getUserName(token);
			
//			userName should not be empty, context-auth must be empty
			if(userName!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
				
//				Validate token
				boolean isValid = util.validateToken(token, userDetails.getUsername());
				if(isValid) {
					UsernamePasswordAuthenticationToken authToken = 
							new UsernamePasswordAuthenticationToken(userName,userDetails.getPassword(),userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//					final object stored in SecurityContext with User Details (un, password )
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		} 
//			Else goto next Filter or next Servlet ( FC in our case )
		filterChain.doFilter(request, response);
	}
	
	

}
