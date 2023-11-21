package com.nemat.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.hibernate.query.criteria.internal.predicate.IsEmptyPredicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	@Value("${jwt.secretkey}")
	private String secret;
	
	
//	1. Generate Token
	public String generateToken(String subject) {
		return Jwts.builder()
				.setSubject(subject)
				.setIssuer("NematApp")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
	
//	2. Read Claims
	public Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(secret.getBytes())
				.parseClaimsJws(token)
				.getBody();
	}
	
//	3. Read Expiration Date
	public Date getExpDate(String token) {
		return getClaims(token).getExpiration();
	}
	
//	4. Read subject/userName
	public String getUserName(String token) {
		return getClaims(token).getSubject();
	}

//	5. validate Expiration Date
	public boolean isTokenExpired(String token) {
		return getExpDate(token).before(new Date(System.currentTimeMillis()));
	}
	
//	6. Validate -> userName in token and database, expDate
	public boolean validateToken(String token, String dbUserName) {
		String tokenUserName = getUserName(token);
		return tokenUserName.equals(dbUserName) && !isTokenExpired(token);
	}
}
