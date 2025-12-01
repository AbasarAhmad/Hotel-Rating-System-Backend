package com.saar.userservice.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.saar.userservice.entities.Hotel;
import com.saar.userservice.entities.Rating;
import com.saar.userservice.entities.User;
import com.saar.userservice.exception.ResourceNotFoundException;
import com.saar.userservice.external.service.HotelService;
import com.saar.userservice.external.service.RatingService;
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
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private RatingService ratingService;
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
	        	
	        	List<Rating>ratingsOfUser=ratingService.getRatingById(user.getUserId());
	            // If no ratings, assign empty list
	            if (ratingsOfUser == null) {
	                user.setRatings(new ArrayList<>());
	                return;
	            }
//	            Will run for all ratings and set hotel of all rating
	    	    List<Rating> ratingList = ratingsOfUser.stream().map(rating -> {
	    	    	// fetching hotel from rating
	    	        Hotel hotel = hotelService.getHotel(rating.getHotelId());

	    	        // Set hotel into rating
	    	        rating.setHotel(hotel);
	    	        return rating;

	    	    }).collect(Collectors.toList());
	    	    user.setRatings(ratingList);

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
		
		// fetching User from UserService Db via userId
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server : " + userId));
/*
	    // WITHOUT Feign 
	    // Fetch ratings from RatingService
	    String ratingUrl = "http://RATINGSERVICE/api/ratings/get/userId/" + user.getUserId();

		//	  Yeh line API call karti hai aur JSON response ko Rating ke array me convert karke return karti hai.
	    Rating[] ratingsOfUser = restTemplate.getForObject(ratingUrl, Rating[].class);   
	    logger.info("Ratings => {}", Arrays.toString(ratingsOfUser));
	    // converting Array to List
	    List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
*/
	    // AFTER feign 
	 // Step-2: Fetch user ratings using Feign Client
	    List<Rating> ratings = ratingService.getRatingById(user.getUserId());
	    logger.info("Ratings Fetched => {}", ratings);
	    List<Rating> ratingList = ratings.stream().map(rating -> {

	        // API call to fetch Hotel via rating
/*			//Before feign client 
	//	    http://localhost:8082/api/hotels/get/5db264b8-ef01-4aea-b545-21351250d983
	        String hotelUrl = "http://HOTELSERVICE/api/hotels/get/" + rating.getHotelId();
	        ResponseEntity<Hotel> response = restTemplate.getForEntity(hotelUrl, Hotel.class);
	        Hotel hotel = response.getBody();
	        logger.info("Hotel Service Status Code => {}", response.getStatusCode());
*/
	    	// After Feign Client
	    	Hotel hotel=hotelService.getHotel(rating.getHotelId());
	        // Set hotel into rating
	        rating.setHotel(hotel);

	        return rating;

	    }).collect(Collectors.toList());

	    // now set Rating into user
	    user.setRatings(ratingList);

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
