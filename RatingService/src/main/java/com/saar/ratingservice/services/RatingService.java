package com.saar.ratingservice.services;

import java.util.List;

import com.saar.ratingservice.entities.Rating;


public interface RatingService {

	
	// add User
		Rating saveRating (Rating rating);
		
		// get all user
		List<Rating> getAllRating();
		
		// get a single user
		Rating getRatingById(String ratingId);
		
		// update a user
		Rating updateRating(String ratingId, Rating rating);
		
		// Delete a user
		String deletedRating(String ratingId);
		
		List<Rating> getAllRatingByUserId(String userId);
		List<Rating> getAllRatingByHotelId(String hotelId); 
}
