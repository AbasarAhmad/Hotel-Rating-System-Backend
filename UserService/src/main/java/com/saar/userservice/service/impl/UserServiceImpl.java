package com.saar.userservice.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.saar.userservice.entities.Rating;
import com.saar.userservice.entities.User;
import com.saar.userservice.exception.ResourceNotFoundException;
import com.saar.userservice.repositories.UserRepository;
import com.saar.userservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private Logger logger =LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
//	jaise browser kisi URL ko hit karta hai Waise hi backend se backend URL call karne ke liye RestTemplate use hota hai.
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Override
	public User saveUser(User user) {
		// code to generate Random userId
		String uuid= UUID.randomUUID().toString();
		user.setUserId(uuid);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		// Step 1: Get all users from DB
	    List<User> users = userRepository.findAll();

	    // Step 2: For each user → call RatingService → attach ratings
	    users.forEach(user -> {
	        try {
	            String url = "http://localhost:8083/api/ratings/get/userId/" + user.getUserId();
	            Rating[] ratingArray = restTemplate.getForObject(url, Rating[].class);

	            if (ratingArray != null) {
	                user.setRatings(Arrays.asList(ratingArray));
	            } else {
	                user.setRatings(new ArrayList<>());
	            }

	        } catch (Exception e) {
	            // In case RatingService fails
	            user.setRatings(new ArrayList<>());
	            logger.error("Failed to fetch ratings for user: {}", user.getUserId(), e);
	        }
	    });

	    return users;
	}

	@Override
	public User getUserById(String userId) {
		// get user from database with the help of user repository
		User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User is not found with id :"+ userId));
		
		//fetch rating of the above user from RatingService
//		http://localhost:8083/api/ratings/get/userId/3fed2354-69e3-40fe-a3c5-2dfcabaa4986
		 ArrayList<Rating> forObject= restTemplate.getForObject("http://localhost:8083/api/ratings/get/userId/"+user.getUserId(),ArrayList.class);
		logger.info("{}", forObject);
		user.setRatings(forObject);
		return user;
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
