package com.saar.userservice.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saar.userservice.entities.User;
import com.saar.userservice.exception.ResourceNotFoundException;
import com.saar.userservice.repositories.UserRepository;
import com.saar.userservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User saveUser(User user) {
		// code to generate Random userId
		String uuid= UUID.randomUUID().toString();
		user.setUserId(uuid);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(String userId) {
		return userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User is not found with id :"+ userId));
	}

	@Override
	public User updateUser(String userId, User user) {
		User user1= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User is not found with id :"+ userId));
		user1.setName(user.getName());
		user1.setAbout(user.getAbout());
		user1.setEmail(user.getEmail());
		return userRepository.save(user1);
		
	}

	@Override
	public String deletedUser(String userId) {
		User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User is not found with id :"+ userId));
		userRepository.delete(user);
		return "User is delete with id : "+userId;
	}

}
