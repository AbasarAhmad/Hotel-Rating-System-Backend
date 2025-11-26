package com.saar.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saar.userservice.entities.User;

public interface UserRepository extends JpaRepository<User, String>{

}
