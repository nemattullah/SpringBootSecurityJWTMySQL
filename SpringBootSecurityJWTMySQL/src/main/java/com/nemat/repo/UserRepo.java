package com.nemat.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nemat.modal.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
