package com.saar.userservice.service;

import java.util.List;

import com.saar.userservice.entities.User;

public interface UserService {
	
	// add User
	User saveUser (User user);
	
	// get all user
	List<User> getAllUser();
	
	// get a single user
	User getUserById(String userId);
	
	// update a user
	User updateUser(String userId, User user);
	
	// Delete a user
	String deletedUser(String userId);
	

}
