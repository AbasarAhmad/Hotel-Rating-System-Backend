package com.saar.ratingservice.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.saar.ratingservice.entities.Rating;

public interface RatingRepository extends MongoRepository<Rating, String>{

	// get All Rating thrugh userId
	List<Rating>findByUserId(String userId);
	
	// get all Ratings of a Specific Hotel
	List<Rating>findByHotelId(String hotelId);
}
